package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.TsImportType
import dev.yidafu.swc.types.TsImportTypeImpl
import dev.yidafu.swc.types.TsQualifiedName
import dev.yidafu.swc.types.TsQualifiedNameImpl
import dev.yidafu.swc.types.TsTypeQueryExpr
import kotlin.Unit

/**
 * subtype of TsTypeQueryExpr
 */
public fun TsTypeQueryExpr.tsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedNameImpl().apply(block)

/**
 * subtype of TsTypeQueryExpr
 */
public fun TsTypeQueryExpr.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * subtype of TsTypeQueryExpr
 */
public fun TsTypeQueryExpr.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)
