// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.266902

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BlockStatement
import dev.yidafu.swc.generated.BlockStatementImpl
import dev.yidafu.swc.generated.CatchClause
import dev.yidafu.swc.generated.TryStatement
import kotlin.Unit

/**
 * TryStatement#finalizer: BlockStatement?
 * extension function for create BlockStatement? -> BlockStatement
 */
public fun TryStatement.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * TryStatement#handler: CatchClause?
 * extension function for create CatchClause? -> CatchClause
 */
public fun TryStatement.catchClause(block: CatchClause.() -> Unit): CatchClause =
    CatchClause().apply(block)
