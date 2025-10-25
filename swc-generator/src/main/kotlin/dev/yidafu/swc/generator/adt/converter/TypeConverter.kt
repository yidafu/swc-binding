package dev.yidafu.swc.generator.adt.converter

import dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.adt.kotlin.KotlinType
import dev.yidafu.swc.generator.adt.kotlin.KotlinTypeFactory
import dev.yidafu.swc.generator.adt.result.ErrorCode
import dev.yidafu.swc.generator.adt.result.GeneratorResult
import dev.yidafu.swc.generator.adt.result.GeneratorResultFactory
import dev.yidafu.swc.generator.adt.typescript.KeywordKind
import dev.yidafu.swc.generator.adt.typescript.LiteralValue
import dev.yidafu.swc.generator.adt.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.adt.typescript.TypeScriptType
import dev.yidafu.swc.generator.adt.typescript.getTypeName
import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.config.GlobalConfig
import dev.yidafu.swc.generator.config.HardcodedRules
import dev.yidafu.swc.generator.config.SwcGeneratorConfig

/**
 * TypeScript 到 Kotlin 类型转换器
 * 
 * 在 ADT 转换过程中自动处理所有类型转换：
 * - 基础类型（string, number, boolean 等）
 * - 复杂类型（数组、联合、交叉等）
 * - 特殊类型（模板字面量、函数等）
 */
class TypeConverter(private val config: SwcGeneratorConfig) {

    /**
     * 将 TypeScript 类型转换为 Kotlin 类型
     */
    fun convertToKotlinType(tsType: TypeScriptType): GeneratorResult<KotlinType> {
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
            GeneratorResultFactory.failure(
                code = ErrorCode.TYPE_RESOLUTION_ERROR,
                message = "Failed to convert type: $tsType",
                cause = e,
                context = mapOf("tsType" to tsType.getTypeName())
            )
        }
    }

    /**
     * 转换关键字类型
     * 直接在 ADT 转换过程中处理类型映射
     */
    private fun convertKeyword(kind: KeywordKind): KotlinType {
        return when (kind) {
            KeywordKind.STRING -> KotlinTypeFactory.string()
            KeywordKind.NUMBER -> KotlinTypeFactory.int()
            KeywordKind.BOOLEAN -> KotlinTypeFactory.boolean()
            KeywordKind.UNDEFINED -> KotlinTypeFactory.nothing()
            KeywordKind.NULL -> KotlinTypeFactory.nothing()
            KeywordKind.BIGINT -> KotlinTypeFactory.long()
            KeywordKind.VOID -> KotlinTypeFactory.unit()
            KeywordKind.ANY -> KotlinTypeFactory.any()
            KeywordKind.UNKNOWN -> KotlinTypeFactory.any()
            KeywordKind.NEVER -> KotlinTypeFactory.nothing()
            KeywordKind.SYMBOL -> KotlinTypeFactory.string() // Symbol 转换为 String
        }
    }

    /**
     * 转换类型引用
     */
    private fun convertReference(ref: TypeScriptType.Reference): KotlinType {
        // 处理泛型参数
        if (ref.typeParams.isNotEmpty()) {
            val params = ref.typeParams.mapNotNull {
                convertToKotlinType(it).getOrNull()
            }
            return KotlinTypeFactory.generic(ref.name, *params.toTypedArray())
        }

        // 检查特殊属性类型覆盖
        val propertyOverride = CodeGenerationRules.getPropertyTypeOverride(ref.name)
        if (propertyOverride != null) {
            return propertyOverride
        }

        return KotlinTypeFactory.simple(ref.name)
    }

    /**
     * 转换联合类型
     */
    private fun convertUnion(union: TypeScriptType.Union): KotlinType {
        val kotlinTypes = union.types.mapNotNull {
            convertToKotlinType(it).getOrNull()
        }

        // 过滤 null
        val nonNullTypes = kotlinTypes.filter {
            it !is KotlinType.Nothing
        }

        return when {
            nonNullTypes.isEmpty() -> KotlinTypeFactory.nothing()
            nonNullTypes.size == 1 -> nonNullTypes.first()
            nonNullTypes.size in 2..4 -> {
                // 检查是否是 Booleanable 类型
                val booleanType = nonNullTypes.find { it is KotlinType.Boolean }
                if (booleanType != null && nonNullTypes.size == 2) {
                    val otherType = nonNullTypes.find { it !is KotlinType.Boolean } ?: KotlinTypeFactory.any()
                    return KotlinTypeFactory.booleanable(otherType)
                }

                // 转换为 Union 类型
                KotlinTypeFactory.union(*nonNullTypes.toTypedArray())
            }
            else -> KotlinTypeFactory.any()
        }
    }

    /**
     * 转换交叉类型
     */
    private fun convertIntersection(intersection: TypeScriptType.Intersection): KotlinType {
        // 交叉类型通常取第一个类型
        val firstType = intersection.types.firstOrNull()
        return if (firstType != null) {
            convertToKotlinType(firstType).getOrDefault(KotlinTypeFactory.any())
        } else {
            KotlinTypeFactory.any()
        }
    }

    /**
     * 转换数组类型
     */
    private fun convertArray(array: TypeScriptType.Array): KotlinType {
        val elementType = convertToKotlinType(array.elementType)
            .getOrDefault(KotlinTypeFactory.any())
        return KotlinTypeFactory.generic("Array", elementType)
    }

    /**
     * 转换元组类型
     */
    private fun convertTuple(tuple: TypeScriptType.Tuple): KotlinType {
        val types = tuple.types.mapNotNull {
            convertToKotlinType(it).getOrNull()
        }

        return when (types.size) {
            2 -> KotlinTypeFactory.union(types[0], types[1])
            else -> KotlinTypeFactory.any()
        }
    }

    /**
     * 转换字面量类型
     */
    private fun convertLiteral(literal: TypeScriptType.Literal): KotlinType {
        return when (val value = literal.value) {
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
        // 对于类型字面量，通常需要创建新的类型
        // 这里返回一个占位符，实际处理在其他地方
        return KotlinTypeFactory.simple("TypeLiteral")
    }

    /**
     * 转换函数类型
     */
    private fun convertFunction(function: TypeScriptType.Function): KotlinType {
        val paramTypes = function.params.mapNotNull {
            convertToKotlinType(it.type).getOrNull()
        }
        val returnType = convertToKotlinType(function.returnType)
            .getOrDefault(KotlinTypeFactory.unit())

        return KotlinType.Function(paramTypes, returnType)
    }

    /**
     * 转换索引签名
     */
    private fun convertIndexSignature(indexSig: TypeScriptType.IndexSignature): KotlinType {
        val keyType = convertToKotlinType(indexSig.keyType)
            .getOrDefault(KotlinTypeFactory.string())
        val valueType = convertToKotlinType(indexSig.valueType)
            .getOrDefault(KotlinTypeFactory.any())

        return KotlinTypeFactory.generic("Map", keyType, valueType)
    }

    /**
     * 转换接口声明为 Kotlin 类声明
     */
    fun convertInterfaceDeclaration(ts: TypeScriptDeclaration.InterfaceDeclaration): GeneratorResult<KotlinDeclaration.ClassDecl> {
        return DeclarationConverter.convertInterfaceDeclaration(ts, config)
    }

    /**
     * 转换类型别名声明为 Kotlin 类型别名声明
     */
    fun convertTypeAliasDeclaration(ts: TypeScriptDeclaration.TypeAliasDeclaration): GeneratorResult<KotlinDeclaration.TypeAliasDecl> {
        return DeclarationConverter.convertTypeAliasDeclaration(ts, config)
    }

    companion object {
        /**
         * 便捷的转换方法
         */
        fun convert(tsType: TypeScriptType): GeneratorResult<KotlinType> {
            val converter = TypeConverter(GlobalConfig.config)
            return converter.convertToKotlinType(tsType)
        }

        /**
         * 便捷的接口声明转换方法
         */
        fun convertInterfaceDeclaration(ts: TypeScriptDeclaration.InterfaceDeclaration): GeneratorResult<KotlinDeclaration.ClassDecl> {
            val converter = TypeConverter(GlobalConfig.config)
            return converter.convertInterfaceDeclaration(ts)
        }

        /**
         * 便捷的类型别名声明转换方法
         */
        fun convertTypeAliasDeclaration(ts: TypeScriptDeclaration.TypeAliasDeclaration): GeneratorResult<KotlinDeclaration.TypeAliasDecl> {
            val converter = TypeConverter(GlobalConfig.config)
            return converter.convertTypeAliasDeclaration(ts)
        }
    }
}
