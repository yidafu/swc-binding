package dev.yidafu.swc.generator.adt.typescript

/**
 * 类型成员（用于 TypeLiteral）
 */
data class TypeMember(
    val name: String,
    val type: TypeScriptType,
    val optional: Boolean = false,
    val readonly: Boolean = false
)
