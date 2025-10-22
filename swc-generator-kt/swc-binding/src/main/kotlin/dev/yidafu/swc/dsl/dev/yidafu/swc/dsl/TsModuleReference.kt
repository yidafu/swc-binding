package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.TsExternalModuleReference
import dev.yidafu.swc.types.TsExternalModuleReferenceImpl
import dev.yidafu.swc.types.TsModuleReference
import dev.yidafu.swc.types.TsQualifiedName
import dev.yidafu.swc.types.TsQualifiedNameImpl
import kotlin.Unit

/**
 * subtype of TsModuleReference
 */
public fun TsModuleReference.tsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedNameImpl().apply(block)

/**
 * subtype of TsModuleReference
 */
public fun TsModuleReference.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * subtype of TsModuleReference
 */
public fun TsModuleReference.tsExternalModuleReference(block: TsExternalModuleReference.() -> Unit):
    TsExternalModuleReference = TsExternalModuleReferenceImpl().apply(block)
