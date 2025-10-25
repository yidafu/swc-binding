package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzer
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzerHolder
import dev.yidafu.swc.generator.codegen.poet.*
import dev.yidafu.swc.generator.extensions.getAllPropertiesForImpl
import dev.yidafu.swc.generator.util.ImplementationClassGenerator
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.util.PerformanceOptimizer
import java.io.File

/**
 * types.kt 生成器（使用 KotlinPoet）
 */
class TypesGenerator(
    private val classDecls: MutableList<KotlinDeclaration.ClassDecl>
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

    /**
     * 添加类声明（可能是枚举类）
     */
    fun addClassDecl(classDecl: KotlinDeclaration.ClassDecl) {
        classDecls.add(classDecl)
        Logger.debug("添加类声明: ${classDecl.name} (${classDecl.modifier.toModifierString()})", 6)
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
                "dev.yidafu.swc" to "Union",
                "dev.yidafu.swc" to "Union.U2",
                "dev.yidafu.swc" to "Union.U3", "dev.yidafu.swc" to "Union.U4",
                "kotlinx.serialization" to "SerialName",
                "kotlinx.serialization" to "Serializable",
                "kotlinx.serialization.json" to "JsonClassDiscriminator"
            )

            // 添加注解类和类型别名
            fileBuilder.addType(createSwcDslMarkerAnnotation())
            fileBuilder.addTypeAlias(createRecordTypeAlias())

            // 添加所有类型别名（包括硬编码的实用工具类型）
            addAllTypeAliases(fileBuilder)

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
        val allInterfaces = classDecls.filter { it.modifier == ClassModifier.Interface || it.modifier == ClassModifier.SealedInterface }
        Logger.debug("  接口数量: ${allInterfaces.size}", 4)

        val sortedInterfaces = sortClassesByInheritance(allInterfaces)
        generateInterfaces(fileBuilder, sortedInterfaces)

        // 2. 生成其他类型的类（如 FinalClass, DataClass, EnumClass 等）
        val otherClasses = classDecls.filter {
            it.modifier !is ClassModifier.Interface && it.modifier !is ClassModifier.SealedInterface &&
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
     * 只有 Node 继承树的叶子节点生成实现类
     * 使用共享工具类优化性能
     */
    private fun findLeafNodes(analyzer: InheritanceAnalyzer): List<KotlinDeclaration.ClassDecl> {
        val allInterfaces = classDecls.filter { it.modifier == ClassModifier.Interface }
        return ImplementationClassGenerator.findLeafNodes(allInterfaces, analyzer)
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
     * 使用共享工具类优化性能
     */
    private fun createImplementationClass(
        interfaceDecl: KotlinDeclaration.ClassDecl,
        analyzer: InheritanceAnalyzer
    ): KotlinDeclaration.ClassDecl {
        // 收集所有属性（包括继承的）
        // 使用 LinkedHashMap 保持属性缓存顺序，确保生成代码的确定性
        val propertyCache = LinkedHashMap<String, List<KotlinDeclaration.PropertyDecl>>()
        val allProperties = interfaceDecl.getAllPropertiesForImpl(analyzer, classDecls, propertyCache)

        // 重新组织属性：自有属性 → type → 继承属性
        val reorderedProperties = reorderImplementationProperties(allProperties, interfaceDecl)

        // 为所有属性添加 override 修饰符和默认值
        val processedProperties = reorderedProperties.map { prop ->
            processImplementationProperty(prop, interfaceDecl.name)
        }

        // 添加实现类需要的注解
        val discriminator = "type"
        val serialName = interfaceDecl.name

        return interfaceDecl.copy(
            name = "${interfaceDecl.name}Impl",
            modifier = ClassModifier.FinalClass,
            parents = listOf(KotlinType.Simple(interfaceDecl.name)),
            properties = processedProperties,
            annotations = listOf(
                KotlinDeclaration.Annotation("SwcDslMarker"),
                KotlinDeclaration.Annotation("Serializable"),
                KotlinDeclaration.Annotation("JsonClassDiscriminator", listOf(Expression.StringLiteral(discriminator))),
                KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral(serialName)))
            )
        )
    }

    /**
     * 重新排序实现类属性：自有属性 → 继承属性 → type → span → decorators
     */
    private fun reorderImplementationProperties(
        allProperties: List<KotlinDeclaration.PropertyDecl>,
        interfaceDecl: KotlinDeclaration.ClassDecl
    ): List<KotlinDeclaration.PropertyDecl> {
        val ownProperties = mutableListOf<KotlinDeclaration.PropertyDecl>()
        val inheritedProperties = mutableListOf<KotlinDeclaration.PropertyDecl>()
        val typeProperty = mutableListOf<KotlinDeclaration.PropertyDecl>()
        val spanProperty = mutableListOf<KotlinDeclaration.PropertyDecl>()
        val decoratorsProperty = mutableListOf<KotlinDeclaration.PropertyDecl>()

        allProperties.forEach { prop ->
            when (prop.name) {
                "type" -> typeProperty.add(prop)
                "span" -> spanProperty.add(prop)
                "decorators" -> decoratorsProperty.add(prop)
                else -> {
                    // 判断是否为自有属性（从接口声明中动态获取）
                    if (isOwnProperty(prop.name, interfaceDecl)) {
                        ownProperties.add(prop)
                    } else {
                        inheritedProperties.add(prop)
                    }
                }
            }
        }

        return ownProperties + inheritedProperties + typeProperty + spanProperty + decoratorsProperty
    }

    /**
     * 判断属性是否为接口的自有属性
     * 从接口声明中动态获取自有属性列表
     */
    private fun isOwnProperty(
        propertyName: String,
        interfaceDecl: KotlinDeclaration.ClassDecl
    ): Boolean {
        return interfaceDecl.properties.any { it.name == propertyName }
    }

    /**
     * 处理实现类属性：添加 override 修饰符和默认值
     */
    private fun processImplementationProperty(
        prop: KotlinDeclaration.PropertyDecl,
        interfaceName: String
    ): KotlinDeclaration.PropertyDecl {
        // 添加 override 修饰符
        val newModifier = when (prop.modifier) {
            is PropertyModifier.Var -> PropertyModifier.OverrideVar
            is PropertyModifier.Val -> PropertyModifier.OverrideVal
            else -> prop.modifier
        }

        // 添加默认值
        val defaultValue = when {
            prop.name == "type" -> Expression.StringLiteral(interfaceName)
            prop.type is KotlinType.Nullable -> Expression.NullLiteral
            else -> prop.defaultValue
        }

        return prop.copy(
            modifier = newModifier,
            defaultValue = defaultValue
        )
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
            // 写入到临时目录，使用4空格缩进
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
     * 添加所有类型别名（包括硬编码的实用工具类型）
     */
    private fun addAllTypeAliases(fileBuilder: FileSpec.Builder) {
        Logger.debug("  添加所有类型别名...", 6)

        // 创建硬编码的实用工具类型
        val hardcodedTypeAliases = listOf(
            TypeAliasSpec.builder("ToSnakeCase", Any::class)
                .addTypeVariable(TypeVariableName("T"))
                .build(),
            TypeAliasSpec.builder("ToSnakeCaseProperties", Any::class)
                .addTypeVariable(TypeVariableName("T"))
                .build()
        )

        // 创建所有类型别名的映射（名称 -> TypeAliasSpec）
        // 使用 LinkedHashMap 保持类型别名顺序，确保生成代码的确定性
        val allTypeAliases = LinkedHashMap<String, TypeAliasSpec>()

        // 添加硬编码的类型别名
        hardcodedTypeAliases.forEach { typeAlias ->
            allTypeAliases[typeAlias.name] = typeAlias
        }

        // 添加从 TypeScript 提取的类型别名
        typeAliases.forEach { typeAlias ->
            try {
                val typeAliasSpec = KotlinPoetConverter.convertTypeAliasDeclaration(typeAlias)
                // 如果类型别名已存在，会覆盖（硬编码的优先）
                allTypeAliases[typeAliasSpec.name] = typeAliasSpec
            } catch (e: Exception) {
                Logger.warn("转换类型别名失败: ${typeAlias.name}, ${e.message}")
            }
        }

        // 所有类型别名按名称排序，去掉硬编码顺序
        allTypeAliases.toSortedMap().values.forEach { typeAliasSpec ->
            fileBuilder.addTypeAlias(typeAliasSpec)
        }

        Logger.debug("  ✓ 添加 ${allTypeAliases.size} 个类型别名", 8)
    }

    /**
     * 后处理文件：修复导入顺序、缩进和格式
     */
    private fun postProcessFile(file: File) {
        try {
            val content = file.readText()
            val lines = content.lines()

            // 1. 分离包声明、导入语句和其他内容
            val packageLine = lines.find { it.startsWith("package ") } ?: ""
            val importLines = lines.filter { it.startsWith("import ") }
            val otherLines = lines.filter {
                !it.startsWith("import ") && !it.startsWith("package ")
            }

            // 2. 重新排序导入语句
            val reorderedImports = reorderImports(importLines)

            // 3. 处理其他行的缩进和格式
            val processedOtherLines = processOtherLines(otherLines)

            // 4. 合并并写入文件
            val finalLines = mutableListOf<String>()
            if (packageLine.isNotEmpty()) {
                finalLines.add(packageLine)
                finalLines.add("")
            }
            if (reorderedImports.isNotEmpty()) {
                finalLines.addAll(reorderedImports)
                finalLines.add("")
            }
            finalLines.addAll(processedOtherLines)

            // 5. 移除多余的空行（特别是在注解前）
            val cleanedLines = removeExtraEmptyLines(finalLines)

            val finalContent = cleanedLines.joinToString("\n")
            file.writeText(finalContent)

            Logger.debug("  后处理完成：导入顺序、缩进和格式修复", 6)
        } catch (e: Exception) {
            Logger.warn("后处理文件失败: ${e.message}")
        }
    }

    /**
     * 重新排序导入语句
     */
    private fun reorderImports(importLines: List<String>): List<String> {
        val devYidafuImports = importLines.filter { it.startsWith("import dev.yidafu.swc") }
        val kotlinxImports = importLines.filter { it.startsWith("import kotlinx.serialization") }
        val kotlinImports = importLines.filter { it.startsWith("import kotlin") && !it.startsWith("import kotlinx") }
        val otherImports = importLines.filter {
            !it.startsWith("import dev.yidafu.swc") && !it.startsWith("import kotlinx.serialization") && !it.startsWith("import kotlin") &&
                !it.startsWith("import Record") // 移除不需要的 Record 导入
        }

        return devYidafuImports + kotlinxImports + kotlinImports + otherImports
    }

    /**
     * 移除多余的空行
     */
    private fun removeExtraEmptyLines(lines: List<String>): List<String> {
        val result = mutableListOf<String>()
        var i = 0

        while (i < lines.size) {
            val line = lines[i]

            if (line.trim().isEmpty()) {
                // 找到空行，检查是否在注解前
                if (i + 1 < lines.size && lines[i + 1].trim().startsWith("@")) {
                    // 如果下一行是注解，移除这个空行
                    i++
                    continue
                }
                // 保留其他空行
                result.add(line)
            } else {
                result.add(line)
            }
            i++
        }

        return result
    }

    /**
     * 处理其他行的缩进和格式
     */
    private fun processOtherLines(lines: List<String>): List<String> {
        val processedLines = mutableListOf<String>()
        var i = 0

        while (i < lines.size) {
            val line = lines[i]

            // 处理多行属性声明的缩进
            if (line.trimEnd().endsWith(":") && i + 1 < lines.size) {
                val nextLine = lines[i + 1]
                if (nextLine.trim().isNotEmpty()) {
                    val indentCount = nextLine.takeWhile { it == ' ' }.length
                    if (indentCount == 12) {
                        // 修复12空格缩进为8空格
                        val fixedNextLine = "        " + nextLine.trim()
                        processedLines.add(line)
                        processedLines.add(fixedNextLine)
                        i += 2
                        continue
                    }
                }
            }

            // 保留必要的空行，只移除连续的空行
            if (line.trim().isEmpty()) {
                // 如果下一行也是空行，则跳过这一行
                if (i + 1 < lines.size && lines[i + 1].trim().isEmpty()) {
                    i++
                    continue
                }
                // 否则保留这个空行
            }

            // 处理普通缩进转换（2空格转4空格）
            var processedLine = line
            var indentCount = 0

            while (indentCount < line.length && line[indentCount] == ' ') {
                indentCount++
            }

            if (indentCount > 0 && indentCount % 2 == 0) {
                val newIndentCount = indentCount * 2
                val indent = " ".repeat(newIndentCount)
                val content = line.substring(indentCount)
                processedLine = indent + content
            }

            processedLines.add(processedLine)
            i++
        }

        return processedLines
    }
}
