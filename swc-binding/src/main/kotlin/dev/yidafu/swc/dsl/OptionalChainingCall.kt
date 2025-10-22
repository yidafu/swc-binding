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
import dev.yidafu.swc.types.ExprOrSpread
import dev.yidafu.swc.types.ExprOrSpreadImpl
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
import dev.yidafu.swc.types.OptionalChainingCall
import dev.yidafu.swc.types.OptionalChainingExpression
import dev.yidafu.swc.types.OptionalChainingExpressionImpl
import dev.yidafu.swc.types.ParenthesisExpression
import dev.yidafu.swc.types.ParenthesisExpressionImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
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
import dev.yidafu.swc.types.TsTypeParameterInstantiation
import dev.yidafu.swc.types.TsTypeParameterInstantiationImpl
import dev.yidafu.swc.types.UnaryExpression
import dev.yidafu.swc.types.UnaryExpressionImpl
import dev.yidafu.swc.types.UpdateExpression
import dev.yidafu.swc.types.UpdateExpressionImpl
import dev.yidafu.swc.types.YieldExpression
import dev.yidafu.swc.types.YieldExpressionImpl
import kotlin.Unit

/**
 * OptionalChainingCall#type: String
 * extension function for create String -> String
 */
public fun OptionalChainingCall.string(block: String.() -> Unit): String = String().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun OptionalChainingCall.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun OptionalChainingCall.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression
    = ArrayExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun OptionalChainingCall.objectExpression(block: ObjectExpression.() -> Unit):
    ObjectExpression = ObjectExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun OptionalChainingCall.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun OptionalChainingCall.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression
    = UnaryExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun OptionalChainingCall.updateExpression(block: UpdateExpression.() -> Unit):
    UpdateExpression = UpdateExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun OptionalChainingCall.binaryExpression(block: BinaryExpression.() -> Unit):
    BinaryExpression = BinaryExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun OptionalChainingCall.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun OptionalChainingCall.memberExpression(block: MemberExpression.() -> Unit):
    MemberExpression = MemberExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun OptionalChainingCall.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun OptionalChainingCall.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun OptionalChainingCall.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun OptionalChainingCall.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun OptionalChainingCall.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun OptionalChainingCall.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun OptionalChainingCall.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun OptionalChainingCall.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun OptionalChainingCall.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun OptionalChainingCall.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun OptionalChainingCall.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun OptionalChainingCall.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun OptionalChainingCall.jSXText(block: JSXText.() -> Unit): JSXText =
    JSXTextImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun OptionalChainingCall.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral
    = TemplateLiteralImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public
    fun OptionalChainingCall.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun OptionalChainingCall.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun OptionalChainingCall.classExpression(block: ClassExpression.() -> Unit): ClassExpression
    = ClassExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun OptionalChainingCall.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression
    = YieldExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun OptionalChainingCall.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun OptionalChainingCall.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression
    = AwaitExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun OptionalChainingCall.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun OptionalChainingCall.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun OptionalChainingCall.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun OptionalChainingCall.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun OptionalChainingCall.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun OptionalChainingCall.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun OptionalChainingCall.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion
    = TsTypeAssertionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun OptionalChainingCall.tsConstAssertion(block: TsConstAssertion.() -> Unit):
    TsConstAssertion = TsConstAssertionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun OptionalChainingCall.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun OptionalChainingCall.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun OptionalChainingCall.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun OptionalChainingCall.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation
    = TsInstantiationImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun OptionalChainingCall.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public
    fun OptionalChainingCall.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * OptionalChainingCall#callee: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun OptionalChainingCall.invalid(block: Invalid.() -> Unit): Invalid =
    InvalidImpl().apply(block)

/**
 * OptionalChainingCall#arguments: Array<ExprOrSpread>
 * extension function for create Array<ExprOrSpread> -> ExprOrSpreadImpl
 */
public fun OptionalChainingCall.exprOrSpread(block: ExprOrSpread.() -> Unit): ExprOrSpread =
    ExprOrSpreadImpl().apply(block)

/**
 * OptionalChainingCall#typeArguments: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
public
    fun OptionalChainingCall.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * OptionalChainingCall#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun OptionalChainingCall.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
