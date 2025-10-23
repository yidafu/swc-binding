package dev.yidafu.swc.generator.monitor

import dev.yidafu.swc.generator.util.Logger
import kotlin.system.measureTimeMillis

/**
 * 性能监控器
 * * 监控关键操作的耗时，帮助识别性能瓶颈
 */
object PerformanceMonitor {

    private val measurements = mutableMapOf<String, MutableList<Long>>()
    private val operationCounts = mutableMapOf<String, Int>()
    private val totalTimes = mutableMapOf<String, Long>()

    /**
     * 测量操作耗时
     * * @param operation 操作名称
     * @param block 要测量的代码块
     * @return 操作结果
     */
    fun <T> measure(operation: String, block: () -> T): T {
        val result: T
        val time = measureTimeMillis {
            result = block()
        }

        recordMeasurement(operation, time)
        return result
    }

    /**
     * 测量操作耗时（带日志）
     */
    fun <T> measureWithLog(operation: String, block: () -> T): T {
        Logger.debug("开始执行: $operation")
        val result = measure(operation, block)
        Logger.debug("完成执行: $operation (${getLastMeasurement(operation)}ms)")
        return result
    }

    /**
     * 记录测量结果
     */
    fun recordMeasurement(operation: String, time: Long) {
        measurements.getOrPut(operation) { mutableListOf() }.add(time)
        operationCounts[operation] = (operationCounts[operation] ?: 0) + 1
        totalTimes[operation] = (totalTimes[operation] ?: 0) + time
    }

    /**
     * 获取最后一次测量结果
     */
    fun getLastMeasurement(operation: String): Long? {
        return measurements[operation]?.lastOrNull()
    }

    /**
     * 获取平均耗时
     */
    fun getAverageTime(operation: String): Double? {
        val times = measurements[operation] ?: return null
        return if (times.isNotEmpty()) times.average() else null
    }

    /**
     * 获取最小耗时
     */
    fun getMinTime(operation: String): Long? {
        return measurements[operation]?.minOrNull()
    }

    /**
     * 获取最大耗时
     */
    fun getMaxTime(operation: String): Long? {
        return measurements[operation]?.maxOrNull()
    }

    /**
     * 获取总耗时
     */
    fun getTotalTime(operation: String): Long {
        return totalTimes[operation] ?: 0
    }

    /**
     * 获取操作次数
     */
    fun getOperationCount(operation: String): Int {
        return operationCounts[operation] ?: 0
    }

    /**
     * 获取所有操作名称
     */
    fun getAllOperations(): List<String> {
        return measurements.keys.toList()
    }

    /**
     * 生成性能报告
     */
    fun generateReport(): String {
        val report = StringBuilder()

        report.appendLine("性能监控报告")
        report.appendLine("=".repeat(50))

        val operations = getAllOperations().sorted()

        if (operations.isEmpty()) {
            report.appendLine("没有记录任何性能数据")
            return report.toString()
        }

        // 总体统计
        val totalOperations = operationCounts.values.sum()
        val totalTime = totalTimes.values.sum()

        report.appendLine("总体统计:")
        report.appendLine("  - 总操作数: $totalOperations")
        report.appendLine("  - 总耗时: ${totalTime}ms")
        report.appendLine("  - 平均每次操作: ${if (totalOperations > 0) totalTime / totalOperations else 0}ms")
        report.appendLine()

        // 各操作详细统计
        report.appendLine("操作详细统计:")
        report.appendLine("操作名称".padEnd(30) + "次数".padEnd(8) + "总耗时(ms)".padEnd(12) + "平均(ms)".padEnd(10) + "最小(ms)".padEnd(10) + "最大(ms)")
        report.appendLine("-".repeat(80))

        operations.forEach { operation ->
            val count = getOperationCount(operation)
            val total = getTotalTime(operation)
            val avg = getAverageTime(operation) ?: 0.0
            val min = getMinTime(operation) ?: 0
            val max = getMaxTime(operation) ?: 0

            report.appendLine(
                operation.padEnd(30) +
                    count.toString().padEnd(8) +
                    total.toString().padEnd(12) +
                    String.format("%.2f", avg).padEnd(10) +
                    min.toString().padEnd(10) +
                    max.toString()
            )
        }

        // 性能警告
        val warnings = generateWarnings()
        if (warnings.isNotEmpty()) {
            report.appendLine("\n性能警告:")
            warnings.forEach { warning ->
                report.appendLine("  ⚠️ $warning")
            }
        }

        return report.toString()
    }

    /**
     * 生成性能警告
     */
    private fun generateWarnings(): List<String> {
        val warnings = mutableListOf<String>()

        getAllOperations().forEach { operation ->
            val avgTime = getAverageTime(operation) ?: 0.0
            val maxTime = getMaxTime(operation) ?: 0
            val count = getOperationCount(operation)

            when {
                avgTime > 1000 -> {
                    warnings.add("操作 '$operation' 平均耗时过长: ${String.format("%.2f", avgTime)}ms")
                }
                maxTime > 5000 -> {
                    warnings.add("操作 '$operation' 最大耗时过长: ${maxTime}ms")
                }
                count > 1000 -> {
                    warnings.add("操作 '$operation' 执行次数过多: $count 次")
                }
            }
        }

        return warnings
    }

    /**
     * 重置所有统计数据
     */
    fun reset() {
        measurements.clear()
        operationCounts.clear()
        totalTimes.clear()
        Logger.debug("性能监控数据已重置")
    }

    /**
     * 获取性能摘要
     */
    fun getSummary(): PerformanceSummary {
        val operations = getAllOperations()
        val totalOps = operationCounts.values.sum()
        val totalTime = totalTimes.values.sum()

        val topSlowOperations = operations
            .mapNotNull { op ->
                val avg = getAverageTime(op)
                if (avg != null) op to avg else null
            }
            .sortedByDescending { it.second }
            .take(5)

        return PerformanceSummary(
            totalOperations = totalOps,
            totalTimeMs = totalTime,
            averageTimePerOperation = if (totalOps > 0) totalTime / totalOps else 0,
            topSlowOperations = topSlowOperations,
            operationCount = operations.size
        )
    }
}

/**
 * 性能摘要
 */
data class PerformanceSummary(
    val totalOperations: Int,
    val totalTimeMs: Long,
    val averageTimePerOperation: Long,
    val topSlowOperations: List<Pair<String, Double>>,
    val operationCount: Int
) {
    fun toCompactString(): String {
        return "操作数: $totalOperations, 总耗时: ${totalTimeMs}ms, 平均: ${averageTimePerOperation}ms"
    }
}

/**
 * 性能监控作用域
 * * 用于自动测量代码块执行时间
 */
class PerformanceScope(private val operation: String) {
    private val startTime = System.currentTimeMillis()

    fun finish(): Long {
        val duration = System.currentTimeMillis() - startTime
        PerformanceMonitor.recordMeasurement(operation, duration)
        return duration
    }
}

/**
 * 创建性能监控作用域
 */
fun performanceScope(operation: String, block: (PerformanceScope) -> Unit) {
    val scope = PerformanceScope(operation)
    try {
        block(scope)
    } finally {
        scope.finish()
    }
}
