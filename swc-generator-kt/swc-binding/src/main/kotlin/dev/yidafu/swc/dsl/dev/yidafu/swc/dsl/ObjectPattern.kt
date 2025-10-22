package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AssignmentPatternProperty
import dev.yidafu.swc.types.AssignmentPatternPropertyImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.KeyValuePatternProperty
import dev.yidafu.swc.types.KeyValuePatternPropertyImpl
import dev.yidafu.swc.types.ObjectPattern
import dev.yidafu.swc.types.RestElement
import dev.yidafu.swc.types.RestElementImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import kotlin.Unit

/**
 * ObjectPattern#type: String
 * extension function for create String -> String
 */
public fun ObjectPattern.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ObjectPattern#properties: Array<ObjectPatternProperty>
 * extension function for create Array<ObjectPatternProperty> -> KeyValuePatternPropertyImpl
 */
public fun ObjectPattern.keyValuePatternProperty(block: KeyValuePatternProperty.() -> Unit):
    KeyValuePatternProperty = KeyValuePatternPropertyImpl().apply(block)

/**
 * ObjectPattern#properties: Array<ObjectPatternProperty>
 * extension function for create Array<ObjectPatternProperty> -> AssignmentPatternPropertyImpl
 */
public fun ObjectPattern.assignmentPatternProperty(block: AssignmentPatternProperty.() -> Unit):
    AssignmentPatternProperty = AssignmentPatternPropertyImpl().apply(block)

/**
 * ObjectPattern#properties: Array<ObjectPatternProperty>
 * extension function for create Array<ObjectPatternProperty> -> RestElementImpl
 */
public fun ObjectPattern.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * ObjectPattern#optional: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ObjectPattern.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * ObjectPattern#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun ObjectPattern.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

/**
 * ObjectPattern#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ObjectPattern.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
