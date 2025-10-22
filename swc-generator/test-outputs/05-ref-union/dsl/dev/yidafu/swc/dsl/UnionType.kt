package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.TypeA
import dev.yidafu.swc.types.TypeAImpl
import dev.yidafu.swc.types.TypeB
import dev.yidafu.swc.types.TypeBImpl
import dev.yidafu.swc.types.TypeC
import dev.yidafu.swc.types.TypeCImpl
import dev.yidafu.swc.types.UnionType
import kotlin.Unit

/**
 * subtype of UnionType
 */
public fun UnionType.typeA(block: TypeA.() -> Unit): TypeA = TypeAImpl().apply(block)

/**
 * subtype of UnionType
 */
public fun UnionType.typeB(block: TypeB.() -> Unit): TypeB = TypeBImpl().apply(block)

/**
 * subtype of UnionType
 */
public fun UnionType.typeC(block: TypeC.() -> Unit): TypeC = TypeCImpl().apply(block)
