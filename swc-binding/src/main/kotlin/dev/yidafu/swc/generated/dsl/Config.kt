// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:16.89688

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.AmdConfig
import dev.yidafu.swc.generated.CommonJsConfig
import dev.yidafu.swc.generated.Config
import dev.yidafu.swc.generated.EnvConfig
import dev.yidafu.swc.generated.Es6Config
import dev.yidafu.swc.generated.JscConfig
import dev.yidafu.swc.generated.NodeNextConfig
import dev.yidafu.swc.generated.SystemjsConfig
import dev.yidafu.swc.generated.UmdConfig
import kotlin.Unit

/**
 * Config#env: EnvConfig?
 * extension function for create EnvConfig? -> EnvConfig
 */
public fun Config.envConfig(block: EnvConfig.() -> Unit): EnvConfig = EnvConfig().apply(block)

/**
 * Config#jsc: JscConfig?
 * extension function for create JscConfig? -> JscConfig
 */
public fun Config.jscConfig(block: JscConfig.() -> Unit): JscConfig = JscConfig().apply(block)

/**
 * Config#module: ModuleConfig?
 * extension function for create ModuleConfig? -> Es6Config
 */
public fun Config.es6Config(block: Es6Config.() -> Unit): Es6Config = Es6Config().apply(block)

/**
 * Config#module: ModuleConfig?
 * extension function for create ModuleConfig? -> NodeNextConfig
 */
public fun Config.nodeNextConfig(block: NodeNextConfig.() -> Unit): NodeNextConfig =
    NodeNextConfig().apply(block)

/**
 * Config#module: ModuleConfig?
 * extension function for create ModuleConfig? -> CommonJsConfig
 */
public fun Config.commonJsConfig(block: CommonJsConfig.() -> Unit): CommonJsConfig =
    CommonJsConfig().apply(block)

/**
 * Config#module: ModuleConfig?
 * extension function for create ModuleConfig? -> UmdConfig
 */
public fun Config.umdConfig(block: UmdConfig.() -> Unit): UmdConfig = UmdConfig().apply(block)

/**
 * Config#module: ModuleConfig?
 * extension function for create ModuleConfig? -> AmdConfig
 */
public fun Config.amdConfig(block: AmdConfig.() -> Unit): AmdConfig = AmdConfig().apply(block)

/**
 * Config#module: ModuleConfig?
 * extension function for create ModuleConfig? -> SystemjsConfig
 */
public fun Config.systemjsConfig(block: SystemjsConfig.() -> Unit): SystemjsConfig =
    SystemjsConfig().apply(block)
