// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:16.940916

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
import dev.yidafu.swc.generated.VariableDeclarator
import dev.yidafu.swc.generated.YieldExpression
import kotlin.Unit

/**
 * VariableDeclarator#id: Pattern?
 * extension function for create Pattern? -> BindingIdentifier
 */
public fun VariableDeclarator.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * VariableDeclarator#id: Pattern?
 * extension function for create Pattern? -> ArrayPattern
 */
public fun VariableDeclarator.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPattern().apply(block)

/**
 * VariableDeclarator#id: Pattern?
 * extension function for create Pattern? -> ObjectPattern
 */
public fun VariableDeclarator.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPattern().apply(block)

/**
 * VariableDeclarator#id: Pattern?
 * extension function for create Pattern? -> AssignmentPattern
 */
public fun VariableDeclarator.assignmentPattern(block: AssignmentPattern.() -> Unit):
    AssignmentPattern = AssignmentPattern().apply(block)

/**
 * VariableDeclarator#id: Pattern?
 * extension function for create Pattern? -> RestElement
 */
public fun VariableDeclarator.restElement(block: RestElement.() -> Unit): RestElement =
    RestElement().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> Invalid
 */
public fun VariableDeclarator.invalid(block: Invalid.() -> Unit): Invalid = Invalid().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> Identifier
 */
public fun VariableDeclarator.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> OptionalChainingExpression
 */
public
    fun VariableDeclarator.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> ThisExpression
 */
public fun VariableDeclarator.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> ArrayExpression
 */
public fun VariableDeclarator.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> ObjectExpression
 */
public fun VariableDeclarator.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression
    = ObjectExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> UnaryExpression
 */
public fun VariableDeclarator.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> UpdateExpression
 */
public fun VariableDeclarator.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression
    = UpdateExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> BinaryExpression
 */
public fun VariableDeclarator.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression
    = BinaryExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> FunctionExpression
 */
public fun VariableDeclarator.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> ClassExpression
 */
public fun VariableDeclarator.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> AssignmentExpression
 */
public fun VariableDeclarator.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> MemberExpression
 */
public fun VariableDeclarator.memberExpression(block: MemberExpression.() -> Unit): MemberExpression
    = MemberExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> SuperPropExpression
 */
public fun VariableDeclarator.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> ConditionalExpression
 */
public fun VariableDeclarator.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> CallExpression
 */
public fun VariableDeclarator.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> NewExpression
 */
public fun VariableDeclarator.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> SequenceExpression
 */
public fun VariableDeclarator.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> ArrowFunctionExpression
 */
public fun VariableDeclarator.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> YieldExpression
 */
public fun VariableDeclarator.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> MetaProperty
 */
public fun VariableDeclarator.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaProperty().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> AwaitExpression
 */
public fun VariableDeclarator.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> TemplateLiteral
 */
public fun VariableDeclarator.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> TaggedTemplateExpression
 */
public fun VariableDeclarator.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> ParenthesisExpression
 */
public fun VariableDeclarator.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> PrivateName
 */
public fun VariableDeclarator.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateName().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> JSXMemberExpression
 */
public fun VariableDeclarator.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> JSXNamespacedName
 */
public fun VariableDeclarator.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedName().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> JSXEmptyExpression
 */
public fun VariableDeclarator.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> JSXElement
 */
public fun VariableDeclarator.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElement().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> JSXFragment
 */
public fun VariableDeclarator.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragment().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> TsAsExpression
 */
public fun VariableDeclarator.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> TsSatisfiesExpression
 */
public fun VariableDeclarator.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> TsInstantiation
 */
public fun VariableDeclarator.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiation().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> TsTypeAssertion
 */
public fun VariableDeclarator.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertion().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> TsConstAssertion
 */
public fun VariableDeclarator.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion
    = TsConstAssertion().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> TsNonNullExpression
 */
public fun VariableDeclarator.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpression().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> JSXText
 */
public fun VariableDeclarator.jSXText(block: JSXText.() -> Unit): JSXText = JSXText().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> StringLiteral
 */
public fun VariableDeclarator.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> BooleanLiteral
 */
public fun VariableDeclarator.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteral().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> NullLiteral
 */
public fun VariableDeclarator.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteral().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> RegExpLiteral
 */
public fun VariableDeclarator.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteral().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> NumericLiteral
 */
public fun VariableDeclarator.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * VariableDeclarator#init: Expression?
 * extension function for create Expression? -> BigIntLiteral
 */
public fun VariableDeclarator.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)
