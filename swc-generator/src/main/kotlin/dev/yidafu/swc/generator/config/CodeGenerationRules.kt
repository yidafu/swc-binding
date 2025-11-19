package dev.yidafu.swc.generator.config

import dev.yidafu.swc.generator.model.kotlin.*
import dev.yidafu.swc.generator.util.Logger

/**
 * Kotlin 代码生成规则统一管理
 * * 整合了所有代码生成相关的规则，包括：
 * - 特殊属性类型覆盖规则
 * - 命名规则（保留字映射）
 * - 修饰符规则
 * - 注解规则
 * - 验证规则
 * - 过滤规则
 * * 注意：基础类型转换现在完全在 ADT 转换过程中自动处理
 */
object CodeGenerationRules {
    // 存储接口名称到 type 字段字面量值的映射（用于生成实现类时的 @SerialName）
    // 这个映射表在 InterfaceConverter 中填充，在生成实现类时使用
    private val typeFieldLiteralValueMap = mutableMapOf<String, String>()

    fun getTypeFieldLiteralValue(interfaceName: String): String? {
        return typeFieldLiteralValueMap[interfaceName]
    }

    fun setTypeFieldLiteralValue(interfaceName: String, literalValue: String) {
        typeFieldLiteralValueMap[interfaceName] = literalValue
    }

    /**
     * 需要显式添加 type 字段的类列表
     * 这些类虽然有 @JsonClassDiscriminator 和 @SerialName，但作为具体类型序列化时
     * （如 Array<VariableDeclarator>），@JsonClassDiscriminator 不会自动添加 type 字段
     * 因此需要显式添加 type 字段以确保序列化时包含该字段
     * * 注意：这里使用的是 Kotlin 类名（如 "Param"），而不是序列化名称（如 "Parameter"）
     */
    val classesRequiringExplicitTypeField: Set<String> = setOf(
        "VariableDeclarator",
        "Param", // Kotlin 类名，序列化名称为 "Parameter"
        "BlockStatement",
        "JSXOpeningElement",
        "JSXClosingElement"
    )

    // ==================== 特殊属性类型覆盖规则 ====================

    /**
     * 特殊属性类型覆盖规则
     * 用于处理某些属性需要特殊类型映射的情况
     */
    val propertyTypeOverrides = mapOf(
        // 某些 Any 类型目前仅用 String 作为占位（暂不完全支持）
        "global_defs" to KotlinType.StringType.makeNullable(),
        "globalDefs" to KotlinType.StringType.makeNullable(),
        "targets" to KotlinType.StringType.makeNullable(),
        "sequences" to KotlinType.StringType.makeNullable(),
        "pure_getters" to KotlinType.StringType.makeNullable(),
        "pureGetters" to KotlinType.StringType.makeNullable(),
        "top_retain" to KotlinType.StringType.makeNullable(),
        "topRetain" to KotlinType.StringType.makeNullable(),
        "toplevel" to KotlinType.StringType.makeNullable()
    )

    /**
     * 获取特定接口和属性的类型覆盖
     * 用于处理某些属性在特定接口中需要特殊类型映射的情况
     * * @param interfaceName 接口名称
     * @param propertyName 属性名称
     * @return 覆盖后的类型，如果没有覆盖则返回 null
     */
    fun getPropertyTypeOverride(interfaceName: String, propertyName: String): KotlinType? {
        val cleanInterfaceName = interfaceName.removeSurrounding("`")
        val cleanPropertyName = propertyName.removeSurrounding("`")

        // ForOfStatement.await 在 TS 中声明为 Span?，但实际应该是 Boolean?
        // 这是 SWC 的特殊情况，需要特殊处理
        if (cleanInterfaceName == "ForOfStatement" && cleanPropertyName == "await") {
            return KotlinType.Boolean.makeNullable()
        }

        return null
    }

    // ==================== 命名规则 ====================

    /**
     * Kotlin 保留字映射（硬编码规则）
     */
    private val kotlinKeywordMap = mapOf(
        "object" to "jsObject",
        "inline" to "jsInline", "in" to "jsIn",
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
     * 类型名称覆盖，避免与 Kotlin 内置或保留名称冲突
     */
    private val typeNameOverrides = mapOf(
        "Class" to "JsClass",
        "Super" to "JsSuper",
        "Import" to "JsImport",
        // 特殊处理：将 ExprOrSpread 统一映射为 Argument，避免双类型导致的解码/DSL 冲突
        "ExprOrSpread" to "Argument"
    )

    /**
     * 获取 Kotlin 保留字映射
     */
    fun getKotlinKeywordMap(): Map<String, String> {
        return kotlinKeywordMap
    }

    /**
     * 映射类型名称，避免命名冲突
     */
    fun mapTypeName(name: String): String {
        return typeNameOverrides[name] ?: name
    }

    /**
     * 反向映射类型名称，从映射后的名称获取原始名称
     * 例如：JsClass -> Class
     */
    fun getReverseMappedName(mappedName: String): String? {
        return typeNameOverrides.entries.find { it.value == mappedName }?.key
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
            !keywordMap.containsKey(typeName.lowercase())
        ) {
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
     * * 对于多态序列化，即使名称相同，也需要 @SerialName 注解来确保序列化器能正确识别类型。
     * 因此，我们始终添加 @SerialName 注解。
     */
    fun getSerializationAnnotations(originalName: String, kotlinName: String): List<KotlinDeclaration.Annotation> {
        val annotations = mutableListOf<KotlinDeclaration.Annotation>()

        // 添加 @Serializable 注解
        annotations.add(KotlinDeclaration.Annotation("Serializable"))

        // 始终添加 @SerialName 注解，这对于多态序列化是必需的
        // 如果名称发生变化，使用原始名称；否则使用类名
        val serialNameValue = if (originalName != kotlinName) {
            originalName
        } else {
            kotlinName
        }
        annotations.add(KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral(serialNameValue))))

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
        "HasDecorator",
        // 合并处理：跳过为 ExprOrSpread 生成 DSL，统一用 Argument 的 DSL
        "ExprOrSpread"
    )

    // 注意：skipClassPatterns 已移除
    // 所有类型现在通过 ADT 转换过程自动处理

    /**
     * 检查是否应该跳过某个 DSL 接收者
     */
    fun shouldSkipDslReceiver(receiverName: String): Boolean {
        return skipDslReceivers.contains(receiverName)
    }

    /**
     * 判断 DSL 扩展函数是否可注册
     */
    fun canRegisterDslExtension(receiverName: String, funName: String): Boolean {
        if (receiverName == funName) return false
        if (shouldSkipDslReceiver(receiverName)) return false
        if (!isValidKotlinTypeName(funName) || !isValidKotlinTypeName(receiverName)) return false
        return true
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

/**
 * Types 生成相关的可组合规则
 */
object TypesImplementationRules {
    private val parserSyntaxLiteral = mapOf(
        "TsParserConfig" to "typescript",
        "EsParserConfig" to "ecmascript"
    )

    data class InterfaceRule(
        val interfaceCleanName: String,
        val syntaxLiteral: String?,
        val discriminator: String
    )

    fun createInterfaceRule(interfaceName: String): InterfaceRule {
        val cleanName = interfaceName.removeSurrounding("`")
        val syntaxLiteral = parserSyntaxLiteral[cleanName]
        val discriminator = if (syntaxLiteral != null) "syntax" else "type"
        return InterfaceRule(
            interfaceCleanName = cleanName,
            syntaxLiteral = syntaxLiteral,
            discriminator = discriminator
        )
    }

    fun implementationAnnotations(rule: InterfaceRule, interfaceDecl: KotlinDeclaration.ClassDecl? = null): List<KotlinDeclaration.Annotation> {
        // 尝试从接口的 type 属性中提取字面量值（如果属性有默认值）
        val typeFieldLiteralValueFromProperty = interfaceDecl?.properties?.find { it.name.removeSurrounding("`") == "type" }?.defaultValue?.let { defaultValue ->
            when (defaultValue) {
                is Expression.StringLiteral -> defaultValue.value
                else -> null
            }
        }

        // 如果没有从属性中获取到，尝试从全局映射表中获取（接口属性没有默认值，但字面量值已存储在映射表中）
        val typeFieldLiteralValue = typeFieldLiteralValueFromProperty ?: CodeGenerationRules.getTypeFieldLiteralValue(rule.interfaceCleanName)

        // 特殊处理：检测已知的 @SerialName 冲突
        // 1. BindingIdentifier 和 Identifier 有相同的 type 值 "Identifier"
        // 2. TsTemplateLiteralType 和 TemplateLiteral 有相同的 type 值 "TemplateLiteral"
        // 为了避免 @SerialName 冲突，这些类型使用接口名称
        val hasSerialNameConflict = when {
            rule.interfaceCleanName == "BindingIdentifier" && typeFieldLiteralValue == "Identifier" -> true
            rule.interfaceCleanName == "TsTemplateLiteralType" && typeFieldLiteralValue == "TemplateLiteral" -> true
            else -> false
        }

        // 特殊处理：ComputedPropName 应该使用 TS 字面量值 "Computed" 而不是接口名 "ComputedPropName"
        // 这是为了确保序列化时使用正确的类型标识符
        val shouldUseLiteralValue = when {
            rule.interfaceCleanName == "ComputedPropName" && typeFieldLiteralValue == "Computed" -> true
            else -> false
        }

        // 优先使用从 TypeScript 提取的字面量值，否则使用 syntaxLiteral 或 interfaceCleanName
        // 如果检测到冲突，使用接口名称作为后备方案
        // ComputedPropName 强制使用字面量值 "Computed"
        val serialNameValue = when {
            hasSerialNameConflict -> rule.interfaceCleanName
            shouldUseLiteralValue && typeFieldLiteralValue != null -> typeFieldLiteralValue
            typeFieldLiteralValue != null && rule.discriminator == "type" -> typeFieldLiteralValue
            rule.syntaxLiteral != null -> rule.syntaxLiteral
            else -> rule.interfaceCleanName
        }

        // Impl 类是 FinalClass，注解应该由 RegularClassConverter.addPolymorphicAnnotations 添加
        // 这里不添加任何注解，避免与 RegularClassConverter 重复
        // 注意：如果需要特殊处理，可以在 RegularClassConverter 中处理
        return emptyList()
    }

    fun reorderImplementationProperties(
        allProperties: List<KotlinDeclaration.PropertyDecl>,
        interfaceDecl: KotlinDeclaration.ClassDecl
    ): List<KotlinDeclaration.PropertyDecl> {
        val ownProperties = mutableListOf<KotlinDeclaration.PropertyDecl>()
        val inheritedProperties = mutableListOf<KotlinDeclaration.PropertyDecl>()
        val typeProperty = mutableListOf<KotlinDeclaration.PropertyDecl>()
        val spanProperty = mutableListOf<KotlinDeclaration.PropertyDecl>()
        val decoratorsProperty = mutableListOf<KotlinDeclaration.PropertyDecl>()

        allProperties.forEach { prop ->
            when (prop.name) {
                "type" -> typeProperty.add(prop)
                "span" -> spanProperty.add(prop)
                "decorators" -> decoratorsProperty.add(prop)
                else -> {
                    if (isOwnProperty(prop.name, interfaceDecl)) {
                        ownProperties.add(prop)
                    } else {
                        inheritedProperties.add(prop)
                    }
                }
            }
        }

        return ownProperties + inheritedProperties + typeProperty + spanProperty + decoratorsProperty
    }

    fun processImplementationProperty(
        prop: KotlinDeclaration.PropertyDecl,
        rule: InterfaceRule
    ): KotlinDeclaration.PropertyDecl {
        val newModifier = when (prop.modifier) {
            is PropertyModifier.Var -> PropertyModifier.OverrideVar
            is PropertyModifier.Val -> PropertyModifier.OverrideVal
            else -> prop.modifier
        }

        val normalizedName = prop.name.removeSurrounding("`")
        val isTypeProperty = normalizedName == "type"
        val isSyntaxProperty = normalizedName == "syntax" && rule.syntaxLiteral != null

        val isSpanCoordinateProperty = rule.interfaceCleanName == "Span" &&
            normalizedName in setOf("start", "end", "ctxt")
        val isSpanProperty = normalizedName == "span"

        val updatedType = when {
            isTypeProperty -> KotlinType.StringType
            isSyntaxProperty -> KotlinType.StringType
            isSpanProperty -> KotlinType.Simple("Span")
            isSpanCoordinateProperty -> KotlinType.Int
            prop.type is KotlinType.Nullable -> prop.type
            else -> KotlinType.Nullable(prop.type)
        }

        val defaultValue = when {
            // 如果是 type 属性，优先使用从 TypeScript 提取的字面量值，否则使用接口名称
            // 如果属性没有默认值（接口属性被清除了），尝试从全局映射表中获取
            isTypeProperty -> prop.defaultValue ?: CodeGenerationRules.getTypeFieldLiteralValue(rule.interfaceCleanName)?.let { literalValue -> Expression.StringLiteral(literalValue) }
                ?: Expression.StringLiteral(rule.interfaceCleanName)
            isSyntaxProperty -> Expression.StringLiteral(rule.syntaxLiteral!!)
            // 对于 span 属性，使用 emptySpan() 函数调用，确保包含 ctxt 字段
            isSpanProperty -> Expression.FunctionCall("emptySpan")
            isSpanCoordinateProperty -> Expression.NumberLiteral("0")
            updatedType is KotlinType.Nullable -> Expression.NullLiteral
            else -> prop.defaultValue
        }

        val annotations = buildList {
            addAll(prop.annotations)
            // 不再为 type/syntax 判别字段添加 @Transient 注解
            // Span 坐标属性：start 和 end 需要 @EncodeDefault，但 ctxt 不需要
            // 因为 @swc/core 不输出默认值为 0 的 ctxt 字段
            if (isSpanProperty) {
                add(KotlinDeclaration.Annotation("EncodeDefault"))
            } else if (isSpanCoordinateProperty) {
                // 只有 start 和 end 需要 @EncodeDefault，ctxt 不需要
                val normalizedName = prop.name.removeSurrounding("`")
                if (normalizedName in setOf("start", "end")) {
                    add(KotlinDeclaration.Annotation("EncodeDefault"))
                }
                // ctxt 字段不使用 @EncodeDefault，这样默认值 0 不会被序列化
            }
        }

        return prop.copy(
            modifier = newModifier,
            type = updatedType,
            defaultValue = defaultValue,
            annotations = annotations
        )
    }

    private fun isOwnProperty(
        propertyName: String,
        interfaceDecl: KotlinDeclaration.ClassDecl
    ): Boolean {
        return interfaceDecl.properties.any { it.name == propertyName }
    }
}
