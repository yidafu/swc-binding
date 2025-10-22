package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AssignmentPattern
import dev.yidafu.swc.types.AssignmentPatternImpl
import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.BindingIdentifierImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Decorator
import dev.yidafu.swc.types.DecoratorImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsParameterProperty
import kotlin.Unit

/**
 * TsParameterProperty#accessibility: String
 * extension function for create String -> String
 */
public fun TsParameterProperty.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsParameterProperty#readonly: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsParameterProperty.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsParameterProperty#param: TsParameterPropertyParameter
 * extension function for create TsParameterPropertyParameter -> BindingIdentifierImpl
 */
public fun TsParameterProperty.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsParameterProperty#param: TsParameterPropertyParameter
 * extension function for create TsParameterPropertyParameter -> AssignmentPatternImpl
 */
public fun TsParameterProperty.assignmentPattern(block: AssignmentPattern.() -> Unit):
    AssignmentPattern = AssignmentPatternImpl().apply(block)

/**
 * TsParameterProperty#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsParameterProperty.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * TsParameterProperty#decorators: Array<Decorator>
 * extension function for create Array<Decorator> -> DecoratorImpl
 */
public fun TsParameterProperty.decorator(block: Decorator.() -> Unit): Decorator =
    DecoratorImpl().apply(block)
