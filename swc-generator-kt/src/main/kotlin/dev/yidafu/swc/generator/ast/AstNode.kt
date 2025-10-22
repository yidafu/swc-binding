package dev.yidafu.swc.generator.ast

import kotlinx.serialization.json.*

/**
 * 通用 AST 节点
 * 
 * 这是一个轻量级的 AST 节点包装器，提供类型安全的访问方法
 */
data class AstNode(
    val type: String,
    private val data: JsonObject
) {
    /**
     * 获取字符串字段
     */
    fun getString(name: String): String? = 
        data[name]?.jsonPrimitive?.contentOrNull
    
    /**
     * 获取数字字段
     */
    fun getInt(name: String): Int? = 
        data[name]?.jsonPrimitive?.intOrNull
    
    /**
     * 获取布尔字段
     */
    fun getBoolean(name: String): Boolean? = 
        data[name]?.jsonPrimitive?.booleanOrNull
    
    /**
     * 获取子节点
     */
    fun getNode(name: String): AstNode? {
        val element = data[name] ?: return null
        if (element is JsonNull) return null
        val obj = element.jsonObject
        return AstNode(
            obj["type"]?.jsonPrimitive?.content ?: "",
            obj
        )
    }
    
    /**
     * 获取节点数组
     */
    fun getNodes(name: String): List<AstNode> {
        val element = data[name] ?: return emptyList()
        if (element is JsonNull) return emptyList()
        val array = element.jsonArray
        return array.mapNotNull { item ->
            if (item is JsonNull) return@mapNotNull null
            val obj = item.jsonObject
            AstNode(
                obj["type"]?.jsonPrimitive?.content ?: "",
                obj
            )
        }
    }
    
    /**
     * 检查字段是否存在且不为 null
     */
    fun has(name: String): Boolean = 
        data.containsKey(name) && data[name] !is JsonNull
    
    /**
     * 获取原始 JSON 数据（用于特殊情况）
     */
    fun getRaw(name: String): JsonElement? = data[name]
    
    /**
     * 获取所有字段名
     */
    fun getFieldNames(): Set<String> = data.keys
    
    /**
     * 转换为 JSON 对象（用于调试）
     */
    fun toJsonObject(): JsonObject = data
    
    companion object {
        /**
         * 从 JSON 字符串创建
         */
        fun fromJson(jsonString: String): AstNode {
            val element = Json.parseToJsonElement(jsonString)
            val obj = element.jsonObject
            return AstNode(
                obj["type"]?.jsonPrimitive?.content ?: "Unknown",
                obj
            )
        }
        
        /**
         * 从 JsonObject 创建
         */
        fun fromJsonObject(obj: JsonObject): AstNode {
            return AstNode(
                obj["type"]?.jsonPrimitive?.content ?: "Unknown",
                obj
            )
        }
    }
    
    override fun toString(): String = "AstNode(type=$type)"
}

/**
 * Span 位置信息
 */
data class Span(
    val start: Int,
    val end: Int,
    val ctxt: Int
)

/**
 * 获取节点的 span
 */
fun AstNode.getSpan(): Span? {
    val spanData = getRaw("span")?.jsonObject ?: return null
    return Span(
        start = spanData["start"]?.jsonPrimitive?.int ?: 0,
        end = spanData["end"]?.jsonPrimitive?.int ?: 0,
        ctxt = spanData["ctxt"]?.jsonPrimitive?.int ?: 0
    )
}

/**
 * 判断节点类型
 */
fun AstNode.isType(vararg types: String): Boolean = types.contains(this.type)

