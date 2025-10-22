package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BaseNode
import dev.yidafu.swc.types.BaseNodeImpl
import dev.yidafu.swc.types.BaseSpan
import dev.yidafu.swc.types.BaseSpanImpl
import dev.yidafu.swc.types.ExprBase
import dev.yidafu.swc.types.ExprBaseImpl
import dev.yidafu.swc.types.StmtBase
import dev.yidafu.swc.types.StmtBaseImpl
import kotlin.Unit

public fun createBaseNode(block: BaseNode.() -> Unit): BaseNode = BaseNodeImpl().apply(block)

public fun createBaseSpan(block: BaseSpan.() -> Unit): BaseSpan = BaseSpanImpl().apply(block)

public fun createExprBase(block: ExprBase.() -> Unit): ExprBase = ExprBaseImpl().apply(block)

public fun createStmtBase(block: StmtBase.() -> Unit): StmtBase = StmtBaseImpl().apply(block)
