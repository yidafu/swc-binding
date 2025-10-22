package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.VariableDeclaration
import dev.yidafu.swc.types.VariableDeclarator
import dev.yidafu.swc.types.VariableDeclaratorImpl
import kotlin.Unit

/**
 * VariableDeclaration#kind: String
 * extension function for create String -> String
 */
public fun VariableDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * VariableDeclaration#declare: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun VariableDeclaration.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * VariableDeclaration#declarations: Array<VariableDeclarator>
 * extension function for create Array<VariableDeclarator> -> VariableDeclaratorImpl
 */
public fun VariableDeclaration.variableDeclarator(block: VariableDeclarator.() -> Unit):
    VariableDeclarator = VariableDeclaratorImpl().apply(block)

/**
 * VariableDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun VariableDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
