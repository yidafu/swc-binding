package dev.yidafu.swc.generator.converter.declaration

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.config.PropertyRulesConfig
import dev.yidafu.swc.generator.config.SerializerConfig
import dev.yidafu.swc.generator.config.TypeAliasRulesConfig
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
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
    private val inheritanceAnalyzer: InheritanceAnalyzer? = null,
    private val unionParentRegistry: MutableMap<String, MutableSet<String>> = mutableMapOf(),
    private val nestedTypeRegistry: MutableMap<String, String> = mutableMapOf()
) {
    companion object {
        private const val PARSE_OPTIONS_ALIAS = "ParseOptions"
        private const val PARSE_OPTIONS_REFERENCE = "ParserConfig"
        private const val BASE_PARSE_OPTIONS_NAME = "BaseParseOptions"
    }

    private val literalNameMap = SwcGeneratorConfig().literalNameMap
    private val typeConverter = TypeConverter(config, nestedTypeRegistry)
    private val forceNullableInterfaces = setOf("WasmAnalysisOptions")
    private val extraDeclarations = mutableListOf<KotlinDeclaration>()

    fun drainExtraDeclarations(): List<KotlinDeclaration> {
        if (extraDeclarations.isEmpty()) return emptyList()
        val snapshot = extraDeclarations.toList()
        extraDeclarations.clear()
        return snapshot
    }

    /**
     * 转换类型别名声明
     */
    fun convert(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration> {
        return try {
            Logger.debug("转换类型别名声明: ${tsTypeAlias.name}", 4)

            val mappedAliasName = CodeGenerationRules.mapTypeName(tsTypeAlias.name)

            handleIntersectionAlias(tsTypeAlias)?.let { return it }

            // 特殊规则：部分别名强制映射为 String（集中于 TypeAliasRulesConfig）
            if (TypeAliasRulesConfig.isForceStringAlias(tsTypeAlias.name)) {
                Logger.debug("  特殊处理 ${tsTypeAlias.name} -> String", 6)
                val typeAliasDecl = KotlinDeclaration.TypeAliasDecl(
                    name = wrapReservedWord(mappedAliasName),
                    type = KotlinType.StringType,
                    typeParameters = emptyList(),
                    annotations = emptyList(),
                    kdoc = tsTypeAlias.kdoc
                )
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
                name = wrapReservedWord(mappedAliasName),
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

    private fun handleIntersectionAlias(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration>? {
        val intersection = tsTypeAlias.type as? TypeScriptType.Intersection ?: return null
        if (tsTypeAlias.name != PARSE_OPTIONS_ALIAS) return null

        val reference = intersection.types.filterIsInstance<TypeScriptType.Reference>()
            .firstOrNull { it.name == PARSE_OPTIONS_REFERENCE } ?: return null
        val literal = intersection.types.filterIsInstance<TypeScriptType.TypeLiteral>().firstOrNull() ?: return null

        Logger.debug("  处理 ParseOptions 交叉类型，生成 BaseParseOptions", 6)
        val baseInterface = createBaseParseOptionsInterface(literal, tsTypeAlias.kdoc)
        extraDeclarations.add(baseInterface)
        unionParentRegistry.getOrPut(PARSE_OPTIONS_REFERENCE) { mutableSetOf() }.add(BASE_PARSE_OPTIONS_NAME)

        val aliasDecl = KotlinDeclaration.TypeAliasDecl(
            name = wrapReservedWord(CodeGenerationRules.mapTypeName(tsTypeAlias.name)),
            type = KotlinTypeFactory.simple(CodeGenerationRules.mapTypeName(reference.name)),
            typeParameters = emptyList(),
            annotations = emptyList(),
            kdoc = tsTypeAlias.kdoc
        )

        return GeneratorResultFactory.success(aliasDecl)
    }

    private fun createBaseParseOptionsInterface(
        typeLiteral: TypeScriptType.TypeLiteral,
        sourceKdoc: String?
    ): KotlinDeclaration.ClassDecl {
        val properties = typeLiteral.members.mapNotNull { member ->
            convertTypeMember(member, BASE_PARSE_OPTIONS_NAME)
        }
        return KotlinDeclaration.ClassDecl(
            name = wrapReservedWord(BASE_PARSE_OPTIONS_NAME),
            modifier = ClassModifier.SealedInterface,
            properties = properties,
            parents = emptyList(),
            typeParameters = emptyList(),
            nestedClasses = emptyList(),
            annotations = buildList {
                add(KotlinDeclaration.Annotation("SwcDslMarker"))
                // 作为 ParserConfig 多态体系一部分：应参与序列化并声明 discriminator
                add(KotlinDeclaration.Annotation("Serializable"))
                add(
                    KotlinDeclaration.Annotation(
                        name = "JsonClassDiscriminator",
                        arguments = listOf(Expression.StringLiteral(SerializerConfig.SYNTAX_DISCRIMINATOR))
                    )
                )
            },
            kdoc = sourceKdoc
        )
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
            (type as? TypeScriptType.Literal)?.value
        }
        if (enumValues.size != union.types.size) {
            Logger.warn("  字面量联合包含非字面量项，跳过枚举生成: ${tsTypeAlias.name}")
            return GeneratorResultFactory.failure(
                code = ErrorCode.UNSUPPORTED_TYPE,
                message = "Literal union contains unsupported members"
            )
        }

        val enumEntries = createEnumEntries(enumValues)
        val enumClass = KotlinDeclaration.ClassDecl(
            name = wrapReservedWord(tsTypeAlias.name),
            modifier = ClassModifier.EnumClass,
            properties = emptyList(),
            parents = emptyList(),
            typeParameters = emptyList(),
            nestedClasses = emptyList(),
            enumEntries = enumEntries,
            annotations = listOf(KotlinDeclaration.Annotation("Serializable")),
            kdoc = tsTypeAlias.kdoc
        )

        return GeneratorResultFactory.success(enumClass)
    }

    private fun createEnumEntries(values: List<LiteralValue>): List<KotlinDeclaration.EnumEntry> {
        val usedNames = mutableSetOf<String>()
        return values.map { value ->
            val serialValue = literalValueToString(value)
            val baseName = resolveEnumEntryName(serialValue)
            val uniqueName = generateUniqueName(baseName, usedNames)
            KotlinDeclaration.EnumEntry(
                name = uniqueName,
                annotations = listOf(
                    KotlinDeclaration.Annotation(
                        name = "SerialName",
                        arguments = listOf(Expression.StringLiteral(serialValue))
                    )
                )
            )
        }
    }

    private fun resolveEnumEntryName(serialValue: String): String {
        val mapping = literalNameMap[serialValue] ?: literalNameMap[serialValue.lowercase()]
        return mapping ?: formatEnumEntryName(serialValue)
    }

    private fun literalValueToString(value: LiteralValue): String {
        return when (value) {
            is LiteralValue.StringLiteral -> value.value
            is LiteralValue.NumberLiteral -> value.value.toString()
            is LiteralValue.BooleanLiteral -> value.value.toString()
            is LiteralValue.NullLiteral -> "null"
            is LiteralValue.UndefinedLiteral -> "undefined"
        }
    }

    private fun generateUniqueName(base: String, usedNames: MutableSet<String>): String {
        var candidate = if (base.isEmpty()) "VALUE" else base
        var index = 1
        while (!usedNames.add(candidate)) {
            candidate = "${base}_${index++}"
        }
        return candidate
    }

    private fun formatEnumEntryName(value: String): String {
        if (value.isEmpty()) return "VALUE"
        val builder = StringBuilder()
        value.forEach { ch ->
            when {
                ch.isLetterOrDigit() -> builder.append(ch.uppercaseChar())
                else -> builder.append('_')
            }
        }
        var result = builder.toString().trim('_')
        if (result.isEmpty()) result = "VALUE"
        if (result.first().isDigit()) result = "_$result"
        return result
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

        interfaceNames.forEach { child ->
            unionParentRegistry.getOrPut(child) { mutableSetOf() }.add(tsTypeAlias.name)
        }

        val parentTypes = unionParentRegistry[tsTypeAlias.name]
            ?.map { parentName ->
                KotlinTypeFactory.simple(CodeGenerationRules.mapTypeName(parentName))
            }
            ?: emptyList()

        val sealedInterface = KotlinDeclaration.ClassDecl(
            name = wrapReservedWord(tsTypeAlias.name),
            modifier = ClassModifier.SealedInterface,
            properties = emptyList(),
            parents = parentTypes,
            typeParameters = emptyList(),
            nestedClasses = emptyList(),
            annotations = run {
                val ann = mutableListOf<KotlinDeclaration.Annotation>()
                ann.add(KotlinDeclaration.Annotation("SwcDslMarker"))
                // 作为多态基类参与序列化
                ann.add(KotlinDeclaration.Annotation("Serializable"))
                // Config 体系或 AST 根添加 discriminator（仅在确认为根时）
                val cleanName = tsTypeAlias.name.removeSurrounding("`")
                val discriminator =
                    when {
                        SerializerConfig.configInterfaceNames.contains(cleanName) ->
                            SerializerConfig.SYNTAX_DISCRIMINATOR
                        cleanName == "Node" ->
                            SerializerConfig.DEFAULT_DISCRIMINATOR
                        else -> null
                    }
                if (discriminator != null) {
                    ann.add(
                        KotlinDeclaration.Annotation(
                            name = "JsonClassDiscriminator",
                            arguments = listOf(Expression.StringLiteral(discriminator))
                        )
                    )
                }
                ann
            },
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
            val adjustedMember = if (TypeAliasRulesConfig.forceNullableForInterface(tsTypeAlias.name)) {
                member.copy(optional = true)
            } else {
                member
            }
            convertTypeMember(adjustedMember, tsTypeAlias.name)
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
    private fun wrapReservedWord(name: String): String = PropertyRulesConfig.wrapReservedWord(name)
}
