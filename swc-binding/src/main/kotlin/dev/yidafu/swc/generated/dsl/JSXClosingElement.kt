// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.098528

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.JSXClosingElement
import dev.yidafu.swc.generated.JSXMemberExpression
import dev.yidafu.swc.generated.JSXNamespacedName
import kotlin.Unit

/**
 * JSXClosingElement#name: JSXElementName?
 * extension function for create JSXElementName? -> JSXMemberExpression
 */
public fun JSXClosingElement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpression().apply(block)

/**
 * JSXClosingElement#name: JSXElementName?
 * extension function for create JSXElementName? -> JSXNamespacedName
 */
public fun JSXClosingElement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedName().apply(block)

/**
 * JSXClosingElement#name: JSXElementName?
 * extension function for create JSXElementName? -> Identifier
 */
public fun JSXClosingElement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
