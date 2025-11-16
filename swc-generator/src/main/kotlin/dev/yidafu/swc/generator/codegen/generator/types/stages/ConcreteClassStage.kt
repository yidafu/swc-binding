package dev.yidafu.swc.generator.codegen.generator.types.stages

import dev.yidafu.swc.generator.codegen.generator.types.ConcreteClassEmitter
import dev.yidafu.swc.generator.codegen.generator.types.TypesGenerationContext
import dev.yidafu.swc.generator.codegen.pipeline.GenerationStage
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.util.Logger

class ConcreteClassStage(
    private val emitter: ConcreteClassEmitter
) : GenerationStage<TypesGenerationContext> {
    override fun run(context: TypesGenerationContext) {
        val classes = context.input.classDecls.filter {
            it.modifier !is ClassModifier.Interface &&
                it.modifier !is ClassModifier.SealedInterface &&
                !it.name.endsWith("Impl")
        }
        Logger.debug("  其他类数量: ${classes.size}", 4)
        emitter.emit(context.fileLayout.commonFileBuilder, classes, context.poet, context.declLookup)
    }
}

