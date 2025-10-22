package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.FunctionSignatures
import dev.yidafu.swc.types.FunctionSignaturesImpl
import kotlin.Unit

public fun createFunctionSignatures(block: FunctionSignatures.() -> Unit): FunctionSignatures =
    FunctionSignaturesImpl().apply(block)
