package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TreeNode
import kotlin.Unit

/**
 * TreeNode#value: String
 * extension function for create String -> String
 */
public fun TreeNode.string(block: String.() -> Unit): String = String().apply(block)
