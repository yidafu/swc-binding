// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.088173

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BlockStatement
import dev.yidafu.swc.generated.CatchClause
import dev.yidafu.swc.generated.TryStatement
import kotlin.Unit

/**
 * TryStatement#finalizer: BlockStatement?
 * extension function for create BlockStatement? -> BlockStatement
 */
public fun TryStatement.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatement().apply(block)

/**
 * TryStatement#handler: CatchClause?
 * extension function for create CatchClause? -> CatchClause
 */
public fun TryStatement.catchClause(block: CatchClause.() -> Unit): CatchClause =
    CatchClause().apply(block)
