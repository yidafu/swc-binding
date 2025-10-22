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
import dev.yidafu.swc.types.WithStatement
import dev.yidafu.swc.types.WithStatementImpl
import dev.yidafu.swc.types.YieldExpression
import dev.yidafu.swc.types.YieldExpressionImpl
import kotlin.Unit

/**
 * WhileStatement#type: String
 * extension function for create String -> String
 */
public fun WhileStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun WhileStatement.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun WhileStatement.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun WhileStatement.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun WhileStatement.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun WhileStatement.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun WhileStatement.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun WhileStatement.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun WhileStatement.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun WhileStatement.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun WhileStatement.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun WhileStatement.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun WhileStatement.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun WhileStatement.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun WhileStatement.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun WhileStatement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun WhileStatement.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun WhileStatement.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun WhileStatement.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun WhileStatement.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun WhileStatement.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun WhileStatement.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun WhileStatement.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun WhileStatement.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun WhileStatement.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun WhileStatement.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun WhileStatement.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun WhileStatement.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun WhileStatement.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun WhileStatement.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun WhileStatement.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun WhileStatement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun WhileStatement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName
    = JSXNamespacedNameImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun WhileStatement.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun WhileStatement.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun WhileStatement.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun WhileStatement.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun WhileStatement.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun WhileStatement.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun WhileStatement.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun WhileStatement.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun WhileStatement.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun WhileStatement.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public fun WhileStatement.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * WhileStatement#test: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun WhileStatement.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> BlockStatementImpl
 */
public fun WhileStatement.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> EmptyStatementImpl
 */
public fun WhileStatement.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> DebuggerStatementImpl
 */
public fun WhileStatement.debuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement
    = DebuggerStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> WithStatementImpl
 */
public fun WhileStatement.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> ReturnStatementImpl
 */
public fun WhileStatement.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> LabeledStatementImpl
 */
public fun WhileStatement.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> BreakStatementImpl
 */
public fun WhileStatement.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> ContinueStatementImpl
 */
public fun WhileStatement.continueStatement(block: ContinueStatement.() -> Unit): ContinueStatement
    = ContinueStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> IfStatementImpl
 */
public fun WhileStatement.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> SwitchStatementImpl
 */
public fun WhileStatement.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> ThrowStatementImpl
 */
public fun WhileStatement.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> TryStatementImpl
 */
public fun WhileStatement.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> DoWhileStatementImpl
 */
public fun WhileStatement.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> ForStatementImpl
 */
public fun WhileStatement.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> ForInStatementImpl
 */
public fun WhileStatement.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> ForOfStatementImpl
 */
public fun WhileStatement.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> ClassDeclarationImpl
 */
public fun WhileStatement.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> FunctionDeclarationImpl
 */
public fun WhileStatement.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> VariableDeclarationImpl
 */
public fun WhileStatement.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclarationImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> TsInterfaceDeclarationImpl
 */
public fun WhileStatement.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> TsTypeAliasDeclarationImpl
 */
public fun WhileStatement.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> TsEnumDeclarationImpl
 */
public fun WhileStatement.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration
    = TsEnumDeclarationImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> TsModuleDeclarationImpl
 */
public fun WhileStatement.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclarationImpl().apply(block)

/**
 * WhileStatement#body: Statement
 * extension function for create Statement -> ExpressionStatementImpl
 */
public fun WhileStatement.expressionStatement(block: ExpressionStatement.() -> Unit):
    ExpressionStatement = ExpressionStatementImpl().apply(block)

/**
 * WhileStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun WhileStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
