package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
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
import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.BindingIdentifierImpl
import dev.yidafu.swc.types.Boolean
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
import dev.yidafu.swc.types.TsSetterSignature
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
 * TsSetterSignature#type: String
 * extension function for create String -> String
 */
public fun TsSetterSignature.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsSetterSignature#optional: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsSetterSignature.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun TsSetterSignature.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun TsSetterSignature.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun TsSetterSignature.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression
    = ObjectExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun TsSetterSignature.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun TsSetterSignature.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun TsSetterSignature.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression
    = UpdateExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun TsSetterSignature.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression
    = BinaryExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun TsSetterSignature.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun TsSetterSignature.memberExpression(block: MemberExpression.() -> Unit): MemberExpression
    = MemberExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun TsSetterSignature.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun TsSetterSignature.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun TsSetterSignature.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun TsSetterSignature.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun TsSetterSignature.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun TsSetterSignature.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun TsSetterSignature.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun TsSetterSignature.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun TsSetterSignature.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun TsSetterSignature.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun TsSetterSignature.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun TsSetterSignature.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun TsSetterSignature.jSXText(block: JSXText.() -> Unit): JSXText =
    JSXTextImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun TsSetterSignature.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun TsSetterSignature.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun TsSetterSignature.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun TsSetterSignature.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun TsSetterSignature.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun TsSetterSignature.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun TsSetterSignature.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun TsSetterSignature.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun TsSetterSignature.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun TsSetterSignature.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun TsSetterSignature.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun TsSetterSignature.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun TsSetterSignature.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun TsSetterSignature.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun TsSetterSignature.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion
    = TsConstAssertionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun TsSetterSignature.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun TsSetterSignature.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun TsSetterSignature.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun TsSetterSignature.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun TsSetterSignature.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public
    fun TsSetterSignature.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * TsSetterSignature#key: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun TsSetterSignature.invalid(block: Invalid.() -> Unit): Invalid =
    InvalidImpl().apply(block)

/**
 * TsSetterSignature#param: TsFnParameter
 * extension function for create TsFnParameter -> BindingIdentifierImpl
 */
public fun TsSetterSignature.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsSetterSignature#param: TsFnParameter
 * extension function for create TsFnParameter -> ArrayPatternImpl
 */
public fun TsSetterSignature.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * TsSetterSignature#param: TsFnParameter
 * extension function for create TsFnParameter -> RestElementImpl
 */
public fun TsSetterSignature.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * TsSetterSignature#param: TsFnParameter
 * extension function for create TsFnParameter -> ObjectPatternImpl
 */
public fun TsSetterSignature.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * TsSetterSignature#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsSetterSignature.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
