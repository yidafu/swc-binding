package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import dev.yidafu.swc.generator.config.CtxtFieldsConfig
import dev.yidafu.swc.generator.config.AnnotationConfig
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType

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
        // 多态类添加 JsonClassDiscriminator
        addPolymorphicAnnotations(builder, nodeDerived, configDerived)

        val existingPropNames = mutableSetOf<String>()
        
        // 添加类自己的属性
        decl.properties.forEach { prop ->
            existingPropNames.add(prop.name)
            // 非 Node 系谱实现类不应包含 `type`
            if (prop.name == "type" && !nodeDerived) {
                return@forEach
            }
            val adjusted = downgradeOverride(prop, decl.parents, declLookup)
            val base = convertProperty(adjusted)
            if (base != null) {
                val propBuilder = base.toBuilder()
                // Node 系谱的 type 属性需要添加 @Transient，因为它与 JsonClassDiscriminator("type") 冲突
                // 避免与 "syntax" 判别关键字冲突：对 ParserConfig 系谱中的 `syntax` 字段标注 @Transient
                addTransientAnnotationIfNeeded(propBuilder, adjusted.name, nodeDerived, configDerived)
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
            builder, decl, nodeDerived, configDerived, declLookup, 
            existingPropNames, convertType
        )
    }

    /**
     * 添加多态序列化注解
     */
    private fun addPolymorphicAnnotations(
        builder: TypeSpec.Builder,
        nodeDerived: Boolean,
        configDerived: Boolean
    ) {
        if (nodeDerived) {
            builder.addAnnotation(
                AnnotationSpec.builder(ClassName("kotlinx.serialization", "ExperimentalSerializationApi")).build()
            )
            builder.addAnnotation(
                AnnotationSpec.builder(ClassName("kotlinx.serialization.json", "JsonClassDiscriminator"))
                    .addMember("%S", "type")
                    .build()
            )
        } else if (configDerived) {
            builder.addAnnotation(
                AnnotationSpec.builder(ClassName("kotlinx.serialization", "ExperimentalSerializationApi")).build()
            )
            builder.addAnnotation(
                AnnotationSpec.builder(ClassName("kotlinx.serialization.json", "JsonClassDiscriminator"))
                    .addMember("%S", "syntax")
                    .build()
            )
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
        if (propName == "type" && nodeDerived) {
            propBuilder.addAnnotation(
                AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
            )
        } else if (propName == "syntax" && configDerived) {
            propBuilder.addAnnotation(
                AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
            )
        }
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
        }

        // 第二轮：处理其他缺失的属性
        parentProps.forEach { p ->
            val propName = p.name
            if (existingPropNames.contains(propName)) return@forEach
            if (propName == "span" && decl.properties.none { it.name == "span" }) {
                return@forEach
            }

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

