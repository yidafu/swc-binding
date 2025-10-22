package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AmdConfig
import dev.yidafu.swc.types.AmdConfigImpl
import dev.yidafu.swc.types.BaseModuleConfig
import dev.yidafu.swc.types.Boolean
import dev.yidafu.swc.types.CommonJsConfig
import dev.yidafu.swc.types.CommonJsConfigImpl
import dev.yidafu.swc.types.Es6Config
import dev.yidafu.swc.types.Es6ConfigImpl
import dev.yidafu.swc.types.NodeNextConfig
import dev.yidafu.swc.types.NodeNextConfigImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.UmdConfig
import dev.yidafu.swc.types.UmdConfigImpl
import kotlin.Unit

/**
 * subtype of BaseModuleConfig
 */
public fun BaseModuleConfig.es6Config(block: Es6Config.() -> Unit): Es6Config =
    Es6ConfigImpl().apply(block)

/**
 * subtype of BaseModuleConfig
 */
public fun BaseModuleConfig.nodeNextConfig(block: NodeNextConfig.() -> Unit): NodeNextConfig =
    NodeNextConfigImpl().apply(block)

/**
 * subtype of BaseModuleConfig
 */
public fun BaseModuleConfig.commonJsConfig(block: CommonJsConfig.() -> Unit): CommonJsConfig =
    CommonJsConfigImpl().apply(block)

/**
 * subtype of BaseModuleConfig
 */
public fun BaseModuleConfig.umdConfig(block: UmdConfig.() -> Unit): UmdConfig =
    UmdConfigImpl().apply(block)

/**
 * subtype of BaseModuleConfig
 */
public fun BaseModuleConfig.amdConfig(block: AmdConfig.() -> Unit): AmdConfig =
    AmdConfigImpl().apply(block)

/**
 * BaseModuleConfig#preserveImportMeta: Boolean
 * extension function for create Boolean -> Boolean
 */
public fun BaseModuleConfig.boolean(block: Boolean.() -> Unit): Boolean = Boolean().apply(block)

/**
 * BaseModuleConfig#importInterop: String
 * extension function for create String -> String
 */
public fun BaseModuleConfig.string(block: String.() -> Unit): String = String().apply(block)
