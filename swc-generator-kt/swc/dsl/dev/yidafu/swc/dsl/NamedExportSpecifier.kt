package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.NamedExportSpecifier
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

/**
 * NamedExportSpecifier#type: String
 * extension function for create String -> String
 */
public fun NamedExportSpecifier.string(block: String.() -> Unit): String = String().apply(block)

/**
 * NamedExportSpecifier#exported: ModuleExportName
 * extension function for create ModuleExportName -> IdentifierImpl
 */
public fun NamedExportSpecifier.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * NamedExportSpecifier#exported: ModuleExportName
 * extension function for create ModuleExportName -> StringLiteralImpl
 */
public fun NamedExportSpecifier.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * NamedExportSpecifier#isTypeOnly: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun NamedExportSpecifier.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * NamedExportSpecifier#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun NamedExportSpecifier.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
