package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.jsSuper(block: Super.() -> Unit): Super {
    return SuperImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.import(block: Import.() -> Unit): Import {
    return ImportImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.thisExpression(block: ThisExpression.() -> Unit): ThisExpression {
    return ThisExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression {
    return ArrayExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression {
    return ObjectExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression {
    return FunctionExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression {
    return UnaryExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression {
    return UpdateExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression {
    return BinaryExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.assignmentExpression(block: AssignmentExpression.() -> Unit): AssignmentExpression {
    return AssignmentExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.memberExpression(block: MemberExpression.() -> Unit): MemberExpression {
    return MemberExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.superPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression {
    return SuperPropExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.conditionalExpression(block: ConditionalExpression.() -> Unit): ConditionalExpression {
    return ConditionalExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.callExpression(block: CallExpression.() -> Unit): CallExpression {
    return CallExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.newExpression(block: NewExpression.() -> Unit): NewExpression {
    return NewExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression {
    return SequenceExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.identifier(block: Identifier.() -> Unit): Identifier {
    return IdentifierImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral {
    return StringLiteralImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral {
    return BooleanLiteralImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral {
    return NullLiteralImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral {
    return NumericLiteralImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral {
    return BigIntLiteralImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral {
    return RegExpLiteralImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.jSXText(block: JSXText.() -> Unit): JSXText {
    return JSXTextImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral {
    return TemplateLiteralImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit): TaggedTemplateExpression {
    return TaggedTemplateExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit): ArrowFunctionExpression {
    return ArrowFunctionExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.classExpression(block: ClassExpression.() -> Unit): ClassExpression {
    return ClassExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression {
    return YieldExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.metaProperty(block: MetaProperty.() -> Unit): MetaProperty {
    return MetaPropertyImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression {
    return AwaitExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.parenthesisExpression(block: ParenthesisExpression.() -> Unit): ParenthesisExpression {
    return ParenthesisExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression {
    return JSXMemberExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName {
    return JSXNamespacedNameImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression {
    return JSXEmptyExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.jSXElement(block: JSXElement.() -> Unit): JSXElement {
    return JSXElementImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment {
    return JSXFragmentImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion {
    return TsTypeAssertionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion {
    return TsConstAssertionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.tsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression {
    return TsNonNullExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression {
    return TsAsExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit): TsSatisfiesExpression {
    return TsSatisfiesExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation {
    return TsInstantiationImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.privateName(block: PrivateName.() -> Unit): PrivateName {
    return PrivateNameImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit): OptionalChainingExpression {
    return OptionalChainingExpressionImpl().apply(block)
}

/**
 * subtype of CallExpressionCallee
 */
fun CallExpressionCallee.invalid(block: Invalid.() -> Unit): Invalid {
    return InvalidImpl().apply(block)
}