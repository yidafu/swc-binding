package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.DebuggerStatement
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * DebuggerStatement#type: String
 * extension function for create String -> String
 */
public fun DebuggerStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * DebuggerStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun DebuggerStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
