package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ExportAllDeclaration
import dev.yidafu.swc.types.ExportAllDeclarationImpl
import dev.yidafu.swc.types.ExportDeclaration
import dev.yidafu.swc.types.ExportDeclarationImpl
import dev.yidafu.swc.types.ExportDefaultDeclaration
import dev.yidafu.swc.types.ExportDefaultDeclarationImpl
import dev.yidafu.swc.types.ExportDefaultExpression
import dev.yidafu.swc.types.ExportDefaultExpressionImpl
import dev.yidafu.swc.types.ExportNamedDeclaration
import dev.yidafu.swc.types.ExportNamedDeclarationImpl
import dev.yidafu.swc.types.ImportDeclaration
import dev.yidafu.swc.types.ImportDeclarationImpl
import dev.yidafu.swc.types.ModuleDeclaration
import dev.yidafu.swc.types.TsExportAssignment
import dev.yidafu.swc.types.TsExportAssignmentImpl
import dev.yidafu.swc.types.TsImportEqualsDeclaration
import dev.yidafu.swc.types.TsImportEqualsDeclarationImpl
import dev.yidafu.swc.types.TsNamespaceExportDeclaration
import dev.yidafu.swc.types.TsNamespaceExportDeclarationImpl
import kotlin.Unit

/**
 * subtype of ModuleDeclaration
 */
public fun ModuleDeclaration.importDeclaration(block: ImportDeclaration.() -> Unit):
    ImportDeclaration = ImportDeclarationImpl().apply(block)

/**
 * subtype of ModuleDeclaration
 */
public fun ModuleDeclaration.exportDeclaration(block: ExportDeclaration.() -> Unit):
    ExportDeclaration = ExportDeclarationImpl().apply(block)

/**
 * subtype of ModuleDeclaration
 */
public fun ModuleDeclaration.exportNamedDeclaration(block: ExportNamedDeclaration.() -> Unit):
    ExportNamedDeclaration = ExportNamedDeclarationImpl().apply(block)

/**
 * subtype of ModuleDeclaration
 */
public fun ModuleDeclaration.exportDefaultDeclaration(block: ExportDefaultDeclaration.() -> Unit):
    ExportDefaultDeclaration = ExportDefaultDeclarationImpl().apply(block)

/**
 * subtype of ModuleDeclaration
 */
public fun ModuleDeclaration.exportDefaultExpression(block: ExportDefaultExpression.() -> Unit):
    ExportDefaultExpression = ExportDefaultExpressionImpl().apply(block)

/**
 * subtype of ModuleDeclaration
 */
public fun ModuleDeclaration.exportAllDeclaration(block: ExportAllDeclaration.() -> Unit):
    ExportAllDeclaration = ExportAllDeclarationImpl().apply(block)

/**
 * subtype of ModuleDeclaration
 */
public fun ModuleDeclaration.tsImportEqualsDeclaration(block: TsImportEqualsDeclaration.() -> Unit):
    TsImportEqualsDeclaration = TsImportEqualsDeclarationImpl().apply(block)

/**
 * subtype of ModuleDeclaration
 */
public fun ModuleDeclaration.tsExportAssignment(block: TsExportAssignment.() -> Unit):
    TsExportAssignment = TsExportAssignmentImpl().apply(block)

/**
 * subtype of ModuleDeclaration
 */
public
    fun ModuleDeclaration.tsNamespaceExportDeclaration(block: TsNamespaceExportDeclaration.() -> Unit):
    TsNamespaceExportDeclaration = TsNamespaceExportDeclarationImpl().apply(block)
