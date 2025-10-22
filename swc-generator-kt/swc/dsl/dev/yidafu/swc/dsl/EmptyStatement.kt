package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.EmptyStatement
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * EmptyStatement#type: String
 * extension function for create String -> String
 */
public fun EmptyStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * EmptyStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun EmptyStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
