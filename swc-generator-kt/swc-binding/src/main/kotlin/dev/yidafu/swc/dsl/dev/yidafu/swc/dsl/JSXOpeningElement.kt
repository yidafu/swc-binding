package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.JSXAttribute
import dev.yidafu.swc.types.JSXAttributeImpl
import dev.yidafu.swc.types.JSXMemberExpression
import dev.yidafu.swc.types.JSXMemberExpressionImpl
import dev.yidafu.swc.types.JSXNamespacedName
import dev.yidafu.swc.types.JSXNamespacedNameImpl
import dev.yidafu.swc.types.JSXOpeningElement
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.SpreadElement
import dev.yidafu.swc.types.SpreadElementImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsTypeParameterInstantiation
import dev.yidafu.swc.types.TsTypeParameterInstantiationImpl
import kotlin.Unit

/**
 * JSXOpeningElement#type: String
 * extension function for create String -> String
 */
public fun JSXOpeningElement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXOpeningElement#name: JSXElementName
 * extension function for create JSXElementName -> IdentifierImpl
 */
public fun JSXOpeningElement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * JSXOpeningElement#name: JSXElementName
 * extension function for create JSXElementName -> JSXMemberExpressionImpl
 */
public fun JSXOpeningElement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * JSXOpeningElement#name: JSXElementName
 * extension function for create JSXElementName -> JSXNamespacedNameImpl
 */
public fun JSXOpeningElement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * JSXOpeningElement#attributes: Array<JSXAttributeOrSpread>
 * extension function for create Array<JSXAttributeOrSpread> -> JSXAttributeImpl
 */
public fun JSXOpeningElement.jSXAttribute(block: JSXAttribute.() -> Unit): JSXAttribute =
    JSXAttributeImpl().apply(block)

/**
 * JSXOpeningElement#attributes: Array<JSXAttributeOrSpread>
 * extension function for create Array<JSXAttributeOrSpread> -> SpreadElementImpl
 */
public fun JSXOpeningElement.spreadElement(block: SpreadElement.() -> Unit): SpreadElement =
    SpreadElementImpl().apply(block)

/**
 * JSXOpeningElement#selfClosing: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun JSXOpeningElement.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * JSXOpeningElement#typeArguments: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
public
    fun JSXOpeningElement.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * JSXOpeningElement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun JSXOpeningElement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
