package dev.yidafu.swc.generator.model.typescript

/**
 * 函数参数
 */
data class FunctionParam(
    val name: String,
    val type: TypeScriptType,
    val optional: Boolean = false,
    val rest: Boolean = false
)
