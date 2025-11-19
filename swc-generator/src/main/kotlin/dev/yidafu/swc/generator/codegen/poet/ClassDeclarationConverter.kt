package dev.yidafu.swc.generator.codegen.poet

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import dev.yidafu.swc.generator.util.CollectionUtils

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
        return parents.any { dfs(it, CollectionUtils.newStringSet()) }
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
        return parents.any { dfs(it, CollectionUtils.newStringSet()) }
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
        return parents.any { dfs(it, CollectionUtils.newStringSet()) }
    }

    /**
     * 检查父类型层级中是否存在同名属性
     * * @param propName 属性名称（可能包含反引号）
     * @param parents 父类型列表
     * @param declLookup 类声明查找表
     * @return 如果父类型中有同名属性返回 true，否则返回 false
     */
    fun parentHasProperty(
        propName: String,
        parents: List<KotlinType>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ): Boolean {
        fun checkProperty(type: KotlinType, target: String, seen: MutableSet<String>): Boolean {
            val name = when (type) {
                is KotlinType.Simple -> type.name
                else -> return false
            }
            if (!seen.add(name)) return false
            val decl = declLookup[name] ?: return false
            // 检查当前类是否有同名属性（支持带反引号的名称）
            val cleanTarget = target.removeSurrounding("`")
            if (decl.properties.any { it.name == target || it.name.removeSurrounding("`") == cleanTarget }) {
                return true
            }
            // 递归检查所有父类型
            return decl.parents.any { checkProperty(it, target, seen) }
        }
        return parents.any { checkProperty(it, propName, CollectionUtils.newStringSet()) }
    }

    /**
     * 如果父类型层级中不存在同名属性，则去掉 Override 修饰，避免生成无效的 override。
     * 如果父类型层级中存在同名属性，则添加 Override 修饰。
     */
    fun adjustOverrideModifier(
        prop: KotlinDeclaration.PropertyDecl,
        parents: List<KotlinType>,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ): KotlinDeclaration.PropertyDecl {
        val hasParentProperty = parentHasProperty(prop.name, parents, declLookup)

        // 如果父类有同名属性，确保添加 override
        if (hasParentProperty) {
            val newModifier = when (prop.modifier) {
                is PropertyModifier.Val -> PropertyModifier.OverrideVal
                is PropertyModifier.Var -> PropertyModifier.OverrideVar
                is PropertyModifier.OverrideVal,
                is PropertyModifier.OverrideVar -> prop.modifier // 已经是 override，保持不变
                else -> prop.modifier
            }
            return if (newModifier == prop.modifier) prop else prop.copy(modifier = newModifier)
        }

        // 如果父类没有同名属性，移除 override（如果存在）
        val newModifier = when (prop.modifier) {
            is PropertyModifier.OverrideVal -> PropertyModifier.Val
            is PropertyModifier.OverrideVar -> PropertyModifier.Var
            else -> prop.modifier
        }
        return if (newModifier == prop.modifier) prop else prop.copy(modifier = newModifier)
    }
}
