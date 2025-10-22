package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BaseParseOptions
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.EsParserConfig
import dev.yidafu.swc.types.EsParserConfigImpl
import dev.yidafu.swc.types.ParseOptions
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsParserConfig
import dev.yidafu.swc.types.TsParserConfigImpl
import kotlin.Unit

/**
 * subtype of BaseParseOptions
 */
public fun BaseParseOptions.parseOptions(block: ParseOptions.() -> Unit): ParseOptions =
    ParseOptions().apply(block)

/**
 * subtype of BaseParseOptions
 */
public fun BaseParseOptions.tsParserConfig(block: TsParserConfig.() -> Unit): TsParserConfig =
    TsParserConfigImpl().apply(block)

/**
 * subtype of BaseParseOptions
 */
public fun BaseParseOptions.esParserConfig(block: EsParserConfig.() -> Unit): EsParserConfig =
    EsParserConfigImpl().apply(block)

/**
 * BaseParseOptions#script: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun BaseParseOptions.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * BaseParseOptions#target: String
 * extension function for create String -> String
 */
public fun BaseParseOptions.string(block: String.() -> Unit): String = String().apply(block)
