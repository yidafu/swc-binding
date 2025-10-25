package dev.yidafu.swc.generator.extensions

import dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzer
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalysisCache

typealias ClassDecl = KotlinDeclaration.ClassDecl
typealias PropertyDecl = KotlinDeclaration.PropertyDecl

/**
 * Extension functions for ClassDecl to reduce boilerplate and improve readability
 */

/**
 * Check if this class declaration is a descendant of Node
 */
fun ClassDecl.isNodeDescendant(analyzer: InheritanceAnalyzer): Boolean {
    return analyzer.isDescendantOf(this.name, "Node")
}

/**
 * Check if this class declaration is a descendant of Node (cached version)
 */
fun ClassDecl.isNodeDescendant(cache: InheritanceAnalysisCache): Boolean {
    return cache.isDescendantOf(this.name, "Node")
}

/**
 * Check if this class declaration is a leaf node (Node descendant with no children)
 */
fun ClassDecl.isLeafNode(analyzer: InheritanceAnalyzer): Boolean {
    val children = analyzer.findAllChildrenByParent(this.name)
    val isNodeDescendant = analyzer.isDescendantOf(this.name, "Node")
    return isNodeDescendant && children.isEmpty()
}

/**
 * Check if this class declaration is a leaf node (cached version)
 */
fun ClassDecl.isLeafNode(cache: InheritanceAnalysisCache): Boolean {
    return cache.isLeafNode(this.name)
}

/**
 * Get all properties including inherited ones (with memoization)
 */
fun ClassDecl.getAllProperties(
    analyzer: InheritanceAnalyzer,
    allDecls: List<ClassDecl>,
    propertyCache: MutableMap<String, List<PropertyDecl>> = mutableMapOf()
): List<PropertyDecl> {
    // Check cache first
    propertyCache[this.name]?.let { return it }
    
    val allProperties = mutableSetOf<PropertyDecl>()
    
    // Add current interface properties
    allProperties.addAll(this.properties)
    
    // Recursively add parent interface properties
    val parentInterfaces = analyzer.findAllParentsByChild(this.name)
    parentInterfaces.forEach { parentName ->
        val parentInterface = allDecls.find { it.name == parentName }
        if (parentInterface != null) {
            allProperties.addAll(parentInterface.getAllProperties(analyzer, allDecls, propertyCache))
        }
    }
    
    val result = allProperties.toList()
    propertyCache[this.name] = result
    return result
}

/**
 * Get all properties including inherited ones (cached version)
 */
fun ClassDecl.getAllProperties(
    cache: InheritanceAnalysisCache,
    allDecls: List<ClassDecl>,
    propertyCache: MutableMap<String, List<PropertyDecl>> = mutableMapOf()
): List<PropertyDecl> {
    // Check cache first
    propertyCache[this.name]?.let { return it }
    
    val allProperties = mutableSetOf<PropertyDecl>()
    
    // Add current interface properties
    allProperties.addAll(this.properties)
    
    // Recursively add parent interface properties
    val parentInterfaces = cache.getParents(this.name)
    parentInterfaces.forEach { parentName ->
        val parentInterface = allDecls.find { it.name == parentName }
        if (parentInterface != null) {
            allProperties.addAll(parentInterface.getAllProperties(cache, allDecls, propertyCache))
        }
    }
    
    val result = allProperties.toList()
    propertyCache[this.name] = result
    return result
}
