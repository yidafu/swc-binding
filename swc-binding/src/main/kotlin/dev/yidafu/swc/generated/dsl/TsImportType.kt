// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.148825

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.StringLiteral
import dev.yidafu.swc.generated.TsImportType
import dev.yidafu.swc.generated.TsQualifiedName
import dev.yidafu.swc.generated.TsTypeParameterInstantiation
import kotlin.Unit

/**
 * TsImportType#argument: StringLiteral?
 * extension function for create StringLiteral? -> StringLiteral
 */
public fun TsImportType.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * TsImportType#qualifier: TsEntityName?
 * extension function for create TsEntityName? -> Identifier
 */
public fun TsImportType.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsImportType#qualifier: TsEntityName?
 * extension function for create TsEntityName? -> TsQualifiedName
 */
public fun TsImportType.tsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedName().apply(block)

/**
 * TsImportType#typeArguments: TsTypeParameterInstantiation?
 * extension function for create TsTypeParameterInstantiation? -> TsTypeParameterInstantiation
 */
public
    fun TsImportType.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiation().apply(block)
