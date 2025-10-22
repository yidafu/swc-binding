package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Combined
import dev.yidafu.swc.types.CombinedImpl
import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.MixinB
import kotlin.Unit

/**
 * subtype of MixinB
 */
public fun MixinB.combined(block: Combined.() -> Unit): Combined = CombinedImpl().apply(block)

/**
 * MixinB#methodB: Int
 * extension function for create Int -> Int
 */
public fun MixinB.int(block: Int.() -> Unit): Int = Int().apply(block)
