// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.008916

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BigIntLiteral
import dev.yidafu.swc.generated.BooleanLiteral
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.JSXAttribute
import dev.yidafu.swc.generated.JSXElement
import dev.yidafu.swc.generated.JSXExpressionContainer
import dev.yidafu.swc.generated.JSXFragment
import dev.yidafu.swc.generated.JSXNamespacedName
import dev.yidafu.swc.generated.JSXText
import dev.yidafu.swc.generated.NullLiteral
import dev.yidafu.swc.generated.NumericLiteral
import dev.yidafu.swc.generated.RegExpLiteral
import dev.yidafu.swc.generated.StringLiteral
import kotlin.Unit

/**
 * JSXAttribute#name: JSXAttributeName?
 * extension function for create JSXAttributeName? -> Identifier
 */
public fun JSXAttribute.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * JSXAttribute#name: JSXAttributeName?
 * extension function for create JSXAttributeName? -> JSXNamespacedName
 */
public fun JSXAttribute.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedName().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue?
 * extension function for create JSXAttrValue? -> JSXExpressionContainer
 */
public fun JSXAttribute.jSXExpressionContainer(block: JSXExpressionContainer.() -> Unit):
    JSXExpressionContainer = JSXExpressionContainer().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue?
 * extension function for create JSXAttrValue? -> JSXElement
 */
public fun JSXAttribute.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElement().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue?
 * extension function for create JSXAttrValue? -> JSXFragment
 */
public fun JSXAttribute.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragment().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue?
 * extension function for create JSXAttrValue? -> JSXText
 */
public fun JSXAttribute.jSXText(block: JSXText.() -> Unit): JSXText = JSXText().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue?
 * extension function for create JSXAttrValue? -> StringLiteral
 */
public fun JSXAttribute.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue?
 * extension function for create JSXAttrValue? -> BooleanLiteral
 */
public fun JSXAttribute.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteral().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue?
 * extension function for create JSXAttrValue? -> NullLiteral
 */
public fun JSXAttribute.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteral().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue?
 * extension function for create JSXAttrValue? -> RegExpLiteral
 */
public fun JSXAttribute.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteral().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue?
 * extension function for create JSXAttrValue? -> NumericLiteral
 */
public fun JSXAttribute.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue?
 * extension function for create JSXAttrValue? -> BigIntLiteral
 */
public fun JSXAttribute.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)
