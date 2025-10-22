package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.JSXEmptyExpression
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * JSXEmptyExpression#type: String
 * extension function for create String -> String
 */
public fun JSXEmptyExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXEmptyExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun JSXEmptyExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
