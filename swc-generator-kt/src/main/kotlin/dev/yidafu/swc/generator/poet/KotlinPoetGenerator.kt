package dev.yidafu.swc.generator.poet

import com.squareup.kotlinpoet.*
import dev.yidafu.swc.generator.model.KotlinClass
import dev.yidafu.swc.generator.model.KotlinProperty
import dev.yidafu.swc.generator.transform.Constants
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.poet.PoetConstants.Serialization
import dev.yidafu.swc.generator.poet.PoetConstants.SwcTypes

/**
 * KotlinPoet 代码生成器
 */
object KotlinPoetGenerator {
    
    /**
     * 从 KotlinProperty 创建 PropertySpec
     */
    fun createPropertySpec(prop: KotlinProperty): PropertySpec {
        val propName = Constants.kotlinKeywordMap[prop.name] ?: prop.name
        val actualType = prop.getActualType().parseAsTypeName().copy(nullable = true)
        
        return PropertySpec.builder(propName, actualType)
            .apply { addPropertyModifiers(prop) }
            .apply { addPropertyAnnotations(prop) }
            .initializer(prop.defaultValue.ifEmpty { "null" })
            .apply { if (prop.comment.isNotEmpty()) addKdoc(prop.comment) }
            .build()
    }
    
    /**
     * 添加属性修饰符
     */
    private fun PropertySpec.Builder.addPropertyModifiers(prop: KotlinProperty) {
        when (prop.modifier) {
            "const val" -> addModifiers(KModifier.CONST)
            "var" -> mutable(true)
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
            addAnnotation(
                AnnotationSpec.builder(Serialization.SERIAL_NAME)
                    .addMember("%S", name.removeSurrounding("\""))
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
        return createTypeBuilder(klass)
            .apply { addClassModifiers(klass) }
            .apply { addAnnotations(klass.annotations) }
            .apply { addParents(klass.parents, klass.modifier.contains("interface")) }
            .apply { addProperties(klass.properties.map { createPropertySpec(it) }) }
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
                        .addMember(param)
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

