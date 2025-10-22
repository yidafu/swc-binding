package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TypeA
import kotlin.Unit

/**
 * TypeA#valueA: String
 * extension function for create String -> String
 */
public fun TypeA.string(block: String.() -> Unit): String = String().apply(block)
