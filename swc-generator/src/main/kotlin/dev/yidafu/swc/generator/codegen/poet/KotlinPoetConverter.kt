package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dev.yidafu.swc.generator.model.kotlin.*
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.util.CacheManager
import dev.yidafu.swc.generator.monitor.PerformanceMonitor
import dev.yidafu.swc.generator.config.Hardcoded

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

        // 为 ParserConfig 子类添加 @SerialName 注解（用于与 Rust 端兼容）
        // 同时为所有 Node 派生类（实现了密封接口的类）添加 @SerialName，使用类名作为序列化名称
        val className = decl.name.removeSurrounding("`")
        val hasSerialName = decl.annotations.any { it.name == "SerialName" }
        val nodeDerived = decl.name == "Node" || isDerivedFromNode(decl.parents, declLookup)
        // 检查是否实现了任何密封接口（如 ModuleItem, Statement, Declaration 等）
        val implementsSealedInterface = implementsSealedInterface(decl.parents, declLookup)
        
        if (!hasSerialName) {
            when (className) {
                "EsParserConfig" -> {
                    builder.addAnnotation(
                        AnnotationSpec.builder(ClassName("kotlinx.serialization", "SerialName"))
                            .addMember("%S", "ecmascript")
                            .build()
                    )
                }
                "TsParserConfig" -> {
                    builder.addAnnotation(
                        AnnotationSpec.builder(ClassName("kotlinx.serialization", "SerialName"))
                            .addMember("%S", "typescript")
                            .build()
                    )
                }
                else -> {
                    // 对于所有 Node 派生类或实现了密封接口的类，如果没有 @SerialName，则使用类名作为序列化名称
                    // 这样可以确保多态序列化时，JSON 中的 type 字段值与序列化名称匹配
                    if ((nodeDerived || implementsSealedInterface) && decl.modifier !is ClassModifier.Interface && decl.modifier !is ClassModifier.SealedInterface) {
                        builder.addAnnotation(
                            AnnotationSpec.builder(ClassName("kotlinx.serialization", "SerialName"))
                                .addMember("%S", className)
                                .build()
                        )
                    }
                }
            }
        }

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
                // Data class: 属性作为构造函数参数
                val constructorParams = decl.properties.map { prop ->
                    val typeName = convertType(prop.type)
                    val paramBuilder = ParameterSpec.builder(prop.name, typeName)

                    // 添加注解
                    prop.annotations.forEach { annotation ->
                        convertAnnotation(annotation)?.let { paramBuilder.addAnnotation(it) }
                    }

                    // 添加默认值
                    prop.defaultValue?.let { defaultValue ->
                        val defaultValueStr = defaultValue.toCodeString()
                        if (defaultValueStr.isNotBlank()) {
                            paramBuilder.defaultValue(defaultValueStr)
                        }
                    }

                    // 若为 Union.Ux 或 Array<Union.Ux>，添加 @Serializable(with=...) 注解并收集
                    addUnionWithAnnotationAndCollectForParam(decl.name, prop.name, prop.type, paramBuilder)

                    paramBuilder.build()
                }
                builder.primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addParameters(constructorParams)
                        .build()
                )
            }
            is ClassModifier.EnumClass -> {
                // 为枚举类添加构造函数参数
                if (decl.properties.isNotEmpty()) {
                    val constructorParams = decl.properties.map { prop ->
                        val typeName = convertType(prop.type)
                        ParameterSpec.builder(prop.name, typeName).build()
                    }

                    builder.primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameters(constructorParams)
                            .build()
                    )
                }

                // 添加枚举条目
                decl.enumEntries.forEach { entry ->
                    val hasArgs = entry.arguments.isNotEmpty()
                    val hasAnnotations = entry.annotations.isNotEmpty()
                    if (hasArgs || hasAnnotations) {
                        val enumBuilder = TypeSpec.anonymousClassBuilder()

                        entry.arguments.forEach { arg ->
                            enumBuilder.addSuperclassConstructorParameter("%L", arg.toCodeString())
                        }

                        entry.annotations.forEach { annotation ->
                            convertAnnotation(annotation)?.let { enumBuilder.addAnnotation(it) }
                        }

                        builder.addEnumConstant(entry.name, enumBuilder.build())
                    } else {
                        builder.addEnumConstant(entry.name)
                    }
                }
            }
            is ClassModifier.Interface, is ClassModifier.SealedInterface -> {
                // 接口：属性不得包含初始化器，生成抽象属性；为 Union.Ux 属性添加 @Serializable(with=...)
                val nodeDerived = decl.name == "Node" || isDerivedFromNode(decl.parents, declLookup)
                decl.properties.forEach { prop ->
                    // 仅 Node 系谱接口保留 `type` 抽象属性；其余接口不生成该字段
                    if (prop.name == "type" && !nodeDerived) {
                        return@forEach
                    }
                    val typeName = convertType(prop.type)
                    val propBuilder = PropertySpec.builder(prop.name, typeName)
                    // 接口属性修饰符（保持 val/var），针对 Node 系谱上的 `type` 强制添加 override（除 Node 本身）
                    addPropertyModifiers(propBuilder, prop.modifier)
                    if (prop.name == "type" && nodeDerived && decl.name != "Node") {
                        propBuilder.addModifiers(KModifier.OVERRIDE)
                    }
                    // 透传原始注解
                    prop.annotations.forEach { annotation ->
                        convertAnnotation(annotation)?.let { propBuilder.addAnnotation(it) }
                    }
                    // Node 接口的 type 属性需要添加 @Transient，因为它与 JsonClassDiscriminator("type") 冲突
                    if (prop.name == "type" && decl.name == "Node") {
                        propBuilder.addAnnotation(
                            AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
                        )
                    }
                    // 为 Union.Ux 或 Array<Union.Ux> 添加专属 with 注解并收集
                    addUnionWithAnnotationAndCollectForProperty(decl.name, prop.name, prop.type, propBuilder)
                    // 文档
                    prop.kdoc?.let { propBuilder.addKdoc(it.cleanKdoc()) }
                    builder.addProperty(propBuilder.build())
                }
            }
            else -> {
                // 普通类: 基于 convertProperty，追加 @Serializable(with=...) 注解
                val nodeDerived = isDerivedFromNode(decl.parents, declLookup)
                val hasSpanDerived = isDerivedFrom(decl.parents, declLookup, "HasSpan")
                val configDerived = isDerivedFrom(decl.parents, declLookup, "ParserConfig") ||
                    isDerivedFrom(decl.parents, declLookup, "BaseParseOptions")
                // 多态类添加 JsonClassDiscriminator
                if (nodeDerived) {
                    // 需要实验 API opt-in
                    builder.addAnnotation(
                        AnnotationSpec.builder(ClassName("kotlinx.serialization", "ExperimentalSerializationApi")).build()
                    )
                    builder.addAnnotation(
                        AnnotationSpec.builder(ClassName("kotlinx.serialization.json", "JsonClassDiscriminator"))
                            .addMember("%S", "type")
                            .build()
                    )
                } else if (configDerived) {
                    // 需要实验 API opt-in
                    builder.addAnnotation(
                        AnnotationSpec.builder(ClassName("kotlinx.serialization", "ExperimentalSerializationApi")).build()
                    )
                    builder.addAnnotation(
                        AnnotationSpec.builder(ClassName("kotlinx.serialization.json", "JsonClassDiscriminator"))
                            .addMember("%S", "syntax")
                            .build()
                    )
                }
                val existingPropNames = mutableSetOf<String>()
                decl.properties.forEach { prop ->
                    existingPropNames.add(prop.name)
                    // 非 Node 系谱实现类不应包含 `type`
                    if (prop.name == "type" && !nodeDerived) {
                        return@forEach
                    }
                    val adjusted = downgradeOverrideIfNeeded(prop, decl.parents, declLookup)
                    val base = convertProperty(adjusted)
                    if (base != null) {
                        val propBuilder = base.toBuilder()
                        // Node 系谱的 type 属性需要添加 @Transient，因为它与 JsonClassDiscriminator("type") 冲突
                        // 避免与 "syntax" 判别关键字冲突：对 ParserConfig 系谱中的 `syntax` 字段标注 @Transient
                        if (adjusted.name == "type" && nodeDerived) {
                            propBuilder.addAnnotation(
                                AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
                            )
                        } else if (adjusted.name == "syntax" && configDerived) {
                            propBuilder.addAnnotation(
                                AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
                            )
                        }
                        addUnionWithAnnotationAndCollectForProperty(decl.name, adjusted.name, adjusted.type, propBuilder)
                        builder.addProperty(propBuilder.build())
                    }
                }
                // 若实现了 HasSpan 但未声明 span 属性，则补充 override span: Span = emptySpan()
                if (hasSpanDerived && decl.properties.none { it.name == "span" }) {
                    val spanType = ClassName("dev.yidafu.swc.generated", "Span")
                    val memberEmptySpan = MemberName("dev.yidafu.swc", "emptySpan")
                    val spanProp = PropertySpec.builder("span", spanType)
                        .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)
                        .mutable(true)
                        .initializer("%M()", memberEmptySpan)
                        .build()
                    builder.addProperty(spanProp)
                }
                // 对于需要 ctxt 字段的类，如果未声明 ctxt 属性，则补充 ctxt: Int = 0
                // 注意：ctxt 是独立的字段，不是 Span 的一部分
                val className = decl.name.removeSurrounding("`")
                val needsCtxt = Hardcoded.CtxtFields.CLASSES_WITH_CTXT.contains(className)
                if (needsCtxt && decl.properties.none { it.name == "ctxt" }) {
                    val ctxtType = ClassName("kotlin", "Int")
                    val ctxtProp = PropertySpec.builder("ctxt", ctxtType)
                        .addModifiers(KModifier.PUBLIC)
                        .mutable(true)
                        .initializer("0")
                        .addAnnotation(
                            AnnotationSpec.builder(Hardcoded.AnnotationNames.toClassName(Hardcoded.AnnotationNames.ENCODE_DEFAULT)).build()
                        )
                        .build()
                    builder.addProperty(ctxtProp)
                }
                // 对于类：补齐父接口链上的抽象属性（override，可空，默认 null），避免未实现抽象属性导致编译错误
                fun collectParentProps(type: KotlinType, seen: MutableSet<String>, out: MutableList<KotlinDeclaration.PropertyDecl>) {
                    val name = when (type) {
                        is KotlinType.Simple -> type.name
                        else -> return
                    }
                    if (!seen.add(name)) return
                    val parentDecl = declLookup[name] ?: return
                    // 仅当父是接口/密封接口时才需要实现其抽象属性
                    if (parentDecl.modifier is ClassModifier.Interface || parentDecl.modifier is ClassModifier.SealedInterface) {
                        out.addAll(parentDecl.properties)
                    }
                    parentDecl.parents.forEach { p -> collectParentProps(p, seen, out) }
                }
                val parentProps = mutableListOf<KotlinDeclaration.PropertyDecl>()
                val visited = mutableSetOf<String>()
                decl.parents.forEach { p -> collectParentProps(p, visited, parentProps) }
                // 逐一添加缺失属性
                parentProps.forEach { p ->
                    val propName = p.name
                    if (existingPropNames.contains(propName)) return@forEach
                    if (propName == "span" && hasSpanDerived) return@forEach // 已在上面补齐
                    // 计算类型：除特殊字段外一律可空
                    val isTypeProp = propName.removeSurrounding("`") == "type"
                    val isSyntaxProp = propName.removeSurrounding("`") == "syntax"
                    // Node 系谱的 type 属性若缺失，保持非空 String 并添加 @Transient 与默认值为声明名
                    if (isTypeProp && nodeDerived) {
                        val typeName = ClassName("kotlin", "String")
                        val typeProp = PropertySpec.builder("type", typeName)
                            .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)
                            .mutable(true)
                            .initializer("%S", decl.name.removeSurrounding("`"))
                            .addAnnotation(
                                AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
                            )
                            .build()
                        builder.addProperty(typeProp)
                        existingPropNames.add(propName)
                        return@forEach
                    }
                    // ParserConfig 系谱的 syntax 属性标注 @Transient
                    val baseTypeName = convertType(p.type)
                    val finalType = if (p.type is KotlinType.Nullable) baseTypeName else baseTypeName.copy(nullable = true)
                    // 跳过：真正属性在下一轮构建（需要默认值与注解）
                }
                // 再次填充（修正 KModifier.OFFSET 误用并设置默认值 null）
                parentProps.forEach { p ->
                    val propName = p.name
                    if (existingPropNames.contains(propName)) return@forEach
                    if (propName == "span" && hasSpanDerived) return@forEach
                    val isSyntaxProp = propName.removeSurrounding("`") == "syntax"
                    val baseTypeName = convertType(p.type)
                    val finalType = if (p.type is KotlinType.Nullable) baseTypeName else baseTypeName.copy(nullable = true)
                    val builderProp = PropertySpec.builder(propName, finalType)
                        .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)
                        .mutable(true)
                    if (isSyntaxProp && configDerived) {
                        builderProp.addAnnotation(AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build())
                    }
                    builderProp.initializer("null")
                    builder.addProperty(builderProp.build())
                    existingPropNames.add(propName)
                }
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
     */
    fun convertProperty(prop: KotlinDeclaration.PropertyDecl): PropertySpec? {
        return try {
            val typeName = convertType(prop.type)
            val builder = PropertySpec.builder(prop.name, typeName)

            // 添加修饰符
            addPropertyModifiers(builder, prop.modifier)

            // 添加注解
            prop.annotations.forEach { annotation ->
                convertAnnotation(annotation)?.let { builder.addAnnotation(it) }
            }

            // 检查是否有默认值
            val hasDefaultValue = prop.defaultValue != null
            val hasEncodeDefault = prop.annotations.any { it.name == "EncodeDefault" }

            // 添加默认值
            prop.defaultValue?.let { defaultValue ->
                val defaultValueStr = defaultValue.toCodeString()
                // 只有当默认值不为空时才添加 initializer
                if (defaultValueStr.isNotBlank()) {
                    builder.initializer(defaultValueStr)
                    // 如果有默认值但没有 @EncodeDefault 注解，自动添加
                    // 这样可以确保反序列化时，如果 JSON 中缺失字段，使用默认值
                    if (!hasEncodeDefault) {
                        builder.addAnnotation(
                            AnnotationSpec.builder(ClassName("kotlinx.serialization", "EncodeDefault")).build()
                        )
                    }
                }
            }

            // 添加文档
            prop.kdoc?.let { builder.addKdoc(it.cleanKdoc()) }

            builder.build()
        } catch (e: Exception) {
            Logger.warn("转换属性失败: ${prop.name}, ${e.message}")
            Logger.warn("错误详情: ${e.stackTraceToString()}")
            null
        }
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
     * 
     * 注意：此方法只检测联合类型（Union.U2/U3/U4）。
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
     */
    fun convertAnnotation(annotation: KotlinDeclaration.Annotation): AnnotationSpec? {
        val cacheKey = "${annotation.name}:${annotation.arguments.joinToString(",") { it.toCodeString() }}"

        return CacheManager.getOrPutAnnotation(cacheKey) {
            try {
                val className = getAnnotationClassName(annotation.name)
                val builder = AnnotationSpec.builder(className)

                // 添加参数
                annotation.arguments.forEach { arg ->
                    addAnnotationArgument(builder, arg)
                }

                builder.build()
            } catch (e: Exception) {
                Logger.warn("转换注解失败: ${annotation.name}, ${e.message}")
                null
            }
        }
    }

    /**
     * 获取注解类名（提取公共逻辑）
     */
    private fun getAnnotationClassName(annotationName: String): ClassName =
        if (annotationName == "SwcDslMarker") {
            ClassName("dev.yidafu.swc.generated", "SwcDslMarker")
        } else {
            Hardcoded.AnnotationNames.toClassName(annotationName)
        }

    /**
     * 添加注解参数（提取公共逻辑）
     */
    private fun addAnnotationArgument(builder: AnnotationSpec.Builder, arg: Expression) {
        when (arg) {
            is Expression.StringLiteral -> builder.addMember("%S", arg.value)
            is Expression.ClassReference -> {
                val className = when (arg.className) {
                    "ExperimentalSerializationApi" -> ClassName("kotlinx.serialization", "ExperimentalSerializationApi")
                    else -> ClassName("", arg.className)
                }
                builder.addMember("%T::class", className)
            }
            else -> builder.addMember("%L", arg.toCodeString())
        }
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
     */
    private fun addPropertyModifiers(builder: PropertySpec.Builder, modifier: PropertyModifier) {
        // 所有属性都应该是 public
        builder.addModifiers(KModifier.PUBLIC)

        when (modifier) {
            is PropertyModifier.ConstVal -> {
                builder.addModifiers(KModifier.CONST)
                builder.mutable(false)
            }
            is PropertyModifier.Val -> builder.mutable(false)
            is PropertyModifier.Var -> builder.mutable(true)
            is PropertyModifier.LateinitVar -> {
                builder.addModifiers(KModifier.LATEINIT)
                builder.mutable(true)
            }
            is PropertyModifier.OverrideVal -> {
                builder.addModifiers(KModifier.OVERRIDE)
                builder.mutable(false)
            }
            is PropertyModifier.OverrideVar -> {
                builder.addModifiers(KModifier.OVERRIDE)
                builder.mutable(true)
            }
        }
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
    private val interfaceSuffixes = Hardcoded.InterfaceHeuristics.interfaceSuffixes

    private fun isInterfaceType(kotlinType: KotlinType, interfaceNames: Set<String>): Boolean {
        return when (kotlinType) {
            is KotlinType.Simple -> {
                val name = kotlinType.name
                interfaceNames.contains(name) || interfaceSuffixes.any { name.endsWith(it) }
            }
            else -> false
        }
    }

    /**
     * 判断接口是否属于 Node 系谱（直接或通过 *Base 接口）
     * 由于这里只能看到直接父类型，采用启发式集合来识别已知的 Node 基类接口。
     */
    private val nodeBaseInterfaces = setOf(
        "Node",
        "ExpressionBase",
        "PatternBase",
        "PropBase",
        "ClassMethodBase",
        "ClassPropertyBase",
        "HasSpan" // 大多数 Node 派生都会实现 HasSpan
    )

    private fun isDerivedFromNode(parents: List<KotlinType>, declLookup: Map<String, KotlinDeclaration.ClassDecl>): Boolean {
        if (parents.isEmpty()) return false
        fun dfs(type: KotlinType, seen: MutableSet<String>): Boolean {
            val name = when (type) {
                is KotlinType.Simple -> type.name
                else -> return false
            }
            if (!seen.add(name)) return false
            if (name == "Node") return true
            val parentDecl = declLookup[name] ?: return false
            return parentDecl.parents.any { p -> dfs(p, seen) }
        }
        return parents.any { dfs(it, mutableSetOf()) }
    }

    private fun isDerivedFrom(
        parents: List<KotlinType>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>,
        target: String
    ): Boolean {
        if (parents.isEmpty()) return false
        fun dfs(type: KotlinType, seen: MutableSet<String>): Boolean {
            val name = when (type) {
                is KotlinType.Simple -> type.name
                else -> return false
            }
            if (!seen.add(name)) return false
            if (name == target) return true
            val parentDecl = declLookup[name] ?: return false
            return parentDecl.parents.any { p -> dfs(p, seen) }
        }
        return parents.any { dfs(it, mutableSetOf()) }
    }

    /**
     * 检查类是否实现了任何密封接口（sealed interface）
     * 这对于多态序列化很重要，因为所有实现密封接口的类都需要 @SerialName 注解
     */
    private fun implementsSealedInterface(
        parents: List<KotlinType>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ): Boolean {
        if (parents.isEmpty()) return false
        fun dfs(type: KotlinType, seen: MutableSet<String>): Boolean {
            val name = when (type) {
                is KotlinType.Simple -> type.name
                else -> return false
            }
            if (!seen.add(name)) return false
            val parentDecl = declLookup[name] ?: return false
            // 检查当前父类型是否是密封接口
            if (parentDecl.modifier is ClassModifier.SealedInterface) {
                return true
            }
            // 递归检查所有父类型
            return parentDecl.parents.any { p -> dfs(p, seen) }
        }
        return parents.any { dfs(it, mutableSetOf()) }
    }

    /**
     * 如果父类型层级中不存在同名属性，则去掉 Override 修饰，避免生成无效的 override。
     */
    private fun downgradeOverrideIfNeeded(
        prop: KotlinDeclaration.PropertyDecl,
        parents: List<KotlinType>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ): KotlinDeclaration.PropertyDecl {
        fun parentHasProperty(type: KotlinType, target: String, seen: MutableSet<String>): Boolean {
            val name = when (type) {
                is KotlinType.Simple -> type.name
                else -> return false
            }
            if (!seen.add(name)) return false
            val decl = declLookup[name] ?: return false
            if (decl.properties.any { it.name == target }) return true
            return decl.parents.any { parentHasProperty(it, target, seen) }
        }
        val has = parents.any { parentHasProperty(it, prop.name, mutableSetOf()) }
        if (has) return prop
        val newModifier = when (prop.modifier) {
            dev.yidafu.swc.generator.model.kotlin.PropertyModifier.OverrideVal ->
                dev.yidafu.swc.generator.model.kotlin.PropertyModifier.Val
            dev.yidafu.swc.generator.model.kotlin.PropertyModifier.OverrideVar ->
                dev.yidafu.swc.generator.model.kotlin.PropertyModifier.Var
            else -> prop.modifier
        }
        return if (newModifier == prop.modifier) prop else prop.copy(modifier = newModifier)
    }
}
