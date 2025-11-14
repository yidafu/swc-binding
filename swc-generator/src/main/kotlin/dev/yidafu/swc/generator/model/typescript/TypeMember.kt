package dev.yidafu.swc.generator.model.typescript

/**
 * 类型成员（用于 TypeLiteral）
 */
data class TypeMember(
    val name: String,
    val type: TypeScriptType,
    val optional: Boolean = false,
    val readonly: Boolean = false,
    val kdoc: String? = null
)
