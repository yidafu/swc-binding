package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.MemberExpression
import dev.yidafu.swc.types.MemberExpressionImpl
import dev.yidafu.swc.types.OptionalChainingCall
import dev.yidafu.swc.types.OptionalChainingCallImpl
import dev.yidafu.swc.types.OptionalChainingExpression
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * OptionalChainingExpression#type: String
 * extension function for create String -> String
 */
public fun OptionalChainingExpression.string(block: String.() -> Unit): String =
    String().apply(block)

/**
 * OptionalChainingExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun OptionalChainingExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * OptionalChainingExpression#base: Union.U2<MemberExpression, OptionalChainingCall>
 * extension function for create Union.U2<MemberExpression, OptionalChainingCall> ->
 * MemberExpressionImpl
 */
public fun OptionalChainingExpression.memberExpression(block: MemberExpression.() -> Unit):
    MemberExpression = MemberExpressionImpl().apply(block)

/**
 * OptionalChainingExpression#base: Union.U2<MemberExpression, OptionalChainingCall>
 * extension function for create Union.U2<MemberExpression, OptionalChainingCall> ->
 * OptionalChainingCallImpl
 */
public fun OptionalChainingExpression.optionalChainingCall(block: OptionalChainingCall.() -> Unit):
    OptionalChainingCall = OptionalChainingCallImpl().apply(block)
