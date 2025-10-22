package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.TsThisType
import dev.yidafu.swc.types.TsThisTypeImpl
import dev.yidafu.swc.types.TsThisTypeOrIdent
import kotlin.Unit

/**
 * subtype of TsThisTypeOrIdent
 */
public fun TsThisTypeOrIdent.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * subtype of TsThisTypeOrIdent
 */
public fun TsThisTypeOrIdent.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
