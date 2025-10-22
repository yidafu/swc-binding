package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsExternalModuleReference
import dev.yidafu.swc.types.TsExternalModuleReferenceImpl
import dev.yidafu.swc.types.TsImportEqualsDeclaration
import dev.yidafu.swc.types.TsQualifiedName
import dev.yidafu.swc.types.TsQualifiedNameImpl
import kotlin.Unit

/**
 * TsImportEqualsDeclaration#type: String
 * extension function for create String -> String
 */
public fun TsImportEqualsDeclaration.string(block: String.() -> Unit): String =
    String().apply(block)

/**
 * TsImportEqualsDeclaration#isTypeOnly: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsImportEqualsDeclaration.boolean(block: Boolean.() -> Unit): Boolean =
    Boolean().apply(block)

/**
 * TsImportEqualsDeclaration#moduleRef: TsModuleReference
 * extension function for create TsModuleReference -> IdentifierImpl
 */
public fun TsImportEqualsDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsImportEqualsDeclaration#moduleRef: TsModuleReference
 * extension function for create TsModuleReference -> TsQualifiedNameImpl
 */
public fun TsImportEqualsDeclaration.tsQualifiedName(block: TsQualifiedName.() -> Unit):
    TsQualifiedName = TsQualifiedNameImpl().apply(block)

/**
 * TsImportEqualsDeclaration#moduleRef: TsModuleReference
 * extension function for create TsModuleReference -> TsExternalModuleReferenceImpl
 */
public
    fun TsImportEqualsDeclaration.tsExternalModuleReference(block: TsExternalModuleReference.() -> Unit):
    TsExternalModuleReference = TsExternalModuleReferenceImpl().apply(block)

/**
 * TsImportEqualsDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsImportEqualsDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
