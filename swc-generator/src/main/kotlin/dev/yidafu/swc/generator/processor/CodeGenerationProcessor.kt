package dev.yidafu.swc.generator.processor

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.model.kotlin.*
import dev.yidafu.swc.generator.model.typescript.InheritanceAnalysisCache
import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.util.ImplementationClassGenerator
import dev.yidafu.swc.generator.util.Logger

/**
 * 实现类生成处理器
 * * 负责为接口生成对应的实现类（如 AbcImpl）
 * 修改 Kotlin ADT，添加实现类声明
 */
class CodeGenerationProcessor : KotlinADTProcessor {

    override fun processDeclarations(
        declarations: List<KotlinDeclaration>,
        config: SwcGeneratorConfig
    ): GeneratorResult<List<KotlinDeclaration>> {
        return try {
            Logger.debug("开始生成实现类...")

            // 分离类声明
            val classDecls = declarations.filterIsInstance<KotlinDeclaration.ClassDecl>()

            Logger.debug("准备为接口生成实现类，接口数量: ${classDecls.size}")

            // 为接口生成实现类
            val implementationClasses = generateImplementationClasses(classDecls)

            // 合并原始声明和新的实现类
            val allDeclarations = mutableListOf<KotlinDeclaration>()
            allDeclarations.addAll(declarations)
            allDeclarations.addAll(implementationClasses)

            Logger.success("实现类生成完成，新增 ${implementationClasses.size} 个实现类")
            GeneratorResultFactory.success(allDeclarations)
        } catch (e: Exception) {
            Logger.error("实现类生成失败: ${e.message}")
            GeneratorResultFactory.failure(ErrorCode.CODE_GENERATION_ERROR, "实现类生成失败", e)
        }
    }

    override fun processDeclaration(
        declaration: KotlinDeclaration,
        config: SwcGeneratorConfig
    ): GeneratorResult<KotlinDeclaration> {
        // 实现类生成处理器不需要处理单个声明
        return GeneratorResultFactory.success(declaration)
    }

    /**
     * 为接口生成实现类
     * 只有 Node 继承树的叶子节点生成实现类
     * 使用缓存优化性能
     */
    private fun generateImplementationClasses(classDecls: List<KotlinDeclaration.ClassDecl>): List<KotlinDeclaration.ClassDecl> {
        val analyzer = InheritanceAnalyzer()

        // 只处理接口
        val interfaces = classDecls.filter { it.modifier == ClassModifier.Interface }

        // 创建继承分析缓存
        val cache = InheritanceAnalysisCache(analyzer, interfaces)

        // 使用共享工具类生成实现类
        return ImplementationClassGenerator.generateImplementationClasses(interfaces, cache, classDecls)
    }
}
