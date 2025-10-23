package dev.yidafu.swc.dsl

import Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.Span
import kotlin.String
import kotlin.Unit

/**
 * Identifier#baseField: String
 * extension function for create String -> String
 */
public fun Identifier.string(block: Identifier.() -> Unit): String {
}

/**
 * Identifier#optional: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun Identifier.boolean(block: Identifier.() -> Unit): Boolean {
}

/**
 * Identifier#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun Identifier.span(block: Identifier.() -> Unit): Span {
}
