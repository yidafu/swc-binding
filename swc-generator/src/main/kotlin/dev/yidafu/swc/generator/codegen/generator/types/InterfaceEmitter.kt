package dev.yidafu.swc.generator.codegen.generator.types

import com.squareup.kotlinpoet.FileSpec
import dev.yidafu.swc.generator.codegen.generator.PoetGenerator
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.util.Logger

/**
 * 生成接口声明。
 */
class InterfaceEmitter(
    private val interfaceRegistry: InterfaceRegistry
) {
    fun emit(
        builderSelector: (KotlinDeclaration.ClassDecl) -> FileSpec.Builder,
        interfaces: List<KotlinDeclaration.ClassDecl>,
        poet: PoetGenerator
    ) {
        interfaces.forEach { interfaceDecl ->
            val builder = builderSelector(interfaceDecl)
            if (poet.emitType(builder, interfaceDecl, interfaceRegistry.names)) {
                Logger.verbose("  ✓ 接口: ${interfaceDecl.name}", 6)
            }
        }
    }
}

