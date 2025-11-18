// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.193022

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
 * extension function for create TsModuleName? -> Identifier
 */
public fun TsModuleDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsModuleDeclaration#id: TsModuleName?
 * extension function for create TsModuleName? -> StringLiteral
 */
public fun TsModuleDeclaration.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

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
