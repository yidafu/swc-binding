package dev.yidafu.swc.generator.config

/**
 * 转换器规则配置
 * 管理 TypeScript 到 Kotlin 转换的相关规则
 */
object ConverterRulesConfig {
    /**
     * 需要跳过的类型别名
     */
    enum class SkippedTypeAlias(val value: String) {
        TO_SNAKE_CASE("ToSnakeCase"),
        TO_SNAKE_CASE_PROPERTIES("ToSnakeCaseProperties")
    }

    /**
     * 跳过的 TypeAlias 名称（无需生成）
     */
    val SKIPPED_TYPE_ALIASES: Set<String> = SkippedTypeAlias.values().map { it.value }.toSet()

    /**
     * 检查类型别名是否应该被跳过
     */
    fun shouldSkipTypeAlias(name: String): Boolean {
        return SKIPPED_TYPE_ALIASES.contains(name)
    }
}
