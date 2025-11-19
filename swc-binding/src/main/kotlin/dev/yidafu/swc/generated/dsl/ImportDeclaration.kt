// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.224896

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ImportDeclaration
import dev.yidafu.swc.generated.ImportDefaultSpecifier
import dev.yidafu.swc.generated.ImportNamespaceSpecifier
import dev.yidafu.swc.generated.NamedImportSpecifier
import dev.yidafu.swc.generated.ObjectExpression
import dev.yidafu.swc.generated.StringLiteral
import kotlin.Unit

/**
 * ImportDeclaration#specifiers: Array<ImportSpecifier>?
 * extension function for create Array<ImportSpecifier>? -> ImportDefaultSpecifier
 */
public fun ImportDeclaration.importDefaultSpecifier(block: ImportDefaultSpecifier.() -> Unit):
    ImportDefaultSpecifier = ImportDefaultSpecifier().apply(block)

/**
 * ImportDeclaration#specifiers: Array<ImportSpecifier>?
 * extension function for create Array<ImportSpecifier>? -> ImportNamespaceSpecifier
 */
public fun ImportDeclaration.importNamespaceSpecifier(block: ImportNamespaceSpecifier.() -> Unit):
    ImportNamespaceSpecifier = ImportNamespaceSpecifier().apply(block)

/**
 * ImportDeclaration#specifiers: Array<ImportSpecifier>?
 * extension function for create Array<ImportSpecifier>? -> NamedImportSpecifier
 */
public fun ImportDeclaration.namedImportSpecifier(block: NamedImportSpecifier.() -> Unit):
    NamedImportSpecifier = NamedImportSpecifier().apply(block)

/**
 * ImportDeclaration#source: StringLiteral?
 * extension function for create StringLiteral? -> StringLiteral
 */
public fun ImportDeclaration.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * ImportDeclaration#asserts: ObjectExpression?
 * extension function for create ObjectExpression? -> ObjectExpression
 */
public fun ImportDeclaration.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression
    = ObjectExpression().apply(block)
