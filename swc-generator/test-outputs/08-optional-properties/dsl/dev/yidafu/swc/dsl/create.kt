package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AllOptional
import dev.yidafu.swc.types.AllOptionalImpl
import dev.yidafu.swc.types.AllRequired
import dev.yidafu.swc.types.AllRequiredImpl
import dev.yidafu.swc.types.PropertyModifiers
import dev.yidafu.swc.types.PropertyModifiersImpl
import kotlin.Unit

public fun createPropertyModifiers(block: PropertyModifiers.() -> Unit): PropertyModifiers =
    PropertyModifiersImpl().apply(block)

public fun createAllOptional(block: AllOptional.() -> Unit): AllOptional =
    AllOptionalImpl().apply(block)

public fun createAllRequired(block: AllRequired.() -> Unit): AllRequired =
    AllRequiredImpl().apply(block)
