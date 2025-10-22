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
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
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
 * AssignmentPattern#type: String
 * extension function for create String -> String
 */
public fun AssignmentPattern.string(block: String.() -> Unit): String = String().apply(block)

/**
 * AssignmentPattern#left: Pattern
 * extension function for create Pattern -> BindingIdentifierImpl
 */
public fun AssignmentPattern.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * AssignmentPattern#left: Pattern
 * extension function for create Pattern -> ArrayPatternImpl
 */
public fun AssignmentPattern.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * AssignmentPattern#left: Pattern
 * extension function for create Pattern -> RestElementImpl
 */
public fun AssignmentPattern.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * AssignmentPattern#left: Pattern
 * extension function for create Pattern -> ObjectPatternImpl
 */
public fun AssignmentPattern.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun AssignmentPattern.invalid(block: Invalid.() -> Unit): Invalid =
    InvalidImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun AssignmentPattern.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun AssignmentPattern.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun AssignmentPattern.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression
    = ObjectExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun AssignmentPattern.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun AssignmentPattern.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun AssignmentPattern.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression
    = UpdateExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun AssignmentPattern.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression
    = BinaryExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun AssignmentPattern.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun AssignmentPattern.memberExpression(block: MemberExpression.() -> Unit): MemberExpression
    = MemberExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun AssignmentPattern.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun AssignmentPattern.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun AssignmentPattern.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun AssignmentPattern.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun AssignmentPattern.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun AssignmentPattern.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun AssignmentPattern.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun AssignmentPattern.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun AssignmentPattern.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun AssignmentPattern.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun AssignmentPattern.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun AssignmentPattern.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun AssignmentPattern.jSXText(block: JSXText.() -> Unit): JSXText =
    JSXTextImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun AssignmentPattern.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun AssignmentPattern.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun AssignmentPattern.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun AssignmentPattern.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun AssignmentPattern.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun AssignmentPattern.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun AssignmentPattern.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun AssignmentPattern.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun AssignmentPattern.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun AssignmentPattern.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun AssignmentPattern.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun AssignmentPattern.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun AssignmentPattern.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun AssignmentPattern.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun AssignmentPattern.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion
    = TsConstAssertionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun AssignmentPattern.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun AssignmentPattern.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun AssignmentPattern.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun AssignmentPattern.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun AssignmentPattern.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * AssignmentPattern#right: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public
    fun AssignmentPattern.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * AssignmentPattern#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun AssignmentPattern.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation
    = TsTypeAnnotationImpl().apply(block)

/**
 * AssignmentPattern#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun AssignmentPattern.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
