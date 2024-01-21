package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

/**
 * subtype of SuperPropExpressionProperty
 */
fun SuperPropExpressionProperty.identifier(block: Identifier.() -> Unit): Identifier {
    return IdentifierImpl().apply(block)
}

/**
 * subtype of SuperPropExpressionProperty
 */
fun SuperPropExpressionProperty.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName {
    return ComputedPropNameImpl().apply(block)
}