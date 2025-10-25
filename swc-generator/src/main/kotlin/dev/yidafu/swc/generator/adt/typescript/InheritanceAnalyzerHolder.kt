package dev.yidafu.swc.generator.adt.typescript

/**
 * 全局继承分析器持有者
 * 用于在 TypeTransformer 中初始化继承关系分析结果，并在代码生成器中共享使用
 */
object InheritanceAnalyzerHolder {
    private var analyzer: InheritanceAnalyzer? = null
    
    /**
     * 初始化继承分析器
     * @param declarations TypeScript 声明列表
     */
    fun initialize(declarations: List<TypeScriptDeclaration>) {
        analyzer = InheritanceAnalyzer(declarations)
    }
    
    /**
     * 获取继承分析器实例
     * @return InheritanceAnalyzer 实例
     * @throws IllegalStateException 如果分析器未初始化
     */
    fun get(): InheritanceAnalyzer {
        return analyzer ?: throw IllegalStateException("InheritanceAnalyzer not initialized. Call initialize() first.")
    }
    
    /**
     * 清理分析器实例
     */
    fun clear() {
        analyzer = null
    }
    
    /**
     * 检查分析器是否已初始化
     */
    fun isInitialized(): Boolean {
        return analyzer != null
    }
}
