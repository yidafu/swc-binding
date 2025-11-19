// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.160347

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ExportNamespaceSpecifier
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.StringLiteral
import kotlin.Unit

/**
 * ExportNamespaceSpecifier#name: ModuleExportName?
 * extension function for create ModuleExportName? -> StringLiteral
 */
public fun ExportNamespaceSpecifier.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * ExportNamespaceSpecifier#name: ModuleExportName?
 * extension function for create ModuleExportName? -> Identifier
 */
public fun ExportNamespaceSpecifier.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
