// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.313539

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.TsCallSignatureDeclaration
import dev.yidafu.swc.generated.TsConstructSignatureDeclaration
import dev.yidafu.swc.generated.TsGetterSignature
import dev.yidafu.swc.generated.TsIndexSignature
import dev.yidafu.swc.generated.TsInterfaceBody
import dev.yidafu.swc.generated.TsMethodSignature
import dev.yidafu.swc.generated.TsPropertySignature
import dev.yidafu.swc.generated.TsSetterSignature
import kotlin.Unit

/**
 * TsInterfaceBody#body: Array<TsTypeElement>?
 * extension function for create Array<TsTypeElement>? -> TsCallSignatureDeclaration
 */
public fun TsInterfaceBody.tsCallSignatureDeclaration(block: TsCallSignatureDeclaration.() -> Unit):
    TsCallSignatureDeclaration = TsCallSignatureDeclaration().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>?
 * extension function for create Array<TsTypeElement>? -> TsConstructSignatureDeclaration
 */
public
    fun TsInterfaceBody.tsConstructSignatureDeclaration(block: TsConstructSignatureDeclaration.() -> Unit):
    TsConstructSignatureDeclaration = TsConstructSignatureDeclaration().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>?
 * extension function for create Array<TsTypeElement>? -> TsPropertySignature
 */
public fun TsInterfaceBody.tsPropertySignature(block: TsPropertySignature.() -> Unit):
    TsPropertySignature = TsPropertySignature().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>?
 * extension function for create Array<TsTypeElement>? -> TsGetterSignature
 */
public fun TsInterfaceBody.tsGetterSignature(block: TsGetterSignature.() -> Unit): TsGetterSignature
    = TsGetterSignature().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>?
 * extension function for create Array<TsTypeElement>? -> TsSetterSignature
 */
public fun TsInterfaceBody.tsSetterSignature(block: TsSetterSignature.() -> Unit): TsSetterSignature
    = TsSetterSignature().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>?
 * extension function for create Array<TsTypeElement>? -> TsMethodSignature
 */
public fun TsInterfaceBody.tsMethodSignature(block: TsMethodSignature.() -> Unit): TsMethodSignature
    = TsMethodSignature().apply(block)

/**
 * TsInterfaceBody#body: Array<TsTypeElement>?
 * extension function for create Array<TsTypeElement>? -> TsIndexSignature
 */
public fun TsInterfaceBody.tsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature =
    TsIndexSignature().apply(block)
