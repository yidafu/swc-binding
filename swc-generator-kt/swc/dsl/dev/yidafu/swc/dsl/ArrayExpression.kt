package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ExprOrSpread
import dev.yidafu.swc.types.ExprOrSpreadImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * ArrayExpression#type: String
 * extension function for create String -> String
 */
public fun ArrayExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ArrayExpression#elements: Array<ExprOrSpread>
 * extension function for create Array<ExprOrSpread> -> ExprOrSpreadImpl
 */
public fun ArrayExpression.exprOrSpread(block: ExprOrSpread.() -> Unit): ExprOrSpread =
    ExprOrSpreadImpl().apply(block)

/**
 * ArrayExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ArrayExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
