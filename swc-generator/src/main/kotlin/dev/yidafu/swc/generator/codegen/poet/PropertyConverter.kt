package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import dev.yidafu.swc.generator.util.Logger

/**
 * 属性转换器
 * 负责将 KotlinDeclaration.PropertyDecl 转换为 KotlinPoet 的 PropertySpec
 */
object PropertyConverter {

    /**
     * 转换属性声明为 PropertySpec
     */
    fun convertProperty(
        prop: KotlinDeclaration.PropertyDecl,
        typeName: com.squareup.kotlinpoet.TypeName
    ): PropertySpec? {
        return try {
            val builder = PropertySpec.builder(prop.name, typeName)

            // 添加修饰符
            addPropertyModifiers(builder, prop.modifier)

            // 添加注解
            prop.annotations.forEach { annotation ->
                AnnotationConverter.convertAnnotation(annotation)?.let { builder.addAnnotation(it) }
            }

            // 检查是否有默认值
            val hasDefaultValue = prop.defaultValue != null
            val hasEncodeDefault = prop.annotations.any { it.name == "EncodeDefault" }

            // 添加默认值
            prop.defaultValue?.let { defaultValue ->
                val defaultValueStr = defaultValue.toCodeString()
                // 只有当默认值不为空时才添加 initializer
                if (defaultValueStr.isNotBlank()) {
                    builder.initializer(defaultValueStr)
                    // 如果有默认值但没有 @EncodeDefault 注解，自动添加
                    // 这样可以确保反序列化时，如果 JSON 中缺失字段，使用默认值
                    if (!hasEncodeDefault) {
                        builder.addAnnotation(
                            AnnotationSpec.builder(ClassName("kotlinx.serialization", "EncodeDefault")).build()
                        )
                    }
                }
            }

            // 添加文档
            prop.kdoc?.let { builder.addKdoc(it.cleanKdoc()) }

            builder.build()
        } catch (e: Exception) {
            Logger.warn("转换属性失败: ${prop.name}, ${e.message}")
            Logger.warn("错误详情: ${e.stackTraceToString()}")
            null
        }
    }

    /**
     * 添加属性修饰符
     */
    fun addPropertyModifiers(builder: PropertySpec.Builder, modifier: PropertyModifier) {
        // 所有属性都应该是 public
        builder.addModifiers(KModifier.PUBLIC)

        when (modifier) {
            is PropertyModifier.ConstVal -> {
                builder.addModifiers(KModifier.CONST)
                builder.mutable(false)
            }
            is PropertyModifier.Val -> builder.mutable(false)
            is PropertyModifier.Var -> builder.mutable(true)
            is PropertyModifier.LateinitVar -> {
                builder.addModifiers(KModifier.LATEINIT)
                builder.mutable(true)
            }
            is PropertyModifier.OverrideVal -> {
                builder.addModifiers(KModifier.OVERRIDE)
                builder.mutable(false)
            }
            is PropertyModifier.OverrideVar -> {
                builder.addModifiers(KModifier.OVERRIDE)
                builder.mutable(true)
            }
        }
    }
}

