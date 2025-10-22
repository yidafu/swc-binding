package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.ListNode
import kotlin.Unit

/**
 * ListNode#data: Int
 * extension function for create Int -> Int
 */
public fun ListNode.int(block: Int.() -> Unit): Int = Int().apply(block)
