// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.161152

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ClassDeclaration
import dev.yidafu.swc.generated.ExportDeclaration
import dev.yidafu.swc.generated.FunctionDeclaration
import dev.yidafu.swc.generated.TsEnumDeclaration
import dev.yidafu.swc.generated.TsInterfaceDeclaration
import dev.yidafu.swc.generated.TsModuleDeclaration
import dev.yidafu.swc.generated.TsTypeAliasDeclaration
import dev.yidafu.swc.generated.VariableDeclaration
import kotlin.Unit

/**
 * ExportDeclaration#declaration: Declaration?
 * extension function for create Declaration? -> FunctionDeclaration
 */
public fun ExportDeclaration.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclaration().apply(block)

/**
 * ExportDeclaration#declaration: Declaration?
 * extension function for create Declaration? -> ClassDeclaration
 */
public fun ExportDeclaration.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration
    = ClassDeclaration().apply(block)

/**
 * ExportDeclaration#declaration: Declaration?
 * extension function for create Declaration? -> VariableDeclaration
 */
public fun ExportDeclaration.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclaration().apply(block)

/**
 * ExportDeclaration#declaration: Declaration?
 * extension function for create Declaration? -> TsInterfaceDeclaration
 */
public fun ExportDeclaration.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclaration().apply(block)

/**
 * ExportDeclaration#declaration: Declaration?
 * extension function for create Declaration? -> TsTypeAliasDeclaration
 */
public fun ExportDeclaration.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclaration().apply(block)

/**
 * ExportDeclaration#declaration: Declaration?
 * extension function for create Declaration? -> TsEnumDeclaration
 */
public fun ExportDeclaration.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit):
    TsEnumDeclaration = TsEnumDeclaration().apply(block)

/**
 * ExportDeclaration#declaration: Declaration?
 * extension function for create Declaration? -> TsModuleDeclaration
 */
public fun ExportDeclaration.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclaration().apply(block)
