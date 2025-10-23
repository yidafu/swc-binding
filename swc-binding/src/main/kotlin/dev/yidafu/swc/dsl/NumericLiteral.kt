package dev.yidafu.swc.dsl

import Int
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.Span
import kotlin.String
import kotlin.Unit

/**
 * NumericLiteral#raw: String
 * extension function for create String -> String
 */
public fun NumericLiteral.string(block: NumericLiteral.() -> Unit): String {
}

/**
 * NumericLiteral#value: Int
 * extension function for create Int -> Int
 */
public fun NumericLiteral.int(block: NumericLiteral.() -> Unit): Int {
}

/**
 * NumericLiteral#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun NumericLiteral.span(block: NumericLiteral.() -> Unit): Span {
}
