package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TemplateElement
import dev.yidafu.swc.types.TemplateElementImpl
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
import dev.yidafu.swc.types.TsTemplateLiteralType
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
 * TsTemplateLiteralType#type: String
 * extension function for create String -> String
 */
public fun TsTemplateLiteralType.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsKeywordTypeImpl
 */
public fun TsTemplateLiteralType.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsThisTypeImpl
 */
public fun TsTemplateLiteralType.tsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsFunctionTypeImpl
 */
public fun TsTemplateLiteralType.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsConstructorTypeImpl
 */
public fun TsTemplateLiteralType.tsConstructorType(block: TsConstructorType.() -> Unit):
    TsConstructorType = TsConstructorTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeReferenceImpl
 */
public fun TsTemplateLiteralType.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference
    = TsTypeReferenceImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeQueryImpl
 */
public fun TsTemplateLiteralType.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeLiteralImpl
 */
public fun TsTemplateLiteralType.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsArrayTypeImpl
 */
public fun TsTemplateLiteralType.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTupleTypeImpl
 */
public fun TsTemplateLiteralType.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsOptionalTypeImpl
 */
public fun TsTemplateLiteralType.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsRestTypeImpl
 */
public fun TsTemplateLiteralType.tsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsUnionTypeImpl
 */
public fun TsTemplateLiteralType.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsIntersectionTypeImpl
 */
public fun TsTemplateLiteralType.tsIntersectionType(block: TsIntersectionType.() -> Unit):
    TsIntersectionType = TsIntersectionTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsConditionalTypeImpl
 */
public fun TsTemplateLiteralType.tsConditionalType(block: TsConditionalType.() -> Unit):
    TsConditionalType = TsConditionalTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsInferTypeImpl
 */
public fun TsTemplateLiteralType.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsParenthesizedTypeImpl
 */
public fun TsTemplateLiteralType.tsParenthesizedType(block: TsParenthesizedType.() -> Unit):
    TsParenthesizedType = TsParenthesizedTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypeOperatorImpl
 */
public fun TsTemplateLiteralType.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsIndexedAccessTypeImpl
 */
public fun TsTemplateLiteralType.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit):
    TsIndexedAccessType = TsIndexedAccessTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsMappedTypeImpl
 */
public fun TsTemplateLiteralType.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsLiteralTypeImpl
 */
public fun TsTemplateLiteralType.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsTypePredicateImpl
 */
public fun TsTemplateLiteralType.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate
    = TsTypePredicateImpl().apply(block)

/**
 * TsTemplateLiteralType#types: Array<TsType>
 * extension function for create Array<TsType> -> TsImportTypeImpl
 */
public fun TsTemplateLiteralType.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * TsTemplateLiteralType#quasis: Array<TemplateElement>
 * extension function for create Array<TemplateElement> -> TemplateElementImpl
 */
public fun TsTemplateLiteralType.templateElement(block: TemplateElement.() -> Unit): TemplateElement
    = TemplateElementImpl().apply(block)

/**
 * TsTemplateLiteralType#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsTemplateLiteralType.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
