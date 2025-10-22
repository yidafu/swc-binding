package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.UmdConfig
import kotlin.Unit

/**
 * UmdConfig#importInterop: String
 * extension function for create String -> String
 */
public fun UmdConfig.string(block: String.() -> Unit): String = String().apply(block)

/**
 * UmdConfig#preserveImportMeta: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun UmdConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
