package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.*
import dev.yidafu.swc.generator.core.model.KotlinClass
import dev.yidafu.swc.generator.core.model.KotlinProperty
import dev.yidafu.swc.generator.config.Constants
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.codegen.poet.PoetConstants.Serialization
import dev.yidafu.swc.generator.codegen.poet.PoetConstants.SwcTypes

/**
 * KotlinPoet 代码生成器
 */
object KotlinPoetGenerator {
    
    /**
     * 从 KotlinProperty 创建 PropertySpec（增强版）
     */
    fun createPropertySpec(prop: KotlinProperty): PropertySpec? {
        val propName = Constants.kotlinKeywordMap[prop.name] ?: prop.name
        
        // 验证属性类型
        val actualTypeStr = prop.getActualType()
        if (!isValidPropertyType(actualTypeStr)) {
            Logger.warn("跳过属性 $propName: 类型无效 '$actualTypeStr'")
            return null
        }
        
        // 解析类型
        val actualType = try {
            actualTypeStr.parseAsTypeName().copy(nullable = true)
        } catch (e: Exception) {
            Logger.warn("属性 $propName 类型解析失败: ${e.message}")
            return null
        }
        
        return try {
            PropertySpec.builder(propName, actualType)
                .apply { addPropertyModifiers(prop, actualTypeStr) }
                .apply { addPropertyAnnotations(prop) }
                .apply { 
                    // 处理默认值
                    val initValue = getValidDefaultValue(prop, actualTypeStr)
                    if (prop.modifier == "const val") {
                        // const 属性必须有字面量初始值
                        initializer("%L", initValue)
                    } else {
                        initializer(initValue)
                    }
                }
                .apply { if (prop.comment.isNotEmpty()) addKdoc(prop.comment) }
                .build()
        } catch (e: Exception) {
            Logger.warn("创建属性 $propName 失败: ${e.message}")
            null
        }
    }
    
    /**
     * 验证属性类型是否有效
     */
    private fun isValidPropertyType(typeStr: String): Boolean {
        if (typeStr.isBlank()) return false
        if (typeStr.contains("/*") && !typeStr.contains("*/")) return false
        return true
    }
    
    /**
     * 获取有效的默认值
     */
    private fun getValidDefaultValue(prop: KotlinProperty, actualTypeStr: String): String {
        val defaultValue = prop.defaultValue.ifEmpty { "null" }
        
        // 对于 const val，确保是合法的基本类型字面量
        if (prop.modifier == "const val") {
            // const 只能用于基本类型
            val cleanType = actualTypeStr.substringBefore("<").replace(Regex("""/\*.*?\*/"""), "").trim()
            if (cleanType !in listOf("String", "Int", "Long", "Boolean", "Double", "Float")) {
                Logger.warn("const val 只能用于基本类型，属性 ${prop.name} 类型为 $cleanType")
                // 降级为普通 val
                return "null"
            }
            
            // 确保有有效的默认值
            if (defaultValue == "null" || defaultValue.isBlank()) {
                return when (cleanType) {
                    "String" -> "\"\""
                    "Int" -> "0"
                    "Long" -> "0L"
                    "Boolean" -> "false"
                    "Double" -> "0.0"
                    "Float" -> "0.0f"
                    else -> "null"
                }
            }
        }
        
        return defaultValue
    }
    
    /**
     * 添加属性修饰符
     */
    private fun PropertySpec.Builder.addPropertyModifiers(prop: KotlinProperty, actualTypeStr: String) {
        // 检查 const 限制
        val canBeConst = if (prop.modifier == "const val") {
            val cleanType = actualTypeStr.substringBefore("<").replace(Regex("""/\*.*?\*/"""), "").trim()
            cleanType in listOf("String", "Int", "Long", "Boolean", "Double", "Float")
        } else {
            false
        }
        
        when (prop.modifier) {
            "const val" -> {
                if (canBeConst) {
                    // const 属性必须是顶层或 object 成员，且类型必须是基本类型
                    addModifiers(KModifier.CONST)
                    mutable(false)  // const val 是不可变的
                } else {
                    // 降级为普通 val
                    Logger.verbose("属性 ${prop.name} 类型不支持 const，降级为 val", 8)
                    mutable(false)
                }
            }
            "var" -> mutable(true)
            "val" -> mutable(false)
            else -> mutable(true)
        }
        
        if (prop.isOverride) {
            addModifiers(KModifier.OVERRIDE)
        }
    }
    
    /**
     * 添加属性注解
     */
    private fun PropertySpec.Builder.addPropertyAnnotations(prop: KotlinProperty) {
        val annotation = prop.getAnnotation()
        
        // @SerialName
        annotation.extractAnnotationParam("@SerialName")?.let { name ->
            // 移除引号并确保是纯字符串值
            val cleanName = name.removeSurrounding("\"").replace("\\", "")
            addAnnotation(
                AnnotationSpec.builder(Serialization.SERIAL_NAME)
                    .addMember("%S", cleanName)
                    .build()
            )
        }
        
        // @Serializable(XxxSerializer::class)
        annotation.extractAnnotationParam("@Serializable")?.let { param ->
            if (param.contains("Serializer")) {
                addAnnotation(
                    AnnotationSpec.builder(Serialization.SERIALIZABLE)
                        .addMember("%L", param)
                        .build()
                )
            }
        }
    }
    
    /**
     * 从注解字符串提取参数
     */
    private fun String.extractAnnotationParam(annotationName: String): String? {
        if (!this.contains(annotationName)) return null
        
        val pattern = when {
            annotationName == "@SerialName" -> """@SerialName\((.+?)\)"""
            annotationName == "@Serializable" -> """@Serializable\((.+?)\)"""
            else -> return null
        }
        
        return Regex(pattern).find(this)?.groupValues?.get(1)
    }
    
    /**
     * 从 KotlinClass 创建 TypeSpec
     */
    fun createTypeSpec(klass: KotlinClass): TypeSpec {
        // 过滤掉无法创建的属性
        val validProperties = klass.properties.mapNotNull { prop ->
            createPropertySpec(prop)
        }
        
        if (validProperties.size < klass.properties.size) {
            val skipped = klass.properties.size - validProperties.size
            Logger.verbose("类 ${klass.klassName}: 跳过了 $skipped 个无效属性", 8)
        }
        
        return createTypeBuilder(klass)
            .apply { addClassModifiers(klass) }
            .apply { addAnnotations(klass.annotations) }
            .apply { addParents(klass.parents, klass.modifier.contains("interface")) }
            .apply { addProperties(validProperties) }
            .apply { if (klass.headerComment.isNotEmpty()) addKdoc(klass.headerComment.cleanKdoc()) }
            .build()
    }
    
    /**
     * 创建 TypeSpec.Builder
     */
    private fun createTypeBuilder(klass: KotlinClass): TypeSpec.Builder {
        return when {
            klass.modifier.contains("interface") -> TypeSpec.interfaceBuilder(klass.klassName)
            klass.modifier.contains("object") -> TypeSpec.objectBuilder(klass.klassName)
            else -> TypeSpec.classBuilder(klass.klassName)
        }
    }
    
    /**
     * 添加类修饰符
     */
    private fun TypeSpec.Builder.addClassModifiers(klass: KotlinClass) {
        when {
            klass.modifier.contains("sealed") -> addModifiers(KModifier.SEALED)
            klass.modifier.contains("open") -> addModifiers(KModifier.OPEN)
        }
    }
    
    /**
     * 批量添加注解
     */
    private fun TypeSpec.Builder.addAnnotations(annotations: List<String>) {
        annotations.mapNotNull { parseAnnotation(it) }.forEach { addAnnotation(it) }
    }
    
    
    /**
     * 解析注解字符串为 AnnotationSpec
     */
    private fun parseAnnotation(annotationStr: String): AnnotationSpec? {
        return when (val cleaned = annotationStr.trim()) {
            "@SwcDslMarker" -> AnnotationSpec.builder(SwcTypes.SWC_DSL_MARKER).build()
            "@Serializable" -> AnnotationSpec.builder(Serialization.SERIALIZABLE).build()
            
            else -> when {
                cleaned.startsWith("@Serializable(") -> {
                    val param = cleaned.substringAfter("(").substringBefore(")")
                    AnnotationSpec.builder(Serialization.SERIALIZABLE)
                        .addMember("%L", param)
                        .build()
                }
                cleaned.startsWith("@SerialName") -> {
                    val param = cleaned.substringAfter("(").substringBefore(")")
                    AnnotationSpec.builder(Serialization.SERIAL_NAME)
                        .addMember("%L", param)
                        .build()
                }
                cleaned.startsWith("@JsonClassDiscriminator") -> {
                    val discriminator = cleaned.substringAfter("(\"").substringBefore("\")")
                    AnnotationSpec.builder(Serialization.Json.JSON_CLASS_DISCRIMINATOR)
                        .addMember("%S", discriminator)
                        .build()
                }
                cleaned.startsWith("@OptIn") -> {
                    AnnotationSpec.builder(PoetConstants.Kotlin.OPT_IN)
                        .addMember("%T::class", Serialization.EXPERIMENTAL)
                        .build()
                }
                else -> {
                    Logger.verbose("跳过注解: $cleaned", 8)
                    null
                }
            }
        }
    }
}

