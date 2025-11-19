// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.309097

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.TsQualifiedName
import dev.yidafu.swc.generated.TsTypeParameterInstantiation
import dev.yidafu.swc.generated.TsTypeReference
import kotlin.Unit

/**
 * TsTypeReference#typeName: TsEntityName?
 * extension function for create TsEntityName? -> TsQualifiedName
 */
public fun TsTypeReference.tsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedName().apply(block)

/**
 * TsTypeReference#typeName: TsEntityName?
 * extension function for create TsEntityName? -> Identifier
 */
public fun TsTypeReference.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsTypeReference#typeParams: TsTypeParameterInstantiation?
 * extension function for create TsTypeParameterInstantiation? -> TsTypeParameterInstantiation
 */
public
    fun TsTypeReference.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiation().apply(block)
