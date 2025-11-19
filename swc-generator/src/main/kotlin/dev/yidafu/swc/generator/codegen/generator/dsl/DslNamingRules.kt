package dev.yidafu.swc.generator.codegen.generator.dsl

import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.util.Logger

object DslNamingRules {
    private val sanitizedTypeNameCache = mutableMapOf<String, String>()

    fun sanitizeTypeName(typeName: String): String {
        return sanitizedTypeNameCache.getOrPut(typeName) {
            sanitizeTypeNameInternal(typeName)
        }
    }

    private fun sanitizeTypeNameInternal(typeName: String): String {
        var cleaned = typeName.trim()
        cleaned = cleaned.replace(Regex("""/\*.*?\*/"""), "").trim()
        cleaned = cleaned.replace(Regex("<[^>]*>?"), "")
        cleaned = cleaned.replace(Regex("\\s+"), "")
        cleaned = cleaned.replace(Regex("[^a-zA-Z0-9_]"), "")
        if (cleaned.isNotEmpty() && !cleaned.first().isUpperCase()) {
            cleaned = cleaned.replaceFirstChar { it.uppercase() }
        }
        if (cleaned.isEmpty() || !cleaned.matches(Regex("[A-Z][a-zA-Z0-9_]*"))) {
            Logger.warn("类型名称清理失败: $typeName -> $cleaned，使用 'InvalidType'")
            return "InvalidType"
        }
        return CodeGenerationRules.wrapReservedWord(cleaned)
    }

    fun removeGenerics(str: String): String {
        return str.replace(Regex("<[^>]*>"), "")
    }

    fun toFunName(str: String): String {
        val withoutGenerics = removeGenerics(str)
        val name = withoutGenerics.replaceFirstChar { it.lowercase() }
        return CodeGenerationRules.getKotlinKeywordMap()[name] ?: name
    }
}
