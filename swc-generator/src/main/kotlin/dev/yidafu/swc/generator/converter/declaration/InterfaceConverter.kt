package dev.yidafu.swc.generator.converter.declaration

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.config.CodeGenerationRules
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
 * 接口转换器
 * 负责将 TypeScript 接口声明转换为 Kotlin 类声明
 */
class InterfaceConverter(
    private val config: Configuration,
    private val inheritanceAnalyzer: InheritanceAnalyzer? = null,
    private val unionParentRegistry: MutableMap<String, MutableSet<String>> = mutableMapOf(),
    private val nestedTypeRegistry: MutableMap<String, String> = mutableMapOf()
) {
    private val syntaxLiteralMap = mapOf(
        "TsParserConfig" to "typescript",
        "EsParserConfig" to "ecmascript"
    )

    private val typeConverter = TypeConverter(config, nestedTypeRegistry)
    private val fallbackParentProperties = mapOf(
        "Node" to setOf("type"),
        "ExpressionBase" to setOf("type"),
        "PropBase" to setOf("type"),
        "PatternBase" to setOf("type"),
        "ClassPropertyBase" to setOf("type"),
        "ClassMember" to setOf("type"),
        "ClassMethodBase" to setOf("type"),
        "HasSpan" to setOf("span"),
        "HasDecorator" to setOf("decorators"),
        "ClassMember" to emptySet(),
        "Expression" to emptySet(),
        "Statement" to emptySet(),
        "Declaration" to emptySet(),
        "Pattern" to emptySet(),
        "Property" to emptySet(),
        "PropertyName" to emptySet(),
        "JSXObject" to emptySet(),
        "JSXElementName" to emptySet(),
        "JSXAttributeName" to emptySet(),
        "JSXExpression" to emptySet(),
        "JSXAttrValue" to emptySet(),
        "JSXElementChild" to emptySet(),
        "JSXAttributeOrSpread" to emptySet(),
        "ModuleExportName" to emptySet(),
        "TsEntityName" to emptySet(),
        "TsThisTypeOrIdent" to emptySet(),
        "TsEnumMemberId" to emptySet(),
        "TsModuleName" to emptySet(),
        "TsLiteral" to emptySet(),
        "TsTypeQueryExpr" to emptySet(),
        "TsModuleReference" to emptySet(),
        "TsNamespaceBody" to emptySet(),
        "TsTypeElement" to emptySet(),
        "TsFnParameter" to emptySet(),
        "TsParameterPropertyParameter" to emptySet(),
        "ObjectPatternProperty" to emptySet(),
        "ImportSpecifier" to emptySet(),
        "ExportSpecifier" to emptySet(),
        "DefaultDecl" to emptySet(),
        "ModuleItem" to emptySet(),
        "SealedInterface" to emptySet()
    )

    /**
     * 转换接口声明为 Kotlin 类声明
     */
    fun convert(
        tsInterface: TypeScriptDeclaration.InterfaceDeclaration
    ): GeneratorResult<KotlinDeclaration.ClassDecl> {
        return try {
            Logger.debug("转换接口声明: ${tsInterface.name}", 4)

            // 转换类型参数
            val kotlinTypeParams = convertTypeParameters(tsInterface.typeParameters)

            // 转换继承关系
            val kotlinParents = tsInterface.extends.map { typeRef ->
                convertTypeReference(typeRef)
            }.toMutableList()

            unionParentRegistry[tsInterface.name]?.forEach { parentName ->
                kotlinParents.add(
                    KotlinTypeFactory.simple(CodeGenerationRules.mapTypeName(parentName))
                )
            }

            val parentPropertyNames = collectParentPropertyNamesForParents(kotlinParents, tsInterface.name)

            val nestedInterfaces = mutableListOf<KotlinDeclaration.ClassDecl>()

            // 转换成员为属性（只转换接口自己的成员，不包括继承的）
            // 去重：优先保留 camelCase 版本，如果只有 snake_case 版本则转换
            val deduplicatedMembers = deduplicateMembers(tsInterface.members)
            val mappedInterfaceName = CodeGenerationRules.mapTypeName(tsInterface.name)
            val kotlinProperties = deduplicatedMembers.mapNotNull { member ->
                convertTypeMember(member, mappedInterfaceName, nestedInterfaces)
            }

            val propertiesWithOverride = applyOverrideModifiers(kotlinProperties, parentPropertyNames)

            // 过滤掉重复声明的父接口属性
            val filteredProperties = filterInheritedProperties(propertiesWithOverride, parentPropertyNames, tsInterface.name)

            // 确定修饰符
            val modifier = determineClassModifier(tsInterface)

            // 添加注解
            val annotations = buildAnnotations(tsInterface)

            val classDecl = KotlinDeclaration.ClassDecl(
                name = wrapReservedWord(mappedInterfaceName),
                modifier = modifier,
                properties = filteredProperties,
                parents = kotlinParents,
                typeParameters = kotlinTypeParams,
                nestedClasses = nestedInterfaces,
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
     * 转换类型引用
     */
    private fun convertTypeReference(typeRef: TypeReference): KotlinType {
        val mappedName = CodeGenerationRules.mapTypeName(typeRef.name)
        val typeArgs = typeRef.typeArguments.mapNotNull { typeArg ->
            typeConverter.convert(typeArg).getOrNull()
        }
        return if (typeArgs.isNotEmpty()) {
            KotlinTypeFactory.generic(mappedName, *typeArgs.toTypedArray())
        } else {
            KotlinTypeFactory.simple(mappedName)
        }
    }

    /**
     * 转换类型成员为属性
     */
    private fun convertTypeMember(
        member: TypeMember,
        interfaceName: String,
        nestedInterfaces: MutableList<KotlinDeclaration.ClassDecl>
    ): KotlinDeclaration.PropertyDecl? {
        val isTypeProperty = member.name == "type"
        val syntaxLiteral = syntaxLiteralMap[interfaceName]
        val isSyntaxProperty = member.name == "syntax" && syntaxLiteral != null
        val kotlinType = when (member.type) {
            is TypeScriptType.TypeLiteral -> {
                val hasIndexSignature = member.type.members.any { it.type is TypeScriptType.IndexSignature }
                if (hasIndexSignature) {
                    val indexSignature = member.type.members.find { it.type is TypeScriptType.IndexSignature }
                    if (indexSignature?.type is TypeScriptType.IndexSignature) {
                        val indexSig = indexSignature.type as TypeScriptType.IndexSignature
                        val keyType = typeConverter.convert(indexSig.keyType).getOrDefault(KotlinTypeFactory.string())
                        val valueType = typeConverter.convert(indexSig.valueType).getOrDefault(KotlinTypeFactory.any())
                        KotlinTypeFactory.generic("Map", keyType, valueType)
                    } else {
                        KotlinTypeFactory.generic("Map", KotlinTypeFactory.string(), KotlinTypeFactory.any())
                    }
                } else {
                    val nestedInterfaceName = ensureNestedInterface(member, interfaceName, nestedInterfaces)
                    KotlinTypeFactory.nested(interfaceName, nestedInterfaceName)
                }
            }
            is TypeScriptType.Union -> {
                // 检查联合类型中是否包含 TypeLiteral
                val hasTypeLiteral = member.type.types.any { it is TypeScriptType.TypeLiteral }
                if (hasTypeLiteral) {
                    // 替换联合类型中的 TypeLiteral 为嵌套接口引用
                    val modifiedTypes = member.type.types.map { type ->
                        if (type is TypeScriptType.TypeLiteral) {
                            // 生成嵌套接口引用
                            val typeMember = TypeMember(member.name, type, member.optional, member.readonly, member.kdoc)
                            val nestedInterfaceName = ensureNestedInterface(typeMember, interfaceName, nestedInterfaces)
                            TypeScriptType.Reference(nestedInterfaceName)
                        } else {
                            type
                        }
                    }
                    val modifiedUnion = TypeScriptType.Union(modifiedTypes)
                    typeConverter.convert(modifiedUnion).getOrDefault(KotlinTypeFactory.any())
                } else {
                    typeConverter.convert(member.type).getOrDefault(KotlinTypeFactory.any())
                }
            }
            else -> {
                typeConverter.convert(member.type).getOrDefault(KotlinTypeFactory.any())
            }
        }

        // 处理可空性
        val propertyName = wrapReservedWord(member.name)
        val modifier = if (member.readonly) PropertyModifier.Val else PropertyModifier.Var
        val shouldForceNullable = !isTypeProperty && !isSyntaxProperty
        val finalType = when {
            isTypeProperty -> KotlinType.StringType
            isSyntaxProperty -> KotlinType.StringType
            member.optional || shouldForceNullable -> kotlinType.makeNullable()
            else -> kotlinType
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
     * 去重成员
     */
    private fun deduplicateMembers(members: List<TypeMember>): List<TypeMember> {
        val memberMap = mutableMapOf<String, TypeMember>()

        for (member in members) {
            val key = member.name.replace("_", "").lowercase()
            if (!memberMap.containsKey(key)) {
                memberMap[key] = member
            } else {
                // 如果已存在，优先保留 camelCase 版本
                val existing = memberMap[key]!!
                if (isCamelCase(member.name) && !isCamelCase(existing.name)) {
                    memberMap[key] = member
                }
            }
        }

        return memberMap.values.toList()
    }

    /**
     * 检查是否为 camelCase
     */
    private fun isCamelCase(name: String): Boolean {
        return name.matches(Regex("[a-z][a-zA-Z0-9]*"))
    }

    /**
     * 确保嵌套接口存在
     */
    private fun ensureNestedInterface(
        member: TypeMember,
        parentName: String,
        nestedInterfaces: MutableList<KotlinDeclaration.ClassDecl>
    ): String {
        val nestedInterfaceName = "${parentName}${member.name.replaceFirstChar { it.uppercase() }}"
        if (nestedTypeRegistry.containsKey(nestedInterfaceName)) {
            return nestedInterfaceName
        }

        val typeLiteral = member.type as? TypeScriptType.TypeLiteral ?: return nestedInterfaceName

        val nestedMembers = typeLiteral.members.mapNotNull { nestedMember ->
            convertTypeMember(nestedMember, nestedInterfaceName, nestedInterfaces)
        }

        nestedTypeRegistry[nestedInterfaceName] = parentName
        nestedInterfaces.add(
            KotlinDeclaration.ClassDecl(
                name = nestedInterfaceName,
                modifier = ClassModifier.Interface,
                properties = nestedMembers,
                parents = emptyList(),
                typeParameters = emptyList(),
                nestedClasses = emptyList(),
                annotations = emptyList(),
                kdoc = null
            )
        )

        return nestedInterfaceName
    }

    private fun collectParentPropertyNamesForParents(
        parents: List<KotlinType>,
        interfaceName: String
    ): Set<String> {
        if (parents.isEmpty()) return emptySet()

        val parentPropertyNames = mutableSetOf<String>()

        for (parent in parents) {
            val parentTypeName = parent.toTypeString()
            Logger.debug("检查父接口 $parentTypeName 的属性", 6)
            val parentProps = collectParentPropertyNamesFromType(parentTypeName)
            if (parentProps.isEmpty()) {
                Logger.debug("  $parentTypeName 未提供可过滤属性", 8)
            } else {
                Logger.debug("  $parentTypeName 属性: $parentProps", 8)
            }
            parentPropertyNames.addAll(parentProps)
        }

        Logger.debug("  接口 $interfaceName 的父属性: $parentPropertyNames", 6)
        return parentPropertyNames
    }

    private fun applyOverrideModifiers(
        properties: List<KotlinDeclaration.PropertyDecl>,
        parentPropertyNames: Set<String>
    ): List<KotlinDeclaration.PropertyDecl> {
        if (parentPropertyNames.isEmpty()) return properties

        return properties.map { property ->
            val propertyName = property.name
            val cleanName = propertyName.removeSurrounding("`")
            val shouldOverride = parentPropertyNames.contains(propertyName) || parentPropertyNames.contains(cleanName)
            if (!shouldOverride) {
                property
            } else {
                val newModifier = when (property.modifier) {
                    is PropertyModifier.Var -> PropertyModifier.OverrideVar
                    is PropertyModifier.Val -> PropertyModifier.OverrideVal
                    is PropertyModifier.OverrideVar,
                    is PropertyModifier.OverrideVal -> property.modifier
                    else -> property.modifier
                }
                property.copy(modifier = newModifier)
            }
        }
    }

    /**
     * 过滤继承的属性
     * 避免重复声明父接口已有的属性
     */
    private fun filterInheritedProperties(
        properties: List<KotlinDeclaration.PropertyDecl>,
        parentPropertyNames: Set<String>,
        interfaceName: String
    ): List<KotlinDeclaration.PropertyDecl> {
        if (parentPropertyNames.isEmpty()) return properties

        // 过滤掉从父接口继承的属性
        val filteredProperties = properties.filter { property ->
            // 检查属性名（带反引号）和去掉反引号的版本
            val propertyNameWithoutBackticks = property.name.removeSurrounding("`")
            val isTypeProperty = propertyNameWithoutBackticks == "type"
            val shouldKeep = isTypeProperty || (
                !parentPropertyNames.contains(property.name) &&
                    !parentPropertyNames.contains(propertyNameWithoutBackticks)
                )
            if (!shouldKeep) {
                Logger.debug("  过滤掉继承的属性: ${property.name}", 8)
            }
            shouldKeep
        }

        Logger.debug("  接口 $interfaceName: 原始属性 ${properties.size} 个，过滤后 ${filteredProperties.size} 个", 6)
        if (interfaceName == "Constructor" || interfaceName == "ForStatement") {
            Logger.debug("  $interfaceName 接口的父属性: $parentPropertyNames", 8)
            Logger.debug("  $interfaceName 接口的原始属性: ${properties.map { it.name }}", 8)
            Logger.debug("  $interfaceName 接口的过滤后属性: ${filteredProperties.map { it.name }}", 8)
        }
        return filteredProperties
    }

    private fun collectParentPropertyNamesFromType(
        parentTypeName: String,
        visited: MutableSet<String> = mutableSetOf()
    ): Set<String> {
        if (!visited.add(parentTypeName)) return emptySet()
        val analyzer = inheritanceAnalyzer
        val result = mutableSetOf<String>()

        val declaration = analyzer?.getDeclaration(parentTypeName) as? TypeScriptDeclaration.InterfaceDeclaration
        if (declaration != null) {
            declaration.members.forEach { member ->
                val wrapped = wrapReservedWord(member.name)
                result.add(wrapped)
                result.add(wrapped.removeSurrounding("`"))
            }
            declaration.extends.forEach { parentRef ->
                result.addAll(collectParentPropertyNamesFromType(parentRef.name, visited))
            }
        }

        if (result.isEmpty()) {
            val fallback = fallbackParentProperties[parentTypeName].orEmpty()
            fallback.forEach { name ->
                val wrapped = wrapReservedWord(name)
                result.add(wrapped)
                result.add(wrapped.removeSurrounding("`"))
            }
        }

        return result
    }

    /**
     * 确定类修饰符
     */
    private fun determineClassModifier(tsInterface: TypeScriptDeclaration.InterfaceDeclaration): ClassModifier {
        val name = tsInterface.name

        return when {
            config.rules.classModifiers.toKotlinClass.contains(name) -> ClassModifier.FinalClass
            config.rules.classModifiers.keepInterface.contains(name) -> ClassModifier.Interface
            config.rules.classModifiers.sealedInterface.contains(name) -> ClassModifier.SealedInterface
            else -> ClassModifier.Interface
        }
    }

    /**
     * 构建注解
     */
    private fun buildAnnotations(tsInterface: TypeScriptDeclaration.InterfaceDeclaration): List<KotlinDeclaration.Annotation> {
        val annotations = mutableListOf<KotlinDeclaration.Annotation>()

        // 添加 SwcDslMarker 注解
        if (config.rules.classModifiers.sealedInterface.contains(tsInterface.name)) {
            annotations.add(KotlinDeclaration.Annotation("SwcDslMarker"))
        }

        return annotations
    }

    /**
     * 包装保留字
     */
    private fun wrapReservedWord(name: String): String {
        val reservedWords = setOf(
            "object", "inline", "in", "super", "class", "interface", "fun",
            "val", "var", "when", "is", "as", "import", "package"
        )

        return if (reservedWords.contains(name.lowercase())) {
            "`$name`"
        } else {
            name
        }
    }
}
