package dev.yidafu.swc.generator.generator

import com.squareup.kotlinpoet.*
import dev.yidafu.swc.generator.poet.*
import dev.yidafu.swc.generator.relation.ExtendRelationship
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
        
        val fileBuilder = createFileBuilder(PoetConstants.PKG_TYPES, "serializer",
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
        fileBuilder.build().writeTo(file.parentFile!!)
        
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


