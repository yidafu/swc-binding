// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.322974

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.TsExternalModuleReference
import dev.yidafu.swc.generated.TsImportEqualsDeclaration
import dev.yidafu.swc.generated.TsQualifiedName
import kotlin.Unit

/**
 * TsImportEqualsDeclaration#moduleRef: TsModuleReference?
 * extension function for create TsModuleReference? -> Identifier
 */
public fun TsImportEqualsDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsImportEqualsDeclaration#moduleRef: TsModuleReference?
 * extension function for create TsModuleReference? -> TsExternalModuleReference
 */
public
    fun TsImportEqualsDeclaration.tsExternalModuleReference(block: TsExternalModuleReference.() -> Unit):
    TsExternalModuleReference = TsExternalModuleReference().apply(block)

/**
 * TsImportEqualsDeclaration#moduleRef: TsModuleReference?
 * extension function for create TsModuleReference? -> TsQualifiedName
 */
public fun TsImportEqualsDeclaration.tsQualifiedName(block: TsQualifiedName.() -> Unit):
    TsQualifiedName = TsQualifiedName().apply(block)
