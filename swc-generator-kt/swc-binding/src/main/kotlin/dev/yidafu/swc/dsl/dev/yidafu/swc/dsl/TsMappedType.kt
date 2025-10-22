package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TruePlusMinus
import dev.yidafu.swc.types.TsArrayType
import dev.yidafu.swc.types.TsArrayTypeImpl
import dev.yidafu.swc.types.TsConditionalType
import dev.yidafu.swc.types.TsConditionalTypeImpl
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
import dev.yidafu.swc.types.TsIntersectionType
import dev.yidafu.swc.types.TsIntersectionTypeImpl
import dev.yidafu.swc.types.TsKeywordType
import dev.yidafu.swc.types.TsKeywordTypeImpl
import dev.yidafu.swc.types.TsLiteralType
import dev.yidafu.swc.types.TsLiteralTypeImpl
import dev.yidafu.swc.types.TsMappedType
import dev.yidafu.swc.types.TsOptionalType
import dev.yidafu.swc.types.TsOptionalTypeImpl
import dev.yidafu.swc.types.TsParenthesizedType
import dev.yidafu.swc.types.TsParenthesizedTypeImpl
import dev.yidafu.swc.types.TsRestType
import dev.yidafu.swc.types.TsRestTypeImpl
import dev.yidafu.swc.types.TsThisType
import dev.yidafu.swc.types.TsThisTypeImpl
import dev.yidafu.swc.types.TsTupleType
import dev.yidafu.swc.types.TsTupleTypeImpl
import dev.yidafu.swc.types.TsTypeLiteral
import dev.yidafu.swc.types.TsTypeLiteralImpl
import dev.yidafu.swc.types.TsTypeOperator
import dev.yidafu.swc.types.TsTypeOperatorImpl
import dev.yidafu.swc.types.TsTypeParameter
import dev.yidafu.swc.types.TsTypeParameterImpl
import dev.yidafu.swc.types.TsTypePredicate
import dev.yidafu.swc.types.TsTypePredicateImpl
import dev.yidafu.swc.types.TsTypeQuery
import dev.yidafu.swc.types.TsTypeQueryImpl
import dev.yidafu.swc.types.TsTypeReference
import dev.yidafu.swc.types.TsTypeReferenceImpl
import dev.yidafu.swc.types.TsUnionType
import dev.yidafu.swc.types.TsUnionTypeImpl
import kotlin.Unit

/**
 * TsMappedType#type: String
 * extension function for create String -> String
 */
public fun TsMappedType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsMappedType#optional: TruePlusMinus
 * extension function for create TruePlusMinus -> TruePlusMinus
 */
public fun TsMappedType.truePlusMinus(block: TruePlusMinus.() -> Unit): TruePlusMinus =
    TruePlusMinus().apply(block)

/**
 * TsMappedType#typeParam: TsTypeParameter
 * extension function for create TsTypeParameter -> TsTypeParameterImpl
 */
public fun TsMappedType.tsTypeParameter(block: TsTypeParameter.() -> Unit): TsTypeParameter =
    TsTypeParameterImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsKeywordTypeImpl
 */
public fun TsMappedType.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsThisTypeImpl
 */
public fun TsMappedType.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsFunctionTypeImpl
 */
public fun TsMappedType.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsConstructorTypeImpl
 */
public fun TsMappedType.tsConstructorType(block: TsConstructorType.() -> Unit): TsConstructorType =
    TsConstructorTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeReferenceImpl
 */
public fun TsMappedType.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeQueryImpl
 */
public fun TsMappedType.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeLiteralImpl
 */
public fun TsMappedType.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsArrayTypeImpl
 */
public fun TsMappedType.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsTupleTypeImpl
 */
public fun TsMappedType.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsOptionalTypeImpl
 */
public fun TsMappedType.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsRestTypeImpl
 */
public fun TsMappedType.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsUnionTypeImpl
 */
public fun TsMappedType.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsIntersectionTypeImpl
 */
public fun TsMappedType.tsIntersectionType(block: TsIntersectionType.() -> Unit): TsIntersectionType
    = TsIntersectionTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsConditionalTypeImpl
 */
public fun TsMappedType.tsConditionalType(block: TsConditionalType.() -> Unit): TsConditionalType =
    TsConditionalTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsInferTypeImpl
 */
public fun TsMappedType.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsParenthesizedTypeImpl
 */
public fun TsMappedType.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeOperatorImpl
 */
public fun TsMappedType.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsIndexedAccessTypeImpl
 */
public fun TsMappedType.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsLiteralTypeImpl
 */
public fun TsMappedType.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsTypePredicateImpl
 */
public fun TsMappedType.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicateImpl().apply(block)

/**
 * TsMappedType#typeAnnotation: TsType
 * extension function for create TsType -> TsImportTypeImpl
 */
public fun TsMappedType.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsMappedType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsMappedType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
