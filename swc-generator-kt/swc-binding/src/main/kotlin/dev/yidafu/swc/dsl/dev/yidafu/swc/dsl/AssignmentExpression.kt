package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
import dev.yidafu.swc.types.ArrowFunctionExpression
import dev.yidafu.swc.types.ArrowFunctionExpressionImpl
import dev.yidafu.swc.types.AssignmentExpression
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
 * AssignmentExpression#operator: String
 * extension function for create String -> String
 */
public fun AssignmentExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun AssignmentExpression.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun AssignmentExpression.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression
    = ArrayExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun AssignmentExpression.objectExpression(block: ObjectExpression.() -> Unit):
    ObjectExpression = ObjectExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun AssignmentExpression.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun AssignmentExpression.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression
    = UnaryExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun AssignmentExpression.updateExpression(block: UpdateExpression.() -> Unit):
    UpdateExpression = UpdateExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun AssignmentExpression.binaryExpression(block: BinaryExpression.() -> Unit):
    BinaryExpression = BinaryExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun AssignmentExpression.memberExpression(block: MemberExpression.() -> Unit):
    MemberExpression = MemberExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun AssignmentExpression.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun AssignmentExpression.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun AssignmentExpression.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun AssignmentExpression.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun AssignmentExpression.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun AssignmentExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun AssignmentExpression.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun AssignmentExpression.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun AssignmentExpression.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun AssignmentExpression.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun AssignmentExpression.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun AssignmentExpression.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun AssignmentExpression.jSXText(block: JSXText.() -> Unit): JSXText =
    JSXTextImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun AssignmentExpression.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral
    = TemplateLiteralImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public
    fun AssignmentExpression.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun AssignmentExpression.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun AssignmentExpression.classExpression(block: ClassExpression.() -> Unit): ClassExpression
    = ClassExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun AssignmentExpression.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression
    = YieldExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun AssignmentExpression.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun AssignmentExpression.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression
    = AwaitExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun AssignmentExpression.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun AssignmentExpression.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun AssignmentExpression.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun AssignmentExpression.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun AssignmentExpression.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun AssignmentExpression.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun AssignmentExpression.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion
    = TsTypeAssertionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun AssignmentExpression.tsConstAssertion(block: TsConstAssertion.() -> Unit):
    TsConstAssertion = TsConstAssertionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun AssignmentExpression.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun AssignmentExpression.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun AssignmentExpression.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun AssignmentExpression.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation
    = TsInstantiationImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun AssignmentExpression.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public
    fun AssignmentExpression.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * AssignmentExpression#right: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun AssignmentExpression.invalid(block: Invalid.() -> Unit): Invalid =
    InvalidImpl().apply(block)

/**
 * AssignmentExpression#left: Union.U2<Expression, Pattern>
 * extension function for create Union.U2<Expression, Pattern> -> BindingIdentifierImpl
 */
public fun AssignmentExpression.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * AssignmentExpression#left: Union.U2<Expression, Pattern>
 * extension function for create Union.U2<Expression, Pattern> -> ArrayPatternImpl
 */
public fun AssignmentExpression.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * AssignmentExpression#left: Union.U2<Expression, Pattern>
 * extension function for create Union.U2<Expression, Pattern> -> RestElementImpl
 */
public fun AssignmentExpression.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * AssignmentExpression#left: Union.U2<Expression, Pattern>
 * extension function for create Union.U2<Expression, Pattern> -> ObjectPatternImpl
 */
public fun AssignmentExpression.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * AssignmentExpression#left: Union.U2<Expression, Pattern>
 * extension function for create Union.U2<Expression, Pattern> -> AssignmentPatternImpl
 */
public fun AssignmentExpression.assignmentPattern(block: AssignmentPattern.() -> Unit):
    AssignmentPattern = AssignmentPatternImpl().apply(block)

/**
 * AssignmentExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun AssignmentExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
