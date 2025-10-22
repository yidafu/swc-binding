package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.ConstModulesConfig
import dev.yidafu.swc.types.ConstModulesConfigImpl
import dev.yidafu.swc.types.OptimizerConfig
import dev.yidafu.swc.types.OptimizerConfigImpl
import dev.yidafu.swc.types.ReactConfig
import dev.yidafu.swc.types.ReactConfigImpl
import dev.yidafu.swc.types.TransformConfig
import kotlin.Unit

/**
 * TransformConfig#react: ReactConfig
 * extension function for create ReactConfig -> ReactConfigImpl
 */
public fun TransformConfig.reactConfig(block: ReactConfig.() -> Unit): ReactConfig =
    ReactConfigImpl().apply(block)

/**
 * TransformConfig#constModules: ConstModulesConfig
 * extension function for create ConstModulesConfig -> ConstModulesConfigImpl
 */
public fun TransformConfig.constModulesConfig(block: ConstModulesConfig.() -> Unit):
    ConstModulesConfig = ConstModulesConfigImpl().apply(block)

/**
 * TransformConfig#optimizer: OptimizerConfig
 * extension function for create OptimizerConfig -> OptimizerConfigImpl
 */
public fun TransformConfig.optimizerConfig(block: OptimizerConfig.() -> Unit): OptimizerConfig =
    OptimizerConfigImpl().apply(block)

/**
 * TransformConfig#useDefineForClassFields: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun TransformConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
