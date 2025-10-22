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
import dev.yidafu.swc.types.JSXExpressionContainer
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
 * JSXExpressionContainer#type: String
 * extension function for create String -> String
 */
public fun JSXExpressionContainer.string(block: String.() -> Unit): String = String().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> JSXEmptyExpressionImpl
 */
public fun JSXExpressionContainer.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> ThisExpressionImpl
 */
public fun JSXExpressionContainer.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> ArrayExpressionImpl
 */
public fun JSXExpressionContainer.arrayExpression(block: ArrayExpression.() -> Unit):
    ArrayExpression = ArrayExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> ObjectExpressionImpl
 */
public fun JSXExpressionContainer.objectExpression(block: ObjectExpression.() -> Unit):
    ObjectExpression = ObjectExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> FunctionExpressionImpl
 */
public fun JSXExpressionContainer.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> UnaryExpressionImpl
 */
public fun JSXExpressionContainer.unaryExpression(block: UnaryExpression.() -> Unit):
    UnaryExpression = UnaryExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> UpdateExpressionImpl
 */
public fun JSXExpressionContainer.updateExpression(block: UpdateExpression.() -> Unit):
    UpdateExpression = UpdateExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> BinaryExpressionImpl
 */
public fun JSXExpressionContainer.binaryExpression(block: BinaryExpression.() -> Unit):
    BinaryExpression = BinaryExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> AssignmentExpressionImpl
 */
public fun JSXExpressionContainer.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> MemberExpressionImpl
 */
public fun JSXExpressionContainer.memberExpression(block: MemberExpression.() -> Unit):
    MemberExpression = MemberExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> SuperPropExpressionImpl
 */
public fun JSXExpressionContainer.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> ConditionalExpressionImpl
 */
public fun JSXExpressionContainer.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> CallExpressionImpl
 */
public fun JSXExpressionContainer.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> NewExpressionImpl
 */
public fun JSXExpressionContainer.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> SequenceExpressionImpl
 */
public fun JSXExpressionContainer.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> IdentifierImpl
 */
public fun JSXExpressionContainer.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> StringLiteralImpl
 */
public fun JSXExpressionContainer.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> BooleanLiteralImpl
 */
public fun JSXExpressionContainer.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> NullLiteralImpl
 */
public fun JSXExpressionContainer.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> NumericLiteralImpl
 */
public fun JSXExpressionContainer.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> BigIntLiteralImpl
 */
public fun JSXExpressionContainer.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> RegExpLiteralImpl
 */
public fun JSXExpressionContainer.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> JSXTextImpl
 */
public fun JSXExpressionContainer.jSXText(block: JSXText.() -> Unit): JSXText =
    JSXTextImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> TemplateLiteralImpl
 */
public fun JSXExpressionContainer.templateLiteral(block: TemplateLiteral.() -> Unit):
    TemplateLiteral = TemplateLiteralImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> TaggedTemplateExpressionImpl
 */
public
    fun JSXExpressionContainer.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> ArrowFunctionExpressionImpl
 */
public
    fun JSXExpressionContainer.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> ClassExpressionImpl
 */
public fun JSXExpressionContainer.classExpression(block: ClassExpression.() -> Unit):
    ClassExpression = ClassExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> YieldExpressionImpl
 */
public fun JSXExpressionContainer.yieldExpression(block: YieldExpression.() -> Unit):
    YieldExpression = YieldExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> MetaPropertyImpl
 */
public fun JSXExpressionContainer.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> AwaitExpressionImpl
 */
public fun JSXExpressionContainer.awaitExpression(block: AwaitExpression.() -> Unit):
    AwaitExpression = AwaitExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> ParenthesisExpressionImpl
 */
public fun JSXExpressionContainer.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> JSXMemberExpressionImpl
 */
public fun JSXExpressionContainer.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> JSXNamespacedNameImpl
 */
public fun JSXExpressionContainer.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> JSXElementImpl
 */
public fun JSXExpressionContainer.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> JSXFragmentImpl
 */
public fun JSXExpressionContainer.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> TsTypeAssertionImpl
 */
public fun JSXExpressionContainer.tsTypeAssertion(block: TsTypeAssertion.() -> Unit):
    TsTypeAssertion = TsTypeAssertionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> TsConstAssertionImpl
 */
public fun JSXExpressionContainer.tsConstAssertion(block: TsConstAssertion.() -> Unit):
    TsConstAssertion = TsConstAssertionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> TsNonNullExpressionImpl
 */
public fun JSXExpressionContainer.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> TsAsExpressionImpl
 */
public fun JSXExpressionContainer.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> TsSatisfiesExpressionImpl
 */
public fun JSXExpressionContainer.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> TsInstantiationImpl
 */
public fun JSXExpressionContainer.tsInstantiation(block: TsInstantiation.() -> Unit):
    TsInstantiation = TsInstantiationImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> PrivateNameImpl
 */
public fun JSXExpressionContainer.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> OptionalChainingExpressionImpl
 */
public
    fun JSXExpressionContainer.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * JSXExpressionContainer#expression: JSXExpression
 * extension function for create JSXExpression -> InvalidImpl
 */
public fun JSXExpressionContainer.invalid(block: Invalid.() -> Unit): Invalid =
    InvalidImpl().apply(block)

/**
 * JSXExpressionContainer#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun JSXExpressionContainer.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
