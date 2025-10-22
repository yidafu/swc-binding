package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BaseSpan
import dev.yidafu.swc.types.ExprBase
import dev.yidafu.swc.types.ExprBaseImpl
import dev.yidafu.swc.types.StmtBase
import dev.yidafu.swc.types.StmtBaseImpl
import dev.yidafu.swc.types.TypeLiteral
import kotlin.Unit

/**
 * subtype of BaseSpan
 */
public fun BaseSpan.exprBase(block: ExprBase.() -> Unit): ExprBase = ExprBaseImpl().apply(block)

/**
 * subtype of BaseSpan
 */
public fun BaseSpan.stmtBase(block: StmtBase.() -> Unit): StmtBase = StmtBaseImpl().apply(block)

/**
 * BaseSpan#span: TypeLiteral
 * extension function for create TypeLiteral -> TypeLiteral
 */
public fun BaseSpan.typeLiteral(block: TypeLiteral.() -> Unit): TypeLiteral =
    TypeLiteral().apply(block)
