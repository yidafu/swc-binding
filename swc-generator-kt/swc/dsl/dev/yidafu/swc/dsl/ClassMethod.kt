package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.ClassMethod
import dev.yidafu.swc.types.ComputedPropName
import dev.yidafu.swc.types.ComputedPropNameImpl
import dev.yidafu.swc.types.FunctionDeclaration
import dev.yidafu.swc.types.FunctionDeclarationImpl
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.MethodProperty
import dev.yidafu.swc.types.MethodPropertyImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

/**
 * ClassMethod#accessibility: String
 * extension function for create String -> String
 */
public fun ClassMethod.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ClassMethod#key: PropertyName
 * extension function for create PropertyName -> IdentifierImpl
 */
public fun ClassMethod.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ClassMethod#key: PropertyName
 * extension function for create PropertyName -> StringLiteralImpl
 */
public fun ClassMethod.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ClassMethod#key: PropertyName
 * extension function for create PropertyName -> NumericLiteralImpl
 */
public fun ClassMethod.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * ClassMethod#key: PropertyName
 * extension function for create PropertyName -> ComputedPropNameImpl
 */
public fun ClassMethod.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropNameImpl().apply(block)

/**
 * ClassMethod#key: PropertyName
 * extension function for create PropertyName -> BigIntLiteralImpl
 */
public fun ClassMethod.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * ClassMethod#function: Fn
 * extension function for create Fn -> FunctionDeclarationImpl
 */
public fun ClassMethod.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * ClassMethod#function: Fn
 * extension function for create Fn -> FunctionExpressionImpl
 */
public fun ClassMethod.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression
    = FunctionExpressionImpl().apply(block)

/**
 * ClassMethod#function: Fn
 * extension function for create Fn -> MethodPropertyImpl
 */
public fun ClassMethod.methodProperty(block: MethodProperty.() -> Unit): MethodProperty =
    MethodPropertyImpl().apply(block)

/**
 * ClassMethod#isOverride: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ClassMethod.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * ClassMethod#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ClassMethod.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
