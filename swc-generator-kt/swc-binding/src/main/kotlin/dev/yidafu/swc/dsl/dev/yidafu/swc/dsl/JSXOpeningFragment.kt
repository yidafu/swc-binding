package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.JSXOpeningFragment
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * JSXOpeningFragment#type: String
 * extension function for create String -> String
 */
public fun JSXOpeningFragment.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXOpeningFragment#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun JSXOpeningFragment.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
