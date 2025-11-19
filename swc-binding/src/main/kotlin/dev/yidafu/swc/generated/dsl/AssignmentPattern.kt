// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.279328

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ArrayExpression
import dev.yidafu.swc.generated.ArrayPattern
import dev.yidafu.swc.generated.ArrowFunctionExpression
import dev.yidafu.swc.generated.AssignmentExpression
import dev.yidafu.swc.generated.AssignmentPattern
import dev.yidafu.swc.generated.AwaitExpression
import dev.yidafu.swc.generated.BigIntLiteral
import dev.yidafu.swc.generated.BinaryExpression
import dev.yidafu.swc.generated.BindingIdentifier
import dev.yidafu.swc.generated.BindingIdentifierImpl
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
import dev.yidafu.swc.generated.ObjectPattern
import dev.yidafu.swc.generated.OptionalChainingExpression
import dev.yidafu.swc.generated.ParenthesisExpression
import dev.yidafu.swc.generated.PrivateName
import dev.yidafu.swc.generated.RegExpLiteral
import dev.yidafu.swc.generated.RestElement
import dev.yidafu.swc.generated.SequenceExpression
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
 * AssignmentPattern#left: Pattern?
 * extension function for create Pattern? -> ArrayPattern
 */
public fun AssignmentPattern.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPattern().apply(block)

/**
 * AssignmentPattern#left: Pattern?
 * extension function for create Pattern? -> ObjectPattern
 */
public fun AssignmentPattern.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPattern().apply(block)

/**
 * AssignmentPattern#left: Pattern?
 * extension function for create Pattern? -> RestElement
 */
public fun AssignmentPattern.restElement(block: RestElement.() -> Unit): RestElement =
    RestElement().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> Invalid
 */
public fun AssignmentPattern.invalid(block: Invalid.() -> Unit): Invalid = Invalid().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> OptionalChainingExpression
 */
public
    fun AssignmentPattern.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> ThisExpression
 */
public fun AssignmentPattern.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> ArrayExpression
 */
public fun AssignmentPattern.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> ObjectExpression
 */
public fun AssignmentPattern.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression
    = ObjectExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> UnaryExpression
 */
public fun AssignmentPattern.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> UpdateExpression
 */
public fun AssignmentPattern.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression
    = UpdateExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> BinaryExpression
 */
public fun AssignmentPattern.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression
    = BinaryExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> FunctionExpression
 */
public fun AssignmentPattern.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> ClassExpression
 */
public fun AssignmentPattern.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> AssignmentExpression
 */
public fun AssignmentPattern.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> MemberExpression
 */
public fun AssignmentPattern.memberExpression(block: MemberExpression.() -> Unit): MemberExpression
    = MemberExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> SuperPropExpression
 */
public fun AssignmentPattern.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> ConditionalExpression
 */
public fun AssignmentPattern.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> CallExpression
 */
public fun AssignmentPattern.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> NewExpression
 */
public fun AssignmentPattern.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> SequenceExpression
 */
public fun AssignmentPattern.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> ArrowFunctionExpression
 */
public fun AssignmentPattern.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> YieldExpression
 */
public fun AssignmentPattern.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> MetaProperty
 */
public fun AssignmentPattern.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaProperty().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> AwaitExpression
 */
public fun AssignmentPattern.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> TaggedTemplateExpression
 */
public fun AssignmentPattern.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> ParenthesisExpression
 */
public fun AssignmentPattern.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> PrivateName
 */
public fun AssignmentPattern.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateName().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> JSXMemberExpression
 */
public fun AssignmentPattern.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> JSXNamespacedName
 */
public fun AssignmentPattern.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedName().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> JSXEmptyExpression
 */
public fun AssignmentPattern.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> JSXElement
 */
public fun AssignmentPattern.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElement().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> JSXFragment
 */
public fun AssignmentPattern.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragment().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> TsAsExpression
 */
public fun AssignmentPattern.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> TsSatisfiesExpression
 */
public fun AssignmentPattern.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> TsInstantiation
 */
public fun AssignmentPattern.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiation().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> TsTypeAssertion
 */
public fun AssignmentPattern.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertion().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> TsConstAssertion
 */
public fun AssignmentPattern.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion
    = TsConstAssertion().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> TsNonNullExpression
 */
public fun AssignmentPattern.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpression().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> JSXText
 */
public fun AssignmentPattern.jSXText(block: JSXText.() -> Unit): JSXText = JSXText().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> StringLiteral
 */
public fun AssignmentPattern.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> BooleanLiteral
 */
public fun AssignmentPattern.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteral().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> NullLiteral
 */
public fun AssignmentPattern.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteral().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> RegExpLiteral
 */
public fun AssignmentPattern.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteral().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> NumericLiteral
 */
public fun AssignmentPattern.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> BigIntLiteral
 */
public fun AssignmentPattern.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)

/**
 * AssignmentPattern#left: Pattern?
 * extension function for create Pattern? -> BindingIdentifier
 */
public fun AssignmentPattern.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> Identifier
 */
public fun AssignmentPattern.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * AssignmentPattern#right: Expression?
 * extension function for create Expression? -> TemplateLiteral
 */
public fun AssignmentPattern.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)
