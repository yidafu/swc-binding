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
 * 协调各个处理阶段完成代码生成
 */
class GeneratorPipeline(
    private val configuration: Configuration,
    private val container: DependencyContainer
) {
    
    private val stages = mutableListOf<Stage<*, *>>()
    
    /**
     * 添加处理阶段
     */
    fun addStage(stage: Stage<*, *>): GeneratorPipeline {
        stages.add(stage)
        return this
    }
    
    /**
     * 执行管道处理
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
