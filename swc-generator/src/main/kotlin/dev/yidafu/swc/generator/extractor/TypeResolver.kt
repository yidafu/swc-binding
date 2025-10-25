package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.adt.converter.TypeConverter
import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.result.*
import dev.yidafu.swc.generator.adt.typescript.*
import dev.yidafu.swc.generator.config.ConfigLoader
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.util.*

/**
 * TypeScript 类型解析器
 * * 使用 AstNode 解析 TypeScript 类型为 ADT
 * 专注于纯 TypeScript 类型提取，不包含 Kotlin 特定逻辑
 */
object TypeResolver {
    private val config by lazy { ConfigLoader.loadConfig() }

    val typeAliasMap = mutableMapOf<String, String>()

    // 缓存 TypeScript 类型解析结果
    private val tsTypeCache = mutableMapOf<AstNode, TypeScriptType>()

    /**
     * 提取 TypeScript 类型（ADT 版本）
     */
    fun extractTypeScriptType(tsType: AstNode?): GeneratorResult<TypeScriptType> {
        if (tsType == null) {
            return GeneratorResultFactory.success(TypeScriptType.Any)
        }

        // 检查缓存
        tsTypeCache[tsType]?.let {
            return GeneratorResultFactory.success(it)
        }

        val result = when {
            tsType.isKeywordType() -> extractKeywordType(tsType)
            tsType.isTypeReference() -> extractTypeReference(tsType)
            tsType.isUnionType() -> extractUnionType(tsType)
            tsType.isIntersectionType() -> extractIntersectionType(tsType)
            tsType.isArrayType() -> extractArrayType(tsType)
            tsType.type == "TsTupleType" -> extractTupleType(tsType)
            tsType.isLiteralType() -> extractLiteralType(tsType)
            tsType.isTypeLiteral() -> extractTypeLiteralType(tsType)
            tsType.type == "TsParenthesizedType" -> extractTypeScriptType(tsType.getNode("typeAnnotation"))
            else -> GeneratorResultFactory.success(TypeScriptType.Any)
        }

        // 缓存结果
        result.getOrNull()?.let { tsTypeCache[tsType] = it }

        return result
    }

    /**
     * 解析 TsType 为 Kotlin 类型字符串（向后兼容版本）
     */
    fun resolveType(tsType: AstNode?): String {
        return extractTypeScriptType(tsType)
            .flatMap { tsType ->
                TypeConverter.convert(tsType)
            }
            .map { it.toTypeString() }
            .getOrDefault("Any")
    }

    // ==================== ADT 提取方法 ====================

    /**
     * 提取关键字类型
     */
    private fun extractKeywordType(type: AstNode): GeneratorResult<TypeScriptType> {
        val kind = type.getKeywordKind()
        val keywordKind = when (kind) {
            "string" -> KeywordKind.STRING
            "number" -> KeywordKind.NUMBER
            "boolean" -> KeywordKind.BOOLEAN
            "undefined" -> KeywordKind.UNDEFINED
            "bigint" -> KeywordKind.BIGINT
            "void" -> KeywordKind.VOID
            "any" -> KeywordKind.ANY
            "unknown" -> KeywordKind.UNKNOWN
            "never" -> KeywordKind.NEVER
            "symbol" -> KeywordKind.SYMBOL
            "object" -> KeywordKind.OBJECT
            "null" -> return GeneratorResultFactory.success(TypeScriptType.Null)
            else -> return GeneratorResultFactory.success(TypeScriptType.Any)
        }
        return GeneratorResultFactory.success(TypeScriptType.Keyword(keywordKind))
    }

    /**
     * 提取类型引用
     */
    private fun extractTypeReference(type: AstNode): GeneratorResult<TypeScriptType> {
        val typeName = type.getTypeReferenceName() ?: return GeneratorResultFactory.success(TypeScriptType.Any)

        // 处理泛型参数
        val typeParams = type.getNode("typeParams")?.getNodes("params") ?: emptyList()
        val paramTypes = typeParams.mapNotNull { param ->
            extractTypeScriptType(param).getOrNull()
        }

        return GeneratorResultFactory.success(TypeScriptType.Reference(typeName, paramTypes))
    }

    /**
     * 提取联合类型
     */
    private fun extractUnionType(type: AstNode): GeneratorResult<TypeScriptType> {
        val typeList = type.getNodes("types")
        val types = typeList.mapNotNull {
            extractTypeScriptType(it).getOrNull()
        }
        return GeneratorResultFactory.success(TypeScriptType.Union(types))
    }

    /**
     * 提取交叉类型
     */
    private fun extractIntersectionType(type: AstNode): GeneratorResult<TypeScriptType> {
        val types = type.getNodes("types")
        val typeTypes = types.mapNotNull {
            extractTypeScriptType(it).getOrNull()
        }
        return GeneratorResultFactory.success(TypeScriptType.Intersection(typeTypes))
    }

    /**
     * 提取数组类型
     */
    private fun extractArrayType(type: AstNode): GeneratorResult<TypeScriptType> {
        val elemType = type.getNode("elemType")
        val elementType = extractTypeScriptType(elemType).getOrDefault(TypeScriptType.Any)
        return GeneratorResultFactory.success(TypeScriptType.Array(elementType))
    }

    /**
     * 提取元组类型
     */
    private fun extractTupleType(type: AstNode): GeneratorResult<TypeScriptType> {
        val elemTypes = type.getNodes("elemTypes")
        val types = elemTypes.mapNotNull { elem ->
            extractTypeScriptType(elem.getNode("ty")).getOrNull()
        }
        return GeneratorResultFactory.success(TypeScriptType.Tuple(types))
    }

    /**
     * 提取字面量类型
     */
    private fun extractLiteralType(type: AstNode): GeneratorResult<TypeScriptType> {
        val literal = type.getNode("literal") ?: return GeneratorResultFactory.success(TypeScriptType.Any)

        val literalValue = when {
            literal.isStringLiteral() -> {
                val value = literal.getStringLiteralValue() ?: ""
                LiteralValue.StringLiteral(value)
            }
            literal.isNumericLiteral() -> {
                val value = literal.getNumericLiteralValue() ?: 0.0
                LiteralValue.NumberLiteral(value)
            }
            literal.isBooleanLiteral() -> {
                val value = literal.getBooleanLiteralValue() ?: false
                LiteralValue.BooleanLiteral(value)
            }
            else -> {
                LiteralValue.StringLiteral("")
            }
        }

        return GeneratorResultFactory.success(TypeScriptType.Literal(literalValue))
    }

    /**
     * 提取类型字面量
     */
    private fun extractTypeLiteralType(type: AstNode): GeneratorResult<TypeScriptType> {
        val members = type.getNodes("members")
        val typeMembers = members.mapNotNull { member ->
            if (member.isPropertySignature()) {
                val propName = member.getPropertyName() ?: return@mapNotNull null
                val typeAnnotation = member.getNode("typeAnnotation")?.getNode("typeAnnotation")
                val memberType = extractTypeScriptType(typeAnnotation).getOrDefault(TypeScriptType.Any)
                TypeMember(propName, memberType, optional = false, readonly = false)
            } else {
                null
            }
        }

        return GeneratorResultFactory.success(TypeScriptType.TypeLiteral(typeMembers))
    }

    /**
     * 从 TsTypeLiteral 提取属性
     */
    fun extractPropertiesFromTypeLiteral(typeLiteral: AstNode): List<KotlinDeclaration.PropertyDecl> {
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
    private fun extractPropertyFromSignature(propSig: AstNode): KotlinDeclaration.PropertyDecl? {
        val propName = propSig.getPropertyName() ?: return null

        if (propName.isEmpty()) return null

        val typeAnnotation = propSig.getNode("typeAnnotation")?.getNode("typeAnnotation")
        val tsType = extractTypeScriptType(typeAnnotation).getOrDefault(TypeScriptType.Any)
        val kotlinType = TypeConverter.convert(tsType).getOrDefault(KotlinType.Any)

        return KotlinDeclaration.PropertyDecl(
            name = propName,
            type = kotlinType,
            modifier = PropertyModifier.Var,
            defaultValue = Expression.StringLiteral("")
        )
    }
}