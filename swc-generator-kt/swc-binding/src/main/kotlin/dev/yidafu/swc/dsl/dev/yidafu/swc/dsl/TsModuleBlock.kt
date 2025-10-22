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
import dev.yidafu.swc.types.TsModuleBlock
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
 * TsModuleBlock#type: String
 * extension function for create String -> String
 */
public fun TsModuleBlock.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ImportDeclarationImpl
 */
public fun TsModuleBlock.importDeclaration(block: ImportDeclaration.() -> Unit): ImportDeclaration =
    ImportDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExportDeclarationImpl
 */
public fun TsModuleBlock.exportDeclaration(block: ExportDeclaration.() -> Unit): ExportDeclaration =
    ExportDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExportNamedDeclarationImpl
 */
public fun TsModuleBlock.exportNamedDeclaration(block: ExportNamedDeclaration.() -> Unit):
    ExportNamedDeclaration = ExportNamedDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExportDefaultDeclarationImpl
 */
public fun TsModuleBlock.exportDefaultDeclaration(block: ExportDefaultDeclaration.() -> Unit):
    ExportDefaultDeclaration = ExportDefaultDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExportDefaultExpressionImpl
 */
public fun TsModuleBlock.exportDefaultExpression(block: ExportDefaultExpression.() -> Unit):
    ExportDefaultExpression = ExportDefaultExpressionImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExportAllDeclarationImpl
 */
public fun TsModuleBlock.exportAllDeclaration(block: ExportAllDeclaration.() -> Unit):
    ExportAllDeclaration = ExportAllDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsImportEqualsDeclarationImpl
 */
public fun TsModuleBlock.tsImportEqualsDeclaration(block: TsImportEqualsDeclaration.() -> Unit):
    TsImportEqualsDeclaration = TsImportEqualsDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsExportAssignmentImpl
 */
public fun TsModuleBlock.tsExportAssignment(block: TsExportAssignment.() -> Unit):
    TsExportAssignment = TsExportAssignmentImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsNamespaceExportDeclarationImpl
 */
public
    fun TsModuleBlock.tsNamespaceExportDeclaration(block: TsNamespaceExportDeclaration.() -> Unit):
    TsNamespaceExportDeclaration = TsNamespaceExportDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> BlockStatementImpl
 */
public fun TsModuleBlock.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> EmptyStatementImpl
 */
public fun TsModuleBlock.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> DebuggerStatementImpl
 */
public fun TsModuleBlock.debuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement =
    DebuggerStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> WithStatementImpl
 */
public fun TsModuleBlock.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ReturnStatementImpl
 */
public fun TsModuleBlock.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> LabeledStatementImpl
 */
public fun TsModuleBlock.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> BreakStatementImpl
 */
public fun TsModuleBlock.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ContinueStatementImpl
 */
public fun TsModuleBlock.continueStatement(block: ContinueStatement.() -> Unit): ContinueStatement =
    ContinueStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> IfStatementImpl
 */
public fun TsModuleBlock.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> SwitchStatementImpl
 */
public fun TsModuleBlock.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ThrowStatementImpl
 */
public fun TsModuleBlock.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TryStatementImpl
 */
public fun TsModuleBlock.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> WhileStatementImpl
 */
public fun TsModuleBlock.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> DoWhileStatementImpl
 */
public fun TsModuleBlock.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ForStatementImpl
 */
public fun TsModuleBlock.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ForInStatementImpl
 */
public fun TsModuleBlock.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ForOfStatementImpl
 */
public fun TsModuleBlock.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ClassDeclarationImpl
 */
public fun TsModuleBlock.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> FunctionDeclarationImpl
 */
public fun TsModuleBlock.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> VariableDeclarationImpl
 */
public fun TsModuleBlock.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsInterfaceDeclarationImpl
 */
public fun TsModuleBlock.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsTypeAliasDeclarationImpl
 */
public fun TsModuleBlock.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsEnumDeclarationImpl
 */
public fun TsModuleBlock.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration =
    TsEnumDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> TsModuleDeclarationImpl
 */
public fun TsModuleBlock.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclarationImpl().apply(block)

/**
 * TsModuleBlock#body: Array<ModuleItem>
 * extension function for create Array<ModuleItem> -> ExpressionStatementImpl
 */
public fun TsModuleBlock.expressionStatement(block: ExpressionStatement.() -> Unit):
    ExpressionStatement = ExpressionStatementImpl().apply(block)

/**
 * TsModuleBlock#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsModuleBlock.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
