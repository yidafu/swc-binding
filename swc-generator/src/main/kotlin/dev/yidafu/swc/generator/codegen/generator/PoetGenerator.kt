package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.FileSpec
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration

interface PoetGenerator {
    fun emitType(
        fileBuilder: FileSpec.Builder,
        declaration: KotlinDeclaration.ClassDecl,
        interfaceNames: Set<String>
    ): Boolean

    fun emitTypeAlias(
        fileBuilder: FileSpec.Builder,
        typeAlias: KotlinDeclaration.TypeAliasDecl
    ): Boolean

    fun buildFile(fileBuilder: FileSpec.Builder): FileSpec
}
