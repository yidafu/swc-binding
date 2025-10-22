package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.JsFormatOptions
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TerserEcmaVersion
import kotlin.Unit

/**
 * JsFormatOptions#wrapFuncArgs: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun JsFormatOptions.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * JsFormatOptions#ecma: TerserEcmaVersion
 * extension function for create TerserEcmaVersion -> TerserEcmaVersion
 */
public fun JsFormatOptions.terserEcmaVersion(block: TerserEcmaVersion.() -> Unit): TerserEcmaVersion
    = TerserEcmaVersion().apply(block)

/**
 * JsFormatOptions#maxLineLen: Union.U2<Int, Boolean>
 * extension function for create Union.U2<Int, Boolean> -> Int
 */
public fun JsFormatOptions.int(block: Int.() -> Unit): Int = Int().apply(block)

/**
 * JsFormatOptions#preamble: String
 * extension function for create String -> String
 */
public fun JsFormatOptions.string(block: String.() -> Unit): String = String().apply(block)
