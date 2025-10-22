package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsThisType
import kotlin.Unit

/**
 * TsThisType#type: String
 * extension function for create String -> String
 */
public fun TsThisType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsThisType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsThisType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
