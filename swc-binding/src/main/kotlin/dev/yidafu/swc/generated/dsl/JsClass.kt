// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.123817

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ArrayExpression
import dev.yidafu.swc.generated.ArrowFunctionExpression
import dev.yidafu.swc.generated.AssignmentExpression
import dev.yidafu.swc.generated.AwaitExpression
import dev.yidafu.swc.generated.BigIntLiteral
import dev.yidafu.swc.generated.BinaryExpression
import dev.yidafu.swc.generated.BooleanLiteral
import dev.yidafu.swc.generated.CallExpression
import dev.yidafu.swc.generated.ClassExpression
import dev.yidafu.swc.generated.ClassMethod
import dev.yidafu.swc.generated.ClassProperty
import dev.yidafu.swc.generated.ConditionalExpression
import dev.yidafu.swc.generated.Constructor
import dev.yidafu.swc.generated.EmptyStatement
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
import dev.yidafu.swc.generated.JsClass
import dev.yidafu.swc.generated.MemberExpression
import dev.yidafu.swc.generated.MetaProperty
import dev.yidafu.swc.generated.NewExpression
import dev.yidafu.swc.generated.NullLiteral
import dev.yidafu.swc.generated.NumericLiteral
import dev.yidafu.swc.generated.ObjectExpression
import dev.yidafu.swc.generated.OptionalChainingExpression
import dev.yidafu.swc.generated.ParenthesisExpression
import dev.yidafu.swc.generated.PrivateMethod
import dev.yidafu.swc.generated.PrivateName
import dev.yidafu.swc.generated.PrivateProperty
import dev.yidafu.swc.generated.RegExpLiteral
import dev.yidafu.swc.generated.SequenceExpression
import dev.yidafu.swc.generated.StaticBlock
import dev.yidafu.swc.generated.StringLiteral
import dev.yidafu.swc.generated.SuperPropExpression
import dev.yidafu.swc.generated.TaggedTemplateExpression
import dev.yidafu.swc.generated.TemplateLiteral
import dev.yidafu.swc.generated.TemplateLiteralImpl
import dev.yidafu.swc.generated.ThisExpression
import dev.yidafu.swc.generated.TsAsExpression
import dev.yidafu.swc.generated.TsConstAssertion
import dev.yidafu.swc.generated.TsExpressionWithTypeArguments
import dev.yidafu.swc.generated.TsIndexSignature
import dev.yidafu.swc.generated.TsInstantiation
import dev.yidafu.swc.generated.TsNonNullExpression
import dev.yidafu.swc.generated.TsSatisfiesExpression
import dev.yidafu.swc.generated.TsTypeAssertion
import dev.yidafu.swc.generated.TsTypeParameterDeclaration
import dev.yidafu.swc.generated.TsTypeParameterInstantiation
import dev.yidafu.swc.generated.UnaryExpression
import dev.yidafu.swc.generated.UpdateExpression
import dev.yidafu.swc.generated.YieldExpression
import kotlin.Unit

/**
 * JsClass#body: Array<ClassMember>?
 * extension function for create Array<ClassMember>? -> ClassProperty
 */
public fun JsClass.classProperty(block: ClassProperty.() -> Unit): ClassProperty =
    ClassProperty().apply(block)

/**
 * JsClass#body: Array<ClassMember>?
 * extension function for create Array<ClassMember>? -> PrivateProperty
 */
public fun JsClass.privateProperty(block: PrivateProperty.() -> Unit): PrivateProperty =
    PrivateProperty().apply(block)

/**
 * JsClass#body: Array<ClassMember>?
 * extension function for create Array<ClassMember>? -> Constructor
 */
public fun JsClass.`constructor`(block: Constructor.() -> Unit): Constructor =
    Constructor().apply(block)

/**
 * JsClass#body: Array<ClassMember>?
 * extension function for create Array<ClassMember>? -> ClassMethod
 */
public fun JsClass.classMethod(block: ClassMethod.() -> Unit): ClassMethod =
    ClassMethod().apply(block)

/**
 * JsClass#body: Array<ClassMember>?
 * extension function for create Array<ClassMember>? -> PrivateMethod
 */
public fun JsClass.privateMethod(block: PrivateMethod.() -> Unit): PrivateMethod =
    PrivateMethod().apply(block)

/**
 * JsClass#body: Array<ClassMember>?
 * extension function for create Array<ClassMember>? -> StaticBlock
 */
public fun JsClass.staticBlock(block: StaticBlock.() -> Unit): StaticBlock =
    StaticBlock().apply(block)

/**
 * JsClass#body: Array<ClassMember>?
 * extension function for create Array<ClassMember>? -> EmptyStatement
 */
public fun JsClass.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatement().apply(block)

/**
 * JsClass#body: Array<ClassMember>?
 * extension function for create Array<ClassMember>? -> TsIndexSignature
 */
public fun JsClass.tsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature =
    TsIndexSignature().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> OptionalChainingExpression
 */
public fun JsClass.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> ThisExpression
 */
public fun JsClass.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> ArrayExpression
 */
public fun JsClass.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> ObjectExpression
 */
public fun JsClass.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> UnaryExpression
 */
public fun JsClass.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> UpdateExpression
 */
public fun JsClass.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> BinaryExpression
 */
public fun JsClass.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> FunctionExpression
 */
public fun JsClass.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression =
    FunctionExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> ClassExpression
 */
public fun JsClass.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> AssignmentExpression
 */
public fun JsClass.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> MemberExpression
 */
public fun JsClass.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> SuperPropExpression
 */
public fun JsClass.superPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression =
    SuperPropExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> ConditionalExpression
 */
public fun JsClass.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> CallExpression
 */
public fun JsClass.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> NewExpression
 */
public fun JsClass.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> SequenceExpression
 */
public fun JsClass.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression =
    SequenceExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> ArrowFunctionExpression
 */
public fun JsClass.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> YieldExpression
 */
public fun JsClass.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> MetaProperty
 */
public fun JsClass.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaProperty().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> AwaitExpression
 */
public fun JsClass.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> TaggedTemplateExpression
 */
public fun JsClass.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> ParenthesisExpression
 */
public fun JsClass.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> PrivateName
 */
public fun JsClass.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateName().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> JSXMemberExpression
 */
public fun JsClass.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression =
    JSXMemberExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> JSXNamespacedName
 */
public fun JsClass.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedName().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> JSXEmptyExpression
 */
public fun JsClass.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression =
    JSXEmptyExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> JSXElement
 */
public fun JsClass.jSXElement(block: JSXElement.() -> Unit): JSXElement = JSXElement().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> JSXFragment
 */
public fun JsClass.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragment().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> TsAsExpression
 */
public fun JsClass.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> TsSatisfiesExpression
 */
public fun JsClass.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> TsInstantiation
 */
public fun JsClass.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiation().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> TsTypeAssertion
 */
public fun JsClass.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertion().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> TsConstAssertion
 */
public fun JsClass.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertion().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> TsNonNullExpression
 */
public fun JsClass.tsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression =
    TsNonNullExpression().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> Invalid
 */
public fun JsClass.invalid(block: Invalid.() -> Unit): Invalid = Invalid().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> JSXText
 */
public fun JsClass.jSXText(block: JSXText.() -> Unit): JSXText = JSXText().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> StringLiteral
 */
public fun JsClass.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> BooleanLiteral
 */
public fun JsClass.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteral().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> NullLiteral
 */
public fun JsClass.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteral().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> RegExpLiteral
 */
public fun JsClass.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteral().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> NumericLiteral
 */
public fun JsClass.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> BigIntLiteral
 */
public fun JsClass.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> Identifier
 */
public fun JsClass.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * JsClass#superClass: Expression?
 * extension function for create Expression? -> TemplateLiteral
 */
public fun JsClass.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * JsClass#typeParams: TsTypeParameterDeclaration?
 * extension function for create TsTypeParameterDeclaration? -> TsTypeParameterDeclaration
 */
public fun JsClass.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclaration().apply(block)

/**
 * JsClass#superTypeParams: TsTypeParameterInstantiation?
 * extension function for create TsTypeParameterInstantiation? -> TsTypeParameterInstantiation
 */
public fun JsClass.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiation().apply(block)

/**
 * JsClass#implements: Array<TsExpressionWithTypeArguments>?
 * extension function for create Array<TsExpressionWithTypeArguments>? ->
 * TsExpressionWithTypeArguments
 */
public fun JsClass.tsExpressionWithTypeArguments(block: TsExpressionWithTypeArguments.() -> Unit):
    TsExpressionWithTypeArguments = TsExpressionWithTypeArguments().apply(block)
