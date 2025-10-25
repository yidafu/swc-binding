package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.ClassName

/**
 * KotlinPoet 常量定义
 */
object PoetConstants {
    // Package names
    const val PKG_TYPES = "dev.yidafu.swc.generated"
    const val PKG_DSL = "dev.yidafu.swc.generated.dsl"
    const val PKG_BOOLEANABLE = "dev.yidafu.swc.booleanable"

    // Kotlinx Serialization
    object Serialization {
        private const val PKG = "kotlinx.serialization"
        val SERIALIZABLE = ClassName(PKG, "Serializable")
        val SERIAL_NAME = ClassName(PKG, "SerialName")
        val CONTEXTUAL = ClassName(PKG, "Contextual")
        val EXPERIMENTAL = ClassName(PKG, "ExperimentalSerializationApi")

        object Json {
            private const val PKG = "kotlinx.serialization.json"
            val JSON_CLASS_DISCRIMINATOR = ClassName(PKG, "JsonClassDiscriminator")
        }

        object Modules {
            private const val PKG = "kotlinx.serialization.modules"
            val SERIALIZERS_MODULE = ClassName(PKG, "SerializersModule")
        }
    }

    // Kotlin标准库
    object Kotlin {
        val OPT_IN = ClassName("kotlin", "OptIn")
        val DSL_MARKER = ClassName("kotlin", "DslMarker")
    }

    // 自定义类型
    object SwcTypes {
        val SWC_DSL_MARKER = ClassName(PKG_TYPES, "SwcDslMarker")
        val NODE_SERIALIZER = ClassName(PKG_TYPES, "NodeSerializer")
    }
}
