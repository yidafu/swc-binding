package dev.yidafu.swc.generator.config

/**
 * 接口规则配置
 * 管理接口转换相关的规则
 */
object InterfaceRulesConfig {
    /**
     * 需要跳过的接口及其原因
     */
    enum class SkippedInterface(val value: String, val reason: String) {
        EXPR_OR_SPREAD("ExprOrSpread", "统一使用 Argument"),
        OPTIONAL_CHAINING_CALL("OptionalChainingCall", "只保留 CallExpression")
    }

    /**
     * 类型引用替换映射
     */
    enum class TypeReferenceReplacement(val from: String, val to: String) {
        OPTIONAL_CHAINING_CALL("OptionalChainingCall", "CallExpression")
    }

    /**
     * 根鉴别器映射
     */
    enum class RootDiscriminatorInterface(val value: String, val discriminator: String) {
        NODE("Node", SerializerConfig.DEFAULT_DISCRIMINATOR),
        CONFIG("Config", SerializerConfig.SYNTAX_DISCRIMINATOR),
        PARSER_CONFIG("ParserConfig", SerializerConfig.SYNTAX_DISCRIMINATOR),
        OPTIONS("Options", SerializerConfig.SYNTAX_DISCRIMINATOR)
    }

    /**
     * 需要跳过的接口名称及其原因
     */
    val SKIPPED_INTERFACES: Map<String, String> = SkippedInterface.values()
        .associate { it.value to it.reason }

    /**
     * 类型引用替换映射
     */
    val TYPE_REFERENCE_REPLACEMENTS: Map<String, String> = TypeReferenceReplacement.values()
        .associate { it.from to it.to }

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
        return RootDiscriminatorInterface.values()
            .find { it.value == mappedInterfaceName }
            ?.discriminator
    }
}

