package dev.yidafu.swc.generator.adt.kotlin

import com.squareup.kotlinpoet.*

/**
 * Kotlin 类型的扩展方法
 */
fun KotlinType.isPrimitive(): Boolean = when (this) {
    is KotlinType.Simple -> when (name) {
        "String", "Int", "Boolean", "Long", "Double", "Float", "Char", "Byte", "Short" -> true
        else -> false
    }
    is KotlinType.StringType, is KotlinType.Int, is KotlinType.Boolean, 
    is KotlinType.Long, is KotlinType.Double, is KotlinType.Float,
    is KotlinType.Char, is KotlinType.Byte, is KotlinType.Short -> true
    else -> false
}

fun KotlinType.isNullable(): Boolean = when (this) {
    is KotlinType.Nullable -> true
    else -> false
}

fun KotlinType.isGeneric(): Boolean = when (this) {
    is KotlinType.Generic -> true
    else -> false
}

fun KotlinType.getTypeName(): String = when (this) {
    is KotlinType.Simple -> name
    is KotlinType.Generic -> name
    is KotlinType.Nullable -> innerType.getTypeName()
    is KotlinType.Function -> "Function"
    is KotlinType.Union -> "Union"
    is KotlinType.Booleanable -> "Booleanable"
    is KotlinType.Any -> "Any"
    is KotlinType.Unit -> "Unit"
    is KotlinType.Nothing -> "Nothing"
    is KotlinType.StringType -> "String"
    is KotlinType.Int -> "Int"
    is KotlinType.Boolean -> "Boolean"
    is KotlinType.Long -> "Long"
    is KotlinType.Double -> "Double"
    is KotlinType.Float -> "Float"
    is KotlinType.Char -> "Char"
    is KotlinType.Byte -> "Byte"
    is KotlinType.Short -> "Short"
}

/**
 * 便捷的创建方法
 */
object KotlinTypeFactory {
    fun string() = KotlinType.StringType
    fun int() = KotlinType.Int
    fun boolean() = KotlinType.Boolean
    fun long() = KotlinType.Long
    fun double() = KotlinType.Double
    fun float() = KotlinType.Float
    fun char() = KotlinType.Char
    fun byte() = KotlinType.Byte
    fun short() = KotlinType.Short
    fun any() = KotlinType.Any
    fun unit() = KotlinType.Unit
    fun nothing() = KotlinType.Nothing
    
    fun simple(name: String) = KotlinType.Simple(name)
    fun generic(name: String, vararg params: KotlinType) = KotlinType.Generic(name, params.toList())
    fun nullable(type: KotlinType) = KotlinType.Nullable(type)
    fun booleanable(type: KotlinType) = KotlinType.Booleanable(type)
    fun union(vararg types: KotlinType) = KotlinType.Union(types.toList())
}

/**
 * 扩展方法：从字符串解析为 KotlinType
 */
fun String.parseToKotlinType(): KotlinType {
    val cleanType = this.trim().replace(Regex("""/\*.*?\*/"""), "").trim()
    
    return when {
        cleanType.startsWith("Array<") -> {
            val elementType = cleanType.substringAfter("Array<").substringBeforeLast(">")
            KotlinTypeFactory.generic("Array", elementType.parseToKotlinType())
        }
        cleanType.contains("<") -> {
            val baseName = cleanType.substringBefore("<")
            val typeParams = cleanType.extractGenericParams().map { it.parseToKotlinType() }
            dev.yidafu.swc.generator.util.Logger.debug("解析泛型类型: $cleanType -> $baseName, 参数: ${typeParams.map { it.toTypeString() }}", 10)
            KotlinTypeFactory.generic(baseName, *typeParams.toTypedArray())
        }
        cleanType.endsWith("?") -> {
            val innerType = cleanType.substringBefore("?")
            KotlinTypeFactory.nullable(innerType.parseToKotlinType())
        }
        else -> when (cleanType) {
            "String" -> KotlinTypeFactory.string()
            "Int" -> KotlinTypeFactory.int()
            "Boolean" -> KotlinTypeFactory.boolean()
            "Long" -> KotlinTypeFactory.long()
            "Double" -> KotlinTypeFactory.double()
            "Float" -> KotlinTypeFactory.float()
            "Char" -> KotlinTypeFactory.char()
            "Byte" -> KotlinTypeFactory.byte()
            "Short" -> KotlinTypeFactory.short()
            "Any" -> KotlinTypeFactory.any()
            "Unit" -> KotlinTypeFactory.unit()
            "Nothing" -> KotlinTypeFactory.nothing()
            else -> KotlinTypeFactory.simple(cleanType)
        }
    }
}

/**
 * 提取泛型参数（支持嵌套）
 */
fun String.extractGenericParams(): List<String> {
    val params = this.substringAfter("<").substringBeforeLast(">")
    if (params.isEmpty()) return emptyList()
    
    return params.extractNestedGenericParams()
}

/**
 * 提取嵌套泛型参数
 */
fun String.extractNestedGenericParams(): List<String> {
    if (isEmpty()) return emptyList()
    
    val result = mutableListOf<String>()
    var current = StringBuilder()
    var depth = 0
    
    for (char in this) {
        when (char) {
            '<' -> {
                depth++
                current.append(char)
            }
            '>' -> {
                depth--
                current.append(char)
            }
            ',' -> {
                if (depth == 0) {
                    result.add(current.toString().trim())
                    current = StringBuilder()
                } else {
                    current.append(char)
                }
            }
            else -> current.append(char)
        }
    }
    
    if (current.isNotEmpty()) {
        result.add(current.toString().trim())
    }
    
    return result
}
