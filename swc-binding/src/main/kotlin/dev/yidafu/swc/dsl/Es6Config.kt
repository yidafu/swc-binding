package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Es6Config
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * Es6Config#importInterop: String
 * extension function for create String -> String
 */
public fun Es6Config.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Es6Config#preserveImportMeta: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun Es6Config.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
