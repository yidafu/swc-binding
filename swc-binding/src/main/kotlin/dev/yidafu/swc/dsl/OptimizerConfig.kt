package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.GlobalPassOption
import dev.yidafu.swc.types.GlobalPassOptionImpl
import dev.yidafu.swc.types.OptimizerConfig
import dev.yidafu.swc.types.TypeLiteral
import kotlin.Unit

/**
 * OptimizerConfig#simplify: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun OptimizerConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * OptimizerConfig#globals: GlobalPassOption
 * extension function for create GlobalPassOption -> GlobalPassOptionImpl
 */
public fun OptimizerConfig.globalPassOption(block: GlobalPassOption.() -> Unit): GlobalPassOption =
    GlobalPassOptionImpl().apply(block)

/**
 * OptimizerConfig#jsonify: TypeLiteral
 * extension function for create TypeLiteral -> TypeLiteral
 */
public fun OptimizerConfig.typeLiteral(block: TypeLiteral.() -> Unit): TypeLiteral =
    TypeLiteral().apply(block)
