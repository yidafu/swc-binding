package dev.yidafu.swc.generator

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.tsParseOptions
import dev.yidafu.swc.generator.ast.*
import dev.yidafu.swc.generator.generator.DslGenerator
import dev.yidafu.swc.generator.generator.SerializerGenerator
import dev.yidafu.swc.generator.generator.TypesGenerator
import dev.yidafu.swc.generator.model.KotlinClass
import dev.yidafu.swc.generator.model.KotlinProperty
import dev.yidafu.swc.generator.relation.ExtendRelationship
import dev.yidafu.swc.generator.transform.Constants
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.util.safeValue
import dev.yidafu.swc.types.*
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {
    // 解析命令行参数
    val config = parseArgs(args)
    
    if (config.showHelp) {
        printHelp()
        return
    }
    
    Logger.header("SWC Generator Kotlin - 开始生成代码")
    Logger.separator()
    
    try {
        val generator = SwcGeneratorKt(config)
        generator.run()
        
        Logger.separator()
        Logger.header("代码生成完成！")
    } catch (e: Exception) {
        Logger.separator()
        Logger.error("代码生成失败: ${e.message}")
        e.printStackTrace()
        throw e
    }
}

/**
 * 配置类
 */
data class GeneratorConfig(
    val inputPath: String? = null,
    val outputTypesPath: String? = null,
    val outputSerializerPath: String? = null,
    val outputDslDir: String? = null,
    val showHelp: Boolean = false
)

/**
 * 解析命令行参数
 */
fun parseArgs(args: Array<String>): GeneratorConfig {
    var inputPath: String? = null
    var outputTypesPath: String? = null
    var outputSerializerPath: String? = null
    var outputDslDir: String? = null
    var showHelp = false
    
    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "-h", "--help" -> {
                showHelp = true
                i++
            }
            "-i", "--input" -> {
                if (i + 1 < args.size) {
                    inputPath = args[i + 1]
                    i += 2
                } else {
                    throw IllegalArgumentException("--input 需要一个参数")
                }
            }
            "-o", "--output-types" -> {
                if (i + 1 < args.size) {
                    outputTypesPath = args[i + 1]
                    i += 2
                } else {
                    throw IllegalArgumentException("--output-types 需要一个参数")
                }
            }
            "-s", "--output-serializer" -> {
                if (i + 1 < args.size) {
                    outputSerializerPath = args[i + 1]
                    i += 2
                } else {
                    throw IllegalArgumentException("--output-serializer 需要一个参数")
                }
            }
            "-d", "--output-dsl" -> {
                if (i + 1 < args.size) {
                    outputDslDir = args[i + 1]
                    i += 2
                } else {
                    throw IllegalArgumentException("--output-dsl 需要一个参数")
                }
            }
            else -> {
                // 如果没有前缀，当作输入文件
                if (!args[i].startsWith("-")) {
                    inputPath = args[i]
                    i++
                } else {
                    throw IllegalArgumentException("未知参数: ${args[i]}")
                }
            }
        }
    }
    
    return GeneratorConfig(
        inputPath = inputPath,
        outputTypesPath = outputTypesPath,
        outputSerializerPath = outputSerializerPath,
        outputDslDir = outputDslDir,
        showHelp = showHelp
    )
}

/**
 * 打印帮助信息
 */
fun printHelp() {
    println("""
        SWC Generator Kotlin - TypeScript 类型定义转 Kotlin 代码生成器
        
        用法:
            ./gradlew run --args="[选项]"
            或
            java -jar swc-generator-kt.jar [选项]
        
        选项:
            -h, --help                      显示帮助信息
            -i, --input <文件>              输入的 .d.ts 文件路径
            -o, --output-types <文件>       输出的 types.kt 文件路径
            -s, --output-serializer <文件>  输出的 serializer.kt 文件路径
            -d, --output-dsl <目录>         输出的 DSL 文件目录
        
        示例:
            # 使用默认配置
            ./gradlew run
            
            # 指定输入文件
            ./gradlew run --args="-i tests/01-base-types.d.ts"
            
            # 简写形式（直接传文件路径）
            ./gradlew run --args="tests/01-base-types.d.ts"
            
            # 指定所有路径
            ./gradlew run --args="-i input.d.ts -o output/types.kt -s output/serializer.kt -d output/dsl"
            
            # 使用环境变量（推荐用于脚本）
            export SWC_TYPES_PATH="tests/01-base-types.d.ts"
            ./gradlew run
        
        优先级:
            1. 命令行参数
            2. 环境变量 (SWC_TYPES_PATH)
            3. 默认配置
        
        默认配置:
            输入: ../swc-generator-kt/test-minimal.d.ts
            输出: ../swc/types/types.kt
                  ../swc/types/serializer.kt
                  ../swc/dsl/
        
        测试文件:
            test-minimal.d.ts         - 最小但完整的测试（推荐）
            test-simple.d.ts          - 极简测试
            test-comprehensive.d.ts   - 综合测试
            tests/*.d.ts              - 独立功能测试
        
        更多信息请查看: TESTING.md
    """.trimIndent())
}



class SwcGeneratorKt(private val config: GeneratorConfig = GeneratorConfig()) {
    private val swc = SwcNative()
    private val kotlinClassMap = mutableMapOf<String, KotlinClass>()
    private val classAllPropertiesMap = mutableMapOf<String, List<KotlinProperty>>()
    
    // 配置路径
    private val projectRoot = Paths.get("").toAbsolutePath().parent.toString()
    
    // 输入路径优先级：命令行参数 > 环境变量 > 默认值
    // 测试文件选项：
    // - test-minimal.d.ts: 最小测试文件，覆盖所有处理逻辑分支
    // - test-simple.d.ts: 简单测试文件
    // - test-comprehensive.d.ts: 综合测试文件，包含所有 TS 语法特性
    private val inputPath = config.inputPath 
        ?: System.getenv("SWC_TYPES_PATH") 
        ?: "$projectRoot/swc-generator-kt/test-minimal.d.ts"  // 推荐：最小但完整的测试
        // ?: "$projectRoot/swc-generator-kt/test-simple.d.ts"
        // ?: "$projectRoot/swc-generator-kt/test-comprehensive.d.ts"
        // ?: "$projectRoot/swc-generator/node_modules/.pnpm/@swc+types@0.1.5/node_modules/@swc/types/index.d.ts"
    
    private val outputTypesPath = config.outputTypesPath 
        ?: "$projectRoot/swc-generator-kt/swc/types/types.kt"
    private val outputSerializerPath = config.outputSerializerPath 
        ?: "$projectRoot/swc-generator-kt/swc/types/serializer.kt"
    private val outputDslDir = config.outputDslDir 
        ?: "$projectRoot/swc-generator-kt/swc/dsl"
    
    fun run() {
        Logger.setTotalSteps(9)
        Logger.startTimer("total")
        
        // 1. 解析 TypeScript 文件
        Logger.step("解析 TypeScript 文件...")
        Logger.startTimer("parse")
        val program = parseTypeScriptFile()
        Logger.endTimer("parse")
        Logger.breakpoint("parse_complete", mapOf("program_type" to program::class.simpleName))
        
        // 2. 遍历 AST
        Logger.step("遍历 AST 提取类型定义...")
        Logger.startTimer("extract")
        val (visitor, interfaceExtractor, typeAliasExtractor) = extractTypes(program)
        Logger.endTimer("extract")
        Logger.breakpoint("extract_complete", mapOf(
            "interfaces" to visitor.getInterfaces().size,
            "typeAliases" to visitor.getTypeAliases().size
        ))
        
        // 3-6. 处理各种类型
        Logger.step("处理字面量联合类型...")
        Logger.startTimer("literal_union")
        processLiteralUnion(visitor, typeAliasExtractor)
        Logger.endTimer("literal_union")
        Logger.stats("生成的 Literal Union 类", kotlinClassMap.values.filter { it.modifier == "object" })
        
        Logger.step("处理混合联合类型...")
        Logger.startTimer("mixed_union")
        processMixedUnion(visitor, typeAliasExtractor)
        Logger.endTimer("mixed_union")
        
        Logger.step("处理交叉类型...")
        Logger.startTimer("intersection")
        processIntersectionType(visitor, typeAliasExtractor, interfaceExtractor)
        Logger.endTimer("intersection")
        Logger.stats("Base interfaces", kotlinClassMap.values.filter { it.klassName.startsWith("Base") })
        
        Logger.step("处理引用联合类型...")
        Logger.startTimer("ref_union")
        processRefUnion(visitor, typeAliasExtractor)
        Logger.endTimer("ref_union")
        Logger.stats("Sealed interfaces", kotlinClassMap.values.filter { it.modifier == "sealed interface" })
        
        // 7-8. 构建关系和处理接口
        Logger.step("构建类型继承关系...")
        Logger.startTimer("build_relationship")
        buildInheritanceRelationship(visitor, interfaceExtractor)
        Logger.endTimer("build_relationship")
        Logger.debug("继承关系数量: ${ExtendRelationship.getAll().size}")
        
        Logger.step("处理接口定义...")
        Logger.startTimer("process_interfaces")
        processInterfaces(visitor, interfaceExtractor)
        Logger.endTimer("process_interfaces")
        Logger.stats("总生成类数", kotlinClassMap.values)
        Logger.breakpoint("before_generate", mapOf(
            "total_classes" to kotlinClassMap.size,
            "total_properties" to classAllPropertiesMap.values.sumOf { it.size }
        ))
        
        // 9. 生成代码
        Logger.step("生成代码文件...")
        Logger.startTimer("generate")
        generateCode()
        Logger.endTimer("generate")
        
        val totalTime = Logger.endTimer("total")
        Logger.info("总耗时: ${totalTime}ms")
    }
    
    /**
     * 提取类型定义
     */
    private fun extractTypes(program: Program): Triple<TsAstVisitor, InterfaceExtractor, TypeAliasExtractor> {
        Logger.debug("创建 AST 访问器...")
        val visitor = TsAstVisitor(program)
        
        Logger.debug("开始遍历 AST...")
        visitor.visit()
        
        Logger.debug("创建提取器...")
        val interfaceExtractor = InterfaceExtractor(visitor)
        val typeAliasExtractor = TypeAliasExtractor(visitor)
        
        val interfaces = visitor.getInterfaces()
        val typeAliases = visitor.getTypeAliases()
        
        Logger.info("找到 ${interfaces.size} 个 interface")
        Logger.info("找到 ${typeAliases.size} 个 type alias")
        
        // 详细日志
        Logger.verbose("Interface 列表:")
        interfaces.take(10).forEach { Logger.verbose("  - ${it.id.safeValue()}", 4) }
        if (interfaces.size > 10) Logger.verbose("  ... 还有 ${interfaces.size - 10} 个", 4)
        
        Logger.verbose("Type Alias 列表:")
        typeAliases.take(10).forEach { Logger.verbose("  - ${it.id.safeValue()}", 4) }
        if (typeAliases.size > 10) Logger.verbose("  ... 还有 ${typeAliases.size - 10} 个", 4)
        
        return Triple(visitor, interfaceExtractor, typeAliasExtractor)
    }
    
    private fun parseTypeScriptFile(): Program {
        Logger.info("输入文件: $inputPath")
        
        val file = File(inputPath)
        require(file.exists()) { "文件不存在: $inputPath" }
        
        val sourceCode = file.readText()
        Logger.info("文件大小: ${sourceCode.length} 字符")
        Logger.verbose("文件内容预览:\n${sourceCode.take(200)}${if (sourceCode.length > 200) "..." else ""}")
        
        val parserConfig = tsParseOptions {
            tsx = true
            decorators = true
        }
        
        return try {
            val program = swc.parseSync(sourceCode, parserConfig, inputPath)
            Logger.debug("解析成功，Program 类型: ${program::class.simpleName}")
            when (program) {
                is Module -> Logger.debug("Module body 大小: ${program.body?.size ?: 0}")
                is Script -> Logger.debug("Script body 大小: ${program.body?.size ?: 0}")
            }
            program
        } catch (e: Exception) {
            Logger.error("解析失败: ${e.message}")
            Logger.info("提示: 这可能是因为 @swc/types 版本与 swc-binding 不兼容", 2)
            Logger.info("建议: 尝试使用更简单的 TypeScript 文件，或更新 swc-binding", 2)
            throw IllegalStateException("TypeScript 文件解析失败", e)
        }
    }
    
    private fun processLiteralUnion(
        visitor: TsAstVisitor,
        typeAliasExtractor: TypeAliasExtractor
    ) {
        val candidates = visitor.getTypeAliases()
            .filter { typeAliasExtractor.isLiteralUnion(it) && typeAliasExtractor.isAllLiteralTypeSame(it) }
        
        Logger.debug("找到 ${candidates.size} 个字面量联合类型")
        
        candidates.forEach { typeAlias ->
            val name = typeAliasExtractor.getTypeAliasName(typeAlias)
            if (name.isEmpty()) {
                Logger.warn("跳过空名称的 type alias")
                return@forEach
            }
            
            Logger.verbose("处理字面量联合: $name")
            val properties = typeAliasExtractor.extractLiteralUnionProperties(typeAlias)
            Logger.debug("  生成 ${properties.size} 个常量属性", 4)
            
            // 记录类型别名
            properties.firstOrNull()?.type?.let { firstType ->
                TypeResolver.typeAliasMap[name] = TypeResolver.removeComment(firstType)
                Logger.verbose("  类型映射: $name -> ${TypeResolver.removeComment(firstType)}", 4)
            }
            
            // 创建 object
            addKotlinClass(KotlinClass(
                klassName = name,
                headerComment = "/**\n  * ${typeAliasExtractor.getTypeString(typeAlias)}\n  */",
                modifier = "object",
                properties = properties.toMutableList()
            ))
            Logger.verbose("  ✓ 创建 object: $name", 4)
        }
    }
    
    private fun processMixedUnion(
        visitor: TsAstVisitor,
        typeAliasExtractor: TypeAliasExtractor
    ) {
        val typesGenerator = TypesGenerator(emptyList())
        
        val candidates = visitor.getTypeAliases()
            .filter { typeAliasExtractor.isLiteralUnion(it) || typeAliasExtractor.isMixedUnion(it) }
            .filter { !typeAliasExtractor.isAllLiteralTypeSame(it) }
        
        Logger.debug("找到 ${candidates.size} 个混合联合类型")
        
        candidates.forEach { typeAlias ->
            val name = typeAliasExtractor.getTypeAliasName(typeAlias)
            val typeString = typeAliasExtractor.getTypeString(typeAlias)
            
            Logger.verbose("处理混合联合: $name")
            Logger.debug("  原始类型: $typeString", 4)
            
            val uniqueTypes = typeString.split(",").map { 
                TypeResolver.removeComment(it.trim()).replace(Regex("""Union\.U\d+<|>"""), "")
            }.toSet().toList()
            
            Logger.debug("  唯一类型数: ${uniqueTypes.size}", 4)
            
            val alias = when {
                uniqueTypes.size == 1 -> {
                    "typealias $name = ${uniqueTypes[0]}".also {
                        Logger.verbose("  生成单一类型别名", 4)
                    }
                }
                uniqueTypes.size == 2 -> {
                    "typealias $name = ${TypeResolver.transformTupleType(uniqueTypes)}".also {
                        Logger.verbose("  生成元组类型别名", 4)
                    }
                }
                uniqueTypes.size > 2 -> {
                    "typealias $name = Union.U${uniqueTypes.size}<${uniqueTypes.joinToString(", ")}>".also {
                        Logger.verbose("  生成 Union 类型别名", 4)
                    }
                }
                else -> null
            }
            
            alias?.let { typesGenerator.addTypeAlias(it) }
        }
        
        Logger.breakpoint("mixed_union_complete", mapOf("generated_aliases" to candidates.size))
    }
    
    private fun processIntersectionType(
        visitor: TsAstVisitor,
        typeAliasExtractor: TypeAliasExtractor,
        interfaceExtractor: InterfaceExtractor
    ) {
        val candidates = visitor.getTypeAliases().filter { typeAliasExtractor.isIntersectionType(it) }
        Logger.debug("找到 ${candidates.size} 个交叉类型")
        
        candidates.forEach { typeAlias ->
            val name = typeAliasExtractor.getTypeAliasName(typeAlias)
            if (name.isEmpty()) {
                Logger.warn("跳过空名称的交叉类型")
                return@forEach
            }
            
            Logger.verbose("处理交叉类型: $name")
            
            val info = typeAliasExtractor.extractIntersectionInfo(typeAlias)
            if (info == null) {
                Logger.warn("  无法提取交叉类型信息: $name")
                return@forEach
            }
            
            val (parentName, typeLiteral) = info
            Logger.debug("  父类型: $parentName", 4)
            
            // 建立继承关系
            ExtendRelationship.addRelation("Base$name", name)
            ExtendRelationship.addRelation(parentName, name)
            ExtendRelationship.addRelation("Base$name", parentName)
            Logger.verbose("  建立继承关系: Base$name -> $name -> $parentName", 4)
            
            // 创建 Base interface
            val properties = TypeResolver.extractPropertiesFromTypeLiteral(typeLiteral)
            Logger.debug("  提取 ${properties.size} 个属性", 4)
            addKotlinClass(createBaseInterface(name, properties))
            classAllPropertiesMap["Base$name"] = properties.map { it.clone() }
            
            // 创建实现类
            val parentProps = visitor.findInterface(parentName)?.let {
                interfaceExtractor.getInterfaceOwnProperties(it)
            } ?: emptyList()
            Logger.debug("  父类属性: ${parentProps.size} 个", 4)
            
            addKotlinClass(createIntersectionImplClass(name, parentName, parentProps, properties))
            Logger.verbose("  ✓ 创建交叉类型: $name", 4)
        }
    }
    
    /**
     * 创建 Base interface
     */
    private fun createBaseInterface(name: String, properties: List<KotlinProperty>): KotlinClass {
        return KotlinClass(
            klassName = "Base$name",
            annotations = mutableListOf("@SwcDslMarker", "@Serializable"),
            modifier = "sealed interface",
            properties = properties.map { it.clone().apply { defaultValue = "" } }.toMutableList()
        )
    }
    
    /**
     * 创建 Intersection 实现类
     */
    private fun createIntersectionImplClass(
        name: String,
        parentName: String,
        parentProps: List<KotlinProperty>,
        ownProps: List<KotlinProperty>
    ): KotlinClass {
        return KotlinClass(
            klassName = name,
            annotations = mutableListOf("@SwcDslMarker", "@Serializable"),
            modifier = "open class",
            parents = mutableListOf(parentName, "Base$name"),
            properties = (parentProps + ownProps.map { it.clone().apply { isOverride = true } }).toMutableList()
        )
    }
    
    private fun processRefUnion(
        visitor: TsAstVisitor,
        typeAliasExtractor: TypeAliasExtractor
    ) {
        val candidates = visitor.getTypeAliases().filter { typeAliasExtractor.isRefUnion(it) }
        Logger.debug("找到 ${candidates.size} 个引用联合类型")
        
        candidates.forEach { typeAlias ->
            val name = typeAliasExtractor.getTypeAliasName(typeAlias)
            if (name.isEmpty()) {
                Logger.warn("跳过空名称的引用联合类型")
                return@forEach
            }
            
            Logger.verbose("处理引用联合: $name")
            
            // 创建 sealed interface
            addKotlinClass(KotlinClass(
                klassName = name,
                annotations = mutableListOf("@SwcDslMarker", "@Serializable"),
                modifier = "sealed interface",
                properties = mutableListOf()
            ))
            
            // 建立继承关系
            val childTypes = typeAliasExtractor.getUnionTypeNames(typeAlias)
            Logger.debug("  子类型数: ${childTypes.size}", 4)
            Logger.verbose("  子类型: ${childTypes.joinToString(", ")}", 4)
            
            childTypes.forEach { child -> 
                ExtendRelationship.addRelation(name, child)
                Logger.logIf(childTypes.size < 5, "    添加关系: $name -> $child")
            }
            
            Logger.verbose("  ✓ 创建 sealed interface: $name", 4)
        }
    }
    
    private fun buildInheritanceRelationship(
        visitor: TsAstVisitor,
        interfaceExtractor: InterfaceExtractor
    ) {
        val interfaces = visitor.getInterfaces()
        Logger.debug("构建 ${interfaces.size} 个接口的继承关系")
        
        var relationCount = 0
        interfaces.forEach { interfaceDecl ->
            val interfaceName = interfaceDecl.id.safeValue().takeIf { it.isNotEmpty() } ?: return@forEach
            
            val parents = interfaceExtractor.extractExtends(interfaceDecl)
            Logger.logIf(parents.isNotEmpty(), "$interfaceName extends ${parents.joinToString(", ")}")
            
            parents.forEach { parent -> 
                ExtendRelationship.addRelation(parent, interfaceName)
                relationCount++
            }
        }
        
        Logger.debug("建立了 $relationCount 个继承关系")
    }
    
    private fun processInterfaces(
        visitor: TsAstVisitor,
        interfaceExtractor: InterfaceExtractor
    ) {
        val interfaces = visitor.getInterfaces()
        Logger.debug("处理 ${interfaces.size} 个接口")
        
        var processedCount = 0
        var skippedCount = 0
        
        interfaces.forEach { interfaceDecl ->
            val name = interfaceDecl.id.safeValue().takeIf { it.isNotEmpty() } 
            if (name == null) {
                skippedCount++
                Logger.warn("跳过空名称的接口")
                return@forEach
            }
            
            Logger.verbose("处理接口: $name")
            
            val kotlinClass = createKotlinClassFromInterface(interfaceDecl, interfaceExtractor)
            Logger.debug("  属性数: ${kotlinClass.properties.size}", 4)
            Logger.debug("  父类型: ${kotlinClass.parents.joinToString(", ")}", 4)
            
            classAllPropertiesMap[name] = kotlinClass.properties
            
            // 应用特殊处理
            applySpecialModifiers(kotlinClass, name)
            Logger.logIf(kotlinClass.modifier != "interface", "  特殊类型: ${kotlinClass.modifier}")
            
            // 添加类
            if (Constants.toKotlinClass.contains(name)) {
                addKotlinClass(kotlinClass.toClass())
                Logger.verbose("  ✓ 生成为 class", 4)
            } else {
                addKotlinClass(kotlinClass.toInterface())
                Logger.verbose("  ✓ 生成为 interface", 4)
            }
            
            // 生成实现类
            if (!Constants.keepInterface.contains(name)) {
                ExtendRelationship.addRelation(name, "${name}Impl")
                addKotlinClass(kotlinClass.toImplClass())
                Logger.verbose("  ✓ 生成 Impl 类", 4)
            } else {
                Logger.verbose("  跳过 Impl 类生成（在保留列表中）", 4)
            }
            
            processedCount++
        }
        
        Logger.debug("处理完成: $processedCount 个接口, 跳过 $skippedCount 个")
    }
    
    /**
     * 从 interface 创建 KotlinClass
     */
    private fun createKotlinClassFromInterface(
        interfaceDecl: TsInterfaceDeclaration,
        interfaceExtractor: InterfaceExtractor
    ): KotlinClass {
        return KotlinClass(
            klassName = interfaceDecl.id.safeValue(),
            headerComment = interfaceExtractor.extractHeaderComment(interfaceDecl),
            modifier = "interface",
            parents = interfaceExtractor.extractExtends(interfaceDecl).toMutableList(),
            properties = interfaceExtractor.getInterfaceAllProperties(interfaceDecl).toMutableList()
        )
    }
    
    /**
     * 应用特殊修饰符
     */
    private fun applySpecialModifiers(kotlinClass: KotlinClass, name: String) {
        when {
            Constants.sealedInterface.contains(name) -> {
                kotlinClass.modifier = "sealed interface"
                kotlinClass.annotations.addAll(listOf(
                    "@SwcDslMarker",
                    "@Serializable(NodeSerializer::class)"
                ))
            }
            Constants.toKotlinClass.contains(name) -> {
                kotlinClass.modifier = "class"
                kotlinClass.annotations.addAll(listOf(
                    "@SwcDslMarker",
                    "@Serializable",
                    "@SerialName(\"$name\")"
                ))
            }
        }
    }
    
    private fun generateCode() {
        val kotlinClasses = kotlinClassMap.values.toList()
        Logger.debug("准备生成代码, 总类数: ${kotlinClasses.size}")
        
        // 确保输出目录存在
        Logger.debug("创建输出目录...")
        ensureOutputDirectories()
        
        // 生成 types.kt
        Logger.debug("生成 types.kt...")
        Logger.startTimer("gen_types")
        TypesGenerator(kotlinClasses).writeToFile(outputTypesPath)
        Logger.endTimer("gen_types")
        
        // 生成 serializer.kt
        Logger.debug("生成 serializer.kt...")
        Logger.startTimer("gen_serializer")
        val astNodeList = (
            ExtendRelationship.findAllRelativeByName("Node") + 
            ExtendRelationship.findAllRelativeByName("HasSpan")
        ).distinct()
        Logger.debug("  AST 节点类型数: ${astNodeList.size}")
        SerializerGenerator().writeToFile(outputSerializerPath, astNodeList)
        Logger.endTimer("gen_serializer")
        
        // 生成 DSL
        Logger.debug("生成 DSL 扩展函数...")
        Logger.startTimer("gen_dsl")
        DslGenerator(kotlinClasses, classAllPropertiesMap).apply {
            generateExtensionFunctions()
            writeToDirectory(outputDslDir)
        }
        Logger.endTimer("gen_dsl")
        
        Logger.breakpoint("generate_complete", mapOf(
            "classes" to kotlinClasses.size,
            "ast_nodes" to astNodeList.size
        ))
    }
    
    /**
     * 确保输出目录存在
     */
    private fun ensureOutputDirectories() {
        File(outputTypesPath).parentFile?.mkdirs()
        File(outputSerializerPath).parentFile?.mkdirs()
        File(outputDslDir).mkdirs()
    }
    
    private fun addKotlinClass(kotlinClass: KotlinClass) {
        Logger.logIf(kotlinClassMap.containsKey(kotlinClass.klassName), 
            "⚠️  覆盖已存在的类: ${kotlinClass.klassName}")
        kotlinClassMap[kotlinClass.klassName] = kotlinClass
        Logger.verbose("添加类: ${kotlinClass.klassName} (${kotlinClass.modifier})", 6)
    }
}

