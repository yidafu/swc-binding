package dev.yidafu.swc.generator.util

import dev.yidafu.swc.generator.monitor.PerformanceMonitor
import java.util.concurrent.ConcurrentHashMap

/**
 * 统一的缓存管理器
 * 管理所有类型转换相关的缓存，提供统一的缓存统计和清理接口
 */
object CacheManager {

    /**
     * 类型名称缓存（String -> TypeName）
     */
    private val typeNameCache = ConcurrentHashMap<String, Any>()

    /**
     * Kotlin 类型缓存（String -> KotlinType）
     */
    private val kotlinTypeCache = ConcurrentHashMap<String, Any>()

    /**
     * 注解缓存（String -> AnnotationSpec）
     */
    private val annotationCache = ConcurrentHashMap<String, Any?>()

    /**
     * 缓存名称常量
     */
    private const val CACHE_TYPE_NAME = "TypeName"
    private const val CACHE_KOTLIN_TYPE = "KotlinType"
    private const val CACHE_ANNOTATION = "Annotation"

    /**
     * 获取或创建类型名称缓存项
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getOrPutTypeName(key: String, defaultValue: () -> T): T {
        return if (typeNameCache.containsKey(key)) {
            PerformanceMonitor.recordCacheHit(CACHE_TYPE_NAME)
            typeNameCache[key] as T
        } else {
            PerformanceMonitor.recordCacheMiss(CACHE_TYPE_NAME)
            val value = defaultValue()
            typeNameCache[key] = value as Any
            value
        }
    }

    /**
     * 获取或创建 Kotlin 类型缓存项
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getOrPutKotlinType(key: String, defaultValue: () -> T): T {
        return if (kotlinTypeCache.containsKey(key)) {
            PerformanceMonitor.recordCacheHit(CACHE_KOTLIN_TYPE)
            kotlinTypeCache[key] as T
        } else {
            PerformanceMonitor.recordCacheMiss(CACHE_KOTLIN_TYPE)
            val value = defaultValue()
            kotlinTypeCache[key] = value as Any
            value
        }
    }

    /**
     * 获取或创建注解缓存项
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getOrPutAnnotation(key: String, defaultValue: () -> T?): T? {
        return if (annotationCache.containsKey(key)) {
            PerformanceMonitor.recordCacheHit(CACHE_ANNOTATION)
            annotationCache[key] as? T
        } else {
            PerformanceMonitor.recordCacheMiss(CACHE_ANNOTATION)
            val value = defaultValue()
            annotationCache[key] = value as? Any
            value
        }
    }

    /**
     * 获取所有缓存统计信息
     */
    fun getAllCacheStats(): List<PerformanceMonitor.CacheStats> {
        return listOf(
            PerformanceMonitor.getCacheStats(CACHE_TYPE_NAME, typeNameCache.size) ?: PerformanceMonitor.CacheStats(CACHE_TYPE_NAME, 0, 0, typeNameCache.size),
            PerformanceMonitor.getCacheStats(CACHE_KOTLIN_TYPE, kotlinTypeCache.size) ?: PerformanceMonitor.CacheStats(CACHE_KOTLIN_TYPE, 0, 0, kotlinTypeCache.size),
            PerformanceMonitor.getCacheStats(CACHE_ANNOTATION, annotationCache.size) ?: PerformanceMonitor.CacheStats(CACHE_ANNOTATION, 0, 0, annotationCache.size)
        )
    }

    /**
     * 清理所有缓存
     */
    fun clearAll() {
        typeNameCache.clear()
        kotlinTypeCache.clear()
        annotationCache.clear()
        PerformanceMonitor.clearCacheStats()
    }

    /**
     * 清理类型名称缓存
     */
    fun clearTypeNameCache() {
        typeNameCache.clear()
    }

    /**
     * 清理 Kotlin 类型缓存
     */
    fun clearKotlinTypeCache() {
        kotlinTypeCache.clear()
    }

    /**
     * 清理注解缓存
     */
    fun clearAnnotationCache() {
        annotationCache.clear()
    }

    /**
     * 打印所有缓存统计信息
     */
    fun printStats() {
        val stats = getAllCacheStats()
        if (stats.isNotEmpty()) {
            Logger.info("=== 缓存统计信息 ===")
            stats.forEach { stat ->
                Logger.info("${stat.cacheName}: 命中率 ${(stat.hitRate * 100).toInt()}% (${stat.hitCount}/${stat.hitCount + stat.missCount}), 大小: ${stat.totalSize}")
            }
        }
    }
}
