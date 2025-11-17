package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
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
        decl.properties.forEach { prop ->
            // 仅 Node 系谱接口保留 `type` 抽象属性；其余接口不生成该字段
            if (prop.name == "type" && !nodeDerived) {
                return@forEach
            }
            val typeName = convertType(prop.type)
            val propBuilder = PropertySpec.builder(prop.name, typeName)
            // 接口属性修饰符（保持 val/var），针对 Node 系谱上的 `type` 强制添加 override（除 Node 本身）
            PropertyConverter.addPropertyModifiers(propBuilder, prop.modifier)
            if (prop.name == "type" && nodeDerived && decl.name != "Node") {
                propBuilder.addModifiers(KModifier.OVERRIDE)
            }
            // 透传原始注解
            prop.annotations.forEach { annotation ->
                AnnotationConverter.convertAnnotation(annotation)?.let { propBuilder.addAnnotation(it) }
            }
            // Node 接口的 type 属性需要添加 @Transient，因为它与 JsonClassDiscriminator("type") 冲突
            if (prop.name == "type" && decl.name == "Node") {
                propBuilder.addAnnotation(
                    AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
                )
            }
            // 为 Union.Ux 或 Array<Union.Ux> 添加专属 with 注解并收集
            addUnionAnnotation(decl.name, prop.name, prop.type, propBuilder)
            // 文档
            prop.kdoc?.let { propBuilder.addKdoc(it.cleanKdoc()) }
            builder.addProperty(propBuilder.build())
        }
    }
}

