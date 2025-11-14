package dev.yidafu.swc.generator.model.typescript

import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.util.Logger

typealias ClassDecl = KotlinDeclaration.ClassDecl

/**
 * Cached wrapper around InheritanceAnalyzer to avoid repeated expensive graph traversals
 * Provides thread-safe lazy initialization and memoization of inheritance queries
 */
class InheritanceAnalysisCache(
    private val analyzer: InheritanceAnalyzer,
    private val allInterfaces: List<ClassDecl>
) {

    // Cache for leaf nodes detection
    private val leafNodesCache: List<ClassDecl> by lazy {
        Logger.debug("构建叶子节点缓存...", 4)
        allInterfaces.filter { interfaceDecl ->
            val children = analyzer.findAllChildrenByParent(interfaceDecl.name)
            val isNodeDescendant = analyzer.isDescendantOf(interfaceDecl.name, "Node")
            isNodeDescendant && children.isEmpty()
        }
    }

    // Cache for parent-child relationships
    private val childrenCache = mutableMapOf<String, List<String>>()
    private val parentsCache = mutableMapOf<String, List<String>>()

    // Cache for descendant checks
    private val descendantCache = mutableMapOf<Pair<String, String>, Boolean>()

    /**
     * Get all leaf nodes (Node descendants with no children)
     */
    fun getLeafNodes(): List<ClassDecl> = leafNodesCache

    /**
     * Get children of a parent interface (cached)
     */
    fun getChildren(parentName: String): List<String> {
        return childrenCache.getOrPut(parentName) {
            analyzer.findAllChildrenByParent(parentName)
        }
    }

    /**
     * Get parents of a child interface (cached)
     */
    fun getParents(childName: String): List<String> {
        return parentsCache.getOrPut(childName) {
            analyzer.findAllParentsByChild(childName)
        }
    }

    /**
     * Check if an interface is a descendant of another (cached)
     */
    fun isDescendantOf(childName: String, parentName: String): Boolean {
        val key = childName to parentName
        return descendantCache.getOrPut(key) {
            analyzer.isDescendantOf(childName, parentName)
        }
    }

    /**
     * Check if an interface is a leaf node (Node descendant with no children)
     */
    fun isLeafNode(interfaceName: String): Boolean {
        val children = getChildren(interfaceName)
        val isNodeDescendant = isDescendantOf(interfaceName, "Node")
        return isNodeDescendant && children.isEmpty()
    }

    /**
     * Clear all caches (useful for testing or memory management)
     */
    fun clearCache() {
        childrenCache.clear()
        parentsCache.clear()
        descendantCache.clear()
        Logger.debug("继承分析缓存已清空", 4)
    }
}
