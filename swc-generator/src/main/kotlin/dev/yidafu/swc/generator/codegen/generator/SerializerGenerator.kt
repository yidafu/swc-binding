package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.*
import dev.yidafu.swc.generator.codegen.poet.*
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.util.Logger
import java.io.File

/**
 * serializer.kt 生成器（使用 KotlinPoet）
 */
class SerializerGenerator {

    /**
     * 写入文件
     */
    fun writeToFile(outputPath: String, astNodeList: List<String>) {
        Logger.debug("使用 KotlinPoet 生成 serializer.kt...", 4)

        val polymorphicMap = buildPolymorphicMap(astNodeList)
        Logger.debug("  多态类型数: ${polymorphicMap.size}", 4)

        val fileBuilder = createFileBuilder(
            PoetConstants.PKG_TYPES, "serializer",
            "kotlinx.serialization" to "DeserializationStrategy",
            "kotlinx.serialization" to "SerializationException",
            "kotlinx.serialization" to "*",
            "kotlinx.serialization.json" to "*",
            "kotlinx.serialization.modules" to "polymorphic",
            "kotlinx.serialization.modules" to "subclass",
            "kotlinx.serialization.modules" to "SerializersModule"
        )

        fileBuilder.addProperty(createSerializersModuleProperty(polymorphicMap))

        val file = File(outputPath)
        file.parentFile?.mkdirs()

        val fileSpec = fileBuilder.build()

        // 创建临时目录来避免 KotlinPoet 创建包目录结构
        val tempDir = File.createTempFile("swc-generator", "").apply {
            delete()
            mkdirs()
        }

        try {
            // 写入到临时目录
            fileSpec.writeTo(tempDir)

            // 找到生成的文件（KotlinPoet 会根据包名创建目录结构）
            val generatedFile = File(tempDir, "dev/yidafu/swc/types/${fileSpec.name}.kt")
            if (generatedFile.exists()) {
                // 复制到目标位置
                generatedFile.copyTo(file, overwrite = true)
            } else {
                // 如果没找到，尝试直接查找
                val foundFile = tempDir.walkTopDown().filter { it.name == "${fileSpec.name}.kt" }.firstOrNull()
                if (foundFile != null) {
                    foundFile.copyTo(file, overwrite = true)
                } else {
                    throw IllegalStateException("无法找到生成的文件: ${fileSpec.name}.kt")
                }
            }
        } finally {
            // 清理临时目录
            tempDir.deleteRecursively()
        }

        Logger.success("Generated: $outputPath (${astNodeList.size} 个类型)")
    }

    /**
     * 构建多态映射
     */
    private fun buildPolymorphicMap(astNodeList: List<String>): Map<String, List<String>> {
        return astNodeList
            .associateWith { key ->
                ExtendRelationship.findAllGrandChildren(key).filter { it != key }
            }
            .filterValues { it.isNotEmpty() }
            .also { map ->
                map.forEach { (key, children) ->
                    Logger.verbose("  $key -> ${children.size} 个子类型", 6)
                }
            }
    }

    /**
     * 创建 swcSerializersModule 属性
     */
    private fun createSerializersModuleProperty(polymorphicMap: Map<String, List<String>>): PropertySpec {
        val initializerCode = buildSerializerModuleCode(polymorphicMap)

        return PropertySpec.builder("swcSerializersModule", PoetConstants.Serialization.Modules.SERIALIZERS_MODULE)
            .initializer(initializerCode)
            .build()
    }

    /**
     * 构建 SerializersModule 初始化代码
     */
    private fun buildSerializerModuleCode(polymorphicMap: Map<String, List<String>>): CodeBlock {
        return CodeBlock.builder()
            .add("SerializersModule {\n")
            .apply {
                indent()
                polymorphicMap.forEach { (parent, children) ->
                    add("polymorphic(%L::class) {\n", parent)
                    indent()
                    children.forEach { child -> add("subclass(%L::class)\n", child) }
                    unindent()
                    add("}\n")
                }
                unindent()
            }
            .add("}")
            .build()
    }
}
