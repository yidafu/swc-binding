package dev.yidafu.swc.generator.core.model

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
                    prop.type = "Double"
                }
            }
        }
    }
    
    /**
     * 转换为字符串
     */
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
            appendLine(if (withValue) prop.toStringWithValue() else prop.toString())
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

