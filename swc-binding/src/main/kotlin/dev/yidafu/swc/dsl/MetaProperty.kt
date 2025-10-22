package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.MetaProperty
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * MetaProperty#kind: String
 * extension function for create String -> String
 */
public fun MetaProperty.string(block: String.() -> Unit): String = String().apply(block)

/**
 * MetaProperty#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun MetaProperty.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
