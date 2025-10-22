package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ContinueStatement
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * ContinueStatement#type: String
 * extension function for create String -> String
 */
public fun ContinueStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ContinueStatement#label: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun ContinueStatement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ContinueStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ContinueStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
