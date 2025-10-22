package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * Identifier#value: String
 * extension function for create String -> String
 */
public fun Identifier.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Identifier#optional: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun Identifier.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * Identifier#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun Identifier.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
