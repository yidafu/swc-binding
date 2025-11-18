package dev.yidafu.swc.generator.codegen.generator.types

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.MAP
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import dev.yidafu.swc.generator.codegen.generator.PoetGenerator
import dev.yidafu.swc.generator.codegen.poet.PoetConstants
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration

/**
 * 负责 types.kt 中注解与 typealias 的生成。
 */
class TypeAliasEmitter {
    fun emit(
        fileBuilder: FileSpec.Builder,
        typeAliases: List<KotlinDeclaration.TypeAliasDecl>,
        poet: PoetGenerator
    ) {
        fileBuilder.addType(createSwcDslMarkerAnnotation())
        fileBuilder.addTypeAlias(createRecordTypeAlias())
        typeAliases.forEach { typeAlias ->
            poet.emitTypeAlias(fileBuilder, typeAlias)
        }
    }

    private fun createSwcDslMarkerAnnotation(): TypeSpec {
        return TypeSpec.annotationBuilder("SwcDslMarker")
            .addAnnotation(PoetConstants.Kotlin.DSL_MARKER)
            .build()
    }

    private fun createRecordTypeAlias(): TypeAliasSpec {
        val tTypeVar = TypeVariableName("T")
        val sTypeVar = TypeVariableName("S")
        return TypeAliasSpec.builder("Record", MAP.parameterizedBy(tTypeVar, STRING))
            .addTypeVariable(tTypeVar)
            .addTypeVariable(sTypeVar)
            .build()
    }
}
