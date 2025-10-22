package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Decorator
import dev.yidafu.swc.types.DecoratorImpl
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Param
import dev.yidafu.swc.types.ParamImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclarationImpl
import kotlin.Unit

/**
 * FunctionExpression#type: String
 * extension function for create String -> String
 */
public fun FunctionExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * FunctionExpression#identifier: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun FunctionExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * FunctionExpression#params: Array<Param>
 * extension function for create Array<Param> -> ParamImpl
 */
public fun FunctionExpression.`param`(block: Param.() -> Unit): Param = ParamImpl().apply(block)

/**
 * FunctionExpression#body: BlockStatement
 * extension function for create BlockStatement -> BlockStatementImpl
 */
public fun FunctionExpression.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * FunctionExpression#async: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun FunctionExpression.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * FunctionExpression#typeParameters: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public
    fun FunctionExpression.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * FunctionExpression#returnType: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun FunctionExpression.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation
    = TsTypeAnnotationImpl().apply(block)

/**
 * FunctionExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun FunctionExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * FunctionExpression#decorators: Array<Decorator>
 * extension function for create Array<Decorator> -> DecoratorImpl
 */
public fun FunctionExpression.decorator(block: Decorator.() -> Unit): Decorator =
    DecoratorImpl().apply(block)
