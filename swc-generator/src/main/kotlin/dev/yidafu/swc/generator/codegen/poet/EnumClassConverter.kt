package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration

/**
 * 枚举类转换器
 * 负责将枚举类声明转换为 KotlinPoet 的 TypeSpec
 */
object EnumClassConverter {

    /**
     * 为枚举类添加构造函数参数和枚举条目
     */
    fun addEnumClassProperties(
        builder: TypeSpec.Builder,
        decl: KotlinDeclaration.ClassDecl,
        convertType: (dev.yidafu.swc.generator.model.kotlin.KotlinType) -> com.squareup.kotlinpoet.TypeName
    ) {
        // 为枚举类添加构造函数参数
        if (decl.properties.isNotEmpty()) {
            val constructorParams = decl.properties.map { prop ->
                val typeName = convertType(prop.type)
                ParameterSpec.builder(prop.name, typeName).build()
            }

            builder.primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameters(constructorParams)
                    .build()
            )
        }

        // 添加枚举条目
        decl.enumEntries.forEach { entry ->
            val hasArgs = entry.arguments.isNotEmpty()
            val hasAnnotations = entry.annotations.isNotEmpty()
            if (hasArgs || hasAnnotations) {
                val enumBuilder = TypeSpec.anonymousClassBuilder()

                entry.arguments.forEach { arg ->
                    enumBuilder.addSuperclassConstructorParameter("%L", arg.toCodeString())
                }

                entry.annotations.forEach { annotation ->
                    AnnotationConverter.convertAnnotation(annotation)?.let { enumBuilder.addAnnotation(it) }
                }

                builder.addEnumConstant(entry.name, enumBuilder.build())
            } else {
                builder.addEnumConstant(entry.name)
            }
        }
    }
}
