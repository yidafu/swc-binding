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
import dev.yidafu.swc.types.WhileStatementImpl
import dev.yidafu.swc.types.WithStatement
import dev.yidafu.swc.types.WithStatementImpl
import dev.yidafu.swc.types.YieldExpression
import dev.yidafu.swc.types.YieldExpressionImpl
import kotlin.Unit

/**
 * DoWhileStatement#type: String
 * extension function for create String -> String
 */
public fun DoWhileStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun DoWhileStatement.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun DoWhileStatement.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun DoWhileStatement.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun DoWhileStatement.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun DoWhileStatement.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun DoWhileStatement.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun DoWhileStatement.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun DoWhileStatement.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun DoWhileStatement.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun DoWhileStatement.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun DoWhileStatement.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun DoWhileStatement.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun DoWhileStatement.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun DoWhileStatement.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun DoWhileStatement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun DoWhileStatement.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun DoWhileStatement.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun DoWhileStatement.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun DoWhileStatement.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun DoWhileStatement.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun DoWhileStatement.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun DoWhileStatement.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun DoWhileStatement.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun DoWhileStatement.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun DoWhileStatement.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun DoWhileStatement.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun DoWhileStatement.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun DoWhileStatement.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun DoWhileStatement.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun DoWhileStatement.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun DoWhileStatement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun DoWhileStatement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun DoWhileStatement.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun DoWhileStatement.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun DoWhileStatement.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun DoWhileStatement.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun DoWhileStatement.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun DoWhileStatement.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun DoWhileStatement.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun DoWhileStatement.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun DoWhileStatement.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun DoWhileStatement.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public
    fun DoWhileStatement.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * DoWhileStatement#test: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun DoWhileStatement.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> BlockStatementImpl
 */
public fun DoWhileStatement.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> EmptyStatementImpl
 */
public fun DoWhileStatement.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> DebuggerStatementImpl
 */
public fun DoWhileStatement.debuggerStatement(block: DebuggerStatement.() -> Unit):
    DebuggerStatement = DebuggerStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> WithStatementImpl
 */
public fun DoWhileStatement.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> ReturnStatementImpl
 */
public fun DoWhileStatement.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> LabeledStatementImpl
 */
public fun DoWhileStatement.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> BreakStatementImpl
 */
public fun DoWhileStatement.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> ContinueStatementImpl
 */
public fun DoWhileStatement.continueStatement(block: ContinueStatement.() -> Unit):
    ContinueStatement = ContinueStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> IfStatementImpl
 */
public fun DoWhileStatement.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> SwitchStatementImpl
 */
public fun DoWhileStatement.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> ThrowStatementImpl
 */
public fun DoWhileStatement.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> TryStatementImpl
 */
public fun DoWhileStatement.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> WhileStatementImpl
 */
public fun DoWhileStatement.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> ForStatementImpl
 */
public fun DoWhileStatement.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> ForInStatementImpl
 */
public fun DoWhileStatement.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> ForOfStatementImpl
 */
public fun DoWhileStatement.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> ClassDeclarationImpl
 */
public fun DoWhileStatement.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> FunctionDeclarationImpl
 */
public fun DoWhileStatement.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> VariableDeclarationImpl
 */
public fun DoWhileStatement.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclarationImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> TsInterfaceDeclarationImpl
 */
public fun DoWhileStatement.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> TsTypeAliasDeclarationImpl
 */
public fun DoWhileStatement.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> TsEnumDeclarationImpl
 */
public fun DoWhileStatement.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit):
    TsEnumDeclaration = TsEnumDeclarationImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> TsModuleDeclarationImpl
 */
public fun DoWhileStatement.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclarationImpl().apply(block)

/**
 * DoWhileStatement#body: Statement
 * extension function for create Statement -> ExpressionStatementImpl
 */
public fun DoWhileStatement.expressionStatement(block: ExpressionStatement.() -> Unit):
    ExpressionStatement = ExpressionStatementImpl().apply(block)

/**
 * DoWhileStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun DoWhileStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
