package dev.yidafu.swc.generator.core.model

import dev.yidafu.swc.generator.config.Constants

/**
 * Kotlin 属性模型
 */
data class KotlinProperty(
    var modifier: String = "var",
    var name: String = "",
    var type: String = "",
    var comment: String = "",
    var defaultValue: String = "",
    var isOverride: Boolean = false,
    var discriminator: String = "type"
) {
    /**
     * 判断是否为数组类型
     */
    fun isArray(): Boolean = type.startsWith("Array<")
    
    /**
     * 获取类型列表（处理 Union 类型）
     */
    fun getTypeList(): List<String> {
        // 跳过 Booleanable 类型，不尝试提取其内部类型
        if (type.startsWith("Booleanable")) {
            return emptyList()
        }
        
        val baseType = if (isArray()) {
            type.substringAfter("Array<").substringBeforeLast(">")
        } else {
            type
        }
        
        return if (baseType.startsWith("Union.U")) {
            extractUnionTypes(baseType)
        } else {
            // 即使是单一类型也要清理
            listOf(cleanTypeName(baseType))
        }
    }
    
    /**
     * 从 Union 类型字符串提取类型列表
     */
    private fun extractUnionTypes(unionType: String): List<String> {
        return Regex("""Union\.U\d+<([^>]+)>""")
            .find(unionType)
            ?.groupValues
            ?.get(1)
            ?.split(",")
            ?.map { cleanTypeName(it) }
            ?: listOf(cleanTypeName(unionType))
    }
    
    /**
     * 清理类型名称，移除空格和修复泛型
     */
    private fun cleanTypeName(typeName: String): String {
        var cleaned = typeName.trim()
        
        // 移除多余的空格
        cleaned = cleaned.replace(Regex("\\s+"), "")
        
        // 修复不完整的泛型参数
        val openCount = cleaned.count { it == '<' }
        val closeCount = cleaned.count { it == '>' }
        if (openCount > closeCount) {
            cleaned += ">".repeat(openCount - closeCount)
        }
        
        return cleaned
    }
    
    /**
     * 获取注解
     */
    fun getAnnotation(): String {
        val actualType = removeComment(getActualType()).replace("<", "").replace(">", "")
        
        if (Constants.kotlinKeywordMap.containsKey(name)) {
            return "@SerialName(\"$name\")\n  "
        }
        
        if (actualType.startsWith("Booleanable")) {
            return "@Serializable(${actualType}Serializer::class)\n  "
        }
        
        return ""
    }
    
    private var cachedActualType: String? = null
    
    /**
     * 获取实际类型（处理 Union 转换为共同父类型等）
     */
    fun getActualType(): String {
        // 使用缓存提升性能
        return cachedActualType ?: computeActualType().also { cachedActualType = it }
    }
    
    /**
     * 计算实际类型
     */
    private fun computeActualType(): String {
        val trimmedType = type.trim()
        
        return if (trimmedType.startsWith("Array<")) {
            val innerType = trimmedType.substringAfter("Array<").substringBeforeLast(">")
            "Array<${processUnion(innerType)}>"
        } else {
            processUnion(trimmedType)
        }
    }
    
    /**
     * 处理 Union 类型转换
     */
    private fun processUnion(typeStr: String): String {
        if (!typeStr.contains("Union.U")) return typeStr
        
        val types = extractUnionTypes(typeStr)
        
        // 处理 Booleanable 类型
        if (types.contains("Boolean")) {
            return handleBooleanableType(types)
        }
        
        // 如果都不是基础类型，返回原类型（需要通过 ExtendRelationship 处理）
        val hasBasicType = types.any { it in listOf("Boolean", "String", "Int") || it.startsWith("Array<") }
        return if (hasBasicType) typeStr else typeStr
    }
    
    /**
     * 处理 Booleanable 类型
     */
    private fun handleBooleanableType(types: List<String>): String {
        return when (types.size) {
            2 -> {
                val otherType = types.find { it != "Boolean" } ?: "String"
                "Booleanable${otherType.replace("<", "").replace(">", "")}"
            }
            3 -> {
                val nonBooleanTypes = types.filter { it != "Boolean" }
                if (nonBooleanTypes.size == 2 && nonBooleanTypes[1].contains("Array<${nonBooleanTypes[0]}")) {
                    "Booleanable${nonBooleanTypes[1].replace("<", "").replace(">", "")}"
                } else {
                    types.joinToString(", ")
                }
            }
            else -> types.joinToString(", ")
        }
    }
    
    private fun removeComment(str: String): String {
        return str.replace(Regex("""/\*(.|\r\n|\n)*?\*/"""), "")
    }
    
    /**
     * 克隆属性
     */
    fun clone(): KotlinProperty = copy()
    
    /**
     * 转换为字符串（不带默认值）
     */
    override fun toString(): String {
        return buildString {
            if (comment.isNotEmpty()) append("$comment\n")
            append(getAnnotation())
            if (name == discriminator) append("  // conflict with @SerialName\n  //")
            if (isOverride) append(" override")
            append(" var ")
            append(Constants.kotlinKeywordMap[name] ?: name)
            val actualType = getActualType()
            if (actualType.isNotEmpty()) append(": $actualType?")
        }
    }
    
    /**
     * 转换为字符串（带默认值）
     */
    fun toStringWithValue(): String {
        return buildString {
            append("  ")
            if (comment.isNotEmpty()) append("$comment\n")
            append(getAnnotation())
            if (name == discriminator) append("  // conflict with @SerialName\n  //")
            if (isOverride) append("override ")
            append("var ")
            append(Constants.kotlinKeywordMap[name] ?: name)
            val actualType = getActualType()
            if (actualType.isNotEmpty()) append(" : $actualType?")
            append(" = ${defaultValue.ifEmpty { "null" }}")
        }
    }
}

