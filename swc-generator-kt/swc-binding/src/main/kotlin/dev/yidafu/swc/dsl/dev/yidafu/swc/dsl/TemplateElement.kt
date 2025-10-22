package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TemplateElement
import kotlin.Unit

/**
 * TemplateElement#raw: String
 * extension function for create String -> String
 */
public fun TemplateElement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TemplateElement#tail: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TemplateElement.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TemplateElement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TemplateElement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
