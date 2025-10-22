package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AssignmentPatternProperty
import dev.yidafu.swc.types.AssignmentPatternPropertyImpl
import dev.yidafu.swc.types.KeyValuePatternProperty
import dev.yidafu.swc.types.KeyValuePatternPropertyImpl
import dev.yidafu.swc.types.ObjectPatternProperty
import dev.yidafu.swc.types.RestElement
import dev.yidafu.swc.types.RestElementImpl
import kotlin.Unit

/**
 * subtype of ObjectPatternProperty
 */
public fun ObjectPatternProperty.keyValuePatternProperty(block: KeyValuePatternProperty.() -> Unit):
    KeyValuePatternProperty = KeyValuePatternPropertyImpl().apply(block)

/**
 * subtype of ObjectPatternProperty
 */
public
    fun ObjectPatternProperty.assignmentPatternProperty(block: AssignmentPatternProperty.() -> Unit):
    AssignmentPatternProperty = AssignmentPatternPropertyImpl().apply(block)

/**
 * subtype of ObjectPatternProperty
 */
public fun ObjectPatternProperty.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)
