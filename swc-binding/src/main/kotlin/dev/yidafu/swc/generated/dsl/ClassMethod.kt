// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:16.894767

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BigIntLiteral
import dev.yidafu.swc.generated.ClassMethod
import dev.yidafu.swc.generated.ComputedPropName
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.NumericLiteral
import dev.yidafu.swc.generated.StringLiteral
import kotlin.Unit

/**
 * ClassMethod#key: PropertyName?
 * extension function for create PropertyName? -> Identifier
 */
public fun ClassMethod.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * ClassMethod#key: PropertyName?
 * extension function for create PropertyName? -> StringLiteral
 */
public fun ClassMethod.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * ClassMethod#key: PropertyName?
 * extension function for create PropertyName? -> NumericLiteral
 */
public fun ClassMethod.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * ClassMethod#key: PropertyName?
 * extension function for create PropertyName? -> BigIntLiteral
 */
public fun ClassMethod.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)

/**
 * ClassMethod#key: PropertyName?
 * extension function for create PropertyName? -> ComputedPropName
 */
public fun ClassMethod.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropName().apply(block)
