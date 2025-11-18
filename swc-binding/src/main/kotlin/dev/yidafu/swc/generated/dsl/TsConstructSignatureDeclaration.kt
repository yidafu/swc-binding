// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.139944

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ArrayPattern
import dev.yidafu.swc.generated.BindingIdentifier
import dev.yidafu.swc.generated.BindingIdentifierImpl
import dev.yidafu.swc.generated.ObjectPattern
import dev.yidafu.swc.generated.RestElement
import dev.yidafu.swc.generated.TsConstructSignatureDeclaration
import dev.yidafu.swc.generated.TsTypeAnnotation
import dev.yidafu.swc.generated.TsTypeParameterDeclaration
import kotlin.Unit

/**
 * TsConstructSignatureDeclaration#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> BindingIdentifier
 */
public fun TsConstructSignatureDeclaration.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsConstructSignatureDeclaration#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> ArrayPattern
 */
public fun TsConstructSignatureDeclaration.arrayPattern(block: ArrayPattern.() -> Unit):
    ArrayPattern = ArrayPattern().apply(block)

/**
 * TsConstructSignatureDeclaration#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> ObjectPattern
 */
public fun TsConstructSignatureDeclaration.objectPattern(block: ObjectPattern.() -> Unit):
    ObjectPattern = ObjectPattern().apply(block)

/**
 * TsConstructSignatureDeclaration#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> RestElement
 */
public fun TsConstructSignatureDeclaration.restElement(block: RestElement.() -> Unit): RestElement =
    RestElement().apply(block)

/**
 * TsConstructSignatureDeclaration#typeAnnotation: TsTypeAnnotation?
 * extension function for create TsTypeAnnotation? -> TsTypeAnnotation
 */
public fun TsConstructSignatureDeclaration.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit):
    TsTypeAnnotation = TsTypeAnnotation().apply(block)

/**
 * TsConstructSignatureDeclaration#typeParams: TsTypeParameterDeclaration?
 * extension function for create TsTypeParameterDeclaration? -> TsTypeParameterDeclaration
 */
public
    fun TsConstructSignatureDeclaration.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclaration().apply(block)
