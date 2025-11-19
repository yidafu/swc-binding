package dev.yidafu.swc.generator.config

import kotlinx.serialization.Serializable

/**
 * SWC Generator 配置数据类
 *
 * 包含代码生成器的所有配置选项，包括：
 * - 类修饰符配置（哪些类型转换为类、密封接口等）
 * - 命名规则配置（字面量操作符映射等）
 * - 路径配置（默认输入输出路径）
 *
 * 配置可以通过 YAML 文件加载，也可以通过代码直接创建。
 *
 * @param classModifiers 类修饰符配置
 * @param namingRules 命名规则配置
 * @param paths 路径配置
 */
@Serializable
data class SwcGeneratorConfig(
    val classModifiers: ClassModifiersConfig = ClassModifiersConfig(),
    val namingRules: NamingRulesConfig = NamingRulesConfig(),
    val paths: PathsConfig = PathsConfig()
) {
    // 便捷访问方法，保持向后兼容
    val toKotlinClass: List<String> get() = classModifiers.toKotlinClass
    val sealedInterface: List<String> get() = classModifiers.sealedInterface

    // kotlinKeywords 已移至 CodeGenerationRules.kt
    val literalNameMap: Map<String, String> get() = namingRules.literalOperators
    val propsToSnakeCase: List<String> get() = classModifiers.propsToSnakeCase
}

/**
 * 类修饰符配置
 *
 * 控制哪些 TypeScript 接口应该转换为哪种 Kotlin 类修饰符。
 *
 * @param toKotlinClass 应转换为普通 Kotlin 类的接口名称列表
 * @param sealedInterface 应转换为密封接口的接口名称列表
 * @param propsToSnakeCase 属性名应转换为 snake_case 的类名称列表
 * @param literalUnionToTypealias 应转换为类型别名的字面量联合类型列表
 */
@Serializable
data class ClassModifiersConfig(
    val toKotlinClass: List<String> = emptyList(),
    val sealedInterface: List<String> = emptyList(),
    val propsToSnakeCase: List<String> = emptyList(),
    val literalUnionToTypealias: List<String> = emptyList()
)

/**
 * 命名规则配置
 *
 * 包含各种命名转换规则，主要用于将 TypeScript 字面量操作符转换为 Kotlin 友好的名称。
 *
 * @param literalOperators 字面量操作符到名称的映射表
 */
@Serializable
data class NamingRulesConfig(
    // kotlinKeywords 已移至 CodeGenerationRules.kt
    val literalOperators: Map<String, String> = mapOf(
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
)

/**
 * 路径配置
 *
 * 包含默认的输入输出路径配置。
 *
 * @param defaultInputPath 默认输入文件路径
 * @param defaultOutputTypesPath 默认输出 types.kt 文件路径
 * @param defaultOutputSerializerPath 默认输出 serializer.kt 文件路径
 * @param defaultOutputDslDir 默认输出 DSL 文件目录
 */
@Serializable
data class PathsConfig(
    val defaultInputPath: String? = null,
    val defaultOutputTypesPath: String? = null,
    val defaultOutputSerializerPath: String? = null,
    val defaultOutputDslDir: String? = null
)
