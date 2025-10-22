package dev.yidafu.swc.dsl

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
import dev.yidafu.swc.types.TsTypeElement
import kotlin.Unit

/**
 * subtype of TsTypeElement
 */
public fun TsTypeElement.tsCallSignatureDeclaration(block: TsCallSignatureDeclaration.() -> Unit):
    TsCallSignatureDeclaration = TsCallSignatureDeclarationImpl().apply(block)

/**
 * subtype of TsTypeElement
 */
public
    fun TsTypeElement.tsConstructSignatureDeclaration(block: TsConstructSignatureDeclaration.() -> Unit):
    TsConstructSignatureDeclaration = TsConstructSignatureDeclarationImpl().apply(block)

/**
 * subtype of TsTypeElement
 */
public fun TsTypeElement.tsPropertySignature(block: TsPropertySignature.() -> Unit):
    TsPropertySignature = TsPropertySignatureImpl().apply(block)

/**
 * subtype of TsTypeElement
 */
public fun TsTypeElement.tsGetterSignature(block: TsGetterSignature.() -> Unit): TsGetterSignature =
    TsGetterSignatureImpl().apply(block)

/**
 * subtype of TsTypeElement
 */
public fun TsTypeElement.tsSetterSignature(block: TsSetterSignature.() -> Unit): TsSetterSignature =
    TsSetterSignatureImpl().apply(block)

/**
 * subtype of TsTypeElement
 */
public fun TsTypeElement.tsMethodSignature(block: TsMethodSignature.() -> Unit): TsMethodSignature =
    TsMethodSignatureImpl().apply(block)

/**
 * subtype of TsTypeElement
 */
public fun TsTypeElement.tsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature =
    TsIndexSignatureImpl().apply(block)
