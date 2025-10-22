package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * RegExpLiteral#flags: String
 * extension function for create String -> String
 */
public fun RegExpLiteral.string(block: String.() -> Unit): String = String().apply(block)

/**
 * RegExpLiteral#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun RegExpLiteral.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
