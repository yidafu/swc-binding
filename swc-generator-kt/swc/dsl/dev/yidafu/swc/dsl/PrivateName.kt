package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * PrivateName#type: String
 * extension function for create String -> String
 */
public fun PrivateName.string(block: String.() -> Unit): String = String().apply(block)

/**
 * PrivateName#id: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun PrivateName.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * PrivateName#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun PrivateName.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
