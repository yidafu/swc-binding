package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dev.yidafu.swc.generator.core.model.KotlinClass
import dev.yidafu.swc.generator.core.model.KotlinProperty
import dev.yidafu.swc.generator.codegen.poet.*
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.adt.kotlin.*
import java.io.File

/**
 * types.kt 生成器（使用 KotlinPoet）
 */
class TypesGenerator(
    private val kotlinClasses: List<KotlinClass>
) {
    private val typeAliases = mutableListOf<KotlinDeclaration.TypeAliasDecl>()
    
    /**
     * 添加 typealias（使用 ADT）
     */
    fun addTypeAlias(typeAlias: KotlinDeclaration.TypeAliasDecl) {
        typeAliases.add(typeAlias)
        Logger.debug("添加 typealias: ${typeAlias.name} = ${typeAlias.type.toTypeString()}", 6)
    }
    
    /**
     * 添加 typealias（向后兼容的字符串方式）
     */
    @Deprecated("使用 addTypeAlias(KotlinDeclaration.TypeAliasDecl) 替代")
    fun addTypeAlias(typeAlias: String) {
        // 解析 typealias 字符串并添加
        Logger.debug("添加 typealias: $typeAlias", 6)
        
        // 解析 typealias 字符串，格式: "typealias Name = Type"
        val regex = Regex("""typealias\s+(\w+)\s*=\s*(.+)""")
        val match = regex.find(typeAlias)
        if (match != null) {
            val name = match.groupValues[1]
            val typeStr = match.groupValues[2]
            
            // 创建 TypeAliasDecl
            val typeAliasDecl = KotlinDeclaration.TypeAliasDecl(
                name = name,
                type = typeStr.parseToKotlinType()
            )
            
            addTypeAlias(typeAliasDecl)
            Logger.debug("成功添加 typealias: $name = $typeStr", 8)
        } else {
            Logger.warn("无法解析 typealias: $typeAlias")
        }
    }
    
    /**
     * 解析类型名称为 TypeName
     */
    private fun parseTypeName(typeStr: String): TypeName {
        return when {
            typeStr.startsWith("Union.U") -> {
                // 处理 Union.U2<A, B> 等
                val match = Regex("""Union\.U(\d+)<(.+)>""").find(typeStr)
                if (match != null) {
                    val unionNum = match.groupValues[1].toInt()
                    val genericParams = match.groupValues[2].split(",").map { it.trim() }
                    
                    val className = ClassName("dev.yidafu.swc", "Union.U$unionNum")
                    val typeParams = genericParams.map { TypeVariableName(it) }
                    className.parameterizedBy(typeParams)
                } else {
                    ClassName("dev.yidafu.swc", typeStr)
                }
            }
            typeStr.startsWith("Map<") -> {
                // 处理 Map<String, String> 等
                val match = Regex("""Map<(.+),\s*(.+)>""").find(typeStr)
                if (match != null) {
                    val keyType = parseTypeName(match.groupValues[1])
                    val valueType = parseTypeName(match.groupValues[2])
                    Map::class.asClassName().parameterizedBy(keyType, valueType)
                } else {
                    ClassName("kotlin.collections", "Map")
                }
            }
            typeStr.startsWith("Array<") -> {
                // 处理 Array<T> 等
                val match = Regex("""Array<(.+)>""").find(typeStr)
                if (match != null) {
                    val elementType = parseTypeName(match.groupValues[1])
                    Array::class.asClassName().parameterizedBy(elementType)
                } else {
                    ClassName("kotlin", "Array")
                }
            }
            else -> {
                // 基本类型
                when (typeStr) {
                    "String" -> String::class.asTypeName()
                    "Int" -> Int::class.asTypeName()
                    "Boolean" -> Boolean::class.asTypeName()
                    "Any" -> Any::class.asTypeName()
                    else -> ClassName("dev.yidafu.swc.types", typeStr)
                }
            }
        }
    }
    
    /**
     * 生成代码并写入文件
     */
    fun writeToFile(outputPath: String) {
        Logger.debug("使用 KotlinPoet 生成 types.kt...", 4)
        
        val fileBuilder = createFileBuilder(PoetConstants.PKG_TYPES, "types",
            PoetConstants.PKG_BOOLEANABLE to "*",
            "kotlinx.serialization" to "*",
            "kotlinx.serialization.json" to "JsonClassDiscriminator"
        )
        
        // 添加注解类和类型别名
        fileBuilder.addType(createSwcDslMarkerAnnotation())
        fileBuilder.addTypeAlias(createRecordTypeAlias())
        
        // 使用 KotlinPoetConverter 添加类型别名
        typeAliases.forEach { typeAlias ->
            try {
                val typeAliasSpec = KotlinPoetConverter.convertTypeAliasDeclaration(typeAlias)
                fileBuilder.addTypeAlias(typeAliasSpec)
            } catch (e: Exception) {
                Logger.warn("转换类型别名失败: ${typeAlias.name}, ${e.message}")
            }
        }
        
        // 生成并添加类
        generateClasses(fileBuilder)
        
        // 写入文件
        writeFile(fileBuilder, outputPath)
    }
    
    /**
     * 生成类并添加到文件
     */
    private fun generateClasses(fileBuilder: FileSpec.Builder) {
        Logger.debug("  开始生成类，总类数: ${kotlinClasses.size}", 4)
        
        val filteredClasses = filterClasses()
        Logger.debug("  过滤后的类数量: ${filteredClasses.size}", 4)
        
        if (filteredClasses.isEmpty()) {
            Logger.warn("⚠️  警告：所有类都被过滤掉了！")
            Logger.debug("  原始类列表: ${kotlinClasses.map { it.klassName }}", 6)
        }
        
        var successCount = 0
        var failureCount = 0
        
        val failedClasses = mutableListOf<Pair<String, String>>()
        
        filteredClasses.forEach { klass ->
            runCatching {
                KotlinPoetGenerator.createTypeSpec(klass)
            }.onSuccess { typeSpec: TypeSpec ->
                fileBuilder.addType(typeSpec)
                successCount++
                Logger.verbose("  ✓ ${klass.klassName}", 6)
            }.onFailure { e ->
                failureCount++
                val errorMsg = e.message ?: "未知错误"
                failedClasses.add(klass.klassName to errorMsg)
                
                Logger.warn("⚠️  生成失败: ${klass.klassName}")
                Logger.verbose("  错误: $errorMsg", 8)
                Logger.verbose("  类型: ${klass.modifier}, 属性数: ${klass.properties.size}", 8)
                
                if (klass.properties.isNotEmpty()) {
                    Logger.verbose("  前3个属性:", 8)
                    klass.properties.take(3).forEach { prop: KotlinProperty ->
                        Logger.verbose("    - ${prop.modifier} ${prop.name}: ${prop.getTypeString()}", 10)
                    }
                }
            }
        }
        
        // 生成汇总报告
        Logger.debug("  生成完成: 成功 $successCount，失败 $failureCount", 4)
        
        if (failureCount > 0) {
            Logger.separator()
            Logger.warn("生成失败汇总 ($failureCount 个类):")
            failedClasses.take(10).forEach { (name, error) ->
                Logger.info("  - $name: ${error.take(60)}", 2)
            }
            if (failedClasses.size > 10) {
                Logger.info("  ... 还有 ${failedClasses.size - 10} 个失败", 2)
            }
        }
        
        // 计算成功率
        val successRate = if (filteredClasses.isNotEmpty()) {
            (successCount.toDouble() / filteredClasses.size * 100).toInt()
        } else {
            0
        }
        Logger.info("  成功率: $successRate% ($successCount/${filteredClasses.size})", 2)
    }
    
    /**
     * 创建 SwcDslMarker 注解
     */
    private fun createSwcDslMarkerAnnotation(): TypeSpec {
        return TypeSpec.annotationBuilder("SwcDslMarker")
            .addAnnotation(PoetConstants.Kotlin.DSL_MARKER)
            .build()
    }
    
    /**
     * 创建 Record typealias
     */
    private fun createRecordTypeAlias(): TypeAliasSpec {
        val tTypeVar = TypeVariableName("T")
        val sTypeVar = TypeVariableName("S")
        
        return TypeAliasSpec.builder(
            "Record",
            MAP.parameterizedBy(tTypeVar, sTypeVar)
        )
            .addTypeVariable(tTypeVar)
            .addTypeVariable(sTypeVar)
            .build()
    }
    
    /**
     * 写入文件并记录日志
     */
    private fun writeFile(fileBuilder: FileSpec.Builder, outputPath: String) {
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
        
        // 检查文件是否存在后再读取
        if (file.exists()) {
            val lines = file.readText().lines().size
            Logger.success("Generated: $outputPath ($lines 行)")
        } else {
            // 文件路径可能不同，直接从 FileSpec 内容计算行数
            val content = StringBuilder()
            fileSpec.writeTo(content)
            val lines = content.lines().size
            Logger.success("Generated: $outputPath ($lines 行)")
        }
    }
    
    /**
     * 过滤不需要的类
     */
    private fun filterClasses(): List<KotlinClass> {
        Logger.debug("  开始过滤类...", 6)
        
        val astNodeList = mutableSetOf<String>()
        astNodeList.addAll(ExtendRelationship.findAllRelativeByName("Node"))
        astNodeList.addAll(ExtendRelationship.findAllRelativeByName("HasSpan"))
        Logger.debug("  AST节点列表大小: ${astNodeList.size}", 8)
        
        // 从配置加载重要接口列表
        val config = dev.yidafu.swc.generator.config.ConfigLoader.loadConfig()
        val importantInterfaces = config.filterRules.importantInterfaces.toSet()
        
        // 找出那些只作为接口存在、不需要独立生成的类
        // 只过滤掉那些是 interface 且有唯一 Impl 子类的
        val notImplClassList = kotlinClasses
            .filter { !astNodeList.contains(it.klassName) }
            .filter { klass ->
                // 重要接口不过滤
                if (importantInterfaces.contains(klass.klassName)) {
                    return@filter false
                }
                // 只过滤 interface，不过滤 class
                if (!klass.modifier.contains("interface")) {
                    return@filter false
                }
                val children = ExtendRelationship.findAllChildrenByParent(klass.klassName)
                children.size == 1 && children[0] == "${klass.klassName}Impl"
            }
            .map { listOf(it.klassName, "${it.klassName}Impl") }
            .flatten()
        Logger.debug("  不需要的Impl类列表大小: ${notImplClassList.size}", 8)
        Logger.debug("  不需要的Impl类列表: $notImplClassList", 8)
        
        val filtered = kotlinClasses
            .filter { !notImplClassList.contains(it.klassName) }
            .filter { klass: KotlinClass ->
                // 使用配置的跳过模式进行过滤
                config.filterRules.skipClassPatterns.none { pattern: String ->
                    klass.klassName.matches(Regex(pattern))
                }
            }
        
        Logger.debug("  过滤完成，剩余类: ${filtered.map { it.klassName }}", 8)
        return filtered
    }
}


