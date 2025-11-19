// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:22.959701

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
