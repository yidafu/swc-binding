package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Combined
import dev.yidafu.swc.types.CombinedImpl
import dev.yidafu.swc.types.MixinA
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * subtype of MixinA
 */
public fun MixinA.combined(block: Combined.() -> Unit): Combined = CombinedImpl().apply(block)

/**
 * MixinA#methodA: String
 * extension function for create String -> String
 */
public fun MixinA.string(block: String.() -> Unit): String = String().apply(block)
