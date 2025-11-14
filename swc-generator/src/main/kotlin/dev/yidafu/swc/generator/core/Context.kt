package dev.yidafu.swc.generator.core

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration

/**
 * 处理上下文
 * 在管道各阶段之间传递共享数据和状态
 */
class ProcessingContext(
    val configuration: Configuration,
    private val metadata: MutableMap<String, Any> = mutableMapOf()
) {
    
    // 类型声明存储
    private val _typeScriptDeclarations = mutableListOf<TypeScriptDeclaration>()
    private val _kotlinDeclarations = mutableListOf<KotlinDeclaration>()
    
    // 分析结果存储
    private val _analysisResults = mutableMapOf<String, Any>()
    
    // 缓存存储
    private val _cache = mutableMapOf<String, Any>()
    
    /**
     * TypeScript 声明列表
     */
    val typeScriptDeclarations: List<TypeScriptDeclaration>
        get() = _typeScriptDeclarations.toList()
    
    /**
     * Kotlin 声明列表
     */
    val kotlinDeclarations: List<KotlinDeclaration>
        get() = _kotlinDeclarations.toList()
    
    /**
     * 添加 TypeScript 声明
     */
    fun addTypeScriptDeclaration(declaration: TypeScriptDeclaration) {
        _typeScriptDeclarations.add(declaration)
    }
    
    /**
     * 添加多个 TypeScript 声明
     */
    fun addTypeScriptDeclarations(declarations: List<TypeScriptDeclaration>) {
        _typeScriptDeclarations.addAll(declarations)
    }
    
    /**
     * 添加 Kotlin 声明
     */
    fun addKotlinDeclaration(declaration: KotlinDeclaration) {
        _kotlinDeclarations.add(declaration)
    }
    
    /**
     * 添加多个 Kotlin 声明
     */
    fun addKotlinDeclarations(declarations: List<KotlinDeclaration>) {
        _kotlinDeclarations.addAll(declarations)
    }
    
    /**
     * 更新 Kotlin 声明
     */
    fun updateKotlinDeclaration(declaration: KotlinDeclaration) {
        val index = _kotlinDeclarations.indexOfFirst { 
            when (declaration) {
                is KotlinDeclaration.ClassDecl -> it is KotlinDeclaration.ClassDecl && it.name == declaration.name
                is KotlinDeclaration.TypeAliasDecl -> it is KotlinDeclaration.TypeAliasDecl && it.name == declaration.name
                else -> false
            }
        }
        
        if (index >= 0) {
            _kotlinDeclarations[index] = declaration
        } else {
            _kotlinDeclarations.add(declaration)
        }
    }
    
    /**
     * 设置分析结果
     */
    fun setAnalysisResult(key: String, result: Any) {
        _analysisResults[key] = result
    }
    
    /**
     * 获取分析结果
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getAnalysisResult(key: String): T? = _analysisResults[key] as? T
    
    /**
     * 设置缓存
     */
    fun setCache(key: String, value: Any) {
        _cache[key] = value
    }
    
    /**
     * 获取缓存
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getCache(key: String): T? = _cache[key] as? T
    
    /**
     * 获取或计算缓存值
     */
    fun <T> getOrComputeCache(key: String, compute: () -> T): T {
        @Suppress("UNCHECKED_CAST")
        return _cache.getOrPut(key) { compute() as Any } as T
    }
    
    /**
     * 设置元数据
     */
    fun setMetadata(key: String, value: Any) {
        metadata[key] = value
    }
    
    /**
     * 获取元数据
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getMetadata(key: String): T? = metadata[key] as? T
    
    /**
     * 清空所有数据
     */
    fun clear() {
        _typeScriptDeclarations.clear()
        _kotlinDeclarations.clear()
        _analysisResults.clear()
        _cache.clear()
        metadata.clear()
    }
    
    /**
     * 获取统计信息
     */
    fun getStats(): Map<String, Any> = mapOf(
        "typeScriptDeclarations" to _typeScriptDeclarations.size,
        "kotlinDeclarations" to _kotlinDeclarations.size,
        "analysisResults" to _analysisResults.size,
        "cacheEntries" to _cache.size,
        "metadataEntries" to metadata.size
    )
}
