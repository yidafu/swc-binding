package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
import dev.yidafu.swc.types.ArrowFunctionExpression
import dev.yidafu.swc.types.ArrowFunctionExpressionImpl
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
import dev.yidafu.swc.types.TsArrayType
import dev.yidafu.swc.types.TsArrayTypeImpl
import dev.yidafu.swc.types.TsAsExpression
import dev.yidafu.swc.types.TsAsExpressionImpl
import dev.yidafu.swc.types.TsConditionalType
import dev.yidafu.swc.types.TsConditionalTypeImpl
import dev.yidafu.swc.types.TsConstAssertion
import dev.yidafu.swc.types.TsConstAssertionImpl
import dev.yidafu.swc.types.TsConstructorType
import dev.yidafu.swc.types.TsConstructorTypeImpl
import dev.yidafu.swc.types.TsFunctionType
import dev.yidafu.swc.types.TsFunctionTypeImpl
import dev.yidafu.swc.types.TsImportType
import dev.yidafu.swc.types.TsImportTypeImpl
import dev.yidafu.swc.types.TsIndexedAccessType
import dev.yidafu.swc.types.TsIndexedAccessTypeImpl
import dev.yidafu.swc.types.TsInferType
import dev.yidafu.swc.types.TsInferTypeImpl
import dev.yidafu.swc.types.TsInstantiation
import dev.yidafu.swc.types.TsInstantiationImpl
import dev.yidafu.swc.types.TsIntersectionType
import dev.yidafu.swc.types.TsIntersectionTypeImpl
import dev.yidafu.swc.types.TsKeywordType
import dev.yidafu.swc.types.TsKeywordTypeImpl
import dev.yidafu.swc.types.TsLiteralType
import dev.yidafu.swc.types.TsLiteralTypeImpl
import dev.yidafu.swc.types.TsMappedType
import dev.yidafu.swc.types.TsMappedTypeImpl
import dev.yidafu.swc.types.TsNonNullExpression
import dev.yidafu.swc.types.TsNonNullExpressionImpl
import dev.yidafu.swc.types.TsOptionalType
import dev.yidafu.swc.types.TsOptionalTypeImpl
import dev.yidafu.swc.types.TsParenthesizedType
import dev.yidafu.swc.types.TsParenthesizedTypeImpl
import dev.yidafu.swc.types.TsRestType
import dev.yidafu.swc.types.TsRestTypeImpl
import dev.yidafu.swc.types.TsSatisfiesExpression
import dev.yidafu.swc.types.TsSatisfiesExpressionImpl
import dev.yidafu.swc.types.TsThisType
import dev.yidafu.swc.types.TsThisTypeImpl
import dev.yidafu.swc.types.TsTupleElement
import dev.yidafu.swc.types.TsTupleType
import dev.yidafu.swc.types.TsTupleTypeImpl
import dev.yidafu.swc.types.TsTypeAssertion
import dev.yidafu.swc.types.TsTypeAssertionImpl
import dev.yidafu.swc.types.TsTypeLiteral
import dev.yidafu.swc.types.TsTypeLiteralImpl
import dev.yidafu.swc.types.TsTypeOperator
import dev.yidafu.swc.types.TsTypeOperatorImpl
import dev.yidafu.swc.types.TsTypePredicate
import dev.yidafu.swc.types.TsTypePredicateImpl
import dev.yidafu.swc.types.TsTypeQuery
import dev.yidafu.swc.types.TsTypeQueryImpl
import dev.yidafu.swc.types.TsTypeReference
import dev.yidafu.swc.types.TsTypeReferenceImpl
import dev.yidafu.swc.types.TsUnionType
import dev.yidafu.swc.types.TsUnionTypeImpl
import dev.yidafu.swc.types.UnaryExpression
import dev.yidafu.swc.types.UnaryExpressionImpl
import dev.yidafu.swc.types.UpdateExpression
import dev.yidafu.swc.types.UpdateExpressionImpl
import dev.yidafu.swc.types.YieldExpression
import dev.yidafu.swc.types.YieldExpressionImpl
import kotlin.Unit

/**
 * TsTupleElement#type: String
 * extension function for create String -> String
 */
public fun TsTupleElement.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> BindingIdentifierImpl
 */
public fun TsTupleElement.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier
    = BindingIdentifierImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> ArrayPatternImpl
 */
public fun TsTupleElement.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> RestElementImpl
 */
public fun TsTupleElement.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> ObjectPatternImpl
 */
public fun TsTupleElement.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> AssignmentPatternImpl
 */
public fun TsTupleElement.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern
    = AssignmentPatternImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> InvalidImpl
 */
public fun TsTupleElement.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> ThisExpressionImpl
 */
public fun TsTupleElement.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> ArrayExpressionImpl
 */
public fun TsTupleElement.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> ObjectExpressionImpl
 */
public fun TsTupleElement.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> FunctionExpressionImpl
 */
public fun TsTupleElement.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> UnaryExpressionImpl
 */
public fun TsTupleElement.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> UpdateExpressionImpl
 */
public fun TsTupleElement.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> BinaryExpressionImpl
 */
public fun TsTupleElement.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> AssignmentExpressionImpl
 */
public fun TsTupleElement.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> MemberExpressionImpl
 */
public fun TsTupleElement.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> SuperPropExpressionImpl
 */
public fun TsTupleElement.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> ConditionalExpressionImpl
 */
public fun TsTupleElement.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> CallExpressionImpl
 */
public fun TsTupleElement.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> NewExpressionImpl
 */
public fun TsTupleElement.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> SequenceExpressionImpl
 */
public fun TsTupleElement.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> IdentifierImpl
 */
public fun TsTupleElement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> StringLiteralImpl
 */
public fun TsTupleElement.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> BooleanLiteralImpl
 */
public fun TsTupleElement.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> NullLiteralImpl
 */
public fun TsTupleElement.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> NumericLiteralImpl
 */
public fun TsTupleElement.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> BigIntLiteralImpl
 */
public fun TsTupleElement.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> RegExpLiteralImpl
 */
public fun TsTupleElement.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> JSXTextImpl
 */
public fun TsTupleElement.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> TemplateLiteralImpl
 */
public fun TsTupleElement.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> TaggedTemplateExpressionImpl
 */
public fun TsTupleElement.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> ArrowFunctionExpressionImpl
 */
public fun TsTupleElement.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> ClassExpressionImpl
 */
public fun TsTupleElement.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> YieldExpressionImpl
 */
public fun TsTupleElement.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> MetaPropertyImpl
 */
public fun TsTupleElement.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> AwaitExpressionImpl
 */
public fun TsTupleElement.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> ParenthesisExpressionImpl
 */
public fun TsTupleElement.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> JSXMemberExpressionImpl
 */
public fun TsTupleElement.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> JSXNamespacedNameImpl
 */
public fun TsTupleElement.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName
    = JSXNamespacedNameImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> JSXEmptyExpressionImpl
 */
public fun TsTupleElement.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> JSXElementImpl
 */
public fun TsTupleElement.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> JSXFragmentImpl
 */
public fun TsTupleElement.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> TsTypeAssertionImpl
 */
public fun TsTupleElement.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> TsConstAssertionImpl
 */
public fun TsTupleElement.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> TsNonNullExpressionImpl
 */
public fun TsTupleElement.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> TsAsExpressionImpl
 */
public fun TsTupleElement.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> TsSatisfiesExpressionImpl
 */
public fun TsTupleElement.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> TsInstantiationImpl
 */
public fun TsTupleElement.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> PrivateNameImpl
 */
public fun TsTupleElement.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * TsTupleElement#label: Pattern
 * extension function for create Pattern -> OptionalChainingExpressionImpl
 */
public fun TsTupleElement.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsKeywordTypeImpl
 */
public fun TsTupleElement.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsThisTypeImpl
 */
public fun TsTupleElement.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsFunctionTypeImpl
 */
public fun TsTupleElement.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsConstructorTypeImpl
 */
public fun TsTupleElement.tsConstructorType(block: TsConstructorType.() -> Unit): TsConstructorType
    = TsConstructorTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsTypeReferenceImpl
 */
public fun TsTupleElement.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsTypeQueryImpl
 */
public fun TsTupleElement.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsTypeLiteralImpl
 */
public fun TsTupleElement.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsArrayTypeImpl
 */
public fun TsTupleElement.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsTupleTypeImpl
 */
public fun TsTupleElement.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsOptionalTypeImpl
 */
public fun TsTupleElement.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsRestTypeImpl
 */
public fun TsTupleElement.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsUnionTypeImpl
 */
public fun TsTupleElement.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsIntersectionTypeImpl
 */
public fun TsTupleElement.tsIntersectionType(block: TsIntersectionType.() -> Unit):
    TsIntersectionType = TsIntersectionTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsConditionalTypeImpl
 */
public fun TsTupleElement.tsConditionalType(block: TsConditionalType.() -> Unit): TsConditionalType
    = TsConditionalTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsInferTypeImpl
 */
public fun TsTupleElement.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsParenthesizedTypeImpl
 */
public fun TsTupleElement.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsTypeOperatorImpl
 */
public fun TsTupleElement.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsIndexedAccessTypeImpl
 */
public fun TsTupleElement.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsMappedTypeImpl
 */
public fun TsTupleElement.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsLiteralTypeImpl
 */
public fun TsTupleElement.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsTypePredicateImpl
 */
public fun TsTupleElement.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicateImpl().apply(block)

/**
 * TsTupleElement#ty: TsType
 * extension function for create TsType -> TsImportTypeImpl
 */
public fun TsTupleElement.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsTupleElement#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsTupleElement.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
