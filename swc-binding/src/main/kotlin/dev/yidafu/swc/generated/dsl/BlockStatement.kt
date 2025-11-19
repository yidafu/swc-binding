// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.216823

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BlockStatement
import dev.yidafu.swc.generated.BreakStatement
import dev.yidafu.swc.generated.ClassDeclaration
import dev.yidafu.swc.generated.ContinueStatement
import dev.yidafu.swc.generated.DebuggerStatement
import dev.yidafu.swc.generated.DoWhileStatement
import dev.yidafu.swc.generated.EmptyStatement
import dev.yidafu.swc.generated.ExpressionStatement
import dev.yidafu.swc.generated.ForInStatement
import dev.yidafu.swc.generated.ForOfStatement
import dev.yidafu.swc.generated.ForStatement
import dev.yidafu.swc.generated.FunctionDeclaration
import dev.yidafu.swc.generated.IfStatement
import dev.yidafu.swc.generated.LabeledStatement
import dev.yidafu.swc.generated.ReturnStatement
import dev.yidafu.swc.generated.SwitchStatement
import dev.yidafu.swc.generated.ThrowStatement
import dev.yidafu.swc.generated.TryStatement
import dev.yidafu.swc.generated.TsEnumDeclaration
import dev.yidafu.swc.generated.TsInterfaceDeclaration
import dev.yidafu.swc.generated.TsModuleDeclaration
import dev.yidafu.swc.generated.TsTypeAliasDeclaration
import dev.yidafu.swc.generated.VariableDeclaration
import dev.yidafu.swc.generated.WhileStatement
import dev.yidafu.swc.generated.WithStatement
import kotlin.Unit

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> ExpressionStatement
 */
public fun BlockStatement.expressionStatement(block: ExpressionStatement.() -> Unit):
    ExpressionStatement = ExpressionStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> EmptyStatement
 */
public fun BlockStatement.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> DebuggerStatement
 */
public fun BlockStatement.debuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement
    = DebuggerStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> WithStatement
 */
public fun BlockStatement.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> ReturnStatement
 */
public fun BlockStatement.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> LabeledStatement
 */
public fun BlockStatement.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> BreakStatement
 */
public fun BlockStatement.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> ContinueStatement
 */
public fun BlockStatement.continueStatement(block: ContinueStatement.() -> Unit): ContinueStatement
    = ContinueStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> IfStatement
 */
public fun BlockStatement.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> SwitchStatement
 */
public fun BlockStatement.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> ThrowStatement
 */
public fun BlockStatement.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> TryStatement
 */
public fun BlockStatement.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> WhileStatement
 */
public fun BlockStatement.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> DoWhileStatement
 */
public fun BlockStatement.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> ForStatement
 */
public fun BlockStatement.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> ForInStatement
 */
public fun BlockStatement.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> ForOfStatement
 */
public fun BlockStatement.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatement().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> FunctionDeclaration
 */
public fun BlockStatement.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclaration().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> ClassDeclaration
 */
public fun BlockStatement.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclaration().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> VariableDeclaration
 */
public fun BlockStatement.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclaration().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> TsInterfaceDeclaration
 */
public fun BlockStatement.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclaration().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> TsTypeAliasDeclaration
 */
public fun BlockStatement.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclaration().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> TsEnumDeclaration
 */
public fun BlockStatement.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration
    = TsEnumDeclaration().apply(block)

/**
 * BlockStatement#stmts: Array<Statement>?
 * extension function for create Array<Statement>? -> TsModuleDeclaration
 */
public fun BlockStatement.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclaration().apply(block)
