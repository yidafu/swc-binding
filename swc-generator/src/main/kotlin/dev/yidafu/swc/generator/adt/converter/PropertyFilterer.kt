package dev.yidafu.swc.generator.adt.converter

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzer
import dev.yidafu.swc.generator.adt.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.util.Logger

/**
 * 专门处理属性过滤的转换器
 * 从 TypeScriptDeclarationConverter 中分离出来的专门职责
 */
object PropertyFilterer {

    /**
     * 过滤掉从父接口继承的属性，避免重复声明
     * 动态查询继承链，而不是硬编码
     */
    fun filterInheritedProperties(
        properties: List<KotlinDeclaration.PropertyDecl>,
        parents: List<KotlinType>,
        interfaceName: String,
        analyzer: InheritanceAnalyzer? = null
    ): List<KotlinDeclaration.PropertyDecl> {
        if (analyzer == null) {
            Logger.debug("  没有继承分析器，跳过属性过滤", 6)
            return properties
        }

        // 获取所有祖先接口的属性名
        val inheritedPropertyNames = getAllInheritedPropertyNames(interfaceName, analyzer)

        Logger.debug("  接口 $interfaceName 的祖先属性: $inheritedPropertyNames", 6)
        Logger.debug("  过滤前属性数: ${properties.size}, 过滤后属性数: ${properties.size - inheritedPropertyNames.size}", 6)

        return properties.filter { property ->
            !inheritedPropertyNames.contains(property.name)
        }
    }

    /**
     * 递归获取所有祖先接口的属性名
     */
    private fun getAllInheritedPropertyNames(
        interfaceName: String,
        analyzer: InheritanceAnalyzer
    ): Set<String> {
        // 使用 LinkedHashSet 保持属性名顺序，确保过滤的确定性
        val allPropertyNames = LinkedHashSet<String>()

        // 获取直接父接口
        val directParents = analyzer.findAllParentsByChild(interfaceName)

        directParents.forEach { parentName ->
            // 1. 递归获取父接口的属性
            val parentProperties = getAllInheritedPropertyNames(parentName, analyzer)
            allPropertyNames.addAll(parentProperties)

            // 2. 获取当前父接口自己的属性
            val parentDecl = analyzer.getDeclaration(parentName)
            if (parentDecl is TypeScriptDeclaration.InterfaceDeclaration) {
                val parentOwnProps = parentDecl.members.map { it.name }
                allPropertyNames.addAll(parentOwnProps)
            }
        }

        return allPropertyNames
    }
}
