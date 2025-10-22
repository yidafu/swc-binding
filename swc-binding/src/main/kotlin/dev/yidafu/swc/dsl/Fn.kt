package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Decorator
import dev.yidafu.swc.types.DecoratorImpl
import dev.yidafu.swc.types.Fn
import dev.yidafu.swc.types.FunctionDeclaration
import dev.yidafu.swc.types.FunctionDeclarationImpl
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.MethodProperty
import dev.yidafu.swc.types.MethodPropertyImpl
import dev.yidafu.swc.types.Param
import dev.yidafu.swc.types.ParamImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclarationImpl
import kotlin.Unit

/**
 * subtype of Fn
 */
public fun Fn.functionDeclaration(block: FunctionDeclaration.() -> Unit): FunctionDeclaration =
    FunctionDeclarationImpl().apply(block)

/**
 * subtype of Fn
 */
public fun Fn.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression =
    FunctionExpressionImpl().apply(block)

/**
 * subtype of Fn
 */
public fun Fn.methodProperty(block: MethodProperty.() -> Unit): MethodProperty =
    MethodPropertyImpl().apply(block)

/**
 * Fn#params: Array<Param>
 * extension function for create Array<Param> -> ParamImpl
 */
public fun Fn.`param`(block: Param.() -> Unit): Param = ParamImpl().apply(block)

/**
 * Fn#body: BlockStatement
 * extension function for create BlockStatement -> BlockStatementImpl
 */
public fun Fn.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * Fn#async: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun Fn.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * Fn#typeParameters: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public fun Fn.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * Fn#returnType: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun Fn.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

/**
 * Fn#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun Fn.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * Fn#decorators: Array<Decorator>
 * extension function for create Array<Decorator> -> DecoratorImpl
 */
public fun Fn.decorator(block: Decorator.() -> Unit): Decorator = DecoratorImpl().apply(block)
