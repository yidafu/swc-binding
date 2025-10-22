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
import dev.yidafu.swc.types.CallExpression
import dev.yidafu.swc.types.CallExpressionImpl
import dev.yidafu.swc.types.CatchClause
import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassExpressionImpl
import dev.yidafu.swc.types.ConditionalExpression
import dev.yidafu.swc.types.ConditionalExpressionImpl
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
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
import dev.yidafu.swc.types.SequenceExpression
import dev.yidafu.swc.types.SequenceExpressionImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.SuperPropExpression
import dev.yidafu.swc.types.SuperPropExpressionImpl
import dev.yidafu.swc.types.TaggedTemplateExpression
import dev.yidafu.swc.types.TaggedTemplateExpressionImpl
import dev.yidafu.swc.types.TemplateLiteral
import dev.yidafu.swc.types.TemplateLiteralImpl
import dev.yidafu.swc.types.ThisExpression
import dev.yidafu.swc.types.ThisExpressionImpl
import dev.yidafu.swc.types.TsAsExpression
import dev.yidafu.swc.types.TsAsExpressionImpl
import dev.yidafu.swc.types.TsConstAssertion
import dev.yidafu.swc.types.TsConstAssertionImpl
import dev.yidafu.swc.types.TsInstantiation
import dev.yidafu.swc.types.TsInstantiationImpl
import dev.yidafu.swc.types.TsNonNullExpression
import dev.yidafu.swc.types.TsNonNullExpressionImpl
import dev.yidafu.swc.types.TsSatisfiesExpression
import dev.yidafu.swc.types.TsSatisfiesExpressionImpl
import dev.yidafu.swc.types.TsTypeAssertion
import dev.yidafu.swc.types.TsTypeAssertionImpl
import dev.yidafu.swc.types.UnaryExpression
import dev.yidafu.swc.types.UnaryExpressionImpl
import dev.yidafu.swc.types.UpdateExpression
import dev.yidafu.swc.types.UpdateExpressionImpl
import dev.yidafu.swc.types.YieldExpression
import dev.yidafu.swc.types.YieldExpressionImpl
import kotlin.Unit

/**
 * CatchClause#type: String
 * extension function for create String -> String
 */
public fun CatchClause.string(block: String.() -> Unit): String = String().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> BindingIdentifierImpl
 */
public fun CatchClause.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier =
    BindingIdentifierImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> ArrayPatternImpl
 */
public fun CatchClause.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> RestElementImpl
 */
public fun CatchClause.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> ObjectPatternImpl
 */
public fun CatchClause.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> AssignmentPatternImpl
 */
public fun CatchClause.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern =
    AssignmentPatternImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> InvalidImpl
 */
public fun CatchClause.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> ThisExpressionImpl
 */
public fun CatchClause.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> ArrayExpressionImpl
 */
public fun CatchClause.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> ObjectExpressionImpl
 */
public fun CatchClause.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> FunctionExpressionImpl
 */
public fun CatchClause.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression
    = FunctionExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> UnaryExpressionImpl
 */
public fun CatchClause.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> UpdateExpressionImpl
 */
public fun CatchClause.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> BinaryExpressionImpl
 */
public fun CatchClause.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> AssignmentExpressionImpl
 */
public fun CatchClause.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> MemberExpressionImpl
 */
public fun CatchClause.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> SuperPropExpressionImpl
 */
public fun CatchClause.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> ConditionalExpressionImpl
 */
public fun CatchClause.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> CallExpressionImpl
 */
public fun CatchClause.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> NewExpressionImpl
 */
public fun CatchClause.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> SequenceExpressionImpl
 */
public fun CatchClause.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression
    = SequenceExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> IdentifierImpl
 */
public fun CatchClause.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> StringLiteralImpl
 */
public fun CatchClause.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> BooleanLiteralImpl
 */
public fun CatchClause.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> NullLiteralImpl
 */
public fun CatchClause.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> NumericLiteralImpl
 */
public fun CatchClause.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> BigIntLiteralImpl
 */
public fun CatchClause.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> RegExpLiteralImpl
 */
public fun CatchClause.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> JSXTextImpl
 */
public fun CatchClause.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> TemplateLiteralImpl
 */
public fun CatchClause.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> TaggedTemplateExpressionImpl
 */
public fun CatchClause.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> ArrowFunctionExpressionImpl
 */
public fun CatchClause.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> ClassExpressionImpl
 */
public fun CatchClause.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> YieldExpressionImpl
 */
public fun CatchClause.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> MetaPropertyImpl
 */
public fun CatchClause.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> AwaitExpressionImpl
 */
public fun CatchClause.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> ParenthesisExpressionImpl
 */
public fun CatchClause.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> JSXMemberExpressionImpl
 */
public fun CatchClause.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> JSXNamespacedNameImpl
 */
public fun CatchClause.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedNameImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> JSXEmptyExpressionImpl
 */
public fun CatchClause.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression
    = JSXEmptyExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> JSXElementImpl
 */
public fun CatchClause.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> JSXFragmentImpl
 */
public fun CatchClause.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> TsTypeAssertionImpl
 */
public fun CatchClause.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> TsConstAssertionImpl
 */
public fun CatchClause.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> TsNonNullExpressionImpl
 */
public fun CatchClause.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> TsAsExpressionImpl
 */
public fun CatchClause.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> TsSatisfiesExpressionImpl
 */
public fun CatchClause.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> TsInstantiationImpl
 */
public fun CatchClause.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> PrivateNameImpl
 */
public fun CatchClause.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * CatchClause#param: Pattern
 * extension function for create Pattern -> OptionalChainingExpressionImpl
 */
public fun CatchClause.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * CatchClause#body: BlockStatement
 * extension function for create BlockStatement -> BlockStatementImpl
 */
public fun CatchClause.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * CatchClause#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun CatchClause.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
