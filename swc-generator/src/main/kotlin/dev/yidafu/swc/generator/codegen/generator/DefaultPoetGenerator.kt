package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.FileSpec
import dev.yidafu.swc.generator.codegen.poet.KotlinPoetConverter
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.util.Logger

class DefaultPoetGenerator : PoetGenerator {
    override fun emitType(
        fileBuilder: FileSpec.Builder,
        declaration: KotlinDeclaration.ClassDecl,
        interfaceNames: Set<String>
    ): Boolean {
        return runCatching {
            KotlinPoetConverter.convertDeclaration(declaration, interfaceNames)
        }.onSuccess { typeSpec ->
            fileBuilder.addType(typeSpec)
        }.onFailure { e ->
            Logger.warn("  生成类失败: ${declaration.name}, ${e.message}")
        }.isSuccess
    }

    override fun emitTypeAlias(
        fileBuilder: FileSpec.Builder,
        typeAlias: KotlinDeclaration.TypeAliasDecl
    ): Boolean {
        return runCatching {
            KotlinPoetConverter.convertTypeAliasDeclaration(typeAlias)
        }.onSuccess { spec ->
            fileBuilder.addTypeAlias(spec)
        }.onFailure { e ->
            Logger.warn("转换类型别名失败: ${typeAlias.name}, ${e.message}")
        }.isSuccess
    }

    override fun buildFile(fileBuilder: FileSpec.Builder): FileSpec = fileBuilder.build()
}
