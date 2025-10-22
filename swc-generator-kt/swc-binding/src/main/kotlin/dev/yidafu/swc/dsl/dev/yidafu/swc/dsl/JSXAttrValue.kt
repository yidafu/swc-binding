package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.JSXAttrValue
import dev.yidafu.swc.types.JSXElement
import dev.yidafu.swc.types.JSXElementImpl
import dev.yidafu.swc.types.JSXExpressionContainer
import dev.yidafu.swc.types.JSXExpressionContainerImpl
import dev.yidafu.swc.types.JSXFragment
import dev.yidafu.swc.types.JSXFragmentImpl
import dev.yidafu.swc.types.JSXText
import dev.yidafu.swc.types.JSXTextImpl
import dev.yidafu.swc.types.NullLiteral
import dev.yidafu.swc.types.NullLiteralImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

/**
 * subtype of JSXAttrValue
 */
public fun JSXAttrValue.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * subtype of JSXAttrValue
 */
public fun JSXAttrValue.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * subtype of JSXAttrValue
 */
public fun JSXAttrValue.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * subtype of JSXAttrValue
 */
public fun JSXAttrValue.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * subtype of JSXAttrValue
 */
public fun JSXAttrValue.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * subtype of JSXAttrValue
 */
public fun JSXAttrValue.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * subtype of JSXAttrValue
 */
public fun JSXAttrValue.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * subtype of JSXAttrValue
 */
public fun JSXAttrValue.jSXExpressionContainer(block: JSXExpressionContainer.() -> Unit):
    JSXExpressionContainer = JSXExpressionContainerImpl().apply(block)

/**
 * subtype of JSXAttrValue
 */
public fun JSXAttrValue.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * subtype of JSXAttrValue
 */
public fun JSXAttrValue.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)
