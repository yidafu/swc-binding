package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
import dev.yidafu.swc.types.ArrayPattern
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
 * ArrayPattern#type: String
 * extension function for create String -> String
 */
public fun ArrayPattern.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> BindingIdentifierImpl
 */
public fun ArrayPattern.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier =
    BindingIdentifierImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> RestElementImpl
 */
public fun ArrayPattern.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> ObjectPatternImpl
 */
public fun ArrayPattern.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> AssignmentPatternImpl
 */
public fun ArrayPattern.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern =
    AssignmentPatternImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> InvalidImpl
 */
public fun ArrayPattern.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> ThisExpressionImpl
 */
public fun ArrayPattern.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> ArrayExpressionImpl
 */
public fun ArrayPattern.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> ObjectExpressionImpl
 */
public fun ArrayPattern.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> FunctionExpressionImpl
 */
public fun ArrayPattern.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression
    = FunctionExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> UnaryExpressionImpl
 */
public fun ArrayPattern.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> UpdateExpressionImpl
 */
public fun ArrayPattern.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> BinaryExpressionImpl
 */
public fun ArrayPattern.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> AssignmentExpressionImpl
 */
public fun ArrayPattern.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> MemberExpressionImpl
 */
public fun ArrayPattern.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> SuperPropExpressionImpl
 */
public fun ArrayPattern.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> ConditionalExpressionImpl
 */
public fun ArrayPattern.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> CallExpressionImpl
 */
public fun ArrayPattern.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> NewExpressionImpl
 */
public fun ArrayPattern.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> SequenceExpressionImpl
 */
public fun ArrayPattern.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression
    = SequenceExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> IdentifierImpl
 */
public fun ArrayPattern.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> StringLiteralImpl
 */
public fun ArrayPattern.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> BooleanLiteralImpl
 */
public fun ArrayPattern.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> NullLiteralImpl
 */
public fun ArrayPattern.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> NumericLiteralImpl
 */
public fun ArrayPattern.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> BigIntLiteralImpl
 */
public fun ArrayPattern.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> RegExpLiteralImpl
 */
public fun ArrayPattern.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> JSXTextImpl
 */
public fun ArrayPattern.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> TemplateLiteralImpl
 */
public fun ArrayPattern.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> TaggedTemplateExpressionImpl
 */
public fun ArrayPattern.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> ArrowFunctionExpressionImpl
 */
public fun ArrayPattern.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> ClassExpressionImpl
 */
public fun ArrayPattern.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> YieldExpressionImpl
 */
public fun ArrayPattern.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> MetaPropertyImpl
 */
public fun ArrayPattern.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> AwaitExpressionImpl
 */
public fun ArrayPattern.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> ParenthesisExpressionImpl
 */
public fun ArrayPattern.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> JSXMemberExpressionImpl
 */
public fun ArrayPattern.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> JSXNamespacedNameImpl
 */
public fun ArrayPattern.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedNameImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> JSXEmptyExpressionImpl
 */
public fun ArrayPattern.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression
    = JSXEmptyExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> JSXElementImpl
 */
public fun ArrayPattern.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> JSXFragmentImpl
 */
public fun ArrayPattern.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> TsTypeAssertionImpl
 */
public fun ArrayPattern.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> TsConstAssertionImpl
 */
public fun ArrayPattern.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> TsNonNullExpressionImpl
 */
public fun ArrayPattern.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> TsAsExpressionImpl
 */
public fun ArrayPattern.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> TsSatisfiesExpressionImpl
 */
public fun ArrayPattern.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> TsInstantiationImpl
 */
public fun ArrayPattern.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> PrivateNameImpl
 */
public fun ArrayPattern.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * ArrayPattern#elements: Array<Pattern>
 * extension function for create Array<Pattern> -> OptionalChainingExpressionImpl
 */
public fun ArrayPattern.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * ArrayPattern#optional: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ArrayPattern.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * ArrayPattern#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun ArrayPattern.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

/**
 * ArrayPattern#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ArrayPattern.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
