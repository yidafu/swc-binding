package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.GrandChildInterface
import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * GrandChildInterface#grandChildProp: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun GrandChildInterface.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * GrandChildInterface#childProp: Int
 * extension function for create Int -> Int
 */
public fun GrandChildInterface.int(block: Int.() -> Unit): Int = Int().apply(block)

/**
 * GrandChildInterface#parentProp: String
 * extension function for create String -> String
 */
public fun GrandChildInterface.string(block: String.() -> Unit): String = String().apply(block)
