// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.220991

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.JSXClosingFragment
import dev.yidafu.swc.generated.JSXElement
import dev.yidafu.swc.generated.JSXExpressionContainer
import dev.yidafu.swc.generated.JSXFragment
import dev.yidafu.swc.generated.JSXOpeningFragment
import dev.yidafu.swc.generated.JSXSpreadChild
import dev.yidafu.swc.generated.JSXText
import kotlin.Unit

/**
 * JSXFragment#opening: JSXOpeningFragment?
 * extension function for create JSXOpeningFragment? -> JSXOpeningFragment
 */
public fun JSXFragment.jSXOpeningFragment(block: JSXOpeningFragment.() -> Unit): JSXOpeningFragment
    = JSXOpeningFragment().apply(block)

/**
 * JSXFragment#children: Array<JSXElementChild>?
 * extension function for create Array<JSXElementChild>? -> JSXExpressionContainer
 */
public fun JSXFragment.jSXExpressionContainer(block: JSXExpressionContainer.() -> Unit):
    JSXExpressionContainer = JSXExpressionContainer().apply(block)

/**
 * JSXFragment#children: Array<JSXElementChild>?
 * extension function for create Array<JSXElementChild>? -> JSXSpreadChild
 */
public fun JSXFragment.jSXSpreadChild(block: JSXSpreadChild.() -> Unit): JSXSpreadChild =
    JSXSpreadChild().apply(block)

/**
 * JSXFragment#children: Array<JSXElementChild>?
 * extension function for create Array<JSXElementChild>? -> JSXText
 */
public fun JSXFragment.jSXText(block: JSXText.() -> Unit): JSXText = JSXText().apply(block)

/**
 * JSXFragment#children: Array<JSXElementChild>?
 * extension function for create Array<JSXElementChild>? -> JSXElement
 */
public fun JSXFragment.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElement().apply(block)

/**
 * JSXFragment#closing: JSXClosingFragment?
 * extension function for create JSXClosingFragment? -> JSXClosingFragment
 */
public fun JSXFragment.jSXClosingFragment(block: JSXClosingFragment.() -> Unit): JSXClosingFragment
    = JSXClosingFragment().apply(block)
