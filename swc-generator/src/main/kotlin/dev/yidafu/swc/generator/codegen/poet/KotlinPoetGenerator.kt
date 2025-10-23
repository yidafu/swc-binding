package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.*
import dev.yidafu.swc.generator.codegen.poet.PoetConstants.Serialization
import dev.yidafu.swc.generator.util.Logger

/**
 * KotlinPoet 代码生成器
 */
object KotlinPoetGenerator {

    /**
     * 从注解字符串提取参数
     */
    private fun String.extractAnnotationParam(annotationName: String): String? {
        if (!this.contains(annotationName)) return null

        val pattern = when {
            annotationName == "@SerialName" -> """@SerialName\((.+?)\)"""
            annotationName == "@Serializable" -> """@Serializable\((.+?)\)"""
            else -> return null
        }

        return Regex(pattern).find(this)?.groupValues?.get(1)
    }

    /**
     * 从 ClassDecl 创建 TypeSpec
     */
    fun createTypeSpec(classDecl: dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration.ClassDecl): TypeSpec {
        return try {
            KotlinPoetConverter.convertDeclaration(classDecl)
        } catch (e: Exception) {
            Logger.warn("使用 KotlinPoetConverter 转换失败: ${e.message}")
            throw e
        }
    }

    /**
     * 批量添加注解
     */
    private fun TypeSpec.Builder.addAnnotations(annotations: List<String>) {
        annotations.mapNotNull { parseAnnotation(it) }.forEach { addAnnotation(it) }
    }

    /**
     * 解析注解字符串为 AnnotationSpec
     */
    private fun parseAnnotation(annotationStr: String): AnnotationSpec? {
        return when (val cleaned = annotationStr.trim()) {
            "@SwcDslMarker" -> AnnotationSpec.builder(ClassName("dev.yidafu.swc.dsl", "SwcDslMarker")).build()
            "@Serializable" -> AnnotationSpec.builder(Serialization.SERIALIZABLE).build()

            else -> when {
                cleaned.startsWith("@Serializable(") -> {
                    val param = cleaned.substringAfter("(").substringBefore(")")
                    AnnotationSpec.builder(Serialization.SERIALIZABLE)
                        .addMember("%L", param)
                        .build()
                }
                cleaned.startsWith("@SerialName") -> {
                    val param = cleaned.substringAfter("(").substringBefore(")")
                    AnnotationSpec.builder(Serialization.SERIAL_NAME)
                        .addMember("%L", param)
                        .build()
                }
                cleaned.startsWith("@JsonClassDiscriminator") -> {
                    val discriminator = cleaned.substringAfter("(\"").substringBefore("\")")
                    AnnotationSpec.builder(Serialization.Json.JSON_CLASS_DISCRIMINATOR)
                        .addMember("%S", discriminator)
                        .build()
                }
                cleaned.startsWith("@OptIn") -> {
                    AnnotationSpec.builder(PoetConstants.Kotlin.OPT_IN)
                        .addMember("%T::class", Serialization.EXPERIMENTAL)
                        .build()
                }
                else -> {
                    Logger.verbose("跳过注解: $cleaned", 8)
                    null
                }
            }
        }
    }
}
