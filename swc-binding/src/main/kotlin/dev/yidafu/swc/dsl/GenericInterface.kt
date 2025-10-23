package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.GenericInterface
import dev.yidafu.swc.types.T
import kotlin.Unit

/**
 * GenericInterface#value: T
 * extension function for create T -> T
 */
public fun GenericInterface.t(block: GenericInterface.() -> Unit): T {
}
