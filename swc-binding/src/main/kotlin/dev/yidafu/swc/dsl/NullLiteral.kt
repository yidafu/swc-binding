package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.NullLiteral
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * NullLiteral#type: String
 * extension function for create String -> String
 */
public fun NullLiteral.string(block: String.() -> Unit): String = String().apply(block)

/**
 * NullLiteral#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun NullLiteral.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
