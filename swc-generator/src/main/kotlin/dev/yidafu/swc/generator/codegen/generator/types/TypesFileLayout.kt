package dev.yidafu.swc.generator.codegen.generator.types

import com.squareup.kotlinpoet.FileSpec
import dev.yidafu.swc.generator.codegen.generator.PoetGenerator
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFile
import dev.yidafu.swc.generator.codegen.poet.createFileBuilder
import java.nio.file.Path
import java.util.LinkedHashMap

/**
 * 负责管理 types 生成的多个文件（common + 每个接口专属文件）。
 */
class TypesFileLayout(
    private val packageName: String,
    private val defaultImports: Array<out Pair<String, String>>,
    private val originalOutputPath: Path,
    private val interfacesWithDedicatedFiles: Set<String>
) {
    // 如果路径是目录，直接使用；如果是文件，使用其父目录
    private val outputDirectory: Path = if (originalOutputPath.toString().endsWith(".kt")) {
        originalOutputPath.parent ?: originalOutputPath
    } else {
        originalOutputPath
    }

    val commonOutputPath: Path = outputDirectory.resolve("common.kt")
    val commonFileBuilder: FileSpec.Builder = createFileBuilder(
        packageName,
        "common",
        *defaultImports
    )

    private val interfaceBuilders = LinkedHashMap<String, FileSpec.Builder>()
    private val classBuilders = LinkedHashMap<String, FileSpec.Builder>()

    fun builderForInterfaceName(name: String): FileSpec.Builder {
        return if (name in interfacesWithDedicatedFiles) {
            interfaceBuilders.getOrPut(name) {
                createFileBuilder(packageName, name, *defaultImports)
            }
        } else {
            commonFileBuilder
        }
    }

    fun builderForLeafInterface(name: String): FileSpec.Builder {
        return builderForInterfaceName(name)
    }

    fun requiresDedicatedFile(name: String): Boolean = name in interfacesWithDedicatedFiles

    /**
     * 为类提供专属文件构建器：类总是独立文件
     */
    fun builderForClassName(name: String): FileSpec.Builder {
        return classBuilders.getOrPut(name) {
            createFileBuilder(packageName, name, *defaultImports)
        }
    }

    fun collectGeneratedFiles(
        poet: PoetGenerator,
        postProcessor: TypesPostProcessor
    ): List<GeneratedFile> {
        val generatedFiles = mutableListOf<GeneratedFile>()

        generatedFiles += createGeneratedFile(commonOutputPath, commonFileBuilder, poet, postProcessor)
        interfaceBuilders.forEach { (name, builder) ->
            generatedFiles += createGeneratedFile(interfaceOutputPath(name), builder, poet, postProcessor)
        }
        classBuilders.forEach { (name, builder) ->
            // 调试：检查 ForOfStatement 和 ComputedPropName
            if (name.removeSurrounding("`") == "ForOfStatement" || name.removeSurrounding("`") == "ComputedPropName") {
                println("  [DEBUG] collectGeneratedFiles: 找到类 $name，将生成文件")
            }
            generatedFiles += createGeneratedFile(interfaceOutputPath(name), builder, poet, postProcessor)
        }

        // 如果 originalOutputPath 是文件路径，创建占位符文件；如果是目录，则不创建
        if (originalOutputPath.toString().endsWith(".kt")) {
            generatedFiles += GeneratedFile(
                outputPath = originalOutputPath,
                contentProducer = { PLACEHOLDER_FILE_COMMENT }
            )
        }

        return generatedFiles
    }

    private fun interfaceOutputPath(name: String): Path = outputDirectory.resolve("$name.kt")

    private fun createGeneratedFile(
        outputPath: Path,
        builder: FileSpec.Builder,
        poet: PoetGenerator,
        postProcessor: TypesPostProcessor
    ): GeneratedFile {
        val fileSpec = poet.buildFile(builder)
        return GeneratedFile(
            outputPath = outputPath,
            fileSpec = fileSpec,
            formatter = { content, _ -> postProcessor.format(content) }
        )
    }

    companion object {
        private const val PLACEHOLDER_FILE_COMMENT =
            "// Auto-generated placeholder. Actual declarations live beside this file."
    }
}
