package dev.yidafu.swc.generator.codegen.generator.types.stages

import dev.yidafu.swc.generator.codegen.generator.types.TypeAliasEmitter
import dev.yidafu.swc.generator.codegen.generator.types.TypesGenerationContext
import dev.yidafu.swc.generator.codegen.pipeline.GenerationStage

class TypeAliasStage(
    private val emitter: TypeAliasEmitter
) : GenerationStage<TypesGenerationContext> {
    override fun run(context: TypesGenerationContext) {
        emitter.emit(context.fileLayout.commonFileBuilder, context.input.typeAliases, context.poet)
    }
}
