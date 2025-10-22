package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.GenericArray
import dev.yidafu.swc.types.T
import kotlin.Unit

/**
 * GenericArray#items: Array<T>
 * extension function for create Array<T> -> T
 */
public fun GenericArray.t(block: T.() -> Unit): T = T().apply(block)
