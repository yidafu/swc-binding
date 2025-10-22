package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.Long
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * BigIntLiteral#raw: String
 * extension function for create String -> String
 */
public fun BigIntLiteral.string(block: String.() -> Unit): String = String().apply(block)

/**
 * BigIntLiteral#value: Long
 * extension function for create Long -> Long
 */
public fun BigIntLiteral.long(block: Long.() -> Unit): Long = Long().apply(block)

/**
 * BigIntLiteral#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun BigIntLiteral.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
