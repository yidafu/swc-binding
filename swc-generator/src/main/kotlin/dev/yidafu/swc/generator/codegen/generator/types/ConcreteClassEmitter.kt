package dev.yidafu.swc.generator.codegen.generator.types

import com.squareup.kotlinpoet.FileSpec
import dev.yidafu.swc.generator.codegen.generator.PoetGenerator
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.util.Logger

/**
 * 负责普通类（非接口）的生成。
 */
class ConcreteClassEmitter(
    private val interfaceRegistry: InterfaceRegistry
) {
    fun emit(
        fileBuilder: FileSpec.Builder,
        classes: List<KotlinDeclaration.ClassDecl>,
        poet: PoetGenerator,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ) {
        classes.forEach { classDecl ->
            val cleanName = classDecl.name.removeSurrounding("`")
            if (cleanName == "ForOfStatement" || cleanName == "ComputedPropName") {
                Logger.info("  处理类: ${classDecl.name} (${classDecl.modifier})", 4)
            } else {
                Logger.debug("  处理类: ${classDecl.name} (${classDecl.modifier})", 6)
            }
            if (poet.emitType(fileBuilder, classDecl, interfaceRegistry.names, declLookup)) {
                if (cleanName == "ForOfStatement" || cleanName == "ComputedPropName") {
                    Logger.info("  ✓ 类: ${classDecl.name} (${classDecl.modifier})", 4)
                } else {
                    Logger.verbose("  ✓ 类: ${classDecl.name} (${classDecl.modifier})", 6)
                }
            } else {
                Logger.warn("  ✗ 类生成失败: ${classDecl.name} (${classDecl.modifier})")
            }
        }
    }
}
