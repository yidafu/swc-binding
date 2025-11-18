package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType

/**
 * 序列化注解辅助类
 * 负责处理与序列化相关的注解，如 @SerialName、@Transient、@JsonClassDiscriminator 等
 */
object SerializationAnnotationHelper {

    /**
     * 为类添加序列化相关的注解
     * 包括 @SerialName（用于 ParserConfig 子类和 Node 派生类）和 @JsonClassDiscriminator
     */
    fun addSerializationAnnotations(
        builder: TypeSpec.Builder,
        decl: KotlinDeclaration.ClassDecl,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ) {
        val className = decl.name.removeSurrounding("`")
        val hasSerialName = decl.annotations.any { it.name == "SerialName" }
        val nodeDerived = decl.name == "Node" || ClassDeclarationConverter.isDerivedFromNode(decl.parents, declLookup)
        val implementsSealedInterface = ClassDeclarationConverter.implementsSealedInterface(decl.parents, declLookup)
        val configDerived = ClassDeclarationConverter.isDerivedFrom(decl.parents, declLookup, "ParserConfig") ||
            ClassDeclarationConverter.isDerivedFrom(decl.parents, declLookup, "BaseParseOptions")

        // 不再自动添加 @SerialName 注解
        // 如果需要 @SerialName，应该在类声明中显式添加
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

        // 添加 @JsonClassDiscriminator 注解
        // 使用 @JsonClassDiscriminator 来指定 type 属性作为 discriminator
        // 这样 type 属性既会被序列化，也会用于多态识别
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
        }
    }

    /**
     * 为属性添加 @Transient 注解（如果需要）
     * Node 系谱的 type 属性和 ParserConfig 系谱的 syntax 属性需要添加 @Transient
     */
    fun addTransientAnnotationIfNeeded(
        propName: String,
        propType: KotlinType,
        nodeDerived: Boolean,
        configDerived: Boolean
    ): AnnotationSpec? {
        return when {
            propName == "type" && nodeDerived -> {
                AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
            }
            propName == "syntax" && configDerived -> {
                AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
            }
            else -> null
        }
    }
}
