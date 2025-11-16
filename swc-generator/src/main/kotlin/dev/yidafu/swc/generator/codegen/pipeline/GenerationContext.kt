package dev.yidafu.swc.generator.codegen.pipeline

/**
 * 通用生成上下文。各生成器在上下文中收集 `GeneratedFile`，
 * 由管线尾部统一写入。
 */
interface GenerationContext {
    val generatedFiles: MutableList<GeneratedFile>
}
