package dev.yidafu.swc.generator.config

/**
 * 全局配置管理器
 * 单例模式，确保配置只加载一次
 */
object GlobalConfig {
    /**
     * 全局配置实例
     * 使用 lazy 确保只加载一次
     */
    private var _config: SwcGeneratorConfig? = null

    val config
        get() = requireNotNull(_config) { "配置未初始化" }

    fun updateConfig(configPath: String?) {
        _config = ConfigLoader.loadConfig(configPath)
    }

    /**
     * 重新加载配置（用于测试或动态更新）
     */
    fun reload(configPath: String?) {
        updateConfig(configPath)
    }
}
