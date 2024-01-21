package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> SuperImpl
 */
fun CallExpression.jsSuper(block: Super.() -> Unit): Super {
    return SuperImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> ImportImpl
 */
fun CallExpression.import(block: Import.() -> Unit): Import {
    return ImportImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> ThisExpressionImpl
 */
fun CallExpression.thisExpression(block: ThisExpression.() -> Unit): ThisExpression {
    return ThisExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> ArrayExpressionImpl
 */
fun CallExpression.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression {
    return ArrayExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> ObjectExpressionImpl
 */
fun CallExpression.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression {
    return ObjectExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> FunctionExpressionImpl
 */
fun CallExpression.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression {
    return FunctionExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> UnaryExpressionImpl
 */
fun CallExpression.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression {
    return UnaryExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> UpdateExpressionImpl
 */
fun CallExpression.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression {
    return UpdateExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> BinaryExpressionImpl
 */
fun CallExpression.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression {
    return BinaryExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> AssignmentExpressionImpl
 */
fun CallExpression.assignmentExpression(block: AssignmentExpression.() -> Unit): AssignmentExpression {
    return AssignmentExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> MemberExpressionImpl
 */
fun CallExpression.memberExpression(block: MemberExpression.() -> Unit): MemberExpression {
    return MemberExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> SuperPropExpressionImpl
 */
fun CallExpression.superPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression {
    return SuperPropExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> ConditionalExpressionImpl
 */
fun CallExpression.conditionalExpression(block: ConditionalExpression.() -> Unit): ConditionalExpression {
    return ConditionalExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> NewExpressionImpl
 */
fun CallExpression.newExpression(block: NewExpression.() -> Unit): NewExpression {
    return NewExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> SequenceExpressionImpl
 */
fun CallExpression.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression {
    return SequenceExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> IdentifierImpl
 */
fun CallExpression.identifier(block: Identifier.() -> Unit): Identifier {
    return IdentifierImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> StringLiteralImpl
 */
fun CallExpression.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral {
    return StringLiteralImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> BooleanLiteralImpl
 */
fun CallExpression.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral {
    return BooleanLiteralImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> NullLiteralImpl
 */
fun CallExpression.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral {
    return NullLiteralImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> NumericLiteralImpl
 */
fun CallExpression.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral {
    return NumericLiteralImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> BigIntLiteralImpl
 */
fun CallExpression.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral {
    return BigIntLiteralImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> RegExpLiteralImpl
 */
fun CallExpression.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral {
    return RegExpLiteralImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> JSXTextImpl
 */
fun CallExpression.jSXText(block: JSXText.() -> Unit): JSXText {
    return JSXTextImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> TemplateLiteralImpl
 */
fun CallExpression.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral {
    return TemplateLiteralImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> TaggedTemplateExpressionImpl
 */
fun CallExpression.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit): TaggedTemplateExpression {
    return TaggedTemplateExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> ArrowFunctionExpressionImpl
 */
fun CallExpression.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit): ArrowFunctionExpression {
    return ArrowFunctionExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> ClassExpressionImpl
 */
fun CallExpression.classExpression(block: ClassExpression.() -> Unit): ClassExpression {
    return ClassExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> YieldExpressionImpl
 */
fun CallExpression.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression {
    return YieldExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> MetaPropertyImpl
 */
fun CallExpression.metaProperty(block: MetaProperty.() -> Unit): MetaProperty {
    return MetaPropertyImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> AwaitExpressionImpl
 */
fun CallExpression.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression {
    return AwaitExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> ParenthesisExpressionImpl
 */
fun CallExpression.parenthesisExpression(block: ParenthesisExpression.() -> Unit): ParenthesisExpression {
    return ParenthesisExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> JSXMemberExpressionImpl
 */
fun CallExpression.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression {
    return JSXMemberExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> JSXNamespacedNameImpl
 */
fun CallExpression.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName {
    return JSXNamespacedNameImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> JSXEmptyExpressionImpl
 */
fun CallExpression.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression {
    return JSXEmptyExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> JSXElementImpl
 */
fun CallExpression.jSXElement(block: JSXElement.() -> Unit): JSXElement {
    return JSXElementImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> JSXFragmentImpl
 */
fun CallExpression.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment {
    return JSXFragmentImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> TsTypeAssertionImpl
 */
fun CallExpression.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion {
    return TsTypeAssertionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> TsConstAssertionImpl
 */
fun CallExpression.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion {
    return TsConstAssertionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> TsNonNullExpressionImpl
 */
fun CallExpression.tsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression {
    return TsNonNullExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> TsAsExpressionImpl
 */
fun CallExpression.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression {
    return TsAsExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> TsSatisfiesExpressionImpl
 */
fun CallExpression.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit): TsSatisfiesExpression {
    return TsSatisfiesExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> TsInstantiationImpl
 */
fun CallExpression.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation {
    return TsInstantiationImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> PrivateNameImpl
 */
fun CallExpression.privateName(block: PrivateName.() -> Unit): PrivateName {
    return PrivateNameImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> OptionalChainingExpressionImpl
 */
fun CallExpression.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit): OptionalChainingExpression {
    return OptionalChainingExpressionImpl().apply(block)
}

/**
 * CallExpression#callee: CallExpressionCallee
 * extension function for create CallExpressionCallee -> InvalidImpl
 */
fun CallExpression.invalid(block: Invalid.() -> Unit): Invalid {
    return InvalidImpl().apply(block)
}

fun CallExpression.argument(block: Argument.() -> Unit): Argument {
    return Argument().apply(block)
}

/**
 * CallExpression#typeArguments: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
fun CallExpression.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit): TsTypeParameterInstantiation {
    return TsTypeParameterInstantiationImpl().apply(block)
}

fun CallExpression.span(block: Span.() -> Unit): Span {
    return Span().apply(block)
}