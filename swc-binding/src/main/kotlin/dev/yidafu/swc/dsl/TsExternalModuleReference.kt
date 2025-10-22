package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.TsExternalModuleReference
import kotlin.Unit

/**
 * TsExternalModuleReference#type: String
 * extension function for create String -> String
 */
public fun TsExternalModuleReference.string(block: String.() -> Unit): String =
    String().apply(block)

/**
 * TsExternalModuleReference#expression: StringLiteral
 * extension function for create StringLiteral -> StringLiteralImpl
 */
public fun TsExternalModuleReference.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TsExternalModuleReference#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsExternalModuleReference.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
