// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.352527

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.TsExpressionWithTypeArguments
import dev.yidafu.swc.generated.TsInterfaceBody
import dev.yidafu.swc.generated.TsInterfaceDeclaration
import dev.yidafu.swc.generated.TsTypeParameterDeclaration
import kotlin.Unit

/**
 * TsInterfaceDeclaration#id: Identifier?
 * extension function for create Identifier? -> Identifier
 */
public fun TsInterfaceDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsInterfaceDeclaration#typeParams: TsTypeParameterDeclaration?
 * extension function for create TsTypeParameterDeclaration? -> TsTypeParameterDeclaration
 */
public
    fun TsInterfaceDeclaration.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclaration().apply(block)

/**
 * TsInterfaceDeclaration#extends: Array<TsExpressionWithTypeArguments>?
 * extension function for create Array<TsExpressionWithTypeArguments>? ->
 * TsExpressionWithTypeArguments
 */
public
    fun TsInterfaceDeclaration.tsExpressionWithTypeArguments(block: TsExpressionWithTypeArguments.() -> Unit):
    TsExpressionWithTypeArguments = TsExpressionWithTypeArguments().apply(block)

/**
 * TsInterfaceDeclaration#body: TsInterfaceBody?
 * extension function for create TsInterfaceBody? -> TsInterfaceBody
 */
public fun TsInterfaceDeclaration.tsInterfaceBody(block: TsInterfaceBody.() -> Unit):
    TsInterfaceBody = TsInterfaceBody().apply(block)
