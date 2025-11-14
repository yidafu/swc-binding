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
 * 接口转换器
 * 负责将 TypeScript 接口声明转换为 Kotlin 类声明
 */
class InterfaceConverter(
    private val config: Configuration,
    private val inheritanceAnalyzer: InheritanceAnalyzer? = null
) {
    
    private val typeConverter = TypeConverter(config)
    
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
            }

            // 转换成员为属性（只转换接口自己的成员，不包括继承的）
            // 去重：优先保留 camelCase 版本，如果只有 snake_case 版本则转换
            val deduplicatedMembers = deduplicateMembers(tsInterface.members)
            val kotlinProperties = deduplicatedMembers.mapNotNull { member ->
                convertTypeMember(member, tsInterface.name)
            }

            // 收集嵌套接口（从 TypeLiteral 成员和包含 TypeLiteral 的联合类型）
            val nestedInterfaces = tsInterface.members.mapNotNull { member ->
                when {
                    member.type is TypeScriptType.TypeLiteral -> {
                        createNestedInterface(member, tsInterface.name)
                    }
                    member.type is TypeScriptType.Union && member.type.types.any { it is TypeScriptType.TypeLiteral } -> {
                        // 从联合类型中提取类型字面量创建嵌套接口
                        val typeLiteral = member.type.types.find { it is TypeScriptType.TypeLiteral } as? TypeScriptType.TypeLiteral
                        if (typeLiteral != null) {
                            val typeMember = TypeMember(member.name, typeLiteral, member.optional, member.readonly, member.kdoc)
                            createNestedInterface(typeMember, tsInterface.name)
                        } else {
                            null
                        }
                    }
                    else -> null
                }
            }

            // 过滤掉重复声明的父接口属性
            val filteredProperties = filterInheritedProperties(kotlinProperties, kotlinParents, tsInterface.name)

            // 确定修饰符
            val modifier = determineClassModifier(tsInterface)

            // 添加注解
            val annotations = buildAnnotations(tsInterface)

            val classDecl = KotlinDeclaration.ClassDecl(
                name = wrapReservedWord(tsInterface.name),
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
        val typeArgs = typeRef.typeArguments.mapNotNull { typeArg ->
            typeConverter.convert(typeArg).getOrNull()
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
        val kotlinType = when (member.type) {
            is TypeScriptType.TypeLiteral -> {
                // 检查是否包含索引签名
                val hasIndexSignature = member.type.members.any { it.type is TypeScriptType.IndexSignature }
                if (hasIndexSignature) {
                    // 如果有索引签名，转换为 Map 类型
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
                    // 普通类型字面量，生成嵌套接口引用
                    // 使用驼峰命名：JsFormatOptions + Comments = JsFormatOptionsComments
                    val nestedInterfaceName = "${interfaceName}${member.name.replaceFirstChar { it.uppercase() }}"
                    KotlinTypeFactory.simple(nestedInterfaceName)
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
                            val nestedInterfaceName = "${interfaceName}${member.name.replaceFirstChar { it.uppercase() }}"
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
     * 去重成员
     */
    private fun deduplicateMembers(members: List<TypeMember>): List<TypeMember> {
        val memberMap = mutableMapOf<String, TypeMember>()
        
        for (member in members) {
            val key = member.name.lowercase()
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
     * 创建嵌套接口
     */
    private fun createNestedInterface(member: TypeMember, parentName: String): KotlinDeclaration.ClassDecl? {
        if (member.type !is TypeScriptType.TypeLiteral) return null
        
        val typeLiteral = member.type as TypeScriptType.TypeLiteral
        val nestedMembers = typeLiteral.members.mapNotNull { nestedMember ->
            convertTypeMember(nestedMember, "${parentName}${member.name.capitalize()}")
        }
        
        // 使用驼峰命名：JsFormatOptions + Comments = JsFormatOptionsComments
        val nestedInterfaceName = "${parentName}${member.name.capitalize()}"
        
        return KotlinDeclaration.ClassDecl(
            name = nestedInterfaceName,
            modifier = ClassModifier.Interface,
            properties = nestedMembers,
            parents = emptyList(),
            typeParameters = emptyList(),
            nestedClasses = emptyList(),
            annotations = emptyList(),
            kdoc = null
        )
    }
    
    /**
     * 过滤继承的属性
     * 避免重复声明父接口已有的属性
     */
    private fun filterInheritedProperties(
        properties: List<KotlinDeclaration.PropertyDecl>,
        parents: List<KotlinType>,
        interfaceName: String
    ): List<KotlinDeclaration.PropertyDecl> {
        if (parents.isEmpty()) return properties
        
        // 获取所有父类型的属性名
        val parentPropertyNames = mutableSetOf<String>()
        
        for (parent in parents) {
            val parentTypeName = parent.toTypeString()
            Logger.debug("检查父接口 $parentTypeName 的属性", 6)
            
            // 根据父接口名称添加已知的属性
            when (parentTypeName) {
                "Node" -> {
                    parentPropertyNames.add("type")
                    Logger.debug("  Node 接口包含属性: type", 8)
                }
                "HasSpan" -> {
                    parentPropertyNames.add("span")
                    Logger.debug("  HasSpan 接口包含属性: span", 8)
                }
                "HasDecorator" -> {
                    parentPropertyNames.add("decorators")
                    Logger.debug("  HasDecorator 接口包含属性: decorators", 8)
                }
                "ClassMember" -> {
                    // ClassMember 是标记接口，没有属性
                    Logger.debug("  ClassMember 是标记接口，无属性", 8)
                }
                "Expression" -> {
                    // Expression 是标记接口，没有属性
                    Logger.debug("  Expression 是标记接口，无属性", 8)
                }
                "Statement" -> {
                    // Statement 是标记接口，没有属性
                    Logger.debug("  Statement 是标记接口，无属性", 8)
                }
                "Declaration" -> {
                    // Declaration 是标记接口，没有属性
                    Logger.debug("  Declaration 是标记接口，无属性", 8)
                }
                "Pattern" -> {
                    // Pattern 是标记接口，没有属性
                    Logger.debug("  Pattern 是标记接口，无属性", 8)
                }
                "Property" -> {
                    // Property 是标记接口，没有属性
                    Logger.debug("  Property 是标记接口，无属性", 8)
                }
                "PropertyName" -> {
                    // PropertyName 是标记接口，没有属性
                    Logger.debug("  PropertyName 是标记接口，无属性", 8)
                }
                "JSXObject" -> {
                    // JSXObject 是标记接口，没有属性
                    Logger.debug("  JSXObject 是标记接口，无属性", 8)
                }
                "JSXElementName" -> {
                    // JSXElementName 是标记接口，没有属性
                    Logger.debug("  JSXElementName 是标记接口，无属性", 8)
                }
                "JSXAttributeName" -> {
                    // JSXAttributeName 是标记接口，没有属性
                    Logger.debug("  JSXAttributeName 是标记接口，无属性", 8)
                }
                "JSXExpression" -> {
                    // JSXExpression 是标记接口，没有属性
                    Logger.debug("  JSXExpression 是标记接口，无属性", 8)
                }
                "JSXAttrValue" -> {
                    // JSXAttrValue 是标记接口，没有属性
                    Logger.debug("  JSXAttrValue 是标记接口，无属性", 8)
                }
                "JSXElementChild" -> {
                    // JSXElementChild 是标记接口，没有属性
                    Logger.debug("  JSXElementChild 是标记接口，无属性", 8)
                }
                "JSXAttributeOrSpread" -> {
                    // JSXAttributeOrSpread 是标记接口，没有属性
                    Logger.debug("  JSXAttributeOrSpread 是标记接口，无属性", 8)
                }
                "ModuleExportName" -> {
                    // ModuleExportName 是标记接口，没有属性
                    Logger.debug("  ModuleExportName 是标记接口，无属性", 8)
                }
                "TsEntityName" -> {
                    // TsEntityName 是标记接口，没有属性
                    Logger.debug("  TsEntityName 是标记接口，无属性", 8)
                }
                "TsThisTypeOrIdent" -> {
                    // TsThisTypeOrIdent 是标记接口，没有属性
                    Logger.debug("  TsThisTypeOrIdent 是标记接口，无属性", 8)
                }
                "TsEnumMemberId" -> {
                    // TsEnumMemberId 是标记接口，没有属性
                    Logger.debug("  TsEnumMemberId 是标记接口，无属性", 8)
                }
                "TsModuleName" -> {
                    // TsModuleName 是标记接口，没有属性
                    Logger.debug("  TsModuleName 是标记接口，无属性", 8)
                }
                "TsLiteral" -> {
                    // TsLiteral 是标记接口，没有属性
                    Logger.debug("  TsLiteral 是标记接口，无属性", 8)
                }
                "TsTypeQueryExpr" -> {
                    // TsTypeQueryExpr 是标记接口，没有属性
                    Logger.debug("  TsTypeQueryExpr 是标记接口，无属性", 8)
                }
                "TsModuleReference" -> {
                    // TsModuleReference 是标记接口，没有属性
                    Logger.debug("  TsModuleReference 是标记接口，无属性", 8)
                }
                "TsNamespaceBody" -> {
                    // TsNamespaceBody 是标记接口，没有属性
                    Logger.debug("  TsNamespaceBody 是标记接口，无属性", 8)
                }
                "TsTypeElement" -> {
                    // TsTypeElement 是标记接口，没有属性
                    Logger.debug("  TsTypeElement 是标记接口，无属性", 8)
                }
                "TsFnParameter" -> {
                    // TsFnParameter 是标记接口，没有属性
                    Logger.debug("  TsFnParameter 是标记接口，无属性", 8)
                }
                "TsParameterPropertyParameter" -> {
                    // TsParameterPropertyParameter 是标记接口，没有属性
                    Logger.debug("  TsParameterPropertyParameter 是标记接口，无属性", 8)
                }
                "ObjectPatternProperty" -> {
                    // ObjectPatternProperty 是标记接口，没有属性
                    Logger.debug("  ObjectPatternProperty 是标记接口，无属性", 8)
                }
                "ImportSpecifier" -> {
                    // ImportSpecifier 是标记接口，没有属性
                    Logger.debug("  ImportSpecifier 是标记接口，无属性", 8)
                }
                "ExportSpecifier" -> {
                    // ExportSpecifier 是标记接口，没有属性
                    Logger.debug("  ExportSpecifier 是标记接口，无属性", 8)
                }
                "DefaultDecl" -> {
                    // DefaultDecl 是标记接口，没有属性
                    Logger.debug("  DefaultDecl 是标记接口，无属性", 8)
                }
                "ModuleItem" -> {
                    // ModuleItem 是标记接口，没有属性
                    Logger.debug("  ModuleItem 是标记接口，无属性", 8)
                }
                "SealedInterface" -> {
                    // SealedInterface 是标记接口，没有属性
                    Logger.debug("  SealedInterface 是标记接口，无属性", 8)
                }
                else -> {
                    Logger.debug("  未知父接口 $parentTypeName，跳过属性过滤", 8)
                }
            }
        }
        
        // 过滤掉从父接口继承的属性
        val filteredProperties = properties.filter { property ->
            // 检查属性名（带反引号）和去掉反引号的版本
            val propertyNameWithoutBackticks = property.name.removeSurrounding("`")
            val shouldKeep = !parentPropertyNames.contains(property.name) && 
                           !parentPropertyNames.contains(propertyNameWithoutBackticks)
            if (!shouldKeep) {
                Logger.debug("  过滤掉继承的属性: ${property.name}", 8)
            }
            shouldKeep
        }
        
        Logger.debug("  接口 $interfaceName: 原始属性 ${properties.size} 个，过滤后 ${filteredProperties.size} 个", 6)
        if (interfaceName == "Constructor" || interfaceName == "ForStatement") {
            Logger.debug("  $interfaceName 接口的父类型: ${parents.map { it.toTypeString() }}", 8)
            Logger.debug("  $interfaceName 接口的父属性: $parentPropertyNames", 8)
            Logger.debug("  $interfaceName 接口的原始属性: ${properties.map { it.name }}", 8)
            Logger.debug("  $interfaceName 接口的过滤后属性: ${filteredProperties.map { it.name }}", 8)
        }
        return filteredProperties
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
            "val", "var", "when", "is", "as", "type", "import", "package"
        )
        
        return if (reservedWords.contains(name.lowercase())) {
            "`$name`"
        } else {
            name
        }
    }
}
