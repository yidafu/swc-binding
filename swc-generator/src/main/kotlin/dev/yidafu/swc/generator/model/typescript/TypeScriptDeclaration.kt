package dev.yidafu.swc.generator.model.typescript

/**
 * TypeScript 声明 ADT（代数数据类型）
 * 基于 @swc/types@0.1.25 分析：19个接口，29个类型别名，无枚举
 */
sealed class TypeScriptDeclaration {
    /**
     * 接口声明
     */
    data class InterfaceDeclaration(
        val name: String,
        val typeParameters: List<TypeParameter> = emptyList(),
        val extends: List<TypeReference> = emptyList(),
        val members: List<TypeMember> = emptyList(),
        val kdoc: String? = null
    ) : TypeScriptDeclaration()

    /**
     * 类型别名声明
     */
    data class TypeAliasDeclaration(
        val name: String,
        val typeParameters: List<TypeParameter> = emptyList(),
        val type: TypeScriptType,
        val kdoc: String? = null
    ) : TypeScriptDeclaration()
}

/**
 * 类型参数
 */
data class TypeParameter(
    val name: String,
    val constraint: TypeScriptType? = null,
    val default: TypeScriptType? = null,
    val variance: Variance = Variance.INVARIANT
)

/**
 * 类型参数变体
 */
enum class Variance {
    INVARIANT, // 不变
    COVARIANT, // 协变 (out)
    CONTRAVARIANT // 逆变 (in)
}

/**
 * 类型引用（用于 extends 子句）
 */
data class TypeReference(
    val name: String,
    val typeArguments: List<TypeScriptType> = emptyList()
)
