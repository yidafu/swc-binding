package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayTypes
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * ArrayTypes#optionalArray: Array<String>
 * extension function for create Array<String> -> String
 */
public fun ArrayTypes.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ArrayTypes#mixedArray: Array<Union.U2<String, Int>>
 * extension function for create Array<Union.U2<String, Int>> -> Int
 */
public fun ArrayTypes.int(block: Int.() -> Unit): Int = Int().apply(block)

/**
 * ArrayTypes#booleanArray: Array<Boolean>
 * extension function for create Array<Boolean> -> Boolean
 */
public fun ArrayTypes.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
