package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.ComputedPropName
import dev.yidafu.swc.types.ComputedPropNameImpl
import dev.yidafu.swc.types.Constructor
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.Param
import dev.yidafu.swc.types.ParamImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.TsParameterProperty
import dev.yidafu.swc.types.TsParameterPropertyImpl
import kotlin.Unit

/**
 * Constructor#accessibility: String
 * extension function for create String -> String
 */
public fun Constructor.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Constructor#key: PropertyName
 * extension function for create PropertyName -> IdentifierImpl
 */
public fun Constructor.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * Constructor#key: PropertyName
 * extension function for create PropertyName -> StringLiteralImpl
 */
public fun Constructor.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * Constructor#key: PropertyName
 * extension function for create PropertyName -> NumericLiteralImpl
 */
public fun Constructor.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * Constructor#key: PropertyName
 * extension function for create PropertyName -> ComputedPropNameImpl
 */
public fun Constructor.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropNameImpl().apply(block)

/**
 * Constructor#key: PropertyName
 * extension function for create PropertyName -> BigIntLiteralImpl
 */
public fun Constructor.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * Constructor#params: Array<Union.U2<TsParameterProperty, Param>>
 * extension function for create Array<Union.U2<TsParameterProperty, Param>> ->
 * TsParameterPropertyImpl
 */
public fun Constructor.tsParameterProperty(block: TsParameterProperty.() -> Unit):
    TsParameterProperty = TsParameterPropertyImpl().apply(block)

/**
 * Constructor#params: Array<Union.U2<TsParameterProperty, Param>>
 * extension function for create Array<Union.U2<TsParameterProperty, Param>> -> ParamImpl
 */
public fun Constructor.`param`(block: Param.() -> Unit): Param = ParamImpl().apply(block)

/**
 * Constructor#body: BlockStatement
 * extension function for create BlockStatement -> BlockStatementImpl
 */
public fun Constructor.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * Constructor#isOptional: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun Constructor.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * Constructor#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun Constructor.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
