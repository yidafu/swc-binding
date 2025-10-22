package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.ComputedPropName
import dev.yidafu.swc.types.ComputedPropNameImpl
import dev.yidafu.swc.types.Decorator
import dev.yidafu.swc.types.DecoratorImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.MethodProperty
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.Param
import dev.yidafu.swc.types.ParamImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclarationImpl
import kotlin.Unit

/**
 * MethodProperty#type: String
 * extension function for create String -> String
 */
public fun MethodProperty.string(block: String.() -> Unit): String = String().apply(block)

/**
 * MethodProperty#key: PropertyName
 * extension function for create PropertyName -> IdentifierImpl
 */
public fun MethodProperty.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * MethodProperty#key: PropertyName
 * extension function for create PropertyName -> StringLiteralImpl
 */
public fun MethodProperty.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * MethodProperty#key: PropertyName
 * extension function for create PropertyName -> NumericLiteralImpl
 */
public fun MethodProperty.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * MethodProperty#key: PropertyName
 * extension function for create PropertyName -> ComputedPropNameImpl
 */
public fun MethodProperty.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropNameImpl().apply(block)

/**
 * MethodProperty#key: PropertyName
 * extension function for create PropertyName -> BigIntLiteralImpl
 */
public fun MethodProperty.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * MethodProperty#params: Array<Param>
 * extension function for create Array<Param> -> ParamImpl
 */
public fun MethodProperty.`param`(block: Param.() -> Unit): Param = ParamImpl().apply(block)

/**
 * MethodProperty#body: BlockStatement
 * extension function for create BlockStatement -> BlockStatementImpl
 */
public fun MethodProperty.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * MethodProperty#async: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun MethodProperty.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * MethodProperty#typeParameters: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public fun MethodProperty.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * MethodProperty#returnType: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun MethodProperty.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

/**
 * MethodProperty#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun MethodProperty.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * MethodProperty#decorators: Array<Decorator>
 * extension function for create Array<Decorator> -> DecoratorImpl
 */
public fun MethodProperty.decorator(block: Decorator.() -> Unit): Decorator =
    DecoratorImpl().apply(block)
