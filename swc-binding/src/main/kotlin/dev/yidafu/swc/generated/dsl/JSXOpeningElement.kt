// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.216284

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.JSXAttribute
import dev.yidafu.swc.generated.JSXMemberExpression
import dev.yidafu.swc.generated.JSXNamespacedName
import dev.yidafu.swc.generated.JSXOpeningElement
import dev.yidafu.swc.generated.SpreadElement
import dev.yidafu.swc.generated.TsTypeParameterInstantiation
import kotlin.Unit

/**
 * JSXOpeningElement#name: JSXElementName?
 * extension function for create JSXElementName? -> JSXMemberExpression
 */
public fun JSXOpeningElement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpression().apply(block)

/**
 * JSXOpeningElement#name: JSXElementName?
 * extension function for create JSXElementName? -> JSXNamespacedName
 */
public fun JSXOpeningElement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedName().apply(block)

/**
 * JSXOpeningElement#name: JSXElementName?
 * extension function for create JSXElementName? -> Identifier
 */
public fun JSXOpeningElement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * JSXOpeningElement#attributes: Array<JSXAttributeOrSpread>?
 * extension function for create Array<JSXAttributeOrSpread>? -> SpreadElement
 */
public fun JSXOpeningElement.spreadElement(block: SpreadElement.() -> Unit): SpreadElement =
    SpreadElement().apply(block)

/**
 * JSXOpeningElement#attributes: Array<JSXAttributeOrSpread>?
 * extension function for create Array<JSXAttributeOrSpread>? -> JSXAttribute
 */
public fun JSXOpeningElement.jSXAttribute(block: JSXAttribute.() -> Unit): JSXAttribute =
    JSXAttribute().apply(block)

/**
 * JSXOpeningElement#typeArguments: TsTypeParameterInstantiation?
 * extension function for create TsTypeParameterInstantiation? -> TsTypeParameterInstantiation
 */
public
    fun JSXOpeningElement.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiation().apply(block)
