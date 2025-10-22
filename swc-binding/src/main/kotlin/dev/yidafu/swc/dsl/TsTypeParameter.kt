package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
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
import dev.yidafu.swc.types.TsTypeParameter
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
 * TsTypeParameter#type: String
 * extension function for create String -> String
 */
public fun TsTypeParameter.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsTypeParameter#name: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun TsTypeParameter.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsTypeParameter#out: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsTypeParameter.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsKeywordTypeImpl
 */
public fun TsTypeParameter.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsThisTypeImpl
 */
public fun TsTypeParameter.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsFunctionTypeImpl
 */
public fun TsTypeParameter.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsConstructorTypeImpl
 */
public fun TsTypeParameter.tsConstructorType(block: TsConstructorType.() -> Unit): TsConstructorType
    = TsConstructorTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsTypeReferenceImpl
 */
public fun TsTypeParameter.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsTypeQueryImpl
 */
public fun TsTypeParameter.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsTypeLiteralImpl
 */
public fun TsTypeParameter.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsArrayTypeImpl
 */
public fun TsTypeParameter.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsTupleTypeImpl
 */
public fun TsTypeParameter.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsOptionalTypeImpl
 */
public fun TsTypeParameter.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsRestTypeImpl
 */
public fun TsTypeParameter.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsUnionTypeImpl
 */
public fun TsTypeParameter.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsIntersectionTypeImpl
 */
public fun TsTypeParameter.tsIntersectionType(block: TsIntersectionType.() -> Unit):
    TsIntersectionType = TsIntersectionTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsConditionalTypeImpl
 */
public fun TsTypeParameter.tsConditionalType(block: TsConditionalType.() -> Unit): TsConditionalType
    = TsConditionalTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsInferTypeImpl
 */
public fun TsTypeParameter.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsParenthesizedTypeImpl
 */
public fun TsTypeParameter.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsTypeOperatorImpl
 */
public fun TsTypeParameter.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsIndexedAccessTypeImpl
 */
public fun TsTypeParameter.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsMappedTypeImpl
 */
public fun TsTypeParameter.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsLiteralTypeImpl
 */
public fun TsTypeParameter.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsTypePredicateImpl
 */
public fun TsTypeParameter.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicateImpl().apply(block)

/**
 * TsTypeParameter#default: TsType
 * extension function for create TsType -> TsImportTypeImpl
 */
public fun TsTypeParameter.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsTypeParameter#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsTypeParameter.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
