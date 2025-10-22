package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * BooleanLiteral#type: String
 * extension function for create String -> String
 */
public fun BooleanLiteral.string(block: String.() -> Unit): String = String().apply(block)

/**
 * BooleanLiteral#value: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun BooleanLiteral.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * BooleanLiteral#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun BooleanLiteral.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
