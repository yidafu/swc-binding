package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

/**
 * SuperPropExpression#obj: Super
 * extension function for create Super -> SuperImpl
 */
fun SuperPropExpression.jsSuper(block: Super.() -> Unit): Super {
    return SuperImpl().apply(block)
}

/**
 * SuperPropExpression#property: SuperPropExpressionProperty
 * extension function for create SuperPropExpressionProperty -> IdentifierImpl
 */
fun SuperPropExpression.identifier(block: Identifier.() -> Unit): Identifier {
    return IdentifierImpl().apply(block)
}

/**
 * SuperPropExpression#property: SuperPropExpressionProperty
 * extension function for create SuperPropExpressionProperty -> ComputedPropNameImpl
 */
fun SuperPropExpression.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName {
    return ComputedPropNameImpl().apply(block)
}

fun SuperPropExpression.span(block: Span.() -> Unit): Span {
    return Span().apply(block)
}