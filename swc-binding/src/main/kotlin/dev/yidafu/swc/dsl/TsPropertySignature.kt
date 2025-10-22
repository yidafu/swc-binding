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
import dev.yidafu.swc.types.TsNonNullExpression
import dev.yidafu.swc.types.TsNonNullExpressionImpl
import dev.yidafu.swc.types.TsPropertySignature
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
 * TsPropertySignature#type: String
 * extension function for create String -> String
 */
public fun TsPropertySignature.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsPropertySignature#optional: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsPropertySignature.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun TsPropertySignature.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun TsPropertySignature.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun TsPropertySignature.objectExpression(block: ObjectExpression.() -> Unit):
    ObjectExpression = ObjectExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun TsPropertySignature.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun TsPropertySignature.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun TsPropertySignature.updateExpression(block: UpdateExpression.() -> Unit):
    UpdateExpression = UpdateExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun TsPropertySignature.binaryExpression(block: BinaryExpression.() -> Unit):
    BinaryExpression = BinaryExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun TsPropertySignature.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun TsPropertySignature.memberExpression(block: MemberExpression.() -> Unit):
    MemberExpression = MemberExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun TsPropertySignature.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun TsPropertySignature.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun TsPropertySignature.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun TsPropertySignature.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun TsPropertySignature.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun TsPropertySignature.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun TsPropertySignature.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun TsPropertySignature.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun TsPropertySignature.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun TsPropertySignature.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun TsPropertySignature.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun TsPropertySignature.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun TsPropertySignature.jSXText(block: JSXText.() -> Unit): JSXText =
    JSXTextImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun TsPropertySignature.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public fun TsPropertySignature.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun TsPropertySignature.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun TsPropertySignature.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun TsPropertySignature.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun TsPropertySignature.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun TsPropertySignature.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun TsPropertySignature.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun TsPropertySignature.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun TsPropertySignature.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun TsPropertySignature.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun TsPropertySignature.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun TsPropertySignature.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun TsPropertySignature.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun TsPropertySignature.tsConstAssertion(block: TsConstAssertion.() -> Unit):
    TsConstAssertion = TsConstAssertionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun TsPropertySignature.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun TsPropertySignature.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> TsSatisfiesExpressionImpl
 */
public fun TsPropertySignature.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun TsPropertySignature.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun TsPropertySignature.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public
    fun TsPropertySignature.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * TsPropertySignature#init: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun TsPropertySignature.invalid(block: Invalid.() -> Unit): Invalid =
    InvalidImpl().apply(block)

/**
 * TsPropertySignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> BindingIdentifierImpl
 */
public fun TsPropertySignature.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * TsPropertySignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> ArrayPatternImpl
 */
public fun TsPropertySignature.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * TsPropertySignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> RestElementImpl
 */
public fun TsPropertySignature.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * TsPropertySignature#params: Array<TsFnParameter>
 * extension function for create Array<TsFnParameter> -> ObjectPatternImpl
 */
public fun TsPropertySignature.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * TsPropertySignature#typeAnnotation: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun TsPropertySignature.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit):
    TsTypeAnnotation = TsTypeAnnotationImpl().apply(block)

/**
 * TsPropertySignature#typeParams: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public
    fun TsPropertySignature.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * TsPropertySignature#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsPropertySignature.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
