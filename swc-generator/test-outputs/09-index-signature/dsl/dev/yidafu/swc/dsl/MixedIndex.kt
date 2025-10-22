package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.MixedIndex
import kotlin.Unit

/**
 * MixedIndex#specificProp: Int
 * extension function for create Int -> Int
 */
public fun MixedIndex.int(block: Int.() -> Unit): Int = Int().apply(block)
