package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.ClassMethod
import dev.yidafu.swc.types.ClassMethodBase
import dev.yidafu.swc.types.ClassMethodImpl
import dev.yidafu.swc.types.FunctionDeclaration
import dev.yidafu.swc.types.FunctionDeclarationImpl
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.MethodProperty
import dev.yidafu.swc.types.MethodPropertyImpl
import dev.yidafu.swc.types.PrivateMethod
import dev.yidafu.swc.types.PrivateMethodImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * subtype of ClassMethodBase
 */
public fun ClassMethodBase.classMethod(block: ClassMethod.() -> Unit): ClassMethod =
    ClassMethodImpl().apply(block)

/**
 * subtype of ClassMethodBase
 */
public fun ClassMethodBase.privateMethod(block: PrivateMethod.() -> Unit): PrivateMethod =
    PrivateMethodImpl().apply(block)

/**
 * ClassMethodBase#function: Fn
 * extension function for create Fn -> FunctionDeclarationImpl
 */
public fun ClassMethodBase.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * ClassMethodBase#function: Fn
 * extension function for create Fn -> FunctionExpressionImpl
 */
public fun ClassMethodBase.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * ClassMethodBase#function: Fn
 * extension function for create Fn -> MethodPropertyImpl
 */
public fun ClassMethodBase.methodProperty(block: MethodProperty.() -> Unit): MethodProperty =
    MethodPropertyImpl().apply(block)

/**
 * ClassMethodBase#type: String
 * extension function for create String -> String
 */
public fun ClassMethodBase.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ClassMethodBase#isOverride: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ClassMethodBase.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * ClassMethodBase#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ClassMethodBase.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
