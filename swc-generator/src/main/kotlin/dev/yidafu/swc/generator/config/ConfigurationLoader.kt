package dev.yidafu.swc.generator.config

import com.charleskorn.kaml.Yaml
import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.util.Logger
import kotlinx.serialization.Serializable
import java.io.File
import java.nio.file.Paths

/**
 * 配置加载器
 * 负责从各种源加载配置
 */
class ConfigurationLoader {

    /**
     * YAML 配置数据类
     * 用于序列化/反序列化 YAML 配置文件
     */
    @Serializable
    data class YamlConfiguration(
        val input: YamlInputConfig? = null,
        val output: YamlOutputConfig? = null,
        val rules: YamlRulesConfig? = null,
        val behavior: YamlBehaviorConfig? = null
    )

    @Serializable
    data class YamlInputConfig(
        val inputPath: String? = null,
        val verbose: Boolean? = null,
        val debug: Boolean? = null
    )

    @Serializable
    data class YamlOutputConfig(
        val outputTypesPath: String? = null,
        val outputSerializerPath: String? = null,
        val outputDslDir: String? = null,
        val dryRun: Boolean? = null
    )

    @Serializable
    data class YamlRulesConfig(
        val classModifiers: YamlClassModifierRules? = null,
        val typeMapping: YamlTypeMappingRules? = null,
        val naming: YamlNamingRules? = null,
        val modifiers: YamlModifierRules? = null
    )

    @Serializable
    data class YamlClassModifierRules(
        val toKotlinClass: List<String>? = null,
        val keepInterface: List<String>? = null,
        val sealedInterface: List<String>? = null,
        val propsToSnakeCase: List<String>? = null,
        val literalUnionToTypealias: List<String>? = null
    )

    @Serializable
    data class YamlTypeMappingRules(
        val jsToKotlinTypeMap: Map<String, String>? = null,
        val propertyTypeOverrides: Map<String, String>? = null
    )

    @Serializable
    data class YamlNamingRules(
        val kotlinKeywordMap: Map<String, String>? = null,
        val reservedWords: List<String>? = null
    )

    @Serializable
    data class YamlModifierRules(
        val importantInterfaces: List<String>? = null,
        val skipClassPatterns: List<String>? = null,
        val skipDslReceivers: List<String>? = null
    )

    @Serializable
    data class YamlBehaviorConfig(
        val enableCaching: Boolean? = null,
        val enableParallelProcessing: Boolean? = null
    )

    /**
     * 从文件加载配置
     */
    fun loadFromFile(configPath: String?): GeneratorResult<Configuration> {
        return try {
            val configFile = when {
                configPath != null -> File(configPath)
                else -> findDefaultConfigFile()
            }

            if (!configFile.exists()) {
                Logger.warn("配置文件不存在: ${configFile.absolutePath}，使用默认配置")
                return GeneratorResultFactory.success(Configuration.default())
            }

            Logger.info("加载配置文件: ${configFile.absolutePath}")
            val config = loadYamlConfiguration(configFile)
            GeneratorResultFactory.success(config)
        } catch (e: Exception) {
            Logger.error("加载配置文件失败: ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.CONFIG_ERROR,
                message = "Failed to load configuration: ${e.message}",
                cause = e
            )
        }
    }

    /**
     * 从命令行参数创建配置
     */
    fun loadFromArgs(
        inputPath: String?,
        outputTypesPath: String?,
        outputSerializerPath: String?,
        outputDslDir: String?,
        verbose: Boolean = false,
        debug: Boolean = false,
        dryRun: Boolean = false
    ): Configuration {
        val projectRoot = Paths.get("").toAbsolutePath().parent.toString()

        return Configuration(
            input = InputConfig(
                inputPath = inputPath ?: "node_modules/@swc/types/index.d.ts",
                verbose = verbose,
                debug = debug
            ),
            output = OutputConfig(
                outputTypesPath = outputTypesPath ?: "$projectRoot/swc-binding/src/main/kotlin/dev/yidafu/swc/generated/ast",
                outputSerializerPath = outputSerializerPath ?: "$projectRoot/swc-binding/src/main/kotlin/dev/yidafu/swc/generated/serializer.kt",
                outputDslDir = outputDslDir ?: "$projectRoot/swc-binding/src/main/kotlin/dev/yidafu/swc/generated/dsl",
                dryRun = dryRun
            ),
            rules = RulesConfig.default(),
            behavior = BehaviorConfig.default()
        )
    }

    /**
     * 查找默认配置文件
     */
    private fun findDefaultConfigFile(): File {
        val possiblePaths = listOf(
            "swc-generator-config.yaml",
            "swc-generator-config.yml",
            "config/swc-generator.yaml",
            "config/swc-generator.yml"
        )

        for (path in possiblePaths) {
            val file = File(path)
            if (file.exists()) {
                return file
            }
        }

        // 如果找不到配置文件，返回一个不存在的文件
        return File("swc-generator-config.yaml")
    }

    /**
     * 加载 YAML 配置文件
     * 使用 kaml 库进行完整的结构化解析
     */
    private fun loadYamlConfiguration(configFile: File): Configuration {
        return try {
            val yamlContent = configFile.readText()
            val yaml = Yaml.default

            // 使用 kaml 解析 YAML
            val yamlConfig = yaml.decodeFromString(YamlConfiguration.serializer(), yamlContent)

            // 转换为内部 Configuration 对象，并基于配置文件位置规范化相对路径
            val config = convertYamlToConfiguration(yamlConfig)
            normalizePathsByConfigLocation(config, configFile.parentFile ?: File("."))
        } catch (e: Exception) {
            Logger.warn("YAML 解析失败: ${e.message}，使用默认配置")
            Logger.debug("YAML 解析错误详情: ${e.stackTraceToString()}")
            Configuration.default()
        }
    }

    /**
     * 将配置中的相对路径（输入与输出路径）按配置文件所在目录解析为绝对路径。
     * 这样即便从项目根目录执行，也能正确定位到 swc-generator 下的 node_modules 与输出目录。
     */
    private fun normalizePathsByConfigLocation(
        configuration: Configuration,
        baseDir: File
    ): Configuration {
        fun resolve(path: String?): String? {
            if (path.isNullOrBlank()) return path
            val f = File(path)
            return if (f.isAbsolute) f.absolutePath else File(baseDir, path).absolutePath
        }

        val normalizedInput = configuration.input.copy(
            inputPath = resolve(configuration.input.inputPath) ?: configuration.input.inputPath
        )
        val normalizedOutput = configuration.output.copy(
            outputTypesPath = resolve(configuration.output.outputTypesPath),
            outputSerializerPath = resolve(configuration.output.outputSerializerPath),
            outputDslDir = resolve(configuration.output.outputDslDir)
        )
        return configuration.copy(input = normalizedInput, output = normalizedOutput)
    }

    /**
     * 将 YAML 配置转换为内部 Configuration 对象
     */
    private fun convertYamlToConfiguration(yamlConfig: YamlConfiguration): Configuration {
        val defaults = Configuration.default()

        return Configuration(
            input = yamlConfig.input?.let { yamlInput ->
                InputConfig(
                    inputPath = yamlInput.inputPath ?: defaults.input.inputPath,
                    verbose = yamlInput.verbose ?: defaults.input.verbose,
                    debug = yamlInput.debug ?: defaults.input.debug
                )
            } ?: defaults.input,

            output = yamlConfig.output?.let { yamlOutput ->
                OutputConfig(
                    outputTypesPath = yamlOutput.outputTypesPath ?: defaults.output.outputTypesPath,
                    outputSerializerPath = yamlOutput.outputSerializerPath ?: defaults.output.outputSerializerPath,
                    outputDslDir = yamlOutput.outputDslDir ?: defaults.output.outputDslDir,
                    dryRun = yamlOutput.dryRun ?: defaults.output.dryRun
                )
            } ?: defaults.output,

            rules = yamlConfig.rules?.let { yamlRules ->
                RulesConfig(
                    classModifiers = yamlRules.classModifiers?.let { yamlClassModifiers ->
                        ClassModifierRules(
                            toKotlinClass = yamlClassModifiers.toKotlinClass ?: defaults.rules.classModifiers.toKotlinClass,
                            keepInterface = yamlClassModifiers.keepInterface ?: defaults.rules.classModifiers.keepInterface,
                            sealedInterface = yamlClassModifiers.sealedInterface ?: defaults.rules.classModifiers.sealedInterface,
                            propsToSnakeCase = yamlClassModifiers.propsToSnakeCase ?: defaults.rules.classModifiers.propsToSnakeCase,
                            literalUnionToTypealias = yamlClassModifiers.literalUnionToTypealias ?: defaults.rules.classModifiers.literalUnionToTypealias
                        )
                    } ?: defaults.rules.classModifiers,

                    typeMapping = yamlRules.typeMapping?.let { yamlTypeMapping ->
                        TypeMappingRules(
                            jsToKotlinTypeMap = yamlTypeMapping.jsToKotlinTypeMap ?: defaults.rules.typeMapping.jsToKotlinTypeMap,
                            propertyTypeOverrides = yamlTypeMapping.propertyTypeOverrides ?: defaults.rules.typeMapping.propertyTypeOverrides
                        )
                    } ?: defaults.rules.typeMapping,

                    naming = yamlRules.naming?.let { yamlNaming ->
                        NamingRules(
                            kotlinKeywordMap = yamlNaming.kotlinKeywordMap ?: defaults.rules.naming.kotlinKeywordMap,
                            reservedWords = yamlNaming.reservedWords?.toSet() ?: defaults.rules.naming.reservedWords
                        )
                    } ?: defaults.rules.naming,

                    modifiers = yamlRules.modifiers?.let { yamlModifiers ->
                        ModifierRules(
                            importantInterfaces = yamlModifiers.importantInterfaces ?: defaults.rules.modifiers.importantInterfaces,
                            skipClassPatterns = yamlModifiers.skipClassPatterns ?: defaults.rules.modifiers.skipClassPatterns,
                            skipDslReceivers = yamlModifiers.skipDslReceivers ?: defaults.rules.modifiers.skipDslReceivers
                        )
                    } ?: defaults.rules.modifiers
                )
            } ?: defaults.rules,

            behavior = yamlConfig.behavior?.let { yamlBehavior ->
                BehaviorConfig(
                    enableCaching = yamlBehavior.enableCaching ?: defaults.behavior.enableCaching,
                    enableParallelProcessing = yamlBehavior.enableParallelProcessing ?: defaults.behavior.enableParallelProcessing
                )
            } ?: defaults.behavior
        )
    }

    /**
     * 生成示例配置文件
     * 将默认配置写入 YAML 文件
     */
    fun generateSampleConfig(outputPath: String): GeneratorResult<Unit> {
        return try {
            val sampleConfig = YamlConfiguration(
                input = YamlInputConfig(
                    inputPath = "node_modules/@swc/types/index.d.ts",
                    verbose = false,
                    debug = false
                ),
                output = YamlOutputConfig(
                    outputTypesPath = "../swc-binding/src/main/kotlin/dev/yidafu/swc/generated/ast",
                    outputSerializerPath = "../swc-binding/src/main/kotlin/dev/yidafu/swc/generated/serializer.kt",
                    outputDslDir = "../swc-binding/src/main/kotlin/dev/yidafu/swc/generated/dsl",
                    dryRun = false
                ),
                rules = YamlRulesConfig(
                    classModifiers = YamlClassModifierRules(
                        toKotlinClass = emptyList(),
                        keepInterface = emptyList(),
                        sealedInterface = emptyList(),
                        propsToSnakeCase = emptyList(),
                        literalUnionToTypealias = emptyList()
                    ),
                    typeMapping = YamlTypeMappingRules(
                        jsToKotlinTypeMap = emptyMap(),
                        propertyTypeOverrides = mapOf(
                            "global_defs" to "Map<String, String>",
                            "targets" to "Map<String, String>",
                            "sequences" to "Boolean",
                            "toplevel" to "String?",
                            "pureGetters" to "String?",
                            "topRetain" to "String?"
                        )
                    ),
                    naming = YamlNamingRules(
                        kotlinKeywordMap = mapOf(
                            "object" to "jsObject",
                            "inline" to "jsInline",
                            "in" to "jsIn",
                            "super" to "jsSuper",
                            "class" to "jsClass",
                            "interface" to "jsInterface",
                            "fun" to "jsFun",
                            "val" to "jsVal",
                            "var" to "jsVar",
                            "when" to "jsWhen",
                            "is" to "jsIs",
                            "as" to "jsAs",
                            "import" to "jsImport",
                            "package" to "jsPackage"
                        ),
                        reservedWords = emptyList()
                    ),
                    modifiers = YamlModifierRules(
                        importantInterfaces = emptyList(),
                        skipClassPatterns = emptyList(),
                        skipDslReceivers = listOf("HasSpan", "HasDecorator")
                    )
                ),
                behavior = YamlBehaviorConfig(
                    enableCaching = true,
                    enableParallelProcessing = false
                )
            )

            val yaml = Yaml.default
            val yamlContent = yaml.encodeToString(YamlConfiguration.serializer(), sampleConfig)

            val outputFile = File(outputPath)
            outputFile.writeText(yamlContent)

            Logger.success("示例配置文件已生成: ${outputFile.absolutePath}")
            GeneratorResultFactory.success(Unit)
        } catch (e: Exception) {
            Logger.error("生成示例配置文件失败: ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.CONFIG_YAML_PARSE_ERROR,
                message = "Failed to generate sample config: ${e.message}",
                cause = e
            )
        }
    }
    fun mergeConfigurations(
        fileConfig: Configuration,
        argsConfig: Configuration
    ): Configuration {
        return Configuration(
            input = argsConfig.input.takeIf { it.inputPath != "node_modules/@swc/types/index.d.ts" } ?: fileConfig.input,
            output = argsConfig.output.takeIf { it.outputTypesPath != null || it.outputSerializerPath != null || it.outputDslDir != null } ?: fileConfig.output,
            rules = fileConfig.rules, // 规则配置优先使用文件配置
            behavior = argsConfig.behavior.takeIf { it.enableCaching != true || it.enableParallelProcessing != true } ?: fileConfig.behavior
        )
    }
}
