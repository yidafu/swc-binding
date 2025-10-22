package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.JSXMemberExpression
import dev.yidafu.swc.types.JSXMemberExpressionImpl
import dev.yidafu.swc.types.JSXObject
import kotlin.Unit

/**
 * subtype of JSXObject
 */
public fun JSXObject.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression
    = JSXMemberExpressionImpl().apply(block)

/**
 * subtype of JSXObject
 */
public fun JSXObject.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
