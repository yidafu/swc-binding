package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.ItemOne
import kotlin.Unit

/**
 * ItemOne#id: Int
 * extension function for create Int -> Int
 */
public fun ItemOne.int(block: Int.() -> Unit): Int = Int().apply(block)
