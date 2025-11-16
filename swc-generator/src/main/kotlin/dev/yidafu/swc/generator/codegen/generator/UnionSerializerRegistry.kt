package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName

/**
 * 收集在生成 types.kt 过程中出现的 Union.Ux 使用信息，
 * 以便在生成阶段统一输出专属的 KSerializer 实现到 UnionSerializer.kt。
 */
object UnionSerializerRegistry {
    data class UnionUsage(
        val ownerSimpleName: String,
        val propertyName: String,
        val unionKind: String, // "U2" | "U3" | "U4" | "U5"
        val typeArguments: List<TypeName>, // 允许参数化类型（如 Array<String>）
        val isArray: Boolean,
        val isNullableElement: List<Boolean> // per-type-arg nullability flags
    )

    private val usages = linkedSetOf<UnionUsage>()

    @Synchronized
    fun addUsage(usage: UnionUsage) {
        usages.add(usage)
    }

    @Synchronized
    fun all(): List<UnionUsage> = usages.toList()

    @Synchronized
    fun clear() {
        usages.clear()
    }

    /**
     * 计算类型参数的规范化 token，用于生成去重后的序列化器名称。
     * 规则：SimpleName + 子参数token连接；Array<String> => ArrayString；Record<String,String> => RecordStringString
     * 可选地包含可空标记以避免碰撞（受配置控制）。
     */
    fun canonicalToken(typeName: TypeName): String {
        val includeNull = dev.yidafu.swc.generator.config.Hardcoded.Union.includeNullabilityInToken
        val tn = if (!includeNull && typeName.isNullable) typeName.copy(nullable = false) else typeName
        return when (tn) {
            is ParameterizedTypeName -> {
                val raw = (tn.rawType as ClassName)
                val base = raw.simpleNames.joinToString("")
                val args = tn.typeArguments.joinToString("") { canonicalToken(it) }
                val nullTag = if (includeNull && typeName.isNullable) "N" else ""
                base + args + nullTag
            }
            is ClassName -> {
                val base = tn.simpleNames.joinToString("")
                val nullTag = if (includeNull && typeName.isNullable) "N" else ""
                base + nullTag
            }
            else -> {
                // 回退到字符串表示，并按需加空性标记
                val base = tn.toString()
                val nullTag = if (includeNull && typeName.isNullable) "N" else ""
                base + nullTag
            }
        }
    }

    /**
     * 依据 unionKind + 参数token 计算去重后的序列化器名。
     * 示例：U2 + [ArrayString, RecordStringString] -> U2_ArrayString_RecordStringString__Serializer
     */
    fun computeSerializerName(unionKind: String, typeArguments: List<TypeName>): String {
        val tokens = typeArguments.map { canonicalToken(it) }
        val middle = if (tokens.isEmpty()) "" else "_${tokens.joinToString("_")}"
        return "${unionKind}${middle}__Serializer"
    }
}


