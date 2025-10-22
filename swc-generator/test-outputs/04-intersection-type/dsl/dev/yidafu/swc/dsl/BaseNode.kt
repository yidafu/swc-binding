package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BaseNode
import dev.yidafu.swc.types.ExprBase
import dev.yidafu.swc.types.ExprBaseImpl
import dev.yidafu.swc.types.StmtBase
import dev.yidafu.swc.types.StmtBaseImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * subtype of BaseNode
 */
public fun BaseNode.exprBase(block: ExprBase.() -> Unit): ExprBase = ExprBaseImpl().apply(block)

/**
 * subtype of BaseNode
 */
public fun BaseNode.stmtBase(block: StmtBase.() -> Unit): StmtBase = StmtBaseImpl().apply(block)

/**
 * BaseNode#type: String
 * extension function for create String -> String
 */
public fun BaseNode.string(block: String.() -> Unit): String = String().apply(block)
