package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AllRequired
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * AllRequired#field1: String
 * extension function for create String -> String
 */
public fun AllRequired.string(block: String.() -> Unit): String = String().apply(block)

/**
 * AllRequired#field2: Int
 * extension function for create Int -> Int
 */
public fun AllRequired.int(block: Int.() -> Unit): Int = Int().apply(block)

/**
 * AllRequired#field3: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun AllRequired.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
