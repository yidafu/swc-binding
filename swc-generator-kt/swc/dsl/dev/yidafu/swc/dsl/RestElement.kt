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
 * RestElement#type: String
 * extension function for create String -> String
 */
public fun RestElement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * RestElement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun RestElement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> BindingIdentifierImpl
 */
public fun RestElement.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier =
    BindingIdentifierImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> ArrayPatternImpl
 */
public fun RestElement.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> ObjectPatternImpl
 */
public fun RestElement.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> AssignmentPatternImpl
 */
public fun RestElement.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern =
    AssignmentPatternImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> InvalidImpl
 */
public fun RestElement.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> ThisExpressionImpl
 */
public fun RestElement.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> ArrayExpressionImpl
 */
public fun RestElement.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> ObjectExpressionImpl
 */
public fun RestElement.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> FunctionExpressionImpl
 */
public fun RestElement.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression
    = FunctionExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> UnaryExpressionImpl
 */
public fun RestElement.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> UpdateExpressionImpl
 */
public fun RestElement.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> BinaryExpressionImpl
 */
public fun RestElement.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> AssignmentExpressionImpl
 */
public fun RestElement.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> MemberExpressionImpl
 */
public fun RestElement.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> SuperPropExpressionImpl
 */
public fun RestElement.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> ConditionalExpressionImpl
 */
public fun RestElement.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> CallExpressionImpl
 */
public fun RestElement.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> NewExpressionImpl
 */
public fun RestElement.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> SequenceExpressionImpl
 */
public fun RestElement.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression
    = SequenceExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> IdentifierImpl
 */
public fun RestElement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> StringLiteralImpl
 */
public fun RestElement.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> BooleanLiteralImpl
 */
public fun RestElement.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> NullLiteralImpl
 */
public fun RestElement.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> NumericLiteralImpl
 */
public fun RestElement.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> BigIntLiteralImpl
 */
public fun RestElement.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> RegExpLiteralImpl
 */
public fun RestElement.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> JSXTextImpl
 */
public fun RestElement.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> TemplateLiteralImpl
 */
public fun RestElement.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> TaggedTemplateExpressionImpl
 */
public fun RestElement.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> ArrowFunctionExpressionImpl
 */
public fun RestElement.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> ClassExpressionImpl
 */
public fun RestElement.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> YieldExpressionImpl
 */
public fun RestElement.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> MetaPropertyImpl
 */
public fun RestElement.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> AwaitExpressionImpl
 */
public fun RestElement.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> ParenthesisExpressionImpl
 */
public fun RestElement.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> JSXMemberExpressionImpl
 */
public fun RestElement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> JSXNamespacedNameImpl
 */
public fun RestElement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedNameImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> JSXEmptyExpressionImpl
 */
public fun RestElement.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression
    = JSXEmptyExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> JSXElementImpl
 */
public fun RestElement.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> JSXFragmentImpl
 */
public fun RestElement.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> TsTypeAssertionImpl
 */
public fun RestElement.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> TsConstAssertionImpl
 */
public fun RestElement.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> TsNonNullExpressionImpl
 */
public fun RestElement.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> TsAsExpressionImpl
 */
public fun RestElement.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> TsSatisfiesExpressionImpl
 */
public fun RestElement.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> TsInstantiationImpl
 */
public fun RestElement.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> PrivateNameImpl
 */
public fun RestElement.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * RestElement#argument: Pattern
 * extension function for create Pattern -> OptionalChainingExpressionImpl
 */
public fun RestElement.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * RestElement#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun RestElement.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)
