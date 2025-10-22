package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.ImportDeclaration
import dev.yidafu.swc.types.ImportDefaultSpecifier
import dev.yidafu.swc.types.ImportDefaultSpecifierImpl
import dev.yidafu.swc.types.ImportNamespaceSpecifier
import dev.yidafu.swc.types.ImportNamespaceSpecifierImpl
import dev.yidafu.swc.types.NamedImportSpecifier
import dev.yidafu.swc.types.NamedImportSpecifierImpl
import dev.yidafu.swc.types.ObjectExpression
import dev.yidafu.swc.types.ObjectExpressionImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

/**
 * ImportDeclaration#type: String
 * extension function for create String -> String
 */
public fun ImportDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ImportDeclaration#specifiers: Array<ImportSpecifier>
 * extension function for create Array<ImportSpecifier> -> NamedImportSpecifierImpl
 */
public fun ImportDeclaration.namedImportSpecifier(block: NamedImportSpecifier.() -> Unit):
    NamedImportSpecifier = NamedImportSpecifierImpl().apply(block)

/**
 * ImportDeclaration#specifiers: Array<ImportSpecifier>
 * extension function for create Array<ImportSpecifier> -> ImportDefaultSpecifierImpl
 */
public fun ImportDeclaration.importDefaultSpecifier(block: ImportDefaultSpecifier.() -> Unit):
    ImportDefaultSpecifier = ImportDefaultSpecifierImpl().apply(block)

/**
 * ImportDeclaration#specifiers: Array<ImportSpecifier>
 * extension function for create Array<ImportSpecifier> -> ImportNamespaceSpecifierImpl
 */
public fun ImportDeclaration.importNamespaceSpecifier(block: ImportNamespaceSpecifier.() -> Unit):
    ImportNamespaceSpecifier = ImportNamespaceSpecifierImpl().apply(block)

/**
 * ImportDeclaration#source: StringLiteral
 * extension function for create StringLiteral -> StringLiteralImpl
 */
public fun ImportDeclaration.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ImportDeclaration#typeOnly: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ImportDeclaration.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * ImportDeclaration#asserts: ObjectExpression
 * extension function for create ObjectExpression -> ObjectExpressionImpl
 */
public fun ImportDeclaration.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression
    = ObjectExpressionImpl().apply(block)

/**
 * ImportDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ImportDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
