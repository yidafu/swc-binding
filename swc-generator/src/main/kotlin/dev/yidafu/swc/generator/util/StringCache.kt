package dev.yidafu.swc.generator.util

import java.util.concurrent.ConcurrentHashMap

/**
 * 字符串缓存工具类
 * * 用于缓存频繁生成的字符串，提升性能
 */
object StringCache {
    private val cache = ConcurrentHashMap<String, String>()

    /**
     * 获取缓存的字符串，如果不存在则使用 provider 生成并缓存
     */
    fun getCached(key: String, provider: () -> String): String {
        return cache.getOrPut(key, provider)
    }

    /**
     * 直接缓存字符串
     */
    fun put(key: String, value: String) {
        cache[key] = value
    }

    /**
     * 获取缓存的字符串，如果不存在返回 null
     */
    fun get(key: String): String? {
        return cache[key]
    }

    /**
     * 清空缓存
     */
    fun clear() {
        cache.clear()
    }

    /**
     * 获取缓存大小
     */
    fun size(): Int = cache.size

    /**
     * 检查是否包含键
     */
    fun containsKey(key: String): Boolean = cache.containsKey(key)
}
