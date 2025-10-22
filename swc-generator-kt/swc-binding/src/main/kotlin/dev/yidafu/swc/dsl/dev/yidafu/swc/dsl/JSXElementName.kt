package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.JSXElementName
import dev.yidafu.swc.types.JSXMemberExpression
import dev.yidafu.swc.types.JSXMemberExpressionImpl
import dev.yidafu.swc.types.JSXNamespacedName
import dev.yidafu.swc.types.JSXNamespacedNameImpl
import kotlin.Unit

/**
 * subtype of JSXElementName
 */
public fun JSXElementName.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * subtype of JSXElementName
 */
public fun JSXElementName.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * subtype of JSXElementName
 */
public fun JSXElementName.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName
    = JSXNamespacedNameImpl().apply(block)
