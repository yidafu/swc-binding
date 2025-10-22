package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.TsModuleBlock
import dev.yidafu.swc.types.TsModuleBlockImpl
import dev.yidafu.swc.types.TsModuleDeclaration
import dev.yidafu.swc.types.TsNamespaceDeclaration
import dev.yidafu.swc.types.TsNamespaceDeclarationImpl
import kotlin.Unit

/**
 * TsModuleDeclaration#type: String
 * extension function for create String -> String
 */
public fun TsModuleDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsModuleDeclaration#global: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsModuleDeclaration.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsModuleDeclaration#id: TsModuleName
 * extension function for create TsModuleName -> IdentifierImpl
 */
public fun TsModuleDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsModuleDeclaration#id: TsModuleName
 * extension function for create TsModuleName -> StringLiteralImpl
 */
public fun TsModuleDeclaration.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TsModuleDeclaration#body: TsNamespaceBody
 * extension function for create TsNamespaceBody -> TsModuleBlockImpl
 */
public fun TsModuleDeclaration.tsModuleBlock(block: TsModuleBlock.() -> Unit): TsModuleBlock =
    TsModuleBlockImpl().apply(block)

/**
 * TsModuleDeclaration#body: TsNamespaceBody
 * extension function for create TsNamespaceBody -> TsNamespaceDeclarationImpl
 */
public fun TsModuleDeclaration.tsNamespaceDeclaration(block: TsNamespaceDeclaration.() -> Unit):
    TsNamespaceDeclaration = TsNamespaceDeclarationImpl().apply(block)

/**
 * TsModuleDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsModuleDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
