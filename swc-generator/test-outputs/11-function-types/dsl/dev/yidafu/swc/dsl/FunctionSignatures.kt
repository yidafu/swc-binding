package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Any
import dev.yidafu.swc.types.FunctionSignatures
import kotlin.Unit

/**
 * FunctionSignatures#complexReturn: Any
 * extension function for create Any -> Any
 */
public fun FunctionSignatures.any(block: Any.() -> Unit): Any = Any().apply(block)
