package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import kotlin.Unit

public fun createSpan(block: Span.() -> Unit): Span = SpanImpl().apply(block)
