package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ListNode
import dev.yidafu.swc.types.ListNodeImpl
import dev.yidafu.swc.types.NodeA
import dev.yidafu.swc.types.NodeAImpl
import dev.yidafu.swc.types.NodeB
import dev.yidafu.swc.types.NodeBImpl
import dev.yidafu.swc.types.TreeNode
import dev.yidafu.swc.types.TreeNodeImpl
import kotlin.Unit

public fun createTreeNode(block: TreeNode.() -> Unit): TreeNode = TreeNodeImpl().apply(block)

public fun createNodeA(block: NodeA.() -> Unit): NodeA = NodeAImpl().apply(block)

public fun createNodeB(block: NodeB.() -> Unit): NodeB = NodeBImpl().apply(block)

public fun createListNode(block: ListNode.() -> Unit): ListNode = ListNodeImpl().apply(block)
