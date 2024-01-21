package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.thisExpression(block: ThisExpression.() -> Unit): ThisExpression {
    return ThisExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression {
    return ArrayExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression {
    return ObjectExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression {
    return FunctionExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression {
    return UnaryExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression {
    return UpdateExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression {
    return BinaryExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.assignmentExpression(block: AssignmentExpression.() -> Unit): AssignmentExpression {
    return AssignmentExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.memberExpression(block: MemberExpression.() -> Unit): MemberExpression {
    return MemberExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.superPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression {
    return SuperPropExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.conditionalExpression(block: ConditionalExpression.() -> Unit): ConditionalExpression {
    return ConditionalExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.callExpression(block: CallExpression.() -> Unit): CallExpression {
    return CallExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.newExpression(block: NewExpression.() -> Unit): NewExpression {
    return NewExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression {
    return SequenceExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.identifier(block: Identifier.() -> Unit): Identifier {
    return IdentifierImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral {
    return StringLiteralImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral {
    return BooleanLiteralImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral {
    return NullLiteralImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral {
    return NumericLiteralImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral {
    return BigIntLiteralImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral {
    return RegExpLiteralImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.jSXText(block: JSXText.() -> Unit): JSXText {
    return JSXTextImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral {
    return TemplateLiteralImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit): TaggedTemplateExpression {
    return TaggedTemplateExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit): ArrowFunctionExpression {
    return ArrowFunctionExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.classExpression(block: ClassExpression.() -> Unit): ClassExpression {
    return ClassExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression {
    return YieldExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.metaProperty(block: MetaProperty.() -> Unit): MetaProperty {
    return MetaPropertyImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression {
    return AwaitExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.parenthesisExpression(block: ParenthesisExpression.() -> Unit): ParenthesisExpression {
    return ParenthesisExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression {
    return JSXMemberExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName {
    return JSXNamespacedNameImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression {
    return JSXEmptyExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.jSXElement(block: JSXElement.() -> Unit): JSXElement {
    return JSXElementImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment {
    return JSXFragmentImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion {
    return TsTypeAssertionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion {
    return TsConstAssertionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.tsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression {
    return TsNonNullExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression {
    return TsAsExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit): TsSatisfiesExpression {
    return TsSatisfiesExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation {
    return TsInstantiationImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.privateName(block: PrivateName.() -> Unit): PrivateName {
    return PrivateNameImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit): OptionalChainingExpression {
    return OptionalChainingExpressionImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.invalid(block: Invalid.() -> Unit): Invalid {
    return InvalidImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier {
    return BindingIdentifierImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern {
    return ArrayPatternImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.restElement(block: RestElement.() -> Unit): RestElement {
    return RestElementImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern {
    return ObjectPatternImpl().apply(block)
}

/**
 * subtype of AssignmentExpressionLeft
 */
fun AssignmentExpressionLeft.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern {
    return AssignmentPatternImpl().apply(block)
}