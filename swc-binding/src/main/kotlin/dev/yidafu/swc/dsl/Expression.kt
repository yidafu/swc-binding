package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Expression
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.StringLiteral
import kotlin.Unit

/**
 * subtype of Expression
 */
public fun Expression.identifier(block: Expression.() -> Unit): Identifier {
}

/**
 * subtype of Expression
 */
public fun Expression.stringLiteral(block: Expression.() -> Unit): StringLiteral {
}

/**
 * subtype of Expression
 */
public fun Expression.numericLiteral(block: Expression.() -> Unit): NumericLiteral {
}
