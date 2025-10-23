package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.StringLiteral
import kotlin.String
import kotlin.Unit

/**
 * StringLiteral#raw: String
 * extension function for create String -> String
 */
public fun StringLiteral.string(block: StringLiteral.() -> Unit): String {
}

/**
 * StringLiteral#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun StringLiteral.span(block: StringLiteral.() -> Unit): Span {
}
