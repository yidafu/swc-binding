// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.226214

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.NamedImportSpecifier
import dev.yidafu.swc.generated.StringLiteral
import kotlin.Unit

/**
 * NamedImportSpecifier#imported: ModuleExportName?
 * extension function for create ModuleExportName? -> Identifier
 */
public fun NamedImportSpecifier.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * NamedImportSpecifier#imported: ModuleExportName?
 * extension function for create ModuleExportName? -> StringLiteral
 */
public fun NamedImportSpecifier.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)
