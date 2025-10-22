package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.Super
import kotlin.Unit

/**
 * Super#type: String
 * extension function for create String -> String
 */
public fun Super.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Super#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun Super.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
