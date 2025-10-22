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
import dev.yidafu.swc.types.ModuleItem
import dev.yidafu.swc.types.ReturnStatement
import dev.yidafu.swc.types.ReturnStatementImpl
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
 * subtype of ModuleItem
 */
public fun ModuleItem.importDeclaration(block: ImportDeclaration.() -> Unit): ImportDeclaration =
    ImportDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.exportDeclaration(block: ExportDeclaration.() -> Unit): ExportDeclaration =
    ExportDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.exportNamedDeclaration(block: ExportNamedDeclaration.() -> Unit):
    ExportNamedDeclaration = ExportNamedDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.exportDefaultDeclaration(block: ExportDefaultDeclaration.() -> Unit):
    ExportDefaultDeclaration = ExportDefaultDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.exportDefaultExpression(block: ExportDefaultExpression.() -> Unit):
    ExportDefaultExpression = ExportDefaultExpressionImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.exportAllDeclaration(block: ExportAllDeclaration.() -> Unit):
    ExportAllDeclaration = ExportAllDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.tsImportEqualsDeclaration(block: TsImportEqualsDeclaration.() -> Unit):
    TsImportEqualsDeclaration = TsImportEqualsDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.tsExportAssignment(block: TsExportAssignment.() -> Unit): TsExportAssignment =
    TsExportAssignmentImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.tsNamespaceExportDeclaration(block: TsNamespaceExportDeclaration.() -> Unit):
    TsNamespaceExportDeclaration = TsNamespaceExportDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.debuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement =
    DebuggerStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.continueStatement(block: ContinueStatement.() -> Unit): ContinueStatement =
    ContinueStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration =
    TsEnumDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclarationImpl().apply(block)

/**
 * subtype of ModuleItem
 */
public fun ModuleItem.expressionStatement(block: ExpressionStatement.() -> Unit):
    ExpressionStatement = ExpressionStatementImpl().apply(block)
