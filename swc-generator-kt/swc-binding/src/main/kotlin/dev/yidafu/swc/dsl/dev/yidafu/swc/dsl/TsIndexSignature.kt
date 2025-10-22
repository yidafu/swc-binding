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
import dev.yidafu.swc.types.TsIndexSignature
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import kotlin.Unit

/**
 * TsIndexSignature#type: String
 * extension function for create String -> String
 */
public fun TsIndexSignature.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsIndexSignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> BindingIdentifierImpl
 */
public fun TsIndexSignature.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsIndexSignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> ArrayPatternImpl
 */
public fun TsIndexSignature.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * TsIndexSignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> RestElementImpl
 */
public fun TsIndexSignature.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * TsIndexSignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> ObjectPatternImpl
 */
public fun TsIndexSignature.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * TsIndexSignature#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun TsIndexSignature.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

/**
 * TsIndexSignature#static: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsIndexSignature.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsIndexSignature#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsIndexSignature.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
