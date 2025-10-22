package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.BreakStatement
import dev.yidafu.swc.types.BreakStatementImpl
import dev.yidafu.swc.types.ClassDeclaration
import dev.yidafu.swc.types.ClassDeclarationImpl
import dev.yidafu.swc.types.ContinueStatement
import dev.yidafu.swc.types.ContinueStatementImpl
import dev.yidafu.swc.types.DebuggerStatement
import dev.yidafu.swc.types.DebuggerStatementImpl
import dev.yidafu.swc.types.DoWhileStatement
import dev.yidafu.swc.types.DoWhileStatementImpl
import dev.yidafu.swc.types.EmptyStatement
import dev.yidafu.swc.types.EmptyStatementImpl
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
import dev.yidafu.swc.types.ExpressionStatement
import dev.yidafu.swc.types.ExpressionStatementImpl
import dev.yidafu.swc.types.ForInStatement
import dev.yidafu.swc.types.ForInStatementImpl
import dev.yidafu.swc.types.ForOfStatement
import dev.yidafu.swc.types.ForOfStatementImpl
import dev.yidafu.swc.types.ForStatement
import dev.yidafu.swc.types.ForStatementImpl
import dev.yidafu.swc.types.FunctionDeclaration
import dev.yidafu.swc.types.FunctionDeclarationImpl
import dev.yidafu.swc.types.IfStatement
import dev.yidafu.swc.types.IfStatementImpl
import dev.yidafu.swc.types.ImportDeclaration
import dev.yidafu.swc.types.ImportDeclarationImpl
import dev.yidafu.swc.types.LabeledStatement
import dev.yidafu.swc.types.LabeledStatementImpl
import dev.yidafu.swc.types.Module
import dev.yidafu.swc.types.ReturnStatement
import dev.yidafu.swc.types.ReturnStatementImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.SwitchStatement
import dev.yidafu.swc.types.SwitchStatementImpl
import dev.yidafu.swc.types.ThrowStatement
import dev.yidafu.swc.types.ThrowStatementImpl
import dev.yidafu.swc.types.TryStatement
import dev.yidafu.swc.types.TryStatementImpl
import dev.yidafu.swc.types.TsEnumDeclaration
import dev.yidafu.swc.types.TsEnumDeclarationImpl
import dev.yidafu.swc.types.TsExportAssignment
import dev.yidafu.swc.types.TsExportAssignmentImpl
import dev.yidafu.swc.types.TsImportEqualsDeclaration
import dev.yidafu.swc.types.TsImportEqualsDeclarationImpl
import dev.yidafu.swc.types.TsInterfaceDeclaration
import dev.yidafu.swc.types.TsInterfaceDeclarationImpl
import dev.yidafu.swc.types.TsModuleDeclaration
import dev.yidafu.swc.types.TsModuleDeclarationImpl
import dev.yidafu.swc.types.TsNamespaceExportDeclaration
import dev.yidafu.swc.types.TsNamespaceExportDeclarationImpl
import dev.yidafu.swc.types.TsTypeAliasDeclaration
import dev.yidafu.swc.types.TsTypeAliasDeclarationImpl
import dev.yidafu.swc.types.VariableDeclaration
import dev.yidafu.swc.types.VariableDeclarationImpl
import dev.yidafu.swc.types.WhileStatement
import dev.yidafu.swc.types.WhileStatementImpl
import dev.yidafu.swc.types.WithStatement
import dev.yidafu.swc.types.WithStatementImpl
import kotlin.Unit

/**
 * Module#interpreter: String
 * extension function for create String -> String
 */
public fun Module.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ImportDeclarationImpl
 */
public fun Module.importDeclaration(block: ImportDeclaration.() -> Unit): ImportDeclaration =
    ImportDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExportDeclarationImpl
 */
public fun Module.exportDeclaration(block: ExportDeclaration.() -> Unit): ExportDeclaration =
    ExportDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExportNamedDeclarationImpl
 */
public fun Module.exportNamedDeclaration(block: ExportNamedDeclaration.() -> Unit):
    ExportNamedDeclaration = ExportNamedDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExportDefaultDeclarationImpl
 */
public fun Module.exportDefaultDeclaration(block: ExportDefaultDeclaration.() -> Unit):
    ExportDefaultDeclaration = ExportDefaultDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExportDefaultExpressionImpl
 */
public fun Module.exportDefaultExpression(block: ExportDefaultExpression.() -> Unit):
    ExportDefaultExpression = ExportDefaultExpressionImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExportAllDeclarationImpl
 */
public fun Module.exportAllDeclaration(block: ExportAllDeclaration.() -> Unit): ExportAllDeclaration
    = ExportAllDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsImportEqualsDeclarationImpl
 */
public fun Module.tsImportEqualsDeclaration(block: TsImportEqualsDeclaration.() -> Unit):
    TsImportEqualsDeclaration = TsImportEqualsDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsExportAssignmentImpl
 */
public fun Module.tsExportAssignment(block: TsExportAssignment.() -> Unit): TsExportAssignment =
    TsExportAssignmentImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsNamespaceExportDeclarationImpl
 */
public fun Module.tsNamespaceExportDeclaration(block: TsNamespaceExportDeclaration.() -> Unit):
    TsNamespaceExportDeclaration = TsNamespaceExportDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> BlockStatementImpl
 */
public fun Module.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> EmptyStatementImpl
 */
public fun Module.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> DebuggerStatementImpl
 */
public fun Module.debuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement =
    DebuggerStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> WithStatementImpl
 */
public fun Module.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ReturnStatementImpl
 */
public fun Module.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> LabeledStatementImpl
 */
public fun Module.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> BreakStatementImpl
 */
public fun Module.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ContinueStatementImpl
 */
public fun Module.continueStatement(block: ContinueStatement.() -> Unit): ContinueStatement =
    ContinueStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> IfStatementImpl
 */
public fun Module.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> SwitchStatementImpl
 */
public fun Module.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ThrowStatementImpl
 */
public fun Module.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TryStatementImpl
 */
public fun Module.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> WhileStatementImpl
 */
public fun Module.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> DoWhileStatementImpl
 */
public fun Module.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ForStatementImpl
 */
public fun Module.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ForInStatementImpl
 */
public fun Module.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ForOfStatementImpl
 */
public fun Module.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ClassDeclarationImpl
 */
public fun Module.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> FunctionDeclarationImpl
 */
public fun Module.functionDeclaration(block: FunctionDeclaration.() -> Unit): FunctionDeclaration =
    FunctionDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> VariableDeclarationImpl
 */
public fun Module.variableDeclaration(block: VariableDeclaration.() -> Unit): VariableDeclaration =
    VariableDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsInterfaceDeclarationImpl
 */
public fun Module.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsTypeAliasDeclarationImpl
 */
public fun Module.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsEnumDeclarationImpl
 */
public fun Module.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration =
    TsEnumDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsModuleDeclarationImpl
 */
public fun Module.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit): TsModuleDeclaration =
    TsModuleDeclarationImpl().apply(block)

/**
 * Module#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExpressionStatementImpl
 */
public fun Module.expressionStatement(block: ExpressionStatement.() -> Unit): ExpressionStatement =
    ExpressionStatementImpl().apply(block)

/**
 * Module#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun Module.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
