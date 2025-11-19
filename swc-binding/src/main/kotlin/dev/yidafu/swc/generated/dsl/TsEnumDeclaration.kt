// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.352991

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.TsEnumDeclaration
import dev.yidafu.swc.generated.TsEnumMember
import kotlin.Unit

/**
 * TsEnumDeclaration#id: Identifier?
 * extension function for create Identifier? -> Identifier
 */
public fun TsEnumDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsEnumDeclaration#members: Array<TsEnumMember>?
 * extension function for create Array<TsEnumMember>? -> TsEnumMember
 */
public fun TsEnumDeclaration.tsEnumMember(block: TsEnumMember.() -> Unit): TsEnumMember =
    TsEnumMember().apply(block)
