// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.102242

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.VariableDeclaration
import dev.yidafu.swc.generated.VariableDeclarator
import dev.yidafu.swc.generated.VariableDeclaratorImpl
import kotlin.Unit

/**
 * VariableDeclaration#declarations: Array<VariableDeclarator>?
 * extension function for create Array<VariableDeclarator>? -> VariableDeclarator
 */
public fun VariableDeclaration.variableDeclarator(block: VariableDeclarator.() -> Unit):
    VariableDeclarator = VariableDeclaratorImpl().apply(block)
