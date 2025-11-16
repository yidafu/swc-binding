package dev.yidafu.swc.generator.codegen.generator.dsl

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeName
import dev.yidafu.swc.generator.codegen.generator.PoetGenerator
import dev.yidafu.swc.generator.codegen.model.KotlinExtensionFun
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFile
import dev.yidafu.swc.generator.codegen.poet.PoetConstants
import dev.yidafu.swc.generator.codegen.poet.createDslLambdaType
import dev.yidafu.swc.generator.codegen.poet.createExtensionFun
import dev.yidafu.swc.generator.codegen.poet.createFileBuilder
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.isInterface
import dev.yidafu.swc.generator.util.Logger
import java.nio.file.Path

class DslFileEmitter(
    private val modelContext: DslModelContext,
    private val poet: PoetGenerator
) {
    fun emit(collection: DslExtensionCollection, outputDir: Path): List<GeneratedFile> {
        val files = mutableListOf<GeneratedFile>()
        collection.groups.forEach { (receiver, funList) ->
            files += emitReceiverDslFile(outputDir, receiver, funList)
        }
        files += emitCreateFile(outputDir, collection.nodeCreatableClasses)
        return files
    }

    private fun emitReceiverDslFile(
        outputDir: Path,
        receiver: String,
        funList: List<KotlinExtensionFun>
    ): GeneratedFile {
        Logger.debug("生成 DSL 文件: $receiver.kt (${funList.size} 个函数)", 6)
        val fileBuilder = createFileBuilder(
            PoetConstants.PKG_DSL,
            receiver,
            PoetConstants.PKG_TYPES to "*"
        )

        funList.forEach { extFun ->
            runCatching {
                createDslExtensionFun(extFun, receiver)
            }.onSuccess { funSpec ->
                fileBuilder.addFunction(funSpec)
            }.onFailure { e ->
                Logger.warn("生成失败: ${extFun.funName}, 跳过 (${e.message})")
            }
        }
        val fileSpec = poet.buildFile(fileBuilder)
        return GeneratedFile(outputDir.resolve("$receiver.kt"), fileSpec = fileSpec)
    }

    private fun emitCreateFile(
        outputDir: Path,
        nodeCreatableClasses: List<KotlinDeclaration.ClassDecl>
    ): GeneratedFile {
        Logger.debug("生成 create.kt", 6)
        val fileBuilder = createFileBuilder(
            PoetConstants.PKG_DSL,
            "create",
            PoetConstants.PKG_TYPES to "*"
        )

        nodeCreatableClasses.forEach { klass ->
            val funSpec = createCreateFunction(klass)
            fileBuilder.addFunction(funSpec)
        }
        val fileSpec = poet.buildFile(fileBuilder)
        Logger.verbose("  生成了 ${nodeCreatableClasses.size} 个 create 函数", 6)
        return GeneratedFile(outputDir.resolve("create.kt"), fileSpec = fileSpec)
    }

    private fun createDslExtensionFun(extFun: KotlinExtensionFun, receiver: String): FunSpec {
        return createDslExtensionFunLegacy(extFun, receiver)
    }

    private fun createDslExtensionFunLegacy(extFun: KotlinExtensionFun, receiver: String): FunSpec {
        val funName = DslNamingRules.toFunName(extFun.funName.replace("Impl", ""))
        val returnTypeName = DslNamingRules.sanitizeTypeName(DslNamingRules.removeGenerics(extFun.funName.replace("Impl", "")))
        val receiverTypeName = DslNamingRules.sanitizeTypeName(DslNamingRules.removeGenerics(receiver))
        val implTypeName = resolveImplClassName(extFun.funName)
            ?: throw IllegalStateException("No concrete implementation for ${extFun.funName}")

        return createExtensionFun(
            funName = funName,
            receiverType = ClassName(PoetConstants.PKG_TYPES, receiverTypeName),
            returnType = ClassName(PoetConstants.PKG_TYPES, returnTypeName),
            implType = ClassName(PoetConstants.PKG_TYPES, implTypeName),
            kdoc = extFun.comments.takeIf { it.isNotEmpty() }
        )
    }

    private fun resolveImplClassName(typeName: String): String? {
        val normalized = DslNamingRules.sanitizeTypeName(DslNamingRules.removeGenerics(typeName)).removeSurrounding("`")
        val decl = modelContext.classInfoByName[normalized]
        return when {
            // 若本身是具体类，直接返回
            decl != null && !decl.modifier.isInterface() -> decl.name
            // 若是接口名，则查找其具体类后代；优先选择 `${name}Impl` 命名的具体类
            decl != null && decl.modifier.isInterface() -> {
                val descendants = modelContext.hierarchy.findDescendants(normalized)
                val concrete = descendants.firstOrNull { name ->
                    modelContext.classInfoByName[name]?.modifier?.isInterface() == false
                }
                val preferred = descendants.firstOrNull { it == "${normalized}Impl" && modelContext.classInfoByName[it]?.modifier?.isInterface() == false }
                preferred ?: concrete
            }
            else -> null
        }
    }

    private fun createCreateFunction(klass: KotlinDeclaration.ClassDecl): FunSpec {
        val className = klass.name.removeSurrounding("`")
        val classType = ClassName(PoetConstants.PKG_TYPES, className)

        return FunSpec.builder("create$className")
            .addParameter("block", createDslLambdaType(classType as TypeName))
            .returns(classType)
            .addStatement("return %T().apply(block)", classType)
            .build()
    }
}
