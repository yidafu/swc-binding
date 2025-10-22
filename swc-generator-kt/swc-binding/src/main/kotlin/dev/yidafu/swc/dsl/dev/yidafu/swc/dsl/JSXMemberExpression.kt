package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.JSXMemberExpression
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * JSXMemberExpression#type: String
 * extension function for create String -> String
 */
public fun JSXMemberExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXMemberExpression#property: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun JSXMemberExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
