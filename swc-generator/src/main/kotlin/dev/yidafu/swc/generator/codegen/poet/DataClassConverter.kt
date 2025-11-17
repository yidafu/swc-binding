package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration

/**
 * 数据类转换器
 * 负责将数据类声明转换为 KotlinPoet 的 TypeSpec
 */
object DataClassConverter {

    /**
     * 为数据类添加构造函数参数
     */
    fun addDataClassProperties(
        builder: TypeSpec.Builder,
        decl: KotlinDeclaration.ClassDecl,
        convertType: (dev.yidafu.swc.generator.model.kotlin.KotlinType) -> com.squareup.kotlinpoet.TypeName,
        addUnionAnnotation: (String, String, dev.yidafu.swc.generator.model.kotlin.KotlinType, ParameterSpec.Builder) -> Unit
    ) {
        // Data class: 属性作为构造函数参数
        val constructorParams = decl.properties.map { prop ->
            val typeName = convertType(prop.type)
            val paramBuilder = ParameterSpec.builder(prop.name, typeName)

            // 添加注解
            prop.annotations.forEach { annotation ->
                AnnotationConverter.convertAnnotation(annotation)?.let { paramBuilder.addAnnotation(it) }
            }

            // 添加默认值
            prop.defaultValue?.let { defaultValue ->
                val defaultValueStr = defaultValue.toCodeString()
                if (defaultValueStr.isNotBlank()) {
                    paramBuilder.defaultValue(defaultValueStr)
                }
            }

            // 若为 Union.Ux 或 Array<Union.Ux>，添加 @Serializable(with=...) 注解并收集
            addUnionAnnotation(decl.name, prop.name, prop.type, paramBuilder)

            paramBuilder.build()
        }
        builder.primaryConstructor(
            FunSpec.constructorBuilder()
                .addParameters(constructorParams)
                .build()
        )
    }
}

