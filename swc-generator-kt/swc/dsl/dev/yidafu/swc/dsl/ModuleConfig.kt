package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AmdConfig
import dev.yidafu.swc.types.AmdConfigImpl
import dev.yidafu.swc.types.CommonJsConfig
import dev.yidafu.swc.types.CommonJsConfigImpl
import dev.yidafu.swc.types.Es6Config
import dev.yidafu.swc.types.Es6ConfigImpl
import dev.yidafu.swc.types.ModuleConfig
import dev.yidafu.swc.types.NodeNextConfig
import dev.yidafu.swc.types.NodeNextConfigImpl
import dev.yidafu.swc.types.SystemjsConfig
import dev.yidafu.swc.types.SystemjsConfigImpl
import dev.yidafu.swc.types.UmdConfig
import dev.yidafu.swc.types.UmdConfigImpl
import kotlin.Unit

/**
 * subtype of ModuleConfig
 */
public fun ModuleConfig.es6Config(block: Es6Config.() -> Unit): Es6Config =
    Es6ConfigImpl().apply(block)

/**
 * subtype of ModuleConfig
 */
public fun ModuleConfig.commonJsConfig(block: CommonJsConfig.() -> Unit): CommonJsConfig =
    CommonJsConfigImpl().apply(block)

/**
 * subtype of ModuleConfig
 */
public fun ModuleConfig.umdConfig(block: UmdConfig.() -> Unit): UmdConfig =
    UmdConfigImpl().apply(block)

/**
 * subtype of ModuleConfig
 */
public fun ModuleConfig.amdConfig(block: AmdConfig.() -> Unit): AmdConfig =
    AmdConfigImpl().apply(block)

/**
 * subtype of ModuleConfig
 */
public fun ModuleConfig.nodeNextConfig(block: NodeNextConfig.() -> Unit): NodeNextConfig =
    NodeNextConfigImpl().apply(block)

/**
 * subtype of ModuleConfig
 */
public fun ModuleConfig.systemjsConfig(block: SystemjsConfig.() -> Unit): SystemjsConfig =
    SystemjsConfigImpl().apply(block)
