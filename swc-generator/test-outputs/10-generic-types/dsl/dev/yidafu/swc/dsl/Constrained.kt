package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Constrained
import dev.yidafu.swc.types.T
import kotlin.Unit

/**
 * Constrained#data: T
 * extension function for create T -> T
 */
public fun Constrained.t(block: T.() -> Unit): T = T().apply(block)
