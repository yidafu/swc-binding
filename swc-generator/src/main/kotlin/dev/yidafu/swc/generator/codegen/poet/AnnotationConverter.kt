package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import dev.yidafu.swc.generator.config.AnnotationConfig
import dev.yidafu.swc.generator.model.kotlin.Expression
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.util.CacheManager
import dev.yidafu.swc.generator.util.Logger

/**
 * 注解转换器
 * 负责将 KotlinDeclaration.Annotation 转换为 KotlinPoet 的 AnnotationSpec
 */
object AnnotationConverter {

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
            AnnotationConfig.toClassName(annotationName)
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
                    "TruePlusMinusSerializer" -> ClassName("dev.yidafu.swc", "TruePlusMinusSerializer")
                    else -> ClassName("", arg.className)
                }
                builder.addMember("%T::class", className)
            }
            else -> builder.addMember("%L", arg.toCodeString())
        }
    }
}
