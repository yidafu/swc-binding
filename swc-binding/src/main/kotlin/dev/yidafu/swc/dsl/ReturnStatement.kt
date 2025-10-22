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
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.CallExpression
import dev.yidafu.swc.types.CallExpressionImpl
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
import dev.yidafu.swc.types.OptionalChainingExpression
import dev.yidafu.swc.types.OptionalChainingExpressionImpl
import dev.yidafu.swc.types.ParenthesisExpression
import dev.yidafu.swc.types.ParenthesisExpressionImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
import dev.yidafu.swc.types.ReturnStatement
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
 * ReturnStatement#type: String
 * extension function for create String -> String
 */
public fun ReturnStatement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun ReturnStatement.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun ReturnStatement.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun ReturnStatement.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun ReturnStatement.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun ReturnStatement.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun ReturnStatement.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun ReturnStatement.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun ReturnStatement.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun ReturnStatement.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun ReturnStatement.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun ReturnStatement.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun ReturnStatement.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun ReturnStatement.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun ReturnStatement.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun ReturnStatement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun ReturnStatement.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun ReturnStatement.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun ReturnStatement.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun ReturnStatement.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun ReturnStatement.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun ReturnStatement.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun ReturnStatement.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun ReturnStatement.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun ReturnStatement.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun ReturnStatement.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun ReturnStatement.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun ReturnStatement.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun ReturnStatement.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun ReturnStatement.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun ReturnStatement.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun ReturnStatement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun ReturnStatement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName
    = JSXNamespacedNameImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun ReturnStatement.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun ReturnStatement.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun ReturnStatement.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun ReturnStatement.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun ReturnStatement.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun ReturnStatement.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun ReturnStatement.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun ReturnStatement.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun ReturnStatement.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun ReturnStatement.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public fun ReturnStatement.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * ReturnStatement#argument: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun ReturnStatement.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * ReturnStatement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ReturnStatement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
