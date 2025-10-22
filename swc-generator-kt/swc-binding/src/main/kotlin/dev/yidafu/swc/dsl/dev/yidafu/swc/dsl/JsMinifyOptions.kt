package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.JsFormatOptions
import dev.yidafu.swc.types.JsFormatOptionsImpl
import dev.yidafu.swc.types.JsMinifyOptions
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TerserEcmaVersion
import kotlin.Unit

/**
 * JsMinifyOptions#format: JsFormatOptions
 * extension function for create JsFormatOptions -> JsFormatOptionsImpl
 */
public fun JsMinifyOptions.jsFormatOptions(block: JsFormatOptions.() -> Unit): JsFormatOptions =
    JsFormatOptionsImpl().apply(block)

/**
 * JsMinifyOptions#ecma: TerserEcmaVersion
 * extension function for create TerserEcmaVersion -> TerserEcmaVersion
 */
public fun JsMinifyOptions.terserEcmaVersion(block: TerserEcmaVersion.() -> Unit): TerserEcmaVersion
    = TerserEcmaVersion().apply(block)

/**
 * JsMinifyOptions#inlineSourcesContent: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun JsMinifyOptions.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * JsMinifyOptions#outputPath: String
 * extension function for create String -> String
 */
public fun JsMinifyOptions.string(block: String.() -> Unit): String = String().apply(block)
