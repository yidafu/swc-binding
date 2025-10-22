package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsQualifiedName
import dev.yidafu.swc.types.TsQualifiedNameImpl
import dev.yidafu.swc.types.TsTypeParameterInstantiation
import dev.yidafu.swc.types.TsTypeParameterInstantiationImpl
import dev.yidafu.swc.types.TsTypeReference
import kotlin.Unit

/**
 * TsTypeReference#type: String
 * extension function for create String -> String
 */
public fun TsTypeReference.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsTypeReference#typeName: TsEntityName
 * extension function for create TsEntityName -> TsQualifiedNameImpl
 */
public fun TsTypeReference.tsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedNameImpl().apply(block)

/**
 * TsTypeReference#typeName: TsEntityName
 * extension function for create TsEntityName -> IdentifierImpl
 */
public fun TsTypeReference.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * TsTypeReference#typeParams: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
public
    fun TsTypeReference.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * TsTypeReference#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun TsTypeReference.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
