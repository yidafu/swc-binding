package dev.yidafu.swc.generator.config

/**
 * 接口启发式配置
 * 推断接口父类型时的常见后缀
 */
object InterfaceHeuristicsConfig {
    /**
     * 接口后缀
     */
    enum class InterfaceSuffix(val value: String) {
        INTERFACE("Interface"),
        OPTIONS("Options"),
        CONFIG("Config")
    }

    /**
     * 推断接口父类型时的常见后缀
     */
    val interfaceSuffixes: Set<String> = InterfaceSuffix.values().map { it.value }.toSet()
}

