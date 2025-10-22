package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.JSXClosingFragment
import dev.yidafu.swc.types.JSXClosingFragmentImpl
import dev.yidafu.swc.types.JSXElement
import dev.yidafu.swc.types.JSXElementImpl
import dev.yidafu.swc.types.JSXExpressionContainer
import dev.yidafu.swc.types.JSXExpressionContainerImpl
import dev.yidafu.swc.types.JSXFragment
import dev.yidafu.swc.types.JSXOpeningFragment
import dev.yidafu.swc.types.JSXOpeningFragmentImpl
import dev.yidafu.swc.types.JSXSpreadChild
import dev.yidafu.swc.types.JSXSpreadChildImpl
import dev.yidafu.swc.types.JSXText
import dev.yidafu.swc.types.JSXTextImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * JSXFragment#type: String
 * extension function for create String -> String
 */
public fun JSXFragment.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXFragment#opening: JSXOpeningFragment
 * extension function for create JSXOpeningFragment -> JSXOpeningFragmentImpl
 */
public fun JSXFragment.jSXOpeningFragment(block: JSXOpeningFragment.() -> Unit): JSXOpeningFragment
    = JSXOpeningFragmentImpl().apply(block)

/**
 * JSXFragment#children: Array<JSXElementChild>
 * extension function for create Array<JSXElementChild> -> JSXTextImpl
 */
public fun JSXFragment.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * JSXFragment#children: Array<JSXElementChild>
 * extension function for create Array<JSXElementChild> -> JSXExpressionContainerImpl
 */
public fun JSXFragment.jSXExpressionContainer(block: JSXExpressionContainer.() -> Unit):
    JSXExpressionContainer = JSXExpressionContainerImpl().apply(block)

/**
 * JSXFragment#children: Array<JSXElementChild>
 * extension function for create Array<JSXElementChild> -> JSXSpreadChildImpl
 */
public fun JSXFragment.jSXSpreadChild(block: JSXSpreadChild.() -> Unit): JSXSpreadChild =
    JSXSpreadChildImpl().apply(block)

/**
 * JSXFragment#children: Array<JSXElementChild>
 * extension function for create Array<JSXElementChild> -> JSXElementImpl
 */
public fun JSXFragment.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * JSXFragment#closing: JSXClosingFragment
 * extension function for create JSXClosingFragment -> JSXClosingFragmentImpl
 */
public fun JSXFragment.jSXClosingFragment(block: JSXClosingFragment.() -> Unit): JSXClosingFragment
    = JSXClosingFragmentImpl().apply(block)

/**
 * JSXFragment#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun JSXFragment.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
