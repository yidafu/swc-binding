package dev.yidafu.swc.generator.adt.kotlin

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

/**
 * Kotlin 类型 ADT（代数数据类型）
 */
sealed class KotlinType {
    data class Simple(val name: String) : KotlinType()
    data class Generic(
        val name: String, 
        val params: List<KotlinType>
    ) : KotlinType()
    data class Nullable(val innerType: KotlinType) : KotlinType()
    data class Function(
        val params: List<KotlinType>,
        val returnType: KotlinType
    ) : KotlinType()
    data class Union(val types: List<KotlinType>) : KotlinType()
    data class Booleanable(val innerType: KotlinType) : KotlinType()
    object Any : KotlinType()
    object Unit : KotlinType()
    object Nothing : KotlinType()
    object StringType : KotlinType()
    object Int : KotlinType()
    object Boolean : KotlinType()
    object Long : KotlinType()
    object Double : KotlinType()
    object Float : KotlinType()
    object Char : KotlinType()
    object Byte : KotlinType()
    object Short : KotlinType()
    
    /**
     * 转换为 KotlinPoet 的 TypeName
     */
    fun toTypeName(): TypeName = when (this) {
        is Simple -> ClassName("", name)
        is Generic -> {
            val baseClassName = when (name) {
                "Array" -> ClassName("kotlin", "Array")
                "List" -> ClassName("kotlin.collections", "List")
                "MutableList" -> ClassName("kotlin.collections", "MutableList")
                "Set" -> ClassName("kotlin.collections", "Set")
                "MutableSet" -> ClassName("kotlin.collections", "MutableSet")
                "Map" -> ClassName("kotlin.collections", "Map")
                "MutableMap" -> ClassName("kotlin.collections", "MutableMap")
                else -> ClassName("", name)
            }
            baseClassName.parameterizedBy(params.map { it.toTypeName() })
        }
        is Nullable -> innerType.toTypeName().copy(nullable = true)
        is Function -> {
            val paramTypes = params.map { it.toTypeName() }
            val returnTypeName = returnType.toTypeName()
            LambdaTypeName.get(parameters = paramTypes.toTypedArray(), returnType = returnTypeName)
        }
        is Union -> convertUnionType(this)
        is Booleanable -> {
            // Booleanable<T> 转换为 T? 类型
            innerType.toTypeName().copy(nullable = true)
        }
        is Any -> ANY
        is Unit -> UNIT
        is Nothing -> NOTHING
        is StringType -> STRING
        is Int -> INT
        is Boolean -> BOOLEAN
        is Long -> LONG
        is Double -> DOUBLE
        is Float -> FLOAT
        is Char -> CHAR
        is Byte -> BYTE
        is Short -> SHORT
    }
    
    /**
     * 转换为类型字符串（向后兼容）
     */
    fun toTypeString(): String = when (this) {
        is Simple -> name
        is Generic -> buildString {
            append(name)
            append('<')
            append(params.joinToString(", ") { it.toTypeString() })
            append('>')
        }
        is Nullable -> "${innerType.toTypeString()}?"
        is Function -> buildString {
            append('(')
            append(params.joinToString(", ") { it.toTypeString() })
            append(") -> ")
            append(returnType.toTypeString())
        }
        is Union -> when (types.size) {
            2 -> buildString {
                append("Union.U2<")
                append(types.joinToString(", ") { it.toTypeString() })
                append('>')
            }
            3 -> buildString {
                append("Union.U3<")
                append(types.joinToString(", ") { it.toTypeString() })
                append('>')
            }
            4 -> buildString {
                append("Union.U4<")
                append(types.joinToString(", ") { it.toTypeString() })
                append('>')
            }
            else -> "Any"
        }
        is Booleanable -> buildString {
            append("Booleanable")
            append(innerType.toTypeString())
        }
        is Any -> "Any"
        is Unit -> "Unit"
        is Nothing -> "Nothing"
        is StringType -> "String"
        is Int -> "Int"
        is Boolean -> "Boolean"
        is Long -> "Long"
        is Double -> "Double"
        is Float -> "Float"
        is Char -> "Char"
        is Byte -> "Byte"
        is Short -> "Short"
    }
    
    /**
     * 转换联合类型为 TypeName
     */
    private fun convertUnionType(union: Union): TypeName {
        return when (union.types.size) {
            2 -> {
                val className = ClassName("dev.yidafu.swc", "Union.U2")
                val typeParams = union.types.map { it.toTypeName() }
                className.parameterizedBy(typeParams)
            }
            3 -> {
                val className = ClassName("dev.yidafu.swc", "Union.U3")
                val typeParams = union.types.map { it.toTypeName() }
                className.parameterizedBy(typeParams)
            }
            4 -> {
                val className = ClassName("dev.yidafu.swc", "Union.U4")
                val typeParams = union.types.map { it.toTypeName() }
                className.parameterizedBy(typeParams)
            }
            else -> ANY
        }
    }
}
