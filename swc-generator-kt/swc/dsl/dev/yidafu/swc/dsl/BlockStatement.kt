package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BlockStatement
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
import dev.yidafu.swc.types.TsInterfaceDeclaration
import dev.yidafu.swc.types.TsInterfaceDeclarationImpl
import dev.yidafu.swc.types.TsModuleDeclaration
import dev.yidafu.swc.types.TsModuleDeclarationImpl
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
 * BlockStatement#type: String
 * extension function for create String -> String
 */
public fun BlockStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> EmptyStatementImpl
 */
public fun BlockStatement.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> DebuggerStatementImpl
 */
public fun BlockStatement.debuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement
    = DebuggerStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> WithStatementImpl
 */
public fun BlockStatement.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> ReturnStatementImpl
 */
public fun BlockStatement.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> LabeledStatementImpl
 */
public fun BlockStatement.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> BreakStatementImpl
 */
public fun BlockStatement.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> ContinueStatementImpl
 */
public fun BlockStatement.continueStatement(block: ContinueStatement.() -> Unit): ContinueStatement
    = ContinueStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> IfStatementImpl
 */
public fun BlockStatement.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> SwitchStatementImpl
 */
public fun BlockStatement.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> ThrowStatementImpl
 */
public fun BlockStatement.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> TryStatementImpl
 */
public fun BlockStatement.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> WhileStatementImpl
 */
public fun BlockStatement.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> DoWhileStatementImpl
 */
public fun BlockStatement.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> ForStatementImpl
 */
public fun BlockStatement.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> ForInStatementImpl
 */
public fun BlockStatement.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> ForOfStatementImpl
 */
public fun BlockStatement.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> ClassDeclarationImpl
 */
public fun BlockStatement.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> FunctionDeclarationImpl
 */
public fun BlockStatement.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> VariableDeclarationImpl
 */
public fun BlockStatement.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclarationImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> TsInterfaceDeclarationImpl
 */
public fun BlockStatement.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> TsTypeAliasDeclarationImpl
 */
public fun BlockStatement.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> TsEnumDeclarationImpl
 */
public fun BlockStatement.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration
    = TsEnumDeclarationImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> TsModuleDeclarationImpl
 */
public fun BlockStatement.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclarationImpl().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>
 * extension function for create Array<Statement> -> ExpressionStatementImpl
 */
public fun BlockStatement.expressionStatement(block: ExpressionStatement.() -> Unit):
    ExpressionStatement = ExpressionStatementImpl().apply(block)

/**
 * BlockStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun BlockStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
