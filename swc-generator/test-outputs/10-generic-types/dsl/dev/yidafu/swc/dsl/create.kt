package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Constrained
import dev.yidafu.swc.types.ConstrainedImpl
import dev.yidafu.swc.types.Container
import dev.yidafu.swc.types.ContainerImpl
import dev.yidafu.swc.types.GenericArray
import dev.yidafu.swc.types.GenericArrayImpl
import dev.yidafu.swc.types.Nested
import dev.yidafu.swc.types.NestedImpl
import dev.yidafu.swc.types.Pair
import dev.yidafu.swc.types.PairImpl
import kotlin.Unit

public fun createContainer(block: Container.() -> Unit): Container = ContainerImpl().apply(block)

public fun createPair(block: Pair.() -> Unit): Pair = PairImpl().apply(block)

public fun createConstrained(block: Constrained.() -> Unit): Constrained =
    ConstrainedImpl().apply(block)

public fun createGenericArray(block: GenericArray.() -> Unit): GenericArray =
    GenericArrayImpl().apply(block)

public fun createNested(block: Nested.() -> Unit): Nested = NestedImpl().apply(block)
