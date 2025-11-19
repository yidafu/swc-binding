package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import dev.yidafu.swc.generator.config.AnnotationConfig
import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.config.CtxtFieldsConfig
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.Expression
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.computeSerialName
import dev.yidafu.swc.generator.util.CollectionUtils
import dev.yidafu.swc.generator.util.NameUtils.clean

/**
 * 普通类转换器
 * 负责将普通类声明转换为 KotlinPoet 的 TypeSpec
 * 处理最复杂的类转换逻辑，包括属性补齐、span/ctxt 字段补充等
 */
object RegularClassConverter {

    /**
     * 为普通类添加属性和相关逻辑
     */
    fun addRegularClassProperties(
        builder: TypeSpec.Builder,
        decl: KotlinDeclaration.ClassDecl,
        nodeDerived: Boolean,
        hasSpanDerived: Boolean,
        configDerived: Boolean,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>,
        convertType: (KotlinType) -> com.squareup.kotlinpoet.TypeName,
        convertProperty: (KotlinDeclaration.PropertyDecl) -> com.squareup.kotlinpoet.PropertySpec?,
        downgradeOverride: (KotlinDeclaration.PropertyDecl, List<KotlinType>, Map<String, KotlinDeclaration.ClassDecl>) -> KotlinDeclaration.PropertyDecl,
        addUnionAnnotation: (String, String, KotlinType, PropertySpec.Builder) -> Unit
    ) {
        // 多态类添加 JsonClassDiscriminator 和 @SerialName
        addPolymorphicAnnotations(builder, nodeDerived, configDerived, decl, declLookup)

        // 为所有 Node 派生类添加 type 字段注释
        if (nodeDerived) {
            // 获取实际的 type 字段值（用于注释），而不是 SerialName（可能不同）
            val typeFieldValue = decl.properties.find { clean(it.name) == "type" }?.defaultValue?.let { defaultValue ->
                when (defaultValue) {
                    is Expression.StringLiteral -> defaultValue.value
                    else -> null
                }
            } ?: dev.yidafu.swc.generator.config.CodeGenerationRules.getTypeFieldLiteralValue(clean(decl.name)) ?: clean(decl.name)
            builder.addKdoc("conflict with @SerialName\nremove class property `override var type : String? = %S`", typeFieldValue)
        }
        // 为所有 Config 派生类添加 syntax 字段注释
        if (configDerived) {
            val serialName = decl.computeSerialName("syntax")
            builder.addKdoc("conflict with @SerialName\nremove class property `override var syntax : String? = %S`", serialName)
        }

        val existingPropNames = CollectionUtils.newStringSet()

        // 添加类自己的属性
        decl.properties.forEach { prop ->
            existingPropNames.add(prop.name)
            // 非 Node 系谱实现类不应包含 `type`
            if (prop.name == "type" && !nodeDerived) {
                return@forEach
            }
            val adjusted = downgradeOverride(prop, decl.parents, declLookup)
            // Node 派生类不生成 type 属性，使用 @SerialName + @JsonClassDiscriminator 代替
            if (adjusted.name == "type" && nodeDerived) {
                return@forEach
            }
            // Config 派生类不生成 syntax 属性，使用 @SerialName + @JsonClassDiscriminator 代替
            if (adjusted.name == "syntax" && configDerived) {
                return@forEach
            }
            val base = convertProperty(adjusted)
            if (base != null) {
                val propBuilder = base.toBuilder()
                addUnionAnnotation(decl.name, adjusted.name, adjusted.type, propBuilder)
                builder.addProperty(propBuilder.build())
            }
        }

        // 补充 span 属性（如果需要）
        addSpanPropertyIfNeeded(builder, decl, hasSpanDerived, existingPropNames)

        // 补充 ctxt 属性（如果需要）
        // 注意：这会在配置的类中添加 ctxt，但不会处理通过父接口继承的 ctxt
        addCtxtPropertyIfNeeded(builder, decl, existingPropNames, declLookup)

        // 补齐父接口链上的抽象属性（包括通过父接口继承的 ctxt）
        addMissingParentProperties(
            builder,
            decl,
            nodeDerived,
            configDerived,
            declLookup,
            existingPropNames,
            convertType
        )

        // 为需要显式 type 字段的类添加 type 属性
        // 这些类虽然有 @JsonClassDiscriminator，但作为具体类型序列化时不会自动添加 type 字段
        addExplicitTypeFieldIfNeeded(builder, decl, nodeDerived, existingPropNames)
    }

    /**
     * 添加多态序列化注解
     * 使用 @JsonClassDiscriminator 来指定 type 属性作为 discriminator
     * 使用 @SerialName 来指定序列化时的类型名称
     */
    private fun addPolymorphicAnnotations(
        builder: TypeSpec.Builder,
        nodeDerived: Boolean,
        configDerived: Boolean,
        decl: KotlinDeclaration.ClassDecl? = null,
        declLookup: Map<String, KotlinDeclaration.ClassDecl> = emptyMap()
    ) {
        // 检查类是否实现了 sealed interface（参与多态序列化）
        val implementsSealedInterface = decl?.let { d ->
            ClassDeclarationConverter.implementsSealedInterface(d.parents, declLookup)
        } ?: false

        // 检查类是否在需要显式 type 字段的列表中
        val className = decl?.name?.removeSurrounding("`") ?: ""
        val requiresExplicitTypeField = CodeGenerationRules.classesRequiringExplicitTypeField.contains(className)

        // 检查类声明中是否已经有 @Serializable 注解（buildAnnotations 可能已经添加了）
        val hasSerializable = decl?.annotations?.any { it.name == "Serializable" } == true

        // Config 派生类优先使用 syntax discriminator
        if (configDerived) {
            // 只有在没有 @Serializable 注解时才添加
            if (!hasSerializable) {
                builder.addAnnotation(
                    AnnotationSpec.builder(PoetConstants.Serialization.SERIALIZABLE).build()
                )
            }
            builder.addAnnotation(
                AnnotationSpec.builder(PoetConstants.Kotlin.OPT_IN)
                    .addMember("%T::class", PoetConstants.Serialization.EXPERIMENTAL)
                    .build()
            )
            builder.addAnnotation(
                AnnotationSpec.builder(ClassName("kotlinx.serialization.json", "JsonClassDiscriminator"))
                    .addMember("%S", "syntax")
                    .build()
            )
            // 为 Config 派生类添加 @SerialName 注解
            if (decl != null) {
                val serialName = decl.computeSerialName("syntax")
                builder.addAnnotation(
                    AnnotationSpec.builder(ClassName("kotlinx.serialization", "SerialName"))
                        .addMember("%S", serialName)
                        .build()
                )
            }
        } else if (nodeDerived || implementsSealedInterface) {
            // 只有在没有 @Serializable 注解时才添加
            if (!hasSerializable) {
                builder.addAnnotation(
                    AnnotationSpec.builder(PoetConstants.Serialization.SERIALIZABLE).build()
                )
            }
            builder.addAnnotation(
                AnnotationSpec.builder(PoetConstants.Kotlin.OPT_IN)
                    .addMember("%T::class", PoetConstants.Serialization.EXPERIMENTAL)
                    .build()
            )
            // 对于需要显式 type 字段的类，不添加 @JsonClassDiscriminator，因为它们有显式的 type 属性
            // 这样可以避免与多态判别器冲突，同时确保非多态序列化时也有 type 字段
            if (!requiresExplicitTypeField) {
                builder.addAnnotation(
                    AnnotationSpec.builder(ClassName("kotlinx.serialization.json", "JsonClassDiscriminator"))
                        .addMember("%S", "type")
                        .build()
                )
            }
            // 为 Node 派生类或实现 sealed interface 的类（叶子节点）添加 @SerialName 和 @SwcDslMarker 注解
            if (decl != null) {
                val serialName = decl.computeSerialName("type")
                builder.addAnnotation(
                    AnnotationSpec.builder(ClassName("kotlinx.serialization", "SerialName"))
                        .addMember("%S", serialName)
                        .build()
                )
                // 为 Node 叶子节点添加 @SwcDslMarker 注解
                if (nodeDerived) {
                    builder.addAnnotation(
                        AnnotationSpec.builder(PoetConstants.SwcTypes.SWC_DSL_MARKER).build()
                    )
                }
            }
        }
    }

    /**
     * 为属性添加 @Transient 注解（如果需要）
     */
    private fun addTransientAnnotationIfNeeded(
        propBuilder: PropertySpec.Builder,
        propName: String,
        nodeDerived: Boolean,
        configDerived: Boolean
    ) {
        // 目前无需对 type 或 syntax 字段添加 @Transient
        // JsonClassDiscriminator("type"/"syntax") 已自动处理判别字段
        // 保留占位以便未来若需对其他字段添加 @Transient
    }

    /**
     * 补充 span 属性（如果需要）
     */
    private fun addSpanPropertyIfNeeded(
        builder: TypeSpec.Builder,
        decl: KotlinDeclaration.ClassDecl,
        hasSpanDerived: Boolean,
        existingPropNames: MutableSet<String>
    ) {
        if (hasSpanDerived && decl.properties.none { it.name == "span" }) {
            val spanType = ClassName("dev.yidafu.swc.generated", "Span")
            val memberEmptySpan = MemberName("dev.yidafu.swc", "emptySpan")
            val spanProp = PropertySpec.builder("span", spanType)
                .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)
                .mutable(true)
                .initializer("%M()", memberEmptySpan)
                .build()
            builder.addProperty(spanProp)
            existingPropNames.add("span")
        }
    }

    /**
     * 补充 ctxt 属性（如果需要）
     */
    private fun addCtxtPropertyIfNeeded(
        builder: TypeSpec.Builder,
        decl: KotlinDeclaration.ClassDecl,
        existingPropNames: MutableSet<String>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ) {
        val className = decl.name.removeSurrounding("`")

        // 检查类名本身是否需要 ctxt（包括反向映射检查）
        // 因为 "Class" 会被映射为 "JsClass"，但配置中使用的是 "Class"
        var needsCtxt = CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(className) ||
            CodeGenerationRules.getReverseMappedName(className)?.let { originalName ->
                CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(originalName)
            } ?: false
        var interfaceName: String? = null

        // 如果是实现类（以 Impl 结尾），检查对应的接口名
        if (!needsCtxt && className.endsWith("Impl")) {
            interfaceName = className.removeSuffix("Impl")
            needsCtxt = CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(interfaceName) ||
                CodeGenerationRules.getReverseMappedName(interfaceName)?.let { originalName ->
                    CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(originalName)
                } ?: false
        }

        // 如果接口在 interfaceToImplMap 中，也检查接口名
        if (!needsCtxt) {
            val interfaceToImplMap = dev.yidafu.swc.generator.config.SerializerConfig.interfaceToImplMap
            interfaceName = interfaceToImplMap.entries.find { it.value == className }?.key
            if (interfaceName != null) {
                needsCtxt = CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(interfaceName) ||
                    CodeGenerationRules.getReverseMappedName(interfaceName)?.let { originalName ->
                        CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(originalName)
                    } ?: false
            }
        }

        if (needsCtxt && decl.properties.none { it.name == "ctxt" }) {
            // 检查父接口是否有 ctxt 属性，如果有则需要添加 override
            // 1. 检查父接口的 properties 中是否有 ctxt（通过 parentHasProperty）
            // 2. 检查父接口是否在配置中需要 ctxt（因为接口的 ctxt 是在代码生成时添加的抽象属性）
            val hasCtxtInParentProperties = ClassDeclarationConverter.parentHasProperty("ctxt", decl.parents, declLookup)

            // 检查父接口是否在配置中需要 ctxt
            val hasCtxtInParentConfig = decl.parents.any { parentType ->
                val parentName = when (parentType) {
                    is KotlinType.Simple -> parentType.name.removeSurrounding("`")
                    else -> null
                }
                parentName?.let { name ->
                    CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(name) ||
                        CodeGenerationRules.getReverseMappedName(name)?.let { originalName ->
                            CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(originalName)
                        } ?: false
                } ?: false
            }

            val hasCtxtInParent = hasCtxtInParentProperties || hasCtxtInParentConfig

            val ctxtType = ClassName("kotlin", "Int")
            val ctxtPropBuilder = PropertySpec.builder("ctxt", ctxtType)
                .addModifiers(KModifier.PUBLIC)
                .mutable(true)
                .initializer("0")
                .addAnnotation(
                    AnnotationSpec.builder(AnnotationConfig.toClassName(AnnotationConfig.ENCODE_DEFAULT)).build()
                )

            // 如果父接口有 ctxt 属性，添加 override 修饰符
            if (hasCtxtInParent) {
                ctxtPropBuilder.addModifiers(KModifier.OVERRIDE)
            }

            builder.addProperty(ctxtPropBuilder.build())
            existingPropNames.add("ctxt")
        }
    }

    /**
     * 为需要显式 type 字段的类添加 type 属性
     * 这些类虽然有 @JsonClassDiscriminator，但作为具体类型序列化时不会自动添加 type 字段
     */
    private fun addExplicitTypeFieldIfNeeded(
        builder: TypeSpec.Builder,
        decl: KotlinDeclaration.ClassDecl,
        nodeDerived: Boolean,
        existingPropNames: MutableSet<String>
    ) {
        val className = decl.name.removeSurrounding("`")
        // 只处理 Node 派生类，且该类在需要显式 type 字段的列表中
        if (!nodeDerived || !CodeGenerationRules.classesRequiringExplicitTypeField.contains(className)) {
            return
        }

        // 对于需要显式 type 字段的类，即使 existingPropNames 中包含 "type"（因为之前跳过了生成），
        // 我们也应该强制添加 type 属性，因为之前的逻辑跳过了生成，所以 existingPropNames 中的 "type" 是误报
        // 注意：这里不检查 existingPropNames，因为对于这些特殊类，type 属性之前被跳过了生成

        // 优先使用从 TypeScript 定义中提取的字面量值
        // 如果没有找到字面量值，再使用 computeSerialName 作为后备
        val typeFieldValue = CodeGenerationRules.getTypeFieldLiteralValue(className)
            ?: decl.computeSerialName("type")
        // 注意：这些类在 addPolymorphicAnnotations 中不会添加 @JsonClassDiscriminator，
        // 因为它们需要显式的 type 属性。当它们作为多态类型序列化时，Kotlinx Serialization
        // 会使用 @SerialName 和显式的 type 属性来进行多态识别。当它们不作为多态类型序列化时
        // （如直接序列化 VariableDeclarator），显式的 type 字段会被序列化。
        val typeProp = PropertySpec.builder("type", ClassName("kotlin", "String"))
            .addModifiers(KModifier.PUBLIC)
            .mutable(true)
            .initializer("%S", typeFieldValue)
            .addAnnotation(
                AnnotationSpec.builder(AnnotationConfig.toClassName(AnnotationConfig.ENCODE_DEFAULT)).build()
            )
            .build()
        builder.addProperty(typeProp)
        existingPropNames.add("type")
    }

    /**
     * 补齐父接口链上的抽象属性
     */
    private fun addMissingParentProperties(
        builder: TypeSpec.Builder,
        decl: KotlinDeclaration.ClassDecl,
        nodeDerived: Boolean,
        configDerived: Boolean,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>,
        existingPropNames: MutableSet<String>,
        convertType: (KotlinType) -> com.squareup.kotlinpoet.TypeName
    ) {
        // 收集父接口的属性
        val parentProps = collectParentProperties(decl.parents, declLookup)

        // 第一轮：处理特殊属性（type）
        parentProps.forEach { p ->
            val propName = p.name
            if (existingPropNames.contains(propName)) return@forEach
            if (propName == "span" && decl.properties.none { it.name == "span" }) {
                // span 已在 addSpanPropertyIfNeeded 中处理
                return@forEach
            }

            val isTypeProp = propName.removeSurrounding("`") == "type"
            // Node 系谱的 type 属性不再生成，使用 @SerialName + @JsonClassDiscriminator 代替
            // 注释已在 addRegularClassProperties 开始处添加，这里只需跳过
            if (isTypeProp && nodeDerived) {
                existingPropNames.add(propName)
                return@forEach
            }
            val isSyntaxProp = propName.removeSurrounding("`") == "syntax"
            // Config 系谱的 syntax 属性不再生成，使用 @SerialName + @JsonClassDiscriminator 代替
            // 注释已在 addRegularClassProperties 开始处添加，这里只需跳过
            if (isSyntaxProp && configDerived) {
                existingPropNames.add(propName)
                return@forEach
            }
        }

        // 第二轮：处理其他缺失的属性
        parentProps.forEach { p ->
            val propName = p.name
            if (existingPropNames.contains(propName)) return@forEach
            if (propName == "span" && decl.properties.none { it.name == "span" }) {
                return@forEach
            }
            // ctxt 字段的特殊处理：
            // 1. 如果已经在 addCtxtPropertyIfNeeded 中添加了（配置的类），跳过
            // 2. 如果是通过父接口继承的（如 JsClass），需要添加但使用 Int 类型和初始值 0
            if (propName == "ctxt") {
                // 检查是否已经在 addCtxtPropertyIfNeeded 中添加了
                if (existingPropNames.contains("ctxt")) {
                    return@forEach
                }
                // 通过父接口继承的 ctxt，添加为 Int 类型，初始值为 0
                val ctxtType = ClassName("kotlin", "Int")
                val ctxtProp = PropertySpec.builder("ctxt", ctxtType)
                    .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)
                    .mutable(true)
                    .initializer("0")
                    .addAnnotation(
                        AnnotationSpec.builder(AnnotationConfig.toClassName(AnnotationConfig.ENCODE_DEFAULT)).build()
                    )
                    .build()
                builder.addProperty(ctxtProp)
                existingPropNames.add("ctxt")
                return@forEach
            }

            val baseTypeName = convertType(p.type)
            val finalType = if (p.type is KotlinType.Nullable) baseTypeName else baseTypeName.copy(nullable = true)
            val builderProp = PropertySpec.builder(propName, finalType)
                .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)
                .mutable(true)
            builderProp.initializer("null")
            builder.addProperty(builderProp.build())
            existingPropNames.add(propName)
        }
    }

    /**
     * 收集父接口链上的所有属性
     * 包括接口的 properties 中定义的属性，以及通过配置添加的抽象属性（如 ctxt）
     */
    private fun collectParentProperties(
        parents: List<KotlinType>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ): MutableList<KotlinDeclaration.PropertyDecl> {
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

                // 检查接口是否在配置中需要 ctxt（因为 ctxt 是在代码生成时添加的抽象属性）
                val interfaceName = name.removeSurrounding("`")
                val needsCtxt = CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(interfaceName) ||
                    CodeGenerationRules.getReverseMappedName(interfaceName)?.let { originalName ->
                        CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(originalName)
                    } ?: false

                // 如果接口需要 ctxt 但 properties 中没有，添加一个虚拟的 ctxt 属性
                if (needsCtxt && !parentDecl.properties.any { it.name == "ctxt" }) {
                    out.add(
                        KotlinDeclaration.PropertyDecl(
                            name = "ctxt",
                            type = KotlinType.Int,
                            modifier = dev.yidafu.swc.generator.model.kotlin.PropertyModifier.Var,
                            defaultValue = null,
                            annotations = emptyList(),
                            kdoc = null
                        )
                    )
                }
            }
            parentDecl.parents.forEach { p -> collectParentProps(p, seen, out) }
        }

        val parentProps = mutableListOf<KotlinDeclaration.PropertyDecl>()
        val visited = CollectionUtils.newStringSet()
        parents.forEach { p -> collectParentProps(p, visited, parentProps) }
        return parentProps
    }
}
