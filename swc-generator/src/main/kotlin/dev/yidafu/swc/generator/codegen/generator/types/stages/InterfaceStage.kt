package dev.yidafu.swc.generator.codegen.generator.types.stages

import dev.yidafu.swc.generator.codegen.generator.types.InterfaceEmitter
import dev.yidafu.swc.generator.codegen.generator.types.TypesGenerationContext
import dev.yidafu.swc.generator.codegen.pipeline.GenerationStage
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.util.Logger

class InterfaceStage(
    private val emitter: InterfaceEmitter
) : GenerationStage<TypesGenerationContext> {
    override fun run(context: TypesGenerationContext) {
        val interfaces = context.input.classDecls.filter {
            it.modifier == ClassModifier.Interface || it.modifier == ClassModifier.SealedInterface
        }
        Logger.debug("  接口数量: ${interfaces.size}", 4)
        val sorted = TypesStageUtils.sortByInheritance(interfaces)
        emitter.emit(
            builderSelector = { decl ->
                context.fileLayout.builderForInterfaceName(decl.name)
            },
            interfaces = sorted,
            poet = context.poet
        )
    }
}

