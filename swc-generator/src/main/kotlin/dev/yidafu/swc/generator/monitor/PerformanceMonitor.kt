package dev.yidafu.swc.generator.monitor

import dev.yidafu.swc.generator.util.Logger
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

/**
 * 性能监控器
 * 统一管理性能监控、缓存统计和性能优化功能
 */
object PerformanceMonitor {

    private val measurements = mutableMapOf<String, MutableList<Long>>()
    private val operationCounts = mutableMapOf<String, Int>()
    private val totalTimes = mutableMapOf<String, Long>()

    // 缓存统计
    private val cacheStats = ConcurrentHashMap<String, MutableCacheStats>()

    /**
     * 可变的缓存统计信息
     */
    private data class MutableCacheStats(
        var hitCount: Int = 0,
        var missCount: Int = 0
    )

    /**
     * 缓存统计信息
     */
    data class CacheStats(
        val cacheName: String,
        val hitCount: Int,
        val missCount: Int,
        val totalSize: Int
    ) {
        val hitRate: Double get() = if (hitCount + missCount > 0) hitCount.toDouble() / (hitCount + missCount) else 0.0
    }

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
        cacheStats.clear()
        Logger.debug("性能监控数据已重置")
    }

    /**
     * 性能计时器（兼容 PerformanceOptimizer.measureTime）
     * 测量操作耗时并记录到性能监控中
     */
    inline fun <T> measureTime(operationName: String, noinline operation: () -> T): T {
        return measure(operationName, operation)
    }

    /**
     * 批量处理优化
     */
    fun <T, R> processBatch(
        items: List<T>,
        batchSize: Int = 100,
        processor: (List<T>) -> List<R>
    ): List<R> {
        return items.chunked(batchSize).flatMap { batch ->
            measureTime("批量处理 ${batch.size} 项") {
                processor(batch)
            }
        }
    }

    /**
     * 记录缓存命中
     */
    fun recordCacheHit(cacheName: String) {
        cacheStats.getOrPut(cacheName) { MutableCacheStats() }.hitCount++
    }

    /**
     * 记录缓存未命中
     */
    fun recordCacheMiss(cacheName: String) {
        cacheStats.getOrPut(cacheName) { MutableCacheStats() }.missCount++
    }

    /**
     * 获取缓存统计信息
     */
    fun getCacheStats(cacheName: String, totalSize: Int = 0): CacheStats? {
        val stats = cacheStats[cacheName] ?: return null
        return CacheStats(
            cacheName = cacheName,
            hitCount = stats.hitCount,
            missCount = stats.missCount,
            totalSize = totalSize
        )
    }

    /**
     * 获取所有缓存统计信息
     */
    fun getAllCacheStats(): List<CacheStats> {
        return cacheStats.keys.mapNotNull { cacheName ->
            val stats = cacheStats[cacheName] ?: return@mapNotNull null
            CacheStats(
                cacheName = cacheName,
                hitCount = stats.hitCount,
                missCount = stats.missCount,
                totalSize = 0 // 需要从实际缓存实例获取
            )
        }
    }

    /**
     * 打印缓存统计信息
     */
    fun printCacheStats() {
        val stats = getAllCacheStats()
        if (stats.isNotEmpty()) {
            Logger.info("=== 缓存性能统计 ===")
            stats.forEach { stat ->
                Logger.info("${stat.cacheName}: 命中率 ${(stat.hitRate * 100).toInt()}% (${stat.hitCount}/${stat.hitCount + stat.missCount}), 大小: ${stat.totalSize}")
            }
        }
    }

    /**
     * 清理所有缓存统计
     */
    fun clearCacheStats() {
        cacheStats.clear()
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
