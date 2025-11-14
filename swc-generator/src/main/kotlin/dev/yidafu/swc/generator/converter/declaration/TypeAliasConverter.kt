package dev.yidafu.swc.generator.converter.declaration

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.converter.type.TypeConverter
import dev.yidafu.swc.generator.model.kotlin.*
import dev.yidafu.swc.generator.model.typescript.*
import dev.yidafu.swc.generator.model.typescript.TypeParameter
import dev.yidafu.swc.generator.model.typescript.Variance
import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.util.Logger

/**
 * 类型别名转换器
 * 负责将 TypeScript 类型别名声明转换为 Kotlin 声明
 */
class TypeAliasConverter(
    private val config: Configuration,
    private val inheritanceAnalyzer: InheritanceAnalyzer? = null
) {
    
    private val typeConverter = TypeConverter(config)
    
    /**
     * 转换类型别名声明
     */
    fun convert(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration> {
        return try {
            Logger.debug("转换类型别名声明: ${tsTypeAlias.name}", 4)

            // 特殊处理：为 ToSnakeCase 和 ToSnakeCaseProperties 添加泛型参数
            if (tsTypeAlias.name in listOf("ToSnakeCase", "ToSnakeCaseProperties")) {
                Logger.debug("  特殊处理复杂类型别名: ${tsTypeAlias.name}", 6)
                val kotlinTypeParams = listOf(
                    KotlinDeclaration.TypeParameter(
                        name = "T",
                        variance = KotlinDeclaration.Variance.INVARIANT,
                        constraint = null
                    )
                )
                
                val typeAliasDecl = KotlinDeclaration.TypeAliasDecl(
                    name = wrapReservedWord(tsTypeAlias.name),
                    type = KotlinTypeFactory.any(),
                    typeParameters = kotlinTypeParams,
                    annotations = emptyList(),
                    kdoc = tsTypeAlias.kdoc
                )

                Logger.debug("  类型: Any, 类型参数: ${kotlinTypeParams.size}", 6)
                return GeneratorResultFactory.success(typeAliasDecl)
            }

            // 检查是否为字面量联合类型
            if (isLiteralUnionType(tsTypeAlias.type)) {
                // 检查是否在 literalUnionToTypealias 配置中
                if (config.rules.classModifiers.literalUnionToTypealias.contains(tsTypeAlias.name)) {
                    Logger.debug("  检测到字面量联合类型，但配置为生成 typealias", 6)
                    // 继续生成 typealias
                } else {
                    Logger.debug("  检测到字面量联合类型，生成枚举类", 6)
                    return convertLiteralUnionToEnumClass(tsTypeAlias)
                }
            }
            // 检查是否为接口联合类型
            else if (isInterfaceUnionType(tsTypeAlias.type)) {
                Logger.debug("  检测到接口联合类型，生成密封接口", 6)
                return convertInterfaceUnionToSealedInterface(tsTypeAlias)
            }
            // 检查是否为类型字面量（应该生成接口）
            else if (tsTypeAlias.type is TypeScriptType.TypeLiteral) {
                Logger.debug("  检测到类型字面量，生成接口", 6)
                return convertTypeLiteralToInterface(tsTypeAlias)
            }

            // 转换类型
            val kotlinType = typeConverter.convert(tsTypeAlias.type)
                .getOrDefault(KotlinTypeFactory.any())

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
    private fun convertTypeParameters(typeParameters: List<TypeParameter>): List<KotlinDeclaration.TypeParameter> {
        return typeParameters.map { param ->
            KotlinDeclaration.TypeParameter(
                name = wrapReservedWord(param.name),
                variance = convertVariance(param.variance),
                constraint = param.constraint?.let { bound ->
                    typeConverter.convert(bound).getOrNull()
                }
            )
        }
    }
    
    /**
     * 转换变体
     */
    private fun convertVariance(variance: Variance): KotlinDeclaration.Variance {
        return when (variance) {
            Variance.INVARIANT -> KotlinDeclaration.Variance.INVARIANT
            Variance.COVARIANT -> KotlinDeclaration.Variance.COVARIANT
            Variance.CONTRAVARIANT -> KotlinDeclaration.Variance.CONTRAVARIANT
        }
    }
    
    /**
     * 检查是否为字面量联合类型
     */
    private fun isLiteralUnionType(type: TypeScriptType): Boolean {
        return type is TypeScriptType.Union && type.types.all { it is TypeScriptType.Literal }
    }
    
    /**
     * 检查是否为接口联合类型
     */
    private fun isInterfaceUnionType(type: TypeScriptType): Boolean {
        return type is TypeScriptType.Union && type.types.all { it is TypeScriptType.Reference }
    }
    
    /**
     * 转换字面量联合类型为枚举类
     */
    private fun convertLiteralUnionToEnumClass(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration.ClassDecl> {
        val union = tsTypeAlias.type as TypeScriptType.Union
        val enumValues = union.types.mapNotNull { type ->
            when (type) {
                is TypeScriptType.Literal -> {
                    when (type.value) {
                        is LiteralValue.StringLiteral -> type.value.value
                        is LiteralValue.NumberLiteral -> type.value.value.toString()
                        is LiteralValue.BooleanLiteral -> type.value.value.toString()
                        else -> null
                    }
                }
                else -> null
            }
        }
        
        val enumClass = KotlinDeclaration.ClassDecl(
            name = wrapReservedWord(tsTypeAlias.name),
            modifier = ClassModifier.EnumClass,
            properties = emptyList(),
            parents = emptyList(),
            typeParameters = emptyList(),
            nestedClasses = emptyList(),
            annotations = listOf(KotlinDeclaration.Annotation("Serializable")),
            kdoc = tsTypeAlias.kdoc
        )
        
        return GeneratorResultFactory.success(enumClass)
    }
    
    /**
     * 转换接口联合类型为密封接口
     */
    private fun convertInterfaceUnionToSealedInterface(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration.ClassDecl> {
        val union = tsTypeAlias.type as TypeScriptType.Union
        val interfaceNames = union.types.mapNotNull { type ->
            if (type is TypeScriptType.Reference) type.name else null
        }
        
        val sealedInterface = KotlinDeclaration.ClassDecl(
            name = wrapReservedWord(tsTypeAlias.name),
            modifier = ClassModifier.SealedInterface,
            properties = emptyList(),
            parents = emptyList(),
            typeParameters = emptyList(),
            nestedClasses = emptyList(),
            annotations = listOf(KotlinDeclaration.Annotation("SwcDslMarker")),
            kdoc = tsTypeAlias.kdoc
        )
        
        return GeneratorResultFactory.success(sealedInterface)
    }
    
    /**
     * 转换类型字面量为接口
     */
    private fun convertTypeLiteralToInterface(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration.ClassDecl> {
        val typeLiteral = tsTypeAlias.type as TypeScriptType.TypeLiteral
        val properties = typeLiteral.members.mapNotNull { member ->
            convertTypeMember(member, tsTypeAlias.name)
        }
        
        val interfaceDecl = KotlinDeclaration.ClassDecl(
            name = wrapReservedWord(tsTypeAlias.name),
            modifier = ClassModifier.Interface,
            properties = properties,
            parents = emptyList(),
            typeParameters = emptyList(),
            nestedClasses = emptyList(),
            annotations = emptyList(),
            kdoc = tsTypeAlias.kdoc
        )
        
        return GeneratorResultFactory.success(interfaceDecl)
    }
    
    /**
     * 转换类型成员为属性
     */
    private fun convertTypeMember(member: TypeMember, interfaceName: String): KotlinDeclaration.PropertyDecl? {
        val kotlinType = typeConverter.convert(member.type).getOrDefault(KotlinTypeFactory.any())
        
        // 处理可空性
        val finalType = if (member.optional) {
            kotlinType.makeNullable()
        } else {
            kotlinType
        }
        
        // 处理属性名
        val propertyName = wrapReservedWord(member.name)
        
        // 处理只读属性
        val modifier = if (member.readonly) {
            PropertyModifier.Val
        } else {
            PropertyModifier.Var
        }
        
        return KotlinDeclaration.PropertyDecl(
            name = propertyName,
            type = finalType,
            modifier = modifier,
            annotations = emptyList(),
            kdoc = member.kdoc
        )
    }
    
    /**
     * 包装保留字
     */
    private fun wrapReservedWord(name: String): String {
        val reservedWords = setOf(
            "object", "inline", "in", "super", "class", "interface", "fun",
            "val", "var", "when", "is", "as", "type", "import", "package"
        )
        
        return if (reservedWords.contains(name.lowercase())) {
            "`$name`"
        } else {
            name
        }
    }
}
