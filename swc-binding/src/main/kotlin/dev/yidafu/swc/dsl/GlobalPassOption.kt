package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.GlobalPassOption
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * GlobalPassOption#envs: Array<String>
 * extension function for create Array<String> -> String
 */
public fun GlobalPassOption.string(block: String.() -> Unit): String = String().apply(block)
