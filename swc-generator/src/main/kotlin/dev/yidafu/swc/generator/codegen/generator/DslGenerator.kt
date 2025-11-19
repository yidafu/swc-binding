package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.codegen.generator.dsl.DslExtensionCollector
import dev.yidafu.swc.generator.codegen.generator.dsl.DslFileEmitter
import dev.yidafu.swc.generator.codegen.generator.dsl.DslModelContext
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFileWriter
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.util.Logger
import java.nio.file.Path
import java.nio.file.Paths

/**
 * DSL 代码生成器
 *
 * 分层架构的协调器，负责生成 DSL 扩展函数文件。
 *
 * 工作流程：
 * 1. 创建 DSL 模型上下文（[DslModelContext]）
 * 2. 收集 DSL 扩展函数（[DslExtensionCollector]）
 * 3. 生成 DSL 文件（[DslFileEmitter]）
 * 4. 写入文件系统
 *
 * @param classDecls 类声明列表，用于分析继承关系和属性
 * @param classAllPropertiesMap 类名到所有属性（包括继承的）的映射
 * @param writer 文件写入器，默认为 [GeneratedFileWriter]
 * @param poetGenerator KotlinPoet 生成器，默认为 [DefaultPoetGenerator]
 */
class DslGenerator(
    private val classDecls: List<KotlinDeclaration.ClassDecl>,
    private val classAllPropertiesMap: Map<String, List<KotlinDeclaration.PropertyDecl>>,
    writer: GeneratedFileWriter = GeneratedFileWriter(),
    private val poetGenerator: PoetGenerator = DefaultPoetGenerator()
) : BaseGenerator(writer) {
    private val modelContext = DslModelContext(classDecls, classAllPropertiesMap)
    private val collector = DslExtensionCollector(modelContext)
    private val emitter = DslFileEmitter(modelContext, poetGenerator)

    /**
     * 生成 DSL 文件并写入目录
     *
     * 执行完整的 DSL 生成流程：
     * 1. 准备输出目录（如果存在则清空）
     * 2. 收集所有 DSL 扩展函数
     * 3. 生成 DSL 文件
     * 4. 写入文件系统
     *
     * @param outputDir 输出目录路径
     */
    fun writeToDirectory(outputDir: String) {
        val outputPath = Paths.get(outputDir)
        prepareOutputDir(outputPath)
        val collection = collector.collect()
        val files = emitter.emit(collection, outputPath)
        writer.write(files)
        Logger.success("Generated DSL files in: $outputDir (${files.size} 个文件)")
    }

    /**
     * 准备输出目录
     *
     * 如果目录已存在，则递归删除其所有内容；然后创建目录。
     *
     * @param path 输出目录路径
     */
    private fun prepareOutputDir(path: Path) {
        val dir = path.toFile()
        if (dir.exists()) {
            dir.deleteRecursively()
        }
        dir.mkdirs()
    }
}
