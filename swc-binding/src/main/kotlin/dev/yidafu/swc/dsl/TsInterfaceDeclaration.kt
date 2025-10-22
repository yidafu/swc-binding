package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsExpressionWithTypeArguments
import dev.yidafu.swc.types.TsExpressionWithTypeArgumentsImpl
import dev.yidafu.swc.types.TsInterfaceBody
import dev.yidafu.swc.types.TsInterfaceBodyImpl
import dev.yidafu.swc.types.TsInterfaceDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclarationImpl
import kotlin.Unit

/**
 * TsInterfaceDeclaration#type: String
 * extension function for create String -> String
 */
public fun TsInterfaceDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsInterfaceDeclaration#id: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun TsInterfaceDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsInterfaceDeclaration#declare: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsInterfaceDeclaration.boolean(block: Boolean.() -> Unit): Boolean =
    Boolean().apply(block)

/**
 * TsInterfaceDeclaration#typeParams: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public
    fun TsInterfaceDeclaration.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * TsInterfaceDeclaration#extends: Array<TsExpressionWithTypeArguments>
 * extension function for create Array<TsExpressionWithTypeArguments> ->
 * TsExpressionWithTypeArgumentsImpl
 */
public
    fun TsInterfaceDeclaration.tsExpressionWithTypeArguments(block: TsExpressionWithTypeArguments.() -> Unit):
    TsExpressionWithTypeArguments = TsExpressionWithTypeArgumentsImpl().apply(block)

/**
 * TsInterfaceDeclaration#body: TsInterfaceBody
 * extension function for create TsInterfaceBody -> TsInterfaceBodyImpl
 */
public fun TsInterfaceDeclaration.tsInterfaceBody(block: TsInterfaceBody.() -> Unit):
    TsInterfaceBody = TsInterfaceBodyImpl().apply(block)

/**
 * TsInterfaceDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsInterfaceDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
