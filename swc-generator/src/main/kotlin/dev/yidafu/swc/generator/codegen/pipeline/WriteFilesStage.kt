package dev.yidafu.swc.generator.codegen.pipeline

/**
 * 通用写入阶段。
 */
class WriteFilesStage<C : GenerationContext>(
    private val writer: GeneratedFileWriter,
    private val formatterOverride: ((String, java.nio.file.Path) -> String)? = null
) : GenerationStage<C> {
    override fun run(context: C) {
        writer.write(context.generatedFiles, formatterOverride)
    }
}
