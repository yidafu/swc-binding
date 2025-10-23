package dev.yidafu.swc.generator.validator

import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.util.Logger

/**
 * 继承关系验证器
 * * 负责检测循环继承、验证继承关系、计算继承深度等
 */
object InheritanceValidator {

    /**
     * 验证结果
     */
    data class ValidationResult(
        val isValid: Boolean,
        val message: String? = null,
        val depth: Int = 0
    )

    /**
     * 检测循环继承
     * * @param graph 继承关系图
     * @return 检测到的循环路径列表
     */
    fun detectCycles(graph: ExtendRelationship): List<List<String>> {
        return detectCyclesInExtendRelationship(graph)
    }

    /**
     * 检测临时图中的循环
     */
    private fun detectCyclesInTempGraph(graph: TempInheritanceGraph): List<List<String>> {
        val cycles = mutableListOf<List<String>>()
        val visited = mutableSetOf<String>()
        val recursionStack = mutableSetOf<String>()

        val allTypes = graph.getAll()

        allTypes.forEach { type ->
            if (!visited.contains(type)) {
                detectCyclesDFS(type, graph, visited, recursionStack, mutableListOf(), cycles)
            }
        }

        return cycles
    }

    /**
     * 检测 ExtendRelationship 中的循环
     */
    private fun detectCyclesInExtendRelationship(graph: ExtendRelationship): List<List<String>> {
        val cycles = mutableListOf<List<String>>()
        val visited = mutableSetOf<String>()
        val recursionStack = mutableSetOf<String>()

        val allTypes = graph.getAll()

        allTypes.forEach { type ->
            if (!visited.contains(type)) {
                detectCyclesDFS(type, graph, visited, recursionStack, mutableListOf(), cycles)
            }
        }

        return cycles
    }

    /**
     * 深度优先搜索检测循环
     */
    private fun detectCyclesDFS(
        current: String,
        graph: Any,
        visited: MutableSet<String>,
        recursionStack: MutableSet<String>,
        path: MutableList<String>,
        cycles: MutableList<List<String>>
    ) {
        visited.add(current)
        recursionStack.add(current)
        path.add(current)

        val parents = when (graph) {
            is ExtendRelationship -> graph.findParentsByChild(current)
            is TempInheritanceGraph -> graph.findParentsByChild(current)
            else -> emptyList()
        }
        parents.forEach { parent ->
            if (!visited.contains(parent)) {
                detectCyclesDFS(parent, graph, visited, recursionStack, path, cycles)
            } else if (recursionStack.contains(parent)) {
                // 发现循环
                val cycleStart = path.indexOf(parent)
                val cycle = path.subList(cycleStart, path.size) + parent
                cycles.add(cycle)
                Logger.warn("检测到循环继承: ${cycle.joinToString(" -> ")}")
            }
        }

        recursionStack.remove(current)
        path.removeAt(path.size - 1)
    }

    /**
     * 验证继承关系
     * * @param child 子类型
     * @param parent 父类型
     * @return 验证结果
     */
    fun validateInheritance(child: String, parent: String): ValidationResult {
        if (child == parent) {
            return ValidationResult(
                isValid = false,
                message = "类型不能继承自己: $child"
            )
        }

        // 检查是否会形成循环
        val tempGraph = createTempGraph()
        tempGraph.addRelation(parent, child)

        val cycles = detectCyclesInTempGraph(tempGraph)
        if (cycles.isNotEmpty()) {
            return ValidationResult(
                isValid = false,
                message = "添加此继承关系会形成循环: ${cycles.first().joinToString(" -> ")}"
            )
        }

        return ValidationResult(
            isValid = true,
            message = "继承关系有效"
        )
    }

    /**
     * 获取继承深度
     * * @param child 子类型
     * @return 继承深度
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

        val parents = ExtendRelationship.findParentsByChild(current)
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
     * 获取所有根类型（没有父类型的类型）
     */
    fun getRootTypes(graph: ExtendRelationship): List<String> {
        val allTypes = graph.getAll()
        val parentTypes = graph.getAllParents()
        return allTypes.filter { !parentTypes.contains(it) }
    }

    /**
     * 获取最大继承深度
     */
    fun getMaxDepth(graph: ExtendRelationship): Int {
        val allTypes = graph.getAll()
        return allTypes.maxOfOrNull { getInheritanceDepth(it) } ?: 0
    }

    /**
     * 创建临时图用于验证
     */
    private fun createTempGraph(): TempInheritanceGraph {
        return TempInheritanceGraph()
    }

    /**
     * 临时继承图实现
     */
    private class TempInheritanceGraph {
        private val relations = mutableListOf<Pair<String, String>>()

        fun addRelation(parent: String, child: String) {
            relations.add(parent to child)
        }

        fun findParentsByChild(child: String): List<String> {
            return relations.filter { it.second == child }.map { it.first }
        }

        fun getAll(): List<String> {
            return relations.flatMap { listOf(it.first, it.second) }.distinct()
        }

        fun getAllParents(): List<String> {
            return relations.map { it.first }.distinct()
        }
    }
}
