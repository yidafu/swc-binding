package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
import dev.yidafu.swc.types.ArrowFunctionExpression
import dev.yidafu.swc.types.ArrowFunctionExpressionImpl
import dev.yidafu.swc.types.AssignmentExpression
import dev.yidafu.swc.types.AssignmentExpressionImpl
import dev.yidafu.swc.types.AssignmentPattern
import dev.yidafu.swc.types.AssignmentPatternImpl
import dev.yidafu.swc.types.AwaitExpression
import dev.yidafu.swc.types.AwaitExpressionImpl
import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BinaryExpression
import dev.yidafu.swc.types.BinaryExpressionImpl
import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.BindingIdentifierImpl
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
import dev.yidafu.swc.types.ObjectPattern
import dev.yidafu.swc.types.ObjectPatternImpl
import dev.yidafu.swc.types.OptionalChainingExpression
import dev.yidafu.swc.types.OptionalChainingExpressionImpl
import dev.yidafu.swc.types.ParenthesisExpression
import dev.yidafu.swc.types.ParenthesisExpressionImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
import dev.yidafu.swc.types.RestElement
import dev.yidafu.swc.types.RestElementImpl
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
 * ForOfStatement#type: String
 * extension function for create String -> String
 */
public fun ForOfStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ForOfStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ForOfStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> VariableDeclarationImpl
 */
public fun ForOfStatement.variableDeclaration(block: VariableDeclaration.() -> Unit):
    VariableDeclaration = VariableDeclarationImpl().apply(block)

/**
 * ForOfStatement#left: Union.U2<VariableDeclaration, Pattern>
 * extension function for create Union.U2<VariableDeclaration, Pattern> -> BindingIdentifierImpl
 */
public fun ForOfStatement.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier
    = BindingIdentifierImpl().apply(block)

/**
 * ForOfStatement#left: Union.U2<VariableDeclaration, Pattern>
 * extension function for create Union.U2<VariableDeclaration, Pattern> -> ArrayPatternImpl
 */
public fun ForOfStatement.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * ForOfStatement#left: Union.U2<VariableDeclaration, Pattern>
 * extension function for create Union.U2<VariableDeclaration, Pattern> -> RestElementImpl
 */
public fun ForOfStatement.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * ForOfStatement#left: Union.U2<VariableDeclaration, Pattern>
 * extension function for create Union.U2<VariableDeclaration, Pattern> -> ObjectPatternImpl
 */
public fun ForOfStatement.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * ForOfStatement#left: Union.U2<VariableDeclaration, Pattern>
 * extension function for create Union.U2<VariableDeclaration, Pattern> -> AssignmentPatternImpl
 */
public fun ForOfStatement.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern
    = AssignmentPatternImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun ForOfStatement.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun ForOfStatement.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun ForOfStatement.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun ForOfStatement.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun ForOfStatement.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun ForOfStatement.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun ForOfStatement.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun ForOfStatement.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun ForOfStatement.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun ForOfStatement.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun ForOfStatement.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun ForOfStatement.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun ForOfStatement.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun ForOfStatement.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun ForOfStatement.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun ForOfStatement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun ForOfStatement.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun ForOfStatement.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun ForOfStatement.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun ForOfStatement.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun ForOfStatement.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun ForOfStatement.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun ForOfStatement.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun ForOfStatement.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun ForOfStatement.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun ForOfStatement.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun ForOfStatement.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun ForOfStatement.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun ForOfStatement.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun ForOfStatement.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun ForOfStatement.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun ForOfStatement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun ForOfStatement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName
    = JSXNamespacedNameImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun ForOfStatement.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun ForOfStatement.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun ForOfStatement.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun ForOfStatement.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun ForOfStatement.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun ForOfStatement.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun ForOfStatement.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun ForOfStatement.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun ForOfStatement.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun ForOfStatement.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * ForOfStatement#right: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public fun ForOfStatement.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> BlockStatementImpl
 */
public fun ForOfStatement.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> EmptyStatementImpl
 */
public fun ForOfStatement.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> DebuggerStatementImpl
 */
public fun ForOfStatement.debuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement
    = DebuggerStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> WithStatementImpl
 */
public fun ForOfStatement.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> ReturnStatementImpl
 */
public fun ForOfStatement.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> LabeledStatementImpl
 */
public fun ForOfStatement.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> BreakStatementImpl
 */
public fun ForOfStatement.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> ContinueStatementImpl
 */
public fun ForOfStatement.continueStatement(block: ContinueStatement.() -> Unit): ContinueStatement
    = ContinueStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> IfStatementImpl
 */
public fun ForOfStatement.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> SwitchStatementImpl
 */
public fun ForOfStatement.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> ThrowStatementImpl
 */
public fun ForOfStatement.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> TryStatementImpl
 */
public fun ForOfStatement.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> WhileStatementImpl
 */
public fun ForOfStatement.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> DoWhileStatementImpl
 */
public fun ForOfStatement.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> ForStatementImpl
 */
public fun ForOfStatement.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> ForInStatementImpl
 */
public fun ForOfStatement.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> ClassDeclarationImpl
 */
public fun ForOfStatement.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> FunctionDeclarationImpl
 */
public fun ForOfStatement.functionDeclaration(block: FunctionDeclaration.() -> Unit):
    FunctionDeclaration = FunctionDeclarationImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> TsInterfaceDeclarationImpl
 */
public fun ForOfStatement.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> TsTypeAliasDeclarationImpl
 */
public fun ForOfStatement.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> TsEnumDeclarationImpl
 */
public fun ForOfStatement.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration
    = TsEnumDeclarationImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> TsModuleDeclarationImpl
 */
public fun ForOfStatement.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit):
    TsModuleDeclaration = TsModuleDeclarationImpl().apply(block)

/**
 * ForOfStatement#body: Statement
 * extension function for create Statement -> ExpressionStatementImpl
 */
public fun ForOfStatement.expressionStatement(block: ExpressionStatement.() -> Unit):
    ExpressionStatement = ExpressionStatementImpl().apply(block)
