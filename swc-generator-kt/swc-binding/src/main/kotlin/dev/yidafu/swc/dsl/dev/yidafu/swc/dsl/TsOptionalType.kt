package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
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
import dev.yidafu.swc.types.TsMappedTypeImpl
import dev.yidafu.swc.types.TsOptionalType
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
 * TsOptionalType#type: String
 * extension function for create String -> String
 */
public fun TsOptionalType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsKeywordTypeImpl
 */
public fun TsOptionalType.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsThisTypeImpl
 */
public fun TsOptionalType.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsFunctionTypeImpl
 */
public fun TsOptionalType.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsConstructorTypeImpl
 */
public fun TsOptionalType.tsConstructorType(block: TsConstructorType.() -> Unit): TsConstructorType
    = TsConstructorTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeReferenceImpl
 */
public fun TsOptionalType.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeQueryImpl
 */
public fun TsOptionalType.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeLiteralImpl
 */
public fun TsOptionalType.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsArrayTypeImpl
 */
public fun TsOptionalType.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsTupleTypeImpl
 */
public fun TsOptionalType.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsRestTypeImpl
 */
public fun TsOptionalType.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsUnionTypeImpl
 */
public fun TsOptionalType.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsIntersectionTypeImpl
 */
public fun TsOptionalType.tsIntersectionType(block: TsIntersectionType.() -> Unit):
    TsIntersectionType = TsIntersectionTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsConditionalTypeImpl
 */
public fun TsOptionalType.tsConditionalType(block: TsConditionalType.() -> Unit): TsConditionalType
    = TsConditionalTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsInferTypeImpl
 */
public fun TsOptionalType.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsParenthesizedTypeImpl
 */
public fun TsOptionalType.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeOperatorImpl
 */
public fun TsOptionalType.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsIndexedAccessTypeImpl
 */
public fun TsOptionalType.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsMappedTypeImpl
 */
public fun TsOptionalType.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsLiteralTypeImpl
 */
public fun TsOptionalType.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsTypePredicateImpl
 */
public fun TsOptionalType.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicateImpl().apply(block)

/**
 * TsOptionalType#typeAnnotation: TsType
 * extension function for create TsType -> TsImportTypeImpl
 */
public fun TsOptionalType.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsOptionalType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsOptionalType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
