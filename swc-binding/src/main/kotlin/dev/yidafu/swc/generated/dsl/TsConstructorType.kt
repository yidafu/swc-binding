// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.146941

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ArrayPattern
import dev.yidafu.swc.generated.BindingIdentifier
import dev.yidafu.swc.generated.BindingIdentifierImpl
import dev.yidafu.swc.generated.ObjectPattern
import dev.yidafu.swc.generated.RestElement
import dev.yidafu.swc.generated.TsConstructorType
import dev.yidafu.swc.generated.TsTypeAnnotation
import dev.yidafu.swc.generated.TsTypeParameterDeclaration
import kotlin.Unit

/**
 * TsConstructorType#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> BindingIdentifier
 */
public fun TsConstructorType.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsConstructorType#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> ArrayPattern
 */
public fun TsConstructorType.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPattern().apply(block)

/**
 * TsConstructorType#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> ObjectPattern
 */
public fun TsConstructorType.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPattern().apply(block)

/**
 * TsConstructorType#params: Array<TsFnParameter>?
 * extension function for create Array<TsFnParameter>? -> RestElement
 */
public fun TsConstructorType.restElement(block: RestElement.() -> Unit): RestElement =
    RestElement().apply(block)

/**
 * TsConstructorType#typeParams: TsTypeParameterDeclaration?
 * extension function for create TsTypeParameterDeclaration? -> TsTypeParameterDeclaration
 */
public
    fun TsConstructorType.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclaration().apply(block)

/**
 * TsConstructorType#typeAnnotation: TsTypeAnnotation?
 * extension function for create TsTypeAnnotation? -> TsTypeAnnotation
 */
public fun TsConstructorType.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation
    = TsTypeAnnotation().apply(block)
