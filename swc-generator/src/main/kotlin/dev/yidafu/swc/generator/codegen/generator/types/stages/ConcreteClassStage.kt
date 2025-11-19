package dev.yidafu.swc.generator.codegen.generator.types.stages

import dev.yidafu.swc.generator.codegen.generator.types.ConcreteClassEmitter
import dev.yidafu.swc.generator.codegen.generator.types.TypesGenerationContext
import dev.yidafu.swc.generator.codegen.pipeline.GenerationStage
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.util.DebugUtils.findDebugTypes
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.util.NameUtils.clean

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

        // 调试：检查调试类型
        val debugTypes = findDebugTypes(classes)
        debugTypes.forEach { (typeName, decl) ->
            Logger.info("  ConcreteClassStage 找到 $typeName: modifier=${decl.modifier}, name=${decl.name}", 4)
        }
        if (debugTypes.isEmpty() && classes.isNotEmpty()) {
            Logger.debug("  ConcreteClassStage 未找到调试类型，总类数: ${classes.size}", 4)
        }

        // 每个类独立文件生成
        classes.forEach { cls ->
            val cleanName = clean(cls.name)
            val builder = context.fileLayout.builderForClassName(cls.name)
            emitter.emit(builder, listOf(cls), context.poet, context.declLookup)
        }
    }
}
