package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.HasInterpreter
import dev.yidafu.swc.types.Module
import dev.yidafu.swc.types.ModuleImpl
import dev.yidafu.swc.types.Script
import dev.yidafu.swc.types.ScriptImpl
import dev.yidafu.swc.types.String
import kotlin.Unit

/**
 * subtype of HasInterpreter
 */
public fun HasInterpreter.module(block: Module.() -> Unit): Module = ModuleImpl().apply(block)

/**
 * subtype of HasInterpreter
 */
public fun HasInterpreter.script(block: Script.() -> Unit): Script = ScriptImpl().apply(block)

/**
 * HasInterpreter#interpreter: String
 * extension function for create String -> String
 */
public fun HasInterpreter.string(block: String.() -> Unit): String = String().apply(block)
