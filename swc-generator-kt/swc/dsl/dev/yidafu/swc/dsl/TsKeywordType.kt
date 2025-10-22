package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsKeywordType
import kotlin.Unit

/**
 * TsKeywordType#kind: String
 * extension function for create String -> String
 */
public fun TsKeywordType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsKeywordType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsKeywordType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
