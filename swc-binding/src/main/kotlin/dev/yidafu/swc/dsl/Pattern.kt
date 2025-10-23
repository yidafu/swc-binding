package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.Pattern
import kotlin.Unit

/**
 * subtype of Pattern
 */
public fun Pattern.identifier(block: Pattern.() -> Unit): Identifier {
}

/**
 * subtype of Pattern
 */
public fun Pattern.arrayPattern(block: Pattern.() -> Unit): ArrayPattern {
}
