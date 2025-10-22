package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
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
import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.BindingIdentifierImpl
import dev.yidafu.swc.types.Boolean
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
import dev.yidafu.swc.types.ObjectPattern
import dev.yidafu.swc.types.ObjectPatternImpl
import dev.yidafu.swc.types.OptionalChainingExpression
import dev.yidafu.swc.types.OptionalChainingExpressionImpl
import dev.yidafu.swc.types.ParenthesisExpression
import dev.yidafu.swc.types.ParenthesisExpressionImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
import dev.yidafu.swc.types.RestElement
import dev.yidafu.swc.types.RestElementImpl
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
import dev.yidafu.swc.types.TsMethodSignature
import dev.yidafu.swc.types.TsNonNullExpression
import dev.yidafu.swc.types.TsNonNullExpressionImpl
import dev.yidafu.swc.types.TsSatisfiesExpression
import dev.yidafu.swc.types.TsSatisfiesExpressionImpl
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import dev.yidafu.swc.types.TsTypeAssertion
import dev.yidafu.swc.types.TsTypeAssertionImpl
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclarationImpl
import dev.yidafu.swc.types.UnaryExpression
import dev.yidafu.swc.types.UnaryExpressionImpl
import dev.yidafu.swc.types.UpdateExpression
import dev.yidafu.swc.types.UpdateExpressionImpl
import dev.yidafu.swc.types.YieldExpression
import dev.yidafu.swc.types.YieldExpressionImpl
import kotlin.Unit

/**
 * TsMethodSignature#type: String
 * extension function for create String -> String
 */
public fun TsMethodSignature.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsMethodSignature#optional: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsMethodSignature.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun TsMethodSignature.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun TsMethodSignature.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun TsMethodSignature.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression
    = ObjectExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun TsMethodSignature.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun TsMethodSignature.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun TsMethodSignature.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression
    = UpdateExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun TsMethodSignature.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression
    = BinaryExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun TsMethodSignature.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun TsMethodSignature.memberExpression(block: MemberExpression.() -> Unit): MemberExpression
    = MemberExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun TsMethodSignature.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun TsMethodSignature.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun TsMethodSignature.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun TsMethodSignature.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun TsMethodSignature.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun TsMethodSignature.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun TsMethodSignature.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun TsMethodSignature.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun TsMethodSignature.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun TsMethodSignature.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun TsMethodSignature.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun TsMethodSignature.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun TsMethodSignature.jSXText(block: JSXText.() -> Unit): JSXText =
    JSXTextImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun TsMethodSignature.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun TsMethodSignature.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun TsMethodSignature.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun TsMethodSignature.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun TsMethodSignature.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun TsMethodSignature.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun TsMethodSignature.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun TsMethodSignature.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun TsMethodSignature.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun TsMethodSignature.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun TsMethodSignature.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun TsMethodSignature.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun TsMethodSignature.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun TsMethodSignature.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun TsMethodSignature.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion
    = TsConstAssertionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun TsMethodSignature.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun TsMethodSignature.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun TsMethodSignature.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun TsMethodSignature.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun TsMethodSignature.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public
    fun TsMethodSignature.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * TsMethodSignature#key: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun TsMethodSignature.invalid(block: Invalid.() -> Unit): Invalid =
    InvalidImpl().apply(block)

/**
 * TsMethodSignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> BindingIdentifierImpl
 */
public fun TsMethodSignature.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsMethodSignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> ArrayPatternImpl
 */
public fun TsMethodSignature.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * TsMethodSignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> RestElementImpl
 */
public fun TsMethodSignature.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * TsMethodSignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> ObjectPatternImpl
 */
public fun TsMethodSignature.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * TsMethodSignature#typeAnn: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun TsMethodSignature.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation
    = TsTypeAnnotationImpl().apply(block)

/**
 * TsMethodSignature#typeParams: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public
    fun TsMethodSignature.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * TsMethodSignature#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsMethodSignature.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
