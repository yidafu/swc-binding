package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.JSXClosingFragment
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * JSXClosingFragment#type: String
 * extension function for create String -> String
 */
public fun JSXClosingFragment.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXClosingFragment#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun JSXClosingFragment.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
