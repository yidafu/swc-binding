package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.StaticBlock
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * StaticBlock#type: String
 * extension function for create String -> String
 */
public fun StaticBlock.string(block: String.() -> Unit): String = String().apply(block)

/**
 * StaticBlock#body: BlockStatement
 * extension function for create BlockStatement -> BlockStatementImpl
 */
public fun StaticBlock.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * StaticBlock#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun StaticBlock.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
