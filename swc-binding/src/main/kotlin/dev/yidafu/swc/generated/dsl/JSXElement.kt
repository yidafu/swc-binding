// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.006286

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.JSXClosingElement
import dev.yidafu.swc.generated.JSXElement
import dev.yidafu.swc.generated.JSXExpressionContainer
import dev.yidafu.swc.generated.JSXFragment
import dev.yidafu.swc.generated.JSXOpeningElement
import dev.yidafu.swc.generated.JSXSpreadChild
import dev.yidafu.swc.generated.JSXText
import kotlin.Unit

/**
 * JSXElement#opening: JSXOpeningElement?
 * extension function for create JSXOpeningElement? -> JSXOpeningElement
 */
public fun JSXElement.jSXOpeningElement(block: JSXOpeningElement.() -> Unit): JSXOpeningElement =
    JSXOpeningElement().apply(block)

/**
 * JSXElement#children: Array<JSXElementChild>?
 * extension function for create Array<JSXElementChild>? -> JSXExpressionContainer
 */
public fun JSXElement.jSXExpressionContainer(block: JSXExpressionContainer.() -> Unit):
    JSXExpressionContainer = JSXExpressionContainer().apply(block)

/**
 * JSXElement#children: Array<JSXElementChild>?
 * extension function for create Array<JSXElementChild>? -> JSXSpreadChild
 */
public fun JSXElement.jSXSpreadChild(block: JSXSpreadChild.() -> Unit): JSXSpreadChild =
    JSXSpreadChild().apply(block)

/**
 * JSXElement#children: Array<JSXElementChild>?
 * extension function for create Array<JSXElementChild>? -> JSXText
 */
public fun JSXElement.jSXText(block: JSXText.() -> Unit): JSXText = JSXText().apply(block)

/**
 * JSXElement#children: Array<JSXElementChild>?
 * extension function for create Array<JSXElementChild>? -> JSXFragment
 */
public fun JSXElement.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragment().apply(block)

/**
 * JSXElement#closing: JSXClosingElement?
 * extension function for create JSXClosingElement? -> JSXClosingElement
 */
public fun JSXElement.jSXClosingElement(block: JSXClosingElement.() -> Unit): JSXClosingElement =
    JSXClosingElement().apply(block)
