package dev.yidafu.swc.generator.config

import com.squareup.kotlinpoet.ClassName

/**
 * 集中管理生成器中的硬编码常量与映射
 */
object Hardcoded {
    object AnnotationNames {
        val SERIAL_NAME = "SerialName"
        val SERIALIZABLE = "Serializable"
        val JSON_CLASS_DISCRIMINATOR = "JsonClassDiscriminator"
        val TRANSIENT = "Transient"
        val ENCODE_DEFAULT = "EncodeDefault"
        val OPT_IN = "OptIn"
        val CONTEXTUAL = "Contextual"

        fun toClassName(name: String): ClassName = when (name) {
            SERIAL_NAME -> ClassName("kotlinx.serialization", "SerialName")
            SERIALIZABLE -> ClassName("kotlinx.serialization", "Serializable")
            JSON_CLASS_DISCRIMINATOR -> ClassName("kotlinx.serialization.json", "JsonClassDiscriminator")
            TRANSIENT -> ClassName("kotlinx.serialization", "Transient")
            ENCODE_DEFAULT -> ClassName("kotlinx.serialization", "EncodeDefault")
            OPT_IN -> ClassName("kotlin", "OptIn")
            CONTEXTUAL -> ClassName("kotlinx.serialization", "Contextual")
            else -> ClassName("", name)
        }
    }

    object InterfaceHeuristics {
        // 推断接口父类型时的常见后缀
        val interfaceSuffixes: Set<String> = setOf("Interface", "Options", "Config")
    }

    object Collections {
        // PoetExtensions 使用的基础集合类型映射
        val MAP = ClassName("kotlin", "Map")
        val LIST = ClassName("kotlin", "List")
        val SET = ClassName("kotlin", "Set")
        val MUTABLE_MAP = ClassName("kotlin.collections", "MutableMap")
        val MUTABLE_LIST = ClassName("kotlin.collections", "MutableList")
        val MUTABLE_SET = ClassName("kotlin.collections", "MutableSet")
    }

    object Serializer {
        const val DEFAULT_DISCRIMINATOR: String = "type"
        const val SYNTAX_DISCRIMINATOR: String = "syntax"
        val configInterfaceNames: Set<String> = setOf(
            "BaseParseOptions",
            "ParserConfig",
            "TsParserConfig",
            "EsParserConfig",
            "Options",
            "Config"
        )
    }

    /**
     * 属性规则与常量
     */
    object PropertyRules {
        const val TYPE = "type"
        const val SYNTAX = "syntax"
        const val SPAN = "span"
        val SPAN_COORDINATES = setOf("start", "end", "ctxt")
        private val RESERVED_WORDS = setOf(
            "object", "inline", "in", "super", "class", "interface", "fun",
            "val", "var", "when", "is", "as", "import", "package"
        )

        fun isTypeProperty(name: String): Boolean = name.removeSurrounding("`") == TYPE
        fun isSyntaxProperty(name: String): Boolean = name.removeSurrounding("`") == SYNTAX
        fun isSpanProperty(name: String): Boolean = name.removeSurrounding("`") == SPAN
        fun isSpanCoordinate(name: String): Boolean = name.removeSurrounding("`") in SPAN_COORDINATES
        fun isSpanCoordinateProperty(interfaceName: String, propertyName: String): Boolean {
            return interfaceName == "Span" && isSpanCoordinate(propertyName)
        }
        fun wrapReservedWord(name: String): String {
            return if (RESERVED_WORDS.contains(name.lowercase())) {
                "`$name`"
            } else {
                name
            }
        }
    }

    /**
     * 类型别名规则与特殊映射
     */
    object TypeAliasRules {
        // 这些别名直接映射为 String
        val FORCE_STRING_ALIASES: Set<String> = setOf(
            "TerserEcmaVersion"
        )

        fun isForceStringAlias(aliasName: String): Boolean = FORCE_STRING_ALIASES.contains(aliasName)

        // 这些接口在由 TypeLiteral 转接口时，强制所有属性可空（历史兼容/生成器策略）
        private val FORCE_NULLABLE_INTERFACES: Set<String> = setOf(
            "WasmAnalysisOptions"
        )
        fun forceNullableForInterface(interfaceName: String): Boolean =
            FORCE_NULLABLE_INTERFACES.contains(interfaceName)
    }

    /**
     * 顶层转换器（TypeScript -> Kotlin）相关规则
     */
    object ConverterRules {
        // 跳过的 TypeAlias 名称（无需生成）
        val SKIPPED_TYPE_ALIASES: Set<String> = setOf(
            "ToSnakeCase",
            "ToSnakeCaseProperties"
        )
        fun shouldSkipTypeAlias(name: String): Boolean = SKIPPED_TYPE_ALIASES.contains(name)
    }
}


