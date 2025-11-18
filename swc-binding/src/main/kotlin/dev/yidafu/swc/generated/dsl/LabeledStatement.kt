// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.093039

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
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
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
 * LabeledStatement#label: Identifier?
 * extension function for create Identifier? -> Identifier
 */
public fun LabeledStatement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> BlockStatement
 */
public fun LabeledStatement.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> ExpressionStatement
 */
public fun LabeledStatement.expressionStatement(block: ExpressionStatement.() -> Unit):
    ExpressionStatement = ExpressionStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> EmptyStatement
 */
public fun LabeledStatement.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> DebuggerStatement
 */
public fun LabeledStatement.debuggerStatement(block: DebuggerStatement.() -> Unit):
    DebuggerStatement = DebuggerStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> WithStatement
 */
public fun LabeledStatement.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> ReturnStatement
 */
public fun LabeledStatement.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> BreakStatement
 */
public fun LabeledStatement.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> ContinueStatement
 */
public fun LabeledStatement.continueStatement(block: ContinueStatement.() -> Unit):
    ContinueStatement = ContinueStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> IfStatement
 */
public fun LabeledStatement.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> SwitchStatement
 */
public fun LabeledStatement.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> ThrowStatement
 */
public fun LabeledStatement.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> TryStatement
 */
public fun LabeledStatement.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> WhileStatement
 */
public fun LabeledStatement.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> DoWhileStatement
 */
public fun LabeledStatement.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> ForStatement
 */
public fun LabeledStatement.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> ForInStatement
 */
public fun LabeledStatement.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> ForOfStatement
 */
public fun LabeledStatement.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatement().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> FunctionDeclaration
 */
public fun LabeledStatement.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclaration().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> ClassDeclaration
 */
public fun LabeledStatement.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclaration().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> VariableDeclaration
 */
public fun LabeledStatement.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclaration().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> TsInterfaceDeclaration
 */
public fun LabeledStatement.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclaration().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> TsTypeAliasDeclaration
 */
public fun LabeledStatement.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclaration().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> TsEnumDeclaration
 */
public fun LabeledStatement.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit):
    TsEnumDeclaration = TsEnumDeclaration().apply(block)

/**
 * LabeledStatement#body: Statement?
 * extension function for create Statement? -> TsModuleDeclaration
 */
public fun LabeledStatement.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclaration().apply(block)
