package dev.yidafu.swc.generator.util

import dev.yidafu.swc.types.*

/**
 * 扩展函数工具类
 */

/**
 * 安全获取 Identifier 的值
 */
fun Identifier?.safeValue(): String = this?.value ?: ""

/**
 * 安全获取 TsQualifiedName 的右侧标识符值
 */
fun TsQualifiedName?.safeRightValue(): String = this?.right?.value ?: ""

/**
 * 从 TsEntityName 获取类型名称
 */
fun TsEntityName?.getTypeName(): String = when (this) {
    is Identifier -> this.safeValue()
    is TsQualifiedName -> this.safeRightValue()
    else -> ""
}

/**
 * 从 Expression 获取类型名称（用于 extends/implements）
 */
fun Expression?.getExpressionTypeName(): String = when (this) {
    is Identifier -> this.safeValue()
    is TsQualifiedName -> this.safeRightValue()
    else -> ""
}

/**
 * 安全获取数组，避免 null
 */
fun <T> Array<T>?.orEmpty(): List<T> = this?.toList() ?: emptyList()

/**
 * 移除注释
 */
fun String.removeComment(): String {
    return this.replace(Regex("""/\*(.|\r\n|\n)*?\*/"""), "")
}

/**
 * 判断字符串是否为空或空白
 */
fun String?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()

/**
 * 获取字面量值的字符串表示
 */
fun TsLiteral.getValueString(): String = when (this) {
    is StringLiteral -> "\"${this.value ?: ""}\""
    is NumericLiteral -> this.value.toString()
    is BigIntLiteral -> this.value.toString()
    is BooleanLiteral -> this.value.toString()
    else -> ""
}

/**
 * 获取字面量的 Kotlin 类型
 */
fun TsLiteral.getKotlinType(): String = when (this) {
    is StringLiteral -> "String"
    is NumericLiteral -> "Int"
    is BigIntLiteral -> "Long"
    is BooleanLiteral -> "Boolean"
    else -> "Any"
}

