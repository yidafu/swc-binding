package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Int
import dev.yidafu.swc.types.Span
import kotlin.Unit

/**
 * Span#ctxt: Int
 * extension function for create Int -> Int
 */
public fun Span.int(block: Int.() -> Unit): Int = Int().apply(block)
