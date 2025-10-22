package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.TsImportType
import dev.yidafu.swc.types.TsQualifiedName
import dev.yidafu.swc.types.TsQualifiedNameImpl
import dev.yidafu.swc.types.TsTypeParameterInstantiation
import dev.yidafu.swc.types.TsTypeParameterInstantiationImpl
import kotlin.Unit

/**
 * TsImportType#type: String
 * extension function for create String -> String
 */
public fun TsImportType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsImportType#argument: StringLiteral
 * extension function for create StringLiteral -> StringLiteralImpl
 */
public fun TsImportType.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TsImportType#qualifier: TsEntityName
 * extension function for create TsEntityName -> TsQualifiedNameImpl
 */
public fun TsImportType.tsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedNameImpl().apply(block)

/**
 * TsImportType#qualifier: TsEntityName
 * extension function for create TsEntityName -> IdentifierImpl
 */
public fun TsImportType.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsImportType#typeArguments: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
public
    fun TsImportType.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * TsImportType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsImportType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
