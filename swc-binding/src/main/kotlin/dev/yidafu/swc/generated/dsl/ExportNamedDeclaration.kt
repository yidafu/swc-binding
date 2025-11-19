// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.156928

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ExportDefaultSpecifier
import dev.yidafu.swc.generated.ExportNamedDeclaration
import dev.yidafu.swc.generated.ExportNamespaceSpecifier
import dev.yidafu.swc.generated.NamedExportSpecifier
import dev.yidafu.swc.generated.ObjectExpression
import dev.yidafu.swc.generated.StringLiteral
import kotlin.Unit

/**
 * ExportNamedDeclaration#specifiers: Array<ExportSpecifier>?
 * extension function for create Array<ExportSpecifier>? -> ExportNamespaceSpecifier
 */
public
    fun ExportNamedDeclaration.exportNamespaceSpecifier(block: ExportNamespaceSpecifier.() -> Unit):
    ExportNamespaceSpecifier = ExportNamespaceSpecifier().apply(block)

/**
 * ExportNamedDeclaration#specifiers: Array<ExportSpecifier>?
 * extension function for create Array<ExportSpecifier>? -> ExportDefaultSpecifier
 */
public fun ExportNamedDeclaration.exportDefaultSpecifier(block: ExportDefaultSpecifier.() -> Unit):
    ExportDefaultSpecifier = ExportDefaultSpecifier().apply(block)

/**
 * ExportNamedDeclaration#specifiers: Array<ExportSpecifier>?
 * extension function for create Array<ExportSpecifier>? -> NamedExportSpecifier
 */
public fun ExportNamedDeclaration.namedExportSpecifier(block: NamedExportSpecifier.() -> Unit):
    NamedExportSpecifier = NamedExportSpecifier().apply(block)

/**
 * ExportNamedDeclaration#source: StringLiteral?
 * extension function for create StringLiteral? -> StringLiteral
 */
public fun ExportNamedDeclaration.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * ExportNamedDeclaration#asserts: ObjectExpression?
 * extension function for create ObjectExpression? -> ObjectExpression
 */
public fun ExportNamedDeclaration.objectExpression(block: ObjectExpression.() -> Unit):
    ObjectExpression = ObjectExpression().apply(block)
