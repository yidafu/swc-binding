package dev.yidafu.swc.generator.processor

import dev.yidafu.swc.generator.config.SwcGeneratorConfig

/**
 * Kotlin ADT 处理器工厂
 * * 根据配置创建相应的处理器实例
 */
object KotlinADTProcessorFactory {

    /**
     * 创建处理器实例
     * * @param config 生成器配置
     * @return 处理器实例
     */
    fun createProcessor(config: SwcGeneratorConfig): KotlinADTProcessor {
        // 目前返回默认实现
        // 未来可以根据配置选择不同的处理器
        return DefaultKotlinADTProcessorImpl()
    }

    /**
     * 创建实现类生成处理器
     * * @return 实现类生成处理器实例
     */
    fun createCodeGenerationProcessor(): KotlinADTProcessor {
        return CodeGenerationProcessor()
    }

    /**
     * 创建组合处理器（默认处理 + 实现类生成）
     * * @param config 生成器配置
     * @return 组合处理器实例
     */
    fun createCombinedProcessor(
        config: SwcGeneratorConfig
    ): KotlinADTProcessor {
        return CombinedKotlinADTProcessor(
            DefaultKotlinADTProcessorImpl(),
            CodeGenerationProcessor()
        )
    }

    /**
     * 创建空处理器（不进行任何处理）
     * * @return 空处理器实例
     */
    fun createEmptyProcessor(): KotlinADTProcessor {
        return DefaultKotlinADTProcessor()
    }
}
