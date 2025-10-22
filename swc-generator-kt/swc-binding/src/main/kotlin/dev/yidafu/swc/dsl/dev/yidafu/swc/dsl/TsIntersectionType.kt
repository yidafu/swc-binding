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
 * TsIntersectionType#type: String
 * extension function for create String -> String
 */
public fun TsIntersectionType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsKeywordTypeImpl
 */
public fun TsIntersectionType.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsThisTypeImpl
 */
public fun TsIntersectionType.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsFunctionTypeImpl
 */
public fun TsIntersectionType.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsConstructorTypeImpl
 */
public fun TsIntersectionType.tsConstructorType(block: TsConstructorType.() -> Unit):
    TsConstructorType = TsConstructorTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeReferenceImpl
 */
public fun TsIntersectionType.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeQueryImpl
 */
public fun TsIntersectionType.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeLiteralImpl
 */
public fun TsIntersectionType.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsArrayTypeImpl
 */
public fun TsIntersectionType.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTupleTypeImpl
 */
public fun TsIntersectionType.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsOptionalTypeImpl
 */
public fun TsIntersectionType.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsRestTypeImpl
 */
public fun TsIntersectionType.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsUnionTypeImpl
 */
public fun TsIntersectionType.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsConditionalTypeImpl
 */
public fun TsIntersectionType.tsConditionalType(block: TsConditionalType.() -> Unit):
    TsConditionalType = TsConditionalTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsInferTypeImpl
 */
public fun TsIntersectionType.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsParenthesizedTypeImpl
 */
public fun TsIntersectionType.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeOperatorImpl
 */
public fun TsIntersectionType.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsIndexedAccessTypeImpl
 */
public fun TsIntersectionType.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsMappedTypeImpl
 */
public fun TsIntersectionType.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsLiteralTypeImpl
 */
public fun TsIntersectionType.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypePredicateImpl
 */
public fun TsIntersectionType.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicateImpl().apply(block)

/**
 * TsIntersectionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsImportTypeImpl
 */
public fun TsIntersectionType.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsIntersectionType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsIntersectionType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
