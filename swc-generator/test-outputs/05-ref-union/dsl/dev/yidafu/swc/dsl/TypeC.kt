package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TypeC
import kotlin.Unit

/**
 * TypeC#kind: String
 * extension function for create String -> String
 */
public fun TypeC.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TypeC#valueC: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TypeC.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
