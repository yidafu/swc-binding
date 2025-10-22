package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TerserCompressOptions
import dev.yidafu.swc.types.TerserEcmaVersion
import kotlin.Unit

/**
 * TerserCompressOptions#module: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TerserCompressOptions.boolean(block: Boolean.() -> Unit): Boolean =
    Boolean().apply(block)

/**
 * TerserCompressOptions#ecma: TerserEcmaVersion
 * extension function for create TerserEcmaVersion -> TerserEcmaVersion
 */
public fun TerserCompressOptions.terserEcmaVersion(block: TerserEcmaVersion.() -> Unit):
    TerserEcmaVersion = TerserEcmaVersion().apply(block)

/**
 * TerserCompressOptions#passes: Int
 * extension function for create Int -> Int
 */
public fun TerserCompressOptions.int(block: Int.() -> Unit): Int = Int().apply(block)

/**
 * TerserCompressOptions#pure_funcs: Array<String>
 * extension function for create Array<String> -> String
 */
public fun TerserCompressOptions.string(block: String.() -> Unit): String = String().apply(block)
