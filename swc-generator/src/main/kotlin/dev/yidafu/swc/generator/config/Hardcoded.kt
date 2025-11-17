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
        /**
         * 允许作为多态父接口的开放接口白名单（非 sealed）
         */
        @JvmStatic
        val additionalOpenBases: MutableSet<String> = linkedSetOf(
            // 常见作为外部 API 入口或需要直接反序列化的非 sealed 基类
            "Node",
            "ModuleItem",
            "ModuleDeclaration",
            "Identifier",
            "BindingIdentifier",
            "VariableDeclarator",
            "Module",
            "Script"
        )

        /**
         * 缺失 @Serializable 注解时的处理策略
         */
        enum class MissingSerializablePolicy {
            ERROR,            // 一律抛错
            WARN_OPEN_BASES,  // 白名单开放父接口仅告警，其他抛错
            WARN_ALL          // 全部仅告警
        }
        @JvmStatic
        var missingSerializablePolicy: MissingSerializablePolicy = MissingSerializablePolicy.WARN_OPEN_BASES
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
     * Union 相关的默认配置（可被外部配置接入覆盖）
     */
    object Union {
        // 命名 token 中是否包含可空性标记（默认关闭，保持兼容）
        @JvmStatic
        var includeNullabilityInToken: Boolean = false
        // 缓存 key 中是否包含可空性标记（默认关闭，保持兼容）
        @JvmStatic
        var includeNullabilityInKey: Boolean = false

        // 需要固化为 object 的高频组合（字符串化 key，生成器内部解释）
        // 约定 key: "${kind}|${args-joined-by-','}|arr=${isArray}"
        @JvmStatic
        val hotFixedCombos: MutableSet<String> = linkedSetOf()

        // 生成的工厂支持的元数（默认 2..5）
        @JvmStatic
        var factoryArity: Set<Int> = setOf(2, 3, 4, 5)
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
     * 需要独立 ctxt 字段的类
     */
    object CtxtFields {
        /**
         * 需要自动添加 ctxt 字段的类名列表
         * 这些类对应 Rust 端包含独立的 ctxt: SyntaxContext 字段的结构体
         * 注意：ctxt 不是 Span 的一部分，而是这些结构体的独立字段
         */
        val CLASSES_WITH_CTXT = setOf(
            // 语句相关
            "BlockStatement",       // 对应 Rust BlockStmt 结构体
            
            // 表达式相关
            "CallExpression",       // 对应 Rust CallExpr 结构体
            "NewExpression",        // 对应 Rust NewExpr 结构体
            "ArrowFunctionExpression",  // 对应 Rust ArrowExpr 结构体
            "TaggedTemplateExpression", // 对应 Rust TaggedTpl 结构体
            
            // 声明相关
            "FunctionDeclaration",  // 对应 Rust Function 结构体（通过 FnDecl 展开）
            "VariableDeclaration",  // 对应 Rust VarDecl 结构体
            
            // 类相关
            "Class",                // 对应 Rust Class 结构体
            "PrivateProperty",      // 对应 Rust PrivateProp 结构体
            "Constructor",          // 对应 Rust Constructor 结构体
            
            // 标识符相关
            "Identifier"            // 对应 Rust Ident 结构体
        )
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

    /**
     * 接口转换相关规则
     */
    object InterfaceRules {
        /**
         * 需要跳过的接口名称及其原因
         * 这些接口在转换时会被跳过，使用替代方案
         */
        val SKIPPED_INTERFACES: Map<String, String> = mapOf(
            "ExprOrSpread" to "统一使用 Argument",
            "OptionalChainingCall" to "只保留 CallExpression"
        )

        /**
         * 检查接口是否应该被跳过
         */
        fun shouldSkipInterface(interfaceName: String): Boolean {
            return SKIPPED_INTERFACES.containsKey(interfaceName)
        }

        /**
         * 获取跳过接口的原因
         */
        fun getSkipReason(interfaceName: String): String? {
            return SKIPPED_INTERFACES[interfaceName]
        }

        /**
         * 类型引用替换映射
         * 在转换联合类型时，某些类型引用需要被替换为其他类型
         */
        val TYPE_REFERENCE_REPLACEMENTS: Map<String, String> = mapOf(
            "OptionalChainingCall" to "CallExpression"
        )

        /**
         * 替换类型引用（如果需要）
         */
        fun replaceTypeReference(typeName: String): String {
            return TYPE_REFERENCE_REPLACEMENTS[typeName] ?: typeName
        }

        /**
         * 根据接口名称确定根鉴别器
         * 返回 null 表示该接口不需要根鉴别器
         */
        fun getRootDiscriminator(mappedInterfaceName: String): String? {
            return when (mappedInterfaceName) {
                // AST 根
                "Node" -> Serializer.DEFAULT_DISCRIMINATOR
                // Config 体系根
                "Config", "ParserConfig", "Options" -> Serializer.SYNTAX_DISCRIMINATOR
                else -> null
            }
        }
    }
}


