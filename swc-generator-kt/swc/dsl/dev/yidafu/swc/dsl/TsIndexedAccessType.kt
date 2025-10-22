package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
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
 * TsIndexedAccessType#type: String
 * extension function for create String -> String
 */
public fun TsIndexedAccessType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsIndexedAccessType#readonly: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsIndexedAccessType.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsKeywordTypeImpl
 */
public fun TsIndexedAccessType.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsThisTypeImpl
 */
public fun TsIndexedAccessType.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsFunctionTypeImpl
 */
public fun TsIndexedAccessType.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsConstructorTypeImpl
 */
public fun TsIndexedAccessType.tsConstructorType(block: TsConstructorType.() -> Unit):
    TsConstructorType = TsConstructorTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsTypeReferenceImpl
 */
public fun TsIndexedAccessType.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsTypeQueryImpl
 */
public fun TsIndexedAccessType.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsTypeLiteralImpl
 */
public fun TsIndexedAccessType.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsArrayTypeImpl
 */
public fun TsIndexedAccessType.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsTupleTypeImpl
 */
public fun TsIndexedAccessType.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsOptionalTypeImpl
 */
public fun TsIndexedAccessType.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsRestTypeImpl
 */
public fun TsIndexedAccessType.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsUnionTypeImpl
 */
public fun TsIndexedAccessType.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsIntersectionTypeImpl
 */
public fun TsIndexedAccessType.tsIntersectionType(block: TsIntersectionType.() -> Unit):
    TsIntersectionType = TsIntersectionTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsConditionalTypeImpl
 */
public fun TsIndexedAccessType.tsConditionalType(block: TsConditionalType.() -> Unit):
    TsConditionalType = TsConditionalTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsInferTypeImpl
 */
public fun TsIndexedAccessType.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsParenthesizedTypeImpl
 */
public fun TsIndexedAccessType.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsTypeOperatorImpl
 */
public fun TsIndexedAccessType.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsMappedTypeImpl
 */
public fun TsIndexedAccessType.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsLiteralTypeImpl
 */
public fun TsIndexedAccessType.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsTypePredicateImpl
 */
public fun TsIndexedAccessType.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicateImpl().apply(block)

/**
 * TsIndexedAccessType#indexType: TsType
 * extension function for create TsType -> TsImportTypeImpl
 */
public fun TsIndexedAccessType.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsIndexedAccessType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsIndexedAccessType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
