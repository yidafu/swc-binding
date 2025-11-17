package dev.yidafu.swc.generator.config

import com.squareup.kotlinpoet.ClassName

/**
 * 注解配置
 * 管理序列化相关注解的名称和类名映射
 */
object AnnotationConfig {
    /**
     * 注解名称枚举
     */
    enum class AnnotationName(val value: String) {
        SERIAL_NAME("SerialName"),
        SERIALIZABLE("Serializable"),
        JSON_CLASS_DISCRIMINATOR("JsonClassDiscriminator"),
        TRANSIENT("Transient"),
        ENCODE_DEFAULT("EncodeDefault"),
        OPT_IN("OptIn"),
        CONTEXTUAL("Contextual")
    }

    /**
     * 将注解名称转换为 ClassName
     */
    fun toClassName(name: String): ClassName {
        return when (name) {
            AnnotationName.SERIAL_NAME.value -> ClassName("kotlinx.serialization", "SerialName")
            AnnotationName.SERIALIZABLE.value -> ClassName("kotlinx.serialization", "Serializable")
            AnnotationName.JSON_CLASS_DISCRIMINATOR.value -> ClassName("kotlinx.serialization.json", "JsonClassDiscriminator")
            AnnotationName.TRANSIENT.value -> ClassName("kotlinx.serialization", "Transient")
            AnnotationName.ENCODE_DEFAULT.value -> ClassName("kotlinx.serialization", "EncodeDefault")
            AnnotationName.OPT_IN.value -> ClassName("kotlin", "OptIn")
            AnnotationName.CONTEXTUAL.value -> ClassName("kotlinx.serialization", "Contextual")
            else -> ClassName("", name)
        }
    }

    /**
     * 获取注解名称常量（向后兼容）
     */
    val SERIAL_NAME: String = AnnotationName.SERIAL_NAME.value
    val SERIALIZABLE: String = AnnotationName.SERIALIZABLE.value
    val JSON_CLASS_DISCRIMINATOR: String = AnnotationName.JSON_CLASS_DISCRIMINATOR.value
    val TRANSIENT: String = AnnotationName.TRANSIENT.value
    val ENCODE_DEFAULT: String = AnnotationName.ENCODE_DEFAULT.value
    val OPT_IN: String = AnnotationName.OPT_IN.value
    val CONTEXTUAL: String = AnnotationName.CONTEXTUAL.value
}

