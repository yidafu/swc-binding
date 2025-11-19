// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.322519

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.TsModuleBlock
import dev.yidafu.swc.generated.TsNamespaceDeclaration
import kotlin.Unit

/**
 * TsNamespaceDeclaration#id: Identifier?
 * extension function for create Identifier? -> Identifier
 */
public fun TsNamespaceDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsNamespaceDeclaration#body: TsNamespaceBody?
 * extension function for create TsNamespaceBody? -> TsModuleBlock
 */
public fun TsNamespaceDeclaration.tsModuleBlock(block: TsModuleBlock.() -> Unit): TsModuleBlock =
    TsModuleBlock().apply(block)
