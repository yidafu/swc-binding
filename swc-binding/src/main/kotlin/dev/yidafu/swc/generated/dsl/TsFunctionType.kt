// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.30785

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ArrayPattern
import dev.yidafu.swc.generated.BindingIdentifier
import dev.yidafu.swc.generated.BindingIdentifierImpl
import dev.yidafu.swc.generated.ObjectPattern
import dev.yidafu.swc.generated.RestElement
import dev.yidafu.swc.generated.TsFunctionType
import dev.yidafu.swc.generated.TsTypeAnnotation
import dev.yidafu.swc.generated.TsTypeParameterDeclaration
import kotlin.Unit

/**
 * TsFunctionType#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> ArrayPattern
 */
public fun TsFunctionType.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPattern().apply(block)

/**
 * TsFunctionType#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> ObjectPattern
 */
public fun TsFunctionType.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPattern().apply(block)

/**
 * TsFunctionType#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> RestElement
 */
public fun TsFunctionType.restElement(block: RestElement.() -> Unit): RestElement =
    RestElement().apply(block)

/**
 * TsFunctionType#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> BindingIdentifier
 */
public fun TsFunctionType.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier
    = BindingIdentifierImpl().apply(block)

/**
 * TsFunctionType#typeParams: TsTypeParameterDeclaration?
 * extension function for create TsTypeParameterDeclaration? -> TsTypeParameterDeclaration
 */
public fun TsFunctionType.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclaration().apply(block)

/**
 * TsFunctionType#typeAnnotation: TsTypeAnnotation?
 * extension function for create TsTypeAnnotation? -> TsTypeAnnotation
 */
public fun TsFunctionType.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotation().apply(block)
