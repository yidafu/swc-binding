package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ClassDeclaration
import dev.yidafu.swc.types.ClassDeclarationImpl
import dev.yidafu.swc.types.ExportDeclaration
import dev.yidafu.swc.types.FunctionDeclaration
import dev.yidafu.swc.types.FunctionDeclarationImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsEnumDeclaration
import dev.yidafu.swc.types.TsEnumDeclarationImpl
import dev.yidafu.swc.types.TsInterfaceDeclaration
import dev.yidafu.swc.types.TsInterfaceDeclarationImpl
import dev.yidafu.swc.types.TsModuleDeclaration
import dev.yidafu.swc.types.TsModuleDeclarationImpl
import dev.yidafu.swc.types.TsTypeAliasDeclaration
import dev.yidafu.swc.types.TsTypeAliasDeclarationImpl
import dev.yidafu.swc.types.VariableDeclaration
import dev.yidafu.swc.types.VariableDeclarationImpl
import kotlin.Unit

/**
 * ExportDeclaration#type: String
 * extension function for create String -> String
 */
public fun ExportDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ExportDeclaration#declaration: Declaration
 * extension function for create Declaration -> ClassDeclarationImpl
 */
public fun ExportDeclaration.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration
    = ClassDeclarationImpl().apply(block)

/**
 * ExportDeclaration#declaration: Declaration
 * extension function for create Declaration -> FunctionDeclarationImpl
 */
public fun ExportDeclaration.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * ExportDeclaration#declaration: Declaration
 * extension function for create Declaration -> VariableDeclarationImpl
 */
public fun ExportDeclaration.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclarationImpl().apply(block)

/**
 * ExportDeclaration#declaration: Declaration
 * extension function for create Declaration -> TsInterfaceDeclarationImpl
 */
public fun ExportDeclaration.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * ExportDeclaration#declaration: Declaration
 * extension function for create Declaration -> TsTypeAliasDeclarationImpl
 */
public fun ExportDeclaration.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * ExportDeclaration#declaration: Declaration
 * extension function for create Declaration -> TsEnumDeclarationImpl
 */
public fun ExportDeclaration.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit):
    TsEnumDeclaration = TsEnumDeclarationImpl().apply(block)

/**
 * ExportDeclaration#declaration: Declaration
 * extension function for create Declaration -> TsModuleDeclarationImpl
 */
public fun ExportDeclaration.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclarationImpl().apply(block)

/**
 * ExportDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ExportDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
