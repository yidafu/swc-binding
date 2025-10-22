package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.JSXAttribute
import dev.yidafu.swc.types.JSXElement
import dev.yidafu.swc.types.JSXElementImpl
import dev.yidafu.swc.types.JSXExpressionContainer
import dev.yidafu.swc.types.JSXExpressionContainerImpl
import dev.yidafu.swc.types.JSXFragment
import dev.yidafu.swc.types.JSXFragmentImpl
import dev.yidafu.swc.types.JSXNamespacedName
import dev.yidafu.swc.types.JSXNamespacedNameImpl
import dev.yidafu.swc.types.JSXText
import dev.yidafu.swc.types.JSXTextImpl
import dev.yidafu.swc.types.NullLiteral
import dev.yidafu.swc.types.NullLiteralImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

/**
 * JSXAttribute#type: String
 * extension function for create String -> String
 */
public fun JSXAttribute.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXAttribute#name: JSXAttributeName
 * extension function for create JSXAttributeName -> IdentifierImpl
 */
public fun JSXAttribute.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * JSXAttribute#name: JSXAttributeName
 * extension function for create JSXAttributeName -> JSXNamespacedNameImpl
 */
public fun JSXAttribute.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedNameImpl().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue
 * extension function for create JSXAttrValue -> StringLiteralImpl
 */
public fun JSXAttribute.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue
 * extension function for create JSXAttrValue -> BooleanLiteralImpl
 */
public fun JSXAttribute.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue
 * extension function for create JSXAttrValue -> NullLiteralImpl
 */
public fun JSXAttribute.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue
 * extension function for create JSXAttrValue -> NumericLiteralImpl
 */
public fun JSXAttribute.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue
 * extension function for create JSXAttrValue -> BigIntLiteralImpl
 */
public fun JSXAttribute.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue
 * extension function for create JSXAttrValue -> RegExpLiteralImpl
 */
public fun JSXAttribute.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue
 * extension function for create JSXAttrValue -> JSXTextImpl
 */
public fun JSXAttribute.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue
 * extension function for create JSXAttrValue -> JSXExpressionContainerImpl
 */
public fun JSXAttribute.jSXExpressionContainer(block: JSXExpressionContainer.() -> Unit):
    JSXExpressionContainer = JSXExpressionContainerImpl().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue
 * extension function for create JSXAttrValue -> JSXElementImpl
 */
public fun JSXAttribute.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * JSXAttribute#value: JSXAttrValue
 * extension function for create JSXAttrValue -> JSXFragmentImpl
 */
public fun JSXAttribute.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * JSXAttribute#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun JSXAttribute.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
