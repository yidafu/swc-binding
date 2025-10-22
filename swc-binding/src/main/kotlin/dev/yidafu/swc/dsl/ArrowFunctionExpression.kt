package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
import dev.yidafu.swc.types.ArrowFunctionExpression
import dev.yidafu.swc.types.AssignmentExpression
import dev.yidafu.swc.types.AssignmentExpressionImpl
import dev.yidafu.swc.types.AssignmentPattern
import dev.yidafu.swc.types.AssignmentPatternImpl
import dev.yidafu.swc.types.AwaitExpression
import dev.yidafu.swc.types.AwaitExpressionImpl
import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BinaryExpression
import dev.yidafu.swc.types.BinaryExpressionImpl
import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.BindingIdentifierImpl
import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
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
 * ArrowFunctionExpression#type: String
 * extension function for create String -> String
 */
public fun ArrowFunctionExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ArrowFunctionExpression#params: Array<Pattern>
 * extension function for create Array<Pattern> -> BindingIdentifierImpl
 */
public fun ArrowFunctionExpression.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * ArrowFunctionExpression#params: Array<Pattern>
 * extension function for create Array<Pattern> -> ArrayPatternImpl
 */
public fun ArrowFunctionExpression.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * ArrowFunctionExpression#params: Array<Pattern>
 * extension function for create Array<Pattern> -> RestElementImpl
 */
public fun ArrowFunctionExpression.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * ArrowFunctionExpression#params: Array<Pattern>
 * extension function for create Array<Pattern> -> ObjectPatternImpl
 */
public fun ArrowFunctionExpression.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * ArrowFunctionExpression#params: Array<Pattern>
 * extension function for create Array<Pattern> -> AssignmentPatternImpl
 */
public fun ArrowFunctionExpression.assignmentPattern(block: AssignmentPattern.() -> Unit):
    AssignmentPattern = AssignmentPatternImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> InvalidImpl
 */
public fun ArrowFunctionExpression.invalid(block: Invalid.() -> Unit): Invalid =
    InvalidImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> ThisExpressionImpl
 */
public fun ArrowFunctionExpression.thisExpression(block: ThisExpression.() -> Unit): ThisExpression
    = ThisExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> ArrayExpressionImpl
 */
public fun ArrowFunctionExpression.arrayExpression(block: ArrayExpression.() -> Unit):
    ArrayExpression = ArrayExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> ObjectExpressionImpl
 */
public fun ArrowFunctionExpression.objectExpression(block: ObjectExpression.() -> Unit):
    ObjectExpression = ObjectExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> FunctionExpressionImpl
 */
public fun ArrowFunctionExpression.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> UnaryExpressionImpl
 */
public fun ArrowFunctionExpression.unaryExpression(block: UnaryExpression.() -> Unit):
    UnaryExpression = UnaryExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> UpdateExpressionImpl
 */
public fun ArrowFunctionExpression.updateExpression(block: UpdateExpression.() -> Unit):
    UpdateExpression = UpdateExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> BinaryExpressionImpl
 */
public fun ArrowFunctionExpression.binaryExpression(block: BinaryExpression.() -> Unit):
    BinaryExpression = BinaryExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> AssignmentExpressionImpl
 */
public fun ArrowFunctionExpression.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> MemberExpressionImpl
 */
public fun ArrowFunctionExpression.memberExpression(block: MemberExpression.() -> Unit):
    MemberExpression = MemberExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> SuperPropExpressionImpl
 */
public fun ArrowFunctionExpression.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> ConditionalExpressionImpl
 */
public fun ArrowFunctionExpression.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> CallExpressionImpl
 */
public fun ArrowFunctionExpression.callExpression(block: CallExpression.() -> Unit): CallExpression
    = CallExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> NewExpressionImpl
 */
public fun ArrowFunctionExpression.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> SequenceExpressionImpl
 */
public fun ArrowFunctionExpression.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> IdentifierImpl
 */
public fun ArrowFunctionExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> StringLiteralImpl
 */
public fun ArrowFunctionExpression.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> BooleanLiteralImpl
 */
public fun ArrowFunctionExpression.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral
    = BooleanLiteralImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> NullLiteralImpl
 */
public fun ArrowFunctionExpression.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> NumericLiteralImpl
 */
public fun ArrowFunctionExpression.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral
    = NumericLiteralImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> BigIntLiteralImpl
 */
public fun ArrowFunctionExpression.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> RegExpLiteralImpl
 */
public fun ArrowFunctionExpression.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> JSXTextImpl
 */
public fun ArrowFunctionExpression.jSXText(block: JSXText.() -> Unit): JSXText =
    JSXTextImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> TemplateLiteralImpl
 */
public fun ArrowFunctionExpression.templateLiteral(block: TemplateLiteral.() -> Unit):
    TemplateLiteral = TemplateLiteralImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> ->
 * TaggedTemplateExpressionImpl
 */
public
    fun ArrowFunctionExpression.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> ClassExpressionImpl
 */
public fun ArrowFunctionExpression.classExpression(block: ClassExpression.() -> Unit):
    ClassExpression = ClassExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> YieldExpressionImpl
 */
public fun ArrowFunctionExpression.yieldExpression(block: YieldExpression.() -> Unit):
    YieldExpression = YieldExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> MetaPropertyImpl
 */
public fun ArrowFunctionExpression.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> AwaitExpressionImpl
 */
public fun ArrowFunctionExpression.awaitExpression(block: AwaitExpression.() -> Unit):
    AwaitExpression = AwaitExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> ParenthesisExpressionImpl
 */
public fun ArrowFunctionExpression.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> JSXMemberExpressionImpl
 */
public fun ArrowFunctionExpression.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> JSXNamespacedNameImpl
 */
public fun ArrowFunctionExpression.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> JSXEmptyExpressionImpl
 */
public fun ArrowFunctionExpression.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> JSXElementImpl
 */
public fun ArrowFunctionExpression.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> JSXFragmentImpl
 */
public fun ArrowFunctionExpression.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> TsTypeAssertionImpl
 */
public fun ArrowFunctionExpression.tsTypeAssertion(block: TsTypeAssertion.() -> Unit):
    TsTypeAssertion = TsTypeAssertionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> TsConstAssertionImpl
 */
public fun ArrowFunctionExpression.tsConstAssertion(block: TsConstAssertion.() -> Unit):
    TsConstAssertion = TsConstAssertionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> TsNonNullExpressionImpl
 */
public fun ArrowFunctionExpression.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> TsAsExpressionImpl
 */
public fun ArrowFunctionExpression.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression
    = TsAsExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> TsSatisfiesExpressionImpl
 */
public fun ArrowFunctionExpression.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> TsInstantiationImpl
 */
public fun ArrowFunctionExpression.tsInstantiation(block: TsInstantiation.() -> Unit):
    TsInstantiation = TsInstantiationImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> PrivateNameImpl
 */
public fun ArrowFunctionExpression.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> ->
 * OptionalChainingExpressionImpl
 */
public
    fun ArrowFunctionExpression.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * ArrowFunctionExpression#body: Union.U2<BlockStatement, Expression>
 * extension function for create Union.U2<BlockStatement, Expression> -> BlockStatementImpl
 */
public fun ArrowFunctionExpression.blockStatement(block: BlockStatement.() -> Unit): BlockStatement
    = BlockStatementImpl().apply(block)

/**
 * ArrowFunctionExpression#generator: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun ArrowFunctionExpression.boolean(block: Boolean.() -> Unit): Boolean =
    Boolean().apply(block)

/**
 * ArrowFunctionExpression#typeParameters: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public
    fun ArrowFunctionExpression.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * ArrowFunctionExpression#returnType: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
public fun ArrowFunctionExpression.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit):
    TsTypeAnnotation = TsTypeAnnotationImpl().apply(block)

/**
 * ArrowFunctionExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ArrowFunctionExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
