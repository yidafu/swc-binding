// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.306703

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
import dev.yidafu.swc.generated.BlockStatement
import dev.yidafu.swc.generated.BlockStatementImpl
import dev.yidafu.swc.generated.BooleanLiteral
import dev.yidafu.swc.generated.CallExpression
import dev.yidafu.swc.generated.CatchClause
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
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> ArrayPattern
 */
public fun CatchClause.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPattern().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> ObjectPattern
 */
public fun CatchClause.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPattern().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> AssignmentPattern
 */
public fun CatchClause.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern =
    AssignmentPattern().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> RestElement
 */
public fun CatchClause.restElement(block: RestElement.() -> Unit): RestElement =
    RestElement().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> Invalid
 */
public fun CatchClause.invalid(block: Invalid.() -> Unit): Invalid = Invalid().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> OptionalChainingExpression
 */
public fun CatchClause.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> ThisExpression
 */
public fun CatchClause.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> ArrayExpression
 */
public fun CatchClause.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> ObjectExpression
 */
public fun CatchClause.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> UnaryExpression
 */
public fun CatchClause.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> UpdateExpression
 */
public fun CatchClause.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> BinaryExpression
 */
public fun CatchClause.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> FunctionExpression
 */
public fun CatchClause.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression
    = FunctionExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> ClassExpression
 */
public fun CatchClause.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> AssignmentExpression
 */
public fun CatchClause.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> MemberExpression
 */
public fun CatchClause.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> SuperPropExpression
 */
public fun CatchClause.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> ConditionalExpression
 */
public fun CatchClause.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> CallExpression
 */
public fun CatchClause.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> NewExpression
 */
public fun CatchClause.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> SequenceExpression
 */
public fun CatchClause.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression
    = SequenceExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> ArrowFunctionExpression
 */
public fun CatchClause.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> YieldExpression
 */
public fun CatchClause.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> MetaProperty
 */
public fun CatchClause.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaProperty().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> AwaitExpression
 */
public fun CatchClause.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> TaggedTemplateExpression
 */
public fun CatchClause.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> ParenthesisExpression
 */
public fun CatchClause.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> PrivateName
 */
public fun CatchClause.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateName().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> JSXMemberExpression
 */
public fun CatchClause.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> JSXNamespacedName
 */
public fun CatchClause.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedName().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> JSXEmptyExpression
 */
public fun CatchClause.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression
    = JSXEmptyExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> JSXElement
 */
public fun CatchClause.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElement().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> JSXFragment
 */
public fun CatchClause.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragment().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> TsAsExpression
 */
public fun CatchClause.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> TsSatisfiesExpression
 */
public fun CatchClause.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> TsInstantiation
 */
public fun CatchClause.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiation().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> TsTypeAssertion
 */
public fun CatchClause.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertion().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> TsConstAssertion
 */
public fun CatchClause.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertion().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> TsNonNullExpression
 */
public fun CatchClause.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpression().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> JSXText
 */
public fun CatchClause.jSXText(block: JSXText.() -> Unit): JSXText = JSXText().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> StringLiteral
 */
public fun CatchClause.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> BooleanLiteral
 */
public fun CatchClause.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteral().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> NullLiteral
 */
public fun CatchClause.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteral().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> RegExpLiteral
 */
public fun CatchClause.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteral().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> NumericLiteral
 */
public fun CatchClause.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> BigIntLiteral
 */
public fun CatchClause.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> BindingIdentifier
 */
public fun CatchClause.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier =
    BindingIdentifierImpl().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> Identifier
 */
public fun CatchClause.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * CatchClause#param: Pattern?
 * extension function for create Pattern? -> TemplateLiteral
 */
public fun CatchClause.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * CatchClause#body: BlockStatement?
 * extension function for create BlockStatement? -> BlockStatement
 */
public fun CatchClause.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)
