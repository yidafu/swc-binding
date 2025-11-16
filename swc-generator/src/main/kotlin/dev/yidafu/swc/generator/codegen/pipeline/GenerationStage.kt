package dev.yidafu.swc.generator.codegen.pipeline

/**
 * 生成阶段。每个阶段接收并修改上下文。
 */
fun interface GenerationStage<C : GenerationContext> {
    fun run(context: C)
}
