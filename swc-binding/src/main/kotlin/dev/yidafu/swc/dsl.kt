package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.*
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * @sample dev.yidafu.swc.sample.createExampleDsl
 */

fun module(block: Module.() -> Unit): Module = createModule(block)

fun options(block: Options.() -> Unit): Options = Options().apply(block)

fun tsParserConfig(block: TsParserConfig.() -> Unit): TsParserConfig = createTsParserConfig(block)

fun esParserConfig(block: EsParserConfig.() -> Unit): EsParserConfig = createEsParserConfig(block)

fun tsParseOptions(block: TsParserConfig.() -> Unit = {}): TsParserConfig = tsParserConfig(block)

fun esParseOptions(block: EsParserConfig.() -> Unit = {}): EsParserConfig = esParserConfig(block)
