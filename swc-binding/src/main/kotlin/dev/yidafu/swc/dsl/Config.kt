package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AmdConfig
import dev.yidafu.swc.types.AmdConfigImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.CommonJsConfig
import dev.yidafu.swc.types.CommonJsConfigImpl
import dev.yidafu.swc.types.Config
import dev.yidafu.swc.types.EnvConfig
import dev.yidafu.swc.types.EnvConfigImpl
import dev.yidafu.swc.types.Es6Config
import dev.yidafu.swc.types.Es6ConfigImpl
import dev.yidafu.swc.types.JscConfig
import dev.yidafu.swc.types.JscConfigImpl
import dev.yidafu.swc.types.NodeNextConfig
import dev.yidafu.swc.types.NodeNextConfigImpl
import dev.yidafu.swc.types.Options
import dev.yidafu.swc.types.OptionsImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.SystemjsConfig
import dev.yidafu.swc.types.SystemjsConfigImpl
import dev.yidafu.swc.types.UmdConfig
import dev.yidafu.swc.types.UmdConfigImpl
import kotlin.Unit

/**
 * subtype of Config
 */
public fun Config.options(block: Options.() -> Unit): Options = OptionsImpl().apply(block)

/**
 * Config#exclude: Array<String>
 * extension function for create Array<String> -> String
 */
public fun Config.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Config#env: EnvConfig
 * extension function for create EnvConfig -> EnvConfigImpl
 */
public fun Config.envConfig(block: EnvConfig.() -> Unit): EnvConfig = EnvConfigImpl().apply(block)

/**
 * Config#jsc: JscConfig
 * extension function for create JscConfig -> JscConfigImpl
 */
public fun Config.jscConfig(block: JscConfig.() -> Unit): JscConfig = JscConfigImpl().apply(block)

/**
 * Config#module: ModuleConfig
 * extension function for create ModuleConfig -> Es6ConfigImpl
 */
public fun Config.es6Config(block: Es6Config.() -> Unit): Es6Config = Es6ConfigImpl().apply(block)

/**
 * Config#module: ModuleConfig
 * extension function for create ModuleConfig -> CommonJsConfigImpl
 */
public fun Config.commonJsConfig(block: CommonJsConfig.() -> Unit): CommonJsConfig =
    CommonJsConfigImpl().apply(block)

/**
 * Config#module: ModuleConfig
 * extension function for create ModuleConfig -> UmdConfigImpl
 */
public fun Config.umdConfig(block: UmdConfig.() -> Unit): UmdConfig = UmdConfigImpl().apply(block)

/**
 * Config#module: ModuleConfig
 * extension function for create ModuleConfig -> AmdConfigImpl
 */
public fun Config.amdConfig(block: AmdConfig.() -> Unit): AmdConfig = AmdConfigImpl().apply(block)

/**
 * Config#module: ModuleConfig
 * extension function for create ModuleConfig -> NodeNextConfigImpl
 */
public fun Config.nodeNextConfig(block: NodeNextConfig.() -> Unit): NodeNextConfig =
    NodeNextConfigImpl().apply(block)

/**
 * Config#module: ModuleConfig
 * extension function for create ModuleConfig -> SystemjsConfigImpl
 */
public fun Config.systemjsConfig(block: SystemjsConfig.() -> Unit): SystemjsConfig =
    SystemjsConfigImpl().apply(block)

/**
 * Config#inlineSourcesContent: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun Config.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)
