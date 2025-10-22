package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Invalid
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * Invalid#type: String
 * extension function for create String -> String
 */
public fun Invalid.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Invalid#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun Invalid.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
