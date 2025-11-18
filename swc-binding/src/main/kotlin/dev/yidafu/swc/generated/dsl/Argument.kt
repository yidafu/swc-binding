// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:16.933993

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Argument
import dev.yidafu.swc.generated.ArrayExpression
import dev.yidafu.swc.generated.ArrowFunctionExpression
import dev.yidafu.swc.generated.AssignmentExpression
import dev.yidafu.swc.generated.AwaitExpression
import dev.yidafu.swc.generated.BigIntLiteral
import dev.yidafu.swc.generated.BinaryExpression
import dev.yidafu.swc.generated.BooleanLiteral
import dev.yidafu.swc.generated.CallExpression
import dev.yidafu.swc.generated.ClassExpression
import dev.yidafu.swc.generated.ConditionalExpression
import dev.yidafu.swc.generated.FunctionExpression
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.Invalid
import dev.yidafu.swc.generated.JSXElement
import dev.yidafu.swc.generated.JSXEmptyExpression
import dev.yidafu.swc.generated.JSXFragment
import dev.yidafu.swc.generated.JSXMemberExpression
import dev.yidafu.swc.generated.JSXNamespacedName
import dev.yidafu.swc.generated.JSXText
import dev.yidafu.swc.generated.MemberExpression
import dev.yidafu.swc.generated.MetaProperty
import dev.yidafu.swc.generated.NewExpression
import dev.yidafu.swc.generated.NullLiteral
import dev.yidafu.swc.generated.NumericLiteral
import dev.yidafu.swc.generated.ObjectExpression
import dev.yidafu.swc.generated.OptionalChainingExpression
import dev.yidafu.swc.generated.ParenthesisExpression
import dev.yidafu.swc.generated.PrivateName
import dev.yidafu.swc.generated.RegExpLiteral
import dev.yidafu.swc.generated.SequenceExpression
import dev.yidafu.swc.generated.Span
import dev.yidafu.swc.generated.StringLiteral
import dev.yidafu.swc.generated.SuperPropExpression
import dev.yidafu.swc.generated.TaggedTemplateExpression
import dev.yidafu.swc.generated.TemplateLiteral
import dev.yidafu.swc.generated.TemplateLiteralImpl
import dev.yidafu.swc.generated.ThisExpression
import dev.yidafu.swc.generated.TsAsExpression
import dev.yidafu.swc.generated.TsConstAssertion
import dev.yidafu.swc.generated.TsInstantiation
import dev.yidafu.swc.generated.TsNonNullExpression
import dev.yidafu.swc.generated.TsSatisfiesExpression
import dev.yidafu.swc.generated.TsTypeAssertion
import dev.yidafu.swc.generated.UnaryExpression
import dev.yidafu.swc.generated.UpdateExpression
import dev.yidafu.swc.generated.YieldExpression
import kotlin.Unit

/**
 * Argument#spread: Span?
 * extension function for create Span? -> Span
 */
public fun Argument.span(block: Span.() -> Unit): Span = Span().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> Identifier
 */
public fun Argument.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> OptionalChainingExpression
 */
public fun Argument.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> ThisExpression
 */
public fun Argument.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> ArrayExpression
 */
public fun Argument.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> ObjectExpression
 */
public fun Argument.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> UnaryExpression
 */
public fun Argument.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> UpdateExpression
 */
public fun Argument.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> BinaryExpression
 */
public fun Argument.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> FunctionExpression
 */
public fun Argument.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression =
    FunctionExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> ClassExpression
 */
public fun Argument.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> AssignmentExpression
 */
public fun Argument.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> MemberExpression
 */
public fun Argument.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> SuperPropExpression
 */
public fun Argument.superPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression
    = SuperPropExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> ConditionalExpression
 */
public fun Argument.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> CallExpression
 */
public fun Argument.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> NewExpression
 */
public fun Argument.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> SequenceExpression
 */
public fun Argument.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression =
    SequenceExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> ArrowFunctionExpression
 */
public fun Argument.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> YieldExpression
 */
public fun Argument.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> MetaProperty
 */
public fun Argument.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaProperty().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> AwaitExpression
 */
public fun Argument.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> TemplateLiteral
 */
public fun Argument.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> TaggedTemplateExpression
 */
public fun Argument.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> ParenthesisExpression
 */
public fun Argument.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> PrivateName
 */
public fun Argument.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateName().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> JSXMemberExpression
 */
public fun Argument.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression
    = JSXMemberExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> JSXNamespacedName
 */
public fun Argument.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedName().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> JSXEmptyExpression
 */
public fun Argument.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression =
    JSXEmptyExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> JSXElement
 */
public fun Argument.jSXElement(block: JSXElement.() -> Unit): JSXElement = JSXElement().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> JSXFragment
 */
public fun Argument.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragment().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> TsAsExpression
 */
public fun Argument.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> TsSatisfiesExpression
 */
public fun Argument.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> TsInstantiation
 */
public fun Argument.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiation().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> TsTypeAssertion
 */
public fun Argument.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertion().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> TsConstAssertion
 */
public fun Argument.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertion().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> TsNonNullExpression
 */
public fun Argument.tsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression
    = TsNonNullExpression().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> Invalid
 */
public fun Argument.invalid(block: Invalid.() -> Unit): Invalid = Invalid().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> JSXText
 */
public fun Argument.jSXText(block: JSXText.() -> Unit): JSXText = JSXText().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> StringLiteral
 */
public fun Argument.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> BooleanLiteral
 */
public fun Argument.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteral().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> NullLiteral
 */
public fun Argument.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteral().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> RegExpLiteral
 */
public fun Argument.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteral().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> NumericLiteral
 */
public fun Argument.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * Argument#expression: Expression?
 * extension function for create Expression? -> BigIntLiteral
 */
public fun Argument.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)
