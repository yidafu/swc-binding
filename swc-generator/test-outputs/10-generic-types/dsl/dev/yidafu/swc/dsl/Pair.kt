package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.K
import dev.yidafu.swc.types.Pair
import dev.yidafu.swc.types.V
import kotlin.Unit

/**
 * Pair#key: K
 * extension function for create K -> K
 */
public fun Pair.k(block: K.() -> Unit): K = K().apply(block)

/**
 * Pair#value: V
 * extension function for create V -> V
 */
public fun Pair.v(block: V.() -> Unit): V = V().apply(block)
