package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ChildInterface
import dev.yidafu.swc.types.ChildInterfaceImpl
import dev.yidafu.swc.types.GrandChildInterface
import dev.yidafu.swc.types.GrandChildInterfaceImpl
import dev.yidafu.swc.types.ParentInterface
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * subtype of ParentInterface
 */
public fun ParentInterface.grandChildInterface(block: GrandChildInterface.() -> Unit):
    GrandChildInterface = GrandChildInterfaceImpl().apply(block)

/**
 * subtype of ParentInterface
 */
public fun ParentInterface.childInterface(block: ChildInterface.() -> Unit): ChildInterface =
    ChildInterfaceImpl().apply(block)

/**
 * ParentInterface#parentProp: String
 * extension function for create String -> String
 */
public fun ParentInterface.string(block: String.() -> Unit): String = String().apply(block)
