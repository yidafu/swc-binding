package dev.yidafu.swc.generator.util

import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration

/**
 * 调试工具类，统一管理调试代码
 */
object DebugUtils {
    private const val DEBUG_ENABLED = false // 设置为 true 启用调试输出

    private val debugTypes = setOf("ForOfStatement", "ComputedPropName")

    /**
     * 检查类型是否在调试列表中
     */
    fun isDebugType(name: String): Boolean {
        return DEBUG_ENABLED && NameUtils.clean(name) in debugTypes
    }

    /**
     * 查找调试类型
     */
    fun findDebugTypes(classDecls: List<KotlinDeclaration.ClassDecl>): Map<String, KotlinDeclaration.ClassDecl> {
        if (!DEBUG_ENABLED) return emptyMap()
        return classDecls.filter { isDebugType(it.name) }
            .associateBy { NameUtils.clean(it.name) }
    }

    /**
     * 查找单个调试类型
     */
    fun findDebugType(classDecls: List<KotlinDeclaration.ClassDecl>, typeName: String): KotlinDeclaration.ClassDecl? {
        if (!DEBUG_ENABLED) return null
        return classDecls.find { NameUtils.clean(it.name) == typeName }
    }

    /**
     * 输出调试信息
     */
    fun debug(message: String) {
        if (DEBUG_ENABLED) {
            println("  [DEBUG] $message")
        }
    }

    /**
     * 输出调试信息（带上下文）
     */
    fun debug(message: String, context: String) {
        if (DEBUG_ENABLED) {
            println("  [DEBUG] $context: $message")
        }
    }
}
