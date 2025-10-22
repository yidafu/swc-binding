package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.FunctionDeclaration
import dev.yidafu.swc.types.FunctionDeclarationImpl
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.MethodProperty
import dev.yidafu.swc.types.MethodPropertyImpl
import dev.yidafu.swc.types.PrivateMethod
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * PrivateMethod#accessibility: String
 * extension function for create String -> String
 */
public fun PrivateMethod.string(block: String.() -> Unit): String = String().apply(block)

/**
 * PrivateMethod#key: PrivateName
 * extension function for create PrivateName -> PrivateNameImpl
 */
public fun PrivateMethod.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * PrivateMethod#function: Fn
 * extension function for create Fn -> FunctionDeclarationImpl
 */
public fun PrivateMethod.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * PrivateMethod#function: Fn
 * extension function for create Fn -> FunctionExpressionImpl
 */
public fun PrivateMethod.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * PrivateMethod#function: Fn
 * extension function for create Fn -> MethodPropertyImpl
 */
public fun PrivateMethod.methodProperty(block: MethodProperty.() -> Unit): MethodProperty =
    MethodPropertyImpl().apply(block)

/**
 * PrivateMethod#isOverride: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun PrivateMethod.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * PrivateMethod#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun PrivateMethod.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
