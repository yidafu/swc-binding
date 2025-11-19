package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.AbstractStage
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.util.Logger

/**
 * 转换阶段
 * 负责将 TypeScript 声明转换为 Kotlin 声明
 */
class ConverterStage(
    private val config: Configuration,
    private val container: DependencyContainer
) : AbstractStage<List<TypeScriptDeclaration>, List<KotlinDeclaration>>() {

    override val name: String = "Converter"

    override fun doExecute(input: List<TypeScriptDeclaration>, context: PipelineContext): GeneratorResult<List<KotlinDeclaration>> {
        Logger.debug("开始转换 TypeScript 声明为 Kotlin 声明")

        val result = container.typeScriptToKotlinConverter.convertDeclarations(input)

        result.onSuccess { kotlinDeclarations ->
            Logger.success("转换完成: ${kotlinDeclarations.size} 个 Kotlin 声明")
            // 将转换结果存储到上下文中
            context.setMetadata("kotlinDeclarations", kotlinDeclarations)
        }

        return result
    }
}
