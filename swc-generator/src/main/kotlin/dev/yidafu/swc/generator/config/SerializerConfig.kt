package dev.yidafu.swc.generator.config

/**
 * 序列化器配置
 * 管理序列化相关的配置
 */
object SerializerConfig {
    /**
     * 鉴别器类型
     */
    enum class Discriminator(val value: String) {
        DEFAULT("type"),
        SYNTAX("syntax")
    }

    /**
     * 缺失 @Serializable 注解时的处理策略
     */
    enum class MissingSerializablePolicy {
        ERROR, // 一律抛错
        WARN_OPEN_BASES, // 白名单开放父接口仅告警，其他抛错
        WARN_ALL // 全部仅告警
    }

    /**
     * 允许作为多态父接口的开放接口名称
     */
    enum class OpenBaseInterface(val value: String) {
        NODE("Node"),
        MODULE_ITEM("ModuleItem"),
        MODULE_DECLARATION("ModuleDeclaration"),
        IDENTIFIER("Identifier"),
        BINDING_IDENTIFIER("BindingIdentifier"),
        VARIABLE_DECLARATOR("VariableDeclarator"),
        MODULE("Module"),
        SCRIPT("Script"),
        // 中间接口（非 sealed），需要显式添加到 additionalOpenBases
        // Declaration、Expression、Statement、Pattern 是 sealed interface，会自动包含
        EXPRESSION_BASE("ExpressionBase"),
        FN("Fn"),
        PATTERN_BASE("PatternBase")
    }

    /**
     * 配置接口名称
     */
    enum class ConfigInterface(val value: String) {
        BASE_PARSE_OPTIONS("BaseParseOptions"),
        PARSER_CONFIG("ParserConfig"),
        TS_PARSER_CONFIG("TsParserConfig"),
        ES_PARSER_CONFIG("EsParserConfig"),
        OPTIONS("Options"),
        CONFIG("Config")
    }

    // 向后兼容的常量
    val DEFAULT_DISCRIMINATOR: String = Discriminator.DEFAULT.value
    val SYNTAX_DISCRIMINATOR: String = Discriminator.SYNTAX.value

    /**
     * 允许作为多态父接口的开放接口白名单（非 sealed）
     */
    @JvmStatic
    val additionalOpenBases: MutableSet<String> = linkedSetOf(
        *OpenBaseInterface.values().map { it.value }.toTypedArray()
    )

    /**
     * 缺失 @Serializable 注解时的处理策略
     */
    @JvmStatic
    var missingSerializablePolicy: MissingSerializablePolicy = MissingSerializablePolicy.WARN_OPEN_BASES

    /**
     * 配置接口名称集合
     */
    val configInterfaceNames: Set<String> = ConfigInterface.values().map { it.value }.toSet()

    /**
     * 接口名到实现类名的映射
     * 在 polymorphic 注册时，这些接口类型会使用对应的 Impl 类型
     * 键：接口名（如 "Identifier"）
     * 值：实现类名（如 "IdentifierImpl"）
     */
    val interfaceToImplMap: Map<String, String> = mapOf(
        "Identifier" to "IdentifierImpl",
        "BindingIdentifier" to "BindingIdentifierImpl",
        "TemplateLiteral" to "TemplateLiteralImpl",
        "TsTemplateLiteralType" to "TsTemplateLiteralTypeImpl"
    )
}
