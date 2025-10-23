package dev.yidafu.swc.generator.core.model

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.core.model.KotlinProperty
import dev.yidafu.swc.generator.core.relation.ExtendRelationship

/**
 * Kotlin 类/接口模型
 */
data class KotlinClass(
    var klassName: String = "",
    var headerComment: String = "",
    var annotations: MutableList<String> = mutableListOf(),
    var modifier: String = "",
    var parents: MutableList<String> = mutableListOf(),
    var properties: MutableList<KotlinProperty> = mutableListOf()
) {
    companion object {
        val SerialNameWhiteList = listOf(
            "OptionalChainingCall",
            "BindingIdentifier",
            "TemplateLiteral",
            "CallExpression",
            "Identifier"
        )
    }
    
    /**
     * 克隆类
     */
    fun clone(): KotlinClass = copy(
        annotations = annotations.toMutableList(),
        parents = parents.toMutableList(),
        properties = properties.map { it.clone() }.toMutableList()
    )
    
    /**
     * 特殊处理 NumericLiteral 的 value 类型
     */
    private fun overrideNumericLiteralValueType() {
        if (klassName.contains("NumericLiteral")) {
            properties.forEach { prop ->
                if (prop.name == "value") {
                    prop.kotlinType = KotlinType.Double
                }
            }
        }
    }
    
    /**
     * 转换为 KotlinDeclaration.ClassDecl
     */
    fun toDeclaration(): KotlinDeclaration.ClassDecl {
        val classModifier = when {
            modifier.contains("sealed interface") -> ClassModifier.SealedInterface
            modifier.contains("interface") -> ClassModifier.Interface
            modifier.contains("object") -> ClassModifier.Object
            modifier.contains("enum") -> ClassModifier.EnumClass
            modifier.contains("sealed") -> ClassModifier.SealedClass
            modifier.contains("abstract") -> ClassModifier.AbstractClass
            modifier.contains("open") -> ClassModifier.OpenClass
            modifier.contains("data") -> ClassModifier.DataClass
            else -> ClassModifier.FinalClass
        }
        
        val propertyDecls = properties.map { prop ->
            prop.toDeclaration()
        }
        
        val parentTypes = parents.map { parent ->
            parent.parseToKotlinType()
        }
        
        val annotations = this.annotations.map { annotationStr ->
            parseAnnotation(annotationStr)
        }.filterNotNull()
        
        return KotlinDeclaration.ClassDecl(
            name = klassName,
            modifier = classModifier,
            properties = propertyDecls,
            parents = parentTypes,
            annotations = annotations,
            kdoc = headerComment.takeIf { it.isNotEmpty() }
        )
    }
    
    /**
     * 解析注解字符串为 Annotation
     */
    private fun parseAnnotation(annotationStr: String): KotlinDeclaration.Annotation? {
        val cleaned = annotationStr.trim()
        return when {
            cleaned.startsWith("@") -> {
                val name = if (cleaned.contains("(")) {
                    cleaned.substring(1, cleaned.indexOf("("))
                } else {
                    cleaned.substring(1)
                }
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
        
        // 简单的参数解析，支持字符串字面量
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
     * 转换为字符串（已废弃，建议使用 toDeclaration()）
     */
    @Deprecated("使用 toDeclaration() 替代", ReplaceWith("toDeclaration()"))
    override fun toString(): String {
        overrideNumericLiteralValueType()
        return if (modifier.contains("interface")) toInterfaceString() else toClassString()
    }
    
    /**
     * 生成类/接口代码
     */
    private fun toInterfaceString(): String = buildCode(false)
    private fun toClassString(): String = buildCode(true)
    
    /**
     * 构建代码
     */
    private fun buildCode(withValue: Boolean): String = buildString {
        if (headerComment.isNotEmpty()) appendLine(headerComment)
        annotations.forEach { appendLine(it) }
        
        val parentStr = if (parents.isNotEmpty()) " : ${parents.joinToString(", ")}" else ""
        appendLine("$modifier $klassName$parentStr {")
        
        properties.forEach { prop ->
            appendLine(if (withValue) prop.toString() else prop.toString())
        }
        
        appendLine("}")
    }.trimEnd()
    
    /**
     * 转换为 interface
     */
    fun toInterface(): KotlinClass {
        val discriminator = getDiscriminator()
        return clone().apply {
            annotations = mutableListOf("@SwcDslMarker")
            properties = properties.map { it.clone().apply { 
                defaultValue = ""
                this.discriminator = discriminator
            }}.toMutableList()
        }
    }
    
    /**
     * 转换为 class
     */
    fun toClass(): KotlinClass {
        val discriminator = getDiscriminator()
        return clone().apply {
            properties = properties.map { it.clone().apply { 
                this.discriminator = discriminator
            }}.toMutableList()
        }
    }
    
    /**
     * 转换为实现类
     */
    fun toImplClass(): KotlinClass {
        val discriminator = getDiscriminator()
        val serialName = computeSerialName(discriminator)
        
        return clone().apply {
            klassName = "${klassName}Impl"
            modifier = "class"
            parents = mutableListOf(klassName)
            annotations = mutableListOf(
                "@OptIn(ExperimentalSerializationApi::class)",
                "@SwcDslMarker",
                "@Serializable",
                "@JsonClassDiscriminator(\"$discriminator\")",
                "@SerialName($serialName)"
            )
            properties = properties.map { it.clone().apply { 
                isOverride = true
                this.discriminator = discriminator
            }}.toMutableList()
        }
    }
    
    /**
     * 计算 @SerialName 的值
     */
    private fun computeSerialName(discriminator: String): String {
        return if (SerialNameWhiteList.contains(klassName)) {
            "\"$klassName\""
        } else {
            properties.find { it.name == discriminator }?.defaultValue ?: "\"$klassName\""
        }
    }
    
    /**
     * 获取判别器字段名
     */
    private fun getDiscriminator(): String {
        var discriminator = "type"
        val root = ExtendRelationship.getRoot(klassName)
        if (root != null && ClassDiscriminatorMap.containsKey(root)) {
            discriminator = ClassDiscriminatorMap[root] ?: "type"
        }
        return discriminator
    }
    
}

object ClassDiscriminatorMap {
    private val discriminatorMap = mutableMapOf<String, String>()
    
    fun setDiscriminator(className: String, discriminator: String) {
        discriminatorMap[className] = discriminator
    }
    
    fun containsKey(className: String): Boolean {
        return discriminatorMap.containsKey(className)
    }
    
    operator fun get(className: String): String? {
        return discriminatorMap[className]
    }
}

