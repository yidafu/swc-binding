package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.ComputedPropName
import dev.yidafu.swc.types.ComputedPropNameImpl
import dev.yidafu.swc.types.GetterProperty
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import kotlin.Unit

/**
 * GetterProperty#type: String
 * extension function for create String -> String
 */
public fun GetterProperty.string(block: String.() -> Unit): String = String().apply(block)

/**
 * GetterProperty#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun GetterProperty.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

/**
 * GetterProperty#body: BlockStatement
 * extension function for create BlockStatement -> BlockStatementImpl
 */
public fun GetterProperty.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * GetterProperty#key: PropertyName
 * extension function for create PropertyName -> IdentifierImpl
 */
public fun GetterProperty.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * GetterProperty#key: PropertyName
 * extension function for create PropertyName -> StringLiteralImpl
 */
public fun GetterProperty.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * GetterProperty#key: PropertyName
 * extension function for create PropertyName -> NumericLiteralImpl
 */
public fun GetterProperty.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * GetterProperty#key: PropertyName
 * extension function for create PropertyName -> ComputedPropNameImpl
 */
public fun GetterProperty.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropNameImpl().apply(block)

/**
 * GetterProperty#key: PropertyName
 * extension function for create PropertyName -> BigIntLiteralImpl
 */
public fun GetterProperty.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * GetterProperty#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun GetterProperty.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
