package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.core.model.KotlinProperty
import dev.yidafu.swc.generator.config.Constants
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.util.*

/**
 * TypeScript 类型解析器
 * 
 * 使用 AstNode 解析 TypeScript 类型为 Kotlin 类型字符串
 */
object TypeResolver {
    private val jsKotlinTypeMap = mapOf(
        "number" to "Int",
        "boolean" to "Boolean",
        "string" to "String",
        "string[]" to "Array<String>"
    )
    
    val typeAliasMap = mutableMapOf<String, String>()
    
    /**
     * 解析 TsType 为 Kotlin 类型字符串
     */
    fun resolveType(tsType: AstNode?): String {
        if (tsType == null) return "Any"
        
        return try {
            val result = when {
                tsType.isKeywordType() -> resolveKeywordType(tsType)
                tsType.isTypeReference() -> resolveTypeReference(tsType)
                tsType.isUnionType() -> resolveUnionType(tsType)
                tsType.isIntersectionType() -> resolveIntersectionType(tsType)
                tsType.isArrayType() -> resolveArrayType(tsType)
                tsType.type == "TsTupleType" -> resolveTupleType(tsType)
                tsType.isLiteralType() -> resolveLiteralType(tsType)
                tsType.isTypeLiteral() -> resolveTypeLiteral(tsType)
                tsType.type == "TsParenthesizedType" -> resolveType(tsType.getNode("typeAnnotation"))
                else -> {
                    if (tsType.type.isNotEmpty()) {
                        Logger.verbose("未知的类型节点: ${tsType.type}", 8)
                    }
                    "Any"
                }
            }
            
            // 验证和修复泛型完整性
            validateAndFixGenerics(result)
        } catch (e: Exception) {
            Logger.warn("类型解析失败: ${e.message}")
            "Any"
        }
    }
    
    /**
     * 验证和修复泛型完整性
     */
    private fun validateAndFixGenerics(typeStr: String): String {
        if (!typeStr.contains("<")) return typeStr
        
        val openCount = typeStr.count { it == '<' }
        val closeCount = typeStr.count { it == '>' }
        
        return when {
            openCount == closeCount -> typeStr
            openCount > closeCount -> {
                // 补全缺失的 >
                Logger.verbose("修复不完整的泛型: $typeStr", 8)
                typeStr + ">".repeat(openCount - closeCount)
            }
            else -> {
                // 移除多余的 >
                Logger.verbose("修复多余的泛型闭合: $typeStr", 8)
                typeStr.substringBefore(">") + ">"
            }
        }
    }
    
    /**
     * 解析关键字类型
     */
    private fun resolveKeywordType(type: AstNode): String {
        val kind = type.getKeywordKind()
        return when (kind) {
            "string" -> "String"
            "number" -> "Int"
            "boolean" -> "Boolean"
            "undefined" -> "null"
            "bigint" -> "Long"
            else -> "Any"
        }
    }
    
    /**
     * 解析类型引用
     */
    private fun resolveTypeReference(type: AstNode): String {
        return try {
            val typeName = type.getTypeReferenceName() ?: "Any"
            
            // 处理泛型参数
            val typeParams = type.getNode("typeParams")?.getNodes("params") ?: emptyList()
            if (typeParams.isNotEmpty()) {
                val paramTypes = typeParams.map { param ->
                    try {
                        resolveType(param)
                    } catch (e: Exception) {
                        Logger.verbose("泛型参数解析失败，使用 Any: ${e.message}", 8)
                        "Any"
                    }
                }
                return "$typeName<${paramTypes.joinToString(", ")}>"
            }
            
            // 特殊处理 Record
            if (typeName == "Record") {
                return "Map<String, String>"
            }
            
            // 查找类型别名
            typeAliasMap[typeName]?.let {
                return it
            }
            
            typeName
        } catch (e: Exception) {
            Logger.warn("类型引用解析失败: ${e.message}")
            "Any"
        }
    }
    
    /**
     * 清理类型名称，移除多余空格和修复不完整泛型
     */
    private fun cleanTypeName(typeName: String): String {
        var cleaned = typeName.trim()
        
        // 移除多余的空格
        cleaned = cleaned.replace(Regex("\\s+"), "")
        
        // 修复不完整的泛型参数（如果有 < 但没有对应的 >）
        val openCount = cleaned.count { it == '<' }
        val closeCount = cleaned.count { it == '>' }
        if (openCount > closeCount) {
            // 补充缺失的 >
            cleaned += ">".repeat(openCount - closeCount)
        }
        
        return cleaned
    }
    
    /**
     * 解析 Union 类型
     */
    private fun resolveUnionType(type: AstNode): String {
        val typeList = type.getNodes("types")
        val resolvedTypes = typeList.map { resolveType(it) }.map { cleanTypeName(it) }
        val uniqueTypes = resolvedTypes.filter { it != "null" }.distinct()
        
        if (uniqueTypes.size == 1) {
            return uniqueTypes[0]
        }
        
        // 检查是否都是字面量
        if (typeList.all { it.isLiteralType() }) {
            val literals = typeList.mapNotNull { it.getNode("literal") }
            val comment = "  /** literal is: ${literals.joinToString(",") { it.getLiteralValue() ?: "" }} */"
            val kotlinType = literals.firstOrNull()?.let {
                when {
                    it.isStringLiteral() -> "String"
                    it.isNumericLiteral() -> "Int"
                    it.isBooleanLiteral() -> "Boolean"
                    else -> "String"
                }
            } ?: "String"
            return "$comment$kotlinType"
        }
        
        // 处理 Booleanable
        if (uniqueTypes.size == 2) {
            if (uniqueTypes.contains("Boolean")) {
                val otherType = uniqueTypes.find { it != "Boolean" } ?: "String"
                return transformTupleType(listOf("Boolean", otherType))
            }
            
            // 检查是否是 T | Array<T>
            if (uniqueTypes.size == 2 && "Array<${uniqueTypes[0]}>" == uniqueTypes[1]) {
                return uniqueTypes[1]
            }
        }
        
        if (uniqueTypes.size in 2..4) {
            return "Union.U${uniqueTypes.size}<${uniqueTypes.joinToString(", ")}>"
        }
        
        return "Any"
    }
    
    /**
     * 解析 Intersection 类型
     */
    private fun resolveIntersectionType(type: AstNode): String {
        // Intersection 通常取第一个类型
        val types = type.getNodes("types")
        return types.firstOrNull()?.let { resolveType(it) } ?: "Any"
    }
    
    /**
     * 解析数组类型
     */
    private fun resolveArrayType(type: AstNode): String {
        val elemType = type.getNode("elemType")
        val elementType = resolveType(elemType)
        return "Array<$elementType>"
    }
    
    /**
     * 解析元组类型
     */
    private fun resolveTupleType(type: AstNode): String {
        val elemTypes = type.getNodes("elemTypes")
        return if (elemTypes.size == 2) {
            val type1 = resolveType(elemTypes[0].getNode("ty"))
            val type2 = resolveType(elemTypes[1].getNode("ty"))
            transformTupleType(listOf(type1, type2))
        } else {
            "Any"
        }
    }
    
    /**
     * 解析字面量类型
     */
    private fun resolveLiteralType(type: AstNode): String {
        val literal = type.getNode("literal") ?: return "Any"
        val value = literal.getLiteralValue() ?: ""
        val comment = "/* literal is: $value */"
        val kotlinType = when {
            literal.isStringLiteral() -> "String"
            literal.isNumericLiteral() -> "Int"
            literal.isBooleanLiteral() -> "Boolean"
            else -> "Any"
        }
        return "$comment$kotlinType"
    }
    
    /**
     * 解析类型字面量
     */
    private fun resolveTypeLiteral(type: AstNode): String {
        // 检查是否是索引签名（字典类型）
        val members = type.getNodes("members")
        if (members.isNotEmpty() && members[0].type == "TsIndexSignature") {
            val indexSig = members[0]
            // 简化处理，index signature 的 key 通常是 string
            val keyType = "String"
            val typeAnnotation = indexSig.getNode("typeAnnotation")?.getNode("typeAnnotation")
            val valueType = resolveType(typeAnnotation)
            return "Map<$keyType, $valueType>"
        }
        
        // 对于其他类型字面量，需要创建新的类型
        // 这里返回一个占位符，实际处理在其他地方
        return "TypeLiteral"
    }
    
    /**
     * 转换元组类型
     */
    fun transformTupleType(types: List<String>): String {
        val cleanTypes = types.map { removeComment(it) }
        if (cleanTypes.contains("Boolean")) {
            val anotherType = cleanTypes.find { it != "Boolean" } ?: "ErrorType"
            return "Booleanable${anotherType.replace("<", "").replace(">", "")}"
        }
        if (cleanTypes.contains("Float") && cleanTypes.contains("String")) {
            return "String"
        }
        return "Union.U2<${types[0]}, ${types[1]}>"
    }
    
    /**
     * 移除注释
     */
    fun removeComment(str: String): String = str.removeComment()
    
    /**
     * 从 TsTypeLiteral 提取属性
     */
    fun extractPropertiesFromTypeLiteral(typeLiteral: AstNode): List<KotlinProperty> {
        val members = typeLiteral.getNodes("members")
        return members.mapNotNull { member ->
            if (member.isPropertySignature()) {
                extractPropertyFromSignature(member)
            } else {
                null
            }
        }
    }
    
    /**
     * 从 TsPropertySignature 提取属性
     */
    private fun extractPropertyFromSignature(propSig: AstNode): KotlinProperty? {
        val propName = propSig.getPropertyName() ?: return null
        
        if (propName.isEmpty()) return null
        
        val typeAnnotation = propSig.getNode("typeAnnotation")?.getNode("typeAnnotation")
        val propType = resolveType(typeAnnotation)
        
        return KotlinProperty(
            name = propName,
            type = propType,
            defaultValue = "null"
        )
    }
}
