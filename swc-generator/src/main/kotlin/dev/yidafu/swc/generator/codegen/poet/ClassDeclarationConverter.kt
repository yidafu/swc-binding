package dev.yidafu.swc.generator.codegen.poet

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType

/**
 * 类声明转换器
 * 提供类声明转换相关的辅助方法
 */
object ClassDeclarationConverter {

    /**
     * 判断接口是否属于 Node 系谱（直接或通过 *Base 接口）
     */
    fun isDerivedFromNode(
        parents: List<KotlinType>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ): Boolean {
        if (parents.isEmpty()) return false
        fun dfs(type: KotlinType, seen: MutableSet<String>): Boolean {
            val name = when (type) {
                is KotlinType.Simple -> type.name
                else -> return false
            }
            if (!seen.add(name)) return false
            if (name == "Node") return true
            val parentDecl = declLookup[name] ?: return false
            return parentDecl.parents.any { p -> dfs(p, seen) }
        }
        return parents.any { dfs(it, mutableSetOf()) }
    }

    /**
     * 判断是否派生自指定类型
     */
    fun isDerivedFrom(
        parents: List<KotlinType>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>,
        target: String
    ): Boolean {
        if (parents.isEmpty()) return false
        fun dfs(type: KotlinType, seen: MutableSet<String>): Boolean {
            val name = when (type) {
                is KotlinType.Simple -> type.name
                else -> return false
            }
            if (!seen.add(name)) return false
            if (name == target) return true
            val parentDecl = declLookup[name] ?: return false
            return parentDecl.parents.any { p -> dfs(p, seen) }
        }
        return parents.any { dfs(it, mutableSetOf()) }
    }

    /**
     * 检查类是否实现了任何密封接口（sealed interface）
     */
    fun implementsSealedInterface(
        parents: List<KotlinType>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ): Boolean {
        if (parents.isEmpty()) return false
        fun dfs(type: KotlinType, seen: MutableSet<String>): Boolean {
            val name = when (type) {
                is KotlinType.Simple -> type.name
                else -> return false
            }
            if (!seen.add(name)) return false
            val parentDecl = declLookup[name] ?: return false
            // 检查当前父类型是否是密封接口
            if (parentDecl.modifier is ClassModifier.SealedInterface) {
                return true
            }
            // 递归检查所有父类型
            return parentDecl.parents.any { p -> dfs(p, seen) }
        }
        return parents.any { dfs(it, mutableSetOf()) }
    }

    /**
     * 如果父类型层级中不存在同名属性，则去掉 Override 修饰，避免生成无效的 override。
     */
    fun downgradeOverrideIfNeeded(
        prop: KotlinDeclaration.PropertyDecl,
        parents: List<KotlinType>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ): KotlinDeclaration.PropertyDecl {
        fun parentHasProperty(type: KotlinType, target: String, seen: MutableSet<String>): Boolean {
            val name = when (type) {
                is KotlinType.Simple -> type.name
                else -> return false
            }
            if (!seen.add(name)) return false
            val decl = declLookup[name] ?: return false
            if (decl.properties.any { it.name == target }) return true
            return decl.parents.any { parentHasProperty(it, target, seen) }
        }
        val has = parents.any { parentHasProperty(it, prop.name, mutableSetOf()) }
        if (has) return prop
        val newModifier = when (prop.modifier) {
            dev.yidafu.swc.generator.model.kotlin.PropertyModifier.OverrideVal ->
                dev.yidafu.swc.generator.model.kotlin.PropertyModifier.Val
            dev.yidafu.swc.generator.model.kotlin.PropertyModifier.OverrideVar ->
                dev.yidafu.swc.generator.model.kotlin.PropertyModifier.Var
            else -> prop.modifier
        }
        return if (newModifier == prop.modifier) prop else prop.copy(modifier = newModifier)
    }
}
