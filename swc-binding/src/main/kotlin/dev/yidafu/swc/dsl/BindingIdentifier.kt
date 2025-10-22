package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import kotlin.Unit

/**
 * BindingIdentifier#value: String
 * extension function for create String -> String
 */
public fun BindingIdentifier.string(block: String.() -> Unit): String = String().apply(block)

/**
 * BindingIdentifier#optional: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun BindingIdentifier.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * BindingIdentifier#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun BindingIdentifier.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation
    = TsTypeAnnotationImpl().apply(block)

/**
 * BindingIdentifier#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun BindingIdentifier.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
