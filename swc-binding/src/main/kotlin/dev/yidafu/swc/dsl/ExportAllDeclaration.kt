package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ExportAllDeclaration
import dev.yidafu.swc.types.ObjectExpression
import dev.yidafu.swc.types.ObjectExpressionImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

/**
 * ExportAllDeclaration#type: String
 * extension function for create String -> String
 */
public fun ExportAllDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ExportAllDeclaration#source: StringLiteral
 * extension function for create StringLiteral -> StringLiteralImpl
 */
public fun ExportAllDeclaration.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ExportAllDeclaration#asserts: ObjectExpression
 * extension function for create ObjectExpression -> ObjectExpressionImpl
 */
public fun ExportAllDeclaration.objectExpression(block: ObjectExpression.() -> Unit):
    ObjectExpression = ObjectExpressionImpl().apply(block)

/**
 * ExportAllDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ExportAllDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
