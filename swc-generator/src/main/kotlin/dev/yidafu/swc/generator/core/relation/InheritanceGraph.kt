package dev.yidafu.swc.generator.core.relation

import dev.yidafu.swc.generator.util.Logger

/**
 * 继承图管理器
 * * 替代全局单例的 ExtendRelationship，提供更好的测试性和可维护性
 */
class InheritanceGraph {
    private val child2ParentMap = mutableMapOf<String, MutableSet<String>>()
    private val parent2ChildMap = mutableMapOf<String, MutableSet<String>>()
    private val nodeExtendsList = mutableListOf<Pair<String, String>>()

    /**
     * 添加继承关系
     * @param parent 父类型
     * @param child 子类型
     */
    fun addRelation(parent: String, child: String) {
        val pSet = child2ParentMap.getOrPut(child) { mutableSetOf() }
        pSet.add(parent)

        val cSet = parent2ChildMap.getOrPut(parent) { mutableSetOf() }
        cSet.add(child)

        nodeExtendsList.add(parent to child)

        Logger.debug("添加继承关系: $parent -> $child")
    }

    /**
     * 批量添加继承关系
     */
    fun addRelations(relations: List<Pair<String, String>>) {
        relations.forEach { (parent, child) ->
            addRelation(parent, child)
        }
    }

    /**
     * 判断是否为叶子节点（没有子类型）
     */
    fun isGrandChild(child: String): Boolean {
        return findAllChildrenByParent(child).isEmpty()
    }

    /**
     * 获取根类型
     */
    fun getRoot(child: String): String? {
        val parentList = child2ParentMap[child]
        if (parentList != null && parentList.isNotEmpty()) {
            for (type in parentList) {
                val root = getRoot(type)
                if (root != null) return root
            }
            return null
        } else {
            return child
        }
    }

    /**
     * 查找直接父类型
     */
    fun findParentsByChild(child: String): List<String> {
        return child2ParentMap[child]?.toList() ?: emptyList()
    }

    /**
     * 查找所有父类型（递归）
     */
    fun findAllParentByChild(child: String): List<String> {
        val result = mutableSetOf<String>()
        findParentsByChild(child).forEach { parent ->
            result.add(parent)
            result.addAll(findAllParentByChild(parent))
        }
        return result.toList()
    }

    /**
     * 获取所有父类型
     */
    fun getAllParents(): List<String> {
        return parent2ChildMap.keys.toList()
    }

    /**
     * 获取所有子类型
     */
    fun getAllChildren(): List<String> {
        return child2ParentMap.keys.toList()
    }

    /**
     * 获取所有类型
     */
    fun getAllTypes(): List<String> {
        val allTypes = mutableSetOf<String>()
        allTypes.addAll(child2ParentMap.keys)
        allTypes.addAll(parent2ChildMap.keys)
        return allTypes.toList()
    }

    /**
     * 查找所有叶子子类型（递归）
     */
    fun findAllGrandChildren(type: String): List<String> {
        val result = mutableSetOf<String>()
        val childTypes = nodeExtendsList.filter { it.first == type }.map { it.second }

        if (childTypes.isNotEmpty()) {
            childTypes.forEach { child ->
                result.addAll(findAllGrandChildren(child))
            }
        } else {
            result.add(type)
        }

        return result.toList()
    }

    /**
     * 查找直接子类型
     */
    fun findAllDirectChildren(type: String): List<String> {
        return parent2ChildMap[type]?.toList() ?: emptyList()
    }

    /**
     * 查找所有子类型（包括间接子类型）
     */
    fun findAllChildrenByParent(type: String): List<String> {
        val result = mutableSetOf<String>()
        val childTypes = nodeExtendsList.filter { it.first == type }.map { it.second }
        result.addAll(childTypes)

        if (childTypes.isNotEmpty()) {
            childTypes.forEach { child ->
                result.addAll(findAllChildrenByParent(child))
            }
        }

        return result.toList()
    }

    /**
     * 查找与指定节点相关的所有类型
     */
    fun findAllRelativeByName(node: String): List<String> {
        val children = findAllGrandChildren(node)
        val result = mutableSetOf<String>()
        children.forEach { child ->
            result.add(child)
            result.addAll(findAllParentByChild(child))
        }
        return result.toList()
    }

    /**
     * 查找共同父类型
     */
    fun getCommonParent(children: List<String>): String {
        val child2ParentPathMap = mutableMapOf<String, List<String>>()
        children.forEach { child ->
            child2ParentPathMap[child] = getPathWithFound(child)
        }

        val parentPathList = child2ParentPathMap.values.toList()
        val distanceMap = mutableMapOf<String, Int>()
        val allParentTypes = parentPathList.flatten().toSet()

        allParentTypes.forEach { pType ->
            var maxDistance = -1
            parentPathList.forEach { list ->
                maxDistance = maxOf(maxDistance, list.indexOf(pType))
            }
            distanceMap[pType] = maxDistance
        }

        var commonType = "Node"
        var minDistance = 999
        distanceMap.entries.forEach { (k, v) ->
            if (v <= minDistance) {
                commonType = k
                minDistance = v
            }
        }

        return commonType
    }

    /**
     * 获取到根节点的路径
     */
    fun getPathWithFound(child: String): List<String> {
        val path2Node = (getPathWithOrder("Node", child) ?: listOf(child)).toMutableSet()
        val pSet = child2ParentMap[child] ?: mutableSetOf()
        pSet.forEach { p ->
            if (!path2Node.contains(p) && p != "HasSpan") {
                path2Node.add(p)
            }
        }
        return path2Node.toList()
    }

    /**
     * 获取有序路径
     */
    private fun getPathWithOrder(node: String, child: String): List<String>? {
        val cSet = parent2ChildMap[node] ?: mutableSetOf()
        if (cSet.contains(child)) {
            return listOf(child, node)
        } else {
            for (c in cSet) {
                val res = getPathWithOrder(c, child)
                if (res != null) {
                    return listOf(c) + res
                }
            }
        }
        return null
    }

    /**
     * 获取根类型列表
     */
    fun getRootTypes(): List<String> {
        val allTypes = getAllTypes()
        val parentTypes = getAllParents()
        return allTypes.filter { !parentTypes.contains(it) }
    }

    /**
     * 获取最大继承深度
     */
    fun getMaxDepth(): Int {
        val allTypes = getAllTypes()
        return allTypes.maxOfOrNull { getInheritanceDepth(it) } ?: 0
    }

    /**
     * 获取指定类型的继承深度
     */
    fun getInheritanceDepth(child: String): Int {
        val visited = mutableSetOf<String>()
        return getInheritanceDepthDFS(child, visited)
    }

    /**
     * 递归计算继承深度
     */
    private fun getInheritanceDepthDFS(
        current: String,
        visited: MutableSet<String>
    ): Int {
        if (visited.contains(current)) {
            return 0 // 避免循环
        }

        visited.add(current)

        val parents = findParentsByChild(current)
        if (parents.isEmpty()) {
            visited.remove(current)
            return 0
        }

        val maxParentDepth = parents.maxOfOrNull { parent ->
            getInheritanceDepthDFS(parent, visited)
        } ?: 0

        visited.remove(current)
        return maxParentDepth + 1
    }

    /**
     * 生成继承图统计信息
     */
    fun generateStats(): String {
        val stats = StringBuilder()
        stats.appendLine("继承图统计信息:")
        stats.appendLine("  - 总类型数: ${getAllTypes().size}")
        stats.appendLine("  - 根类型数: ${getRootTypes().size}")
        stats.appendLine("  - 最大继承深度: ${getMaxDepth()}")
        stats.appendLine("  - 继承关系数: ${nodeExtendsList.size}")

        val depthDistribution = getAllTypes().groupBy { getInheritanceDepth(it) }
        stats.appendLine("  - 深度分布:")
        depthDistribution.entries.sortedBy { it.key }.forEach { (depth, types) ->
            stats.appendLine("    深度 $depth: ${types.size} 个类型")
        }

        return stats.toString()
    }

    /**
     * 打印所有继承关系
     */
    fun printAllRelations() {
        Logger.info("=== 继承关系图 ===")
        if (nodeExtendsList.isEmpty()) {
            Logger.info("  无继承关系")
            return
        }

        nodeExtendsList.forEach { (parent, child) ->
            Logger.info("  $parent -> $child")
        }
    }

    /**
     * 打印指定类型的依赖关系
     */
    fun printTypeDependencies(typeName: String) {
        Logger.info("=== $typeName 的依赖关系 ===")

        val parents = findParentsByChild(typeName)
        val children = findAllDirectChildren(typeName)
        val allParents = findAllParentByChild(typeName)
        val allChildren = findAllChildrenByParent(typeName)

        Logger.info("  直接父类型 (${parents.size}):")
        parents.forEach { parent ->
            Logger.info("    -> $parent")
        }

        Logger.info("  所有父类型 (${allParents.size}):")
        allParents.forEach { parent ->
            Logger.info("    -> $parent")
        }

        Logger.info("  直接子类型 (${children.size}):")
        children.forEach { child ->
            Logger.info("    $child ->")
        }

        Logger.info("  所有子类型 (${allChildren.size}):")
        allChildren.forEach { child ->
            Logger.info("    $child ->")
        }

        val root = getRoot(typeName)
        Logger.info("  根类型: $root")
        Logger.info("  继承深度: ${getInheritanceDepth(typeName)}")
    }

    /**
     * 打印继承层次结构（树形结构）
     */
    fun printInheritanceTree() {
        Logger.info("=== 继承层次结构 ===")
        val rootTypes = getRootTypes()

        if (rootTypes.isEmpty()) {
            Logger.info("  无根类型")
            return
        }

        rootTypes.forEach { root ->
            printSubTree(root, 0)
        }
    }

    /**
     * 递归打印子树
     */
    private fun printSubTree(typeName: String, depth: Int) {
        val indent = "  ".repeat(depth)
        Logger.info("${indent}$typeName")

        val children = findAllDirectChildren(typeName)
        children.forEach { child ->
            printSubTree(child, depth + 1)
        }
    }

    /**
     * 打印循环依赖检测
     */
    fun printCircularDependencies() {
        Logger.info("=== 循环依赖检测 ===")
        val visited = mutableSetOf<String>()
        val recursionStack = mutableSetOf<String>()
        val circularDeps = mutableListOf<List<String>>()

        getAllTypes().forEach { type ->
            if (!visited.contains(type)) {
                findCircularDependenciesDFS(type, visited, recursionStack, mutableListOf(), circularDeps)
            }
        }

        if (circularDeps.isEmpty()) {
            Logger.info("  ✓ 无循环依赖")
        } else {
            Logger.warn("  ⚠️ 发现 ${circularDeps.size} 个循环依赖:")
            circularDeps.forEachIndexed { index, cycle ->
                Logger.warn("    循环 ${index + 1}: ${cycle.joinToString(" -> ")} -> ${cycle.first()}")
            }
        }
    }

    /**
     * 递归查找循环依赖
     */
    private fun findCircularDependenciesDFS(
        current: String,
        visited: MutableSet<String>,
        recursionStack: MutableSet<String>,
        path: MutableList<String>,
        circularDeps: MutableList<List<String>>
    ) {
        visited.add(current)
        recursionStack.add(current)
        path.add(current)

        val children = findAllDirectChildren(current)
        children.forEach { child ->
            if (recursionStack.contains(child)) {
                // 发现循环依赖
                val cycleStart = path.indexOf(child)
                if (cycleStart >= 0) {
                    circularDeps.add(path.subList(cycleStart, path.size) + child)
                }
            } else if (!visited.contains(child)) {
                findCircularDependenciesDFS(child, visited, recursionStack, path, circularDeps)
            }
        }

        recursionStack.remove(current)
        path.removeAt(path.size - 1)
    }

    /**
     * 打印依赖关系统计
     */
    fun printDependencyStats() {
        Logger.info("=== 依赖关系统计 ===")
        val allTypes = getAllTypes()
        val rootTypes = getRootTypes()
        val leafTypes = allTypes.filter { isGrandChild(it) }

        Logger.info("  总类型数: ${allTypes.size}")
        Logger.info("  根类型数: ${rootTypes.size}")
        Logger.info("  叶子类型数: ${leafTypes.size}")
        Logger.info("  继承关系数: ${nodeExtendsList.size}")
        Logger.info("  最大继承深度: ${getMaxDepth()}")

        // 按继承深度分组
        val depthGroups = allTypes.groupBy { getInheritanceDepth(it) }
        Logger.info("  深度分布:")
        depthGroups.entries.sortedBy { it.key }.forEach { (depth, types) ->
            Logger.info("    深度 $depth: ${types.size} 个类型")
        }

        // 最复杂的类型（子类型最多）
        val mostComplex = allTypes.maxByOrNull { findAllChildrenByParent(it).size }
        if (mostComplex != null) {
            val childCount = findAllChildrenByParent(mostComplex).size
            Logger.info("  最复杂的类型: $mostComplex ($childCount 个子类型)")
        }
    }

    /**
     * 打印完整的依赖关系报告
     */
    fun printFullReport() {
        Logger.info("==========================================")
        Logger.info("继承图完整报告")
        Logger.info("==========================================")

        printDependencyStats()
        Logger.info("")
        printCircularDependencies()
        Logger.info("")
        printInheritanceTree()
        Logger.info("")
        printAllRelations()

        Logger.info("==========================================")
    }

    /**
     * 清空所有关系（用于测试）
     */
    fun clear() {
        child2ParentMap.clear()
        parent2ChildMap.clear()
        nodeExtendsList.clear()
    }
}
