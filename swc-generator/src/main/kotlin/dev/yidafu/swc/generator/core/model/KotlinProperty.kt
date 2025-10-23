package dev.yidafu.swc.generator.core.model

import dev.yidafu.swc.generator.adt.kotlin.*

/**
 * Kotlin 属性模型
 */
data class KotlinProperty(
    var modifier: String = "var",
    var name: String = "",
    var kotlinType: KotlinType = KotlinType.Any,
    var comment: String = "",
    var defaultValue: String = "",
    var isOverride: Boolean = false,
    var discriminator: String = "type"
) {
    /**
     * 判断是否为数组类型
     */
    fun isArray(): Boolean = when (val type = kotlinType) {
        is KotlinType.Generic -> type.name == "Array"
        else -> false
    }
    
    /**
     * 判断是否为联合类型
     */
    fun isUnion(): Boolean = kotlinType is KotlinType.Union
    
    /**
     * 判断是否为 Booleanable 类型
     */
    fun isBooleanable(): Boolean = kotlinType is KotlinType.Booleanable
    
    /**
     * 判断是否为基本类型
     */
    fun isPrimitive(): Boolean = kotlinType.isPrimitive()
    
    /**
     * 获取实际的 Kotlin 类型
     */
    fun getActualKotlinType(): KotlinType = kotlinType
    
    /**
     * 获取类型字符串（用于向后兼容）
     */
    fun getTypeString(): String = kotlinType.toTypeString()
    
    /**
     * 获取注解
     */
    fun getAnnotation(): String {
        val annotation = buildString {
            if (discriminator != "type") {
                append("@SerialName(\"$discriminator\")")
            }
        }
        return annotation
    }
    
    /**
     * 获取实际类型字符串（已废弃，建议使用 kotlinType.toTypeName()）
     */
    @Deprecated("使用 kotlinType.toTypeName() 替代", ReplaceWith("kotlinType.toTypeName()"))
    fun getActualType(): String = kotlinType.toTypeString()
    
    /**
     * 转换为 KotlinDeclaration.PropertyDecl
     */
    fun toDeclaration(): KotlinDeclaration.PropertyDecl {
        val propertyModifier = when (modifier) {
            "const val" -> PropertyModifier.ConstVal
            "val" -> PropertyModifier.Val
            "var" -> PropertyModifier.Var
            "lateinit var" -> PropertyModifier.LateinitVar
            else -> PropertyModifier.Var
        }
        
        val defaultValue = if (defaultValue.isNotEmpty()) {
            parseDefaultValue(defaultValue)
        } else null
        
        val annotations = parseAnnotations()
        
        return KotlinDeclaration.PropertyDecl(
            name = name,
            type = kotlinType,
            modifier = propertyModifier,
            defaultValue = defaultValue,
            annotations = annotations,
            kdoc = comment.takeIf { it.isNotEmpty() }
        )
    }
    
    /**
     * 解析默认值
     */
    private fun parseDefaultValue(valueStr: String): Expression? {
        val trimmed = valueStr.trim()
        return when {
            trimmed == "null" -> Expression.NullLiteral
            trimmed.startsWith("\"") && trimmed.endsWith("\"") -> {
                Expression.StringLiteral(trimmed.substring(1, trimmed.length - 1))
            }
            trimmed == "true" -> Expression.BooleanLiteral(true)
            trimmed == "false" -> Expression.BooleanLiteral(false)
            trimmed.matches(Regex("\\d+")) -> Expression.NumberLiteral(trimmed)
            trimmed.matches(Regex("\\d+\\.\\d+")) -> Expression.NumberLiteral(trimmed)
            trimmed.endsWith("L") -> Expression.NumberLiteral(trimmed)
            trimmed.endsWith("f") -> Expression.NumberLiteral(trimmed)
            else -> Expression.Literal(trimmed)
        }
    }
    
    /**
     * 解析注解
     */
    private fun parseAnnotations(): List<KotlinDeclaration.Annotation> {
        val annotationStr = getAnnotation()
        if (annotationStr.isEmpty()) return emptyList()
        
        return listOfNotNull(parseAnnotation(annotationStr))
    }
    
    /**
     * 解析单个注解
     */
    private fun parseAnnotation(annotationStr: String): KotlinDeclaration.Annotation? {
        val cleaned = annotationStr.trim()
        return when {
            cleaned.startsWith("@") -> {
                val name = cleaned.substring(1)
                val args = extractAnnotationArgs(cleaned)
                KotlinDeclaration.Annotation(name, args)
            }
            else -> null
        }
    }
    
    /**
     * 提取注解参数
     */
    private fun extractAnnotationArgs(annotationStr: String): List<Expression> {
        if (!annotationStr.contains("(")) return emptyList()
        
        val argsStr = annotationStr.substringAfter("(").substringBeforeLast(")")
        if (argsStr.isEmpty()) return emptyList()
        
        return argsStr.split(",").map { arg ->
            val trimmed = arg.trim()
            when {
                trimmed.startsWith("\"") && trimmed.endsWith("\"") -> {
                    Expression.StringLiteral(trimmed.substring(1, trimmed.length - 1))
                }
                trimmed == "true" -> Expression.BooleanLiteral(true)
                trimmed == "false" -> Expression.BooleanLiteral(false)
                trimmed.matches(Regex("\\d+")) -> Expression.NumberLiteral(trimmed)
                else -> Expression.Literal(trimmed)
            }
        }
    }
    
    /**
     * 克隆属性
     */
    fun clone(): KotlinProperty = copy(kotlinType = kotlinType)
}