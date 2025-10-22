package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AssignmentProperty
import dev.yidafu.swc.types.AssignmentPropertyImpl
import dev.yidafu.swc.types.GetterProperty
import dev.yidafu.swc.types.GetterPropertyImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.KeyValueProperty
import dev.yidafu.swc.types.KeyValuePropertyImpl
import dev.yidafu.swc.types.MethodProperty
import dev.yidafu.swc.types.MethodPropertyImpl
import dev.yidafu.swc.types.Property
import dev.yidafu.swc.types.SetterProperty
import dev.yidafu.swc.types.SetterPropertyImpl
import kotlin.Unit

/**
 * subtype of Property
 */
public fun Property.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * subtype of Property
 */
public fun Property.keyValueProperty(block: KeyValueProperty.() -> Unit): KeyValueProperty =
    KeyValuePropertyImpl().apply(block)

/**
 * subtype of Property
 */
public fun Property.assignmentProperty(block: AssignmentProperty.() -> Unit): AssignmentProperty =
    AssignmentPropertyImpl().apply(block)

/**
 * subtype of Property
 */
public fun Property.getterProperty(block: GetterProperty.() -> Unit): GetterProperty =
    GetterPropertyImpl().apply(block)

/**
 * subtype of Property
 */
public fun Property.setterProperty(block: SetterProperty.() -> Unit): SetterProperty =
    SetterPropertyImpl().apply(block)

/**
 * subtype of Property
 */
public fun Property.methodProperty(block: MethodProperty.() -> Unit): MethodProperty =
    MethodPropertyImpl().apply(block)
