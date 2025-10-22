package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.EsParserConfig
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * EsParserConfig#syntax: String
 * extension function for create String -> String
 */
public fun EsParserConfig.string(block: String.() -> Unit): String = String().apply(block)

/**
 * EsParserConfig#importAssertions: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun EsParserConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
