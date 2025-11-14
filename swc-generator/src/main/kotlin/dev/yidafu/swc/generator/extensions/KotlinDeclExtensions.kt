package dev.yidafu.swc.generator.extensions

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.typescript.InheritanceAnalysisCache
import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer

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
 * Check if this class declaration is a leaf node (interface with no children)
 * 修复：移除 Node 后代的限制，为所有没有子类的接口生成实现类
 */
fun ClassDecl.isLeafNode(analyzer: InheritanceAnalyzer): Boolean {
    val children = analyzer.findAllChildrenByParent(this.name)
    return children.isEmpty()
}

/**
 * Check if this class declaration is a leaf node (cached version)
 */
fun ClassDecl.isLeafNode(cache: InheritanceAnalysisCache): Boolean {
    return cache.isLeafNode(this.name)
}

/**
 * Get all properties including inherited ones (with memoization)
 * For interfaces, only return their own properties, not inherited ones
 * For implementation classes, add inherited properties
 */
fun ClassDecl.getAllProperties(
    analyzer: InheritanceAnalyzer,
    allDecls: List<ClassDecl>,
    propertyCache: MutableMap<String, List<PropertyDecl>> = mutableMapOf()
): List<PropertyDecl> {
    // Check cache first
    propertyCache[this.name]?.let { return it }

    val allProperties = LinkedHashSet<PropertyDecl>()

    // Add current interface properties
    allProperties.addAll(this.properties)

    // For interfaces, don't add inherited properties - they should only have their own properties
    // For implementation classes, add inherited properties
    if (this.modifier !is ClassModifier.Interface && this.modifier !is ClassModifier.SealedInterface) {
        // Recursively add parent interface properties (only for implementation classes)
        val parentInterfaces = analyzer.findAllParentsByChild(this.name)
        parentInterfaces.forEach { parentName ->
            val parentInterface = allDecls.find { it.name == parentName }
            if (parentInterface != null) {
                allProperties.addAll(parentInterface.getAllProperties(analyzer, allDecls, propertyCache))
            }
        }
    }

    val result = allProperties.toList()
    propertyCache[this.name] = result
    return result
}

/**
 * Get all properties for implementation class generation
 * This method should collect all properties from the interface and its parent interfaces
 */
fun ClassDecl.getAllPropertiesForImpl(
    analyzer: InheritanceAnalyzer,
    allDecls: List<ClassDecl>,
    propertyCache: MutableMap<String, List<PropertyDecl>> = mutableMapOf()
): List<PropertyDecl> {
    // Check cache first
    propertyCache[this.name]?.let { return it }

    val allProperties = LinkedHashSet<PropertyDecl>()

    // Add current interface properties
    allProperties.addAll(this.properties)

    // Always add inherited properties for implementation class generation
    val parentInterfaces = analyzer.findAllParentsByChild(this.name)
    parentInterfaces.forEach { parentName ->
        val parentInterface = allDecls.find { it.name == parentName }
        if (parentInterface != null) {
            allProperties.addAll(parentInterface.getAllPropertiesForImpl(analyzer, allDecls, propertyCache))
        }
    }

    val result = allProperties.toList()
    propertyCache[this.name] = result
    return result
}

/**
 * Get all properties including inherited ones (cached version)
 * For interfaces, only return their own properties, not inherited ones
 */
fun ClassDecl.getAllProperties(
    cache: InheritanceAnalysisCache,
    allDecls: List<ClassDecl>,
    propertyCache: MutableMap<String, List<PropertyDecl>> = mutableMapOf()
): List<PropertyDecl> {
    // Check cache first
    propertyCache[this.name]?.let { return it }

    val allProperties = LinkedHashSet<PropertyDecl>()

    // Add current interface properties
    allProperties.addAll(this.properties)

    // For interfaces, don't add inherited properties - they should only have their own properties
    // For implementation classes, add inherited properties
    if (this.modifier !is ClassModifier.Interface && this.modifier !is ClassModifier.SealedInterface) {
        // Recursively add parent interface properties (only for implementation classes)
        val parentInterfaces = cache.getParents(this.name)
        parentInterfaces.forEach { parentName ->
            val parentInterface = allDecls.find { it.name == parentName }
            if (parentInterface != null) {
                allProperties.addAll(parentInterface.getAllProperties(cache, allDecls, propertyCache))
            }
        }
    }

    val result = allProperties.toList()
    propertyCache[this.name] = result
    return result
}
