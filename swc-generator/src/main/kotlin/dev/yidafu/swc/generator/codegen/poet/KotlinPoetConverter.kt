package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dev.yidafu.swc.generator.config.InterfaceHeuristicsConfig
import dev.yidafu.swc.generator.model.kotlin.*
import dev.yidafu.swc.generator.monitor.PerformanceMonitor
import dev.yidafu.swc.generator.util.CacheManager
import dev.yidafu.swc.generator.util.Logger

/**
 * ADT 到 KotlinPoet 的核心转换器
 * * 负责将 KotlinDeclaration ADT 转换为 KotlinPoet 的 TypeSpec、PropertySpec 等
 */
object KotlinPoetConverter {

    /**
     * 转换 KotlinType 为 TypeName
     * 使用统一的缓存管理器
     */
    fun convertType(kotlinType: KotlinType): TypeName {
        val typeString = kotlinType.toTypeString()

        // 使用统一缓存避免重复转换
        return CacheManager.getOrPutTypeName(typeString) {
            PerformanceMonitor.measureTime("类型转换: $typeString") {
                try {
                    kotlinType.toTypeName()
                } catch (e: Exception) {
                    Logger.warn("类型转换失败: $typeString, ${e.message}")
                    Logger.verbose("错误详情: ${e.stackTraceToString()}", 8)
                    throw e
                }
            }
        }
    }

    /**
     * 转换 KotlinDeclaration 为 TypeSpec
     */
    fun convertDeclaration(decl: KotlinDeclaration): TypeSpec {
        return convertDeclaration(decl, emptySet(), emptyMap())
    }

    /**
     * 转换 KotlinDeclaration 为 TypeSpec（带接口名称参数）
     */
    fun convertDeclaration(
        decl: KotlinDeclaration,
        interfaceNames: Set<String>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ): TypeSpec {
        return when (decl) {
            is KotlinDeclaration.ClassDecl -> convertClassDeclaration(decl, interfaceNames, declLookup)
            is KotlinDeclaration.PropertyDecl -> {
                // 属性声明不能直接转换为 TypeSpec，应该通过 convertProperty 处理
                throw IllegalArgumentException("PropertyDecl cannot be converted to TypeSpec directly")
            }
            is KotlinDeclaration.FunctionDecl -> {
                // 函数声明不能直接转换为 TypeSpec，应该通过 convertFunctionDeclaration 处理
                throw IllegalArgumentException("FunctionDecl cannot be converted to TypeSpec directly")
            }
            is KotlinDeclaration.TypeAliasDecl -> {
                // 类型别名声明不能直接转换为 TypeSpec，应该通过 convertTypeAliasDeclaration 处理
                throw IllegalArgumentException("TypeAliasDecl cannot be converted to TypeSpec directly")
            }
            is KotlinDeclaration.EnumEntry -> {
                // 枚举条目不能直接转换为 TypeSpec，应该通过枚举类处理
                throw IllegalArgumentException("EnumEntry cannot be converted to TypeSpec directly")
            }
        }
    }

    /**
     * 转换类声明为 TypeSpec
     */
    fun convertClassDeclaration(
        decl: KotlinDeclaration.ClassDecl,
        interfaceNames: Set<String> = emptySet(),
        declLookup: Map<String, KotlinDeclaration.ClassDecl> = emptyMap()
    ): TypeSpec {
        val builder = createTypeBuilder(decl.name, decl.modifier)

        // 添加修饰符
        addClassModifiers(builder, decl.modifier)

        // 添加注解
        decl.annotations.forEach { annotation ->
            convertAnnotation(annotation)?.let { builder.addAnnotation(it) }
        }

        // 不再自动添加 @SerialName 注解
        // 如果需要 @SerialName，应该在类声明中显式添加
        // val className = decl.name.removeSurrounding("`")
        // val hasSerialName = decl.annotations.any { it.name == "SerialName" }
        // val nodeDerived = decl.name == "Node" || ClassDeclarationConverter.isDerivedFromNode(decl.parents, declLookup)
        // val implementsSealedInterface = ClassDeclarationConverter.implementsSealedInterface(decl.parents, declLookup)
        //
        // if (!hasSerialName) {
        //     when (className) {
        //         "EsParserConfig" -> {
        //             builder.addAnnotation(
        //                 AnnotationSpec.builder(ClassName("kotlinx.serialization", "SerialName"))
        //                     .addMember("%S", "ecmascript")
        //                     .build()
        //             )
        //         }
        //         "TsParserConfig" -> {
        //             builder.addAnnotation(
        //                 AnnotationSpec.builder(ClassName("kotlinx.serialization", "SerialName"))
        //                     .addMember("%S", "typescript")
        //                     .build()
        //             )
        //         }
        //         else -> {
        //             if ((nodeDerived || implementsSealedInterface) && decl.modifier !is ClassModifier.Interface && decl.modifier !is ClassModifier.SealedInterface) {
        //                 builder.addAnnotation(
        //                     AnnotationSpec.builder(ClassName("kotlinx.serialization", "SerialName"))
        //                         .addMember("%S", className)
        //                         .build()
        //                 )
        //             }
        //         }
        //     }
        // }

        // 添加类型参数
        if (decl.typeParameters.isNotEmpty()) {
            decl.typeParameters.forEach { typeParam ->
                val typeVariable = TypeVariableName(typeParam.name)
                builder.addTypeVariable(typeVariable)
            }
        }

        // 添加父类型
        if (decl.parents.isNotEmpty()) {
            addParents(builder, decl.parents, decl.modifier is ClassModifier.Interface, interfaceNames)
        }

        // 添加属性或构造函数参数
        when (decl.modifier) {
            is ClassModifier.DataClass -> {
                DataClassConverter.addDataClassProperties(
                    builder,
                    decl,
                    convertType = { convertType(it) },
                    addUnionAnnotation = { owner, prop, type, paramBuilder ->
                        addUnionWithAnnotationAndCollectForParam(owner, prop, type, paramBuilder)
                    }
                )
            }
            is ClassModifier.EnumClass -> {
                EnumClassConverter.addEnumClassProperties(
                    builder,
                    decl,
                    convertType = { convertType(it) }
                )
            }
            is ClassModifier.Interface, is ClassModifier.SealedInterface -> {
                val nodeDerived = decl.name == "Node" || ClassDeclarationConverter.isDerivedFromNode(decl.parents, declLookup)
                InterfaceClassConverter.addInterfaceProperties(
                    builder,
                    decl,
                    nodeDerived,
                    convertType = { convertType(it) },
                    addUnionAnnotation = { owner, prop, type, propBuilder ->
                        addUnionWithAnnotationAndCollectForProperty(owner, prop, type, propBuilder)
                    }
                )
            }
            else -> {
                // 普通类: 基于 convertProperty，追加 @Serializable(with=...) 注解
                val nodeDerived = ClassDeclarationConverter.isDerivedFromNode(decl.parents, declLookup)
                val hasSpanDerived = ClassDeclarationConverter.isDerivedFrom(decl.parents, declLookup, "HasSpan")
                val configDerived = ClassDeclarationConverter.isDerivedFrom(decl.parents, declLookup, "ParserConfig") ||
                    ClassDeclarationConverter.isDerivedFrom(decl.parents, declLookup, "BaseParseOptions")

                RegularClassConverter.addRegularClassProperties(
                    builder, decl, nodeDerived, hasSpanDerived, configDerived, declLookup,
                    convertType = { convertType(it) },
                    convertProperty = { convertProperty(it) },
                    downgradeOverride = { prop, parents, lookup -> ClassDeclarationConverter.downgradeOverrideIfNeeded(prop, parents, lookup) },
                    addUnionAnnotation = { owner, prop, type, propBuilder ->
                        addUnionWithAnnotationAndCollectForProperty(owner, prop, type, propBuilder)
                    }
                )
            }
        }

        // 添加嵌套类
        decl.nestedClasses.forEach { nestedClass ->
            val nestedTypeSpec = convertClassDeclaration(nestedClass, interfaceNames)
            builder.addType(nestedTypeSpec)
        }

        // 添加文档
        decl.kdoc?.let { builder.addKdoc(it.cleanKdoc()) }

        return builder.build()
    }

    /**
     * 转换属性声明为 PropertySpec
     * 委托给 PropertyConverter
     */
    fun convertProperty(prop: KotlinDeclaration.PropertyDecl): PropertySpec? {
        val typeName = convertType(prop.type)
        return PropertyConverter.convertProperty(prop, typeName)
    }

    /**
     * 转换函数声明为 FunSpec
     */
    fun convertFunctionDeclaration(func: KotlinDeclaration.FunctionDecl): FunSpec {
        val builder = FunSpec.builder(func.name)

        // 添加接收者
        func.receiver?.let { receiver ->
            builder.receiver(convertType(receiver))
        }

        // 添加参数
        func.parameters.forEach { param ->
            val paramSpec = ParameterSpec.builder(
                param.name,
                convertType(param.type)
            )

            // 添加参数注解
            param.annotations.forEach { annotation ->
                convertAnnotation(annotation)?.let { paramSpec.addAnnotation(it) }
            }

            // 添加默认值
            param.defaultValue?.let { defaultValue ->
                paramSpec.defaultValue(defaultValue.toCodeString())
            }

            builder.addParameter(paramSpec.build())
        }

        // 设置返回类型
        builder.returns(convertType(func.returnType))

        // 添加修饰符
        addFunctionModifiers(builder, func.modifier)

        // 添加注解
        func.annotations.forEach { annotation ->
            convertAnnotation(annotation)?.let { builder.addAnnotation(it) }
        }

        // 添加文档
        func.kdoc?.let { builder.addKdoc(it.cleanKdoc()) }

        return builder.build()
    }

    // === Union 序列化器收集与注解 ===
    private fun addUnionWithAnnotationAndCollectForProperty(
        ownerName: String,
        propName: String,
        propType: KotlinType,
        builder: PropertySpec.Builder
    ) {
        val usage = computeUnionUsage(ownerName, propName, propType) ?: return
        // 收集 usage
        dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.addUsage(usage)
        // 添加注解 @Serializable(with = dev.yidafu.swc.generated.<Name>::class)
        val serializerName = serializerNameFor(usage)
        val serializable = AnnotationSpec.builder(ClassName("kotlinx.serialization", "Serializable"))
            .addMember("with = %T::class", ClassName("dev.yidafu.swc.generated", serializerName))
            .build()
        builder.addAnnotation(serializable)
    }

    private fun addUnionWithAnnotationAndCollectForParam(
        ownerName: String,
        propName: String,
        propType: KotlinType,
        builder: ParameterSpec.Builder
    ) {
        val usage = computeUnionUsage(ownerName, propName, propType) ?: return
        // 收集 usage
        dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.addUsage(usage)
        // 添加注解 @Serializable(with = dev.yidafu.swc.generated.<Name>::class)
        val serializerName = serializerNameFor(usage)
        val serializable = AnnotationSpec.builder(ClassName("kotlinx.serialization", "Serializable"))
            .addMember("with = %T::class", ClassName("dev.yidafu.swc.generated", serializerName))
            .build()
        builder.addAnnotation(serializable)
    }

    private fun serializerNameFor(usage: dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.UnionUsage): String {
        // 去重命名：基于 unionKind + 规范化参数token
        return dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.computeSerializerName(
            usage.unionKind,
            usage.typeArguments
        )
    }

    /**
     * 计算联合类型使用情况
     * * 注意：此方法只检测联合类型（Union.U2/U3）。
     * 如果联合类型已被替换为公共父接口（通过 TypeConverter.convertInterfaceUnion），
     * 此方法会返回 null，表示不需要联合类型序列化器。
     * 公共父接口应使用标准的多态序列化（polymorphic serialization），而不是联合类型序列化器。
     */
    private fun computeUnionUsage(
        ownerName: String,
        propName: String,
        type: KotlinType
    ): dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.UnionUsage? {
        fun unwrapNullable(t: KotlinType): Pair<KotlinType, Boolean> =
            if (t is KotlinType.Nullable) (t.innerType to true) else (t to false)
        var isArray = false
        var t = type
        if (t is KotlinType.Nullable) t = t.innerType
        if (t is KotlinType.Generic && t.name == "Array" && t.params.size == 1) {
            isArray = true
            t = t.params[0]
        }
        val (core, _) = unwrapNullable(t)
        if (core is KotlinType.Generic && core.name.startsWith("Union.U")) {
            val unionKind = core.name.removePrefix("Union.")
            val args = core.params.map { a ->
                val (inner, isNull) = unwrapNullable(a)
                val cls: com.squareup.kotlinpoet.TypeName = when (inner) {
                    is KotlinType.Simple -> {
                        val raw = inner.name.removePrefix("`").removeSuffix("`")
                        when (raw) {
                            "String" -> ClassName("kotlin", "String")
                            "Int" -> ClassName("kotlin", "Int")
                            "Double" -> ClassName("kotlin", "Double")
                            "Float" -> ClassName("kotlin", "Float")
                            "Long" -> ClassName("kotlin", "Long")
                            "Short" -> ClassName("kotlin", "Short")
                            "Byte" -> ClassName("kotlin", "Byte")
                            "Boolean" -> ClassName("kotlin", "Boolean")
                            "Any" -> ClassName("kotlin", "Any")
                            "Unit" -> ClassName("kotlin", "Unit")
                            "Nothing" -> ClassName("kotlin", "Nothing")
                            else -> ClassName("dev.yidafu.swc.generated", raw)
                        }
                    }
                    is KotlinType.Generic -> {
                        val raw = inner.name.removePrefix("`").removeSuffix("`")
                        if (raw == "Array" && inner.params.size == 1) {
                            val (elemInner, _) = unwrapNullable(inner.params[0])
                            val elemType = when (elemInner) {
                                is KotlinType.Simple -> {
                                    val eraw = elemInner.name.removePrefix("`").removeSuffix("`")
                                    when (eraw) {
                                        "String" -> ClassName("kotlin", "String")
                                        "Int" -> ClassName("kotlin", "Int")
                                        "Double" -> ClassName("kotlin", "Double")
                                        "Float" -> ClassName("kotlin", "Float")
                                        "Long" -> ClassName("kotlin", "Long")
                                        "Short" -> ClassName("kotlin", "Short")
                                        "Byte" -> ClassName("kotlin", "Byte")
                                        "Boolean" -> ClassName("kotlin", "Boolean")
                                        "Any" -> ClassName("kotlin", "Any")
                                        "Unit" -> ClassName("kotlin", "Unit")
                                        "Nothing" -> ClassName("kotlin", "Nothing")
                                        else -> ClassName("dev.yidafu.swc.generated", eraw)
                                    }
                                }
                                is KotlinType.StringType -> ClassName("kotlin", "String")
                                is KotlinType.Int -> ClassName("kotlin", "Int")
                                is KotlinType.Boolean -> ClassName("kotlin", "Boolean")
                                is KotlinType.Long -> ClassName("kotlin", "Long")
                                is KotlinType.Double -> ClassName("kotlin", "Double")
                                is KotlinType.Float -> ClassName("kotlin", "Float")
                                is KotlinType.Char -> ClassName("kotlin", "Char")
                                is KotlinType.Byte -> ClassName("kotlin", "Byte")
                                is KotlinType.Short -> ClassName("kotlin", "Short")
                                is KotlinType.Any -> ClassName("kotlin", "Any")
                                is KotlinType.Unit -> ClassName("kotlin", "Unit")
                                is KotlinType.Nothing -> ClassName("kotlin", "Nothing")
                                else -> ClassName("kotlin", "Any")
                            }
                            ClassName("kotlin", "Array").parameterizedBy(elemType)
                        } else {
                            val base = ClassName("dev.yidafu.swc.generated", raw)
                            if (inner.params.isNotEmpty()) {
                                val paramTypes = inner.params.map { p ->
                                    when (p) {
                                        is KotlinType.Nullable -> mapInnerToPoetType(p.innerType).copy(nullable = true)
                                        else -> mapInnerToPoetType(p)
                                    }
                                }
                                base.parameterizedBy(paramTypes)
                            } else {
                                base
                            }
                        }
                    }
                    is KotlinType.Nested -> {
                        val parent = inner.parent.removePrefix("`").removeSuffix("`")
                        val name = inner.name.removePrefix("`").removeSuffix("`")
                        ClassName("dev.yidafu.swc.generated", parent, name)
                    }
                    is KotlinType.StringType -> ClassName("kotlin", "String")
                    is KotlinType.Int -> ClassName("kotlin", "Int")
                    is KotlinType.Boolean -> ClassName("kotlin", "Boolean")
                    is KotlinType.Long -> ClassName("kotlin", "Long")
                    is KotlinType.Double -> ClassName("kotlin", "Double")
                    is KotlinType.Float -> ClassName("kotlin", "Float")
                    is KotlinType.Char -> ClassName("kotlin", "Char")
                    is KotlinType.Byte -> ClassName("kotlin", "Byte")
                    is KotlinType.Short -> ClassName("kotlin", "Short")
                    is KotlinType.Any -> ClassName("kotlin", "Any")
                    is KotlinType.Unit -> ClassName("kotlin", "Unit")
                    is KotlinType.Nothing -> ClassName("kotlin", "Nothing")
                    else -> ClassName("kotlin", "Any")
                }
                cls to isNull
            }
            val ownerSimple = ownerName.removePrefix("`").removeSuffix("`")
            return dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.UnionUsage(
                ownerSimpleName = ownerSimple,
                propertyName = propName,
                unionKind = unionKind,
                typeArguments = args.map { it.first },
                isArray = isArray,
                isNullableElement = args.map { it.second }
            )
        }
        return null
    }

    private fun mapInnerToPoetType(inner: KotlinType): com.squareup.kotlinpoet.TypeName {
        return when (inner) {
            is KotlinType.Simple -> {
                val raw = inner.name.removePrefix("`").removeSuffix("`")
                when (raw) {
                    "String" -> ClassName("kotlin", "String")
                    "Int" -> ClassName("kotlin", "Int")
                    "Double" -> ClassName("kotlin", "Double")
                    "Float" -> ClassName("kotlin", "Float")
                    "Long" -> ClassName("kotlin", "Long")
                    "Short" -> ClassName("kotlin", "Short")
                    "Byte" -> ClassName("kotlin", "Byte")
                    "Boolean" -> ClassName("kotlin", "Boolean")
                    "Any" -> ClassName("kotlin", "Any")
                    "Unit" -> ClassName("kotlin", "Unit")
                    "Nothing" -> ClassName("kotlin", "Nothing")
                    else -> ClassName("dev.yidafu.swc.generated", raw)
                }
            }
            is KotlinType.Generic -> {
                val raw = inner.name.removePrefix("`").removeSuffix("`")
                if (raw == "Array" && inner.params.size == 1) {
                    val elemType = mapInnerToPoetType(inner.params[0])
                    ClassName("kotlin", "Array").parameterizedBy(elemType)
                } else {
                    val base = ClassName("dev.yidafu.swc.generated", raw)
                    if (inner.params.isNotEmpty()) {
                        base.parameterizedBy(inner.params.map { mapInnerToPoetType(it) })
                    } else {
                        base
                    }
                }
            }
            is KotlinType.StringType -> ClassName("kotlin", "String")
            is KotlinType.Int -> ClassName("kotlin", "Int")
            is KotlinType.Boolean -> ClassName("kotlin", "Boolean")
            is KotlinType.Long -> ClassName("kotlin", "Long")
            is KotlinType.Double -> ClassName("kotlin", "Double")
            is KotlinType.Float -> ClassName("kotlin", "Float")
            is KotlinType.Char -> ClassName("kotlin", "Char")
            is KotlinType.Byte -> ClassName("kotlin", "Byte")
            is KotlinType.Short -> ClassName("kotlin", "Short")
            is KotlinType.Any -> ClassName("kotlin", "Any")
            is KotlinType.Unit -> ClassName("kotlin", "Unit")
            is KotlinType.Nothing -> ClassName("kotlin", "Nothing")
            else -> ClassName("kotlin", "Any")
        }
    }

    /**
     * 转换类型别名声明为 TypeAliasSpec
     */
    fun convertTypeAliasDeclaration(alias: KotlinDeclaration.TypeAliasDecl): TypeAliasSpec {
        val builder = TypeAliasSpec.builder(alias.name, convertType(alias.type))

        // 添加类型参数
        alias.typeParameters.forEach { typeParam ->
            val typeVar = when (typeParam.variance) {
                KotlinDeclaration.Variance.COVARIANT -> TypeVariableName(typeParam.name, KModifier.OUT)
                KotlinDeclaration.Variance.CONTRAVARIANT -> TypeVariableName(typeParam.name, KModifier.IN)
                else -> TypeVariableName(typeParam.name)
            }
            builder.addTypeVariable(typeVar)
        }

        // 添加注解
        alias.annotations.forEach { annotation ->
            convertAnnotation(annotation)?.let { builder.addAnnotation(it) }
        }

        // 添加文档
        alias.kdoc?.let { builder.addKdoc(it.cleanKdoc()) }

        return builder.build()
    }

    /**
     * 转换注解为 AnnotationSpec
     * 使用统一的缓存管理器
     * 委托给 AnnotationConverter
     */
    fun convertAnnotation(annotation: KotlinDeclaration.Annotation): AnnotationSpec? {
        return AnnotationConverter.convertAnnotation(annotation)
    }

    /**
     * 创建 TypeSpec.Builder
     */
    private fun createTypeBuilder(name: String, modifier: ClassModifier): TypeSpec.Builder {
        return when (modifier) {
            is ClassModifier.Interface -> TypeSpec.interfaceBuilder(name)
            is ClassModifier.SealedInterface -> TypeSpec.interfaceBuilder(name)
            is ClassModifier.Object -> TypeSpec.objectBuilder(name)
            is ClassModifier.EnumClass -> TypeSpec.enumBuilder(name)
            else -> TypeSpec.classBuilder(name)
        }
    }

    /**
     * 添加类修饰符
     */
    private fun addClassModifiers(builder: TypeSpec.Builder, modifier: ClassModifier) {
        // 所有顶级声明都应该是 public
        builder.addModifiers(KModifier.PUBLIC)

        when (modifier) {
            is ClassModifier.SealedInterface -> builder.addModifiers(KModifier.SEALED)
            is ClassModifier.SealedClass -> builder.addModifiers(KModifier.SEALED)
            is ClassModifier.OpenClass -> builder.addModifiers(KModifier.OPEN)
            is ClassModifier.AbstractClass -> builder.addModifiers(KModifier.ABSTRACT)
            is ClassModifier.DataClass -> builder.addModifiers(KModifier.DATA)
            else -> { /* 默认修饰符 */ }
        }
    }

    /**
     * 添加属性修饰符
     * 委托给 PropertyConverter
     */
    private fun addPropertyModifiers(builder: PropertySpec.Builder, modifier: PropertyModifier) {
        PropertyConverter.addPropertyModifiers(builder, modifier)
    }

    /**
     * 添加函数修饰符
     */
    private fun addFunctionModifiers(builder: FunSpec.Builder, modifier: FunctionModifier) {
        when (modifier) {
            is FunctionModifier.OverrideFun -> builder.addModifiers(KModifier.OVERRIDE)
            is FunctionModifier.AbstractFun -> builder.addModifiers(KModifier.ABSTRACT)
            is FunctionModifier.OpenFun -> builder.addModifiers(KModifier.OPEN)
            is FunctionModifier.FinalFun -> builder.addModifiers(KModifier.FINAL)
            is FunctionModifier.InfixFun -> builder.addModifiers(KModifier.INFIX)
            is FunctionModifier.OperatorFun -> builder.addModifiers(KModifier.OPERATOR)
            is FunctionModifier.SuspendFun -> builder.addModifiers(KModifier.SUSPEND)
            else -> { /* 默认修饰符 */ }
        }
    }

    /**
     * 添加父类型
     */
    private fun addParents(
        builder: TypeSpec.Builder,
        parents: List<KotlinType>,
        isInterface: Boolean,
        interfaceNames: Set<String>
    ) {
        if (parents.isEmpty()) return

        if (isInterface) {
            parents.forEach { parent ->
                builder.addSuperinterface(convertType(parent))
            }
        } else {
            // 根据父类型本身的性质决定是 superclass 还是 superinterface
            parents.forEach { parent ->
                val parentTypeName = convertType(parent)
                // 检查父类型是否是接口（通过类型名称判断）
                if (isInterfaceType(parent, interfaceNames)) {
                    builder.addSuperinterface(parentTypeName)
                } else {
                    builder.superclass(parentTypeName)
                }
            }
        }
    }

    /**
     * 判断类型是否是接口
     * 使用动态生成的接口名称集合
     */
    private val interfaceSuffixes = InterfaceHeuristicsConfig.interfaceSuffixes

    private fun isInterfaceType(kotlinType: KotlinType, interfaceNames: Set<String>): Boolean {
        return when (kotlinType) {
            is KotlinType.Simple -> {
                val name = kotlinType.name
                interfaceNames.contains(name) || interfaceSuffixes.any { name.endsWith(it) }
            }
            else -> false
        }
    }
}
