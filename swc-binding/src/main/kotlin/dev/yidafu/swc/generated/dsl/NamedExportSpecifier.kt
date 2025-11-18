// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.031325

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.NamedExportSpecifier
import dev.yidafu.swc.generated.StringLiteral
import kotlin.Unit

/**
 * NamedExportSpecifier#exported: ModuleExportName?
 * extension function for create ModuleExportName? -> Identifier
 */
public fun NamedExportSpecifier.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * NamedExportSpecifier#exported: ModuleExportName?
 * extension function for create ModuleExportName? -> StringLiteral
 */
public fun NamedExportSpecifier.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)
