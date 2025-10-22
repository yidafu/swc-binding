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
import dev.yidafu.swc.types.Script
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
 * Script#interpreter: String
 * extension function for create String -> String
 */
public fun Script.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> BlockStatementImpl
 */
public fun Script.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> EmptyStatementImpl
 */
public fun Script.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> DebuggerStatementImpl
 */
public fun Script.debuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement =
    DebuggerStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> WithStatementImpl
 */
public fun Script.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> ReturnStatementImpl
 */
public fun Script.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> LabeledStatementImpl
 */
public fun Script.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> BreakStatementImpl
 */
public fun Script.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> ContinueStatementImpl
 */
public fun Script.continueStatement(block: ContinueStatement.() -> Unit): ContinueStatement =
    ContinueStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> IfStatementImpl
 */
public fun Script.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> SwitchStatementImpl
 */
public fun Script.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> ThrowStatementImpl
 */
public fun Script.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> TryStatementImpl
 */
public fun Script.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> WhileStatementImpl
 */
public fun Script.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> DoWhileStatementImpl
 */
public fun Script.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> ForStatementImpl
 */
public fun Script.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> ForInStatementImpl
 */
public fun Script.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> ForOfStatementImpl
 */
public fun Script.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> ClassDeclarationImpl
 */
public fun Script.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> FunctionDeclarationImpl
 */
public fun Script.functionDeclaration(block: FunctionDeclaration.() -> Unit): FunctionDeclaration =
    FunctionDeclarationImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> VariableDeclarationImpl
 */
public fun Script.variableDeclaration(block: VariableDeclaration.() -> Unit): VariableDeclaration =
    VariableDeclarationImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> TsInterfaceDeclarationImpl
 */
public fun Script.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> TsTypeAliasDeclarationImpl
 */
public fun Script.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> TsEnumDeclarationImpl
 */
public fun Script.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration =
    TsEnumDeclarationImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> TsModuleDeclarationImpl
 */
public fun Script.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit): TsModuleDeclaration =
    TsModuleDeclarationImpl().apply(block)

/**
 * Script#body: Array<Statement>
 * extension function for create Array<Statement> -> ExpressionStatementImpl
 */
public fun Script.expressionStatement(block: ExpressionStatement.() -> Unit): ExpressionStatement =
    ExpressionStatementImpl().apply(block)

/**
 * Script#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun Script.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
