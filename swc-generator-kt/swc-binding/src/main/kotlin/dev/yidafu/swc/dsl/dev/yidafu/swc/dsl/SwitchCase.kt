package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
import dev.yidafu.swc.types.ArrowFunctionExpression
import dev.yidafu.swc.types.ArrowFunctionExpressionImpl
import dev.yidafu.swc.types.AssignmentExpression
import dev.yidafu.swc.types.AssignmentExpressionImpl
import dev.yidafu.swc.types.AwaitExpression
import dev.yidafu.swc.types.AwaitExpressionImpl
import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BinaryExpression
import dev.yidafu.swc.types.BinaryExpressionImpl
import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.BreakStatement
import dev.yidafu.swc.types.BreakStatementImpl
import dev.yidafu.swc.types.CallExpression
import dev.yidafu.swc.types.CallExpressionImpl
import dev.yidafu.swc.types.ClassDeclaration
import dev.yidafu.swc.types.ClassDeclarationImpl
import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassExpressionImpl
import dev.yidafu.swc.types.ConditionalExpression
import dev.yidafu.swc.types.ConditionalExpressionImpl
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
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.IfStatement
import dev.yidafu.swc.types.IfStatementImpl
import dev.yidafu.swc.types.Invalid
import dev.yidafu.swc.types.InvalidImpl
import dev.yidafu.swc.types.JSXElement
import dev.yidafu.swc.types.JSXElementImpl
import dev.yidafu.swc.types.JSXEmptyExpression
import dev.yidafu.swc.types.JSXEmptyExpressionImpl
import dev.yidafu.swc.types.JSXFragment
import dev.yidafu.swc.types.JSXFragmentImpl
import dev.yidafu.swc.types.JSXMemberExpression
import dev.yidafu.swc.types.JSXMemberExpressionImpl
import dev.yidafu.swc.types.JSXNamespacedName
import dev.yidafu.swc.types.JSXNamespacedNameImpl
import dev.yidafu.swc.types.JSXText
import dev.yidafu.swc.types.JSXTextImpl
import dev.yidafu.swc.types.LabeledStatement
import dev.yidafu.swc.types.LabeledStatementImpl
import dev.yidafu.swc.types.MemberExpression
import dev.yidafu.swc.types.MemberExpressionImpl
import dev.yidafu.swc.types.MetaProperty
import dev.yidafu.swc.types.MetaPropertyImpl
import dev.yidafu.swc.types.NewExpression
import dev.yidafu.swc.types.NewExpressionImpl
import dev.yidafu.swc.types.NullLiteral
import dev.yidafu.swc.types.NullLiteralImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.ObjectExpression
import dev.yidafu.swc.types.ObjectExpressionImpl
import dev.yidafu.swc.types.OptionalChainingExpression
import dev.yidafu.swc.types.OptionalChainingExpressionImpl
import dev.yidafu.swc.types.ParenthesisExpression
import dev.yidafu.swc.types.ParenthesisExpressionImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
import dev.yidafu.swc.types.ReturnStatement
import dev.yidafu.swc.types.ReturnStatementImpl
import dev.yidafu.swc.types.SequenceExpression
import dev.yidafu.swc.types.SequenceExpressionImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.SuperPropExpression
import dev.yidafu.swc.types.SuperPropExpressionImpl
import dev.yidafu.swc.types.SwitchCase
import dev.yidafu.swc.types.SwitchStatement
import dev.yidafu.swc.types.SwitchStatementImpl
import dev.yidafu.swc.types.TaggedTemplateExpression
import dev.yidafu.swc.types.TaggedTemplateExpressionImpl
import dev.yidafu.swc.types.TemplateLiteral
import dev.yidafu.swc.types.TemplateLiteralImpl
import dev.yidafu.swc.types.ThisExpression
import dev.yidafu.swc.types.ThisExpressionImpl
import dev.yidafu.swc.types.ThrowStatement
import dev.yidafu.swc.types.ThrowStatementImpl
import dev.yidafu.swc.types.TryStatement
import dev.yidafu.swc.types.TryStatementImpl
import dev.yidafu.swc.types.TsAsExpression
import dev.yidafu.swc.types.TsAsExpressionImpl
import dev.yidafu.swc.types.TsConstAssertion
import dev.yidafu.swc.types.TsConstAssertionImpl
import dev.yidafu.swc.types.TsEnumDeclaration
import dev.yidafu.swc.types.TsEnumDeclarationImpl
import dev.yidafu.swc.types.TsInstantiation
import dev.yidafu.swc.types.TsInstantiationImpl
import dev.yidafu.swc.types.TsInterfaceDeclaration
import dev.yidafu.swc.types.TsInterfaceDeclarationImpl
import dev.yidafu.swc.types.TsModuleDeclaration
import dev.yidafu.swc.types.TsModuleDeclarationImpl
import dev.yidafu.swc.types.TsNonNullExpression
import dev.yidafu.swc.types.TsNonNullExpressionImpl
import dev.yidafu.swc.types.TsSatisfiesExpression
import dev.yidafu.swc.types.TsSatisfiesExpressionImpl
import dev.yidafu.swc.types.TsTypeAliasDeclaration
import dev.yidafu.swc.types.TsTypeAliasDeclarationImpl
import dev.yidafu.swc.types.TsTypeAssertion
import dev.yidafu.swc.types.TsTypeAssertionImpl
import dev.yidafu.swc.types.UnaryExpression
import dev.yidafu.swc.types.UnaryExpressionImpl
import dev.yidafu.swc.types.UpdateExpression
import dev.yidafu.swc.types.UpdateExpressionImpl
import dev.yidafu.swc.types.VariableDeclaration
import dev.yidafu.swc.types.VariableDeclarationImpl
import dev.yidafu.swc.types.WhileStatement
import dev.yidafu.swc.types.WhileStatementImpl
import dev.yidafu.swc.types.WithStatement
import dev.yidafu.swc.types.WithStatementImpl
import dev.yidafu.swc.types.YieldExpression
import dev.yidafu.swc.types.YieldExpressionImpl
import kotlin.Unit

/**
 * SwitchCase#type: String
 * extension function for create String -> String
 */
public fun SwitchCase.string(block: String.() -> Unit): String = String().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun SwitchCase.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun SwitchCase.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun SwitchCase.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun SwitchCase.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression =
    FunctionExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun SwitchCase.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun SwitchCase.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun SwitchCase.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun SwitchCase.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun SwitchCase.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun SwitchCase.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun SwitchCase.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun SwitchCase.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun SwitchCase.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun SwitchCase.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression =
    SequenceExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun SwitchCase.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun SwitchCase.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun SwitchCase.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun SwitchCase.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun SwitchCase.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun SwitchCase.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun SwitchCase.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun SwitchCase.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun SwitchCase.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun SwitchCase.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun SwitchCase.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun SwitchCase.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun SwitchCase.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun SwitchCase.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun SwitchCase.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun SwitchCase.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun SwitchCase.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun SwitchCase.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedNameImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun SwitchCase.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression =
    JSXEmptyExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun SwitchCase.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun SwitchCase.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun SwitchCase.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun SwitchCase.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun SwitchCase.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun SwitchCase.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun SwitchCase.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun SwitchCase.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun SwitchCase.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public fun SwitchCase.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * SwitchCase#test: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun SwitchCase.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> BlockStatementImpl
 */
public fun SwitchCase.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> EmptyStatementImpl
 */
public fun SwitchCase.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> DebuggerStatementImpl
 */
public fun SwitchCase.debuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement =
    DebuggerStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> WithStatementImpl
 */
public fun SwitchCase.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> ReturnStatementImpl
 */
public fun SwitchCase.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> LabeledStatementImpl
 */
public fun SwitchCase.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> BreakStatementImpl
 */
public fun SwitchCase.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> ContinueStatementImpl
 */
public fun SwitchCase.continueStatement(block: ContinueStatement.() -> Unit): ContinueStatement =
    ContinueStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> IfStatementImpl
 */
public fun SwitchCase.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> SwitchStatementImpl
 */
public fun SwitchCase.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> ThrowStatementImpl
 */
public fun SwitchCase.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> TryStatementImpl
 */
public fun SwitchCase.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> WhileStatementImpl
 */
public fun SwitchCase.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> DoWhileStatementImpl
 */
public fun SwitchCase.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> ForStatementImpl
 */
public fun SwitchCase.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> ForInStatementImpl
 */
public fun SwitchCase.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> ForOfStatementImpl
 */
public fun SwitchCase.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> ClassDeclarationImpl
 */
public fun SwitchCase.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> FunctionDeclarationImpl
 */
public fun SwitchCase.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> VariableDeclarationImpl
 */
public fun SwitchCase.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclarationImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> TsInterfaceDeclarationImpl
 */
public fun SwitchCase.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> TsTypeAliasDeclarationImpl
 */
public fun SwitchCase.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> TsEnumDeclarationImpl
 */
public fun SwitchCase.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration =
    TsEnumDeclarationImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> TsModuleDeclarationImpl
 */
public fun SwitchCase.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclarationImpl().apply(block)

/**
 * SwitchCase#consequent: Array<Statement>
 * extension function for create Array<Statement> -> ExpressionStatementImpl
 */
public fun SwitchCase.expressionStatement(block: ExpressionStatement.() -> Unit):
    ExpressionStatement = ExpressionStatementImpl().apply(block)

/**
 * SwitchCase#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun SwitchCase.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
