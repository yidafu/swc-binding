package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.JSXElement
import dev.yidafu.swc.types.JSXElementChild
import dev.yidafu.swc.types.JSXElementImpl
import dev.yidafu.swc.types.JSXExpressionContainer
import dev.yidafu.swc.types.JSXExpressionContainerImpl
import dev.yidafu.swc.types.JSXFragment
import dev.yidafu.swc.types.JSXFragmentImpl
import dev.yidafu.swc.types.JSXSpreadChild
import dev.yidafu.swc.types.JSXSpreadChildImpl
import dev.yidafu.swc.types.JSXText
import dev.yidafu.swc.types.JSXTextImpl
import kotlin.Unit

/**
 * subtype of JSXElementChild
 */
public fun JSXElementChild.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * subtype of JSXElementChild
 */
public fun JSXElementChild.jSXExpressionContainer(block: JSXExpressionContainer.() -> Unit):
    JSXExpressionContainer = JSXExpressionContainerImpl().apply(block)

/**
 * subtype of JSXElementChild
 */
public fun JSXElementChild.jSXSpreadChild(block: JSXSpreadChild.() -> Unit): JSXSpreadChild =
    JSXSpreadChildImpl().apply(block)

/**
 * subtype of JSXElementChild
 */
public fun JSXElementChild.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * subtype of JSXElementChild
 */
public fun JSXElementChild.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)
