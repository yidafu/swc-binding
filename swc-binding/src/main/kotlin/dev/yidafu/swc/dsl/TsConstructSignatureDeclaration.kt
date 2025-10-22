package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.BindingIdentifierImpl
import dev.yidafu.swc.types.ObjectPattern
import dev.yidafu.swc.types.ObjectPatternImpl
import dev.yidafu.swc.types.RestElement
import dev.yidafu.swc.types.RestElementImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsConstructSignatureDeclaration
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclarationImpl
import kotlin.Unit

/**
 * TsConstructSignatureDeclaration#type: String
 * extension function for create String -> String
 */
public fun TsConstructSignatureDeclaration.string(block: String.() -> Unit): String =
    String().apply(block)

/**
 * TsConstructSignatureDeclaration#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> BindingIdentifierImpl
 */
public fun TsConstructSignatureDeclaration.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsConstructSignatureDeclaration#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> ArrayPatternImpl
 */
public fun TsConstructSignatureDeclaration.arrayPattern(block: ArrayPattern.() -> Unit):
    ArrayPattern = ArrayPatternImpl().apply(block)

/**
 * TsConstructSignatureDeclaration#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> RestElementImpl
 */
public fun TsConstructSignatureDeclaration.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * TsConstructSignatureDeclaration#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> ObjectPatternImpl
 */
public fun TsConstructSignatureDeclaration.objectPattern(block: ObjectPattern.() -> Unit):
    ObjectPattern = ObjectPatternImpl().apply(block)

/**
 * TsConstructSignatureDeclaration#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun TsConstructSignatureDeclaration.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit):
    TsTypeAnnotation = TsTypeAnnotationImpl().apply(block)

/**
 * TsConstructSignatureDeclaration#typeParams: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public
    fun TsConstructSignatureDeclaration.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * TsConstructSignatureDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsConstructSignatureDeclaration.span(block: Span.() -> Unit): Span =
    SpanImpl().apply(block)
