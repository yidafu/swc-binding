package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.EsParserConfig
import dev.yidafu.swc.types.EsParserConfigImpl
import dev.yidafu.swc.types.JsMinifyOptions
import dev.yidafu.swc.types.JsMinifyOptionsImpl
import dev.yidafu.swc.types.JscConfig
import dev.yidafu.swc.types.ParseOptions
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TransformConfig
import dev.yidafu.swc.types.TransformConfigImpl
import dev.yidafu.swc.types.TsParserConfig
import dev.yidafu.swc.types.TsParserConfigImpl
import dev.yidafu.swc.types.TypeLiteral
import kotlin.Unit

/**
 * JscConfig#preserveAllComments: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun JscConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * JscConfig#parser: ParserConfig
 * extension function for create ParserConfig -> ParseOptions
 */
public fun JscConfig.parseOptions(block: ParseOptions.() -> Unit): ParseOptions =
    ParseOptions().apply(block)

/**
 * JscConfig#parser: ParserConfig
 * extension function for create ParserConfig -> TsParserConfigImpl
 */
public fun JscConfig.tsParserConfig(block: TsParserConfig.() -> Unit): TsParserConfig =
    TsParserConfigImpl().apply(block)

/**
 * JscConfig#parser: ParserConfig
 * extension function for create ParserConfig -> EsParserConfigImpl
 */
public fun JscConfig.esParserConfig(block: EsParserConfig.() -> Unit): EsParserConfig =
    EsParserConfigImpl().apply(block)

/**
 * JscConfig#transform: TransformConfig
 * extension function for create TransformConfig -> TransformConfigImpl
 */
public fun JscConfig.transformConfig(block: TransformConfig.() -> Unit): TransformConfig =
    TransformConfigImpl().apply(block)

/**
 * JscConfig#baseUrl: String
 * extension function for create String -> String
 */
public fun JscConfig.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JscConfig#experimental: TypeLiteral
 * extension function for create TypeLiteral -> TypeLiteral
 */
public fun JscConfig.typeLiteral(block: TypeLiteral.() -> Unit): TypeLiteral =
    TypeLiteral().apply(block)

/**
 * JscConfig#minify: JsMinifyOptions
 * extension function for create JsMinifyOptions -> JsMinifyOptionsImpl
 */
public fun JscConfig.jsMinifyOptions(block: JsMinifyOptions.() -> Unit): JsMinifyOptions =
    JsMinifyOptionsImpl().apply(block)
