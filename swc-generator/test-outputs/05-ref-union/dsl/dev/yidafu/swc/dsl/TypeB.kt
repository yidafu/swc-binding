package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TypeB
import kotlin.Unit

/**
 * TypeB#kind: String
 * extension function for create String -> String
 */
public fun TypeB.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TypeB#valueB: Int
 * extension function for create Int -> Int
 */
public fun TypeB.int(block: Int.() -> Unit): Int = Int().apply(block)
