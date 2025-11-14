package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.AbstractStage
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.transformer.TransformResult
import dev.yidafu.swc.generator.util.Logger

/**
 * 代码生成阶段
 * 负责生成最终的 Kotlin 代码文件
 */
class CodeGenStage(
    private val config: Configuration,
    private val container: DependencyContainer
) : AbstractStage<List<KotlinDeclaration>, Unit>() {

    override val name: String = "CodeGen"

    override fun doExecute(input: List<KotlinDeclaration>, context: PipelineContext): GeneratorResult<Unit> {
        Logger.debug("开始生成 Kotlin 代码文件")

        // 从上下文中获取处理后的声明
        val processedDeclarations = context.getMetadata<List<KotlinDeclaration>>("processedDeclarations") ?: input

        // 分离类声明和类型别名
        val classDecls = processedDeclarations.filterIsInstance<KotlinDeclaration.ClassDecl>()
        val typeAliases = processedDeclarations.filterIsInstance<KotlinDeclaration.TypeAliasDecl>()

        // 创建属性映射（从上下文中获取或创建空的）
        val classAllPropertiesMap = context.getMetadata<Map<String, List<KotlinDeclaration.PropertyDecl>>>("classAllPropertiesMap") ?: emptyMap()

        // 创建TransformResult
        val transformResult = TransformResult(
            classDecls = classDecls,
            classAllPropertiesMap = classAllPropertiesMap,
            typeAliases = typeAliases
        )

        // 使用CodeEmitter生成代码
        val result = container.codeEmitter.emit(transformResult)

        if (result.isFailure()) {
            result.onFailure { error ->
                Logger.error("代码生成失败: ${error.message}")
            }
            return result
        }

        Logger.success("代码生成完成")
        return GeneratorResultFactory.success(Unit)
    }
}
