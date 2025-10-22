package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsTypeParameter
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterImpl
import kotlin.Unit

/**
 * TsTypeParameterDeclaration#type: String
 * extension function for create String -> String
 */
public fun TsTypeParameterDeclaration.string(block: String.() -> Unit): String =
    String().apply(block)

/**
 * TsTypeParameterDeclaration#parameters: Array<TsTypeParameter>
 * extension function for create Array<TsTypeParameter> -> TsTypeParameterImpl
 */
public fun TsTypeParameterDeclaration.tsTypeParameter(block: TsTypeParameter.() -> Unit):
    TsTypeParameter = TsTypeParameterImpl().apply(block)

/**
 * TsTypeParameterDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsTypeParameterDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
