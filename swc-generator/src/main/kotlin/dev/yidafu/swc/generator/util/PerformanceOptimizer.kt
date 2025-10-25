package dev.yidafu.swc.generator.util

import java.util.concurrent.ConcurrentHashMap

/**
 * 性能优化工具类
 * 提供缓存管理、性能监控等功能
 */
object PerformanceOptimizer {

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

    private val cacheStats = ConcurrentHashMap<String, MutableCacheStats>()

    /**
     * 可变的缓存统计信息
     */
    private data class MutableCacheStats(
        var hitCount: Int = 0,
        var missCount: Int = 0
    )

    /**
     * 带统计的缓存
     */
    class CachedMap<K, V>(
        private val cacheName: String,
        private val computeFunction: (K) -> V
    ) : MutableMap<K, V> by ConcurrentHashMap<K, V>() {

        override fun get(key: K): V? {
            val stats = cacheStats.getOrPut(cacheName) { MutableCacheStats() }

            return if (containsKey(key)) {
                stats.hitCount++
                this[key]
            } else {
                stats.missCount++
                val value = computeFunction(key)
                put(key, value)
                value
            }
        }

        fun getOrPut(key: K, defaultValue: () -> V): V {
            val stats = cacheStats.getOrPut(cacheName) { MutableCacheStats() }

            return if (containsKey(key)) {
                stats.hitCount++
                this[key]!!
            } else {
                stats.missCount++
                val value = defaultValue()
                put(key, value)
                value
            }
        }

        fun getStats(): CacheStats {
            val stats = cacheStats[cacheName] ?: MutableCacheStats()
            return CacheStats(
                cacheName = cacheName,
                hitCount = stats.hitCount,
                missCount = stats.missCount,
                totalSize = size
            )
        }
    }

    /**
     * 创建带统计的缓存
     */
    fun <K, V> createCache(cacheName: String, computeFunction: (K) -> V): CachedMap<K, V> {
        return CachedMap(cacheName, computeFunction)
    }

    /**
     * 获取所有缓存统计信息
     */
    fun getAllCacheStats(): List<CacheStats> {
        return cacheStats.keys.mapNotNull { cacheName ->
            // 这里需要访问实际的缓存实例来获取大小
            // 由于设计限制，我们只能返回命中和未命中统计
            val stats = cacheStats[cacheName] ?: return@mapNotNull null
            CacheStats(
                cacheName = cacheName,
                hitCount = stats.hitCount,
                missCount = stats.missCount,
                totalSize = 0 // 无法获取实际大小
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
     * 性能计时器
     */
    inline fun <T> measureTime(operationName: String, operation: () -> T): T {
        val startTime = System.currentTimeMillis()
        return try {
            operation()
        } finally {
            val duration = System.currentTimeMillis() - startTime
            Logger.debug("$operationName 耗时: ${duration}ms", 6)
        }
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
}
