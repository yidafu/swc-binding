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
import dev.yidafu.swc.types.TsTypeAliasDeclaration
import dev.yidafu.swc.types.TsTypeLiteral
import dev.yidafu.swc.types.TsTypeLiteralImpl
import dev.yidafu.swc.types.TsTypeOperator
import dev.yidafu.swc.types.TsTypeOperatorImpl
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclarationImpl
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
 * TsTypeAliasDeclaration#type: String
 * extension function for create String -> String
 */
public fun TsTypeAliasDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsTypeAliasDeclaration#declare: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TsTypeAliasDeclaration.boolean(block: Boolean.() -> Unit): Boolean =
    Boolean().apply(block)

/**
 * TsTypeAliasDeclaration#id: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun TsTypeAliasDeclaration.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeParams: TsTypeParameterDeclaration
 * extension function for create TsTypeParameterDeclaration -> TsTypeParameterDeclarationImpl
 */
public
    fun TsTypeAliasDeclaration.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsKeywordTypeImpl
 */
public fun TsTypeAliasDeclaration.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsThisTypeImpl
 */
public fun TsTypeAliasDeclaration.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsFunctionTypeImpl
 */
public fun TsTypeAliasDeclaration.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsConstructorTypeImpl
 */
public fun TsTypeAliasDeclaration.tsConstructorType(block: TsConstructorType.() -> Unit):
    TsConstructorType = TsConstructorTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeReferenceImpl
 */
public fun TsTypeAliasDeclaration.tsTypeReference(block: TsTypeReference.() -> Unit):
    TsTypeReference = TsTypeReferenceImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeQueryImpl
 */
public fun TsTypeAliasDeclaration.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeLiteralImpl
 */
public fun TsTypeAliasDeclaration.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsArrayTypeImpl
 */
public fun TsTypeAliasDeclaration.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsTupleTypeImpl
 */
public fun TsTypeAliasDeclaration.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsOptionalTypeImpl
 */
public fun TsTypeAliasDeclaration.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsRestTypeImpl
 */
public fun TsTypeAliasDeclaration.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsUnionTypeImpl
 */
public fun TsTypeAliasDeclaration.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsIntersectionTypeImpl
 */
public fun TsTypeAliasDeclaration.tsIntersectionType(block: TsIntersectionType.() -> Unit):
    TsIntersectionType = TsIntersectionTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsConditionalTypeImpl
 */
public fun TsTypeAliasDeclaration.tsConditionalType(block: TsConditionalType.() -> Unit):
    TsConditionalType = TsConditionalTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsInferTypeImpl
 */
public fun TsTypeAliasDeclaration.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsParenthesizedTypeImpl
 */
public fun TsTypeAliasDeclaration.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsTypeOperatorImpl
 */
public fun TsTypeAliasDeclaration.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsIndexedAccessTypeImpl
 */
public fun TsTypeAliasDeclaration.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsMappedTypeImpl
 */
public fun TsTypeAliasDeclaration.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsLiteralTypeImpl
 */
public fun TsTypeAliasDeclaration.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsTypePredicateImpl
 */
public fun TsTypeAliasDeclaration.tsTypePredicate(block: TsTypePredicate.() -> Unit):
    TsTypePredicate = TsTypePredicateImpl().apply(block)

/**
 * TsTypeAliasDeclaration#typeAnnotation: TsType
 * extension function for create TsType -> TsImportTypeImpl
 */
public fun TsTypeAliasDeclaration.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsTypeAliasDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsTypeAliasDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
