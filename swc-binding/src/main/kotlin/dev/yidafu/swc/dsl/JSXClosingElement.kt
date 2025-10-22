package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.JSXClosingElement
import dev.yidafu.swc.types.JSXMemberExpression
import dev.yidafu.swc.types.JSXMemberExpressionImpl
import dev.yidafu.swc.types.JSXNamespacedName
import dev.yidafu.swc.types.JSXNamespacedNameImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * JSXClosingElement#type: String
 * extension function for create String -> String
 */
public fun JSXClosingElement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXClosingElement#name: JSXElementName
 * extension function for create JSXElementName -> IdentifierImpl
 */
public fun JSXClosingElement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * JSXClosingElement#name: JSXElementName
 * extension function for create JSXElementName -> JSXMemberExpressionImpl
 */
public fun JSXClosingElement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * JSXClosingElement#name: JSXElementName
 * extension function for create JSXElementName -> JSXNamespacedNameImpl
 */
public fun JSXClosingElement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * JSXClosingElement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun JSXClosingElement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
