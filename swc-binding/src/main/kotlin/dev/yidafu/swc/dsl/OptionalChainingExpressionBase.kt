package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

/**
 * subtype of OptionalChainingExpressionBase
 */
fun OptionalChainingExpressionBase.memberExpression(block: MemberExpression.() -> Unit): MemberExpression {
    return MemberExpressionImpl().apply(block)
}

/**
 * subtype of OptionalChainingExpressionBase
 */
fun OptionalChainingExpressionBase.optionalChainingCall(block: OptionalChainingCall.() -> Unit): OptionalChainingCall {
    return OptionalChainingCallImpl().apply(block)
}