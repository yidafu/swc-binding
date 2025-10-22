package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.TsConstructorType
import dev.yidafu.swc.types.TsConstructorTypeImpl
import dev.yidafu.swc.types.TsFnOrConstructorType
import dev.yidafu.swc.types.TsFunctionType
import dev.yidafu.swc.types.TsFunctionTypeImpl
import kotlin.Unit

/**
 * subtype of TsFnOrConstructorType
 */
public fun TsFnOrConstructorType.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * subtype of TsFnOrConstructorType
 */
public fun TsFnOrConstructorType.tsConstructorType(block: TsConstructorType.() -> Unit):
    TsConstructorType = TsConstructorTypeImpl().apply(block)
