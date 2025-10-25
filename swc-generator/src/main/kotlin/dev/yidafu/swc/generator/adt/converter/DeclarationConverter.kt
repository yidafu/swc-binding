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
class DeclarationConverter(
    private val config: SwcGeneratorConfig,
    private val inheritanceAnalyzer: InheritanceAnalyzer? = null
) {

    private val typeConverter = TypeConverter(config)

    /**
     * 转换接口声明为 Kotlin 类声明
     */
    fun convertInterfaceDeclaration(
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
     * 转换类型别名声明为 Kotlin 类型别名声明或枚举类
     */
    fun convertTypeAliasDeclaration(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration> {
        return try {
            Logger.debug("转换类型别名声明: ${tsTypeAlias.name}", 4)

            // 检查是否为字面量联合类型
            if (isLiteralUnionType(tsTypeAlias.type)) {
                // 检查是否在 literalUnionToTypealias 配置中
                if (config.classModifiers.literalUnionToTypealias.contains(tsTypeAlias.name)) {
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
     * 转换变体
     */
    private fun convertVariance(variance: dev.yidafu.swc.generator.adt.typescript.Variance): KotlinDeclaration.Variance {
        return when (variance) {
            dev.yidafu.swc.generator.adt.typescript.Variance.INVARIANT -> KotlinDeclaration.Variance.INVARIANT
            dev.yidafu.swc.generator.adt.typescript.Variance.COVARIANT -> KotlinDeclaration.Variance.COVARIANT
            dev.yidafu.swc.generator.adt.typescript.Variance.CONTRAVARIANT -> KotlinDeclaration.Variance.CONTRAVARIANT
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
        val kotlinType = when (member.type) {
            is TypeScriptType.TypeLiteral -> {
                // 检查是否包含索引签名
                val hasIndexSignature = member.type.members.any { it.type is TypeScriptType.IndexSignature }
                if (hasIndexSignature) {
                    // 如果有索引签名，转换为 Map 类型
                    val indexSignature = member.type.members.find { it.type is TypeScriptType.IndexSignature }
                    if (indexSignature?.type is TypeScriptType.IndexSignature) {
                        val indexSig = indexSignature.type as TypeScriptType.IndexSignature
                        val keyType = typeConverter.convertToKotlinType(indexSig.keyType).getOrDefault(KotlinTypeFactory.string())
                        val valueType = typeConverter.convertToKotlinType(indexSig.valueType).getOrDefault(KotlinTypeFactory.any())
                        KotlinTypeFactory.generic("Map", keyType, valueType)
                    } else {
                        KotlinTypeFactory.generic("Map", KotlinTypeFactory.string(), KotlinTypeFactory.any())
                    }
                } else {
                    // 对于普通类型字面量，生成嵌套接口名称
                    val nestedPart = member.name.replaceFirstChar { it.uppercase() }
                    KotlinTypeFactory.nested(interfaceName, nestedPart)
                }
            }
            is TypeScriptType.Union -> {
                // 检查联合类型是否包含类型字面量
                val hasTypeLiteral = member.type.types.any { it is TypeScriptType.TypeLiteral }
                if (hasTypeLiteral) {
                    // 如果有类型字面量，生成嵌套接口名称并替换联合类型中的类型字面量
                    val nestedPart = member.name.replaceFirstChar { it.uppercase() }
                    val updatedTypes = member.type.types.map { type ->
                        if (type is TypeScriptType.TypeLiteral) {
                            // 直接创建嵌套类型，而不是通过 TypeScriptType.Reference
                            KotlinTypeFactory.nested(interfaceName, nestedPart)
                        } else {
                            typeConverter.convertToKotlinType(type).getOrDefault(KotlinType.Any)
                        }
                    }
                    // 过滤掉非 KotlinType 的元素，只保留 KotlinType
                    val kotlinTypes = updatedTypes.filterIsInstance<KotlinType>()
                    if (kotlinTypes.size == 1) {
                        kotlinTypes.first()
                    } else {
                        KotlinTypeFactory.union(*kotlinTypes.toTypedArray())
                    }
                } else {
                    typeConverter.convertToKotlinType(member.type).getOrDefault(KotlinType.Any)
                }
            }
            else -> typeConverter.convertToKotlinType(member.type)
                .getOrDefault(KotlinType.Any)
        }

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

        // 对于 toKotlinClass 中的类，属性应该有默认值 null
        val propertyDefaultValue = if (interfaceName != null && config.toKotlinClass.contains(interfaceName)) {
            Expression.NullLiteral
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
                is LiteralValue.StringLiteral -> value.value // 不添加双引号，让 Expression.StringLiteral 处理
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
        parents: List<KotlinType>,
        interfaceName: String
    ): List<KotlinDeclaration.PropertyDecl> {
        // 使用专门的属性过滤器
        return PropertyFilterer.filterInheritedProperties(properties, parents, interfaceName, inheritanceAnalyzer)
    }

    /**
     * 后备的硬编码过滤逻辑
     */
    private fun filterInheritedPropertiesFallback(
        properties: List<KotlinDeclaration.PropertyDecl>,
        parents: List<KotlinType>
    ): List<KotlinDeclaration.PropertyDecl> {
        val parentPropertyNames = mutableSetOf<String>()
        val parentNames = parents.mapNotNull { it as? KotlinType.Simple }.map { it.name }

        // 检查是否继承了 Node（直接或间接）
        if (parentNames.contains("Node") || parentNames.contains("ExpressionBase") || parentNames.contains("PatternBase")) {
            parentPropertyNames.add("type")
        }
        if (parentNames.contains("HasSpan")) {
            parentPropertyNames.add("span")
        }
        if (parentNames.contains("HasDecorator")) {
            parentPropertyNames.add("decorators")
        }
        if (parentNames.contains("HasInterpreter")) {
            parentPropertyNames.add("interpreter")
        }

        return properties.filter { prop ->
            !parentPropertyNames.contains(prop.name)
        }
    }

    /**
     * 获取接口的属性名列表
     * 从 TypeScript 声明中获取接口属性
     */
    private fun getInterfaceProperties(interfaceName: String): List<String> {
        // 这里应该从 TypeScript 声明中获取接口属性
        // 暂时使用硬编码的常见属性作为示例
        // TODO: 实现从 TypeScript 声明中动态获取接口属性
        return when (interfaceName) {
            "Node" -> listOf("type")
            "HasSpan" -> listOf("span")
            "HasDecorator" -> listOf("decorators")
            "HasInterpreter" -> listOf("interpreter")
            "ExpressionBase" -> listOf("type") // ExpressionBase 继承了 Node
            "PatternBase" -> listOf("type") // PatternBase 继承了 Node
            else -> emptyList()
        }
    }

    /**
     * 检查是否为字面量联合类型
     */
    private fun isLiteralUnionType(tsType: TypeScriptType): Boolean {
        return when (tsType) {
            is TypeScriptType.Union -> {
                tsType.types.all { it is TypeScriptType.Literal }
            }
            else -> false
        }
    }

    /**
     * 将字面量联合类型转换为枚举类
     */
    private fun convertLiteralUnionToEnumClass(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration.ClassDecl> {
        return try {
            val unionType = tsTypeAlias.type as TypeScriptType.Union
            val enumEntries = mutableListOf<KotlinDeclaration.EnumEntry>()

            unionType.types.forEach { literalType ->
                if (literalType is TypeScriptType.Literal) {
                    val entryName = when (val value = literalType.value) {
                        is LiteralValue.StringLiteral -> {
                            // 将字符串值转换为有效的枚举名称
                            sanitizeEnumEntryName(value.value)
                        }
                        is LiteralValue.NumberLiteral -> {
                            "NUMBER_${value.value.toString().replace(".", "_")}"
                        }
                        is LiteralValue.BooleanLiteral -> {
                            "BOOL_${value.value.toString().uppercase()}"
                        }
                        else -> "UNKNOWN"
                    }

                    val entry = KotlinDeclaration.EnumEntry(
                        name = entryName,
                        arguments = listOf(
                            Expression.StringLiteral(
                                when (val value = literalType.value) {
                                    is LiteralValue.StringLiteral -> value.value
                                    is LiteralValue.NumberLiteral -> value.value.toString()
                                    is LiteralValue.BooleanLiteral -> value.value.toString()
                                    else -> value.toString()
                                }
                            )
                        )
                    )
                    enumEntries.add(entry)
                }
            }

            // 为枚举类添加构造函数参数
            val constructorParam = KotlinDeclaration.PropertyDecl(
                name = "value",
                type = KotlinType.StringType,
                modifier = PropertyModifier.Val,
                defaultValue = null,
                annotations = emptyList(),
                kdoc = null
            )

            val enumClass = KotlinDeclaration.ClassDecl(
                name = wrapReservedWord(tsTypeAlias.name),
                modifier = ClassModifier.EnumClass,
                parents = emptyList(),
                properties = listOf(constructorParam),
                functions = emptyList(),
                nestedClasses = emptyList(),
                enumEntries = enumEntries,
                annotations = emptyList(),
                kdoc = tsTypeAlias.kdoc
            )

            Logger.debug("  生成枚举类: ${enumClass.name}, 条目数: ${enumEntries.size}", 6)
            GeneratorResultFactory.success(enumClass)
        } catch (e: Exception) {
            Logger.error("转换字面量联合类型为枚举类失败: ${tsTypeAlias.name}, ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.TYPE_RESOLUTION_ERROR,
                message = "Failed to convert literal union to enum class: ${tsTypeAlias.name}",
                cause = e
            )
        }
    }

    /**
     * 操作符名称映射表
     */
    private val literalNameMap = mapOf(
        "+" to "Addition",
        "+=" to "AdditionAssignment",
        "=" to "Assignment",
        "&" to "BitwiseAND",
        "&=" to "BitwiseANDAssignment",
        "~" to "BitwiseNOT",
        "|" to "BitwiseOR",
        "|=" to "BitwiseORAssignment",
        "^" to "BitwiseXOR",
        "^=" to "BitwiseXORAssignment",
        "," to "CommaOperator",
        "ternary" to "Conditional",
        "--" to "Decrement",
        "/" to "Division",
        "/=" to "DivisionAssignment",
        "==" to "Equality",
        "**" to "Exponentiation",
        "**=" to "ExponentiationAssignment",
        ">" to "GreaterThan",
        ">=" to "GreaterThanOrEqual",
        " " to "GroupingOperator",
        "++" to "Increment",
        "!=" to "Inequality",
        "<<" to "LeftShift",
        "<<=" to "LeftShiftAssignment",
        "<" to "LessThan",
        "<=" to "LessThanOrEqual",
        "&&" to "LogicalAND",
        "&&=" to "LogicalANDAssignment",
        "!" to "LogicalNOT",
        "||" to "LogicalOR",
        "||=" to "LogicalORAssignment",
        "*" to "Multiplication",
        "*=" to "MultiplicationAssignment",
        "??=" to "NullishCoalescingAssignment",
        "??" to "NullishCoalescingOperator",
        "?." to "OptionalChaining",
        "%" to "Remainder",
        "%=" to "RemainderAssignment",
        ">>" to "RightShift",
        ">>=" to "RightShiftAssignment",
        "..." to "SpreadSyntax",
        "===" to "StrictEquality",
        "!==" to "StrictInequality",
        "-" to "Subtraction",
        "-=" to "SubtractionAssignment",
        ">>>" to "UnsignedRightShift",
        ">>>=" to "UnsignedRightShiftAssignment"
    )

    /**
     * 清理枚举条目名称，转换为有效的 Kotlin 标识符
     */
    private fun sanitizeEnumEntryName(value: String): String {
        // 首先检查操作符映射表
        if (literalNameMap.containsKey(value)) {
            return literalNameMap[value]!!
        }

        // 处理 Kotlin 关键字
        val keywordMap = CodeGenerationRules.getKotlinKeywordMap()
        if (keywordMap.containsKey(value)) {
            return keywordMap[value]!!.uppercase()
        }

        // 如果只包含字母数字，转为大写
        if (value.matches(Regex("[a-zA-Z][a-zA-Z0-9]*"))) {
            return value.uppercase()
        }

        // 对于包含特殊字符的，先尝试清理特殊字符
        val cleaned = value.replace(Regex("[^a-zA-Z0-9]"), "_")
            .replace(Regex("_+"), "_")
            .trim('_')

        if (cleaned.isNotEmpty() && cleaned.matches(Regex("[a-zA-Z][a-zA-Z0-9_]*"))) {
            return cleaned.uppercase()
        }

        // 如果清理后仍然无效，生成一个基于内容的名称
        return "LITERAL_${value.hashCode().toString().replace("-", "NEG")}"
    }

    /**
     * 确定类修饰符
     */
    private fun determineClassModifier(tsInterface: TypeScriptDeclaration.InterfaceDeclaration): ClassModifier {
        return when {
            config.toKotlinClass.contains(tsInterface.name) -> ClassModifier.FinalClass
            isSealedInterface(tsInterface.name) -> ClassModifier.SealedInterface
            else -> ClassModifier.Interface
        }
    }
    
    /**
     * 检查是否是密封接口
     */
    private fun isSealedInterface(interfaceName: String): Boolean {
        val sealedInterfaces = setOf(
            "Config", "BaseModuleConfig", "Node", "HasSpan", "HasDecorator", "Class",
            "ClassPropertyBase", "ClassMethodBase", "ExpressionBase", "ParserConfig",
            "ModuleConfig", "ClassMember", "Declaration", "Expression", "JSXObject",
            "JSXExpression", "JSXElementName", "JSXAttributeOrSpread", "JSXAttributeName",
            "JSXAttrValue", "JSXElementChild", "Literal", "ModuleDeclaration", "DefaultDecl",
            "ImportSpecifier", "ModuleExportName", "ExportSpecifier", "Program", "ModuleItem",
            "Pattern", "ObjectPatternProperty", "Property", "PropertyName", "Statement",
            "TsParameterPropertyParameter", "TsEntityName", "TsTypeElement", "TsType",
            "TsFnOrConstructorType", "TsFnParameter", "TsThisTypeOrIdent", "TsTypeQueryExpr",
            "TsUnionOrIntersectionType", "TsLiteral", "TsEnumMemberId", "TsNamespaceBody",
            "TsModuleName", "TsModuleReference"
        )
        return sealedInterfaces.contains(interfaceName)
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
            isSealedInterface(tsInterface.name) -> {
                annotations.add(KotlinDeclaration.Annotation("SwcDslMarker"))
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
     * 检查是否为接口联合类型
     */
    private fun isInterfaceUnionType(tsType: dev.yidafu.swc.generator.adt.typescript.TypeScriptType): Boolean {
        return when (tsType) {
            is dev.yidafu.swc.generator.adt.typescript.TypeScriptType.Union -> {
                tsType.types.all { it is dev.yidafu.swc.generator.adt.typescript.TypeScriptType.Reference }
            }
            else -> false
        }
    }

    /**
     * 转换 TypeScript 类型参数为 Kotlin 类型参数
     */
    private fun convertTypeParameters(tsTypeParams: List<dev.yidafu.swc.generator.adt.typescript.TypeParameter>): List<KotlinDeclaration.TypeParameter> {
        // 设置类型转换器
        TypeParameterConverter.setTypeConverter(typeConverter)
        
        // 使用专门的类型参数转换器
        return TypeParameterConverter.convertTypeParameters(tsTypeParams)
    }

    /**
     * 将接口联合类型转换为密封接口
     */
    private fun convertInterfaceUnionToSealedInterface(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration.ClassDecl> {
        return try {
            val unionType = tsTypeAlias.type as dev.yidafu.swc.generator.adt.typescript.TypeScriptType.Union
            val interfaceNames = unionType.types.mapNotNull { type ->
                if (type is dev.yidafu.swc.generator.adt.typescript.TypeScriptType.Reference) {
                    type.name
                } else {
                    null
                }
            }

            val sealedInterface = KotlinDeclaration.ClassDecl(
                name = wrapReservedWord(tsTypeAlias.name),
                modifier = ClassModifier.SealedInterface,
                parents = emptyList(),
                properties = emptyList(),
                functions = emptyList(),
                nestedClasses = emptyList(),
                enumEntries = emptyList(),
                annotations = listOf(
                    KotlinDeclaration.Annotation("SwcDslMarker")
                ),
                kdoc = tsTypeAlias.kdoc
            )

            Logger.debug("  生成密封接口: ${sealedInterface.name}, 包含接口: ${interfaceNames.joinToString(", ")}", 6)
            GeneratorResultFactory.success(sealedInterface)
        } catch (e: Exception) {
            GeneratorResultFactory.failure(
                code = ErrorCode.TYPE_RESOLUTION_ERROR,
                message = "Failed to convert interface union to sealed interface: ${tsTypeAlias.name}",
                cause = e
            )
        }
    }

    /**
     * 将类型字面量转换为接口
     */
    private fun convertTypeLiteralToInterface(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration
    ): GeneratorResult<KotlinDeclaration.ClassDecl> {
        return try {
            val typeLiteral = tsTypeAlias.type as TypeScriptType.TypeLiteral
            
            // 转换类型字面量的成员为属性
            val properties = typeLiteral.members.mapNotNull { member ->
                convertTypeMember(member, tsTypeAlias.name)
            }

            val interfaceDecl = KotlinDeclaration.ClassDecl(
                name = wrapReservedWord(tsTypeAlias.name),
                modifier = ClassModifier.Interface,
                parents = emptyList(),
                properties = properties,
                functions = emptyList(),
                nestedClasses = emptyList(),
                enumEntries = emptyList(),
                annotations = listOf(
                    KotlinDeclaration.Annotation("SwcDslMarker")
                ),
                kdoc = tsTypeAlias.kdoc
            )

            Logger.debug("  生成接口: ${interfaceDecl.name}, 属性数量: ${properties.size}", 6)
            GeneratorResultFactory.success(interfaceDecl)
        } catch (e: Exception) {
            GeneratorResultFactory.failure(
                code = ErrorCode.TYPE_RESOLUTION_ERROR,
                message = "Failed to convert type literal to interface: ${tsTypeAlias.name}",
                cause = e
            )
        }
    }

    /**
     * 创建嵌套接口
     */
    private fun createNestedInterface(member: TypeMember, parentInterfaceName: String): KotlinDeclaration.ClassDecl? {
        return try {
            val typeLiteral = member.type as TypeScriptType.TypeLiteral
            val nestedInterfaceName = "${parentInterfaceName}${member.name.replaceFirstChar { it.uppercase() }}"
            val nestedShortName = member.name.replaceFirstChar { it.uppercase() }
            
            // 转换嵌套接口的成员为属性
            val properties = typeLiteral.members.mapNotNull { nestedMember ->
                convertTypeMember(nestedMember, nestedInterfaceName)
            }

            val nestedInterface = KotlinDeclaration.ClassDecl(
                name = wrapReservedWord(nestedShortName),
                modifier = ClassModifier.Interface,
                parents = emptyList(),
                properties = properties,
                functions = emptyList(),
                nestedClasses = emptyList(),
                enumEntries = emptyList(),
                annotations = emptyList(),
                kdoc = null
            )

            Logger.debug("  生成嵌套接口: ${nestedInterface.name}, 属性数量: ${properties.size}", 6)
            nestedInterface
        } catch (e: Exception) {
            Logger.warn("创建嵌套接口失败: ${member.name}, ${e.message}")
            null
        }
    }

    /**
     * 去重成员：优先保留 camelCase 版本，如果只有 snake_case 版本则保留
     */
    private fun deduplicateMembers(members: List<TypeMember>): List<TypeMember> {
        val memberMap = mutableMapOf<String, TypeMember>()
        
        for (member in members) {
            val camelCaseName = snakeToCamelCase(member.name)
            
            when {
                // 如果已经是 camelCase 版本，直接保留
                member.name == camelCaseName -> {
                    memberMap[camelCaseName] = member
                }
                // 如果是 snake_case 版本
                else -> {
                    // 如果还没有 camelCase 版本，保留这个 snake_case 版本
                    if (!memberMap.containsKey(camelCaseName)) {
                        memberMap[camelCaseName] = member
                    }
                    // 如果已经有 camelCase 版本，忽略这个 snake_case 版本
                }
            }
        }
        
        return memberMap.values.toList()
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
            config: SwcGeneratorConfig,
            inheritanceAnalyzer: InheritanceAnalyzer? = null
        ): GeneratorResult<KotlinDeclaration.ClassDecl> {
            val converter = DeclarationConverter(config, inheritanceAnalyzer)
            return converter.convertInterfaceDeclaration(tsInterface)
        }

        fun convertTypeAliasDeclaration(
            tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration,
            config: SwcGeneratorConfig,
            inheritanceAnalyzer: InheritanceAnalyzer? = null
        ): GeneratorResult<KotlinDeclaration> {
            val converter = DeclarationConverter(config, inheritanceAnalyzer)
            return converter.convertTypeAliasDeclaration(tsTypeAlias)
        }
    }
}

