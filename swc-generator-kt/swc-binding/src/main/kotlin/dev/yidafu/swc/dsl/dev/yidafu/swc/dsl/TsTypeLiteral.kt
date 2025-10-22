package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsCallSignatureDeclaration
import dev.yidafu.swc.types.TsCallSignatureDeclarationImpl
import dev.yidafu.swc.types.TsConstructSignatureDeclaration
import dev.yidafu.swc.types.TsConstructSignatureDeclarationImpl
import dev.yidafu.swc.types.TsGetterSignature
import dev.yidafu.swc.types.TsGetterSignatureImpl
import dev.yidafu.swc.types.TsIndexSignature
import dev.yidafu.swc.types.TsIndexSignatureImpl
import dev.yidafu.swc.types.TsMethodSignature
import dev.yidafu.swc.types.TsMethodSignatureImpl
import dev.yidafu.swc.types.TsPropertySignature
import dev.yidafu.swc.types.TsPropertySignatureImpl
import dev.yidafu.swc.types.TsSetterSignature
import dev.yidafu.swc.types.TsSetterSignatureImpl
import dev.yidafu.swc.types.TsTypeLiteral
import kotlin.Unit

/**
 * TsTypeLiteral#type: String
 * extension function for create String -> String
 */
public fun TsTypeLiteral.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsTypeLiteral#members: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsCallSignatureDeclarationImpl
 */
public fun TsTypeLiteral.tsCallSignatureDeclaration(block: TsCallSignatureDeclaration.() -> Unit):
    TsCallSignatureDeclaration = TsCallSignatureDeclarationImpl().apply(block)

/**
 * TsTypeLiteral#members: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsConstructSignatureDeclarationImpl
 */
public
    fun TsTypeLiteral.tsConstructSignatureDeclaration(block: TsConstructSignatureDeclaration.() -> Unit):
    TsConstructSignatureDeclaration = TsConstructSignatureDeclarationImpl().apply(block)

/**
 * TsTypeLiteral#members: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsPropertySignatureImpl
 */
public fun TsTypeLiteral.tsPropertySignature(block: TsPropertySignature.() -> Unit):
    TsPropertySignature = TsPropertySignatureImpl().apply(block)

/**
 * TsTypeLiteral#members: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsGetterSignatureImpl
 */
public fun TsTypeLiteral.tsGetterSignature(block: TsGetterSignature.() -> Unit): TsGetterSignature =
    TsGetterSignatureImpl().apply(block)

/**
 * TsTypeLiteral#members: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsSetterSignatureImpl
 */
public fun TsTypeLiteral.tsSetterSignature(block: TsSetterSignature.() -> Unit): TsSetterSignature =
    TsSetterSignatureImpl().apply(block)

/**
 * TsTypeLiteral#members: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsMethodSignatureImpl
 */
public fun TsTypeLiteral.tsMethodSignature(block: TsMethodSignature.() -> Unit): TsMethodSignature =
    TsMethodSignatureImpl().apply(block)

/**
 * TsTypeLiteral#members: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsIndexSignatureImpl
 */
public fun TsTypeLiteral.tsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature =
    TsIndexSignatureImpl().apply(block)

/**
 * TsTypeLiteral#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsTypeLiteral.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
