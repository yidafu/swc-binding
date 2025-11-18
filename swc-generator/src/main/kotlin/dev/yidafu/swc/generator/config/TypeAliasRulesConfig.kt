package dev.yidafu.swc.generator.config

/**
 * 类型别名规则配置
 * 管理类型别名相关的规则
 */
object TypeAliasRulesConfig {
    /**
     * 强制映射为 String 的类型别名
     */
    enum class ForceStringAlias(val value: String) {
        TERSER_ECMA_VERSION("TerserEcmaVersion")
    }

    /**
     * 强制可空的接口
     */
    enum class ForceNullableInterface(val value: String) {
        WASM_ANALYSIS_OPTIONS("WasmAnalysisOptions")
    }

    /**
     * 这些别名直接映射为 String
     */
    val FORCE_STRING_ALIASES: Set<String> = ForceStringAlias.values().map { it.value }.toSet()

    /**
     * 这些接口在由 TypeLiteral 转接口时，强制所有属性可空（历史兼容/生成器策略）
     */
    private val FORCE_NULLABLE_INTERFACES: Set<String> = ForceNullableInterface.values().map { it.value }.toSet()

    /**
     * 检查是否为强制 String 的类型别名
     */
    fun isForceStringAlias(aliasName: String): Boolean {
        return FORCE_STRING_ALIASES.contains(aliasName)
    }

    /**
     * 检查接口是否需要强制可空
     */
    fun forceNullableForInterface(interfaceName: String): Boolean {
        return FORCE_NULLABLE_INTERFACES.contains(interfaceName)
    }
}
