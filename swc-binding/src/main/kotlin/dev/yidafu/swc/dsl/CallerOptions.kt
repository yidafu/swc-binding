package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.CallerOptions
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * CallerOptions#name: String
 * extension function for create String -> String
 */
public fun CallerOptions.string(block: String.() -> Unit): String = String().apply(block)
