package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ComputedPropName
import dev.yidafu.swc.types.ComputedPropNameImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.Super
import dev.yidafu.swc.types.SuperImpl
import dev.yidafu.swc.types.SuperPropExpression
import kotlin.Unit

/**
 * SuperPropExpression#type: String
 * extension function for create String -> String
 */
public fun SuperPropExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * SuperPropExpression#obj: Super
 * extension function for create Super -> SuperImpl
 */
public fun SuperPropExpression.jsSuper(block: Super.() -> Unit): Super = SuperImpl().apply(block)

/**
 * SuperPropExpression#property: Union.U2<Identifier, ComputedPropName>
 * extension function for create Union.U2<Identifier, ComputedPropName> -> IdentifierImpl
 */
public fun SuperPropExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * SuperPropExpression#property: Union.U2<Identifier, ComputedPropName>
 * extension function for create Union.U2<Identifier, ComputedPropName> -> ComputedPropNameImpl
 */
public fun SuperPropExpression.computedPropName(block: ComputedPropName.() -> Unit):
    ComputedPropName = ComputedPropNameImpl().apply(block)

/**
 * SuperPropExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun SuperPropExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
