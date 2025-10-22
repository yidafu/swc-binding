package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsModuleBlock
import dev.yidafu.swc.types.TsModuleBlockImpl
import dev.yidafu.swc.types.TsNamespaceDeclaration
import kotlin.Unit

/**
 * TsNamespaceDeclaration#type: String
 * extension function for create String -> String
 */
public fun TsNamespaceDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsNamespaceDeclaration#global: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsNamespaceDeclaration.boolean(block: Boolean.() -> Unit): Boolean =
    Boolean().apply(block)

/**
 * TsNamespaceDeclaration#id: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun TsNamespaceDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsNamespaceDeclaration#body: TsNamespaceBody
 * extension function for create TsNamespaceBody -> TsModuleBlockImpl
 */
public fun TsNamespaceDeclaration.tsModuleBlock(block: TsModuleBlock.() -> Unit): TsModuleBlock =
    TsModuleBlockImpl().apply(block)

/**
 * TsNamespaceDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsNamespaceDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
