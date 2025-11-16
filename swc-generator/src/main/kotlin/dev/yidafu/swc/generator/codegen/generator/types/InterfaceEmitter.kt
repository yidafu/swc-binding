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
            val lookup = interfaceRegistry.names // placeholder
            // 使用全量声明查找表：通过 context 传入更合理，这里由调用者传入 declLookup
            // 但当前接口侧父类皆为接口，使用 interfaces 构建也可；为统一，改由上层传入
            if (poet.emitType(builder, interfaceDecl, interfaceRegistry.names, emptyMap())) {
                Logger.verbose("  ✓ 接口: ${interfaceDecl.name}", 6)
            }
        }
    }
}

