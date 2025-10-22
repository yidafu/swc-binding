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
import dev.yidafu.swc.types.ClassDeclaration
import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassExpressionImpl
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
 * ClassDeclaration#type: String
 * extension function for create String -> String
 */
public fun ClassDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun ClassDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ClassDeclaration#isAbstract: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ClassDeclaration.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * ClassDeclaration#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> ConstructorImpl
 */
public fun ClassDeclaration.`constructor`(block: Constructor.() -> Unit): Constructor =
    ConstructorImpl().apply(block)

/**
 * ClassDeclaration#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> ClassMethodImpl
 */
public fun ClassDeclaration.classMethod(block: ClassMethod.() -> Unit): ClassMethod =
    ClassMethodImpl().apply(block)

/**
 * ClassDeclaration#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> PrivateMethodImpl
 */
public fun ClassDeclaration.privateMethod(block: PrivateMethod.() -> Unit): PrivateMethod =
    PrivateMethodImpl().apply(block)

/**
 * ClassDeclaration#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> ClassPropertyImpl
 */
public fun ClassDeclaration.classProperty(block: ClassProperty.() -> Unit): ClassProperty =
    ClassPropertyImpl().apply(block)

/**
 * ClassDeclaration#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> PrivatePropertyImpl
 */
public fun ClassDeclaration.privateProperty(block: PrivateProperty.() -> Unit): PrivateProperty =
    PrivatePropertyImpl().apply(block)

/**
 * ClassDeclaration#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> TsIndexSignatureImpl
 */
public fun ClassDeclaration.tsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature =
    TsIndexSignatureImpl().apply(block)

/**
 * ClassDeclaration#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> EmptyStatementImpl
 */
public fun ClassDeclaration.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * ClassDeclaration#body: Array<ClassMember>
 * extension function for create Array<ClassMember> -> StaticBlockImpl
 */
public fun ClassDeclaration.staticBlock(block: StaticBlock.() -> Unit): StaticBlock =
    StaticBlockImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun ClassDeclaration.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun ClassDeclaration.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun ClassDeclaration.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun ClassDeclaration.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun ClassDeclaration.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun ClassDeclaration.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun ClassDeclaration.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun ClassDeclaration.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun ClassDeclaration.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun ClassDeclaration.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun ClassDeclaration.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun ClassDeclaration.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun ClassDeclaration.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun ClassDeclaration.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun ClassDeclaration.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun ClassDeclaration.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun ClassDeclaration.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun ClassDeclaration.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun ClassDeclaration.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun ClassDeclaration.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun ClassDeclaration.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun ClassDeclaration.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun ClassDeclaration.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun ClassDeclaration.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun ClassDeclaration.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun ClassDeclaration.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun ClassDeclaration.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun ClassDeclaration.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun ClassDeclaration.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun ClassDeclaration.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun ClassDeclaration.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun ClassDeclaration.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun ClassDeclaration.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun ClassDeclaration.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun ClassDeclaration.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun ClassDeclaration.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun ClassDeclaration.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun ClassDeclaration.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun ClassDeclaration.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun ClassDeclaration.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun ClassDeclaration.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public
    fun ClassDeclaration.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * ClassDeclaration#superClass: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun ClassDeclaration.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * ClassDeclaration#typeParams: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public
    fun ClassDeclaration.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * ClassDeclaration#superTypeParams: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
public
    fun ClassDeclaration.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * ClassDeclaration#implements: Array<TsExpressionWithTypeArguments>
 * extension function for create Array<TsExpressionWithTypeArguments> ->
 * TsExpressionWithTypeArgumentsImpl
 */
public
    fun ClassDeclaration.tsExpressionWithTypeArguments(block: TsExpressionWithTypeArguments.() -> Unit):
    TsExpressionWithTypeArguments = TsExpressionWithTypeArgumentsImpl().apply(block)

/**
 * ClassDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ClassDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

/**
 * ClassDeclaration#decorators: Array<Decorator>
 * extension function for create Array<Decorator> -> DecoratorImpl
 */
public fun ClassDeclaration.decorator(block: Decorator.() -> Unit): Decorator =
    DecoratorImpl().apply(block)
