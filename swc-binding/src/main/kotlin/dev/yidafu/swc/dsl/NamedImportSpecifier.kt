package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.NamedImportSpecifier
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

/**
 * NamedImportSpecifier#type: String
 * extension function for create String -> String
 */
public fun NamedImportSpecifier.string(block: String.() -> Unit): String = String().apply(block)

/**
 * NamedImportSpecifier#imported: ModuleExportName
 * extension function for create ModuleExportName -> IdentifierImpl
 */
public fun NamedImportSpecifier.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * NamedImportSpecifier#imported: ModuleExportName
 * extension function for create ModuleExportName -> StringLiteralImpl
 */
public fun NamedImportSpecifier.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * NamedImportSpecifier#isTypeOnly: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun NamedImportSpecifier.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * NamedImportSpecifier#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun NamedImportSpecifier.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
