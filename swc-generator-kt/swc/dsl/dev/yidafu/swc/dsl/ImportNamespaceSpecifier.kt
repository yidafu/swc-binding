package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.ImportNamespaceSpecifier
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * ImportNamespaceSpecifier#type: String
 * extension function for create String -> String
 */
public fun ImportNamespaceSpecifier.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ImportNamespaceSpecifier#local: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun ImportNamespaceSpecifier.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ImportNamespaceSpecifier#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ImportNamespaceSpecifier.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
