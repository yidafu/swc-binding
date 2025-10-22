package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.NodeA
import dev.yidafu.swc.types.NodeAImpl
import dev.yidafu.swc.types.NodeB
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * NodeB#type: String
 * extension function for create String -> String
 */
public fun NodeB.string(block: String.() -> Unit): String = String().apply(block)

/**
 * NodeB#next: NodeA
 * extension function for create NodeA -> NodeAImpl
 */
public fun NodeB.nodeA(block: NodeA.() -> Unit): NodeA = NodeAImpl().apply(block)
