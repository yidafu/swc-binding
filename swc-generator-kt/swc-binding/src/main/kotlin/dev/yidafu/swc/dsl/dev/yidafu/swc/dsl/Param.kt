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
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.CallExpression
import dev.yidafu.swc.types.CallExpressionImpl
import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassExpressionImpl
import dev.yidafu.swc.types.ConditionalExpression
import dev.yidafu.swc.types.ConditionalExpressionImpl
import dev.yidafu.swc.types.Decorator
import dev.yidafu.swc.types.DecoratorImpl
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
import dev.yidafu.swc.types.Param
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
 * Param#type: String
 * extension function for create String -> String
 */
public fun Param.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> BindingIdentifierImpl
 */
public fun Param.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier =
    BindingIdentifierImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> ArrayPatternImpl
 */
public fun Param.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> RestElementImpl
 */
public fun Param.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> ObjectPatternImpl
 */
public fun Param.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> AssignmentPatternImpl
 */
public fun Param.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern =
    AssignmentPatternImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> InvalidImpl
 */
public fun Param.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> ThisExpressionImpl
 */
public fun Param.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> ArrayExpressionImpl
 */
public fun Param.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> ObjectExpressionImpl
 */
public fun Param.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> FunctionExpressionImpl
 */
public fun Param.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression =
    FunctionExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> UnaryExpressionImpl
 */
public fun Param.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> UpdateExpressionImpl
 */
public fun Param.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> BinaryExpressionImpl
 */
public fun Param.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> AssignmentExpressionImpl
 */
public fun Param.assignmentExpression(block: AssignmentExpression.() -> Unit): AssignmentExpression
    = AssignmentExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> MemberExpressionImpl
 */
public fun Param.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> SuperPropExpressionImpl
 */
public fun Param.superPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression =
    SuperPropExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> ConditionalExpressionImpl
 */
public fun Param.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> CallExpressionImpl
 */
public fun Param.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> NewExpressionImpl
 */
public fun Param.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> SequenceExpressionImpl
 */
public fun Param.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression =
    SequenceExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> IdentifierImpl
 */
public fun Param.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> StringLiteralImpl
 */
public fun Param.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> BooleanLiteralImpl
 */
public fun Param.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> NullLiteralImpl
 */
public fun Param.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> NumericLiteralImpl
 */
public fun Param.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> BigIntLiteralImpl
 */
public fun Param.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> RegExpLiteralImpl
 */
public fun Param.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> JSXTextImpl
 */
public fun Param.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> TemplateLiteralImpl
 */
public fun Param.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> TaggedTemplateExpressionImpl
 */
public fun Param.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> ArrowFunctionExpressionImpl
 */
public fun Param.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> ClassExpressionImpl
 */
public fun Param.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> YieldExpressionImpl
 */
public fun Param.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> MetaPropertyImpl
 */
public fun Param.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> AwaitExpressionImpl
 */
public fun Param.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> ParenthesisExpressionImpl
 */
public fun Param.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> JSXMemberExpressionImpl
 */
public fun Param.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression =
    JSXMemberExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> JSXNamespacedNameImpl
 */
public fun Param.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedNameImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> JSXEmptyExpressionImpl
 */
public fun Param.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression =
    JSXEmptyExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> JSXElementImpl
 */
public fun Param.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> JSXFragmentImpl
 */
public fun Param.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> TsTypeAssertionImpl
 */
public fun Param.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> TsConstAssertionImpl
 */
public fun Param.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> TsNonNullExpressionImpl
 */
public fun Param.tsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression =
    TsNonNullExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> TsAsExpressionImpl
 */
public fun Param.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> TsSatisfiesExpressionImpl
 */
public fun Param.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> TsInstantiationImpl
 */
public fun Param.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> PrivateNameImpl
 */
public fun Param.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * Param#pat: Pattern
 * extension function for create Pattern -> OptionalChainingExpressionImpl
 */
public fun Param.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * Param#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun Param.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * Param#decorators: Array<Decorator>
 * extension function for create Array<Decorator> -> DecoratorImpl
 */
public fun Param.decorator(block: Decorator.() -> Unit): Decorator = DecoratorImpl().apply(block)
