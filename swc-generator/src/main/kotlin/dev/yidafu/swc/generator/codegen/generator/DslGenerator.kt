package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.codegen.generator.dsl.DslExtensionCollector
import dev.yidafu.swc.generator.codegen.generator.dsl.DslFileEmitter
import dev.yidafu.swc.generator.codegen.generator.dsl.DslModelContext
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFileWriter
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.util.Logger
import java.nio.file.Files
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
     * 1. 确保输出目录存在
     * 2. 收集所有 DSL 扩展函数
     * 3. 生成 DSL 文件
     * 4. 清理不需要的旧文件
     * 5. 写入文件系统（GeneratedFileWriter 会处理内容比较，避免不必要的写入）
     *
     * @param outputDir 输出目录路径
     */
    fun writeToDirectory(outputDir: String) {
        val outputPath = Paths.get(outputDir)
        ensureOutputDir(outputPath)
        val collection = collector.collect()
        val files = emitter.emit(collection, outputPath)

        // 清理不在生成列表中的旧文件
        cleanupOldFiles(outputPath, files.map { it.outputPath.fileName.toString() }.toSet())

        // 写入文件（GeneratedFileWriter 会处理内容比较）
        writer.write(files)
        Logger.success("Generated DSL files in: $outputDir (${files.size} 个文件)")
    }

    /**
     * 确保输出目录存在
     *
     * 如果目录不存在，则创建目录。
     *
     * @param path 输出目录路径
     */
    private fun ensureOutputDir(path: Path) {
        Files.createDirectories(path)
    }

    /**
     * 清理不需要的旧文件
     *
     * 删除输出目录中不在生成文件列表中的 .kt 文件。
     *
     * @param outputPath 输出目录路径
     * @param generatedFileNames 生成的文件名集合
     */
    private fun cleanupOldFiles(outputPath: Path, generatedFileNames: Set<String>) {
        if (!Files.exists(outputPath)) {
            return
        }

        Files.list(outputPath).use { stream ->
            stream.filter { path ->
                val fileName = path.fileName.toString()
                fileName.endsWith(".kt") && !generatedFileNames.contains(fileName)
            }.forEach { path ->
                try {
                    Files.delete(path)
                    Logger.debug("删除旧文件: ${path.fileName}", 6)
                } catch (e: Exception) {
                    Logger.warn("删除旧文件失败: ${path.fileName} (${e.message})")
                }
            }
        }
    }
}
