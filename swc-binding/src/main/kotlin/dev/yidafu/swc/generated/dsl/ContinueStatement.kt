// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.256588

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ContinueStatement
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import kotlin.Unit

/**
 * ContinueStatement#label: Identifier?
 * extension function for create Identifier? -> Identifier
 */
public fun ContinueStatement.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)
