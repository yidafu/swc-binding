package dev.yidafu.swc.generator.config

/**
 * Union 类型配置
 * 管理 Union 类型相关的配置
 */
object UnionConfig {
    /**
     * 命名 token 中是否包含可空性标记（默认关闭，保持兼容）
     */
    @JvmStatic
    var includeNullabilityInToken: Boolean = false

    /**
     * 缓存 key 中是否包含可空性标记（默认关闭，保持兼容）
     */
    @JvmStatic
    var includeNullabilityInKey: Boolean = false

    /**
     * 需要固化为 object 的高频组合（字符串化 key，生成器内部解释）
     * 约定 key: "${kind}|${args-joined-by-','}|arr=${isArray}"
     */
    @JvmStatic
    val hotFixedCombos: MutableSet<String> = linkedSetOf()

    /**
     * 生成的工厂支持的元数（默认 2..5）
     */
    @JvmStatic
    var factoryArity: Set<Int> = setOf(2, 3, 4, 5)

    /**
     * 验证配置
     */
    fun validate() {
        require(factoryArity.isNotEmpty()) { "factoryArity 不能为空" }
        require(factoryArity.all { it >= 2 && it <= 10 }) { "factoryArity 的值必须在 2-10 之间" }
    }
}

