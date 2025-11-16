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
        val allInterfaces = context.input.classDecls.filter {
            it.modifier == ClassModifier.Interface || it.modifier == ClassModifier.SealedInterface
        }
        // 新策略：所有接口（包含叶子）都生成接口声明文件
        Logger.debug("  接口数量(含叶子): ${allInterfaces.size}", 4)
        val sorted = TypesStageUtils.sortByInheritance(allInterfaces)
        emitter.emit(
            builderSelector = { decl ->
                context.fileLayout.builderForInterfaceName(decl.name)
            },
            interfaces = sorted,
            poet = context.poet
        )
    }
}

