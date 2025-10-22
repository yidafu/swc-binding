package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Container
import dev.yidafu.swc.types.T
import kotlin.Unit

/**
 * Container#value: T
 * extension function for create T -> T
 */
public fun Container.t(block: T.() -> Unit): T = T().apply(block)
