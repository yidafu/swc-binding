package dev.yidafu.swc.generator.adt.typescript

/**
 * 字面量值
 */
sealed class LiteralValue {
    data class StringLiteral(val value: String) : LiteralValue()
    data class NumberLiteral(val value: Double) : LiteralValue()
    data class BooleanLiteral(val value: Boolean) : LiteralValue()
    data class NullLiteral(val value: Nothing? = null) : LiteralValue()
    data class UndefinedLiteral(val value: Unit = Unit) : LiteralValue()
}
