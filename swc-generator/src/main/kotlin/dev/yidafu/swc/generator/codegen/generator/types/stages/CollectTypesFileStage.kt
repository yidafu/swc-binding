package dev.yidafu.swc.generator.codegen.generator.types.stages

import dev.yidafu.swc.generator.codegen.generator.types.TypesGenerationContext
import dev.yidafu.swc.generator.codegen.generator.types.TypesPostProcessor
import dev.yidafu.swc.generator.codegen.pipeline.GenerationStage
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFile
import dev.yidafu.swc.generator.util.Logger

class CollectTypesFileStage(
    private val postProcessor: TypesPostProcessor
) : GenerationStage<TypesGenerationContext> {
    override fun run(context: TypesGenerationContext) {
        val generatedFiles = context.fileLayout.collectGeneratedFiles(context.poet, postProcessor)
        context.generatedFiles += generatedFiles

        generatedFiles.forEach { file ->
            val lineCount = file.fileSpec?.toString()?.lines()?.size ?: 1
            Logger.success("Generated: ${file.outputPath} ($lineCount 行)")
        }
    }
}

