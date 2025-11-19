// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.357574

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.StringLiteral
import dev.yidafu.swc.generated.TsModuleBlock
import dev.yidafu.swc.generated.TsModuleDeclaration
import dev.yidafu.swc.generated.TsNamespaceDeclaration
import kotlin.Unit

/**
 * TsModuleDeclaration#id: TsModuleName?
 * extension function for create TsModuleName? -> StringLiteral
 */
public fun TsModuleDeclaration.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * TsModuleDeclaration#id: TsModuleName?
 * extension function for create TsModuleName? -> Identifier
 */
public fun TsModuleDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsModuleDeclaration#body: TsNamespaceBody?
 * extension function for create TsNamespaceBody? -> TsModuleBlock
 */
public fun TsModuleDeclaration.tsModuleBlock(block: TsModuleBlock.() -> Unit): TsModuleBlock =
    TsModuleBlock().apply(block)

/**
 * TsModuleDeclaration#body: TsNamespaceBody?
 * extension function for create TsNamespaceBody? -> TsNamespaceDeclaration
 */
public fun TsModuleDeclaration.tsNamespaceDeclaration(block: TsNamespaceDeclaration.() -> Unit):
    TsNamespaceDeclaration = TsNamespaceDeclaration().apply(block)
