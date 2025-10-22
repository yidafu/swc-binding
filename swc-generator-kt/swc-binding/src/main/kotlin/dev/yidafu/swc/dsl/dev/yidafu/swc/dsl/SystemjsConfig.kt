package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.SystemjsConfig
import kotlin.Unit

/**
 * SystemjsConfig#type: String
 * extension function for create String -> String
 */
public fun SystemjsConfig.string(block: String.() -> Unit): String = String().apply(block)

/**
 * SystemjsConfig#allowTopLevelThis: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun SystemjsConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
