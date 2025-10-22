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
 * TsInstantiation#type: String
 * extension function for create String -> String
 */
public fun TsInstantiation.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun TsInstantiation.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun TsInstantiation.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun TsInstantiation.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun TsInstantiation.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun TsInstantiation.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun TsInstantiation.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun TsInstantiation.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun TsInstantiation.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun TsInstantiation.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun TsInstantiation.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun TsInstantiation.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun TsInstantiation.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun TsInstantiation.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun TsInstantiation.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun TsInstantiation.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun TsInstantiation.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun TsInstantiation.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun TsInstantiation.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun TsInstantiation.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun TsInstantiation.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun TsInstantiation.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun TsInstantiation.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun TsInstantiation.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun TsInstantiation.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun TsInstantiation.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun TsInstantiation.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun TsInstantiation.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun TsInstantiation.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun TsInstantiation.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun TsInstantiation.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun TsInstantiation.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun TsInstantiation.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName
    = JSXNamespacedNameImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun TsInstantiation.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun TsInstantiation.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun TsInstantiation.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun TsInstantiation.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun TsInstantiation.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun TsInstantiation.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun TsInstantiation.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun TsInstantiation.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun TsInstantiation.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public fun TsInstantiation.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * TsInstantiation#expression: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun TsInstantiation.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * TsInstantiation#typeArguments: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
public
    fun TsInstantiation.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * TsInstantiation#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsInstantiation.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
