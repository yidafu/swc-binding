package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ComplexValueIndex
import dev.yidafu.swc.types.ComplexValueIndexImpl
import dev.yidafu.swc.types.MixedIndex
import dev.yidafu.swc.types.MixedIndexImpl
import dev.yidafu.swc.types.NumberIndex
import dev.yidafu.swc.types.NumberIndexImpl
import dev.yidafu.swc.types.StringIndex
import dev.yidafu.swc.types.StringIndexImpl
import kotlin.Unit

public fun createStringIndex(block: StringIndex.() -> Unit): StringIndex =
    StringIndexImpl().apply(block)

public fun createNumberIndex(block: NumberIndex.() -> Unit): NumberIndex =
    NumberIndexImpl().apply(block)

public fun createMixedIndex(block: MixedIndex.() -> Unit): MixedIndex =
    MixedIndexImpl().apply(block)

public fun createComplexValueIndex(block: ComplexValueIndex.() -> Unit): ComplexValueIndex =
    ComplexValueIndexImpl().apply(block)
