package dev.yidafu.swc.dsl

import Boolean
import Int
import dev.yidafu.swc.types.OptionalFields
import kotlin.String
import kotlin.Unit

/**
 * OptionalFields#required: String
 * extension function for create String -> String
 */
public fun OptionalFields.string(block: OptionalFields.() -> Unit): String {
}

/**
 * OptionalFields#optional: Int
 * extension function for create Int -> Int
 */
public fun OptionalFields.int(block: OptionalFields.() -> Unit): Int {
}

/**
 * OptionalFields#readonlyField: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun OptionalFields.boolean(block: OptionalFields.() -> Unit): Boolean {
}
