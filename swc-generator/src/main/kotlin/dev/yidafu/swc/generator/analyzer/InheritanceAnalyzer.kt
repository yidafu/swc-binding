package dev.yidafu.swc.generator.analyzer

import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.model.typescript.TypeScriptType
import dev.yidafu.swc.generator.util.Logger

/**
 * 基于 TypeScript ADT 的继承关系分析器
 * 替代全局的 ExtendRelationship，提供更好的类型安全和可测试性
 */
class InheritanceAnalyzer(private val declarations: List<TypeScriptDeclaration> = emptyList()) {

    private val inheritanceMap = buildInheritanceMap()

    /**
     * 构建继承关系映射
     */
    private fun buildInheritanceMap(): Map<String, List<String>> {
        // 使用 LinkedHashMap 保持继承关系顺序，确保生成的确定性
        val map = LinkedHashMap<String, MutableList<String>>()

        declarations.forEach { declaration ->
            when (declaration) {
                is TypeScriptDeclaration.InterfaceDeclaration -> {
                    val childName = declaration.name
                    declaration.extends.forEach { parentRef ->
                        val parentName = parentRef.name
                        map.getOrPut(parentName) { mutableListOf() }.add(childName)
                    }
                }
                is TypeScriptDeclaration.TypeAliasDeclaration -> {
                    // 类型别名通常不参与继承关系
                }
            }
        }

        Logger.debug("构建继承关系映射: ${map.size} 个父类型")
        return map
    }

    /**
     * 查找所有子类型
     */
    fun findAllChildrenByParent(parentName: String): List<String> {
        return inheritanceMap[parentName] ?: emptyList()
    }

    /**
     * 查找所有父类型
     */
    fun findAllParentsByChild(childName: String): List<String> {
        return declarations
            .filterIsInstance<TypeScriptDeclaration.InterfaceDeclaration>()
            .find { it.name == childName }
            ?.extends?.map { it.name } ?: emptyList()
    }

    /**
     * 添加继承关系
     */
    fun addInheritance(parentName: String, childName: String) {
        (inheritanceMap as MutableMap<String, MutableList<String>>).getOrPut(parentName) { mutableListOf() }.add(childName)
        Logger.debug("添加继承关系: $childName -> $parentName", 6)
    }

    /**
     * 查找所有后代类型（递归）
     */
    fun findAllGrandChildren(parentName: String): List<String> {
        // 使用 LinkedHashSet 保持遍历顺序，确保递归遍历的确定性
        val result = LinkedHashSet<String>()
        val visited = LinkedHashSet<String>()

        fun collectChildren(name: String) {
            if (visited.contains(name)) return
            visited.add(name)

            val children = inheritanceMap[name] ?: emptyList()
            children.forEach { child ->
                result.add(child)
                collectChildren(child)
            }
        }

        collectChildren(parentName)
        return result.toList()
    }

    /**
     * 查找所有祖先类型（递归）
     */
    fun findAllGrandParents(childName: String): List<String> {
        // 使用 LinkedHashSet 保持遍历顺序，确保递归遍历的确定性
        val result = LinkedHashSet<String>()
        val visited = LinkedHashSet<String>()

        fun collectParents(name: String) {
            if (visited.contains(name)) return
            visited.add(name)

            val parents = findAllParentsByChild(name)
            parents.forEach { parent ->
                result.add(parent)
                collectParents(parent)
            }
        }

        collectParents(childName)
        return result.toList()
    }

    /**
     * 查找根类型（没有父类型的类型）
     */
    fun findRootTypes(): List<String> {
        val allTypes = declarations
            .filterIsInstance<TypeScriptDeclaration.InterfaceDeclaration>()
            .map { it.name }
            .toSet()

        val typesWithParents = allTypes.filter { type ->
            findAllParentsByChild(type).isNotEmpty()
        }.toSet()

        return allTypes.filter { it !in typesWithParents }
    }

    /**
     * 检测循环继承
     */
    fun detectCycles(): List<List<String>> {
        val cycles = mutableListOf<List<String>>()
        val visited = mutableSetOf<String>()
        val recursionStack = mutableSetOf<String>()

        declarations
            .filterIsInstance<TypeScriptDeclaration.InterfaceDeclaration>()
            .forEach { declaration ->
                if (!visited.contains(declaration.name)) {
                    detectCyclesDFS(declaration.name, visited, recursionStack, mutableListOf(), cycles)
                }
            }

        return cycles
    }

    private fun detectCyclesDFS(
        current: String,
        visited: MutableSet<String>,
        recursionStack: MutableSet<String>,
        path: MutableList<String>,
        cycles: MutableList<List<String>>
    ) {
        visited.add(current)
        recursionStack.add(current)
        path.add(current)

        val children = inheritanceMap[current] ?: emptyList()
        children.forEach { child ->
            if (!visited.contains(child)) {
                detectCyclesDFS(child, visited, recursionStack, path, cycles)
            } else if (recursionStack.contains(child)) {
                // 发现循环
                val cycleStart = path.indexOf(child)
                val cycle = path.subList(cycleStart, path.size) + child
                cycles.add(cycle)
                Logger.warn("检测到循环继承: ${cycle.joinToString(" -> ")}")
            }
        }

        recursionStack.remove(current)
        path.removeAt(path.size - 1)
    }

    /**
     * 获取继承深度
     */
    fun getInheritanceDepth(typeName: String): Int {
        val parents = findAllParentsByChild(typeName)
        return if (parents.isEmpty()) {
            0
        } else {
            parents.maxOfOrNull { getInheritanceDepth(it) }?.plus(1) ?: 1
        }
    }

    /**
     * 获取最大继承深度
     */
    fun getMaxDepth(): Int {
        return declarations
            .filterIsInstance<TypeScriptDeclaration.InterfaceDeclaration>()
            .map { getInheritanceDepth(it.name) }
            .maxOrNull() ?: 0
    }

    /**
     * 检查是否有子类型
     */
    fun hasChildren(typeName: String): Boolean {
        return inheritanceMap[typeName]?.isNotEmpty() ?: false
    }

    /**
     * 检查是否有父类型
     */
    fun hasParents(typeName: String): Boolean {
        return findAllParentsByChild(typeName).isNotEmpty()
    }

    /**
     * 检查一个类型是否是另一个类型的后代
     */
    fun isDescendantOf(childName: String, ancestorName: String): Boolean {
        if (childName == ancestorName) return true

        val parents = findAllParentsByChild(childName)
        if (parents.isEmpty()) return false

        // 直接父类型中包含目标祖先
        if (parents.contains(ancestorName)) return true

        // 递归检查所有父类型
        return parents.any { parent -> isDescendantOf(parent, ancestorName) }
    }

    /**
     * 根据名称获取声明
     */
    fun getDeclaration(name: String): TypeScriptDeclaration? {
        return declarations.find {
            when (it) {
                is TypeScriptDeclaration.InterfaceDeclaration -> it.name == name
                is TypeScriptDeclaration.TypeAliasDeclaration -> it.name == name
            }
        }
    }

    /**
     * 展开类型别名为其所有成员类型
     * 
     * 如果类型别名是联合类型（如 `Expression = A | B | C`），展开为所有成员类型名称列表。
     * 支持递归展开（类型别名可能引用其他类型别名）。
     * 
     * @param typeName 类型别名名称
     * @param visited 已访问的类型别名集合（用于避免无限递归）
     * @return 展开后的类型名称列表，如果不是类型别名或不是联合类型则返回 null
     */
    fun expandTypeAlias(typeName: String, visited: MutableSet<String> = mutableSetOf()): List<String>? {
        // 检查是否已访问（避免循环引用）
        if (visited.contains(typeName)) {
            Logger.debug("检测到类型别名循环引用: $typeName", 6)
            return null
        }
        visited.add(typeName)

        // 查找类型别名声明
        val declaration = getDeclaration(typeName) as? TypeScriptDeclaration.TypeAliasDeclaration
            ?: return null

        // 如果类型别名是联合类型，展开为所有成员类型名称
        val type = declaration.type
        if (type is TypeScriptType.Union) {
            val expandedTypes = mutableListOf<String>()
            
            for (memberType in type.types) {
                when (memberType) {
                    is TypeScriptType.Reference -> {
                        // 检查成员类型是否是另一个类型别名
                        val memberName = memberType.name
                        val memberExpanded = expandTypeAlias(memberName, visited)
                        
                        if (memberExpanded != null) {
                            // 递归展开类型别名
                            expandedTypes.addAll(memberExpanded)
                        } else {
                            // 不是类型别名或无法展开，直接使用类型名称
                            expandedTypes.add(memberName)
                        }
                    }
                    else -> {
                        // 其他类型（字面量、关键字等）不支持展开
                        Logger.debug("类型别名 $typeName 的成员类型不是引用类型: ${memberType::class.simpleName}", 8)
                    }
                }
            }
            
            if (expandedTypes.isNotEmpty()) {
                Logger.debug("展开类型别名 $typeName: ${expandedTypes.size} 个类型", 6)
                return expandedTypes.distinct() // 去重
            }
        }

        // 不是联合类型或展开失败
        visited.remove(typeName)
        return null
    }

    /**
     * 查找多个类型的最近公共父接口（Lowest Common Ancestor, LCA）
     * 
     * 算法：
     * 1. 收集每个类型的所有祖先（包括自身）
     * 2. 找到所有类型的公共祖先
     * 3. 在公共祖先中，选择深度最大的（即最具体的）
     * 4. 如果最深的公共祖先是 `Node` 或没有找到，返回 `null`
     * 
     * @param typeNames 类型名称列表
     * @return 最近公共父接口名称，如果找不到（除了 Node）则返回 null
     */
    fun findLowestCommonAncestor(typeNames: List<String>): String? {
        if (typeNames.isEmpty()) return null
        if (typeNames.size == 1) return typeNames.first()

        // 收集每个类型的所有祖先（包括自身）
        val ancestorSets = typeNames.map { typeName ->
            val ancestors = mutableSetOf<String>()
            ancestors.add(typeName) // 包括自身
            ancestors.addAll(findAllGrandParents(typeName)) // 包括所有祖先
            ancestors
        }

        // 找到所有类型的公共祖先
        val commonAncestors = ancestorSets.reduce { acc, ancestors ->
            acc.intersect(ancestors).toMutableSet()
        }

        if (commonAncestors.isEmpty()) {
            Logger.debug("未找到类型 ${typeNames.joinToString(", ")} 的公共祖先", 6)
            return null
        }

        // 在公共祖先中，选择深度最大的（即最具体的）
        // 深度越大，说明离根节点越远，越具体
        val lca = commonAncestors.maxByOrNull { getInheritanceDepth(it) }

        if (lca == null) {
            Logger.debug("无法确定类型 ${typeNames.joinToString(", ")} 的最近公共祖先", 6)
            return null
        }

        // 直接返回最近公共祖先，包括 Node
        Logger.debug("类型 ${typeNames.joinToString(", ")} 的最近公共祖先是: $lca (深度: ${getInheritanceDepth(lca)})", 6)
        return lca
    }

    /**
     * 生成继承关系统计
     */
    fun generateStats(): String {
        val totalTypes = declarations.filterIsInstance<TypeScriptDeclaration.InterfaceDeclaration>().size
        val rootTypes = findRootTypes()
        val maxDepth = getMaxDepth()
        val cycles = detectCycles()

        return buildString {
            appendLine("继承关系统计:")
            appendLine("  总类型数: $totalTypes")
            appendLine("  根类型数: ${rootTypes.size}")
            appendLine("  最大深度: $maxDepth")
            appendLine("  循环数: ${cycles.size}")
            if (cycles.isNotEmpty()) {
                appendLine("  循环详情:")
                cycles.forEach { cycle ->
                    appendLine("    - ${cycle.joinToString(" -> ")}")
                }
            }
        }
    }
}
