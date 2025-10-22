package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.NodeNextConfig
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * NodeNextConfig#importInterop: String
 * extension function for create String -> String
 */
public fun NodeNextConfig.string(block: String.() -> Unit): String = String().apply(block)

/**
 * NodeNextConfig#preserveImportMeta: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun NodeNextConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
