package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BaseType
import dev.yidafu.swc.types.BaseTypeImpl
import dev.yidafu.swc.types.ItemOne
import dev.yidafu.swc.types.ItemOneImpl
import dev.yidafu.swc.types.ItemTwo
import dev.yidafu.swc.types.ItemTwoImpl
import dev.yidafu.swc.types.TypeA
import dev.yidafu.swc.types.TypeAImpl
import dev.yidafu.swc.types.TypeB
import dev.yidafu.swc.types.TypeBImpl
import dev.yidafu.swc.types.TypeC
import dev.yidafu.swc.types.TypeCImpl
import kotlin.Unit

public fun createBaseType(block: BaseType.() -> Unit): BaseType = BaseTypeImpl().apply(block)

public fun createTypeA(block: TypeA.() -> Unit): TypeA = TypeAImpl().apply(block)

public fun createTypeB(block: TypeB.() -> Unit): TypeB = TypeBImpl().apply(block)

public fun createTypeC(block: TypeC.() -> Unit): TypeC = TypeCImpl().apply(block)

public fun createItemOne(block: ItemOne.() -> Unit): ItemOne = ItemOneImpl().apply(block)

public fun createItemTwo(block: ItemTwo.() -> Unit): ItemTwo = ItemTwoImpl().apply(block)
