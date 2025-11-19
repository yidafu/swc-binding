// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.205554

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BigIntLiteral
import dev.yidafu.swc.generated.ComputedPropName
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.NumericLiteral
import dev.yidafu.swc.generated.PropBase
import dev.yidafu.swc.generated.StringLiteral
import kotlin.Unit

/**
 * PropBase#key: PropertyName?
 * extension function for create PropertyName? -> StringLiteral
 */
public fun PropBase.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * PropBase#key: PropertyName?
 * extension function for create PropertyName? -> NumericLiteral
 */
public fun PropBase.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * PropBase#key: PropertyName?
 * extension function for create PropertyName? -> BigIntLiteral
 */
public fun PropBase.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)

/**
 * PropBase#key: PropertyName?
 * extension function for create PropertyName? -> ComputedPropName
 */
public fun PropBase.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropName().apply(block)

/**
 * PropBase#key: PropertyName?
 * extension function for create PropertyName? -> Identifier
 */
public fun PropBase.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
