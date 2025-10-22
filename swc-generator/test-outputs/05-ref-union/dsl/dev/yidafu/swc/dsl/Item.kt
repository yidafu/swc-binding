package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Item
import dev.yidafu.swc.types.ItemOne
import dev.yidafu.swc.types.ItemOneImpl
import dev.yidafu.swc.types.ItemTwo
import dev.yidafu.swc.types.ItemTwoImpl
import kotlin.Unit

/**
 * subtype of Item
 */
public fun Item.itemOne(block: ItemOne.() -> Unit): ItemOne = ItemOneImpl().apply(block)

/**
 * subtype of Item
 */
public fun Item.itemTwo(block: ItemTwo.() -> Unit): ItemTwo = ItemTwoImpl().apply(block)
