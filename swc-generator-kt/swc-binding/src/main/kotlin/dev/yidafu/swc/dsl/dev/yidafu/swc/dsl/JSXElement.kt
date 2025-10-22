package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.JSXClosingElement
import dev.yidafu.swc.types.JSXClosingElementImpl
import dev.yidafu.swc.types.JSXElement
import dev.yidafu.swc.types.JSXExpressionContainer
import dev.yidafu.swc.types.JSXExpressionContainerImpl
import dev.yidafu.swc.types.JSXFragment
import dev.yidafu.swc.types.JSXFragmentImpl
import dev.yidafu.swc.types.JSXOpeningElement
import dev.yidafu.swc.types.JSXOpeningElementImpl
import dev.yidafu.swc.types.JSXSpreadChild
import dev.yidafu.swc.types.JSXSpreadChildImpl
import dev.yidafu.swc.types.JSXText
import dev.yidafu.swc.types.JSXTextImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * JSXElement#type: String
 * extension function for create String -> String
 */
public fun JSXElement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXElement#opening: JSXOpeningElement
 * extension function for create JSXOpeningElement -> JSXOpeningElementImpl
 */
public fun JSXElement.jSXOpeningElement(block: JSXOpeningElement.() -> Unit): JSXOpeningElement =
    JSXOpeningElementImpl().apply(block)

/**
 * JSXElement#children: Array<JSXElementChild>
 * extension function for create Array<JSXElementChild> -> JSXTextImpl
 */
public fun JSXElement.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * JSXElement#children: Array<JSXElementChild>
 * extension function for create Array<JSXElementChild> -> JSXExpressionContainerImpl
 */
public fun JSXElement.jSXExpressionContainer(block: JSXExpressionContainer.() -> Unit):
    JSXExpressionContainer = JSXExpressionContainerImpl().apply(block)

/**
 * JSXElement#children: Array<JSXElementChild>
 * extension function for create Array<JSXElementChild> -> JSXSpreadChildImpl
 */
public fun JSXElement.jSXSpreadChild(block: JSXSpreadChild.() -> Unit): JSXSpreadChild =
    JSXSpreadChildImpl().apply(block)

/**
 * JSXElement#children: Array<JSXElementChild>
 * extension function for create Array<JSXElementChild> -> JSXFragmentImpl
 */
public fun JSXElement.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * JSXElement#closing: JSXClosingElement
 * extension function for create JSXClosingElement -> JSXClosingElementImpl
 */
public fun JSXElement.jSXClosingElement(block: JSXClosingElement.() -> Unit): JSXClosingElement =
    JSXClosingElementImpl().apply(block)

/**
 * JSXElement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun JSXElement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
