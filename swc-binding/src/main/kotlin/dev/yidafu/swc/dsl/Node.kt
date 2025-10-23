package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.Node
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.StringLiteral
import kotlin.String
import kotlin.Unit

/**
 * subtype of Node
 */
public fun Node.identifier(block: Node.() -> Unit): Identifier {
}

/**
 * subtype of Node
 */
public fun Node.stringLiteral(block: Node.() -> Unit): StringLiteral {
}

/**
 * subtype of Node
 */
public fun Node.numericLiteral(block: Node.() -> Unit): NumericLiteral {
}

/**
 * subtype of Node
 */
public fun Node.arrayPattern(block: Node.() -> Unit): ArrayPattern {
}

/**
 * Node#type: String
 * extension function for create String -> String
 */
public fun Node.string(block: Node.() -> Unit): String {
}
