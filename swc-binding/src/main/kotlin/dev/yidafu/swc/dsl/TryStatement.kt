package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.CatchClause
import dev.yidafu.swc.types.CatchClauseImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TryStatement
import kotlin.Unit

/**
 * TryStatement#type: String
 * extension function for create String -> String
 */
public fun TryStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TryStatement#finalizer: BlockStatement
 * extension function for create BlockStatement -> BlockStatementImpl
 */
public fun TryStatement.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * TryStatement#handler: CatchClause
 * extension function for create CatchClause -> CatchClauseImpl
 */
public fun TryStatement.catchClause(block: CatchClause.() -> Unit): CatchClause =
    CatchClauseImpl().apply(block)

/**
 * TryStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TryStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
