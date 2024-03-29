package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

/**
 * ArrowFunctionExpression#params: Array<Pattern>
 * extension function for create Array<Pattern> -> BindingIdentifierImpl
 */
fun ArrowFunctionExpression.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier {
    return BindingIdentifierImpl().apply(block)
}

/**
 * ArrowFunctionExpression#params: Array<Pattern>
 * extension function for create Array<Pattern> -> ArrayPatternImpl
 */
fun ArrowFunctionExpression.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern {
    return ArrayPatternImpl().apply(block)
}

/**
 * ArrowFunctionExpression#params: Array<Pattern>
 * extension function for create Array<Pattern> -> RestElementImpl
 */
fun ArrowFunctionExpression.restElement(block: RestElement.() -> Unit): RestElement {
    return RestElementImpl().apply(block)
}

/**
 * ArrowFunctionExpression#params: Array<Pattern>
 * extension function for create Array<Pattern> -> ObjectPatternImpl
 */
fun ArrowFunctionExpression.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern {
    return ObjectPatternImpl().apply(block)
}

/**
 * ArrowFunctionExpression#params: Array<Pattern>
 * extension function for create Array<Pattern> -> AssignmentPatternImpl
 */
fun ArrowFunctionExpression.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern {
    return AssignmentPatternImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> InvalidImpl
 */
fun ArrowFunctionExpression.invalid(block: Invalid.() -> Unit): Invalid {
    return InvalidImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> ThisExpressionImpl
 */
fun ArrowFunctionExpression.thisExpression(block: ThisExpression.() -> Unit): ThisExpression {
    return ThisExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> ArrayExpressionImpl
 */
fun ArrowFunctionExpression.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression {
    return ArrayExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> ObjectExpressionImpl
 */
fun ArrowFunctionExpression.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression {
    return ObjectExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> FunctionExpressionImpl
 */
fun ArrowFunctionExpression.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression {
    return FunctionExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> UnaryExpressionImpl
 */
fun ArrowFunctionExpression.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression {
    return UnaryExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> UpdateExpressionImpl
 */
fun ArrowFunctionExpression.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression {
    return UpdateExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> BinaryExpressionImpl
 */
fun ArrowFunctionExpression.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression {
    return BinaryExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> AssignmentExpressionImpl
 */
fun ArrowFunctionExpression.assignmentExpression(block: AssignmentExpression.() -> Unit): AssignmentExpression {
    return AssignmentExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> MemberExpressionImpl
 */
fun ArrowFunctionExpression.memberExpression(block: MemberExpression.() -> Unit): MemberExpression {
    return MemberExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> SuperPropExpressionImpl
 */
fun ArrowFunctionExpression.superPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression {
    return SuperPropExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> ConditionalExpressionImpl
 */
fun ArrowFunctionExpression.conditionalExpression(block: ConditionalExpression.() -> Unit): ConditionalExpression {
    return ConditionalExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> CallExpressionImpl
 */
fun ArrowFunctionExpression.callExpression(block: CallExpression.() -> Unit): CallExpression {
    return CallExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> NewExpressionImpl
 */
fun ArrowFunctionExpression.newExpression(block: NewExpression.() -> Unit): NewExpression {
    return NewExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> SequenceExpressionImpl
 */
fun ArrowFunctionExpression.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression {
    return SequenceExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> IdentifierImpl
 */
fun ArrowFunctionExpression.identifier(block: Identifier.() -> Unit): Identifier {
    return IdentifierImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> StringLiteralImpl
 */
fun ArrowFunctionExpression.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral {
    return StringLiteralImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> BooleanLiteralImpl
 */
fun ArrowFunctionExpression.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral {
    return BooleanLiteralImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> NullLiteralImpl
 */
fun ArrowFunctionExpression.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral {
    return NullLiteralImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> NumericLiteralImpl
 */
fun ArrowFunctionExpression.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral {
    return NumericLiteralImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> BigIntLiteralImpl
 */
fun ArrowFunctionExpression.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral {
    return BigIntLiteralImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> RegExpLiteralImpl
 */
fun ArrowFunctionExpression.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral {
    return RegExpLiteralImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> JSXTextImpl
 */
fun ArrowFunctionExpression.jSXText(block: JSXText.() -> Unit): JSXText {
    return JSXTextImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> TemplateLiteralImpl
 */
fun ArrowFunctionExpression.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral {
    return TemplateLiteralImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> TaggedTemplateExpressionImpl
 */
fun ArrowFunctionExpression.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit): TaggedTemplateExpression {
    return TaggedTemplateExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> ClassExpressionImpl
 */
fun ArrowFunctionExpression.classExpression(block: ClassExpression.() -> Unit): ClassExpression {
    return ClassExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> YieldExpressionImpl
 */
fun ArrowFunctionExpression.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression {
    return YieldExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> MetaPropertyImpl
 */
fun ArrowFunctionExpression.metaProperty(block: MetaProperty.() -> Unit): MetaProperty {
    return MetaPropertyImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> AwaitExpressionImpl
 */
fun ArrowFunctionExpression.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression {
    return AwaitExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> ParenthesisExpressionImpl
 */
fun ArrowFunctionExpression.parenthesisExpression(block: ParenthesisExpression.() -> Unit): ParenthesisExpression {
    return ParenthesisExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> JSXMemberExpressionImpl
 */
fun ArrowFunctionExpression.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression {
    return JSXMemberExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> JSXNamespacedNameImpl
 */
fun ArrowFunctionExpression.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName {
    return JSXNamespacedNameImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> JSXEmptyExpressionImpl
 */
fun ArrowFunctionExpression.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression {
    return JSXEmptyExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> JSXElementImpl
 */
fun ArrowFunctionExpression.jSXElement(block: JSXElement.() -> Unit): JSXElement {
    return JSXElementImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> JSXFragmentImpl
 */
fun ArrowFunctionExpression.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment {
    return JSXFragmentImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> TsTypeAssertionImpl
 */
fun ArrowFunctionExpression.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion {
    return TsTypeAssertionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> TsConstAssertionImpl
 */
fun ArrowFunctionExpression.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion {
    return TsConstAssertionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> TsNonNullExpressionImpl
 */
fun ArrowFunctionExpression.tsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression {
    return TsNonNullExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> TsAsExpressionImpl
 */
fun ArrowFunctionExpression.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression {
    return TsAsExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> TsSatisfiesExpressionImpl
 */
fun ArrowFunctionExpression.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit): TsSatisfiesExpression {
    return TsSatisfiesExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> TsInstantiationImpl
 */
fun ArrowFunctionExpression.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation {
    return TsInstantiationImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> PrivateNameImpl
 */
fun ArrowFunctionExpression.privateName(block: PrivateName.() -> Unit): PrivateName {
    return PrivateNameImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> OptionalChainingExpressionImpl
 */
fun ArrowFunctionExpression.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit): OptionalChainingExpression {
    return OptionalChainingExpressionImpl().apply(block)
}

/**
 * ArrowFunctionExpression#body: ArrowFunctionExpressionBody
 * extension function for create ArrowFunctionExpressionBody -> BlockStatementImpl
 */
fun ArrowFunctionExpression.blockStatement(block: BlockStatement.() -> Unit): BlockStatement {
    return BlockStatementImpl().apply(block)
}

/**
 * ArrowFunctionExpression#typeParameters: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
fun ArrowFunctionExpression.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit): TsTypeParameterDeclaration {
    return TsTypeParameterDeclarationImpl().apply(block)
}

/**
 * ArrowFunctionExpression#returnType: TsTypeAnnotation
 * extension function for create TsTypeAnnotation -> TsTypeAnnotationImpl
 */
fun ArrowFunctionExpression.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation {
    return TsTypeAnnotationImpl().apply(block)
}

fun ArrowFunctionExpression.span(block: Span.() -> Unit): Span {
    return Span().apply(block)
}