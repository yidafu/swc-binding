package dev.yidafu.swc.generator.ast

import dev.yidafu.swc.generator.model.KotlinProperty
import dev.yidafu.swc.generator.transform.Constants
import dev.yidafu.swc.generator.util.*
import dev.yidafu.swc.types.*

/**
 * Type Alias 提取器
 */
class TypeAliasExtractor(private val visitor: TsAstVisitor) {
    
    /**
     * 判断是否为字面量 Union（如 type T = "a" | "b"）
     */
    fun isLiteralUnion(typeAlias: TsTypeAliasDeclaration): Boolean {
        return (typeAlias.typeAnnotation as? TsUnionType)
            ?.types.orEmpty()
            .all { it is TsLiteralType }
    }
    
    /**
     * 判断是否为混合 Union（包含字面量和基础类型）
     */
    fun isMixedUnion(typeAlias: TsTypeAliasDeclaration): Boolean {
        val types = (typeAlias.typeAnnotation as? TsUnionType)?.types.orEmpty()
        val hasLiteral = types.any { it is TsLiteralType }
        val hasBasicType = types.any { 
            (it as? TsKeywordType)?.kind in listOf("string", "number", "boolean")
        }
        return hasLiteral && hasBasicType
    }
    
    /**
     * 判断是否为引用 Union（如 type T = A | B）
     */
    fun isRefUnion(typeAlias: TsTypeAliasDeclaration): Boolean {
        return (typeAlias.typeAnnotation as? TsUnionType)
            ?.types.orEmpty()
            .all { it is TsTypeReference }
    }
    
    /**
     * 判断是否为 Intersection 类型
     */
    fun isIntersectionType(typeAlias: TsTypeAliasDeclaration): Boolean {
        return typeAlias.typeAnnotation is TsIntersectionType
    }
    
    /**
     * 提取字面量 Union 的属性列表
     */
    fun extractLiteralUnionProperties(typeAlias: TsTypeAliasDeclaration): List<KotlinProperty> {
        val unionType = typeAlias.typeAnnotation as? TsUnionType ?: return emptyList()
        
        return unionType.types.orEmpty()
            .mapNotNull { (it as? TsLiteralType)?.literal }
            .mapNotNull { literal -> createPropertyFromLiteral(literal) }
    }
    
    /**
     * 从字面量创建属性
     */
    private fun createPropertyFromLiteral(literal: TsLiteral): KotlinProperty? {
        return when (literal) {
            is StringLiteral -> KotlinProperty(
                modifier = "const val",
                name = Constants.literalNameMap[literal.value?.uppercase()] ?: literal.value?.uppercase() ?: "",
                type = "String",  // 设置类型为 String
                defaultValue = literal.getValueString()
            )
            is BooleanLiteral -> KotlinProperty(
                modifier = "const val",
                name = "BOOL_${literal.value.toString().uppercase()}",
                type = "Boolean",  // 设置类型为 Boolean
                defaultValue = literal.value.toString()
            )
            is NumericLiteral -> {
                val numValue = literal.value ?: return null
                KotlinProperty(
                    modifier = "const val",
                    name = "NUMBER_${numValue.toInt()}",  // 转换为整数避免小数点
                    type = "Int",  // 设置类型为 Int
                    defaultValue = numValue.toInt().toString()
                )
            }
            else -> null
        }
    }
    
    /**
     * 获取 Union 类型的所有类型名称
     */
    fun getUnionTypeNames(typeAlias: TsTypeAliasDeclaration): List<String> {
        return (typeAlias.typeAnnotation as? TsUnionType)
            ?.types.orEmpty()
            .mapNotNull { (it as? TsTypeReference)?.typeName?.getTypeName() }
            .filter { it.isNotEmpty() }
    }
    
    /**
     * 提取 Intersection 类型信息
     * 返回 Pair<父类型, TypeLiteral>
     */
    fun extractIntersectionInfo(typeAlias: TsTypeAliasDeclaration): Pair<String, TsTypeLiteral>? {
        val types = (typeAlias.typeAnnotation as? TsIntersectionType)?.types.orEmpty()
        if (types.size < 2) return null
        
        val first = types[0] as? TsTypeReference ?: return null
        val second = types[1] as? TsTypeLiteral ?: return null
        val parentName = first.typeName.getTypeName().takeIf { it.isNotEmpty() } ?: return null
        
        return parentName to second
    }
    
    /**
     * 检查字面量 Union 的所有类型是否相同
     */
    fun isAllLiteralTypeSame(typeAlias: TsTypeAliasDeclaration): Boolean {
        val types = (typeAlias.typeAnnotation as? TsUnionType)
            ?.types.orEmpty()
            .mapNotNull { (it as? TsLiteralType)?.literal?.getKotlinType() }
        
        return types.isNotEmpty() && types.distinct().size == 1
    }
    
    /**
     * 获取类型别名的类型字符串表示
     */
    fun getTypeString(typeAlias: TsTypeAliasDeclaration): String {
        return TypeResolver.resolveType(typeAlias.typeAnnotation)
    }
    
    /**
     * 提取类型别名名称
     */
    fun getTypeAliasName(typeAlias: TsTypeAliasDeclaration): String {
        return typeAlias.id.safeValue()
    }
}

