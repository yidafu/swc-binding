package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import dev.yidafu.swc.generator.config.CtxtFieldsConfig
import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration

/**
 * 接口类转换器
 * 负责将接口和密封接口声明转换为 KotlinPoet 的 TypeSpec
 */
object InterfaceClassConverter {

    /**
     * 为接口添加属性
     */
    fun addInterfaceProperties(
        builder: TypeSpec.Builder,
        decl: KotlinDeclaration.ClassDecl,
        nodeDerived: Boolean,
        convertType: (dev.yidafu.swc.generator.model.kotlin.KotlinType) -> com.squareup.kotlinpoet.TypeName,
        addUnionAnnotation: (String, String, dev.yidafu.swc.generator.model.kotlin.KotlinType, PropertySpec.Builder) -> Unit
    ) {
        // 接口：属性不得包含初始化器，生成抽象属性；为 Union.Ux 属性添加 @Serializable(with=...)
        // 所有接口都不生成 type 字段，使用 @SerialName + @JsonClassDiscriminator 代替
        if (decl.name == "Node") {
            // 为 Node 接口添加注释说明 type 字段与 @SerialName 冲突
            builder.addKdoc("conflict with @SerialName\nremove class property `var type: String?`")
        }
        val existingPropNames = mutableSetOf<String>()
        decl.properties.forEach { prop ->
            // 所有接口都不生成 type 字段，使用 @SerialName + @JsonClassDiscriminator 代替
            // @JsonClassDiscriminator("type") 会自动处理 type 字段，不需要在接口中声明
            if (prop.name == "type") {
                return@forEach
            }
            val typeName = convertType(prop.type)
            val propBuilder = PropertySpec.builder(prop.name, typeName)
            // 接口属性修饰符（保持 val/var）
            PropertyConverter.addPropertyModifiers(propBuilder, prop.modifier)
            // 透传原始注解
            prop.annotations.forEach { annotation ->
                AnnotationConverter.convertAnnotation(annotation)?.let { propBuilder.addAnnotation(it) }
            }
            // 注意：不再为 Node 接口的 type 属性添加 @Transient
            // 当使用 @JsonClassDiscriminator("type") 时，Kotlinx Serialization 会自动处理 type 字段
            // 如果添加 @Transient，会导致序列化时缺少 type 字段
            // if (prop.name == "type" && decl.name == "Node") {
            //     propBuilder.addAnnotation(
            //         AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
            //     )
            // }
            // 为 Union.Ux 或 Array<Union.Ux> 添加专属 with 注解并收集
            addUnionAnnotation(decl.name, prop.name, prop.type, propBuilder)
            // 文档
            prop.kdoc?.let { propBuilder.addKdoc(it.cleanKdoc()) }
            builder.addProperty(propBuilder.build())
            existingPropNames.add(prop.name)
        }

        // 补充 ctxt 属性（如果需要）
        addCtxtPropertyIfNeeded(builder, decl, existingPropNames)
    }

    /**
     * 补充 ctxt 属性（如果需要）
     */
    private fun addCtxtPropertyIfNeeded(
        builder: TypeSpec.Builder,
        decl: KotlinDeclaration.ClassDecl,
        existingPropNames: MutableSet<String>
    ) {
        val interfaceName = decl.name.removeSurrounding("`")
        // 检查映射后的名称和原始名称（通过反向映射）
        // 因为 "Class" 会被映射为 "JsClass"，但配置中使用的是 "Class"
        val needsCtxt = CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(interfaceName) ||
            // 反向映射：检查是否有原始名称在配置中
            CodeGenerationRules.getReverseMappedName(interfaceName)?.let { originalName ->
                CtxtFieldsConfig.CLASSES_WITH_CTXT.contains(originalName)
            } ?: false

        if (needsCtxt && !existingPropNames.contains("ctxt")) {
            val ctxtType = ClassName("kotlin", "Int")
            val ctxtProp = PropertySpec.builder("ctxt", ctxtType)
                .addModifiers(KModifier.PUBLIC, KModifier.ABSTRACT)
                .mutable(true)
                .build()
            builder.addProperty(ctxtProp)
        }
    }
}
