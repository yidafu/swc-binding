// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.102374

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BlockStatement
import dev.yidafu.swc.generated.BlockStatementImpl
import dev.yidafu.swc.generated.StaticBlock
import kotlin.Unit

/**
 * StaticBlock#body: BlockStatement?
 * extension function for create BlockStatement? -> BlockStatement
 */
public fun StaticBlock.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)
