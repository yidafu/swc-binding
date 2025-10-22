package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ItemTwo
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * ItemTwo#name: String
 * extension function for create String -> String
 */
public fun ItemTwo.string(block: String.() -> Unit): String = String().apply(block)
