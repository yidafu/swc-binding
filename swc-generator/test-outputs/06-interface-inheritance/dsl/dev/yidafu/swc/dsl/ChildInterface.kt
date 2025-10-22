package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ChildInterface
import dev.yidafu.swc.types.GrandChildInterface
import dev.yidafu.swc.types.GrandChildInterfaceImpl
import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * subtype of ChildInterface
 */
public fun ChildInterface.grandChildInterface(block: GrandChildInterface.() -> Unit):
    GrandChildInterface = GrandChildInterfaceImpl().apply(block)

/**
 * ChildInterface#childProp: Int
 * extension function for create Int -> Int
 */
public fun ChildInterface.int(block: Int.() -> Unit): Int = Int().apply(block)

/**
 * ChildInterface#parentProp: String
 * extension function for create String -> String
 */
public fun ChildInterface.string(block: String.() -> Unit): String = String().apply(block)
