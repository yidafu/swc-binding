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
import dev.yidafu.swc.types.TemplateElement
import dev.yidafu.swc.types.TemplateElementImpl
import dev.yidafu.swc.types.TemplateLiteral
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
 * TemplateLiteral#type: String
 * extension function for create String -> String
 */
public fun TemplateLiteral.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ThisExpressionImpl
 */
public fun TemplateLiteral.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ArrayExpressionImpl
 */
public fun TemplateLiteral.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ObjectExpressionImpl
 */
public fun TemplateLiteral.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> FunctionExpressionImpl
 */
public fun TemplateLiteral.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> UnaryExpressionImpl
 */
public fun TemplateLiteral.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> UpdateExpressionImpl
 */
public fun TemplateLiteral.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> BinaryExpressionImpl
 */
public fun TemplateLiteral.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> AssignmentExpressionImpl
 */
public fun TemplateLiteral.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> MemberExpressionImpl
 */
public fun TemplateLiteral.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> SuperPropExpressionImpl
 */
public fun TemplateLiteral.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ConditionalExpressionImpl
 */
public fun TemplateLiteral.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> CallExpressionImpl
 */
public fun TemplateLiteral.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> NewExpressionImpl
 */
public fun TemplateLiteral.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> SequenceExpressionImpl
 */
public fun TemplateLiteral.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> IdentifierImpl
 */
public fun TemplateLiteral.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> StringLiteralImpl
 */
public fun TemplateLiteral.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> BooleanLiteralImpl
 */
public fun TemplateLiteral.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> NullLiteralImpl
 */
public fun TemplateLiteral.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> NumericLiteralImpl
 */
public fun TemplateLiteral.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> BigIntLiteralImpl
 */
public fun TemplateLiteral.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> RegExpLiteralImpl
 */
public fun TemplateLiteral.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXTextImpl
 */
public fun TemplateLiteral.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TaggedTemplateExpressionImpl
 */
public fun TemplateLiteral.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ArrowFunctionExpressionImpl
 */
public fun TemplateLiteral.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ClassExpressionImpl
 */
public fun TemplateLiteral.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> YieldExpressionImpl
 */
public fun TemplateLiteral.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> MetaPropertyImpl
 */
public fun TemplateLiteral.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> AwaitExpressionImpl
 */
public fun TemplateLiteral.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> ParenthesisExpressionImpl
 */
public fun TemplateLiteral.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXMemberExpressionImpl
 */
public fun TemplateLiteral.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXNamespacedNameImpl
 */
public fun TemplateLiteral.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName
    = JSXNamespacedNameImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXEmptyExpressionImpl
 */
public fun TemplateLiteral.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXElementImpl
 */
public fun TemplateLiteral.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> JSXFragmentImpl
 */
public fun TemplateLiteral.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsTypeAssertionImpl
 */
public fun TemplateLiteral.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsConstAssertionImpl
 */
public fun TemplateLiteral.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsNonNullExpressionImpl
 */
public fun TemplateLiteral.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsAsExpressionImpl
 */
public fun TemplateLiteral.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsSatisfiesExpressionImpl
 */
public fun TemplateLiteral.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> TsInstantiationImpl
 */
public fun TemplateLiteral.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> PrivateNameImpl
 */
public fun TemplateLiteral.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> OptionalChainingExpressionImpl
 */
public fun TemplateLiteral.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * TemplateLiteral#expressions: Array<Expression>
 * extension function for create Array<Expression> -> InvalidImpl
 */
public fun TemplateLiteral.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * TemplateLiteral#quasis: Array<TemplateElement>
 * extension function for create Array<TemplateElement> -> TemplateElementImpl
 */
public fun TemplateLiteral.templateElement(block: TemplateElement.() -> Unit): TemplateElement =
    TemplateElementImpl().apply(block)

/**
 * TemplateLiteral#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TemplateLiteral.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
