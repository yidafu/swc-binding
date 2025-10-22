package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
import dev.yidafu.swc.types.AssignmentPattern
import dev.yidafu.swc.types.AssignmentPatternImpl
import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.BindingIdentifierImpl
import dev.yidafu.swc.types.ObjectPattern
import dev.yidafu.swc.types.ObjectPatternImpl
import dev.yidafu.swc.types.PatternBase
import dev.yidafu.swc.types.RestElement
import dev.yidafu.swc.types.RestElementImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import kotlin.Unit

/**
 * subtype of PatternBase
 */
public fun PatternBase.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier =
    BindingIdentifierImpl().apply(block)

/**
 * subtype of PatternBase
 */
public fun PatternBase.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * subtype of PatternBase
 */
public fun PatternBase.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * subtype of PatternBase
 */
public fun PatternBase.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern =
    AssignmentPatternImpl().apply(block)

/**
 * subtype of PatternBase
 */
public fun PatternBase.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * PatternBase#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun PatternBase.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

/**
 * PatternBase#type: String
 * extension function for create String -> String
 */
public fun PatternBase.string(block: String.() -> Unit): String = String().apply(block)

/**
 * PatternBase#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun PatternBase.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
