package dev.yidafu.swc.generator.builder

import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.relation.InheritanceGraph
import dev.yidafu.swc.generator.extractor.InterfaceExtractor
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.parser.TsAstVisitor
import dev.yidafu.swc.generator.parser.getInterfaceName
import dev.yidafu.swc.generator.util.Logger

/**
 * 继承关系构建器
 * * 负责从 TypeScript 接口定义中构建继承关系图
 */
class InheritanceBuilder(
    private val visitor: TsAstVisitor,
    private val config: SwcGeneratorConfig
) {

    private val interfaceExtractor = InterfaceExtractor(visitor)

    /**
     * 构建继承关系图
     * * @param interfaces 接口定义列表
     * @return 继承关系图
     */
    fun buildInheritanceGraph(interfaces: List<AstNode>): InheritanceGraph {
        val graph = InheritanceGraph()
        var relationCount = 0

        Logger.debug("开始构建继承关系图...")
        Logger.debug("接口数量: ${interfaces.size}")

        interfaces.forEach { interfaceDecl ->
            val interfaceName = interfaceDecl.getInterfaceName()
            if (interfaceName != null) {
                val parents = extractParents(interfaceDecl)
                Logger.logIf(parents.isNotEmpty(), "接口 $interfaceName 继承: ${parents.map { it.name }.joinToString(", ")}")

                parents.forEach { parent ->
                    graph.addRelation(parent.name, interfaceName)
                    relationCount++
                }
            }
        }

        Logger.debug("继承关系图构建完成:")
        Logger.debug("  - 总类型数: ${graph.getAllTypes().size}")
        Logger.debug("  - 根类型数: ${graph.getRootTypes().size}")
        Logger.debug("  - 最大继承深度: ${graph.getMaxDepth()}")
        Logger.debug("  - 继承关系数: $relationCount")

        return graph
    }

    /**
     * 提取接口的父类型信息
     * * @param interfaceDecl 接口定义
     * @return 父类型信息列表
     */
    fun extractParents(interfaceDecl: AstNode): List<ParentInfo> {
        val interfaceName = interfaceDecl.getInterfaceName() ?: return emptyList()

        val genericParents = interfaceExtractor.extractGenericExtends(interfaceDecl)
        return genericParents.map { genericParent ->
            ParentInfo(
                name = genericParent.name,
                typeArguments = genericParent.typeArguments,
                hasTypeArguments = genericParent.hasTypeArguments(),
                typeArgumentCount = genericParent.getTypeArgumentCount()
            )
        }
    }

    /**
     * 验证继承关系
     * * @param graph 继承关系图
     * @return 验证结果
     */
    fun validateInheritanceGraph(graph: InheritanceGraph): ValidationReport {
        val report = ValidationReport()

        // 检查循环继承
        val cycles = detectCycles(graph)
        if (cycles.isNotEmpty()) {
            report.addError("检测到循环继承: ${cycles.first().joinToString(" -> ")}")
            cycles.forEach { cycle ->
                report.addWarning("循环路径: ${cycle.joinToString(" -> ")}")
            }
        }

        // 检查继承深度
        val maxDepth = graph.getMaxDepth()
        if (maxDepth > 10) {
            report.addWarning("继承深度过深: $maxDepth，可能影响性能")
        }

        // 检查根类型
        val rootTypes = graph.getRootTypes()
        if (rootTypes.isEmpty()) {
            report.addWarning("未找到根类型")
        } else if (rootTypes.size > 5) {
            report.addWarning("根类型过多: ${rootTypes.size}，可能存在设计问题")
        }

        // 生成统计信息
        report.addInfo("继承图统计:")
        report.addInfo("  - 总类型数: ${graph.getAllTypes().size}")
        report.addInfo("  - 根类型数: ${rootTypes.size}")
        report.addInfo("  - 最大深度: $maxDepth")

        return report
    }

    /**
     * 检测循环继承
     */
    private fun detectCycles(graph: InheritanceGraph): List<List<String>> {
        val cycles = mutableListOf<List<String>>()
        val visited = mutableSetOf<String>()
        val recursionStack = mutableSetOf<String>()

        val allTypes = graph.getAllTypes()

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
        graph: InheritanceGraph,
        visited: MutableSet<String>,
        recursionStack: MutableSet<String>,
        path: MutableList<String>,
        cycles: MutableList<List<String>>
    ) {
        visited.add(current)
        recursionStack.add(current)
        path.add(current)

        val parents = graph.findParentsByChild(current)
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
     * 生成继承关系报告
     */
    fun generateInheritanceReport(graph: InheritanceGraph): String {
        val report = StringBuilder()

        report.appendLine("继承关系报告")
        report.appendLine("=".repeat(50))

        // 基本统计
        report.appendLine(graph.generateStats())

        // 根类型列表
        val rootTypes = graph.getRootTypes()
        if (rootTypes.isNotEmpty()) {
            report.appendLine("\n根类型:")
            rootTypes.forEach { root ->
                report.appendLine("  - $root")
            }
        }

        // 深度分布
        val depthDistribution = graph.getAllTypes().groupBy { graph.getInheritanceDepth(it) }
        report.appendLine("\n深度分布:")
        depthDistribution.entries.sortedBy { it.key }.forEach { (depth, types) ->
            report.appendLine("  深度 $depth: ${types.size} 个类型")
            if (types.size <= 10) {
                report.appendLine("    ${types.joinToString(", ")}")
            }
        }

        // 叶子类型
        val leafTypes = graph.getAllTypes().filter { graph.isGrandChild(it) }
        if (leafTypes.isNotEmpty()) {
            report.appendLine("\n叶子类型 (${leafTypes.size} 个):")
            leafTypes.take(20).forEach { leaf ->
                report.appendLine("  - $leaf")
            }
            if (leafTypes.size > 20) {
                report.appendLine("  ... 还有 ${leafTypes.size - 20} 个")
            }
        }

        return report.toString()
    }
}

/**
 * 父类型信息
 */
data class ParentInfo(
    val name: String,
    val typeArguments: List<dev.yidafu.swc.generator.adt.kotlin.KotlinType> = emptyList(),
    val hasTypeArguments: Boolean = false,
    val typeArgumentCount: Int = 0
) {
    /**
     * 转换为字符串表示
     */
    fun toTypeString(): String {
        return if (typeArguments.isEmpty()) {
            name
        } else {
            "$name<${typeArguments.joinToString(", ") { it.toTypeString() }}>"
        }
    }
}

/**
 * 验证报告
 */
class ValidationReport {
    private val errors = mutableListOf<String>()
    private val warnings = mutableListOf<String>()
    private val infos = mutableListOf<String>()

    fun addError(message: String) = errors.add(message)
    fun addWarning(message: String) = warnings.add(message)
    fun addInfo(message: String) = infos.add(message)

    fun hasErrors(): Boolean = errors.isNotEmpty()
    fun hasWarnings(): Boolean = warnings.isNotEmpty()

    fun getErrors(): List<String> = errors.toList()
    fun getWarnings(): List<String> = warnings.toList()
    fun getInfos(): List<String> = infos.toList()

    fun generateReport(): String {
        val report = StringBuilder()

        if (errors.isNotEmpty()) {
            report.appendLine("错误:")
            errors.forEach { error ->
                report.appendLine("  ❌ $error")
            }
        }

        if (warnings.isNotEmpty()) {
            report.appendLine("警告:")
            warnings.forEach { warning ->
                report.appendLine("  ⚠️ $warning")
            }
        }

        if (infos.isNotEmpty()) {
            report.appendLine("信息:")
            infos.forEach { info ->
                report.appendLine("  ℹ️ $info")
            }
        }

        return report.toString()
    }
}
