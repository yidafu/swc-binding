// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.264323

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ArrayPattern
import dev.yidafu.swc.generated.BindingIdentifier
import dev.yidafu.swc.generated.BindingIdentifierImpl
import dev.yidafu.swc.generated.ObjectPattern
import dev.yidafu.swc.generated.RestElement
import dev.yidafu.swc.generated.TsCallSignatureDeclaration
import dev.yidafu.swc.generated.TsTypeAnnotation
import dev.yidafu.swc.generated.TsTypeParameterDeclaration
import kotlin.Unit

/**
 * TsCallSignatureDeclaration#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> ArrayPattern
 */
public fun TsCallSignatureDeclaration.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPattern().apply(block)

/**
 * TsCallSignatureDeclaration#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> ObjectPattern
 */
public fun TsCallSignatureDeclaration.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern
    = ObjectPattern().apply(block)

/**
 * TsCallSignatureDeclaration#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> RestElement
 */
public fun TsCallSignatureDeclaration.restElement(block: RestElement.() -> Unit): RestElement =
    RestElement().apply(block)

/**
 * TsCallSignatureDeclaration#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> BindingIdentifier
 */
public fun TsCallSignatureDeclaration.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsCallSignatureDeclaration#typeAnnotation: TsTypeAnnotation?
 * extension function for create TsTypeAnnotation? -> TsTypeAnnotation
 */
public fun TsCallSignatureDeclaration.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit):
    TsTypeAnnotation = TsTypeAnnotation().apply(block)

/**
 * TsCallSignatureDeclaration#typeParams: TsTypeParameterDeclaration?
 * extension function for create TsTypeParameterDeclaration? -> TsTypeParameterDeclaration
 */
public
    fun TsCallSignatureDeclaration.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclaration().apply(block)
