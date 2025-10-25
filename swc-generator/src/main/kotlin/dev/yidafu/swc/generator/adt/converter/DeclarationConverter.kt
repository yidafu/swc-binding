package dev.yidafu.swc.generator.adt.converter

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.result.*
import dev.yidafu.swc.generator.adt.typescript.*
import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.config.HardcodedRules
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.util.Logger

/**
 * TypeScript 声明到 Kotlin 声明的转换器
 * 处理特殊情况和配置规则
 */
class DeclarationConverter(private val config: SwcGeneratorConfig) {

    private val typeConverter = TypeConverter(config)

    /**
     * 转换接口声明为 Kotlin 类声明
     */
    fun convertInterfaceDeclaration(
        tsInterface: TypeScriptDeclaration.InterfaceDeclaration
    ): GeneratorResult<KotlinDeclaration.ClassDecl> {
        return try {
            Logger.debug("转换接口声明: ${tsInterface.name}", 4)

            // 转换类型参数（暂时未使用，保留用于未来扩展）
            // val kotlinTypeParams = convertTypeParameters(tsInterface.typeParameters)

            // 转换继承关系
            val kotlinParents = tsInterface.extends.map { typeRef ->
                convertTypeReference(typeRef)
            }

            // 转换成员为属性（只转换接口自己的成员，不包括继承的）
            val kotlinProperties = tsInterface.members.mapNotNull { member ->
                convertTypeMember(member, tsInterface.name)
            }

            // 过滤掉重复声明的父接口属性
            val filteredProperties = filterInheritedProperties(kotlinProperties, kotlinParents)

            // 确定修饰符
            val modifier = determineClassModifier(tsInterface)

            // 添加注解
            val annotations = buildAnnotations(tsInterface)

            val classDecl = KotlinDeclaration.ClassDecl(
                name = wrapReservedWord(tsInterface.name),
                modifier = modifier,
                properties = filteredProperties,
                parents = kotlinParents,
                annotations = annotations,
                kdoc = tsInterface.kdoc
            )

            Logger.debug("  属性数: ${kotlinProperties.size}, 父类型: ${kotlinParents.size}", 6)

            GeneratorResultFactory.success(classDecl)
        } catch (e: Exception) {
            Logger.error("转换接口声明失败: ${tsInterface.name}, ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.TYPE_RESOLUTION_ERROR,
                message = "Failed to convert interface declaration: ${tsInterface.name}",
                cause = e
            )
        }
    }

    /**
     * 转换类型别名声明为 Kotlin 类型别名声明
     */
    fun convertTypeAliasDeclaration(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration.TypeAliasDecl> {
        return try {
            Logger.debug("转换类型别名声明: ${tsTypeAlias.name}", 4)

            // 转换类型
            val kotlinType = typeConverter.convertToKotlinType(tsTypeAlias.type)
                .getOrDefault(KotlinType.Any)

            // 转换类型参数
            val kotlinTypeParams = convertTypeParameters(tsTypeAlias.typeParameters)

            val typeAliasDecl = KotlinDeclaration.TypeAliasDecl(
                name = wrapReservedWord(tsTypeAlias.name),
                type = kotlinType,
                typeParameters = kotlinTypeParams,
                annotations = emptyList(),
                kdoc = tsTypeAlias.kdoc
            )

            Logger.debug("  类型: ${kotlinType.toTypeString()}, 类型参数: ${kotlinTypeParams.size}", 6)

            GeneratorResultFactory.success(typeAliasDecl)
        } catch (e: Exception) {
            Logger.error("转换类型别名声明失败: ${tsTypeAlias.name}, ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.TYPE_RESOLUTION_ERROR,
                message = "Failed to convert type alias declaration: ${tsTypeAlias.name}",
                cause = e
            )
        }
    }

    /**
     * 转换类型参数
     */
    private fun convertTypeParameters(tsTypeParams: List<TypeParameter>): List<KotlinDeclaration.TypeParameter> {
        return tsTypeParams.map { tsParam ->
            val constraint = tsParam.constraint?.let { constraint ->
                typeConverter.convertToKotlinType(constraint).getOrNull()
            }
            // val default = tsParam.default?.let { default ->
            //     typeConverter.convertToKotlinType(default).getOrNull()
            // }

            KotlinDeclaration.TypeParameter(
                name = tsParam.name,
                variance = convertVariance(tsParam.variance),
                upperBounds = constraint?.let { listOf(it) } ?: emptyList()
            )
        }
    }

    /**
     * 转换变体
     */
    private fun convertVariance(variance: dev.yidafu.swc.generator.adt.typescript.Variance): dev.yidafu.swc.generator.adt.kotlin.Variance {
        return when (variance) {
            dev.yidafu.swc.generator.adt.typescript.Variance.INVARIANT -> dev.yidafu.swc.generator.adt.kotlin.Variance.INVARIANT
            dev.yidafu.swc.generator.adt.typescript.Variance.COVARIANT -> dev.yidafu.swc.generator.adt.kotlin.Variance.COVARIANT
            dev.yidafu.swc.generator.adt.typescript.Variance.CONTRAVARIANT -> dev.yidafu.swc.generator.adt.kotlin.Variance.CONTRAVARIANT
        }
    }

    /**
     * 转换类型引用
     */
    private fun convertTypeReference(typeRef: TypeReference): KotlinType {
        val typeArgs = typeRef.typeArguments.mapNotNull { typeArg ->
            typeConverter.convertToKotlinType(typeArg).getOrNull()
        }
        return if (typeArgs.isNotEmpty()) {
            KotlinTypeFactory.generic(typeRef.name, *typeArgs.toTypedArray())
        } else {
            KotlinTypeFactory.simple(typeRef.name)
        }
    }

    /**
     * 转换类型成员为属性
     */
    private fun convertTypeMember(member: TypeMember, interfaceName: String): KotlinDeclaration.PropertyDecl? {
        val kotlinType = typeConverter.convertToKotlinType(member.type)
            .getOrDefault(KotlinType.Any)

        // 应用配置重写
        val actualName = CodeGenerationRules.getKotlinKeywordMap()[member.name] ?: member.name
        val finalType = HardcodedRules.getPropertyTypeOverride(actualName) ?: kotlinType

        // 转换为驼峰命名（Kotlin 命名规范）
        val camelCaseName = snakeToCamelCase(actualName)
        
        // 提取默认值
        val defaultValue = extractDefaultValue(member.type)

        // 如果名称被转换了，添加 @SerialName 注解保存原始名称
        val annotations = if (camelCaseName != actualName) {
            listOf(KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral(actualName))))
        } else {
            emptyList()
        }

        // type 属性应该是不可空的，其他属性应该是可空的
        val nullableType = when {
            camelCaseName == "type" -> finalType
            finalType is KotlinType.Nullable -> finalType
            else -> KotlinType.Nullable(finalType)
        }
        
        // 检查是否在 toKotlinClass 配置中，如果是，则属性应该有默认值
        val isInToKotlinClass = config.toKotlinClass.contains(interfaceName)
        val propertyDefaultValue = if (isInToKotlinClass) {
            Expression.NullLiteral
        } else if (defaultValue.isNotBlank()) {
            Expression.StringLiteral(defaultValue)
        } else {
            null
        }

        return KotlinDeclaration.PropertyDecl(
            name = wrapReservedWord(camelCaseName),
            type = nullableType,
            modifier = if (member.readonly) PropertyModifier.Val else PropertyModifier.Var,
            defaultValue = propertyDefaultValue,
            annotations = annotations,
            kdoc = member.kdoc
        )
    }

    /**
     * 从 TypeScript 类型提取默认值
     */
    private fun extractDefaultValue(tsType: TypeScriptType): String {
        return when (tsType) {
            is TypeScriptType.Literal -> when (val value = tsType.value) {
                is LiteralValue.StringLiteral -> value.value  // 不添加双引号，让 Expression.StringLiteral 处理
                is LiteralValue.NumberLiteral -> value.value.toString()
                is LiteralValue.BooleanLiteral -> value.value.toString()
                else -> ""
            }
            else -> ""
        }
    }

    /**
     * 过滤掉重复声明的父接口属性
     */
    private fun filterInheritedProperties(
        properties: List<KotlinDeclaration.PropertyDecl>,
        parents: List<KotlinType>
    ): List<KotlinDeclaration.PropertyDecl> {
        // 获取父接口的属性名集合
        val parentPropertyNames = mutableSetOf<String>()
        
        // 这里简化处理，只检查常见的父接口属性
        // 在实际应用中，可能需要更复杂的继承关系分析
        if (parents.any { it is KotlinType.Simple && it.name == "Node" }) {
            parentPropertyNames.add("type")
        }
        if (parents.any { it is KotlinType.Simple && it.name == "HasSpan" }) {
            parentPropertyNames.add("span")
        }
        if (parents.any { it is KotlinType.Simple && it.name == "HasDecorator" }) {
            parentPropertyNames.add("decorators")
        }
        if (parents.any { it is KotlinType.Simple && it.name == "HasInterpreter" }) {
            parentPropertyNames.add("interpreter")
        }
        
        // 过滤掉父接口中已存在的属性
        return properties.filter { prop ->
            !parentPropertyNames.contains(prop.name)
        }
    }

    /**
     * 确定类修饰符
     */
    private fun determineClassModifier(tsInterface: TypeScriptDeclaration.InterfaceDeclaration): ClassModifier {
        return when {
            config.toKotlinClass.contains(tsInterface.name) -> ClassModifier.FinalClass
            else -> ClassModifier.Interface
        }
    }

    /**
     * 构建注解
     */
    private fun buildAnnotations(tsInterface: TypeScriptDeclaration.InterfaceDeclaration): List<KotlinDeclaration.Annotation> {
        val annotations = mutableListOf<KotlinDeclaration.Annotation>()

        // 根据配置添加特殊注解
        when {
            config.toKotlinClass.contains(tsInterface.name) -> {
                annotations.add(KotlinDeclaration.Annotation("Serializable"))
            }
        }

        return annotations
    }

    /**
     * 包装保留字
     */
    private fun wrapReservedWord(name: String): String {
        return CodeGenerationRules.getKotlinKeywordMap()[name] ?: name
    }

    /**
     * 将 snake_case 转换为 camelCase
     */
    private fun snakeToCamelCase(name: String): String {
        if (!name.contains('_')) {
            return name
        }
        
        return name.split('_')
            .mapIndexed { index, part ->
                if (index == 0) {
                    part
                } else {
                    part.replaceFirstChar { it.uppercase() }
                }
            }
            .joinToString("")
    }

    companion object {
        /**
         * 便捷的转换方法
         */
        fun convertInterfaceDeclaration(
            tsInterface: TypeScriptDeclaration.InterfaceDeclaration,
            config: SwcGeneratorConfig
        ): GeneratorResult<KotlinDeclaration.ClassDecl> {
            val converter = DeclarationConverter(config)
            return converter.convertInterfaceDeclaration(tsInterface)
        }

        fun convertTypeAliasDeclaration(
            tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration,
            config: SwcGeneratorConfig
        ): GeneratorResult<KotlinDeclaration.TypeAliasDecl> {
            val converter = DeclarationConverter(config)
            return converter.convertTypeAliasDeclaration(tsTypeAlias)
        }
    }
}
