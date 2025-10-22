package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.TsLiteralType
import dev.yidafu.swc.types.TsTemplateLiteralType
import dev.yidafu.swc.types.TsTemplateLiteralTypeImpl
import kotlin.Unit

/**
 * TsLiteralType#type: String
 * extension function for create String -> String
 */
public fun TsLiteralType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsLiteralType#literal: TsLiteral
 * extension function for create TsLiteral -> NumericLiteralImpl
 */
public fun TsLiteralType.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * TsLiteralType#literal: TsLiteral
 * extension function for create TsLiteral -> StringLiteralImpl
 */
public fun TsLiteralType.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TsLiteralType#literal: TsLiteral
 * extension function for create TsLiteral -> BooleanLiteralImpl
 */
public fun TsLiteralType.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * TsLiteralType#literal: TsLiteral
 * extension function for create TsLiteral -> BigIntLiteralImpl
 */
public fun TsLiteralType.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * TsLiteralType#literal: TsLiteral
 * extension function for create TsLiteral -> TsTemplateLiteralTypeImpl
 */
public fun TsLiteralType.tsTemplateLiteralType(block: TsTemplateLiteralType.() -> Unit):
    TsTemplateLiteralType = TsTemplateLiteralTypeImpl().apply(block)

/**
 * TsLiteralType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsLiteralType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
