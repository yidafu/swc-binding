package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Decorator
import dev.yidafu.swc.types.DecoratorImpl
import dev.yidafu.swc.types.FunctionDeclaration
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
 * FunctionDeclaration#type: String
 * extension function for create String -> String
 */
public fun FunctionDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * FunctionDeclaration#identifier: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun FunctionDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * FunctionDeclaration#async: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun FunctionDeclaration.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * FunctionDeclaration#params: Array<Param>
 * extension function for create Array<Param> -> ParamImpl
 */
public fun FunctionDeclaration.`param`(block: Param.() -> Unit): Param = ParamImpl().apply(block)

/**
 * FunctionDeclaration#body: BlockStatement
 * extension function for create BlockStatement -> BlockStatementImpl
 */
public fun FunctionDeclaration.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * FunctionDeclaration#typeParameters: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public
    fun FunctionDeclaration.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * FunctionDeclaration#returnType: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun FunctionDeclaration.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit):
    TsTypeAnnotation = TsTypeAnnotationImpl().apply(block)

/**
 * FunctionDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun FunctionDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * FunctionDeclaration#decorators: Array<Decorator>
 * extension function for create Array<Decorator> -> DecoratorImpl
 */
public fun FunctionDeclaration.decorator(block: Decorator.() -> Unit): Decorator =
    DecoratorImpl().apply(block)
