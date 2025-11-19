// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:22.958953

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Assumptions
import dev.yidafu.swc.generated.EsParserConfig
import dev.yidafu.swc.generated.JsMinifyOptions
import dev.yidafu.swc.generated.JscConfig
import dev.yidafu.swc.generated.TransformConfig
import dev.yidafu.swc.generated.TsParserConfig
import kotlin.Unit

/**
 * JscConfig#assumptions: Assumptions?
 * extension function for create Assumptions? -> Assumptions
 */
public fun JscConfig.assumptions(block: Assumptions.() -> Unit): Assumptions =
    Assumptions().apply(block)

/**
 * JscConfig#parser: ParserConfig?
 * extension function for create ParserConfig? -> TsParserConfig
 */
public fun JscConfig.tsParserConfig(block: TsParserConfig.() -> Unit): TsParserConfig =
    TsParserConfig().apply(block)

/**
 * JscConfig#parser: ParserConfig?
 * extension function for create ParserConfig? -> EsParserConfig
 */
public fun JscConfig.esParserConfig(block: EsParserConfig.() -> Unit): EsParserConfig =
    EsParserConfig().apply(block)

/**
 * JscConfig#transform: TransformConfig?
 * extension function for create TransformConfig? -> TransformConfig
 */
public fun JscConfig.transformConfig(block: TransformConfig.() -> Unit): TransformConfig =
    TransformConfig().apply(block)

/**
 * JscConfig#minify: JsMinifyOptions?
 * extension function for create JsMinifyOptions? -> JsMinifyOptions
 */
public fun JscConfig.jsMinifyOptions(block: JsMinifyOptions.() -> Unit): JsMinifyOptions =
    JsMinifyOptions().apply(block)
