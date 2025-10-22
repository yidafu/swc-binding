package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsThisType
import dev.yidafu.swc.types.TsThisTypeImpl
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import dev.yidafu.swc.types.TsTypePredicate
import kotlin.Unit

/**
 * TsTypePredicate#type: String
 * extension function for create String -> String
 */
public fun TsTypePredicate.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsTypePredicate#asserts: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsTypePredicate.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsTypePredicate#paramName: TsThisTypeOrIdent
 * extension function for create TsThisTypeOrIdent -> TsThisTypeImpl
 */
public fun TsTypePredicate.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsTypePredicate#paramName: TsThisTypeOrIdent
 * extension function for create TsThisTypeOrIdent -> IdentifierImpl
 */
public fun TsTypePredicate.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsTypePredicate#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun TsTypePredicate.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

/**
 * TsTypePredicate#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsTypePredicate.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
