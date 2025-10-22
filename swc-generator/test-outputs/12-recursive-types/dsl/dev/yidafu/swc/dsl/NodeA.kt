package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.NodeA
import dev.yidafu.swc.types.NodeB
import dev.yidafu.swc.types.NodeBImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * NodeA#type: String
 * extension function for create String -> String
 */
public fun NodeA.string(block: String.() -> Unit): String = String().apply(block)

/**
 * NodeA#next: NodeB
 * extension function for create NodeB -> NodeBImpl
 */
public fun NodeA.nodeB(block: NodeB.() -> Unit): NodeB = NodeBImpl().apply(block)
