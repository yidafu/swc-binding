package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayTypes
import dev.yidafu.swc.types.ArrayTypesImpl
import kotlin.Unit

public fun createArrayTypes(block: ArrayTypes.() -> Unit): ArrayTypes =
    ArrayTypesImpl().apply(block)
