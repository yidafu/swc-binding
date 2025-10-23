package dev.yidafu.swc.generator.config

import com.charleskorn.kaml.Yaml
import dev.yidafu.swc.generator.util.Logger
import kotlinx.serialization.decodeFromString
import java.io.File

/**
 * 配置加载器
 */
object ConfigLoader {
    private const val DEFAULT_CONFIG_PATH = "swc-generator-config.yaml"

    /**
     * 加载配置文件
     * @param customPath 自定义配置文件路径，为 null 时使用默认路径
     * @return 配置对象
     */
    fun loadConfig(customPath: String? = null): SwcGeneratorConfig {
        val configPath = customPath ?: DEFAULT_CONFIG_PATH
        val configFile = File(configPath)

        return if (configFile.exists()) {
            try {
                Logger.debug("加载配置文件: $configPath")
                val yamlContent = configFile.readText()
                Logger.debug("YAML 内容长度: ${yamlContent.length}")

                // 使用 KAML 解析 YAML 配置
                val loadedConfig = Yaml.default.decodeFromString<SwcGeneratorConfig>(yamlContent)
                Logger.debug("配置文件解析成功")

                // 合并默认配置和加载的配置
                mergeWithDefaults(loadedConfig)
            } catch (e: Exception) {
                Logger.warn("配置文件解析失败: ${e.message}，使用默认配置")
                Logger.debug("错误详情: ${e.stackTraceToString()}")
                Logger.debug("YAML 内容预览: ${configFile.readText().take(500)}")
                getDefaultConfig()
            }
        } else {
            Logger.debug("配置文件不存在: $configPath，使用默认配置")
            getDefaultConfig()
        }
    }

    /**
     * 合并加载的配置和默认配置
     * 确保所有字段都有值，缺失的字段使用默认值
     */
    private fun mergeWithDefaults(loadedConfig: SwcGeneratorConfig): SwcGeneratorConfig {
        val defaults = getDefaultConfig()

        return SwcGeneratorConfig(
            classModifiers = ClassModifiersConfig(
                toKotlinClass = loadedConfig.classModifiers.toKotlinClass.takeIf { it.isNotEmpty() } ?: defaults.classModifiers.toKotlinClass,
                keepInterface = loadedConfig.classModifiers.keepInterface.takeIf { it.isNotEmpty() } ?: defaults.classModifiers.keepInterface,
                sealedInterface = loadedConfig.classModifiers.sealedInterface.takeIf { it.isNotEmpty() } ?: defaults.classModifiers.sealedInterface,
                propsToSnakeCase = loadedConfig.classModifiers.propsToSnakeCase.takeIf { it.isNotEmpty() } ?: defaults.classModifiers.propsToSnakeCase
            ),
            namingRules = NamingRulesConfig(
                kotlinKeywords = loadedConfig.namingRules.kotlinKeywords.takeIf { it.isNotEmpty() } ?: defaults.namingRules.kotlinKeywords,
                literalOperators = loadedConfig.namingRules.literalOperators.takeIf { it.isNotEmpty() } ?: defaults.namingRules.literalOperators
            ),
            paths = PathsConfig(
                defaultInputPath = loadedConfig.paths.defaultInputPath ?: defaults.paths.defaultInputPath,
                defaultOutputTypesPath = loadedConfig.paths.defaultOutputTypesPath ?: defaults.paths.defaultOutputTypesPath,
                defaultOutputSerializerPath = loadedConfig.paths.defaultOutputSerializerPath ?: defaults.paths.defaultOutputSerializerPath,
                defaultOutputDslDir = loadedConfig.paths.defaultOutputDslDir ?: defaults.paths.defaultOutputDslDir
            )
        )
    }

    /**
     * 获取默认配置（当前硬编码的值）
     */
    private fun getDefaultConfig(): SwcGeneratorConfig {
        return SwcGeneratorConfig(
            classModifiers = ClassModifiersConfig(
                toKotlinClass = listOf("Span"),
                keepInterface = listOf(
                    "HasDecorator",
                    "HasSpan",
                    "Node",
                    "Fn",
                    "PropBase",
                    "ExpressionBase",
                    "ClassPropertyBase",
                    "PatternBase",
                    "ClassMethodBase",
                    "BaseModuleConfig"
                ),
                sealedInterface = listOf("Node"),
                propsToSnakeCase = listOf("JsFormatOptions")
            ),
            namingRules = NamingRulesConfig(
                kotlinKeywords = mapOf(
                    "object" to "jsObject",
                    "inline" to "jsInline",
                    "in" to "jsIn",
                    "super" to "jsSuper",
                    "class" to "jsClass"
                ),
                literalOperators = mapOf(
                    "+" to "Addition",
                    "+=" to "AdditionAssignment",
                    "=" to "Assignment",
                    "&" to "BitwiseAND",
                    "&=" to "BitwiseANDAssignment",
                    "~" to "BitwiseNOT",
                    "|" to "BitwiseOR",
                    "|=" to "BitwiseORAssignment",
                    "^" to "BitwiseXOR",
                    "^=" to "BitwiseXORAssignment",
                    "," to "CommaOperator",
                    "ternary" to "Conditional",
                    "--" to "Decrement",
                    "/" to "Division",
                    "/=" to "DivisionAssignment",
                    "==" to "Equality",
                    "**" to "Exponentiation",
                    "**=" to "ExponentiationAssignment",
                    ">" to "GreaterThan",
                    ">=" to "GreaterThanOrEqual",
                    " " to "GroupingOperator",
                    "++" to "Increment",
                    "!=" to "Inequality",
                    "<<" to "LeftShift",
                    "<<=" to "LeftShiftAssignment",
                    "<" to "LessThan",
                    "<=" to "LessThanOrEqual",
                    "&&" to "LogicalAND",
                    "&&=" to "LogicalANDAssignment",
                    "!" to "LogicalNOT",
                    "||" to "LogicalOR",
                    "||=" to "LogicalORAssignment",
                    "*" to "Multiplication",
                    "*=" to "MultiplicationAssignment",
                    "??=" to "NullishCoalescingAssignment",
                    "??" to "NullishCoalescingOperator",
                    "?." to "OptionalChaining",
                    "%" to "Remainder",
                    "%=" to "RemainderAssignment",
                    ">>" to "RightShift",
                    ">>=" to "RightShiftAssignment",
                    "..." to "SpreadSyntax",
                    "===" to "StrictEquality",
                    "!==" to "StrictInequality",
                    "-" to "Subtraction",
                    "-=" to "SubtractionAssignment",
                    ">>>" to "UnsignedRightShift",
                    ">>>=" to "UnsignedRightShiftAssignment"
                )
            ),
            paths = PathsConfig(
                defaultInputPath = null,
                defaultOutputTypesPath = "../swc-binding/src/main/kotlin/dev/yidafu/swc/types/types.kt",
                defaultOutputSerializerPath = "../swc-binding/src/main/kotlin/dev/yidafu/swc/types/serializer.kt",
                defaultOutputDslDir = "../swc-binding/src/main/kotlin/dev/yidafu/swc/dsl"
            )
        )
    }
}
