// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.218397

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BreakStatement
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import kotlin.Unit

/**
 * BreakStatement#label: Identifier?
 * extension function for create Identifier? -> Identifier
 */
public fun BreakStatement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
