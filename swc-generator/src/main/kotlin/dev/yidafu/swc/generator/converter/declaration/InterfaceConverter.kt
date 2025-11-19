package dev.yidafu.swc.generator.converter.declaration

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.config.InterfaceRulesConfig
import dev.yidafu.swc.generator.config.PropertyRulesConfig
import dev.yidafu.swc.generator.config.SerializerConfig
import dev.yidafu.swc.generator.config.TypesImplementationRules
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
    // 移除硬编码的语法字面量映射，改为从 TS ADT 成员中推导

    private val typeConverter = TypeConverter(config, nestedTypeRegistry, inheritanceAnalyzer)
    // 移除硬编码的父属性兜底，父属性应从 TS ADT 与继承分析中推导

    /**
     * 转换接口声明为 Kotlin 类声明
     */
    fun convert(
        tsInterface: TypeScriptDeclaration.InterfaceDeclaration
    ): GeneratorResult<KotlinDeclaration.ClassDecl> {
        return try {
            // 检查是否需要跳过该接口
            if (InterfaceRulesConfig.shouldSkipInterface(tsInterface.name)) {
                val skipReason = InterfaceRulesConfig.getSkipReason(tsInterface.name) ?: "使用替代方案"
                Logger.debug("跳过 ${tsInterface.name} 接口（$skipReason）", 4)
                return GeneratorResultFactory.failure(
                    code = ErrorCode.SKIPPED_INTERFACE,
                    message = "${tsInterface.name} is skipped, $skipReason"
                )
            }

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

            // 提取 type 字段的字面量值（用于生成正确的 @SerialName）
            val typeFieldLiteralValue = extractTypeFieldLiteralValue(tsInterface.members)

            val kotlinProperties = deduplicatedMembers.mapNotNull { member ->
                convertTypeMember(member, mappedInterfaceName, nestedInterfaces)
            }

            val propertiesWithOverride = applyOverrideModifiers(kotlinProperties, parentPropertyNames)

            // 过滤掉重复声明的父接口属性
            val filteredProperties = filterInheritedProperties(propertiesWithOverride, parentPropertyNames, tsInterface.name)

            // 确定修饰符
            val modifier = determineClassModifier(tsInterface)

            // 对于 FinalClass，应用 CodeGenerationRules 处理属性
            val processedProperties = if (modifier is ClassModifier.FinalClass) {
                val rule = TypesImplementationRules.createInterfaceRule(mappedInterfaceName)
                filteredProperties.map { prop ->
                    processFinalClassProperty(prop, rule)
                }
            } else {
                // 对于接口，清除所有属性的默认值（Kotlin 接口属性不能有初始值）
                // 但保留字面量值信息用于后续生成实现类时的 @SerialName
                // 将字面量值存储在全局映射表中
                if (typeFieldLiteralValue != null) {
                    CodeGenerationRules.setTypeFieldLiteralValue(mappedInterfaceName, typeFieldLiteralValue)
                }
                filteredProperties.map { prop ->
                    val isTypeProp = prop.name.removeSurrounding("`") == "type"
                    if (isTypeProp) prop else prop.copy(defaultValue = null)
                }
            }

            // 添加注解
            val annotations = buildAnnotations(tsInterface, modifier, typeFieldLiteralValue)

            val classDecl = KotlinDeclaration.ClassDecl(
                name = wrapReservedWord(mappedInterfaceName),
                modifier = modifier,
                properties = processedProperties,
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
        val isTypeProperty = PropertyRulesConfig.isTypeProperty(member.name)
        // 从 TS ADT 中推导 syntax 的字面量（若存在）
        val syntaxLiteral: String? = if (PropertyRulesConfig.isSyntaxProperty(member.name) && member.type is TypeScriptType.Literal) {
            val v = (member.type as TypeScriptType.Literal).value
            if (v is LiteralValue.StringLiteral) v.value else null
        } else {
            null
        }
        val isSyntaxProperty = PropertyRulesConfig.isSyntaxProperty(member.name) && syntaxLiteral != null

        // 检查是否有特定接口和属性的类型覆盖（如 ForOfStatement.await）
        val cleanInterfaceName = interfaceName.removeSurrounding("`")
        val cleanPropertyName = member.name.removeSurrounding("`")
        val interfacePropertyOverride = CodeGenerationRules.getPropertyTypeOverride(cleanInterfaceName, cleanPropertyName)

        val overrideType = getSpecialPropertyType(interfaceName, member.name)
        val kotlinType = if (interfacePropertyOverride != null) {
            // 优先使用接口和属性的类型覆盖
            interfacePropertyOverride
        } else if (overrideType != null) {
            overrideType
        } else {
            when (member.type) {
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
                    // 替换 OptionalChainingCall 为 CallExpression
                    val modifiedTypes = member.type.types.map { type ->
                        when (type) {
                            is TypeScriptType.Reference -> {
                                // 将 OptionalChainingCall 替换为 CallExpression
                                if (type.name == "OptionalChainingCall") {
                                    TypeScriptType.Reference("CallExpression")
                                } else {
                                    type
                                }
                            }
                            is TypeScriptType.TypeLiteral -> {
                                // 生成嵌套接口引用
                                val typeMember = TypeMember(member.name, type, member.optional, member.readonly, member.kdoc)
                                val nestedInterfaceName = ensureNestedInterface(typeMember, interfaceName, nestedInterfaces)
                                TypeScriptType.Reference(nestedInterfaceName)
                            }
                            else -> type
                        }
                    }
                    val modifiedUnion = TypeScriptType.Union(modifiedTypes)
                    if (hasTypeLiteral) {
                        typeConverter.convert(modifiedUnion).getOrDefault(KotlinTypeFactory.any())
                    } else {
                        typeConverter.convert(modifiedUnion).getOrDefault(KotlinTypeFactory.any())
                    }
                }
                else -> {
                    typeConverter.convert(member.type).getOrDefault(KotlinTypeFactory.any())
                }
            }
        }

        // 处理可空性
        val propertyName = wrapReservedWord(member.name)
        // cleanPropertyName 和 cleanInterfaceName 已在上面声明，这里不再重复声明
        val isSpanCoordinate = PropertyRulesConfig.isSpanCoordinateProperty(cleanInterfaceName, cleanPropertyName)
        val modifier = if (member.readonly) PropertyModifier.Val else PropertyModifier.Var
        val isSpanProperty = isRequiredSpanProperty(cleanPropertyName, kotlinType)

        // interfacePropertyOverride 已在上面计算，这里使用已计算的值
        val baseKotlinType = interfacePropertyOverride ?: kotlinType

        val shouldForceNullable = !isTypeProperty && !isSyntaxProperty && !isSpanCoordinate
        val finalType = when {
            isTypeProperty -> KotlinType.StringType
            isSyntaxProperty -> KotlinType.StringType
            isSpanProperty -> baseKotlinType
            member.optional || shouldForceNullable -> baseKotlinType.makeNullable()
            else -> baseKotlinType
        }

        // 如果是 type 属性且类型是字面量，将字面量值存储在默认值中
        // 注意：对于接口，默认值会在后续被清除，但字面量值会存储在全局映射表中
        val defaultValue = when {
            isTypeProperty && member.type is TypeScriptType.Literal -> {
                when (val literalValue = (member.type as TypeScriptType.Literal).value) {
                    is LiteralValue.StringLiteral -> Expression.StringLiteral(literalValue.value)
                    else -> null
                }
            }
            isSyntaxProperty && syntaxLiteral != null -> {
                // 接口阶段会在后续清理默认值，但这里保留以利于实现类 SerialName 计算链路一致
                Expression.StringLiteral(syntaxLiteral)
            }
            else -> null
        }

        return KotlinDeclaration.PropertyDecl(
            name = propertyName,
            type = finalType,
            modifier = modifier,
            defaultValue = defaultValue,
            annotations = emptyList(),
            kdoc = member.kdoc
        )
    }

    private fun getSpecialPropertyType(
        interfaceName: String,
        propertyName: String
    ): KotlinType? {
        val cleanInterfaceName = interfaceName.removeSurrounding("`")
        return when {
            PropertyRulesConfig.isSpanCoordinateProperty(cleanInterfaceName, propertyName) -> KotlinTypeFactory.int()
            else -> null
        }
    }

    // isSpanCoordinateProperty 已移动至 PropertyRulesConfig

    private fun isRequiredSpanProperty(
        propertyName: String,
        kotlinType: KotlinType
    ): Boolean {
        if (propertyName != "span") return false
        return when (kotlinType) {
            is KotlinType.Simple -> kotlinType.name.removeSurrounding("`") == "Span"
            else -> false
        }
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
                // Node 接口的 type 属性在生成 Kotlin 代码时被跳过了，所以不应该收集它
                if (parentTypeName == "Node" && member.name == "type") {
                    return@forEach
                }
                val wrapped = wrapReservedWord(member.name)
                result.add(wrapped)
                result.add(wrapped.removeSurrounding("`"))
            }
            declaration.extends.forEach { parentRef ->
                result.addAll(collectParentPropertyNamesFromType(parentRef.name, visited))
            }
        }

        if (result.isEmpty()) {
            // 不再使用硬编码兜底；如果为空，说明父接口无可过滤的公开属性
        }

        return result
    }

    /**
     * 确定类修饰符
     */
    private fun determineClassModifier(tsInterface: TypeScriptDeclaration.InterfaceDeclaration): ClassModifier {
        val name = tsInterface.name
        val mappedName = CodeGenerationRules.mapTypeName(name)
        // 配置优先级：toKotlinClass > keepInterface > sealedInterface > interfaceToImplMap
        if (config.rules.classModifiers.toKotlinClass.contains(name)) {
            Logger.debug("  接口 $name 配置为 FinalClass (toKotlinClass)", 6)
            return ClassModifier.FinalClass
        }
        if (config.rules.classModifiers.keepInterface.contains(name)) {
            Logger.debug("  接口 $name 配置为 Interface (keepInterface)", 6)
            return ClassModifier.Interface
        }
        if (config.rules.classModifiers.sealedInterface.contains(name)) {
            Logger.debug("  接口 $name 配置为 SealedInterface (sealedInterface)", 6)
            return ClassModifier.SealedInterface
        }
        // 对于 interfaceToImplMap 中的接口，强制生成为 Interface，以便生成对应的 Impl 类
        // 注意：interfaceToImplMap 中的键是 Kotlin 名称，需要先映射
        if (SerializerConfig.interfaceToImplMap.containsKey(mappedName)) {
            Logger.debug("  接口 $name (映射为 $mappedName) 配置为 Interface (interfaceToImplMap)", 6)
            return ClassModifier.Interface
        }

        // 默认策略：当提供继承分析器时，将"无子类型"的叶子接口作为类生成
        // 未提供分析器时保持为接口，以维持转换阶段与测试的既有预期
        val analyzer = inheritanceAnalyzer
        if (analyzer == null) {
            Logger.debug("  接口 $name 默认保持为 Interface (无分析器)", 6)
            return ClassModifier.Interface
        }
        val hasChildren = analyzer.hasChildren(name)
        val result = if (!hasChildren) ClassModifier.FinalClass else ClassModifier.Interface
        Logger.debug("  接口 $name 默认策略: ${if (hasChildren) "Interface (有子类型)" else "FinalClass (无子类型)"}", 6)
        return result
    }

    /**
     * 构建注解
     */
    /**
     * 提取 type 字段的字面量值
     */
    private fun extractTypeFieldLiteralValue(members: List<TypeMember>): String? {
        val typeMember = members.find { it.name == "type" } ?: return null
        return when (val type = typeMember.type) {
            is TypeScriptType.Literal -> {
                when (val value = type.value) {
                    is LiteralValue.StringLiteral -> value.value
                    else -> null
                }
            }
            else -> null
        }
    }

    private fun buildAnnotations(
        tsInterface: TypeScriptDeclaration.InterfaceDeclaration,
        modifier: ClassModifier,
        typeFieldLiteralValue: String? = null
    ): List<KotlinDeclaration.Annotation> {
        val annotations = mutableListOf<KotlinDeclaration.Annotation>()

        // 为密封接口和最终类添加 @Serializable
        // 注意：普通接口（即使是在 additionalOpenBases 中的）不能添加 @Serializable 注解
        // 因为 Kotlinx Serialization 要求：非 sealed 接口默认支持多态序列化，不能使用不带参数的 @Serializable
        val interfaceName = CodeGenerationRules.mapTypeName(tsInterface.name)
        val isInAdditionalOpenBases = SerializerConfig.additionalOpenBases.contains(interfaceName)

        when (modifier) {
            is ClassModifier.FinalClass,
            is ClassModifier.SealedInterface -> annotations.add(KotlinDeclaration.Annotation("Serializable"))
            is ClassModifier.Interface -> {
                // 普通接口不添加 @Serializable 注解
                // 它们仍然会在 polymorphic 块中注册（通过 additionalOpenBases），但不需要 @Serializable 注解
            }
            else -> {}
        }

        // 为"参与多态"的基类接口添加必要 discriminator（仅在根接口处，避免层级冲突）
        if (modifier is ClassModifier.Interface || modifier is ClassModifier.SealedInterface) {
            val mappedName = CodeGenerationRules.mapTypeName(tsInterface.name)
            // 仅在固定的根接口上打上 discriminator，防止层级内冲突
            val rootDiscriminator: String? = when (mappedName) {
                // AST 根
                "Node" -> SerializerConfig.DEFAULT_DISCRIMINATOR
                // Config 体系根
                "Config", "ParserConfig", "Options" -> SerializerConfig.SYNTAX_DISCRIMINATOR
                else -> null
            }
            // 如果接口在 additionalOpenBases 中，也需要添加 @JsonClassDiscriminator（使用默认的 "type"）
            val discriminator = rootDiscriminator ?: if (isInAdditionalOpenBases) {
                SerializerConfig.DEFAULT_DISCRIMINATOR
            } else {
                null
            }
            if (discriminator != null) {
                annotations.add(
                    KotlinDeclaration.Annotation(
                        "JsonClassDiscriminator",
                        listOf(Expression.StringLiteral(discriminator))
                    )
                )
            }
        }

        // 添加 SwcDslMarker 注解
        if (config.rules.classModifiers.sealedInterface.contains(tsInterface.name)) {
            annotations.add(KotlinDeclaration.Annotation("SwcDslMarker"))
        }

        return annotations
    }

    /**
     * 处理 FinalClass 的属性，应用 CodeGenerationRules
     */
    private fun processFinalClassProperty(
        prop: KotlinDeclaration.PropertyDecl,
        rule: TypesImplementationRules.InterfaceRule
    ): KotlinDeclaration.PropertyDecl {
        val normalizedName = prop.name.removeSurrounding("`")
        val isTypeProperty = PropertyRulesConfig.isTypeProperty(normalizedName)
        val isSyntaxProperty = PropertyRulesConfig.isSyntaxProperty(normalizedName) && rule.syntaxLiteral != null

        val isSpanCoordinateProperty = PropertyRulesConfig.isSpanCoordinateProperty(rule.interfaceCleanName, normalizedName)
        val isSpanProperty = PropertyRulesConfig.isSpanProperty(normalizedName)

        val updatedType = when {
            isTypeProperty -> KotlinType.StringType
            isSyntaxProperty -> KotlinType.StringType
            isSpanProperty -> KotlinType.Simple("Span")
            isSpanCoordinateProperty -> KotlinType.Int
            prop.type is KotlinType.Nullable -> prop.type
            else -> prop.type
        }

        val defaultValue = when {
            // 如果是 type 属性，优先使用从 TypeScript 提取的字面量值，否则使用接口名称
            isTypeProperty -> prop.defaultValue ?: Expression.StringLiteral(rule.interfaceCleanName)
            isSyntaxProperty -> Expression.StringLiteral(rule.syntaxLiteral!!)
            // 对于 span 属性，使用 emptySpan() 函数调用，确保包含 ctxt 字段
            isSpanProperty -> Expression.FunctionCall("emptySpan")
            isSpanCoordinateProperty -> Expression.NumberLiteral("0")
            updatedType is KotlinType.Nullable -> Expression.NullLiteral
            else -> prop.defaultValue
        }

        val annotations = buildList {
            addAll(prop.annotations)
            if (isSpanProperty || isSpanCoordinateProperty) {
                add(KotlinDeclaration.Annotation("EncodeDefault"))
            }
        }

        return prop.copy(
            type = updatedType,
            defaultValue = defaultValue,
            annotations = annotations
        )
    }

    /**
     * 包装保留字
     */
    private fun wrapReservedWord(name: String): String {
        return PropertyRulesConfig.wrapReservedWord(name)
    }
}
