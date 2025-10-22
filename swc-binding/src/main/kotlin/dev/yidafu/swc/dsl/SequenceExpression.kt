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
 * SequenceExpression#type: String
 * extension function for create String -> String
 */
public fun SequenceExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ThisExpressionImpl
 */
public fun SequenceExpression.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ArrayExpressionImpl
 */
public fun SequenceExpression.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ObjectExpressionImpl
 */
public fun SequenceExpression.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression
    = ObjectExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> FunctionExpressionImpl
 */
public fun SequenceExpression.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> UnaryExpressionImpl
 */
public fun SequenceExpression.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> UpdateExpressionImpl
 */
public fun SequenceExpression.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression
    = UpdateExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> BinaryExpressionImpl
 */
public fun SequenceExpression.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression
    = BinaryExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> AssignmentExpressionImpl
 */
public fun SequenceExpression.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> MemberExpressionImpl
 */
public fun SequenceExpression.memberExpression(block: MemberExpression.() -> Unit): MemberExpression
    = MemberExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> SuperPropExpressionImpl
 */
public fun SequenceExpression.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ConditionalExpressionImpl
 */
public fun SequenceExpression.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> CallExpressionImpl
 */
public fun SequenceExpression.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> NewExpressionImpl
 */
public fun SequenceExpression.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> IdentifierImpl
 */
public fun SequenceExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> StringLiteralImpl
 */
public fun SequenceExpression.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> BooleanLiteralImpl
 */
public fun SequenceExpression.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> NullLiteralImpl
 */
public fun SequenceExpression.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> NumericLiteralImpl
 */
public fun SequenceExpression.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> BigIntLiteralImpl
 */
public fun SequenceExpression.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> RegExpLiteralImpl
 */
public fun SequenceExpression.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXTextImpl
 */
public fun SequenceExpression.jSXText(block: JSXText.() -> Unit): JSXText =
    JSXTextImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TemplateLiteralImpl
 */
public fun SequenceExpression.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TaggedTemplateExpressionImpl
 */
public fun SequenceExpression.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ArrowFunctionExpressionImpl
 */
public fun SequenceExpression.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ClassExpressionImpl
 */
public fun SequenceExpression.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> YieldExpressionImpl
 */
public fun SequenceExpression.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> MetaPropertyImpl
 */
public fun SequenceExpression.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> AwaitExpressionImpl
 */
public fun SequenceExpression.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ParenthesisExpressionImpl
 */
public fun SequenceExpression.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXMemberExpressionImpl
 */
public fun SequenceExpression.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXNamespacedNameImpl
 */
public fun SequenceExpression.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXEmptyExpressionImpl
 */
public fun SequenceExpression.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXElementImpl
 */
public fun SequenceExpression.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXFragmentImpl
 */
public fun SequenceExpression.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsTypeAssertionImpl
 */
public fun SequenceExpression.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsConstAssertionImpl
 */
public fun SequenceExpression.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion
    = TsConstAssertionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsNonNullExpressionImpl
 */
public fun SequenceExpression.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsAsExpressionImpl
 */
public fun SequenceExpression.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsSatisfiesExpressionImpl
 */
public fun SequenceExpression.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsInstantiationImpl
 */
public fun SequenceExpression.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> PrivateNameImpl
 */
public fun SequenceExpression.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> OptionalChainingExpressionImpl
 */
public
    fun SequenceExpression.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * SequenceExpression#expressions: Array<Expression>
 * extension function for create Array<Expression> -> InvalidImpl
 */
public fun SequenceExpression.invalid(block: Invalid.() -> Unit): Invalid =
    InvalidImpl().apply(block)

/**
 * SequenceExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun SequenceExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
