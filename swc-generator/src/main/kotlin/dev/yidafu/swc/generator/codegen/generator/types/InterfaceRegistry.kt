package dev.yidafu.swc.generator.codegen.generator.types

import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.isInterface

/**
 * 维护接口名称的可变视图，避免惰性快照带来的不一致。
 */
class InterfaceRegistry(classDecls: Collection<KotlinDeclaration.ClassDecl>) {
    private val mutableNames = classDecls
        .filter { it.modifier.isInterface() }
        .map { it.name }
        .toMutableSet()

    val names: Set<String> get() = mutableNames

    fun register(classDecl: KotlinDeclaration.ClassDecl) {
        if (classDecl.modifier.isInterface()) {
            mutableNames.add(classDecl.name)
        }
    }
}
