package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AmdConfig
import dev.yidafu.swc.types.AmdConfigImpl
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.CallerOptions
import dev.yidafu.swc.types.CallerOptionsImpl
import dev.yidafu.swc.types.CommonJsConfig
import dev.yidafu.swc.types.CommonJsConfigImpl
import dev.yidafu.swc.types.EnvConfig
import dev.yidafu.swc.types.EnvConfigImpl
import dev.yidafu.swc.types.Es6Config
import dev.yidafu.swc.types.Es6ConfigImpl
import dev.yidafu.swc.types.JscConfig
import dev.yidafu.swc.types.JscConfigImpl
import dev.yidafu.swc.types.MatchPattern
import dev.yidafu.swc.types.MatchPatternImpl
import dev.yidafu.swc.types.NodeNextConfig
import dev.yidafu.swc.types.NodeNextConfigImpl
import dev.yidafu.swc.types.Options
import dev.yidafu.swc.types.Plugin
import dev.yidafu.swc.types.PluginImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.SystemjsConfig
import dev.yidafu.swc.types.SystemjsConfigImpl
import dev.yidafu.swc.types.UmdConfig
import dev.yidafu.swc.types.UmdConfigImpl
import kotlin.Unit

/**
 * Options#inlineSourcesContent: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun Options.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * Options#exclude: Array<String>
 * extension function for create Array<String> -> String
 */
public fun Options.string(block: String.() -> Unit): String = String().apply(block)

/**
 * Options#caller: CallerOptions
 * extension function for create CallerOptions -> CallerOptionsImpl
 */
public fun Options.callerOptions(block: CallerOptions.() -> Unit): CallerOptions =
    CallerOptionsImpl().apply(block)

/**
 * Options#swcrcRoots: Union.U3<Boolean, MatchPattern, Array<MatchPattern>>
 * extension function for create Union.U3<Boolean, MatchPattern, Array<MatchPattern>> ->
 * MatchPatternImpl
 */
public fun Options.matchPattern(block: MatchPattern.() -> Unit): MatchPattern =
    MatchPatternImpl().apply(block)

/**
 * Options#plugin: Plugin
 * extension function for create Plugin -> PluginImpl
 */
public fun Options.plugin(block: Plugin.() -> Unit): Plugin = PluginImpl().apply(block)

/**
 * Options#env: EnvConfig
 * extension function for create EnvConfig -> EnvConfigImpl
 */
public fun Options.envConfig(block: EnvConfig.() -> Unit): EnvConfig = EnvConfigImpl().apply(block)

/**
 * Options#jsc: JscConfig
 * extension function for create JscConfig -> JscConfigImpl
 */
public fun Options.jscConfig(block: JscConfig.() -> Unit): JscConfig = JscConfigImpl().apply(block)

/**
 * Options#module: ModuleConfig
 * extension function for create ModuleConfig -> Es6ConfigImpl
 */
public fun Options.es6Config(block: Es6Config.() -> Unit): Es6Config = Es6ConfigImpl().apply(block)

/**
 * Options#module: ModuleConfig
 * extension function for create ModuleConfig -> CommonJsConfigImpl
 */
public fun Options.commonJsConfig(block: CommonJsConfig.() -> Unit): CommonJsConfig =
    CommonJsConfigImpl().apply(block)

/**
 * Options#module: ModuleConfig
 * extension function for create ModuleConfig -> UmdConfigImpl
 */
public fun Options.umdConfig(block: UmdConfig.() -> Unit): UmdConfig = UmdConfigImpl().apply(block)

/**
 * Options#module: ModuleConfig
 * extension function for create ModuleConfig -> AmdConfigImpl
 */
public fun Options.amdConfig(block: AmdConfig.() -> Unit): AmdConfig = AmdConfigImpl().apply(block)

/**
 * Options#module: ModuleConfig
 * extension function for create ModuleConfig -> NodeNextConfigImpl
 */
public fun Options.nodeNextConfig(block: NodeNextConfig.() -> Unit): NodeNextConfig =
    NodeNextConfigImpl().apply(block)

/**
 * Options#module: ModuleConfig
 * extension function for create ModuleConfig -> SystemjsConfigImpl
 */
public fun Options.systemjsConfig(block: SystemjsConfig.() -> Unit): SystemjsConfig =
    SystemjsConfigImpl().apply(block)
