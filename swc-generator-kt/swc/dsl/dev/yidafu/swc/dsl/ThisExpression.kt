package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.ThisExpression
import kotlin.Unit

/**
 * ThisExpression#type: String
 * extension function for create String -> String
 */
public fun ThisExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ThisExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ThisExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
