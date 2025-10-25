package dev.yidafu.swc.generator.adt.typescript

import dev.yidafu.swc.generator.util.Logger

/**
 * 基于 TypeScript ADT 的继承关系分析器
 * 替代全局的 ExtendRelationship，提供更好的类型安全和可测试性
 */
class InheritanceAnalyzer(private val declarations: List<TypeScriptDeclaration>) {

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
