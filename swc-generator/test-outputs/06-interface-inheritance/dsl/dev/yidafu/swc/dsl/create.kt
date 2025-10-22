package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ChildInterface
import dev.yidafu.swc.types.ChildInterfaceImpl
import dev.yidafu.swc.types.Combined
import dev.yidafu.swc.types.CombinedImpl
import dev.yidafu.swc.types.GrandChildInterface
import dev.yidafu.swc.types.GrandChildInterfaceImpl
import dev.yidafu.swc.types.MixinA
import dev.yidafu.swc.types.MixinAImpl
import dev.yidafu.swc.types.MixinB
import dev.yidafu.swc.types.MixinBImpl
import dev.yidafu.swc.types.ParentInterface
import dev.yidafu.swc.types.ParentInterfaceImpl
import kotlin.Unit

public fun createParentInterface(block: ParentInterface.() -> Unit): ParentInterface =
    ParentInterfaceImpl().apply(block)

public fun createChildInterface(block: ChildInterface.() -> Unit): ChildInterface =
    ChildInterfaceImpl().apply(block)

public fun createGrandChildInterface(block: GrandChildInterface.() -> Unit): GrandChildInterface =
    GrandChildInterfaceImpl().apply(block)

public fun createMixinA(block: MixinA.() -> Unit): MixinA = MixinAImpl().apply(block)

public fun createMixinB(block: MixinB.() -> Unit): MixinB = MixinBImpl().apply(block)

public fun createCombined(block: Combined.() -> Unit): Combined = CombinedImpl().apply(block)
