package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Import
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * Import#type: String
 * extension function for create String -> String
 */
public fun Import.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Import#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun Import.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
