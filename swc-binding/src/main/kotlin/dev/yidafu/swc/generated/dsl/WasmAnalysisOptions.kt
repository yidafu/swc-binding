// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.209223

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.EsParserConfig
import dev.yidafu.swc.generated.TsParserConfig
import dev.yidafu.swc.generated.WasmAnalysisOptions
import kotlin.Unit

/**
 * WasmAnalysisOptions#parser: ParserConfig?
 * extension function for create ParserConfig? -> TsParserConfig
 */
public fun WasmAnalysisOptions.tsParserConfig(block: TsParserConfig.() -> Unit): TsParserConfig =
    TsParserConfig().apply(block)

/**
 * WasmAnalysisOptions#parser: ParserConfig?
 * extension function for create ParserConfig? -> EsParserConfig
 */
public fun WasmAnalysisOptions.esParserConfig(block: EsParserConfig.() -> Unit): EsParserConfig =
    EsParserConfig().apply(block)
