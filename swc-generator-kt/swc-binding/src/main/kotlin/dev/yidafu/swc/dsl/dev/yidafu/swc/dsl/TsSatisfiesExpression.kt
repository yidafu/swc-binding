package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
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
import dev.yidafu.swc.types.OptionalChainingExpression
import dev.yidafu.swc.types.OptionalChainingExpressionImpl
import dev.yidafu.swc.types.ParenthesisExpression
import dev.yidafu.swc.types.ParenthesisExpressionImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
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
import dev.yidafu.swc.types.TsThisType
import dev.yidafu.swc.types.TsThisTypeImpl
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
 * TsSatisfiesExpression#type: String
 * extension function for create String -> String
 */
public fun TsSatisfiesExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> ThisExpressionImpl
 */
public fun TsSatisfiesExpression.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> ArrayExpressionImpl
 */
public fun TsSatisfiesExpression.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression
    = ArrayExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> ObjectExpressionImpl
 */
public fun TsSatisfiesExpression.objectExpression(block: ObjectExpression.() -> Unit):
    ObjectExpression = ObjectExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> FunctionExpressionImpl
 */
public fun TsSatisfiesExpression.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> UnaryExpressionImpl
 */
public fun TsSatisfiesExpression.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression
    = UnaryExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> UpdateExpressionImpl
 */
public fun TsSatisfiesExpression.updateExpression(block: UpdateExpression.() -> Unit):
    UpdateExpression = UpdateExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> BinaryExpressionImpl
 */
public fun TsSatisfiesExpression.binaryExpression(block: BinaryExpression.() -> Unit):
    BinaryExpression = BinaryExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> AssignmentExpressionImpl
 */
public fun TsSatisfiesExpression.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> MemberExpressionImpl
 */
public fun TsSatisfiesExpression.memberExpression(block: MemberExpression.() -> Unit):
    MemberExpression = MemberExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> SuperPropExpressionImpl
 */
public fun TsSatisfiesExpression.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> ConditionalExpressionImpl
 */
public fun TsSatisfiesExpression.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> CallExpressionImpl
 */
public fun TsSatisfiesExpression.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> NewExpressionImpl
 */
public fun TsSatisfiesExpression.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> SequenceExpressionImpl
 */
public fun TsSatisfiesExpression.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> IdentifierImpl
 */
public fun TsSatisfiesExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> StringLiteralImpl
 */
public fun TsSatisfiesExpression.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> BooleanLiteralImpl
 */
public fun TsSatisfiesExpression.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> NullLiteralImpl
 */
public fun TsSatisfiesExpression.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> NumericLiteralImpl
 */
public fun TsSatisfiesExpression.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> BigIntLiteralImpl
 */
public fun TsSatisfiesExpression.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> RegExpLiteralImpl
 */
public fun TsSatisfiesExpression.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> JSXTextImpl
 */
public fun TsSatisfiesExpression.jSXText(block: JSXText.() -> Unit): JSXText =
    JSXTextImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> TemplateLiteralImpl
 */
public fun TsSatisfiesExpression.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral
    = TemplateLiteralImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> TaggedTemplateExpressionImpl
 */
public
    fun TsSatisfiesExpression.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> ArrowFunctionExpressionImpl
 */
public fun TsSatisfiesExpression.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> ClassExpressionImpl
 */
public fun TsSatisfiesExpression.classExpression(block: ClassExpression.() -> Unit): ClassExpression
    = ClassExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> YieldExpressionImpl
 */
public fun TsSatisfiesExpression.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression
    = YieldExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> MetaPropertyImpl
 */
public fun TsSatisfiesExpression.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> AwaitExpressionImpl
 */
public fun TsSatisfiesExpression.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression
    = AwaitExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> ParenthesisExpressionImpl
 */
public fun TsSatisfiesExpression.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> JSXMemberExpressionImpl
 */
public fun TsSatisfiesExpression.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> JSXNamespacedNameImpl
 */
public fun TsSatisfiesExpression.jSXNamespacedName(block: JSXNamespacedName.() -> Unit):
    JSXNamespacedName = JSXNamespacedNameImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> JSXEmptyExpressionImpl
 */
public fun TsSatisfiesExpression.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> JSXElementImpl
 */
public fun TsSatisfiesExpression.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> JSXFragmentImpl
 */
public fun TsSatisfiesExpression.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> TsTypeAssertionImpl
 */
public fun TsSatisfiesExpression.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion
    = TsTypeAssertionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> TsConstAssertionImpl
 */
public fun TsSatisfiesExpression.tsConstAssertion(block: TsConstAssertion.() -> Unit):
    TsConstAssertion = TsConstAssertionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> TsNonNullExpressionImpl
 */
public fun TsSatisfiesExpression.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> TsAsExpressionImpl
 */
public fun TsSatisfiesExpression.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> TsInstantiationImpl
 */
public fun TsSatisfiesExpression.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation
    = TsInstantiationImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> PrivateNameImpl
 */
public fun TsSatisfiesExpression.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> OptionalChainingExpressionImpl
 */
public
    fun TsSatisfiesExpression.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * TsSatisfiesExpression#expression: Expression
 * extension function for create Expression -> InvalidImpl
 */
public fun TsSatisfiesExpression.invalid(block: Invalid.() -> Unit): Invalid =
    InvalidImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsKeywordTypeImpl
 */
public fun TsSatisfiesExpression.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsThisTypeImpl
 */
public fun TsSatisfiesExpression.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsFunctionTypeImpl
 */
public fun TsSatisfiesExpression.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsConstructorTypeImpl
 */
public fun TsSatisfiesExpression.tsConstructorType(block: TsConstructorType.() -> Unit):
    TsConstructorType = TsConstructorTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeReferenceImpl
 */
public fun TsSatisfiesExpression.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference
    = TsTypeReferenceImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeQueryImpl
 */
public fun TsSatisfiesExpression.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeLiteralImpl
 */
public fun TsSatisfiesExpression.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsArrayTypeImpl
 */
public fun TsSatisfiesExpression.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsTupleTypeImpl
 */
public fun TsSatisfiesExpression.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsOptionalTypeImpl
 */
public fun TsSatisfiesExpression.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsRestTypeImpl
 */
public fun TsSatisfiesExpression.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsUnionTypeImpl
 */
public fun TsSatisfiesExpression.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsIntersectionTypeImpl
 */
public fun TsSatisfiesExpression.tsIntersectionType(block: TsIntersectionType.() -> Unit):
    TsIntersectionType = TsIntersectionTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsConditionalTypeImpl
 */
public fun TsSatisfiesExpression.tsConditionalType(block: TsConditionalType.() -> Unit):
    TsConditionalType = TsConditionalTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsInferTypeImpl
 */
public fun TsSatisfiesExpression.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsParenthesizedTypeImpl
 */
public fun TsSatisfiesExpression.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeOperatorImpl
 */
public fun TsSatisfiesExpression.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsIndexedAccessTypeImpl
 */
public fun TsSatisfiesExpression.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsMappedTypeImpl
 */
public fun TsSatisfiesExpression.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsLiteralTypeImpl
 */
public fun TsSatisfiesExpression.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsTypePredicateImpl
 */
public fun TsSatisfiesExpression.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate
    = TsTypePredicateImpl().apply(block)

/**
 * TsSatisfiesExpression#typeAnnotation: TsType
 * extension function for create TsType -> TsImportTypeImpl
 */
public fun TsSatisfiesExpression.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsSatisfiesExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsSatisfiesExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
