package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.AbstractStage
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.util.Logger

/**
 * 处理器阶段
 * 负责对 Kotlin 声明进行自定义处理
 */
class ProcessorStage(
    private val config: Configuration,
    private val container: DependencyContainer
) : AbstractStage<List<KotlinDeclaration>, List<KotlinDeclaration>>() {

    override val name: String = "Processor"

    override fun doExecute(input: List<KotlinDeclaration>, context: PipelineContext): GeneratorResult<List<KotlinDeclaration>> {
        Logger.debug("开始处理 Kotlin 声明")

        val processResult = container.kotlinADTProcessor.processDeclarations(input, container.swcGeneratorConfig)

        if (processResult.isFailure()) {
            processResult.onFailure { error ->
                Logger.error("处理器执行失败: ${error.message}")
            }
            return processResult
        }

        val processedDeclarations = processResult.getOrThrow()

        Logger.success("处理完成: ${processedDeclarations.size} 个声明")

        // 将处理结果存储到上下文中
        context.setMetadata("processedDeclarations", processedDeclarations)

        return GeneratorResultFactory.success(processedDeclarations)
    }
}
