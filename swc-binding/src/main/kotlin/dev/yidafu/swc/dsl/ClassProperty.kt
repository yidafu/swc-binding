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
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.CallExpression
import dev.yidafu.swc.types.CallExpressionImpl
import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassExpressionImpl
import dev.yidafu.swc.types.ClassProperty
import dev.yidafu.swc.types.ComputedPropName
import dev.yidafu.swc.types.ComputedPropNameImpl
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
 * ClassProperty#accessibility: String
 * extension function for create String -> String
 */
public fun ClassProperty.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun ClassProperty.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun ClassProperty.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun ClassProperty.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * ClassProperty#key: PropertyName
 * extension function for create PropertyName -> ComputedPropNameImpl
 */
public fun ClassProperty.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropNameImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun ClassProperty.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * ClassProperty#definite: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ClassProperty.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun ClassProperty.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun ClassProperty.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun ClassProperty.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun ClassProperty.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun ClassProperty.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun ClassProperty.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun ClassProperty.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun ClassProperty.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun ClassProperty.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun ClassProperty.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun ClassProperty.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun ClassProperty.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun ClassProperty.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun ClassProperty.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun ClassProperty.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun ClassProperty.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun ClassProperty.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun ClassProperty.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun ClassProperty.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun ClassProperty.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun ClassProperty.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun ClassProperty.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun ClassProperty.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun ClassProperty.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun ClassProperty.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun ClassProperty.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun ClassProperty.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun ClassProperty.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedNameImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun ClassProperty.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun ClassProperty.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun ClassProperty.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun ClassProperty.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun ClassProperty.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun ClassProperty.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun ClassProperty.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun ClassProperty.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun ClassProperty.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun ClassProperty.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public fun ClassProperty.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * ClassProperty#value: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun ClassProperty.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * ClassProperty#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun ClassProperty.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

/**
 * ClassProperty#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ClassProperty.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * ClassProperty#decorators: Array<Decorator>
 * extension function for create Array<Decorator> -> DecoratorImpl
 */
public fun ClassProperty.decorator(block: Decorator.() -> Unit): Decorator =
    DecoratorImpl().apply(block)
