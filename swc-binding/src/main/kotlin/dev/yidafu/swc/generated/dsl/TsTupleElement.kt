// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.394263

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ArrayExpression
import dev.yidafu.swc.generated.ArrayPattern
import dev.yidafu.swc.generated.ArrowFunctionExpression
import dev.yidafu.swc.generated.AssignmentExpression
import dev.yidafu.swc.generated.AssignmentPattern
import dev.yidafu.swc.generated.AwaitExpression
import dev.yidafu.swc.generated.BigIntLiteral
import dev.yidafu.swc.generated.BinaryExpression
import dev.yidafu.swc.generated.BindingIdentifier
import dev.yidafu.swc.generated.BindingIdentifierImpl
import dev.yidafu.swc.generated.BooleanLiteral
import dev.yidafu.swc.generated.CallExpression
import dev.yidafu.swc.generated.ClassExpression
import dev.yidafu.swc.generated.ConditionalExpression
import dev.yidafu.swc.generated.FunctionExpression
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.Invalid
import dev.yidafu.swc.generated.JSXElement
import dev.yidafu.swc.generated.JSXEmptyExpression
import dev.yidafu.swc.generated.JSXFragment
import dev.yidafu.swc.generated.JSXMemberExpression
import dev.yidafu.swc.generated.JSXNamespacedName
import dev.yidafu.swc.generated.JSXText
import dev.yidafu.swc.generated.MemberExpression
import dev.yidafu.swc.generated.MetaProperty
import dev.yidafu.swc.generated.NewExpression
import dev.yidafu.swc.generated.NullLiteral
import dev.yidafu.swc.generated.NumericLiteral
import dev.yidafu.swc.generated.ObjectExpression
import dev.yidafu.swc.generated.ObjectPattern
import dev.yidafu.swc.generated.OptionalChainingExpression
import dev.yidafu.swc.generated.ParenthesisExpression
import dev.yidafu.swc.generated.PrivateName
import dev.yidafu.swc.generated.RegExpLiteral
import dev.yidafu.swc.generated.RestElement
import dev.yidafu.swc.generated.SequenceExpression
import dev.yidafu.swc.generated.StringLiteral
import dev.yidafu.swc.generated.SuperPropExpression
import dev.yidafu.swc.generated.TaggedTemplateExpression
import dev.yidafu.swc.generated.TemplateLiteral
import dev.yidafu.swc.generated.TemplateLiteralImpl
import dev.yidafu.swc.generated.ThisExpression
import dev.yidafu.swc.generated.TsArrayType
import dev.yidafu.swc.generated.TsAsExpression
import dev.yidafu.swc.generated.TsConditionalType
import dev.yidafu.swc.generated.TsConstAssertion
import dev.yidafu.swc.generated.TsConstructorType
import dev.yidafu.swc.generated.TsFunctionType
import dev.yidafu.swc.generated.TsImportType
import dev.yidafu.swc.generated.TsIndexedAccessType
import dev.yidafu.swc.generated.TsInferType
import dev.yidafu.swc.generated.TsInstantiation
import dev.yidafu.swc.generated.TsIntersectionType
import dev.yidafu.swc.generated.TsKeywordType
import dev.yidafu.swc.generated.TsLiteralType
import dev.yidafu.swc.generated.TsMappedType
import dev.yidafu.swc.generated.TsNonNullExpression
import dev.yidafu.swc.generated.TsOptionalType
import dev.yidafu.swc.generated.TsParenthesizedType
import dev.yidafu.swc.generated.TsRestType
import dev.yidafu.swc.generated.TsSatisfiesExpression
import dev.yidafu.swc.generated.TsThisType
import dev.yidafu.swc.generated.TsTupleElement
import dev.yidafu.swc.generated.TsTupleType
import dev.yidafu.swc.generated.TsTypeAssertion
import dev.yidafu.swc.generated.TsTypeLiteral
import dev.yidafu.swc.generated.TsTypeOperator
import dev.yidafu.swc.generated.TsTypePredicate
import dev.yidafu.swc.generated.TsTypeQuery
import dev.yidafu.swc.generated.TsTypeReference
import dev.yidafu.swc.generated.TsUnionType
import dev.yidafu.swc.generated.UnaryExpression
import dev.yidafu.swc.generated.UpdateExpression
import dev.yidafu.swc.generated.YieldExpression
import kotlin.Unit

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> ArrayPattern
 */
public fun TsTupleElement.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPattern().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> ObjectPattern
 */
public fun TsTupleElement.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPattern().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> AssignmentPattern
 */
public fun TsTupleElement.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern
    = AssignmentPattern().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> RestElement
 */
public fun TsTupleElement.restElement(block: RestElement.() -> Unit): RestElement =
    RestElement().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> Invalid
 */
public fun TsTupleElement.invalid(block: Invalid.() -> Unit): Invalid = Invalid().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> OptionalChainingExpression
 */
public fun TsTupleElement.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> ThisExpression
 */
public fun TsTupleElement.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> ArrayExpression
 */
public fun TsTupleElement.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> ObjectExpression
 */
public fun TsTupleElement.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> UnaryExpression
 */
public fun TsTupleElement.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> UpdateExpression
 */
public fun TsTupleElement.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> BinaryExpression
 */
public fun TsTupleElement.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> FunctionExpression
 */
public fun TsTupleElement.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> ClassExpression
 */
public fun TsTupleElement.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> AssignmentExpression
 */
public fun TsTupleElement.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> MemberExpression
 */
public fun TsTupleElement.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> SuperPropExpression
 */
public fun TsTupleElement.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> ConditionalExpression
 */
public fun TsTupleElement.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> CallExpression
 */
public fun TsTupleElement.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> NewExpression
 */
public fun TsTupleElement.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> SequenceExpression
 */
public fun TsTupleElement.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> ArrowFunctionExpression
 */
public fun TsTupleElement.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> YieldExpression
 */
public fun TsTupleElement.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> MetaProperty
 */
public fun TsTupleElement.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaProperty().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> AwaitExpression
 */
public fun TsTupleElement.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> TaggedTemplateExpression
 */
public fun TsTupleElement.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> ParenthesisExpression
 */
public fun TsTupleElement.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> PrivateName
 */
public fun TsTupleElement.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateName().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> JSXMemberExpression
 */
public fun TsTupleElement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> JSXNamespacedName
 */
public fun TsTupleElement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName
    = JSXNamespacedName().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> JSXEmptyExpression
 */
public fun TsTupleElement.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> JSXElement
 */
public fun TsTupleElement.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElement().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> JSXFragment
 */
public fun TsTupleElement.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragment().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> TsAsExpression
 */
public fun TsTupleElement.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> TsSatisfiesExpression
 */
public fun TsTupleElement.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> TsInstantiation
 */
public fun TsTupleElement.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiation().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> TsTypeAssertion
 */
public fun TsTupleElement.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertion().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> TsConstAssertion
 */
public fun TsTupleElement.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertion().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> TsNonNullExpression
 */
public fun TsTupleElement.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpression().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> JSXText
 */
public fun TsTupleElement.jSXText(block: JSXText.() -> Unit): JSXText = JSXText().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> StringLiteral
 */
public fun TsTupleElement.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> BooleanLiteral
 */
public fun TsTupleElement.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteral().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> NullLiteral
 */
public fun TsTupleElement.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteral().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> RegExpLiteral
 */
public fun TsTupleElement.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteral().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> NumericLiteral
 */
public fun TsTupleElement.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> BigIntLiteral
 */
public fun TsTupleElement.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> BindingIdentifier
 */
public fun TsTupleElement.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier
    = BindingIdentifierImpl().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> Identifier
 */
public fun TsTupleElement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsTupleElement#label: Pattern?
 * extension function for create Pattern? -> TemplateLiteral
 */
public fun TsTupleElement.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsKeywordType
 */
public fun TsTupleElement.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsThisType
 */
public fun TsTupleElement.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsTypeReference
 */
public fun TsTupleElement.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReference().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsTypePredicate
 */
public fun TsTupleElement.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicate().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsImportType
 */
public fun TsTupleElement.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsTypeQuery
 */
public fun TsTupleElement.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQuery().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsTypeLiteral
 */
public fun TsTupleElement.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteral().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsArrayType
 */
public fun TsTupleElement.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsTupleType
 */
public fun TsTupleElement.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsOptionalType
 */
public fun TsTupleElement.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsRestType
 */
public fun TsTupleElement.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsConditionalType
 */
public fun TsTupleElement.tsConditionalType(block: TsConditionalType.() -> Unit): TsConditionalType
    = TsConditionalType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsInferType
 */
public fun TsTupleElement.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsParenthesizedType
 */
public fun TsTupleElement.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsTypeOperator
 */
public fun TsTupleElement.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperator().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsIndexedAccessType
 */
public fun TsTupleElement.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsMappedType
 */
public fun TsTupleElement.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsLiteralType
 */
public fun TsTupleElement.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsFunctionType
 */
public fun TsTupleElement.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsConstructorType
 */
public fun TsTupleElement.tsConstructorType(block: TsConstructorType.() -> Unit): TsConstructorType
    = TsConstructorType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsUnionType
 */
public fun TsTupleElement.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionType().apply(block)

/**
 * TsTupleElement#ty: TsType?
 * extension function for create TsType? -> TsIntersectionType
 */
public fun TsTupleElement.tsIntersectionType(block: TsIntersectionType.() -> Unit):
    TsIntersectionType = TsIntersectionType().apply(block)
