// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:22.95974

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BigIntLiteral
import dev.yidafu.swc.generated.ClassProperty
import dev.yidafu.swc.generated.ComputedPropName
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.NumericLiteral
import dev.yidafu.swc.generated.StringLiteral
import kotlin.Unit

/**
 * ClassProperty#key: PropertyName?
 * extension function for create PropertyName? -> StringLiteral
 */
public fun ClassProperty.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteral().apply(block)

/**
 * ClassProperty#key: PropertyName?
 * extension function for create PropertyName? -> NumericLiteral
 */
public fun ClassProperty.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteral().apply(block)

/**
 * ClassProperty#key: PropertyName?
 * extension function for create PropertyName? -> BigIntLiteral
 */
public fun ClassProperty.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteral().apply(block)

/**
 * ClassProperty#key: PropertyName?
 * extension function for create PropertyName? -> ComputedPropName
 */
public fun ClassProperty.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropName().apply(block)

/**
 * ClassProperty#key: PropertyName?
 * extension function for create PropertyName? -> Identifier
 */
public fun ClassProperty.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
