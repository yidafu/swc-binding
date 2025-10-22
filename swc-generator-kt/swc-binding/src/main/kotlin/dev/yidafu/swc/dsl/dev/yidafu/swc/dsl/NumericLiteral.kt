package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * NumericLiteral#raw: String
 * extension function for create String -> String
 */
public fun NumericLiteral.string(block: String.() -> Unit): String = String().apply(block)

/**
 * NumericLiteral#value: Int
 * extension function for create Int -> Int
 */
public fun NumericLiteral.int(block: Int.() -> Unit): Int = Int().apply(block)

/**
 * NumericLiteral#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun NumericLiteral.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
