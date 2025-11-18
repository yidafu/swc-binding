// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.147375

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.TsThisType
import dev.yidafu.swc.generated.TsTypeAnnotation
import dev.yidafu.swc.generated.TsTypePredicate
import kotlin.Unit

/**
 * TsTypePredicate#paramName: TsThisTypeOrIdent?
 * extension function for create TsThisTypeOrIdent? -> Identifier
 */
public fun TsTypePredicate.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsTypePredicate#paramName: TsThisTypeOrIdent?
 * extension function for create TsThisTypeOrIdent? -> TsThisType
 */
public fun TsTypePredicate.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisType().apply(block)

/**
 * TsTypePredicate#typeAnnotation: TsTypeAnnotation?
 * extension function for create TsTypeAnnotation? -> TsTypeAnnotation
 */
public fun TsTypePredicate.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotation().apply(block)
