package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Combined
import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * Combined#ownProp: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun Combined.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * Combined#methodA: String
 * extension function for create String -> String
 */
public fun Combined.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Combined#methodB: Int
 * extension function for create Int -> Int
 */
public fun Combined.int(block: Int.() -> Unit): Int = Int().apply(block)
