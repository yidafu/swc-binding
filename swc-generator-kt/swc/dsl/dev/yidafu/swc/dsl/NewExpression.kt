package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Argument
import dev.yidafu.swc.types.ArgumentImpl
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
 * NewExpression#type: String
 * extension function for create String -> String
 */
public fun NewExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun NewExpression.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun NewExpression.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun NewExpression.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun NewExpression.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun NewExpression.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun NewExpression.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun NewExpression.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun NewExpression.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun NewExpression.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun NewExpression.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun NewExpression.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun NewExpression.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun NewExpression.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun NewExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun NewExpression.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun NewExpression.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun NewExpression.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun NewExpression.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun NewExpression.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun NewExpression.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun NewExpression.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun NewExpression.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun NewExpression.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun NewExpression.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun NewExpression.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun NewExpression.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun NewExpression.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun NewExpression.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun NewExpression.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun NewExpression.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun NewExpression.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedNameImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun NewExpression.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun NewExpression.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun NewExpression.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun NewExpression.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun NewExpression.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun NewExpression.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun NewExpression.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun NewExpression.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun NewExpression.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun NewExpression.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public fun NewExpression.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * NewExpression#callee: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun NewExpression.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * NewExpression#arguments: Array<Argument>
 * extension function for create Array<Argument> -> ArgumentImpl
 */
public fun NewExpression.argument(block: Argument.() -> Unit): Argument =
    ArgumentImpl().apply(block)

/**
 * NewExpression#typeArguments: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
public
    fun NewExpression.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * NewExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun NewExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
