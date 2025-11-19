// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.312462

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.TsImportType
import dev.yidafu.swc.generated.TsQualifiedName
import dev.yidafu.swc.generated.TsTypeParameterInstantiation
import dev.yidafu.swc.generated.TsTypeQuery
import kotlin.Unit

/**
 * TsTypeQuery#exprName: TsTypeQueryExpr?
 * extension function for create TsTypeQueryExpr? -> TsImportType
 */
public fun TsTypeQuery.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportType().apply(block)

/**
 * TsTypeQuery#exprName: TsTypeQueryExpr?
 * extension function for create TsTypeQueryExpr? -> TsQualifiedName
 */
public fun TsTypeQuery.tsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedName().apply(block)

/**
 * TsTypeQuery#exprName: TsTypeQueryExpr?
 * extension function for create TsTypeQueryExpr? -> Identifier
 */
public fun TsTypeQuery.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsTypeQuery#typeArguments: TsTypeParameterInstantiation?
 * extension function for create TsTypeParameterInstantiation? -> TsTypeParameterInstantiation
 */
public fun TsTypeQuery.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiation().apply(block)
