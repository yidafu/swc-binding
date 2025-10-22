package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Module
import dev.yidafu.swc.types.ModuleImpl
import dev.yidafu.swc.types.Program
import dev.yidafu.swc.types.Script
import dev.yidafu.swc.types.ScriptImpl
import kotlin.Unit

/**
 * subtype of Program
 */
public fun Program.module(block: Module.() -> Unit): Module = ModuleImpl().apply(block)

/**
 * subtype of Program
 */
public fun Program.script(block: Script.() -> Unit): Script = ScriptImpl().apply(block)
