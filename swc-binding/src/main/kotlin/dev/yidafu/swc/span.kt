package dev.yidafu.swc

import dev.yidafu.swc.generated.Span


fun span(
    start: Int = 0,
    end: Int = 0,
    ctxt: Int = 0,
    block: Span.() -> Unit = {}
): Span = Span().apply {
    this.start = start
    this.end = end
    this.ctxt = ctxt
    block()
}

fun emptySpan(): Span = span(start = 0, end = 0, ctxt = 0)
