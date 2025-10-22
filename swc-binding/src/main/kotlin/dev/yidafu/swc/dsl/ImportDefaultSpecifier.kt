package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.ImportDefaultSpecifier
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * ImportDefaultSpecifier#type: String
 * extension function for create String -> String
 */
public fun ImportDefaultSpecifier.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ImportDefaultSpecifier#local: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun ImportDefaultSpecifier.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ImportDefaultSpecifier#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ImportDefaultSpecifier.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
