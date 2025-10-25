package dev.yidafu.swc.generator.config

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.util.Logger

/**
 * Kotlin 代码生成规则统一管理
 * 
 * 整合了所有代码生成相关的规则，包括：
 * - 特殊属性类型覆盖规则
 * - 命名规则（保留字映射）
 * - 修饰符规则
 * - 注解规则
 * - 验证规则
 * - 过滤规则
 * 
 * 注意：基础类型转换现在完全在 ADT 转换过程中自动处理
 */
object CodeGenerationRules {

    // ==================== 特殊属性类型覆盖规则 ====================

    /**
     * 特殊属性类型覆盖规则
     * 用于处理某些属性需要特殊类型映射的情况
     */
    val propertyTypeOverrides = mapOf(
        "global_defs" to KotlinType.Generic("Map", listOf(KotlinType.StringType, KotlinType.StringType)),
        "targets" to KotlinType.Generic("Map", listOf(KotlinType.StringType, KotlinType.StringType)),
        "top_retain" to KotlinType.Booleanable(KotlinType.StringType),
        "pure_getters" to KotlinType.Booleanable(KotlinType.StringType),
        "toplevel" to KotlinType.Booleanable(KotlinType.StringType),
        "sequences" to KotlinType.Boolean
    )

    // ==================== 命名规则 ====================

    /**
     * Kotlin 保留字映射（硬编码规则）
     */
    private val kotlinKeywordMap = mapOf(
        "object" to "jsObject",
        "inline" to "jsInline", 
        "in" to "jsIn",
        "super" to "jsSuper",
        "class" to "jsClass",
        "interface" to "jsInterface",
        "fun" to "jsFun",
        "val" to "jsVal",
        "var" to "jsVar",
        "when" to "jsWhen",
        "is" to "jsIs",
        "as" to "jsAs",
        // "type" to "jsType",
        "import" to "jsImport",
        "package" to "jsPackage"
    )

    /**
     * 获取 Kotlin 保留字映射
     */
    fun getKotlinKeywordMap(): Map<String, String> {
        return kotlinKeywordMap
    }

    /**
     * 包装保留字
     */
    fun wrapReservedWord(name: String): String {
        val keywordMap = getKotlinKeywordMap()
        return if (keywordMap.containsKey(name.lowercase())) {
            "`$name`"
        } else {
            name
        }
    }

    /**
     * snake_case 转 camelCase
     */
    fun snakeToCamelCase(name: String): String {
        if (!name.contains('_')) return name
        return name.split('_')
            .mapIndexed { index, part ->
                if (index == 0) part else part.replaceFirstChar { it.uppercase() }
            }
            .joinToString("")
    }

    // ==================== 验证规则 ====================

    /**
     * 验证 Kotlin 类型名称是否有效
     */
    fun isValidKotlinTypeName(typeName: String): Boolean {
        if (typeName.isBlank()) {
            Logger.debug("类型名称为空: '$typeName'")
            return false
        }

        // 检查是否包含泛型符号
        if (typeName.contains("<") || typeName.contains(">")) {
            Logger.debug("类型名称包含泛型符号: '$typeName'")
            return false
        }

        // 检查是否包含空白字符
        if (typeName.contains(" ") || typeName.contains("\n") || typeName.contains("\t")) {
            Logger.debug("类型名称包含空白字符: '$typeName'")
            return false
        }

        // 检查是否包含注释残留
        if (typeName.contains("/*") || typeName.contains("*/")) {
            Logger.debug("类型名称包含注释: '$typeName'")
            return false
        }

        // 检查是否以小写字母开头（但保留字例外）
        val keywordMap = getKotlinKeywordMap()
        if (typeName.isNotEmpty() && typeName[0].isLowerCase() &&
            !keywordMap.containsKey(typeName.lowercase())) {
            Logger.debug("类型名称以小写字母开头: '$typeName'")
            return false
        }

        return true
    }

    /**
     * 验证属性名称是否有效
     */
    fun isValidPropertyName(propertyName: String): Boolean {
        if (propertyName.isBlank()) return false
        
        // 检查是否包含特殊字符
        val invalidChars = setOf('<', '>', ' ', '\n', '\t', '(', ')', '[', ']', '{', '}')
        if (propertyName.any { it in invalidChars }) return false
        
        return true
    }

    // ==================== 修饰符规则 ====================

    /**
     * 获取类修饰符
     */
    fun getClassModifier(
        className: String,
        isSealed: Boolean = false,
        isData: Boolean = false,
        isEnum: Boolean = false
    ): ClassModifier {
        return when {
            isEnum -> ClassModifier.EnumClass
            isData -> ClassModifier.DataClass
            isSealed -> ClassModifier.SealedInterface
            else -> ClassModifier.Interface
        }
    }

    /**
     * 获取属性修饰符
     */
    fun getPropertyModifier(
        isOverride: Boolean = false,
        isReadOnly: Boolean = false
    ): PropertyModifier {
        return when {
            isOverride && isReadOnly -> PropertyModifier.OverrideVal
            isOverride -> PropertyModifier.OverrideVar
            isReadOnly -> PropertyModifier.Val
            else -> PropertyModifier.Var
        }
    }

    // ==================== 注解规则 ====================

    /**
     * 获取序列化注解
     */
    fun getSerializationAnnotations(originalName: String, kotlinName: String): List<KotlinDeclaration.Annotation> {
        val annotations = mutableListOf<KotlinDeclaration.Annotation>()
        
        // 添加 @Serializable 注解
        annotations.add(KotlinDeclaration.Annotation("Serializable"))
        
        // 如果名称发生变化，添加 @SerialName 注解
        if (originalName != kotlinName) {
            annotations.add(KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral(originalName))))
        }
        
        return annotations
    }

    /**
     * 获取 DSL 注解
     */
    fun getDslAnnotations(): List<KotlinDeclaration.Annotation> {
        return listOf(
            KotlinDeclaration.Annotation("SwcDslMarker"),
            KotlinDeclaration.Annotation("Serializable")
        )
    }

    // ==================== 类型处理规则 ====================

    /**
     * 获取属性类型覆盖
     * 检查特定属性是否需要特殊的类型映射
     */
    fun getPropertyTypeOverride(propertyName: String): KotlinType? {
        return propertyTypeOverrides[propertyName]
    }

    /**
     * 检查是否应该为可空类型
     */
    fun shouldBeNullable(typeName: String, isOptional: Boolean = true): Boolean {
        // 所有属性默认为可空，除非明确指定为非可选
        return isOptional
    }

    /**
     * 创建可空类型
     */
    fun makeNullableIfNeeded(type: KotlinType, shouldBeNullable: Boolean = true): KotlinType {
        return if (shouldBeNullable && type !is KotlinType.Nullable) {
            KotlinType.Nullable(type)
        } else {
            type
        }
    }

    // ==================== 过滤规则 ====================


    /**
     * 跳过的 DSL 接收者
     */
    val skipDslReceivers = setOf(
        "HasSpan",
        "HasDecorator"
    )

    // 注意：skipClassPatterns 已移除
    // 所有类型现在通过 ADT 转换过程自动处理

    /**
     * 检查是否应该跳过某个 DSL 接收者
     */
    fun shouldSkipDslReceiver(receiverName: String): Boolean {
        return skipDslReceivers.contains(receiverName)
    }


    // ==================== 代码生成规则 ====================

    /**
     * 生成属性声明
     */
    fun createPropertyDecl(
        name: String,
        type: KotlinType,
        isOptional: Boolean = true,
        isOverride: Boolean = false,
        originalName: String? = null
    ): KotlinDeclaration.PropertyDecl {
        val kotlinName = wrapReservedWord(name)
        val actualOriginalName = originalName ?: name
        val isNullable = shouldBeNullable(name, isOptional)
        val finalType = makeNullableIfNeeded(type, isNullable)
        val modifier = getPropertyModifier(isOverride, false)
        val annotations = getSerializationAnnotations(actualOriginalName, kotlinName)
        
        return KotlinDeclaration.PropertyDecl(
            name = kotlinName,
            type = finalType,
            modifier = modifier,
            annotations = annotations,
            kdoc = null
        )
    }

    /**
     * 生成类声明
     */
    fun createClassDecl(
        name: String,
        modifier: ClassModifier,
        properties: List<KotlinDeclaration.PropertyDecl>,
        parents: List<String> = emptyList(),
        annotations: List<KotlinDeclaration.Annotation> = emptyList()
    ): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = wrapReservedWord(name),
            modifier = modifier,
            properties = properties,
            parents = parents.map { KotlinType.Simple(it) },
            annotations = annotations,
            kdoc = null
        )
    }

    // ==================== 错误处理规则 ====================

    /**
     * 处理类型转换错误
     */
    fun handleTypeConversionError(typeName: String, error: Exception): KotlinType {
        Logger.warn("类型转换失败: $typeName, ${error.message}")
        return KotlinType.Any
    }

    /**
     * 处理命名错误
     */
    fun handleNamingError(originalName: String, error: Exception): String {
        Logger.warn("命名处理失败: $originalName, ${error.message}")
        return "InvalidName_${System.currentTimeMillis()}"
    }
}
