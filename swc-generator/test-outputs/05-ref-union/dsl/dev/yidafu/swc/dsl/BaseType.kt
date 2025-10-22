package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BaseType
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TypeA
import dev.yidafu.swc.types.TypeAImpl
import dev.yidafu.swc.types.TypeB
import dev.yidafu.swc.types.TypeBImpl
import dev.yidafu.swc.types.TypeC
import dev.yidafu.swc.types.TypeCImpl
import kotlin.Unit

/**
 * subtype of BaseType
 */
public fun BaseType.typeA(block: TypeA.() -> Unit): TypeA = TypeAImpl().apply(block)

/**
 * subtype of BaseType
 */
public fun BaseType.typeB(block: TypeB.() -> Unit): TypeB = TypeBImpl().apply(block)

/**
 * subtype of BaseType
 */
public fun BaseType.typeC(block: TypeC.() -> Unit): TypeC = TypeCImpl().apply(block)

/**
 * BaseType#kind: String
 * extension function for create String -> String
 */
public fun BaseType.string(block: String.() -> Unit): String = String().apply(block)
