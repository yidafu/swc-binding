package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BreakStatement
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * BreakStatement#type: String
 * extension function for create String -> String
 */
public fun BreakStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * BreakStatement#label: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun BreakStatement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * BreakStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun BreakStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
