package dev.yidafu.swc.generator.config

import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import java.io.File

/**
 * 统一配置接口
 * 整合所有配置相关数据
 */
data class Configuration(
    val input: InputConfig,
    val output: OutputConfig,
    val rules: RulesConfig,
    val behavior: BehaviorConfig
) {
    companion object {
        /**
         * 默认配置
         */
        fun default(): Configuration = Configuration(
            input = InputConfig.default(),
            output = OutputConfig.default(),
            rules = RulesConfig.default(),
            behavior = BehaviorConfig.default()
        )
    }

    /**
     * 验证配置有效性
     */
    fun validate(): GeneratorResult<Unit> {
        // 验证输入路径
        if (input.inputPath.isBlank()) {
            return GeneratorResultFactory.failure(
                ErrorCode.CONFIG_INVALID_PATH,
                "输入路径不能为空"
            )
        }

        // 验证输入文件存在
        val inputFile = File(input.inputPath)
        if (!inputFile.exists()) {
            return GeneratorResultFactory.failure(
                ErrorCode.CONFIG_INVALID_PATH,
                "输入文件不存在: ${input.inputPath}"
            )
        }

        // 验证输出路径
        if (!output.dryRun) {
            if (output.outputTypesPath.isNullOrBlank() &&
                output.outputSerializerPath.isNullOrBlank() &&
                output.outputDslDir.isNullOrBlank()
            ) {
                return GeneratorResultFactory.failure(
                    ErrorCode.CONFIG_INVALID_PATH,
                    "至少需要指定一个输出路径"
                )
            }
        }

        return GeneratorResultFactory.success(Unit)
    }
}

/**
 * 输入配置
 */
data class InputConfig(
    val inputPath: String,
    val verbose: Boolean = false,
    val debug: Boolean = false
) {
    companion object {
        fun default(): InputConfig = InputConfig(
            inputPath = "node_modules/@swc/types/index.d.ts"
        )
    }
}

/**
 * 输出配置
 */
data class OutputConfig(
    val outputTypesPath: String?,
    val outputSerializerPath: String?,
    val outputDslDir: String?,
    val dryRun: Boolean = false
) {
    companion object {
        fun default(): OutputConfig = OutputConfig(
            outputTypesPath = null,
            outputSerializerPath = null,
            outputDslDir = null
        )
    }
}

/**
 * 规则配置
 */
data class RulesConfig(
    val classModifiers: ClassModifierRules,
    val typeMapping: TypeMappingRules,
    val naming: NamingRules,
    val modifiers: ModifierRules
) {
    companion object {
        fun default(): RulesConfig = RulesConfig(
            classModifiers = ClassModifierRules.default(),
            typeMapping = TypeMappingRules.default(),
            naming = NamingRules.default(),
            modifiers = ModifierRules.default()
        )
    }
}

/**
 * 行为配置
 */
data class BehaviorConfig(
    val enableCaching: Boolean = true,
    val enableParallelProcessing: Boolean = true,
    val maxParallelThreads: Int = Runtime.getRuntime().availableProcessors(),
    val enablePerformanceMonitoring: Boolean = false
) {
    companion object {
        fun default(): BehaviorConfig = BehaviorConfig()
    }
}

/**
 * 类修饰符规则
 */
data class ClassModifierRules(
    val toKotlinClass: List<String> = emptyList(),
    val keepInterface: List<String> = emptyList(),
    val sealedInterface: List<String> = emptyList(),
    val propsToSnakeCase: List<String> = emptyList(),
    val literalUnionToTypealias: List<String> = emptyList()
) {
    companion object {
        fun default(): ClassModifierRules = ClassModifierRules()
    }
}

/**
 * 类型映射规则
 */
data class TypeMappingRules(
    val jsToKotlinTypeMap: Map<String, String> = emptyMap(),
    val propertyTypeOverrides: Map<String, String> = emptyMap()
) {
    companion object {
        fun default(): TypeMappingRules = TypeMappingRules()
    }
}

/**
 * 命名规则
 */
data class NamingRules(
    val kotlinKeywordMap: Map<String, String> = emptyMap(),
    val reservedWords: Set<String> = emptySet()
) {
    companion object {
        fun default(): NamingRules = NamingRules()
    }
}

/**
 * 修饰符规则
 */
data class ModifierRules(
    val importantInterfaces: List<String> = emptyList(),
    val skipClassPatterns: List<String> = emptyList(),
    val skipDslReceivers: List<String> = emptyList()
) {
    companion object {
        fun default(): ModifierRules = ModifierRules()
    }
}
