package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

fun JSXClosingFragment.span(block: Span.() -> Unit): Span {
    return Span().apply(block)
}