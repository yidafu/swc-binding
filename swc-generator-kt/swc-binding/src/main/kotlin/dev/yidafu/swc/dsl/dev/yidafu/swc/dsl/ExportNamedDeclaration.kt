package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.ExportDefaultSpecifier
import dev.yidafu.swc.types.ExportDefaultSpecifierImpl
import dev.yidafu.swc.types.ExportNamedDeclaration
import dev.yidafu.swc.types.ExportNamespaceSpecifier
import dev.yidafu.swc.types.ExportNamespaceSpecifierImpl
import dev.yidafu.swc.types.NamedExportSpecifier
import dev.yidafu.swc.types.NamedExportSpecifierImpl
import dev.yidafu.swc.types.ObjectExpression
import dev.yidafu.swc.types.ObjectExpressionImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

/**
 * ExportNamedDeclaration#type: String
 * extension function for create String -> String
 */
public fun ExportNamedDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ExportNamedDeclaration#specifiers: Array<ExportSpecifier>
 * extension function for create Array<ExportSpecifier> -> ExportNamespaceSpecifierImpl
 */
public
    fun ExportNamedDeclaration.exportNamespaceSpecifier(block: ExportNamespaceSpecifier.() -> Unit):
    ExportNamespaceSpecifier = ExportNamespaceSpecifierImpl().apply(block)

/**
 * ExportNamedDeclaration#specifiers: Array<ExportSpecifier>
 * extension function for create Array<ExportSpecifier> -> ExportDefaultSpecifierImpl
 */
public fun ExportNamedDeclaration.exportDefaultSpecifier(block: ExportDefaultSpecifier.() -> Unit):
    ExportDefaultSpecifier = ExportDefaultSpecifierImpl().apply(block)

/**
 * ExportNamedDeclaration#specifiers: Array<ExportSpecifier>
 * extension function for create Array<ExportSpecifier> -> NamedExportSpecifierImpl
 */
public fun ExportNamedDeclaration.namedExportSpecifier(block: NamedExportSpecifier.() -> Unit):
    NamedExportSpecifier = NamedExportSpecifierImpl().apply(block)

/**
 * ExportNamedDeclaration#source: StringLiteral
 * extension function for create StringLiteral -> StringLiteralImpl
 */
public fun ExportNamedDeclaration.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ExportNamedDeclaration#typeOnly: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ExportNamedDeclaration.boolean(block: Boolean.() -> Unit): Boolean =
    Boolean().apply(block)

/**
 * ExportNamedDeclaration#asserts: ObjectExpression
 * extension function for create ObjectExpression -> ObjectExpressionImpl
 */
public fun ExportNamedDeclaration.objectExpression(block: ObjectExpression.() -> Unit):
    ObjectExpression = ObjectExpressionImpl().apply(block)

/**
 * ExportNamedDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ExportNamedDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
