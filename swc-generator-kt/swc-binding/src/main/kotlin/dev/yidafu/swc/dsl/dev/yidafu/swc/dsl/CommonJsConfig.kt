package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.CommonJsConfig
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * CommonJsConfig#importInterop: String
 * extension function for create String -> String
 */
public fun CommonJsConfig.string(block: String.() -> Unit): String = String().apply(block)

/**
 * CommonJsConfig#preserveImportMeta: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun CommonJsConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
