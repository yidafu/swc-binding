package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.StmtBase
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TypeLiteral
import kotlin.Unit

/**
 * StmtBase#stmtField: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun StmtBase.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * StmtBase#type: String
 * extension function for create String -> String
 */
public fun StmtBase.string(block: String.() -> Unit): String = String().apply(block)

/**
 * StmtBase#span: TypeLiteral
 * extension function for create TypeLiteral -> TypeLiteral
 */
public fun StmtBase.typeLiteral(block: TypeLiteral.() -> Unit): TypeLiteral =
    TypeLiteral().apply(block)
