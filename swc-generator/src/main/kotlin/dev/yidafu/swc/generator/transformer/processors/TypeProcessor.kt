package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.parser.AstNode

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
    private val classDeclMap: MutableMap<String, KotlinDeclaration.ClassDecl>,
    private val propertiesMap: MutableMap<String, List<KotlinDeclaration.PropertyDecl>> = mutableMapOf()
) {
    fun addClassDecl(classDecl: KotlinDeclaration.ClassDecl) {
        classDeclMap[classDecl.name] = classDecl
    }

    fun addProperties(className: String, properties: List<KotlinDeclaration.PropertyDecl>) {
        propertiesMap[className] = properties
    }

    fun getAllPropertiesMap(): Map<String, List<KotlinDeclaration.PropertyDecl>> = propertiesMap

    fun getClassDecl(name: String): KotlinDeclaration.ClassDecl? = classDeclMap[name]

    fun getAllClassDecls(): List<KotlinDeclaration.ClassDecl> = classDeclMap.values.toList()
}
