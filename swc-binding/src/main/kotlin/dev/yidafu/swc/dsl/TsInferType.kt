package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsInferType
import dev.yidafu.swc.types.TsTypeParameter
import dev.yidafu.swc.types.TsTypeParameterImpl
import kotlin.Unit

/**
 * TsInferType#type: String
 * extension function for create String -> String
 */
public fun TsInferType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsInferType#typeParam: TsTypeParameter
 * extension function for create TsTypeParameter -> TsTypeParameterImpl
 */
public fun TsInferType.tsTypeParameter(block: TsTypeParameter.() -> Unit): TsTypeParameter =
    TsTypeParameterImpl().apply(block)

/**
 * TsInferType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsInferType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
