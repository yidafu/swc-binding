// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:16.887259

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ConstModulesConfig
import dev.yidafu.swc.generated.OptimizerConfig
import dev.yidafu.swc.generated.ReactConfig
import dev.yidafu.swc.generated.TransformConfig
import kotlin.Unit

/**
 * TransformConfig#react: ReactConfig?
 * extension function for create ReactConfig? -> ReactConfig
 */
public fun TransformConfig.reactConfig(block: ReactConfig.() -> Unit): ReactConfig =
    ReactConfig().apply(block)

/**
 * TransformConfig#constModules: ConstModulesConfig?
 * extension function for create ConstModulesConfig? -> ConstModulesConfig
 */
public fun TransformConfig.constModulesConfig(block: ConstModulesConfig.() -> Unit):
    ConstModulesConfig = ConstModulesConfig().apply(block)

/**
 * TransformConfig#optimizer: OptimizerConfig?
 * extension function for create OptimizerConfig? -> OptimizerConfig
 */
public fun TransformConfig.optimizerConfig(block: OptimizerConfig.() -> Unit): OptimizerConfig =
    OptimizerConfig().apply(block)
