package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AllOptional
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * AllOptional#field1: String
 * extension function for create String -> String
 */
public fun AllOptional.string(block: String.() -> Unit): String = String().apply(block)

/**
 * AllOptional#field2: Int
 * extension function for create Int -> Int
 */
public fun AllOptional.int(block: Int.() -> Unit): Int = Int().apply(block)

/**
 * AllOptional#field3: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun AllOptional.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
