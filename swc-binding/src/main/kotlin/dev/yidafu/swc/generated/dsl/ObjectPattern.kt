// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.033921

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.AssignmentPatternProperty
import dev.yidafu.swc.generated.KeyValuePatternProperty
import dev.yidafu.swc.generated.ObjectPattern
import dev.yidafu.swc.generated.RestElement
import kotlin.Unit

/**
 * ObjectPattern#properties: Array<ObjectPatternProperty>?
 * extension function for create Array<ObjectPatternProperty>? -> RestElement
 */
public fun ObjectPattern.restElement(block: RestElement.() -> Unit): RestElement =
    RestElement().apply(block)

/**
 * ObjectPattern#properties: Array<ObjectPatternProperty>?
 * extension function for create Array<ObjectPatternProperty>? -> KeyValuePatternProperty
 */
public fun ObjectPattern.keyValuePatternProperty(block: KeyValuePatternProperty.() -> Unit):
    KeyValuePatternProperty = KeyValuePatternProperty().apply(block)

/**
 * ObjectPattern#properties: Array<ObjectPatternProperty>?
 * extension function for create Array<ObjectPatternProperty>? -> AssignmentPatternProperty
 */
public fun ObjectPattern.assignmentPatternProperty(block: AssignmentPatternProperty.() -> Unit):
    AssignmentPatternProperty = AssignmentPatternProperty().apply(block)
