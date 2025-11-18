package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import dev.yidafu.swc.generator.config.AnnotationConfig
import dev.yidafu.swc.generator.config.CtxtFieldsConfig
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.Expression
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.computeSerialName

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
        addPolymorphicAnnotations(builder, nodeDerived, configDerived, decl)

        // 为所有 Node 派生类添加 type 字段注释
        if (nodeDerived) {
            // 获取实际的 type 字段值（用于注释），而不是 SerialName（可能不同）
            val typeFieldValue = decl.properties.find { it.name.removeSurrounding("`") == "type" }?.defaultValue?.let { defaultValue ->
                when (defaultValue) {
                    is Expression.StringLiteral -> defaultValue.value
                    else -> null
                }
            } ?: dev.yidafu.swc.generator.config.CodeGenerationRules.getTypeFieldLiteralValue(decl.name.removeSurrounding("`")) ?: decl.name.removeSurrounding("`")
            builder.addKdoc("conflict with @SerialName\nremove class property `override var type : String? = %S`", typeFieldValue)
        }
        // 为所有 Config 派生类添加 syntax 字段注释
        if (configDerived) {
            val serialName = decl.computeSerialName("syntax")
            builder.addKdoc("conflict with @SerialName\nremove class property `override var syntax : String? = %S`", serialName)
        }

        val existingPropNames = mutableSetOf<String>()

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
        addCtxtPropertyIfNeeded(builder, decl, existingPropNames)

        // 补齐父接口链上的抽象属性
        addMissingParentProperties(
            builder,
            decl,
            nodeDerived,
            configDerived,
            declLookup,
            existingPropNames,
            convertType
        )
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
        decl: KotlinDeclaration.ClassDecl? = null
    ) {
        if (nodeDerived) {
            builder.addAnnotation(
                AnnotationSpec.builder(PoetConstants.Kotlin.OPT_IN)
                    .addMember("%T::class", PoetConstants.Serialization.EXPERIMENTAL)
                    .build()
            )
            builder.addAnnotation(
                AnnotationSpec.builder(ClassName("kotlinx.serialization.json", "JsonClassDiscriminator"))
                    .addMember("%S", "type")
                    .build()
            )
            // 为 Node 派生类（叶子节点）添加 @SerialName 和 @SwcDslMarker 注解
            if (decl != null) {
                val serialName = decl.computeSerialName("type")
                builder.addAnnotation(
                    AnnotationSpec.builder(ClassName("kotlinx.serialization", "SerialName"))
                        .addMember("%S", serialName)
                        .build()
                )
                // 为 Node 叶子节点添加 @SwcDslMarker 注解
                builder.addAnnotation(
                    AnnotationSpec.builder(PoetConstants.SwcTypes.SWC_DSL_MARKER).build()
                )
            }
        } else if (configDerived) {
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
        existingPropNames: MutableSet<String>
    ) {
        val className = decl.name.removeSurrounding("`")
        val needsCtxt = CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(className)
        if (needsCtxt && decl.properties.none { it.name == "ctxt" }) {
            val ctxtType = ClassName("kotlin", "Int")
            val ctxtProp = PropertySpec.builder("ctxt", ctxtType)
                .addModifiers(KModifier.PUBLIC)
                .mutable(true)
                .initializer("0")
                .addAnnotation(
                    AnnotationSpec.builder(AnnotationConfig.toClassName(AnnotationConfig.ENCODE_DEFAULT)).build()
                )
                .build()
            builder.addProperty(ctxtProp)
            existingPropNames.add("ctxt")
        }
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
            }
            parentDecl.parents.forEach { p -> collectParentProps(p, seen, out) }
        }

        val parentProps = mutableListOf<KotlinDeclaration.PropertyDecl>()
        val visited = mutableSetOf<String>()
        parents.forEach { p -> collectParentProps(p, visited, parentProps) }
        return parentProps
    }
}
