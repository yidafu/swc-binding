package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Output
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * Output#map: String
 * extension function for create String -> String
 */
public fun Output.string(block: String.() -> Unit): String = String().apply(block)
