package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsArrayType
import dev.yidafu.swc.types.TsArrayTypeImpl
import dev.yidafu.swc.types.TsConditionalType
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
import dev.yidafu.swc.types.TsUnionTypeImpl
import kotlin.Unit

/**
 * TsConditionalType#type: String
 * extension function for create String -> String
 */
public fun TsConditionalType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsKeywordTypeImpl
 */
public fun TsConditionalType.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsThisTypeImpl
 */
public fun TsConditionalType.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsFunctionTypeImpl
 */
public fun TsConditionalType.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsConstructorTypeImpl
 */
public fun TsConditionalType.tsConstructorType(block: TsConstructorType.() -> Unit):
    TsConstructorType = TsConstructorTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsTypeReferenceImpl
 */
public fun TsConditionalType.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsTypeQueryImpl
 */
public fun TsConditionalType.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsTypeLiteralImpl
 */
public fun TsConditionalType.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsArrayTypeImpl
 */
public fun TsConditionalType.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsTupleTypeImpl
 */
public fun TsConditionalType.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsOptionalTypeImpl
 */
public fun TsConditionalType.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsRestTypeImpl
 */
public fun TsConditionalType.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsUnionTypeImpl
 */
public fun TsConditionalType.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsIntersectionTypeImpl
 */
public fun TsConditionalType.tsIntersectionType(block: TsIntersectionType.() -> Unit):
    TsIntersectionType = TsIntersectionTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsInferTypeImpl
 */
public fun TsConditionalType.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsParenthesizedTypeImpl
 */
public fun TsConditionalType.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsTypeOperatorImpl
 */
public fun TsConditionalType.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsIndexedAccessTypeImpl
 */
public fun TsConditionalType.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsMappedTypeImpl
 */
public fun TsConditionalType.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsLiteralTypeImpl
 */
public fun TsConditionalType.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsTypePredicateImpl
 */
public fun TsConditionalType.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicateImpl().apply(block)

/**
 * TsConditionalType#falseType: TsType
 * extension function for create TsType -> TsImportTypeImpl
 */
public fun TsConditionalType.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsConditionalType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsConditionalType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
