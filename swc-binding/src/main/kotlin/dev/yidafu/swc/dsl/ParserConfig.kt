package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.EsParserConfig
import dev.yidafu.swc.types.EsParserConfigImpl
import dev.yidafu.swc.types.ParseOptions
import dev.yidafu.swc.types.ParserConfig
import dev.yidafu.swc.types.TsParserConfig
import dev.yidafu.swc.types.TsParserConfigImpl
import kotlin.Unit

/**
 * subtype of ParserConfig
 */
public fun ParserConfig.parseOptions(block: ParseOptions.() -> Unit): ParseOptions =
    ParseOptions().apply(block)

/**
 * subtype of ParserConfig
 */
public fun ParserConfig.tsParserConfig(block: TsParserConfig.() -> Unit): TsParserConfig =
    TsParserConfigImpl().apply(block)

/**
 * subtype of ParserConfig
 */
public fun ParserConfig.esParserConfig(block: EsParserConfig.() -> Unit): EsParserConfig =
    EsParserConfigImpl().apply(block)
