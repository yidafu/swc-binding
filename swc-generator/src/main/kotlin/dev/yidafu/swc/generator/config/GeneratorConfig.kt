package dev.yidafu.swc.generator.config

import kotlinx.serialization.Serializable

/**
 * SWC Generator 配置数据类
 */
@Serializable
data class SwcGeneratorConfig(
    val typeMapping: TypeMappingConfig = TypeMappingConfig(),
    val classModifiers: ClassModifiersConfig = ClassModifiersConfig(),
    val namingRules: NamingRulesConfig = NamingRulesConfig(),
    val filterRules: FilterRulesConfig = FilterRulesConfig(),
    val paths: PathsConfig = PathsConfig()
) {
    // 便捷访问方法，保持向后兼容
    val toKotlinClass: List<String> get() = classModifiers.toKotlinClass
    val keepInterface: List<String> get() = classModifiers.keepInterface
    val sealedInterface: List<String> get() = classModifiers.sealedInterface
    val kotlinKeywordMap: Map<String, String> get() = namingRules.kotlinKeywords
    val literalNameMap: Map<String, String> get() = namingRules.literalOperators
    val propTypeRewrite: Map<String, String> get() = typeMapping.propertyTypeOverrides
    val propsToSnakeCase: List<String> get() = classModifiers.propsToSnakeCase
    
    // 不生成 Impl 类的根类型列表（保持原有逻辑）
    val noImplRootList: List<String> = listOf(
        "ParserConfig",
        "Config", 
        "JscConfig",
        "BaseModuleConfig"
    )
}

/**
 * 类型映射配置
 */
@Serializable
data class TypeMappingConfig(
    val jsToKotlin: Map<String, String> = emptyMap(),
    val propertyTypeOverrides: Map<String, String> = emptyMap()
)

/**
 * 类修饰符配置
 */
@Serializable
data class ClassModifiersConfig(
    val toKotlinClass: List<String> = emptyList(),
    val keepInterface: List<String> = emptyList(),
    val sealedInterface: List<String> = emptyList(),
    val propsToSnakeCase: List<String> = emptyList()
)

/**
 * 命名规则配置
 */
@Serializable
data class NamingRulesConfig(
    val kotlinKeywords: Map<String, String> = emptyMap(),
    val literalOperators: Map<String, String> = emptyMap()
)

/**
 * 过滤规则配置
 */
@Serializable
data class FilterRulesConfig(
    val importantInterfaces: List<String> = emptyList(),
    val skipClassPatterns: List<String> = emptyList(),
    val skipDslReceivers: List<String> = emptyList()
)

/**
 * 路径配置
 */
@Serializable
data class PathsConfig(
    val defaultInputPath: String? = null,
    val defaultOutputTypesPath: String? = null,
    val defaultOutputSerializerPath: String? = null,
    val defaultOutputDslDir: String? = null
)
