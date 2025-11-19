package dev.yidafu.swc.generator.converter.type

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.model.kotlin.*
import dev.yidafu.swc.generator.model.typescript.*
import dev.yidafu.swc.generator.model.typescript.KeywordKind
import dev.yidafu.swc.generator.model.typescript.LiteralValue
import dev.yidafu.swc.generator.model.typescript.getTypeName
import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.util.Logger

/**
 * TypeScript 到 Kotlin 类型转换器
 * 在 ADT 转换过程中自动处理所有类型转换
 */
class TypeConverter(
    private val config: Configuration,
    private val nestedTypeRegistry: Map<String, String> = emptyMap(),
    private val inheritanceAnalyzer: InheritanceAnalyzer? = null
) {

    /**
     * 将 TypeScript 类型转换为 Kotlin 类型
     */
    fun convert(tsType: TypeScriptType): GeneratorResult<KotlinType> {
        return try {
            val result = when (tsType) {
                is TypeScriptType.Keyword -> convertKeyword(tsType.kind)
                is TypeScriptType.Reference -> convertReference(tsType)
                is TypeScriptType.Union -> convertUnion(tsType)
                is TypeScriptType.Intersection -> convertIntersection(tsType)
                is TypeScriptType.Array -> convertArray(tsType)
                is TypeScriptType.Tuple -> convertTuple(tsType)
                is TypeScriptType.Literal -> convertLiteral(tsType)
                is TypeScriptType.TypeLiteral -> convertTypeLiteral(tsType)
                is TypeScriptType.Function -> convertFunction(tsType)
                is TypeScriptType.IndexSignature -> convertIndexSignature(tsType)
                is TypeScriptType.Any -> KotlinTypeFactory.any()
                is TypeScriptType.Unknown -> KotlinTypeFactory.any()
                is TypeScriptType.Never -> KotlinTypeFactory.nothing()
                is TypeScriptType.Void -> KotlinTypeFactory.unit()
                is TypeScriptType.Null -> KotlinTypeFactory.nothing()
                is TypeScriptType.Undefined -> KotlinTypeFactory.nothing()
            }

            GeneratorResultFactory.success(result)
        } catch (e: Exception) {
            Logger.error("类型转换失败: ${tsType.getTypeName()}, ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.TYPE_RESOLUTION_ERROR,
                message = "Failed to convert type: ${tsType.getTypeName()}",
                cause = e
            )
        }
    }

    /**
     * 转换关键字类型
     */
    private fun convertKeyword(kind: KeywordKind): KotlinType {
        return when (kind) {
            KeywordKind.STRING -> KotlinTypeFactory.string()
            KeywordKind.NUMBER -> KotlinTypeFactory.double()
            KeywordKind.BOOLEAN -> KotlinTypeFactory.boolean()
            KeywordKind.UNDEFINED -> KotlinTypeFactory.nothing()
            KeywordKind.NULL -> KotlinTypeFactory.nothing()
            KeywordKind.BIGINT -> KotlinTypeFactory.long()
            KeywordKind.VOID -> KotlinTypeFactory.unit()
            KeywordKind.ANY -> KotlinTypeFactory.any()
            KeywordKind.UNKNOWN -> KotlinTypeFactory.any()
            KeywordKind.NEVER -> KotlinTypeFactory.nothing()
            KeywordKind.SYMBOL -> KotlinTypeFactory.string() // Symbol 转换为 String
            KeywordKind.OBJECT -> KotlinTypeFactory.generic("Map", KotlinTypeFactory.string(), KotlinTypeFactory.string())
        }
    }

    /**
     * 转换类型引用
     */
    private fun convertReference(ref: TypeScriptType.Reference): KotlinType {
        // 处理泛型参数
        val mappedName = CodeGenerationRules.mapTypeName(ref.name)

        if (ref.typeParams.isNotEmpty()) {
            val params = ref.typeParams.mapNotNull {
                convert(it).getOrNull()
            }
            return KotlinTypeFactory.generic(mappedName, *params.toTypedArray())
        }

        // 检查特殊属性类型覆盖
        val propertyOverride = CodeGenerationRules.propertyTypeOverrides[ref.name]
        if (propertyOverride != null) {
            // 直接返回配置中指定的覆盖类型
            Logger.debug("使用类型覆盖: ${ref.name} -> ${propertyOverride.toTypeString()}", 6)
            return propertyOverride
        }

        nestedTypeRegistry[ref.name]?.let { parent ->
            val mappedParent = CodeGenerationRules.mapTypeName(parent)
            return KotlinTypeFactory.nested(mappedParent, mappedName)
        }

        return KotlinTypeFactory.simple(mappedName)
    }

    /**
     * 创建 Union 类型（U2/U3）
     * 统一处理 Union.U2/U3 的创建逻辑，减少代码重复
     */
    private fun createUnionType(typeParams: Array<KotlinType>): KotlinType {
        return when (typeParams.size) {
            2 -> KotlinTypeFactory.generic("Union.U2", *typeParams)
            3 -> KotlinTypeFactory.generic("Union.U3", *typeParams)
            else -> KotlinTypeFactory.any()
        }
    }

    /**
     * 转换联合类型
     */
    private fun convertUnion(union: TypeScriptType.Union): KotlinType {
        Logger.debug("转换联合类型: ${union.types.size} 个类型", 6)

        // 优先检查是否为 T | undefined 形式，转换为 T?
        if (isUndefinedUnion(union)) {
            Logger.debug("  T | undefined 联合类型 -> T?", 6)
            return convertUndefinedUnion(union)
        }

        // 规则：字符串字面量 + 布尔（关键字或字面量）仅生成 Boolean | String（U2）
        if (isOnlyStringLiteralsAndBoolean(union)) {
            Logger.debug("  字符串字面量 + 布尔 -> Union.U2<Boolean, String>", 6)
            return createUnionType(arrayOf(KotlinTypeFactory.boolean(), KotlinTypeFactory.string()))
        }

        // 检查是否为字面量联合类型
        if (isLiteralUnion(union)) {
            Logger.debug("  字面量联合类型", 6)
            return convertLiteralUnion(union)
        }

        // 检查是否为接口联合类型
        if (isInterfaceUnion(union)) {
            Logger.debug("  接口联合类型", 6)
            return convertInterfaceUnion(union)
        }

        // 检查是否为 Union.U2/U3 类型
        if (isUnionType(union)) {
            Logger.debug("  Union.U2/U3 类型", 6)
            return convertUnionType(union)
        }

        // 检查是否为混合类型联合（包含字面量和对象类型）
        if (isMixedUnion(union)) {
            Logger.debug("  混合类型联合", 6)
            return convertMixedUnion(union)
        }

        // 尝试将所有类型转换为 Union.U2/U3（适用于基础类型与字面量组合）
        val convertedTypes = union.types.map { convert(it).getOrNull() }
        if (convertedTypes.all { it != null }) {
            // 去重（例如多字符串字面量合并为一个 String）
            val distinctParams = convertedTypes.filterNotNull()
                .distinctBy { it.toTypeString() }
            if (distinctParams.size in 2..4) {
                Logger.debug("  通用联合类型去重后 -> Union.U${distinctParams.size}", 6)
                return createUnionType(distinctParams.toTypedArray())
            }
        }

        Logger.debug("  其他联合类型 -> Any", 6)
        return KotlinTypeFactory.any()
    }

    /**
     * 转换交叉类型
     */
    private fun convertIntersection(intersection: TypeScriptType.Intersection): KotlinType {
        // 交叉类型转换为第一个类型（简化处理）
        val firstType = intersection.types.firstOrNull()
        return if (firstType != null) {
            convert(firstType).getOrDefault(KotlinTypeFactory.any())
        } else {
            KotlinTypeFactory.any()
        }
    }

    /**
     * 转换数组类型
     */
    private fun convertArray(array: TypeScriptType.Array): KotlinType {
        val elementType = convert(array.elementType).getOrDefault(KotlinTypeFactory.any())
        return KotlinTypeFactory.generic("Array", elementType)
    }

    /**
     * 转换元组类型
     */
    private fun convertTuple(tuple: TypeScriptType.Tuple): KotlinType {
        val elementTypes = tuple.types.mapNotNull { convert(it).getOrNull() }
        // Convert 2-element tuples to Union.U2 for better type safety
        return when (elementTypes.size) {
            2 -> createUnionType(elementTypes.toTypedArray())
            else -> KotlinTypeFactory.generic("Array", *elementTypes.toTypedArray())
        }
    }

    /**
     * 转换字面量类型
     */
    private fun convertLiteral(literal: TypeScriptType.Literal): KotlinType {
        return when (literal.value) {
            is LiteralValue.StringLiteral -> KotlinTypeFactory.string()
            is LiteralValue.NumberLiteral -> KotlinTypeFactory.int()
            is LiteralValue.BooleanLiteral -> KotlinTypeFactory.boolean()
            is LiteralValue.NullLiteral -> KotlinTypeFactory.nothing()
            is LiteralValue.UndefinedLiteral -> KotlinTypeFactory.nothing()
        }
    }

    /**
     * 转换类型字面量
     */
    private fun convertTypeLiteral(typeLiteral: TypeScriptType.TypeLiteral): KotlinType {
        // 类型字面量转换为 Any（简化处理）
        return KotlinTypeFactory.any()
    }

    /**
     * 转换函数类型
     */
    private fun convertFunction(function: TypeScriptType.Function): KotlinType {
        // 函数类型转换为 Function（简化处理）
        return KotlinTypeFactory.simple("Function")
    }

    /**
     * 转换索引签名
     */
    private fun convertIndexSignature(indexSig: TypeScriptType.IndexSignature): KotlinType {
        val keyType = convert(indexSig.keyType).getOrDefault(KotlinTypeFactory.string())
        val valueType = convert(indexSig.valueType).getOrDefault(KotlinTypeFactory.any())
        return KotlinTypeFactory.generic("Map", keyType, valueType)
    }

    /**
     * 检查是否为 T | undefined 形式的联合类型
     */
    private fun isUndefinedUnion(union: TypeScriptType.Union): Boolean {
        if (union.types.size != 2) return false

        val hasUndefined = union.types.any {
            it is TypeScriptType.Undefined || (it is TypeScriptType.Keyword && it.kind == KeywordKind.UNDEFINED)
        }

        return hasUndefined
    }

    /**
     * 转换 T | undefined 联合类型为 T?
     */
    private fun convertUndefinedUnion(union: TypeScriptType.Union): KotlinType {
        // 找到非 undefined 的类型
        val nonUndefinedType = union.types.firstOrNull {
            it !is TypeScriptType.Undefined && !(it is TypeScriptType.Keyword && it.kind == KeywordKind.UNDEFINED)
        }

        if (nonUndefinedType == null) {
            Logger.warn("未找到非 undefined 类型，返回 Any?")
            return KotlinTypeFactory.nullable(KotlinTypeFactory.any())
        }

        // 转换非 undefined 类型
        val kotlinType = convert(nonUndefinedType).getOrDefault(KotlinTypeFactory.any())

        // 如果已经是可空类型，直接返回；否则包装为可空类型
        return if (kotlinType is KotlinType.Nullable) {
            kotlinType
        } else {
            KotlinTypeFactory.nullable(kotlinType)
        }
    }

    /**
     * 检查是否为字面量联合类型
     */
    private fun isLiteralUnion(union: TypeScriptType.Union): Boolean {
        return union.types.all { it is TypeScriptType.Literal }
    }

    /**
     * 检查是否为接口联合类型
     */
    private fun isInterfaceUnion(union: TypeScriptType.Union): Boolean {
        return union.types.all { it is TypeScriptType.Reference }
    }

    /**
     * 检查是否为混合类型联合（包含字面量和对象类型）
     */
    private fun isMixedUnion(union: TypeScriptType.Union): Boolean {
        val typeCount = union.types.size
        if (typeCount !in 2..4) return false

        val hasLiteral = union.types.any { it is TypeScriptType.Literal }
        val hasObjectType = union.types.any { it is TypeScriptType.TypeLiteral }
        val hasReference = union.types.any { it is TypeScriptType.Reference }

        // 混合类型：包含字面量 + (对象类型 或 引用类型)
        return hasLiteral && (hasObjectType || hasReference)
    }

    /**
     * 转换字面量联合类型
     */
    private fun convertLiteralUnion(union: TypeScriptType.Union): KotlinType {
        // 检查字面量类型
        val literalTypes = union.types.mapNotNull { if (it is TypeScriptType.Literal) it.value else null }

        // 如果都是字符串字面量，转换为 Union.U2/U3 或 Any
        if (literalTypes.all { it is LiteralValue.StringLiteral }) {
            // 直接折叠为 String，避免生成 U3<String, String, ...>
            return KotlinTypeFactory.string()
        }

        // 如果都是布尔字面量，转换为 Union.U2/U3
        if (literalTypes.all { it is LiteralValue.BooleanLiteral }) {
            val typeCount = union.types.size
            if (typeCount in 2..3) {
                val booleanType: KotlinType = KotlinTypeFactory.boolean()
                val typeParams: Array<KotlinType> = Array(typeCount) { booleanType }
                return createUnionType(typeParams)
            }
            return KotlinTypeFactory.boolean()
        }

        // 如果都是数字字面量，直接提升为 Int 或 Double
        if (literalTypes.all { it is LiteralValue.NumberLiteral }) {
            val hasFraction = literalTypes.any {
                val value = (it as LiteralValue.NumberLiteral).value
                value % 1.0 != 0.0
            }
            return if (hasFraction) {
                KotlinTypeFactory.double()
            } else {
                KotlinTypeFactory.int()
            }
        }

        // 混合类型，尝试转换为 Union.U2/U3（并对同类去重）
        val typeCount = union.types.size
        if (typeCount in 2..3) {
            val converted = union.types.mapNotNull { convert(it).getOrNull() }
            val distinctParams = converted.distinctBy { it.toTypeString() }
            if (distinctParams.size in 2..3) {
                return createUnionType(distinctParams.toTypedArray())
            }
        }

        // 超过 3 个类型，转换为 Any
        return KotlinTypeFactory.any()
    }

    /**
     * 检测是否仅包含（字符串字面量）与（布尔关键字或布尔字面量）
     * 例如：false | "inline" | "commonjs" 或 boolean | "inline" | "commonjs"
     * 命中后应折叠为 Union.U2<Boolean, String>
     */
    private fun isOnlyStringLiteralsAndBoolean(union: TypeScriptType.Union): Boolean {
        var hasStringLiteral = false
        var hasBoolean = false
        for (t in union.types) {
            when (t) {
                is TypeScriptType.Literal -> {
                    when (t.value) {
                        is LiteralValue.StringLiteral -> hasStringLiteral = true
                        is LiteralValue.BooleanLiteral -> hasBoolean = true
                        else -> return false
                    }
                }
                is TypeScriptType.Keyword -> {
                    if (t.kind == KeywordKind.BOOLEAN) {
                        hasBoolean = true
                    } else {
                        return false
                    }
                }
                else -> return false
            }
        }
        // 需要同时存在字符串字面量与布尔（关键字/字面量）
        return hasStringLiteral && hasBoolean
    }

    /**
     * 转换接口联合类型
     */
    private fun convertInterfaceUnion(union: TypeScriptType.Union): KotlinType {
        val typeCount = union.types.size
        Logger.debug("  转换接口联合类型: $typeCount 个类型", 8)

        // 如果提供了 InheritanceAnalyzer，尝试查找公共父接口
        if (inheritanceAnalyzer != null) {
            // 提取所有类型引用名称，并尝试展开类型别名
            val expandedTypes = mutableListOf<String>()

            for (tsType in union.types) {
                when (tsType) {
                    is TypeScriptType.Reference -> {
                        val typeName = tsType.name

                        // 尝试展开类型别名（如 Expression、Pattern）
                        val expanded = inheritanceAnalyzer.expandTypeAlias(typeName)

                        if (expanded != null) {
                            // 类型别名展开成功，添加所有展开的类型
                            expandedTypes.addAll(expanded)
                            Logger.debug("  展开类型别名 $typeName -> ${expanded.size} 个类型", 8)
                        } else {
                            // 不是类型别名或无法展开，直接使用类型名称
                            expandedTypes.add(typeName)
                        }
                    }
                    else -> {
                        // 非引用类型不支持展开
                        Logger.debug("  跳过非引用类型: ${tsType.javaClass.simpleName}", 8)
                    }
                }
            }

            // 如果有展开的类型，在所有展开的类型中查找公共父接口
            if (expandedTypes.isNotEmpty()) {
                val distinctTypes = expandedTypes.distinct()
                Logger.debug("  展开后的类型总数: ${distinctTypes.size} (去重后)", 8)

                val commonAncestor = inheritanceAnalyzer.findLowestCommonAncestor(distinctTypes)

                if (commonAncestor != null) {
                    // 找到公共父接口，直接返回该接口类型
                    val mappedName = CodeGenerationRules.mapTypeName(commonAncestor)
                    Logger.debug("  找到公共父接口: $commonAncestor -> $mappedName，替换 Union.U$typeCount", 8)
                    return KotlinTypeFactory.simple(mappedName)
                }
            }

            // 如果展开失败或没有找到公共父接口，回退到直接查找原始类型的公共父接口
            val typeRefs = union.types.mapNotNull { tsType ->
                when (tsType) {
                    is TypeScriptType.Reference -> tsType.name
                    else -> null
                }
            }

            if (typeRefs.size == typeCount && typeRefs.isNotEmpty()) {
                val commonAncestor = inheritanceAnalyzer.findLowestCommonAncestor(typeRefs)

                if (commonAncestor != null) {
                    // 找到公共父接口，直接返回该接口类型
                    val mappedName = CodeGenerationRules.mapTypeName(commonAncestor)
                    Logger.debug("  找到公共父接口: $commonAncestor -> $mappedName，替换 Union.U$typeCount", 8)
                    return KotlinTypeFactory.simple(mappedName)
                }
            }
        }

        // 如果没有找到公共父接口，回退到原来的逻辑（生成 Union.U2/U3）
        // 尝试转换所有类型并去重
        val typeParams = union.types.mapNotNull { convert(it).getOrNull() }
            .distinctBy { it.toTypeString() }

        // 如果成功转换了所有类型，生成 Union.U2/U3
        if (typeParams.size == typeCount && typeCount in 2..3) {
            return createUnionType(typeParams.toTypedArray())
        }

        // 如果转换失败，返回 Any
        Logger.debug("  接口联合类型转换失败，返回 Any", 8)
        return KotlinTypeFactory.any()
    }

    /**
     * 转换混合类型联合（包含字面量和对象类型）
     */
    private fun convertMixedUnion(union: TypeScriptType.Union): KotlinType {
        val typeCount = union.types.size
        Logger.debug("  转换混合联合类型: $typeCount 个类型", 8)

        // 尝试转换所有类型并去重
        val typeParams = union.types.mapNotNull { convert(it).getOrNull() }
            .distinctBy { it.toTypeString() }

        // 去重后如果在 2..3 范围内，生成 Union.U2/U3
        // 注意：混合联合中可能存在多个同类字面量（如两个字符串字面量），去重后数量会减少
        if (typeParams.size in 2..3) {
            return createUnionType(typeParams.toTypedArray())
        }

        // 如果转换失败，返回 Any
        Logger.debug("  混合联合类型转换失败，返回 Any", 8)
        return KotlinTypeFactory.any()
    }

    /**
     * 检查是否为 Union.U2/U3 类型
     */
    private fun isUnionType(union: TypeScriptType.Union): Boolean {
        val typeCount = union.types.size
        return typeCount in 2..3 && union.types.all { it is TypeScriptType.Reference || it is TypeScriptType.Array }
    }

    /**
     * 转换 Union.U2/U3 类型
     */
    private fun convertUnionType(union: TypeScriptType.Union): KotlinType {
        val typeParams = union.types.mapNotNull { convert(it).getOrNull() }
            .distinctBy { it.toTypeString() }
        if (typeParams.size in 2..3) {
            return createUnionType(typeParams.toTypedArray())
        }
        return KotlinTypeFactory.any()
    }
}
