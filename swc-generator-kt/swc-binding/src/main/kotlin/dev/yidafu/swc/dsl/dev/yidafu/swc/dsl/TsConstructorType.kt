package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.BindingIdentifierImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.ObjectPattern
import dev.yidafu.swc.types.ObjectPatternImpl
import dev.yidafu.swc.types.RestElement
import dev.yidafu.swc.types.RestElementImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsConstructorType
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclarationImpl
import kotlin.Unit

/**
 * TsConstructorType#type: String
 * extension function for create String -> String
 */
public fun TsConstructorType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsConstructorType#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> BindingIdentifierImpl
 */
public fun TsConstructorType.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsConstructorType#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> ArrayPatternImpl
 */
public fun TsConstructorType.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * TsConstructorType#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> RestElementImpl
 */
public fun TsConstructorType.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * TsConstructorType#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> ObjectPatternImpl
 */
public fun TsConstructorType.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * TsConstructorType#typeParams: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public
    fun TsConstructorType.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * TsConstructorType#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun TsConstructorType.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation
    = TsTypeAnnotationImpl().apply(block)

/**
 * TsConstructorType#isAbstract: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsConstructorType.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsConstructorType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsConstructorType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
