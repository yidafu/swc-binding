package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AmdConfig
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * AmdConfig#importInterop: String
 * extension function for create String -> String
 */
public fun AmdConfig.string(block: String.() -> Unit): String = String().apply(block)

/**
 * AmdConfig#preserveImportMeta: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun AmdConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
