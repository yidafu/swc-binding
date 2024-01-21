package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.blockStatement(block: BlockStatement.() -> Unit): BlockStatement {
    return BlockStatementImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.thisExpression(block: ThisExpression.() -> Unit): ThisExpression {
    return ThisExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression {
    return ArrayExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression {
    return ObjectExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression {
    return FunctionExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression {
    return UnaryExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression {
    return UpdateExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression {
    return BinaryExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.assignmentExpression(block: AssignmentExpression.() -> Unit): AssignmentExpression {
    return AssignmentExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.memberExpression(block: MemberExpression.() -> Unit): MemberExpression {
    return MemberExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.superPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression {
    return SuperPropExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.conditionalExpression(block: ConditionalExpression.() -> Unit): ConditionalExpression {
    return ConditionalExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.callExpression(block: CallExpression.() -> Unit): CallExpression {
    return CallExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.newExpression(block: NewExpression.() -> Unit): NewExpression {
    return NewExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression {
    return SequenceExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.identifier(block: Identifier.() -> Unit): Identifier {
    return IdentifierImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral {
    return StringLiteralImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral {
    return BooleanLiteralImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral {
    return NullLiteralImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral {
    return NumericLiteralImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral {
    return BigIntLiteralImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral {
    return RegExpLiteralImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.jSXText(block: JSXText.() -> Unit): JSXText {
    return JSXTextImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral {
    return TemplateLiteralImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit): TaggedTemplateExpression {
    return TaggedTemplateExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit): ArrowFunctionExpression {
    return ArrowFunctionExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.classExpression(block: ClassExpression.() -> Unit): ClassExpression {
    return ClassExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression {
    return YieldExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.metaProperty(block: MetaProperty.() -> Unit): MetaProperty {
    return MetaPropertyImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression {
    return AwaitExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.parenthesisExpression(block: ParenthesisExpression.() -> Unit): ParenthesisExpression {
    return ParenthesisExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression {
    return JSXMemberExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName {
    return JSXNamespacedNameImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression {
    return JSXEmptyExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.jSXElement(block: JSXElement.() -> Unit): JSXElement {
    return JSXElementImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment {
    return JSXFragmentImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion {
    return TsTypeAssertionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion {
    return TsConstAssertionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.tsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression {
    return TsNonNullExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression {
    return TsAsExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit): TsSatisfiesExpression {
    return TsSatisfiesExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation {
    return TsInstantiationImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.privateName(block: PrivateName.() -> Unit): PrivateName {
    return PrivateNameImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit): OptionalChainingExpression {
    return OptionalChainingExpressionImpl().apply(block)
}

/**
 * subtype of ArrowFunctionExpressionBody
 */
fun ArrowFunctionExpressionBody.invalid(block: Invalid.() -> Unit): Invalid {
    return InvalidImpl().apply(block)
}