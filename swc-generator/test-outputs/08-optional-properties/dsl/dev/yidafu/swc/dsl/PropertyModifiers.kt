package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.PropertyModifiers
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * PropertyModifiers#optionalReadonly: String
 * extension function for create String -> String
 */
public fun PropertyModifiers.string(block: String.() -> Unit): String = String().apply(block)

/**
 * PropertyModifiers#optional: Int
 * extension function for create Int -> Int
 */
public fun PropertyModifiers.int(block: Int.() -> Unit): Int = Int().apply(block)

/**
 * PropertyModifiers#readonlyField: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun PropertyModifiers.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
