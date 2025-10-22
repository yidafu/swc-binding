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
import kotlin.Unit

/**
 * TsUnionType#type: String
 * extension function for create String -> String
 */
public fun TsUnionType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsKeywordTypeImpl
 */
public fun TsUnionType.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsThisTypeImpl
 */
public fun TsUnionType.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsFunctionTypeImpl
 */
public fun TsUnionType.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsConstructorTypeImpl
 */
public fun TsUnionType.tsConstructorType(block: TsConstructorType.() -> Unit): TsConstructorType =
    TsConstructorTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeReferenceImpl
 */
public fun TsUnionType.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeQueryImpl
 */
public fun TsUnionType.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeLiteralImpl
 */
public fun TsUnionType.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsArrayTypeImpl
 */
public fun TsUnionType.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTupleTypeImpl
 */
public fun TsUnionType.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsOptionalTypeImpl
 */
public fun TsUnionType.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsRestTypeImpl
 */
public fun TsUnionType.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsIntersectionTypeImpl
 */
public fun TsUnionType.tsIntersectionType(block: TsIntersectionType.() -> Unit): TsIntersectionType
    = TsIntersectionTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsConditionalTypeImpl
 */
public fun TsUnionType.tsConditionalType(block: TsConditionalType.() -> Unit): TsConditionalType =
    TsConditionalTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsInferTypeImpl
 */
public fun TsUnionType.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsParenthesizedTypeImpl
 */
public fun TsUnionType.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeOperatorImpl
 */
public fun TsUnionType.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsIndexedAccessTypeImpl
 */
public fun TsUnionType.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsMappedTypeImpl
 */
public fun TsUnionType.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsLiteralTypeImpl
 */
public fun TsUnionType.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypePredicateImpl
 */
public fun TsUnionType.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicateImpl().apply(block)

/**
 * TsUnionType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsImportTypeImpl
 */
public fun TsUnionType.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsUnionType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsUnionType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
