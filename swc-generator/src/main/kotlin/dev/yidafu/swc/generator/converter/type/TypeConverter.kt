package dev.yidafu.swc.generator.converter.type

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
class TypeConverter(private val config: Configuration) {

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
        if (ref.typeParams.isNotEmpty()) {
            val params = ref.typeParams.mapNotNull {
                convert(it).getOrNull()
            }
            return KotlinTypeFactory.generic(ref.name, *params.toTypedArray())
        }

        // 检查特殊属性类型覆盖
        val propertyOverride = dev.yidafu.swc.generator.config.CodeGenerationRules.propertyTypeOverrides[ref.name]
        if (propertyOverride != null) {
            // 直接返回配置中指定的覆盖类型
            Logger.debug("使用类型覆盖: ${ref.name} -> ${propertyOverride.toTypeString()}", 6)
            return propertyOverride
        }

        return KotlinTypeFactory.simple(ref.name)
    }

    /**
     * 转换联合类型
     */
    private fun convertUnion(union: TypeScriptType.Union): KotlinType {
        Logger.debug("转换联合类型: ${union.types.size} 个类型", 6)
        
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

        // 检查是否为 Union.U2/U3/U4 类型
        if (isUnionType(union)) {
            Logger.debug("  Union.U2/U3/U4 类型", 6)
            return convertUnionType(union)
        }

        // 检查是否为混合类型联合（包含字面量和对象类型）
        if (isMixedUnion(union)) {
            Logger.debug("  混合类型联合", 6)
            return convertMixedUnion(union)
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
        val elementTypes = tuple.types.mapNotNull { 
            convert(it).getOrNull() 
        }
        // Convert 2-element tuples to Union.U2 for better type safety
        return when (elementTypes.size) {
            2 -> KotlinTypeFactory.generic("Union.U2", *elementTypes.toTypedArray())
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
        val literalTypes = union.types.mapNotNull { 
            if (it is TypeScriptType.Literal) it.value else null 
        }
        
        // 如果都是字符串字面量，转换为 Union.U2/U3/U4 或 Any
        if (literalTypes.all { it is LiteralValue.StringLiteral }) {
            val typeCount = union.types.size
            return when (typeCount) {
                2 -> KotlinTypeFactory.generic("Union.U2", KotlinTypeFactory.string(), KotlinTypeFactory.string())
                3 -> KotlinTypeFactory.generic("Union.U3", KotlinTypeFactory.string(), KotlinTypeFactory.string(), KotlinTypeFactory.string())
                4 -> KotlinTypeFactory.generic("Union.U4", KotlinTypeFactory.string(), KotlinTypeFactory.string(), KotlinTypeFactory.string(), KotlinTypeFactory.string())
                else -> KotlinTypeFactory.any() // 超过 4 个类型转换为 Any
            }
        }
        
        // 如果都是布尔字面量，转换为 Union.U2/U3/U4
        if (literalTypes.all { it is LiteralValue.BooleanLiteral }) {
            val typeCount = union.types.size
            return when (typeCount) {
                2 -> KotlinTypeFactory.generic("Union.U2", KotlinTypeFactory.boolean(), KotlinTypeFactory.boolean())
                3 -> KotlinTypeFactory.generic("Union.U3", KotlinTypeFactory.boolean(), KotlinTypeFactory.boolean(), KotlinTypeFactory.boolean())
                4 -> KotlinTypeFactory.generic("Union.U4", KotlinTypeFactory.boolean(), KotlinTypeFactory.boolean(), KotlinTypeFactory.boolean(), KotlinTypeFactory.boolean())
                else -> KotlinTypeFactory.boolean()
            }
        }
        
        // 混合类型，尝试转换为 Union.U2/U3/U4
        val typeCount = union.types.size
        if (typeCount in 2..4) {
            val typeParams = union.types.mapNotNull { 
                convert(it).getOrNull() 
            }
            return when (typeCount) {
                2 -> KotlinTypeFactory.generic("Union.U2", *typeParams.toTypedArray())
                3 -> KotlinTypeFactory.generic("Union.U3", *typeParams.toTypedArray())
                4 -> KotlinTypeFactory.generic("Union.U4", *typeParams.toTypedArray())
                else -> KotlinTypeFactory.any()
            }
        }
        
        // 超过 4 个类型，转换为 Any
        return KotlinTypeFactory.any()
    }

    /**
     * 转换接口联合类型
     */
    private fun convertInterfaceUnion(union: TypeScriptType.Union): KotlinType {
        val typeCount = union.types.size
        Logger.debug("  转换接口联合类型: $typeCount 个类型", 8)
        
        // 尝试转换所有类型
        val typeParams = union.types.mapNotNull { 
            convert(it).getOrNull() 
        }
        
        // 如果成功转换了所有类型，生成 Union.U2/U3/U4
        if (typeParams.size == typeCount) {
            return when (typeCount) {
                2 -> KotlinTypeFactory.generic("Union.U2", *typeParams.toTypedArray())
                3 -> KotlinTypeFactory.generic("Union.U3", *typeParams.toTypedArray())
                4 -> KotlinTypeFactory.generic("Union.U4", *typeParams.toTypedArray())
                else -> KotlinTypeFactory.any()
            }
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
        
        // 尝试转换所有类型
        val typeParams = union.types.mapNotNull { 
            convert(it).getOrNull() 
        }
        
        // 如果成功转换了所有类型，生成 Union.U2/U3/U4
        if (typeParams.size == typeCount) {
            return when (typeCount) {
                2 -> KotlinTypeFactory.generic("Union.U2", *typeParams.toTypedArray())
                3 -> KotlinTypeFactory.generic("Union.U3", *typeParams.toTypedArray())
                4 -> KotlinTypeFactory.generic("Union.U4", *typeParams.toTypedArray())
                else -> KotlinTypeFactory.any()
            }
        }
        
        // 如果转换失败，返回 Any
        Logger.debug("  混合联合类型转换失败，返回 Any", 8)
        return KotlinTypeFactory.any()
    }

    /**
     * 检查是否为 Union.U2/U3/U4 类型
     */
    private fun isUnionType(union: TypeScriptType.Union): Boolean {
        val typeCount = union.types.size
        return typeCount in 2..4 && union.types.all { 
            it is TypeScriptType.Reference || it is TypeScriptType.Array 
        }
    }

    /**
     * 转换 Union.U2/U3/U4 类型
     */
    private fun convertUnionType(union: TypeScriptType.Union): KotlinType {
        val typeCount = union.types.size
        val typeParams = union.types.mapNotNull { 
            convert(it).getOrNull() 
        }
        
        return when (typeCount) {
            2 -> KotlinTypeFactory.generic("Union.U2", *typeParams.toTypedArray())
            3 -> KotlinTypeFactory.generic("Union.U3", *typeParams.toTypedArray())
            4 -> KotlinTypeFactory.generic("Union.U4", *typeParams.toTypedArray())
            else -> KotlinTypeFactory.any()
        }
    }
}
