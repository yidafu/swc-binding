// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.352374

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BigIntLiteral
import dev.yidafu.swc.generated.BooleanLiteral
import dev.yidafu.swc.generated.NumericLiteral
import dev.yidafu.swc.generated.StringLiteral
import dev.yidafu.swc.generated.TsLiteralType
import dev.yidafu.swc.generated.TsTemplateLiteralType
import dev.yidafu.swc.generated.TsTemplateLiteralTypeImpl
import kotlin.Unit

/**
 * TsLiteralType#literal: TsLiteral?
 * extension function for create TsLiteral? -> StringLiteral
 */
public fun TsLiteralType.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * TsLiteralType#literal: TsLiteral?
 * extension function for create TsLiteral? -> BooleanLiteral
 */
public fun TsLiteralType.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteral().apply(block)

/**
 * TsLiteralType#literal: TsLiteral?
 * extension function for create TsLiteral? -> NumericLiteral
 */
public fun TsLiteralType.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * TsLiteralType#literal: TsLiteral?
 * extension function for create TsLiteral? -> BigIntLiteral
 */
public fun TsLiteralType.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)

/**
 * TsLiteralType#literal: TsLiteral?
 * extension function for create TsLiteral? -> TsTemplateLiteralType
 */
public fun TsLiteralType.tsTemplateLiteralType(block: TsTemplateLiteralType.() -> Unit):
    TsTemplateLiteralType = TsTemplateLiteralTypeImpl().apply(block)
