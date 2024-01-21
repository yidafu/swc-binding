package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.variableDeclaration(block: VariableDeclaration.() -> Unit): VariableDeclaration {
    return VariableDeclarationImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier {
    return BindingIdentifierImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern {
    return ArrayPatternImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.restElement(block: RestElement.() -> Unit): RestElement {
    return RestElementImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern {
    return ObjectPatternImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern {
    return AssignmentPatternImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.invalid(block: Invalid.() -> Unit): Invalid {
    return InvalidImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.thisExpression(block: ThisExpression.() -> Unit): ThisExpression {
    return ThisExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression {
    return ArrayExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression {
    return ObjectExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression {
    return FunctionExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression {
    return UnaryExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression {
    return UpdateExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression {
    return BinaryExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.assignmentExpression(block: AssignmentExpression.() -> Unit): AssignmentExpression {
    return AssignmentExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.memberExpression(block: MemberExpression.() -> Unit): MemberExpression {
    return MemberExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.superPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression {
    return SuperPropExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.conditionalExpression(block: ConditionalExpression.() -> Unit): ConditionalExpression {
    return ConditionalExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.callExpression(block: CallExpression.() -> Unit): CallExpression {
    return CallExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.newExpression(block: NewExpression.() -> Unit): NewExpression {
    return NewExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression {
    return SequenceExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.identifier(block: Identifier.() -> Unit): Identifier {
    return IdentifierImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral {
    return StringLiteralImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral {
    return BooleanLiteralImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral {
    return NullLiteralImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral {
    return NumericLiteralImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral {
    return BigIntLiteralImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral {
    return RegExpLiteralImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.jSXText(block: JSXText.() -> Unit): JSXText {
    return JSXTextImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral {
    return TemplateLiteralImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit): TaggedTemplateExpression {
    return TaggedTemplateExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit): ArrowFunctionExpression {
    return ArrowFunctionExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.classExpression(block: ClassExpression.() -> Unit): ClassExpression {
    return ClassExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression {
    return YieldExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.metaProperty(block: MetaProperty.() -> Unit): MetaProperty {
    return MetaPropertyImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression {
    return AwaitExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.parenthesisExpression(block: ParenthesisExpression.() -> Unit): ParenthesisExpression {
    return ParenthesisExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression {
    return JSXMemberExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName {
    return JSXNamespacedNameImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression {
    return JSXEmptyExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.jSXElement(block: JSXElement.() -> Unit): JSXElement {
    return JSXElementImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment {
    return JSXFragmentImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion {
    return TsTypeAssertionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion {
    return TsConstAssertionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.tsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression {
    return TsNonNullExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression {
    return TsAsExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit): TsSatisfiesExpression {
    return TsSatisfiesExpressionImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation {
    return TsInstantiationImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.privateName(block: PrivateName.() -> Unit): PrivateName {
    return PrivateNameImpl().apply(block)
}

/**
 * subtype of ForOfStatementLeft
 */
fun ForOfStatementLeft.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit): OptionalChainingExpression {
    return OptionalChainingExpressionImpl().apply(block)
}