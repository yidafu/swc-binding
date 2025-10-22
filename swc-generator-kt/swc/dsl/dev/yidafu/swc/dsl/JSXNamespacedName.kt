package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.JSXNamespacedName
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * JSXNamespacedName#type: String
 * extension function for create String -> String
 */
public fun JSXNamespacedName.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXNamespacedName#name: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun JSXNamespacedName.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
