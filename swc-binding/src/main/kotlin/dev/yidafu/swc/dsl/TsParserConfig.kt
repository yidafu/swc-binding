package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsParserConfig
import kotlin.Unit

/**
 * TsParserConfig#syntax: String
 * extension function for create String -> String
 */
public fun TsParserConfig.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsParserConfig#dynamicImport: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsParserConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
