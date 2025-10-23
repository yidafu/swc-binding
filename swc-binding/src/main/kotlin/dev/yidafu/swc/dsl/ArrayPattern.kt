package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.Span
import kotlin.String
import kotlin.Unit

/**
 * ArrayPattern#type: String
 * extension function for create String -> String
 */
public fun ArrayPattern.string(block: ArrayPattern.() -> Unit): String {
}

/**
 * ArrayPattern#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ArrayPattern.span(block: ArrayPattern.() -> Unit): Span {
}
