package dev.yidafu.swc.generator.core

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.stages.*
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.util.Logger

/**
 * 生成器管道
 *
 * 协调各个处理阶段完成代码生成。管道按顺序执行各个阶段，每个阶段的输出作为下一个阶段的输入。
 *
 * 默认管道包含以下阶段：
 * 1. [ParserStage] - 解析 TypeScript 文件
 * 2. [ExtractorStage] - 提取声明
 * 3. [AnalyzerStage] - 分析继承关系
 * 4. [ConverterStage] - 转换为 Kotlin 声明
 * 5. [ProcessorStage] - 处理 Kotlin 声明
 * 6. [CodeGenStage] - 生成代码文件
 *
 * @param configuration 生成器配置
 * @param container 依赖注入容器，提供各个阶段所需的组件
 */
class GeneratorPipeline(
    private val configuration: Configuration,
    private val container: DependencyContainer
) {

    private val stages = mutableListOf<Stage<*, *>>()

    /**
     * 添加处理阶段到管道
     *
     * @param stage 要添加的阶段
     * @return 返回自身以支持链式调用
     */
    fun addStage(stage: Stage<*, *>): GeneratorPipeline {
        stages.add(stage)
        return this
    }

    /**
     * 执行管道处理
     *
     * 按顺序执行所有阶段，每个阶段的输出作为下一个阶段的输入。
     * 如果任何阶段失败，管道会立即停止并返回错误结果。
     *
     * @param input 输入字符串，通常是 TypeScript 文件路径或内容
     * @return 处理结果，成功时返回 [GeneratorResult.success]，失败时返回包含错误信息的 [GeneratorResult.failure]
     */
    fun execute(input: String): GeneratorResult<Unit> {
        Logger.debug("开始执行生成器管道，共 ${stages.size} 个阶段")

        var currentInput: Any = input
        val context = PipelineContext(configuration)

        for ((index, stage) in stages.withIndex()) {
            Logger.debug("执行阶段 ${index + 1}/${stages.size}: ${stage.name}")

            @Suppress("UNCHECKED_CAST")
            val result = try {
                (stage as Stage<Any, Any>).execute(currentInput, context)
            } catch (e: Exception) {
                Logger.error("阶段 ${stage.name} 执行失败: ${e.message}")
                Logger.error("错误堆栈: ${e.stackTraceToString()}")
                return GeneratorResultFactory.failure(
                    code = ErrorCode.STAGE_EXECUTION_ERROR,
                    message = "Stage ${stage.name} (${index + 1}/${stages.size}) failed: ${e.message}",
                    cause = e
                )
            }

            if (result.isFailure()) {
                result.onFailure { error ->
                    Logger.error("阶段 ${stage.name} 返回错误: ${error.message}")
                }
                return result as GeneratorResult<Unit>
            }

            currentInput = result.getOrThrow()
        }

        Logger.success("生成器管道执行完成")
        return GeneratorResultFactory.success(Unit)
    }

    /**
     * 创建默认管道
     *
     * 创建一个包含所有默认阶段的生成器管道。
     *
     * @param configuration 生成器配置
     * @return 配置好的生成器管道实例
     */
    companion object {
        fun createDefault(configuration: Configuration): GeneratorPipeline {
            val container = DependencyContainer(configuration)
            return GeneratorPipeline(configuration, container)
                .addStage(ParserStage(configuration, container))
                .addStage(ExtractorStage(configuration, container))
                .addStage(AnalyzerStage(configuration))
                .addStage(ConverterStage(configuration, container))
                .addStage(ProcessorStage(configuration, container))
                .addStage(CodeGenStage(configuration, container))
        }
    }
}
