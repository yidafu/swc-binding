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

        // 调试：检查 ForOfStatement 和 ComputedPropName
        val forOfStatement = classes.find { it.name.removeSurrounding("`") == "ForOfStatement" }
        val computedPropName = classes.find { it.name.removeSurrounding("`") == "ComputedPropName" }
        if (forOfStatement != null) {
            Logger.info("  ConcreteClassStage 找到 ForOfStatement: modifier=${forOfStatement.modifier}, name=${forOfStatement.name}", 4)
        } else {
            Logger.warn("  ConcreteClassStage 未找到 ForOfStatement，总类数: ${classes.size}")
            Logger.warn("  所有类名: ${classes.map { it.name.removeSurrounding("`") }.take(20).joinToString(", ")}")
        }
        if (computedPropName != null) {
            Logger.info("  ConcreteClassStage 找到 ComputedPropName: modifier=${computedPropName.modifier}, name=${computedPropName.name}", 4)
        } else {
            Logger.warn("  ConcreteClassStage 未找到 ComputedPropName，总类数: ${classes.size}")
        }

        // 每个类独立文件生成
        classes.forEach { cls ->
            val cleanName = cls.name.removeSurrounding("`")
            if (cleanName == "ForOfStatement" || cleanName == "ComputedPropName") {
                println("  [DEBUG] ConcreteClassStage: 处理类 $cleanName (${cls.name}), modifier=${cls.modifier}")
            }
            val builder = context.fileLayout.builderForClassName(cls.name)
            if (cleanName == "ForOfStatement" || cleanName == "ComputedPropName") {
                println("  [DEBUG] ConcreteClassStage: 获取 builder 成功，开始 emit")
            }
            emitter.emit(builder, listOf(cls), context.poet, context.declLookup)
            if (cleanName == "ForOfStatement" || cleanName == "ComputedPropName") {
                println("  [DEBUG] ConcreteClassStage: emit 完成")
            }
        }
    }
}
