package dev.yidafu.swc.generator.util

import dev.yidafu.swc.types.*
import kotlinx.serialization.json.*

/**
 * 扩展函数工具类
 */

/**
 * 安全获取 Identifier 的值
 */
fun Identifier?.safeValue(): String = this?.value ?: ""

/**
 * 安全获取 TsQualifiedName 的右侧标识符值
 */
fun TsQualifiedName?.safeRightValue(): String = this?.right?.value ?: ""

/**
 * 从 TsEntityName 获取类型名称
 */
fun TsEntityName?.getTypeName(): String = when (this) {
    is Identifier -> this.safeValue()
    is TsQualifiedName -> this.safeRightValue()
    else -> ""
}

/**
 * 从 Expression 获取类型名称（用于 extends/implements）
 */
fun Expression?.getExpressionTypeName(): String = when (this) {
    is Identifier -> this.safeValue()
    is TsQualifiedName -> this.safeRightValue()
    else -> ""
}

/**
 * 安全获取数组，避免 null
 */
fun <T> Array<T>?.orEmpty(): List<T> = this?.toList() ?: emptyList()

/**
 * 移除注释
 */
fun String.removeComment(): String {
    return this.replace(Regex("""/\*(.|\r\n|\n)*?\*/"""), "")
}

/**
 * 判断字符串是否为空或空白
 */
fun String?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()

/**
 * 获取字面量值的字符串表示
 */
fun TsLiteral.getValueString(): String = when (this) {
    is StringLiteral -> "\"${this.value ?: ""}\""
    is NumericLiteral -> this.value.toString()
    is BigIntLiteral -> this.value.toString()
    is BooleanLiteral -> this.value.toString()
    else -> ""
}

/**
 * 获取字面量的 Kotlin 类型
 */
fun TsLiteral.getKotlinType(): String = when (this) {
    is StringLiteral -> "String"
    is NumericLiteral -> "Int"
    is BigIntLiteral -> "Long"
    is BooleanLiteral -> "Boolean"
    else -> "Any"
}

// ========== JSON 扩展函数 ==========

/**
 * 安全检查是否为 JsonObject
 */
fun JsonElement.isJsonObject(): Boolean = this is JsonObject

/**
 * 安全检查是否为 JsonArray
 */
fun JsonElement.isJsonArray(): Boolean = this is JsonArray

/**
 * 安全检查是否为 JsonPrimitive
 */
fun JsonElement.isJsonPrimitive(): Boolean = this is JsonPrimitive

/**
 * 安全检查是否为 JsonNull
 */
fun JsonElement.isJsonNull(): Boolean = this is JsonNull

/**
 * 安全转换为 JsonObject（带类型检查）
 */
fun JsonElement.asJsonObjectOrNull(): JsonObject? {
    return if (this.isJsonObject()) this as JsonObject else null
}

/**
 * 安全转换为 JsonArray（带类型检查）
 */
fun JsonElement.asJsonArrayOrNull(): JsonArray? {
    return if (this.isJsonArray()) this as JsonArray else null
}

/**
 * 安全转换为 JsonPrimitive（带类型检查）
 */
fun JsonElement.asJsonPrimitiveOrNull(): JsonPrimitive? {
    return if (this.isJsonPrimitive()) this as JsonPrimitive else null
}

/**
 * 获取 JSON 节点的 "type" 字段（类型安全版本）
 */
fun JsonElement.getType(): String {
    return try {
        this.asJsonObjectOrNull()
            ?.get("type")
            ?.asJsonPrimitiveOrNull()
            ?.contentOrNull
            ?: ""
    } catch (e: Exception) {
        Logger.warn("获取 type 字段失败: ${e.message}")
        ""
    }
}

/**
 * 获取字符串字段（类型安全版本）
 */
fun JsonElement.getStringField(key: String): String {
    return try {
        val obj = this.asJsonObjectOrNull() ?: return ""
        val value = obj[key] ?: return ""
        
        when {
            value.isJsonNull() -> ""
            value.isJsonPrimitive() -> value.asJsonPrimitiveOrNull()?.contentOrNull ?: ""
            else -> {
                Logger.verbose("字段 '$key' 不是字符串类型，实际类型: ${value::class.simpleName}")
                ""
            }
        }
    } catch (e: Exception) {
        Logger.warn("获取字符串字段 '$key' 失败: ${e.message}")
        ""
    }
}

/**
 * 获取数组字段（类型安全版本）
 */
fun JsonElement.getArrayField(key: String): List<JsonElement> {
    return try {
        val obj = this.asJsonObjectOrNull() ?: return emptyList()
        val value = obj[key] ?: return emptyList()
        
        when {
            value.isJsonNull() -> emptyList()
            value.isJsonArray() -> value.asJsonArrayOrNull()?.toList() ?: emptyList()
            else -> {
                Logger.verbose("字段 '$key' 不是数组类型，实际类型: ${value::class.simpleName}")
                emptyList()
            }
        }
    } catch (e: Exception) {
        Logger.warn("获取数组字段 '$key' 失败: ${e.message}")
        emptyList()
    }
}

/**
 * 获取 JsonObject 字段（类型安全版本）
 */
fun JsonElement.getObjectField(key: String): JsonObject? {
    return try {
        val obj = this.asJsonObjectOrNull() ?: return null
        val value = obj[key] ?: return null
        
        when {
            value.isJsonNull() -> null
            value.isJsonObject() -> value.asJsonObjectOrNull()
            else -> {
                Logger.verbose("字段 '$key' 不是对象类型，实际类型: ${value::class.simpleName}")
                null
            }
        }
    } catch (e: Exception) {
        Logger.warn("获取对象字段 '$key' 失败: ${e.message}")
        null
    }
}

/**
 * 获取布尔字段（类型安全版本）
 */
fun JsonElement.getBooleanField(key: String, defaultValue: Boolean = false): Boolean {
    return try {
        val obj = this.asJsonObjectOrNull() ?: return defaultValue
        val value = obj[key] ?: return defaultValue
        
        when {
            value.isJsonNull() -> defaultValue
            value.isJsonPrimitive() -> value.asJsonPrimitiveOrNull()?.booleanOrNull ?: defaultValue
            else -> {
                Logger.verbose("字段 '$key' 不是布尔类型，实际类型: ${value::class.simpleName}")
                defaultValue
            }
        }
    } catch (e: Exception) {
        Logger.warn("获取布尔字段 '$key' 失败: ${e.message}")
        defaultValue
    }
}

/**
 * 获取数字字段（类型安全版本）
 */
fun JsonElement.getNumberField(key: String, defaultValue: Int = 0): Int {
    return try {
        val obj = this.asJsonObjectOrNull() ?: return defaultValue
        val value = obj[key] ?: return defaultValue
        
        when {
            value.isJsonNull() -> defaultValue
            value.isJsonPrimitive() -> value.asJsonPrimitiveOrNull()?.intOrNull ?: defaultValue
            else -> {
                Logger.verbose("字段 '$key' 不是数字类型，实际类型: ${value::class.simpleName}")
                defaultValue
            }
        }
    } catch (e: Exception) {
        Logger.warn("获取数字字段 '$key' 失败: ${e.message}")
        defaultValue
    }
}

/**
 * 安全获取 value 字段（替代 Identifier.safeValue()）
 */
fun JsonElement?.safeValue(): String {
    return this?.getStringField("value") ?: ""
}

/**
 * 从 JSON 获取类型名称（替代 TsEntityName.getTypeName()）
 */
fun JsonElement?.getTypeName(): String {
    if (this == null) return ""
    val type = this.getType()
    return when (type) {
        "Identifier" -> this.safeValue()
        "TsQualifiedName" -> this.getObjectField("right")?.safeValue() ?: ""
        else -> {
            if (type.isNotEmpty()) {
                Logger.verbose("未知的 EntityName 类型: $type")
            }
            ""
        }
    }
}

/**
 * 从 Expression JSON 获取类型名称
 */
fun JsonElement?.getExpressionTypeName(): String {
    if (this == null) return ""
    val type = this.getType()
    return when (type) {
        "Identifier" -> this.safeValue()
        "TsQualifiedName" -> this.getObjectField("right")?.safeValue() ?: ""
        else -> {
            if (type.isNotEmpty()) {
                Logger.verbose("未知的 Expression 类型: $type")
            }
            ""
        }
    }
}

/**
 * 获取字面量值的字符串表示（JSON 版本 - 类型安全）
 */
fun JsonElement.getValueString(): String {
    return try {
        val type = this.getType()
        when (type) {
            "StringLiteral" -> {
                val value = this.getStringField("value")
                "\"$value\""
            }
            "NumericLiteral" -> {
                this.asJsonObjectOrNull()
                    ?.get("value")
                    ?.asJsonPrimitiveOrNull()
                    ?.contentOrNull
                    ?: "0"
            }
            "BigIntLiteral" -> {
                this.asJsonObjectOrNull()
                    ?.get("value")
                    ?.asJsonPrimitiveOrNull()
                    ?.contentOrNull
                    ?: "0"
            }
            "BooleanLiteral" -> {
                this.asJsonObjectOrNull()
                    ?.get("value")
                    ?.asJsonPrimitiveOrNull()
                    ?.booleanOrNull
                    ?.toString()
                    ?: "false"
            }
            else -> {
                Logger.verbose("未知的字面量类型: $type")
                ""
            }
        }
    } catch (e: Exception) {
        Logger.warn("获取字面量值失败: ${e.message}")
        ""
    }
}

/**
 * 获取字面量的 Kotlin 类型（JSON 版本 - 类型安全）
 */
fun JsonElement.getKotlinType(): String {
    val type = this.getType()
    return when (type) {
        "StringLiteral" -> "String"
        "NumericLiteral" -> "Int"
        "BigIntLiteral" -> "Long"
        "BooleanLiteral" -> "Boolean"
        else -> {
            if (type.isNotEmpty()) {
                Logger.verbose("未知的字面量类型: $type")
            }
            "Any"
        }
    }
}

/**
 * 检查字段是否存在
 */
fun JsonElement.hasField(key: String): Boolean {
    return this.asJsonObjectOrNull()?.containsKey(key) ?: false
}

/**
 * 获取所有字段名
 */
fun JsonElement.getFieldNames(): Set<String> {
    return this.asJsonObjectOrNull()?.keys ?: emptySet()
}
