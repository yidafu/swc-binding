package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.kotlin.isValidKotlinTypeName
import dev.yidafu.swc.generator.codegen.poet.*
import dev.yidafu.swc.generator.config.GlobalConfig
import dev.yidafu.swc.generator.config.HardcodedRules
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzer
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzerHolder
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.util.PerformanceOptimizer
import java.io.File

/**
 * types.kt 生成器（使用 KotlinPoet）
 */
class TypesGenerator(
    private val classDecls: List<KotlinDeclaration.ClassDecl>
) {
    private val typeAliases = mutableListOf<KotlinDeclaration.TypeAliasDecl>()
    
    // 懒加载接口名称集合，避免重复计算
    private val interfaceNames: Set<String> by lazy {
        classDecls
            .asSequence()
            .filter { it.modifier.isInterface() }
            .map { it.name }
            .toSet()
    }

    /**
     * 添加 typealias（使用 ADT）
     */
    fun addTypeAlias(typeAlias: KotlinDeclaration.TypeAliasDecl) {
        typeAliases.add(typeAlias)
        Logger.debug("添加 typealias: ${typeAlias.name} = ${typeAlias.type.toTypeString()}", 6)
    }


    // 注意：类型转换现在完全基于 ADT
    // 使用 KotlinPoetConverter.convertType(kotlinType) 进行转换
    // 不再使用基于字符串解析的方式

    /**
     * 生成代码并写入文件
     */
    fun writeToFile(outputPath: String) {
        PerformanceOptimizer.measureTime("生成 types.kt 文件") {
            Logger.debug("使用 KotlinPoet 生成 types.kt...", 4)

            // 使用懒加载的接口名称集合

            val fileBuilder = createFileBuilder(
                PoetConstants.PKG_TYPES,
                "types",
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
        
        // 打印性能统计
        PerformanceOptimizer.printCacheStats()
    }

    /**
     * 生成类并添加到文件
     * 简化逻辑：所有接口 + 叶子节点实现类
     */
    private fun generateClasses(fileBuilder: FileSpec.Builder) {
        Logger.debug("  开始生成类，总类数: ${classDecls.size}", 4)

        val analyzer = InheritanceAnalyzerHolder.get()
        
        // 1. 生成所有接口（保留继承关系）
        val allInterfaces = classDecls.filter { 
            it.modifier == ClassModifier.Interface || it.modifier == ClassModifier.SealedInterface 
        }
        Logger.debug("  接口数量: ${allInterfaces.size}", 4)
        
        val sortedInterfaces = sortClassesByInheritance(allInterfaces)
        generateInterfaces(fileBuilder, sortedInterfaces)
        
        // 2. 生成其他类型的类（如 FinalClass, DataClass 等）
        val otherClasses = classDecls.filter { 
            it.modifier !is ClassModifier.Interface && 
            it.modifier !is ClassModifier.SealedInterface &&
            !it.name.endsWith("Impl") // 排除实现类，它们会在后面处理
        }
        Logger.debug("  其他类数量: ${otherClasses.size}", 4)
        
        generateOtherClasses(fileBuilder, otherClasses)
        
        // 3. 生成叶子节点的实现类
        val leafNodes = findLeafNodes(analyzer)
        Logger.debug("  叶子节点数量: ${leafNodes.size}", 4)
        
        generateImplementationClasses(fileBuilder, leafNodes, analyzer)
    }
    
    /**
     * 生成所有接口（保留继承关系）
     */
    private fun generateInterfaces(fileBuilder: FileSpec.Builder, interfaces: List<KotlinDeclaration.ClassDecl>) {
        Logger.debug("  生成接口...", 4)
        
        interfaces.forEach { interfaceDecl ->
            try {
                val typeSpec = KotlinPoetConverter.convertDeclaration(interfaceDecl, interfaceNames)
                fileBuilder.addType(typeSpec)
                Logger.verbose("  ✓ 接口: ${interfaceDecl.name}", 6)
            } catch (e: Exception) {
                Logger.warn("  生成接口失败: ${interfaceDecl.name}, ${e.message}")
            }
        }
    }
    
    /**
     * 生成其他类型的类（如 FinalClass, DataClass 等）
     */
    private fun generateOtherClasses(fileBuilder: FileSpec.Builder, classes: List<KotlinDeclaration.ClassDecl>) {
        Logger.debug("  生成其他类...", 4)
        
        classes.forEach { classDecl ->
            try {
                val typeSpec = KotlinPoetConverter.convertDeclaration(classDecl, interfaceNames)
                fileBuilder.addType(typeSpec)
                Logger.verbose("  ✓ 类: ${classDecl.name} (${classDecl.modifier})", 6)
            } catch (e: Exception) {
                Logger.warn("  生成类失败: ${classDecl.name}, ${e.message}")
            }
        }
    }
    
    /**
     * 查找叶子节点（没有子类的接口）
     * 只对 Node 继承树的叶子节点生成实现类
     */
    private fun findLeafNodes(analyzer: InheritanceAnalyzer): List<KotlinDeclaration.ClassDecl> {
        val allInterfaces = classDecls.filter { it.modifier == ClassModifier.Interface }
        
        return allInterfaces.filter { interfaceDecl ->
            val children = analyzer.findAllChildrenByParent(interfaceDecl.name)
            val isNodeDescendant = analyzer.isDescendantOf(interfaceDecl.name, "Node")
            
            // Generate impl classes for leaf nodes that are descendants of Node
            children.isEmpty() && isNodeDescendant
        }
    }
    
    /**
     * 生成叶子节点的实现类
     */
    private fun generateImplementationClasses(
        fileBuilder: FileSpec.Builder, 
        leafNodes: List<KotlinDeclaration.ClassDecl>,
        analyzer: InheritanceAnalyzer
    ) {
        Logger.debug("  生成实现类...", 4)
        
        leafNodes.forEach { leafNode ->
            try {
                val implClass = createImplementationClass(leafNode, analyzer)
                val typeSpec = KotlinPoetConverter.convertDeclaration(implClass, interfaceNames)
                fileBuilder.addType(typeSpec)
                Logger.verbose("  ✓ 实现类: ${implClass.name}", 6)
            } catch (e: Exception) {
                Logger.warn("  生成实现类失败: ${leafNode.name}Impl, ${e.message}")
            }
        }
    }
    
    /**
     * 创建实现类
     */
    private fun createImplementationClass(
        interfaceDecl: KotlinDeclaration.ClassDecl,
        analyzer: InheritanceAnalyzer
    ): KotlinDeclaration.ClassDecl {
        // 收集所有继承的属性（包括父接口的属性）
        val allProperties = collectAllProperties(interfaceDecl, analyzer)
        
        // 添加实现类需要的注解
        val discriminator = "type"
        val serialName = interfaceDecl.name
        
        return interfaceDecl.copy(
            name = "${interfaceDecl.name}Impl",
            modifier = ClassModifier.FinalClass,
            parents = listOf(KotlinType.Simple(interfaceDecl.name)),
            annotations = listOf(
                KotlinDeclaration.Annotation("SwcDslMarker"),
                KotlinDeclaration.Annotation("Serializable"),
                KotlinDeclaration.Annotation("JsonClassDiscriminator", listOf(Expression.StringLiteral(discriminator))),
                KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral(serialName)))
            ),
            properties = allProperties
        )
    }
    
    /**
     * 收集所有属性（包括继承的属性）
     */
    private fun collectAllProperties(
        interfaceDecl: KotlinDeclaration.ClassDecl,
        analyzer: InheritanceAnalyzer
    ): List<KotlinDeclaration.PropertyDecl> {
        val allProperties = mutableMapOf<String, KotlinDeclaration.PropertyDecl>()
        
        // 添加当前接口的属性
        interfaceDecl.properties.forEach { prop ->
            allProperties[prop.name] = prop
        }
        
        // 递归添加父接口的属性
        val parentInterfaces = analyzer.findAllParentsByChild(interfaceDecl.name)
        parentInterfaces.forEach { parentName: String ->
            val parentInterface = classDecls.find { it.name == parentName }
            if (parentInterface != null) {
                collectAllProperties(parentInterface, analyzer).forEach { prop ->
                    // 如果属性名已存在，跳过（子接口的属性优先）
                    if (!allProperties.containsKey(prop.name)) {
                        allProperties[prop.name] = prop
                    }
                }
            }
        }
        
        // 将属性转换为可空且默认值为 null 的实现类属性
        return allProperties.values.map { prop ->
            val updatedProp = prop.withNullableIfNeeded().copy(
                modifier = PropertyModifier.OverrideVar
            )
            
            // 如果是 type 属性，设置为不可空 String 类型并设置默认值为接口名称
            if (prop.name == "type") {
                val innerType = when (updatedProp.type) {
                    is KotlinType.Nullable -> updatedProp.type.innerType
                    else -> updatedProp.type
                }
                if (innerType is KotlinType.StringType) {
                    // type 属性应该是不可空的 String 类型
                    updatedProp.copy(
                        type = KotlinType.StringType,
                        defaultValue = Expression.StringLiteral(interfaceDecl.name)
                    )
                } else {
                    updatedProp.copy(defaultValue = Expression.NullLiteral)
                }
            } else {
                // 其他属性设置默认值为 null
                updatedProp.copy(defaultValue = Expression.NullLiteral)
            }
        }.toList()
    }

    // 注意：批量生成方法已简化，现在分别处理接口和实现类

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
            MAP.parameterizedBy(tTypeVar, STRING)
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
            val generatedFile = File(tempDir, "dev/yidafu/swc/generated/${fileSpec.name}.kt")
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
            
            // 后处理：移除不必要的 public 修饰符
            postProcessFile(file)
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

    // 注意：过滤逻辑已简化，现在直接生成所有接口和叶子节点实现类

    /**
     * 按继承关系排序类声明
     * 1. 按继承树分组
     * 2. 树内按深度排序（父类在前）
     */
    private fun sortClassesByInheritance(classes: List<KotlinDeclaration.ClassDecl>): List<KotlinDeclaration.ClassDecl> {
        // 按继承树根节点分组
        val groupedByRoot = classes.groupBy { it.getInheritanceRoot() }
        
        val result = mutableListOf<KotlinDeclaration.ClassDecl>()
        
        // 处理每个继承树
        groupedByRoot.forEach { (_, classesInTree) ->
            // 树内按继承深度排序（深度小的在前）
            val sortedTree = classesInTree.sortedBy { it.getInheritanceDepth() }
            result.addAll(sortedTree)
        }
        
        return result
    }
    
    /**
     * 后处理文件（当前为空，保留用于未来扩展）
     */
    private fun postProcessFile(file: File) {
        // 当前不需要后处理，保留方法用于未来扩展
        Logger.debug("  后处理完成", 6)
    }
}
