package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ExpressionBase
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.Span
import kotlin.String
import kotlin.Unit

/**
 * subtype of ExpressionBase
 */
public fun ExpressionBase.identifier(block: ExpressionBase.() -> Unit): Identifier {
}

/**
 * ExpressionBase#type: String
 * extension function for create String -> String
 */
public fun ExpressionBase.string(block: ExpressionBase.() -> Unit): String {
}

/**
 * ExpressionBase#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ExpressionBase.span(block: ExpressionBase.() -> Unit): Span {
}
