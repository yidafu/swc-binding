package dev.yidafu.swc.generator.cli

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.tsParseOptions
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.extractor.*
import dev.yidafu.swc.generator.codegen.generator.DslGenerator
import dev.yidafu.swc.generator.codegen.generator.SerializerGenerator
import dev.yidafu.swc.generator.codegen.generator.TypesGenerator
import dev.yidafu.swc.generator.core.model.KotlinClass
import dev.yidafu.swc.generator.core.model.KotlinProperty
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.config.Constants
import dev.yidafu.swc.generator.util.*
import dev.yidafu.swc.types.*
import kotlinx.serialization.json.*
import kotlinx.cli.*
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val parser = ArgParser("swc-generator-kt")
    
    // 定义命令行参数
    val input by parser.option(
        ArgType.String,
        shortName = "i",
        fullName = "input",
        description = "输入的 TypeScript 定义文件路径 (.d.ts)"
    )
    
    val outputTypes by parser.option(
        ArgType.String,
        shortName = "o",
        fullName = "output-types",
        description = "输出 types.kt 文件路径"
    )
    
    val outputSerializer by parser.option(
        ArgType.String,
        shortName = "s",
        fullName = "output-serializer",
        description = "输出 serializer.kt 文件路径"
    )
    
    val outputDsl by parser.option(
        ArgType.String,
        shortName = "d",
        fullName = "output-dsl",
        description = "输出 DSL 文件夹路径"
    )
    
    val verbose by parser.option(
        ArgType.Boolean,
        shortName = "v",
        fullName = "verbose",
        description = "启用详细日志输出"
    ).default(false)
    
    val debug by parser.option(
        ArgType.Boolean,
        fullName = "debug",
        description = "启用调试日志输出"
    ).default(false)
    
    val dryRun by parser.option(
        ArgType.Boolean,
        fullName = "dry-run",
        description = "模拟运行，不实际生成文件"
    ).default(false)
    
    val typesOnly by parser.option(
        ArgType.Boolean,
        fullName = "types-only",
        description = "仅生成 types.kt，跳过 serializer.kt 和 DSL"
    ).default(false)
    
    // 位置参数（可选）
    val inputFile by parser.argument(
        ArgType.String,
        description = "输入的 TypeScript 定义文件（也可以用 -i 指定）"
    ).optional()
    
    try {
        parser.parse(args)
        
        // 设置日志级别
        if (verbose) {
            System.setProperty("VERBOSE", "true")
        }
        if (debug) {
            System.setProperty("DEBUG", "true")
        }
        if (dryRun) {
            System.setProperty("DRY_RUN", "true")
        }
        
        // 确定输入文件（优先级：-i 参数 > 位置参数 > 环境变量 > 默认值）
        // 注意：如果没有提供输入文件，SwcGeneratorKt 类会使用默认值
        val inputPath = input ?: inputFile
        
        val config = GeneratorConfig(
            inputPath = inputPath,
            outputTypesPath = outputTypes,
            outputSerializerPath = outputSerializer,
            outputDslDir = outputDsl,
            dryRun = dryRun,
            typesOnly = typesOnly
        )
        
        // 打印配置信息
        printConfiguration(config)
        
        Logger.header("SWC Generator Kotlin - 开始生成代码")
        Logger.separator()
        
        val generator = SwcGeneratorKt(config)
        generator.run()
        
        Logger.separator()
        Logger.header("代码生成完成！")
        
    } catch (e: IllegalArgumentException) {
        Logger.error("参数错误: ${e.message}")
        println()
        println("运行 --help 查看使用方法")
        exitProcess(1)
    } catch (e: Exception) {
        Logger.separator()
        Logger.error("代码生成失败: ${e.message}")
        val debugEnabled = System.getProperty("DEBUG")?.toBoolean() ?: false
        if (debugEnabled) {
            e.printStackTrace()
        }
        exitProcess(1)
    }
}

/**
 * 打印使用说明并退出
 */
private fun printUsageAndExit(parser: ArgParser) {
    Logger.error("错误: 必须指定输入文件")
    println()
    println("使用方法:")
    println("  swc-generator-kt <input.d.ts>")
    println("  swc-generator-kt -i <input.d.ts> -o <output-types.kt>")
    println()
    println("示例:")
    println("  # 使用默认输出路径")
    println("  swc-generator-kt tests/01-base-types.d.ts")
    println()
    println("  # 指定所有输出路径")
    println("  swc-generator-kt -i input.d.ts \\")
    println("    -o output/types.kt \\")
    println("    -s output/serializer.kt \\")
    println("    -d output/dsl")
    println()
    println("  # 启用详细日志")
    println("  swc-generator-kt -i input.d.ts -v")
    println()
    println("运行 --help 查看完整选项列表")
    exitProcess(1)
}

/**
 * 打印配置信息
 */
private fun printConfiguration(config: GeneratorConfig) {
    if (System.getProperty("VERBOSE")?.toBoolean() == true) {
        Logger.separator()
        Logger.info("配置信息:")
        Logger.info("  输入文件: ${config.inputPath}", 2)
        Logger.info("  输出 types: ${config.outputTypesPath ?: "(使用默认)"}", 2)
        Logger.info("  输出 serializer: ${config.outputSerializerPath ?: "(使用默认)"}", 2)
        Logger.info("  输出 DSL: ${config.outputDslDir ?: "(使用默认)"}", 2)
        if (config.dryRun) {
            Logger.info("  模式: 模拟运行（不会生成文件）", 2)
        }
        if (config.typesOnly) {
            Logger.info("  仅生成 types: 是", 2)
        }
        Logger.separator()
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
    val dryRun: Boolean = false,
    val typesOnly: Boolean = false
)



class SwcGeneratorKt(private val config: GeneratorConfig = GeneratorConfig()) {
    companion object {
        private const val RECOMMENDED_SWC_TYPES_VERSION = "0.1.25"
        private const val MIN_SUPPORTED_VERSION = "0.1.20"
    }
    
    private val swc = SwcNative()
    private val kotlinClassMap = mutableMapOf<String, KotlinClass>()
    private val classAllPropertiesMap = mutableMapOf<String, List<KotlinProperty>>()
    
    // 保存原始 JSON 字符串，供 visitor 使用
    private var astJsonString: String = ""
    
    // 配置路径 - 项目根目录（swc-generator-kt 的父目录）
    private val projectRoot = Paths.get("").toAbsolutePath().parent.toString()
    
    // 输入路径优先级：命令行参数 > 环境变量 > 默认值
    private val inputPath = config.inputPath 
        ?: System.getenv("SWC_TYPES_PATH") 
        // 使用完整的 @swc/types 文件
        ?: "$projectRoot/swc-generator/node_modules/.pnpm/@swc+types@0.1.5/node_modules/@swc/types/index.d.ts"
    
    private val outputTypesPath = config.outputTypesPath 
        ?: "$projectRoot/swc-binding/src/main/kotlin/dev/yidafu/swc/types/types.kt"
    private val outputSerializerPath = config.outputSerializerPath 
        ?: "$projectRoot/swc-binding/src/main/kotlin/dev/yidafu/swc/types/serializer.kt"
    private val outputDslDir = config.outputDslDir 
        ?: "$projectRoot/swc-binding/src/main/kotlin/dev/yidafu/swc/dsl"
    
    fun run() {
        Logger.setTotalSteps(9)
        Logger.startTimer("total")
        
        // 版本兼容性检查
        checkVersionCompatibility()
        
        // swc-binding 可用性检查
        if (!checkSwcBindingAvailability()) {
            Logger.error("swc-binding 不可用或存在兼容性问题")
            suggestFallbackOptions()
            throw IllegalStateException("swc-binding 环境检查失败")
        }
        
        // 1. 解析 TypeScript 文件
        Logger.step("解析 TypeScript 文件...")
        Logger.startTimer("parse")
        val program = parseTypeScriptFile()
        Logger.endTimer("parse")
        Logger.breakpoint("parse_complete", mapOf("program_type" to program.type))
        
        // 2. 遍历 AST
        Logger.step("遍历 AST 提取类型定义...")
        Logger.startTimer("extract")
        val (visitor, interfaceExtractor, typeAliasExtractor) = extractTypes()
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
        
        // 提示用户生成模式
        if (config.typesOnly) {
            Logger.info("已完成 types.kt 生成（跳过 serializer 和 DSL）")
        }
    }
    
    /**
     * 提取类型定义
     */
    private fun extractTypes(): Triple<TsAstVisitor, InterfaceExtractor, TypeAliasExtractor> {
        Logger.debug("创建 AST 访问器...")
        val visitor = TsAstVisitor(astJsonString)
        
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
        interfaces.take(10).forEach { Logger.verbose("  - ${it.getInterfaceName() ?: "unknown"}", 4) }
        if (interfaces.size > 10) Logger.verbose("  ... 还有 ${interfaces.size - 10} 个", 4)
        
        Logger.verbose("Type Alias 列表:")
        typeAliases.take(10).forEach { Logger.verbose("  - ${it.getTypeAliasName() ?: "unknown"}", 4) }
        if (typeAliases.size > 10) Logger.verbose("  ... 还有 ${typeAliases.size - 10} 个", 4)
        
        return Triple(visitor, interfaceExtractor, typeAliasExtractor)
    }
    
    /**
     * 检查 swc-binding 是否可用
     */
    private fun checkSwcBindingAvailability(): Boolean {
        try {
            // 尝试解析一个最简单的 TypeScript 代码
            val testCode = "interface Test { value: string }"
            val optionsJson = "{\"syntax\":\"typescript\",\"tsx\":false}"
            swc.parseSync(testCode, optionsJson, "test.d.ts")
            Logger.debug("swc-binding 预检查通过")
            return true
        } catch (e: Exception) {
            Logger.warn("swc-binding 预检查失败: ${e.message}")
            return false
        }
    }
    
    /**
     * 检查 @swc/types 版本兼容性
     */
    private fun checkVersionCompatibility() {
        // 检查 package.json 是否存在
        val packageJsonFile = File("package.json")
        if (!packageJsonFile.exists()) {
            Logger.warn("未找到 package.json，无法验证 @swc/types 版本")
            Logger.info("推荐使用 @swc/types@$RECOMMENDED_SWC_TYPES_VERSION", 4)
            return
        }
        
        try {
            val packageJson = Json.parseToJsonElement(packageJsonFile.readText()).jsonObject
            val dependencies = packageJson["dependencies"]?.jsonObject
            val swcTypesVersion = dependencies?.get("@swc/types")?.jsonPrimitive?.content
            
            if (swcTypesVersion == null) {
                Logger.warn("package.json 中未找到 @swc/types 依赖")
                Logger.info("推荐版本: @swc/types@$RECOMMENDED_SWC_TYPES_VERSION", 4)
                return
            }
            
            // 清理版本号（去除 ^ 和 ~ 前缀）
            val cleanVersion = swcTypesVersion.removePrefix("^").removePrefix("~")
            
            Logger.debug("检测到 @swc/types 版本: $cleanVersion")
            
            // 版本比较（简单实现）
            if (cleanVersion != RECOMMENDED_SWC_TYPES_VERSION) {
                Logger.warn("检测到 @swc/types@$cleanVersion")
                Logger.warn("推荐版本: @swc/types@$RECOMMENDED_SWC_TYPES_VERSION")
                Logger.info("不同版本可能导致解析失败或生成不正确的代码", 4)
            } else {
                Logger.debug("版本检查通过: @swc/types@$cleanVersion")
            }
            
        } catch (e: Exception) {
            Logger.debug("版本检查失败: ${e.message}")
            // 不中断执行，只是警告
        }
    }
    
    private fun parseTypeScriptFile(): AstNode {
        Logger.info("输入文件: $inputPath")
        
        val file = File(inputPath)
        require(file.exists()) { "文件不存在: $inputPath" }
        
        val sourceCode = file.readText()
        Logger.info("文件大小: ${sourceCode.length} 字符")
        Logger.verbose("文件内容预览:\n${sourceCode.take(200)}${if (sourceCode.length > 200) "..." else ""}")
        
        // 检测是否是 @swc/types
        val isSwcTypes = inputPath.contains("@swc/types") || inputPath.contains("index.d.ts")
        
        if (isSwcTypes) {
            Logger.warn("检测到尝试解析 @swc/types 或大型类型文件")
            Logger.warn("这可能因循环依赖导致失败（详见 ARCHITECTURE.md）")
        }
        
        val parserConfig = tsParseOptions {
            tsx = true
            decorators = true
        }
        
        return try {
            // 使用返回 JSON 字符串的版本
            // 手动构造 JSON 字符串以确保包含所有必需字段
            val optionsJson = buildString {
                append("{\"syntax\":\"typescript\"")
                if (parserConfig.tsx == true) append(",\"tsx\":true")
                if (parserConfig.decorators == true) append(",\"decorators\":true")
                if (parserConfig.target != null) append(",\"target\":\"${parserConfig.target}\"")
                if (parserConfig.comments == true) append(",\"comments\":true")
                if (parserConfig.script == true) append(",\"script\":true")
                append("}")
            }
            Logger.debug("Parser options: $optionsJson")
            val jsonString = swc.parseSync(sourceCode, optionsJson, inputPath)
            Logger.debug("解析成功，JSON 长度: ${jsonString.length}")
            
            // 保存 JSON 字符串供后续使用
            astJsonString = jsonString
            
            // 创建 AstNode
            val program = AstNode.fromJson(jsonString)
            Logger.debug("Program 类型: ${program.type}")
            
            when (program.type) {
                "Module" -> {
                    val bodySize = program.getNodes("body").size
                    Logger.debug("Module body 大小: $bodySize")
                }
                "Script" -> {
                    val bodySize = program.getNodes("body").size
                    Logger.debug("Script body 大小: $bodySize")
                }
            }
            
            program
            
        } catch (e: kotlinx.serialization.SerializationException) {
            Logger.error("序列化错误: ${e.message}")
            handleSerializationError(e, isSwcTypes)
            throw IllegalStateException("TypeScript 文件解析失败", e)
        } catch (e: Exception) {
            Logger.error("解析失败: ${e.message}")
            handleParseError(e, isSwcTypes)
            throw IllegalStateException("TypeScript 文件解析失败", e)
        }
    }
    
    /**
     * 处理序列化错误
     */
    private fun handleSerializationError(e: Exception, isSwcTypes: Boolean) {
        Logger.separator()
        Logger.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
        Logger.error("序列化错误 - 这是循环依赖问题")
        Logger.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
        
        if (isSwcTypes) {
            Logger.error("")
            Logger.error("原因：")
            Logger.error("  swc-generator-kt 依赖 swc-binding 来解析 TypeScript")
            Logger.error("  但 swc-binding 的类型定义本身需要从 @swc/types 生成")
            Logger.error("  这形成了循环依赖")
            Logger.error("")
            Logger.error("解决方案：")
            Logger.error("  1. 使用 TypeScript 版本的 swc-generator 生成 @swc/types")
            Logger.error("     cd ../swc-generator && npm run dev")
            Logger.error("")
            Logger.error("  2. 或者使用 swc-generator-kt 处理您自己的类型文件")
            Logger.error("     ./gradlew :swc-generator-kt:run --args=\"-i your-types.d.ts\"")
        } else {
            Logger.error("")
            Logger.error("可能原因：")
            Logger.error("  1. TypeScript 文件使用了 swc-binding 不支持的类型")
            Logger.error("  2. @swc/types 版本与 swc-binding 不兼容")
            Logger.error("")
            Logger.error("建议：")
            Logger.error("  1. 简化您的 TypeScript 类型定义")
            Logger.error("  2. 使用测试文件验证：")
            Logger.error("     ./gradlew :swc-generator-kt:run --args=\"-i test-simple.d.ts\"")
        }
        
        Logger.error("")
        Logger.error("详细信息请参考：")
        Logger.error("  - ARCHITECTURE.md（循环依赖架构说明）")
        Logger.error("  - TROUBLESHOOTING.md（故障排除指南）")
        Logger.separator()
    }
    
    /**
     * 处理解析错误
     */
    private fun handleParseError(e: Exception, isSwcTypes: Boolean) {
        Logger.separator()
        Logger.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
        Logger.error("解析错误")
        Logger.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
        Logger.error("")
        Logger.error("错误信息: ${e.message}")
        Logger.error("")
        Logger.error("可能原因：")
        Logger.error("  1. TypeScript 语法不被 SWC 支持")
        Logger.error("  2. 文件编码问题")
        Logger.error("  3. swc-binding JNI 错误")
        Logger.error("")
        Logger.error("建议：")
        Logger.error("  1. 检查 TypeScript 文件语法")
        Logger.error("  2. 使用简单的测试文件验证环境")
        Logger.error("  3. 查看 TROUBLESHOOTING.md")
        Logger.separator()
    }
    
    /**
     * 建议降级方案
     */
    private fun suggestFallbackOptions() {
        Logger.separator()
        Logger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
        Logger.info("降级方案建议")
        Logger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
        Logger.info("")
        Logger.info("选项 1: 使用 TypeScript 版本的 swc-generator")
        Logger.info("  cd ../swc-generator")
        Logger.info("  npm install")
        Logger.info("  npm run dev")
        Logger.info("")
        Logger.info("选项 2: 测试简单类型文件")
        Logger.info("  ./gradlew :swc-generator-kt:run --args=\"-i test-simple.d.ts\"")
        Logger.info("")
        Logger.info("选项 3: 检查 swc-binding 状态")
        Logger.info("  ./gradlew :swc-binding:build")
        Logger.separator()
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
            val interfaceName = interfaceDecl.getInterfaceName() ?: return@forEach
            
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
            val name = interfaceDecl.getInterfaceName()
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
        interfaceDecl: AstNode,
        interfaceExtractor: InterfaceExtractor
    ): KotlinClass {
        return KotlinClass(
            klassName = interfaceDecl.getInterfaceName() ?: "",
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
        
        val isDryRun = config.dryRun
        val typesOnly = config.typesOnly
        
        if (isDryRun) {
            Logger.warn("模拟运行模式：将跳过实际文件写入")
        }
        
        if (typesOnly) {
            Logger.info("仅生成 types.kt 模式")
        }
        
        // 确保输出目录存在
        if (!isDryRun) {
            Logger.debug("创建输出目录...")
            ensureOutputDirectories()
        }
        
        // 生成 types.kt
        Logger.debug("生成 types.kt...")
        Logger.startTimer("gen_types")
        if (isDryRun) {
            Logger.info("  [DRY-RUN] 将生成到: $outputTypesPath", 2)
            Logger.info("  [DRY-RUN] 类数量: ${kotlinClasses.size}", 2)
        } else {
            TypesGenerator(kotlinClasses).writeToFile(outputTypesPath)
        }
        Logger.endTimer("gen_types")
        
        // 如果只生成 types，则跳过后续步骤
        if (typesOnly) {
            Logger.info("跳过 serializer.kt 和 DSL 生成")
            return
        }
        
        // 生成 serializer.kt
        Logger.debug("生成 serializer.kt...")
        Logger.startTimer("gen_serializer")
        val astNodeList = (
            ExtendRelationship.findAllRelativeByName("Node") + 
            ExtendRelationship.findAllRelativeByName("HasSpan")
        ).distinct()
        Logger.debug("  AST 节点类型数: ${astNodeList.size}")
        if (isDryRun) {
            Logger.info("  [DRY-RUN] 将生成到: $outputSerializerPath", 2)
            Logger.info("  [DRY-RUN] AST 节点数: ${astNodeList.size}", 2)
        } else {
            SerializerGenerator().writeToFile(outputSerializerPath, astNodeList)
        }
        Logger.endTimer("gen_serializer")
        
        // 生成 DSL
        Logger.debug("生成 DSL 扩展函数...")
        Logger.startTimer("gen_dsl")
        if (isDryRun) {
            Logger.info("  [DRY-RUN] 将生成到: $outputDslDir", 2)
            val generator = DslGenerator(kotlinClasses, classAllPropertiesMap)
            generator.generateExtensionFunctions()
            val functionCount = kotlinClasses.count { it.modifier in listOf("interface", "sealed interface") }
            Logger.info("  [DRY-RUN] DSL 函数数（估计）: ~$functionCount", 2)
        } else {
            DslGenerator(kotlinClasses, classAllPropertiesMap).apply {
                generateExtensionFunctions()
                writeToDirectory(outputDslDir)
            }
        }
        Logger.endTimer("gen_dsl")
        
        Logger.breakpoint("generate_complete", mapOf(
            "classes" to kotlinClasses.size,
            "ast_nodes" to astNodeList.size,
            "dry_run" to isDryRun
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

