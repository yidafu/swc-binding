package dev.yidafu.swc.generator.codegen.generator.types.stages

import dev.yidafu.swc.generator.codegen.generator.types.ImplementationEmitter
import dev.yidafu.swc.generator.codegen.generator.types.TypesGenerationContext
import dev.yidafu.swc.generator.codegen.pipeline.GenerationStage

class ImplementationStage(
    private val emitter: ImplementationEmitter
) : GenerationStage<TypesGenerationContext> {
    override fun run(context: TypesGenerationContext) {
        emitter.emit(
            builderSelector = { decl ->
                context.fileLayout.builderForLeafInterface(decl.name)
            },
            context.input.classDecls,
            context.analyzer,
            context.propertyCache,
            context.poet
        )
    }
}

