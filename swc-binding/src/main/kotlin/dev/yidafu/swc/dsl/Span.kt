package dev.yidafu.swc.dsl

import Int
import dev.yidafu.swc.types.Span
import kotlin.Unit

/**
 * Span#ctxt: Int
 * extension function for create Int -> Int
 */
public fun Span.int(block: Span.() -> Unit): Int {
}
