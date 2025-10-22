package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.ComputedPropName
import dev.yidafu.swc.types.ComputedPropNameImpl
import dev.yidafu.swc.types.GetterProperty
import dev.yidafu.swc.types.GetterPropertyImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.KeyValueProperty
import dev.yidafu.swc.types.KeyValuePropertyImpl
import dev.yidafu.swc.types.MethodProperty
import dev.yidafu.swc.types.MethodPropertyImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.PropBase
import dev.yidafu.swc.types.SetterProperty
import dev.yidafu.swc.types.SetterPropertyImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

/**
 * subtype of PropBase
 */
public fun PropBase.keyValueProperty(block: KeyValueProperty.() -> Unit): KeyValueProperty =
    KeyValuePropertyImpl().apply(block)

/**
 * subtype of PropBase
 */
public fun PropBase.getterProperty(block: GetterProperty.() -> Unit): GetterProperty =
    GetterPropertyImpl().apply(block)

/**
 * subtype of PropBase
 */
public fun PropBase.setterProperty(block: SetterProperty.() -> Unit): SetterProperty =
    SetterPropertyImpl().apply(block)

/**
 * subtype of PropBase
 */
public fun PropBase.methodProperty(block: MethodProperty.() -> Unit): MethodProperty =
    MethodPropertyImpl().apply(block)

/**
 * PropBase#key: PropertyName
 * extension function for create PropertyName -> IdentifierImpl
 */
public fun PropBase.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * PropBase#key: PropertyName
 * extension function for create PropertyName -> StringLiteralImpl
 */
public fun PropBase.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * PropBase#key: PropertyName
 * extension function for create PropertyName -> NumericLiteralImpl
 */
public fun PropBase.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * PropBase#key: PropertyName
 * extension function for create PropertyName -> ComputedPropNameImpl
 */
public fun PropBase.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropNameImpl().apply(block)

/**
 * PropBase#key: PropertyName
 * extension function for create PropertyName -> BigIntLiteralImpl
 */
public fun PropBase.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * PropBase#type: String
 * extension function for create String -> String
 */
public fun PropBase.string(block: String.() -> Unit): String = String().apply(block)
