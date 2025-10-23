package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration

/**
 * 类型处理器接口
 */
interface TypeProcessor {
    /**
     * 判断是否可以处理该类型别名
     */
    fun canProcess(typeAlias: AstNode): Boolean
    
    /**
     * 处理类型别名（新方式，返回 ADT 声明）
     */
    fun process(typeAlias: AstNode, context: TransformContext): KotlinDeclaration? {
        // 默认实现调用旧方法，保持向后兼容
        processLegacy(typeAlias, context)
        return null
    }
    
    /**
     * 处理类型别名（旧方式，直接修改 context）
     */
    @Deprecated("使用 process(AstNode, TransformContext): KotlinDeclaration? 替代")
    fun processLegacy(typeAlias: AstNode, context: TransformContext)
}

/**
 * 转换上下文
 */
data class TransformContext(
    private val kotlinClassMap: MutableMap<String, dev.yidafu.swc.generator.core.model.KotlinClass>,
    private val propertiesMap: MutableMap<String, List<dev.yidafu.swc.generator.core.model.KotlinProperty>> = mutableMapOf()
) {
    fun addKotlinClass(klass: dev.yidafu.swc.generator.core.model.KotlinClass) {
        kotlinClassMap[klass.klassName] = klass
    }
    
    fun addProperties(className: String, properties: List<dev.yidafu.swc.generator.core.model.KotlinProperty>) {
        propertiesMap[className] = properties
    }
    
    fun getAllPropertiesMap(): Map<String, List<dev.yidafu.swc.generator.core.model.KotlinProperty>> = propertiesMap
    
    fun getKotlinClass(name: String): dev.yidafu.swc.generator.core.model.KotlinClass? = kotlinClassMap[name]
    
    fun getAllKotlinClasses(): List<dev.yidafu.swc.generator.core.model.KotlinClass> = kotlinClassMap.values.toList()
}
