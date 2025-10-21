package dev.yidafu.swc.generator.ast

import dev.yidafu.swc.generator.model.KotlinProperty
import dev.yidafu.swc.generator.transform.Constants
import dev.yidafu.swc.generator.util.*
import dev.yidafu.swc.types.*

/**
 * TypeScript 类型解析器
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
    fun resolveType(tsType: TsType?): String {
        if (tsType == null) return "Any"
        
        return when (tsType) {
            is TsKeywordType -> resolveKeywordType(tsType)
            is TsTypeReference -> resolveTypeReference(tsType)
            is TsUnionOrIntersectionType -> resolveUnionOrIntersectionType(tsType)
            is TsArrayType -> resolveArrayType(tsType)
            is TsTupleType -> resolveTupleType(tsType)
            is TsLiteralType -> resolveLiteralType(tsType)
            is TsTypeLiteral -> resolveTypeLiteral(tsType)
            is TsParenthesizedType -> resolveType(tsType.typeAnnotation)
            else -> "Any"
        }
    }
    
    /**
     * 解析关键字类型
     */
    private fun resolveKeywordType(type: TsKeywordType): String {
        return when (type.kind) {
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
    private fun resolveTypeReference(type: TsTypeReference): String {
        val typeName = type.typeName.getTypeName().takeIf { it.isNotEmpty() } ?: "Any"
        
        // 处理泛型参数
        val typeParams = type.typeParams?.params.orEmpty()
        if (typeParams.isNotEmpty()) {
            val paramTypes = typeParams.map { resolveType(it) }
            return "$typeName<${paramTypes.joinToString(", ")}>"
        }
        
        // 特殊处理 Record
        if (typeName == "Record") {
            return "Map<String, String>"
        }
        
        // 查找类型别名
        typeAliasMap[typeName]?.let {
            return "\t/**\n  * [$typeName]\n */$it"
        }
        
        return typeName
    }
    
    /**
     * 解析 Union 或 Intersection 类型
     */
    private fun resolveUnionOrIntersectionType(type: TsUnionOrIntersectionType): String {
        return when (type) {
            is TsUnionType -> resolveUnionType(type)
            is TsIntersectionType -> resolveIntersectionType(type)
            else -> "Any"
        }
    }
    
    /**
     * 解析 Union 类型
     */
    private fun resolveUnionType(type: TsUnionType): String {
        val typeList = type.types.orEmpty()
        val resolvedTypes = typeList.map { resolveType(it) }
        val uniqueTypes = resolvedTypes.filter { it != "null" }.distinct()
        
        if (uniqueTypes.size == 1) {
            return uniqueTypes[0]
        }
        
        // 检查是否都是字面量
        if (typeList.all { it is TsLiteralType }) {
            val literals = typeList.mapNotNull { (it as? TsLiteralType)?.literal }
            val comment = "  /** literal is: ${literals.joinToString(",")} */"
            val kotlinType = literals.firstOrNull()?.getKotlinType() ?: "String"
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
    private fun resolveIntersectionType(type: TsIntersectionType): String {
        // Intersection 通常取第一个类型
        return type.types.orEmpty().firstOrNull()?.let { resolveType(it) } ?: "Any"
    }
    
    /**
     * 解析数组类型
     */
    private fun resolveArrayType(type: TsArrayType): String {
        val elementType = resolveType(type.elemType)
        return "Array<$elementType>"
    }
    
    /**
     * 解析元组类型
     */
    private fun resolveTupleType(type: TsTupleType): String {
        val elemTypes = type.elemTypes.orEmpty()
        return if (elemTypes.size == 2) {
            val type1 = resolveType(elemTypes[0].ty)
            val type2 = resolveType(elemTypes[1].ty)
            transformTupleType(listOf(type1, type2))
        } else {
            "Any"
        }
    }
    
    /**
     * 解析字面量类型
     */
    private fun resolveLiteralType(type: TsLiteralType): String {
        val literal = type.literal ?: return "Any"
        val comment = "/* literal is: ${literal.getValueString()} */"
        val kotlinType = literal.getKotlinType()
        return "$comment$kotlinType"
    }
    
    /**
     * 解析类型字面量
     */
    private fun resolveTypeLiteral(type: TsTypeLiteral): String {
        // 检查是否是索引签名（字典类型）
        val members = type.members
        if (members != null && members.isNotEmpty() && members[0] is TsIndexSignature) {
            val indexSig = members[0] as TsIndexSignature
            // 简化处理，index signature 的 key 通常是 string
            val keyType = "String"
            val valueType = indexSig.typeAnnotation?.typeAnnotation?.let { resolveType(it) } ?: "String"
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
    fun extractPropertiesFromTypeLiteral(typeLiteral: TsTypeLiteral): List<KotlinProperty> {
        return typeLiteral.members.orEmpty().mapNotNull { member ->
            (member as? TsPropertySignature)?.let { extractPropertyFromSignature(it) }
        }
    }
    
    /**
     * 从 TsPropertySignature 提取属性
     */
    private fun extractPropertyFromSignature(propSig: TsPropertySignature): KotlinProperty? {
        val propName = when (val key = propSig.key) {
            is Identifier -> key.safeValue()
            is StringLiteral -> key.value
            else -> null
        }?.takeIf { it.isNotEmpty() } ?: return null
        
        val propType = propSig.typeAnnotation?.typeAnnotation?.let { resolveType(it) } ?: "Any"
        
        return KotlinProperty(
            name = propName,
            type = propType,
            defaultValue = "null"
        )
    }
}

