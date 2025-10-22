package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.JSXText
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * JSXText#raw: String
 * extension function for create String -> String
 */
public fun JSXText.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXText#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun JSXText.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
