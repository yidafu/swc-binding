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
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.IfStatement
import dev.yidafu.swc.types.IfStatementImpl
import dev.yidafu.swc.types.LabeledStatement
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
 * LabeledStatement#type: String
 * extension function for create String -> String
 */
public fun LabeledStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * LabeledStatement#label: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun LabeledStatement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> BlockStatementImpl
 */
public fun LabeledStatement.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> EmptyStatementImpl
 */
public fun LabeledStatement.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> DebuggerStatementImpl
 */
public fun LabeledStatement.debuggerStatement(block: DebuggerStatement.() -> Unit):
    DebuggerStatement = DebuggerStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> WithStatementImpl
 */
public fun LabeledStatement.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> ReturnStatementImpl
 */
public fun LabeledStatement.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> BreakStatementImpl
 */
public fun LabeledStatement.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> ContinueStatementImpl
 */
public fun LabeledStatement.continueStatement(block: ContinueStatement.() -> Unit):
    ContinueStatement = ContinueStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> IfStatementImpl
 */
public fun LabeledStatement.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> SwitchStatementImpl
 */
public fun LabeledStatement.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> ThrowStatementImpl
 */
public fun LabeledStatement.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> TryStatementImpl
 */
public fun LabeledStatement.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> WhileStatementImpl
 */
public fun LabeledStatement.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> DoWhileStatementImpl
 */
public fun LabeledStatement.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> ForStatementImpl
 */
public fun LabeledStatement.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> ForInStatementImpl
 */
public fun LabeledStatement.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> ForOfStatementImpl
 */
public fun LabeledStatement.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> ClassDeclarationImpl
 */
public fun LabeledStatement.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> FunctionDeclarationImpl
 */
public fun LabeledStatement.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> VariableDeclarationImpl
 */
public fun LabeledStatement.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclarationImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> TsInterfaceDeclarationImpl
 */
public fun LabeledStatement.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> TsTypeAliasDeclarationImpl
 */
public fun LabeledStatement.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> TsEnumDeclarationImpl
 */
public fun LabeledStatement.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit):
    TsEnumDeclaration = TsEnumDeclarationImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> TsModuleDeclarationImpl
 */
public fun LabeledStatement.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclarationImpl().apply(block)

/**
 * LabeledStatement#body: Statement
 * extension function for create Statement -> ExpressionStatementImpl
 */
public fun LabeledStatement.expressionStatement(block: ExpressionStatement.() -> Unit):
    ExpressionStatement = ExpressionStatementImpl().apply(block)

/**
 * LabeledStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun LabeledStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
