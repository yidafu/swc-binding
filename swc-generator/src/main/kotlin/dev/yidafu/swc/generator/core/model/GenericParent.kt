package dev.yidafu.swc.generator.core.model

import dev.yidafu.swc.generator.adt.kotlin.KotlinType

/**
 * 泛型父类型信息
 * * 用于保留接口继承时的泛型参数信息
 */
data class GenericParent(
    val name: String,
    val typeArguments: List<KotlinType> = emptyList()
) {
    /**
     * 转换为字符串表示
     */
    fun toTypeString(): String {
        return if (typeArguments.isEmpty()) {
            name
        } else {
            "$name<${typeArguments.joinToString(", ") { it.toTypeString() }}>"
        }
    }

    /**
     * 检查是否包含泛型参数
     */
    fun hasTypeArguments(): Boolean = typeArguments.isNotEmpty()

    /**
     * 获取泛型参数数量
     */
    fun getTypeArgumentCount(): Int = typeArguments.size
}
