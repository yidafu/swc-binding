package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ExportDefaultSpecifier
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * ExportDefaultSpecifier#type: String
 * extension function for create String -> String
 */
public fun ExportDefaultSpecifier.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ExportDefaultSpecifier#exported: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun ExportDefaultSpecifier.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ExportDefaultSpecifier#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ExportDefaultSpecifier.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
