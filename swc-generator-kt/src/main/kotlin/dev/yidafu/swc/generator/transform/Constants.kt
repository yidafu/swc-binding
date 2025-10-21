package dev.yidafu.swc.generator.transform

/**
 * 常量定义
 */
object Constants {
    /**
     * 需要转换为 Kotlin class 的类型
     */
    val toKotlinClass = listOf("Span")
    
    /**
     * Kotlin 关键字映射
     */
    val kotlinKeywordMap = mapOf(
        "object" to "jsObject",
        "inline" to "jsInline",
        "in" to "jsIn",
        "super" to "jsSuper",
        "class" to "jsClass"
    )
    
    /**
     * 字面量操作符名称映射
     */
    val literalNameMap = mapOf(
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
    
    /**
     * 需要保留的接口（不生成 Impl 类）
     */
    val keepInterface = listOf(
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
    )
    
    /**
     * 属性类型重写
     */
    val propTypeRewrite = mapOf(
        "global_defs" to "Map<String, String>",
        "top_retain" to "BooleanableString",
        "sequences" to "Boolean",
        "pure_getters" to "BooleanableString",
        "toplevel" to "BooleanableString",
        "targets" to "Map<String, String>"
    )
    
    /**
     * 不生成 Impl 类的根类型列表
     */
    val noImplRootList = listOf(
        "ParserConfig",
        "Config",
        "JscConfig",
        "BaseModuleConfig"
    )
    
    /**
     * sealed interface 列表
     */
    val sealedInterface = listOf("Node")
    
    /**
     * 属性名转 snake_case 的类型
     */
    val propsToSnakeCase = listOf("JsFormatOptions")
}

