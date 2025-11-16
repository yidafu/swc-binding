package dev.yidafu.swc.generator.codegen.pipeline

/**
 * 简单的阶段式生成管线。
 */
class GenerationPipeline<C : GenerationContext>(
    private val stages: List<GenerationStage<C>>
) {
    fun execute(context: C) {
        stages.forEach { stage -> stage.run(context) }
    }
}
