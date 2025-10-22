package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.TsLiteral
import dev.yidafu.swc.types.TsTemplateLiteralType
import dev.yidafu.swc.types.TsTemplateLiteralTypeImpl
import kotlin.Unit

/**
 * subtype of TsLiteral
 */
public fun TsLiteral.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * subtype of TsLiteral
 */
public fun TsLiteral.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * subtype of TsLiteral
 */
public fun TsLiteral.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * subtype of TsLiteral
 */
public fun TsLiteral.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * subtype of TsLiteral
 */
public fun TsLiteral.tsTemplateLiteralType(block: TsTemplateLiteralType.() -> Unit):
    TsTemplateLiteralType = TsTemplateLiteralTypeImpl().apply(block)
