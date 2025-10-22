package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.ReactConfig
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * ReactConfig#importSource: String
 * extension function for create String -> String
 */
public fun ReactConfig.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ReactConfig#refresh: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ReactConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
