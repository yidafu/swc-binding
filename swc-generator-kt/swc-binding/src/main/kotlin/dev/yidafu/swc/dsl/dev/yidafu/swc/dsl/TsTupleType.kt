package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsTupleElement
import dev.yidafu.swc.types.TsTupleElementImpl
import dev.yidafu.swc.types.TsTupleType
import kotlin.Unit

/**
 * TsTupleType#type: String
 * extension function for create String -> String
 */
public fun TsTupleType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsTupleType#elemTypes: Array<TsTupleElement>
 * extension function for create Array<TsTupleElement> -> TsTupleElementImpl
 */
public fun TsTupleType.tsTupleElement(block: TsTupleElement.() -> Unit): TsTupleElement =
    TsTupleElementImpl().apply(block)

/**
 * TsTupleType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsTupleType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
