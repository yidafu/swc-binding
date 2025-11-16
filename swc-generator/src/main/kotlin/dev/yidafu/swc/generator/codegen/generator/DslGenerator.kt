package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.codegen.generator.dsl.DslExtensionCollector
import dev.yidafu.swc.generator.codegen.generator.dsl.DslFileEmitter
import dev.yidafu.swc.generator.codegen.generator.dsl.DslModelContext
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFileWriter
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.util.Logger
import java.io.Closeable
import java.nio.file.Path
import java.nio.file.Paths

/**
 * DSL 代码生成器（分层 orchestrator）
 */
class DslGenerator(
    private val classDecls: List<KotlinDeclaration.ClassDecl>,
    private val classAllPropertiesMap: Map<String, List<KotlinDeclaration.PropertyDecl>>,
    private val writer: GeneratedFileWriter = GeneratedFileWriter(),
    private val poetGenerator: PoetGenerator = DefaultPoetGenerator()
) : Closeable {
    private val modelContext = DslModelContext(classDecls, classAllPropertiesMap)
    private val collector = DslExtensionCollector(modelContext)
    private val emitter = DslFileEmitter(modelContext, poetGenerator)

    fun writeToDirectory(outputDir: String) {
        val outputPath = Paths.get(outputDir)
        prepareOutputDir(outputPath)
        val collection = collector.collect()
        val files = emitter.emit(collection, outputPath)
        writer.write(files)
        Logger.success("Generated DSL files in: $outputDir (${files.size} 个文件)")
    }

    private fun prepareOutputDir(path: Path) {
        val dir = path.toFile()
        if (dir.exists()) {
            dir.deleteRecursively()
        }
        dir.mkdirs()
    }

    /**
     * 关闭资源，释放线程池
     */
    override fun close() {
        writer.close()
    }
}
