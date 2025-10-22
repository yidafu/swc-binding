package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsEnumDeclaration
import dev.yidafu.swc.types.TsEnumMember
import dev.yidafu.swc.types.TsEnumMemberImpl
import kotlin.Unit

/**
 * TsEnumDeclaration#type: String
 * extension function for create String -> String
 */
public fun TsEnumDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsEnumDeclaration#isConst: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsEnumDeclaration.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsEnumDeclaration#id: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun TsEnumDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsEnumDeclaration#members: Array<TsEnumMember>
 * extension function for create Array<TsEnumMember> -> TsEnumMemberImpl
 */
public fun TsEnumDeclaration.tsEnumMember(block: TsEnumMember.() -> Unit): TsEnumMember =
    TsEnumMemberImpl().apply(block)

/**
 * TsEnumDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsEnumDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
