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
import dev.yidafu.swc.types.TsInterfaceBody
import dev.yidafu.swc.types.TsMethodSignature
import dev.yidafu.swc.types.TsMethodSignatureImpl
import dev.yidafu.swc.types.TsPropertySignature
import dev.yidafu.swc.types.TsPropertySignatureImpl
import dev.yidafu.swc.types.TsSetterSignature
import dev.yidafu.swc.types.TsSetterSignatureImpl
import kotlin.Unit

/**
 * TsInterfaceBody#type: String
 * extension function for create String -> String
 */
public fun TsInterfaceBody.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsCallSignatureDeclarationImpl
 */
public fun TsInterfaceBody.tsCallSignatureDeclaration(block: TsCallSignatureDeclaration.() -> Unit):
    TsCallSignatureDeclaration = TsCallSignatureDeclarationImpl().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsConstructSignatureDeclarationImpl
 */
public
    fun TsInterfaceBody.tsConstructSignatureDeclaration(block: TsConstructSignatureDeclaration.() -> Unit):
    TsConstructSignatureDeclaration = TsConstructSignatureDeclarationImpl().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsPropertySignatureImpl
 */
public fun TsInterfaceBody.tsPropertySignature(block: TsPropertySignature.() -> Unit):
    TsPropertySignature = TsPropertySignatureImpl().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsGetterSignatureImpl
 */
public fun TsInterfaceBody.tsGetterSignature(block: TsGetterSignature.() -> Unit): TsGetterSignature
    = TsGetterSignatureImpl().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsSetterSignatureImpl
 */
public fun TsInterfaceBody.tsSetterSignature(block: TsSetterSignature.() -> Unit): TsSetterSignature
    = TsSetterSignatureImpl().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsMethodSignatureImpl
 */
public fun TsInterfaceBody.tsMethodSignature(block: TsMethodSignature.() -> Unit): TsMethodSignature
    = TsMethodSignatureImpl().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>
 * extension function for create Array<TsTypeElement> -> TsIndexSignatureImpl
 */
public fun TsInterfaceBody.tsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature =
    TsIndexSignatureImpl().apply(block)

/**
 * TsInterfaceBody#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsInterfaceBody.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
