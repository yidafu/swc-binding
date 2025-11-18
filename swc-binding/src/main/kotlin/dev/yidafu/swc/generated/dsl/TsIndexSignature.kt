// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.145568

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ArrayPattern
import dev.yidafu.swc.generated.BindingIdentifier
import dev.yidafu.swc.generated.BindingIdentifierImpl
import dev.yidafu.swc.generated.ObjectPattern
import dev.yidafu.swc.generated.RestElement
import dev.yidafu.swc.generated.TsIndexSignature
import dev.yidafu.swc.generated.TsTypeAnnotation
import kotlin.Unit

/**
 * TsIndexSignature#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> BindingIdentifier
 */
public fun TsIndexSignature.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsIndexSignature#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> ArrayPattern
 */
public fun TsIndexSignature.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPattern().apply(block)

/**
 * TsIndexSignature#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> ObjectPattern
 */
public fun TsIndexSignature.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPattern().apply(block)

/**
 * TsIndexSignature#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> RestElement
 */
public fun TsIndexSignature.restElement(block: RestElement.() -> Unit): RestElement =
    RestElement().apply(block)

/**
 * TsIndexSignature#typeAnnotation: TsTypeAnnotation?
 * extension function for create TsTypeAnnotation? -> TsTypeAnnotation
 */
public fun TsIndexSignature.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotation().apply(block)
