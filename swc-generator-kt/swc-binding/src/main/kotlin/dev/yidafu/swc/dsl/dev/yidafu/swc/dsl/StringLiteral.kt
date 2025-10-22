package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import kotlin.Unit

/**
 * StringLiteral#raw: String
 * extension function for create String -> String
 */
public fun StringLiteral.string(block: String.() -> Unit): String = String().apply(block)

/**
 * StringLiteral#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun StringLiteral.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
