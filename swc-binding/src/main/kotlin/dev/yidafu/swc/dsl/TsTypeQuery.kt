package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsImportType
import dev.yidafu.swc.types.TsImportTypeImpl
import dev.yidafu.swc.types.TsQualifiedName
import dev.yidafu.swc.types.TsQualifiedNameImpl
import dev.yidafu.swc.types.TsTypeParameterInstantiation
import dev.yidafu.swc.types.TsTypeParameterInstantiationImpl
import dev.yidafu.swc.types.TsTypeQuery
import kotlin.Unit

/**
 * TsTypeQuery#type: String
 * extension function for create String -> String
 */
public fun TsTypeQuery.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsTypeQuery#exprName: TsTypeQueryExpr
 * extension function for create TsTypeQueryExpr -> TsQualifiedNameImpl
 */
public fun TsTypeQuery.tsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedNameImpl().apply(block)

/**
 * TsTypeQuery#exprName: TsTypeQueryExpr
 * extension function for create TsTypeQueryExpr -> IdentifierImpl
 */
public fun TsTypeQuery.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsTypeQuery#exprName: TsTypeQueryExpr
 * extension function for create TsTypeQueryExpr -> TsImportTypeImpl
 */
public fun TsTypeQuery.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsTypeQuery#typeArguments: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
public fun TsTypeQuery.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * TsTypeQuery#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsTypeQuery.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
