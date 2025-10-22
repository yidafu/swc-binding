package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ExportNamespaceSpecifier
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

/**
 * ExportNamespaceSpecifier#type: String
 * extension function for create String -> String
 */
public fun ExportNamespaceSpecifier.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ExportNamespaceSpecifier#name: ModuleExportName
 * extension function for create ModuleExportName -> IdentifierImpl
 */
public fun ExportNamespaceSpecifier.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ExportNamespaceSpecifier#name: ModuleExportName
 * extension function for create ModuleExportName -> StringLiteralImpl
 */
public fun ExportNamespaceSpecifier.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ExportNamespaceSpecifier#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ExportNamespaceSpecifier.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
