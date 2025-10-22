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
import dev.yidafu.swc.types.ComputedPropName
import dev.yidafu.swc.types.ComputedPropNameImpl
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
import dev.yidafu.swc.types.KeyValueProperty
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
 * KeyValueProperty#type: String
 * extension function for create String -> String
 */
public fun KeyValueProperty.string(block: String.() -> Unit): String = String().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun KeyValueProperty.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun KeyValueProperty.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun KeyValueProperty.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun KeyValueProperty.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun KeyValueProperty.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun KeyValueProperty.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun KeyValueProperty.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun KeyValueProperty.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun KeyValueProperty.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun KeyValueProperty.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun KeyValueProperty.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun KeyValueProperty.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun KeyValueProperty.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun KeyValueProperty.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * KeyValueProperty#key: PropertyName
 * extension function for create PropertyName -> IdentifierImpl
 */
public fun KeyValueProperty.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * KeyValueProperty#key: PropertyName
 * extension function for create PropertyName -> StringLiteralImpl
 */
public fun KeyValueProperty.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun KeyValueProperty.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun KeyValueProperty.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * KeyValueProperty#key: PropertyName
 * extension function for create PropertyName -> NumericLiteralImpl
 */
public fun KeyValueProperty.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * KeyValueProperty#key: PropertyName
 * extension function for create PropertyName -> BigIntLiteralImpl
 */
public fun KeyValueProperty.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun KeyValueProperty.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun KeyValueProperty.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun KeyValueProperty.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun KeyValueProperty.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun KeyValueProperty.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun KeyValueProperty.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun KeyValueProperty.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun KeyValueProperty.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun KeyValueProperty.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun KeyValueProperty.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun KeyValueProperty.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun KeyValueProperty.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun KeyValueProperty.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun KeyValueProperty.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun KeyValueProperty.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun KeyValueProperty.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun KeyValueProperty.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun KeyValueProperty.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun KeyValueProperty.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun KeyValueProperty.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun KeyValueProperty.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun KeyValueProperty.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public
    fun KeyValueProperty.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * KeyValueProperty#value: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun KeyValueProperty.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * KeyValueProperty#key: PropertyName
 * extension function for create PropertyName -> ComputedPropNameImpl
 */
public fun KeyValueProperty.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropNameImpl().apply(block)
