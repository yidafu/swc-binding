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
import dev.yidafu.swc.types.ObjectExpression
import dev.yidafu.swc.types.SetterProperty
import dev.yidafu.swc.types.SetterPropertyImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.SpreadElement
import dev.yidafu.swc.types.SpreadElementImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * ObjectExpression#type: String
 * extension function for create String -> String
 */
public fun ObjectExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ObjectExpression#properties: Array<Union.U2<SpreadElement, Property>>
 * extension function for create Array<Union.U2<SpreadElement, Property>> -> SpreadElementImpl
 */
public fun ObjectExpression.spreadElement(block: SpreadElement.() -> Unit): SpreadElement =
    SpreadElementImpl().apply(block)

/**
 * ObjectExpression#properties: Array<Union.U2<SpreadElement, Property>>
 * extension function for create Array<Union.U2<SpreadElement, Property>> -> IdentifierImpl
 */
public fun ObjectExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ObjectExpression#properties: Array<Union.U2<SpreadElement, Property>>
 * extension function for create Array<Union.U2<SpreadElement, Property>> -> KeyValuePropertyImpl
 */
public fun ObjectExpression.keyValueProperty(block: KeyValueProperty.() -> Unit): KeyValueProperty =
    KeyValuePropertyImpl().apply(block)

/**
 * ObjectExpression#properties: Array<Union.U2<SpreadElement, Property>>
 * extension function for create Array<Union.U2<SpreadElement, Property>> -> AssignmentPropertyImpl
 */
public fun ObjectExpression.assignmentProperty(block: AssignmentProperty.() -> Unit):
    AssignmentProperty = AssignmentPropertyImpl().apply(block)

/**
 * ObjectExpression#properties: Array<Union.U2<SpreadElement, Property>>
 * extension function for create Array<Union.U2<SpreadElement, Property>> -> GetterPropertyImpl
 */
public fun ObjectExpression.getterProperty(block: GetterProperty.() -> Unit): GetterProperty =
    GetterPropertyImpl().apply(block)

/**
 * ObjectExpression#properties: Array<Union.U2<SpreadElement, Property>>
 * extension function for create Array<Union.U2<SpreadElement, Property>> -> SetterPropertyImpl
 */
public fun ObjectExpression.setterProperty(block: SetterProperty.() -> Unit): SetterProperty =
    SetterPropertyImpl().apply(block)

/**
 * ObjectExpression#properties: Array<Union.U2<SpreadElement, Property>>
 * extension function for create Array<Union.U2<SpreadElement, Property>> -> MethodPropertyImpl
 */
public fun ObjectExpression.methodProperty(block: MethodProperty.() -> Unit): MethodProperty =
    MethodPropertyImpl().apply(block)

/**
 * ObjectExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ObjectExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)
