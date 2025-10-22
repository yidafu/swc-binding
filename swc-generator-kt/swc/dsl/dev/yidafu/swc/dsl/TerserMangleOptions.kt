package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TerserMangleOptions
import dev.yidafu.swc.types.TerserManglePropertiesOptions
import dev.yidafu.swc.types.TerserManglePropertiesOptionsImpl
import kotlin.Unit

/**
 * TerserMangleOptions#props: TerserManglePropertiesOptions
 * extension function for create TerserManglePropertiesOptions -> TerserManglePropertiesOptionsImpl
 */
public
    fun TerserMangleOptions.terserManglePropertiesOptions(block: TerserManglePropertiesOptions.() -> Unit):
    TerserManglePropertiesOptions = TerserManglePropertiesOptionsImpl().apply(block)

/**
 * TerserMangleOptions#safari10: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TerserMangleOptions.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TerserMangleOptions#reserved: Array<String>
 * extension function for create Array<String> -> String
 */
public fun TerserMangleOptions.string(block: String.() -> Unit): String = String().apply(block)
