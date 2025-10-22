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
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.CallExpression
import dev.yidafu.swc.types.CallExpressionImpl
import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassMethod
import dev.yidafu.swc.types.ClassMethodImpl
import dev.yidafu.swc.types.ClassProperty
import dev.yidafu.swc.types.ClassPropertyImpl
import dev.yidafu.swc.types.ConditionalExpression
import dev.yidafu.swc.types.ConditionalExpressionImpl
import dev.yidafu.swc.types.Constructor
import dev.yidafu.swc.types.ConstructorImpl
import dev.yidafu.swc.types.Decorator
import dev.yidafu.swc.types.DecoratorImpl
import dev.yidafu.swc.types.EmptyStatement
import dev.yidafu.swc.types.EmptyStatementImpl
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
import dev.yidafu.swc.types.PrivateMethod
import dev.yidafu.swc.types.PrivateMethodImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.PrivateProperty
import dev.yidafu.swc.types.PrivatePropertyImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
import dev.yidafu.swc.types.SequenceExpression
import dev.yidafu.swc.types.SequenceExpressionImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.StaticBlock
import dev.yidafu.swc.types.StaticBlockImpl
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
import dev.yidafu.swc.types.TsExpressionWithTypeArguments
import dev.yidafu.swc.types.TsExpressionWithTypeArgumentsImpl
import dev.yidafu.swc.types.TsIndexSignature
import dev.yidafu.swc.types.TsIndexSignatureImpl
import dev.yidafu.swc.types.TsInstantiation
import dev.yidafu.swc.types.TsInstantiationImpl
import dev.yidafu.swc.types.TsNonNullExpression
import dev.yidafu.swc.types.TsNonNullExpressionImpl
import dev.yidafu.swc.types.TsSatisfiesExpression
import dev.yidafu.swc.types.TsSatisfiesExpressionImpl
import dev.yidafu.swc.types.TsTypeAssertion
import dev.yidafu.swc.types.TsTypeAssertionImpl
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclarationImpl
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
 * ClassExpression#type: String
 * extension function for create String -> String
 */
public fun ClassExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun ClassExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ClassExpression#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> ConstructorImpl
 */
public fun ClassExpression.`constructor`(block: Constructor.() -> Unit): Constructor =
    ConstructorImpl().apply(block)

/**
 * ClassExpression#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> ClassMethodImpl
 */
public fun ClassExpression.classMethod(block: ClassMethod.() -> Unit): ClassMethod =
    ClassMethodImpl().apply(block)

/**
 * ClassExpression#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> PrivateMethodImpl
 */
public fun ClassExpression.privateMethod(block: PrivateMethod.() -> Unit): PrivateMethod =
    PrivateMethodImpl().apply(block)

/**
 * ClassExpression#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> ClassPropertyImpl
 */
public fun ClassExpression.classProperty(block: ClassProperty.() -> Unit): ClassProperty =
    ClassPropertyImpl().apply(block)

/**
 * ClassExpression#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> PrivatePropertyImpl
 */
public fun ClassExpression.privateProperty(block: PrivateProperty.() -> Unit): PrivateProperty =
    PrivatePropertyImpl().apply(block)

/**
 * ClassExpression#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> TsIndexSignatureImpl
 */
public fun ClassExpression.tsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature =
    TsIndexSignatureImpl().apply(block)

/**
 * ClassExpression#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> EmptyStatementImpl
 */
public fun ClassExpression.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * ClassExpression#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> StaticBlockImpl
 */
public fun ClassExpression.staticBlock(block: StaticBlock.() -> Unit): StaticBlock =
    StaticBlockImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun ClassExpression.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun ClassExpression.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun ClassExpression.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun ClassExpression.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun ClassExpression.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun ClassExpression.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun ClassExpression.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun ClassExpression.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun ClassExpression.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun ClassExpression.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun ClassExpression.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun ClassExpression.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun ClassExpression.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun ClassExpression.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun ClassExpression.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun ClassExpression.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun ClassExpression.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun ClassExpression.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun ClassExpression.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun ClassExpression.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun ClassExpression.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun ClassExpression.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun ClassExpression.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun ClassExpression.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun ClassExpression.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun ClassExpression.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun ClassExpression.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun ClassExpression.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun ClassExpression.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun ClassExpression.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName
    = JSXNamespacedNameImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun ClassExpression.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun ClassExpression.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun ClassExpression.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun ClassExpression.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun ClassExpression.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun ClassExpression.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun ClassExpression.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun ClassExpression.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun ClassExpression.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun ClassExpression.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public fun ClassExpression.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * ClassExpression#superClass: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun ClassExpression.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * ClassExpression#isAbstract: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ClassExpression.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * ClassExpression#typeParams: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public fun ClassExpression.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * ClassExpression#superTypeParams: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
public
    fun ClassExpression.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * ClassExpression#implements: Array<TsExpressionWithTypeArguments>
 * extension function for create Array<TsExpressionWithTypeArguments> ->
 * TsExpressionWithTypeArgumentsImpl
 */
public
    fun ClassExpression.tsExpressionWithTypeArguments(block: TsExpressionWithTypeArguments.() -> Unit):
    TsExpressionWithTypeArguments = TsExpressionWithTypeArgumentsImpl().apply(block)

/**
 * ClassExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ClassExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * ClassExpression#decorators: Array<Decorator>
 * extension function for create Array<Decorator> -> DecoratorImpl
 */
public fun ClassExpression.decorator(block: Decorator.() -> Unit): Decorator =
    DecoratorImpl().apply(block)
