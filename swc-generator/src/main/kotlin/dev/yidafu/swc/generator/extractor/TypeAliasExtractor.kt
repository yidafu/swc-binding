package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.model.kotlin.*
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.util.Logger

/**
 * Type Alias 提取器
 * * 使用 AstNode 提取和分析 type alias 声明
 */
class TypeAliasExtractor(private val visitor: TsAstVisitor) {

    /**
     * 判断是否为字面量 Union（如 type T = "a" | "b"）
     */
    fun isLiteralUnion(typeAlias: AstNode): Boolean {
        val typeAnnotation = typeAlias.getNode("typeAnnotation") ?: return false
        if (!typeAnnotation.isUnionType()) return false

        val types = typeAnnotation.getNodes("types")
        return types.all { it.isLiteralType() }
    }

    /**
     * 判断是否为混合 Union（包含字面量和基础类型）
     */
    fun isMixedUnion(typeAlias: AstNode): Boolean {
        val typeAnnotation = typeAlias.getNode("typeAnnotation") ?: return false
        if (!typeAnnotation.isUnionType()) return false

        val types = typeAnnotation.getNodes("types")
        val hasLiteral = types.any { it.isLiteralType() }
        val hasBasicType = types.any {
            val kind = it.getString("kind")
            kind in listOf("string", "number", "boolean")
        }
        return hasLiteral && hasBasicType
    }

    /**
     * 判断是否为引用 Union（如 type T = A | B）
     */
    fun isRefUnion(typeAlias: AstNode): Boolean {
        val typeAnnotation = typeAlias.getNode("typeAnnotation") ?: return false
        if (!typeAnnotation.isUnionType()) return false

        val types = typeAnnotation.getNodes("types")
        return types.all { it.isTypeReference() }
    }

    /**
     * 判断是否为 Intersection 类型
     */
    fun isIntersectionType(typeAlias: AstNode): Boolean {
        val typeAnnotation = typeAlias.getNode("typeAnnotation") ?: return false
        return typeAnnotation.isIntersectionType()
    }

    /**
     * 提取字面量 Union 的属性列表
     */
    fun extractLiteralUnionProperties(typeAlias: AstNode): List<KotlinDeclaration.PropertyDecl> {
        val typeAnnotation = typeAlias.getNode("typeAnnotation") ?: return emptyList()
        if (!typeAnnotation.isUnionType()) return emptyList()

        val types = typeAnnotation.getNodes("types")
        return types
            .mapNotNull { it.getNode("literal") }
            .mapNotNull { literal -> createPropertyFromLiteral(literal) }
    }

    /**
     * 从字面量创建属性
     */
    private fun createPropertyFromLiteral(literal: AstNode): KotlinDeclaration.PropertyDecl? {
        return when {
            literal.isStringLiteral() -> {
                val value = literal.getStringLiteralValue() ?: return null

                // 使用字面量名称映射
                val literalNameMap = SwcGeneratorConfig().literalNameMap
                val propertyName = literalNameMap[value] ?: literalNameMap[value.uppercase()] ?: sanitizeLiteralName(value)

                // 验证生成的属性名是否有效
                if (!isValidPropertyName(propertyName)) {
                    Logger.debug("跳过无效字面量: '$value' -> '$propertyName'", 6)
                    return null
                }

                KotlinDeclaration.PropertyDecl(
                    name = propertyName,
                    type = KotlinType.StringType,
                    modifier = PropertyModifier.Val,
                    defaultValue = Expression.StringLiteral(value)
                )
            }
            literal.isBooleanLiteral() -> {
                val value = literal.getBooleanLiteralValue() ?: false
                KotlinDeclaration.PropertyDecl(
                    name = "BOOL_${value.toString().uppercase()}",
                    type = KotlinType.Boolean,
                    modifier = PropertyModifier.Val,
                    defaultValue = Expression.BooleanLiteral(value)
                )
            }
            literal.isNumericLiteral() -> {
                val value = literal.getNumericLiteralValue() ?: return null
                val numValue = value.toInt()
                KotlinDeclaration.PropertyDecl(
                    name = "NUMBER_$numValue",
                    type = KotlinType.Int,
                    modifier = PropertyModifier.Val,
                    defaultValue = Expression.NumberLiteral(numValue.toString())
                )
            }
            else -> null
        }
    }

    /**
     * 清理字面量名称，转换为有效的 Kotlin 标识符
     */
    private fun sanitizeLiteralName(value: String): String {
        // 处理 Kotlin 关键字
        val keywordMap = CodeGenerationRules.getKotlinKeywordMap()
        if (keywordMap.containsKey(value)) {
            return keywordMap[value]!!.uppercase()
        }

        // 如果只包含字母数字，转为大写
        if (value.matches(Regex("[a-zA-Z][a-zA-Z0-9]*"))) {
            return value.uppercase()
        }

        // 对于包含特殊字符但没有映射的，生成一个基于内容的名称
        return "LITERAL_${value.hashCode().toString().replace("-", "NEG")}"
    }

    /**
     * 验证属性名是否有效
     */
    private fun isValidPropertyName(name: String): Boolean {
        if (name.isEmpty()) return false
        if (!name[0].isLetter() && name[0] != '_') return false
        return name.all { it.isLetterOrDigit() || it == '_' }
    }

    /**
     * 获取 Union 类型的所有类型名称
     */
    fun getUnionTypeNames(typeAlias: AstNode): List<String> {
        val typeAnnotation = typeAlias.getNode("typeAnnotation") ?: return emptyList()
        if (!typeAnnotation.isUnionType()) return emptyList()

        val types = typeAnnotation.getNodes("types")
        return types
            .mapNotNull { it.getTypeReferenceName() }
            .filter { it.isNotEmpty() }
    }

    /**
     * 提取 Intersection 类型信息
     * 返回 Pair<父类型, TypeLiteral AstNode>
     */
    fun extractIntersectionInfo(typeAlias: AstNode): Pair<String, AstNode>? {
        val typeAnnotation = typeAlias.getNode("typeAnnotation") ?: return null
        if (!typeAnnotation.isIntersectionType()) return null

        val types = typeAnnotation.getNodes("types")
        if (types.size < 2) return null

        val first = types[0]
        val second = types[1]

        if (!first.isTypeReference()) return null
        if (!second.isTypeLiteral()) return null

        val parentName = first.getTypeReferenceName() ?: return null

        return parentName to second
    }

    /**
     * 检查字面量 Union 的所有类型是否相同
     */
    fun isAllLiteralTypeSame(typeAlias: AstNode): Boolean {
        val typeAnnotation = typeAlias.getNode("typeAnnotation") ?: return false
        if (!typeAnnotation.isUnionType()) return false

        val types = typeAnnotation.getNodes("types")
            .mapNotNull {
                val literal = it.getNode("literal")
                when {
                    literal?.isStringLiteral() == true -> "String"
                    literal?.isNumericLiteral() == true -> "Number"
                    literal?.isBooleanLiteral() == true -> "Boolean"
                    else -> null
                }
            }

        return types.isNotEmpty() && types.distinct().size == 1
    }

    /**
     * 获取类型别名的类型字符串表示
     */
    fun getTypeString(typeAlias: AstNode): String {
        val typeAnnotation = typeAlias.getNode("typeAnnotation")
        return TypeResolver.resolveType(typeAnnotation)
    }

    /**
     * 提取类型别名名称
     */
    fun getTypeAliasName(typeAlias: AstNode): String {
        return typeAlias.getTypeAliasName() ?: ""
    }
}
