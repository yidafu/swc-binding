// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.099424

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.CallerOptions
import dev.yidafu.swc.generated.JscConfig
import dev.yidafu.swc.generated.Options
import dev.yidafu.swc.generated.Plugin
import kotlin.Unit

/**
 * Options#caller: CallerOptions?
 * extension function for create CallerOptions? -> CallerOptions
 */
public fun Options.callerOptions(block: CallerOptions.() -> Unit): CallerOptions =
    CallerOptions().apply(block)

/**
 * Options#plugin: Plugin?
 * extension function for create Plugin? -> Plugin
 */
public fun Options.plugin(block: Plugin.() -> Unit): Plugin = Plugin().apply(block)

/**
 * Options#jsc: JscConfig?
 * extension function for create JscConfig? -> JscConfig
 */
public fun Options.jsc(block: JscConfig.() -> Unit): JscConfig =
    JscConfig().apply(block).also { jsc = it }

/**
 * Create JscConfig using DSL builder pattern.
 */
public fun jscConfig(block: JscConfig.() -> Unit): JscConfig =
    JscConfig().apply(block)
