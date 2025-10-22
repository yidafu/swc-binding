package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.JSXAttribute
import dev.yidafu.swc.types.JSXAttributeImpl
import dev.yidafu.swc.types.JSXAttributeOrSpread
import dev.yidafu.swc.types.SpreadElement
import dev.yidafu.swc.types.SpreadElementImpl
import kotlin.Unit

/**
 * subtype of JSXAttributeOrSpread
 */
public fun JSXAttributeOrSpread.jSXAttribute(block: JSXAttribute.() -> Unit): JSXAttribute =
    JSXAttributeImpl().apply(block)

/**
 * subtype of JSXAttributeOrSpread
 */
public fun JSXAttributeOrSpread.spreadElement(block: SpreadElement.() -> Unit): SpreadElement =
    SpreadElementImpl().apply(block)
