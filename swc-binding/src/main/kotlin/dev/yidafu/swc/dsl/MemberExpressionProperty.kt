package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

/**
 * subtype of MemberExpressionProperty
 */
fun MemberExpressionProperty.identifier(block: Identifier.() -> Unit): Identifier {
    return IdentifierImpl().apply(block)
}

/**
 * subtype of MemberExpressionProperty
 */
fun MemberExpressionProperty.privateName(block: PrivateName.() -> Unit): PrivateName {
    return PrivateNameImpl().apply(block)
}

/**
 * subtype of MemberExpressionProperty
 */
fun MemberExpressionProperty.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName {
    return ComputedPropNameImpl().apply(block)
}