package dev.yidafu.swc.generator.config

import kotlinx.serialization.Serializable

/**
 * SWC Generator 配置数据类
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

    // noImplRootList 已废弃：新的生成逻辑不再生成任何 *Impl 类
}

/**
 * 类修饰符配置
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
 */
@Serializable
data class PathsConfig(
    val defaultInputPath: String? = null,
    val defaultOutputTypesPath: String? = null,
    val defaultOutputSerializerPath: String? = null,
    val defaultOutputDslDir: String? = null
)
