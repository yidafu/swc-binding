package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.TsEntityName
import dev.yidafu.swc.types.TsQualifiedName
import dev.yidafu.swc.types.TsQualifiedNameImpl
import kotlin.Unit

/**
 * subtype of TsEntityName
 */
public fun TsEntityName.tsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedNameImpl().apply(block)

/**
 * subtype of TsEntityName
 */
public fun TsEntityName.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
