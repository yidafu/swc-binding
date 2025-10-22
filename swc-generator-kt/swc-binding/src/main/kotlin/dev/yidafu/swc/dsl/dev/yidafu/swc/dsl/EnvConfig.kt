package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.EnvConfig
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * EnvConfig#path: String
 * extension function for create String -> String
 */
public fun EnvConfig.string(block: String.() -> Unit): String = String().apply(block)

/**
 * EnvConfig#forceAllTransforms: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun EnvConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
