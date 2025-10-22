package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ExprBase
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TypeLiteral
import kotlin.Unit

/**
 * ExprBase#type: String
 * extension function for create String -> String
 */
public fun ExprBase.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ExprBase#span: TypeLiteral
 * extension function for create TypeLiteral -> TypeLiteral
 */
public fun ExprBase.typeLiteral(block: TypeLiteral.() -> Unit): TypeLiteral =
    TypeLiteral().apply(block)
