package dev.yidafu.swc.generator.util

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.typescript.InheritanceAnalysisCache
import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.extensions.getAllProperties
import dev.yidafu.swc.generator.extensions.getAllPropertiesForImpl
import dev.yidafu.swc.generator.extensions.isLeafNode

typealias ClassDecl = KotlinDeclaration.ClassDecl
typealias PropertyDecl = KotlinDeclaration.PropertyDecl

/**
 * Shared utility for generating implementation classes
 * Eliminates code duplication between CodeGenerationProcessor and TypesGenerator
 */
object ImplementationClassGenerator {

    /**
     * Find leaf nodes using inheritance analyzer
     */
    fun findLeafNodes(
        allInterfaces: List<ClassDecl>,
        analyzer: InheritanceAnalyzer
    ): List<ClassDecl> {
        return allInterfaces.filter { interfaceDecl ->
            interfaceDecl.isLeafNode(analyzer)
        }
    }

    /**
     * Find leaf nodes using cached inheritance analysis
     */
    fun findLeafNodes(
        allInterfaces: List<ClassDecl>,
        cache: InheritanceAnalysisCache
    ): List<ClassDecl> {
        return cache.getLeafNodes()
    }

    /**
     * Create implementation class with property collection
     */
    fun createImplementationClass(
        interfaceDecl: ClassDecl,
        analyzer: InheritanceAnalyzer,
        allClassDecls: List<ClassDecl>
    ): ClassDecl {
        // Use memoization for property collection
        val propertyCache = mutableMapOf<String, List<PropertyDecl>>()
        val allProperties = interfaceDecl.getAllPropertiesForImpl(analyzer, allClassDecls, propertyCache)

        return interfaceDecl.copy(
            name = "${interfaceDecl.name}Impl",
            modifier = ClassModifier.DataClass,
            properties = allProperties,
            typeParameters = interfaceDecl.typeParameters // Preserve type parameters
        )
    }

    /**
     * Create implementation class with cached inheritance analysis
     */
    fun createImplementationClass(
        interfaceDecl: ClassDecl,
        cache: InheritanceAnalysisCache,
        allClassDecls: List<ClassDecl>
    ): ClassDecl {
        // Use memoization for property collection
        val propertyCache = mutableMapOf<String, List<PropertyDecl>>()
        val allProperties = interfaceDecl.getAllProperties(cache, allClassDecls, propertyCache)

        return interfaceDecl.copy(
            name = "${interfaceDecl.name}Impl",
            modifier = ClassModifier.DataClass,
            properties = allProperties,
            typeParameters = interfaceDecl.typeParameters // Preserve type parameters
        )
    }

    /**
     * Generate implementation classes for all leaf nodes
     */
    fun generateImplementationClasses(
        allInterfaces: List<ClassDecl>,
        analyzer: InheritanceAnalyzer,
        allClassDecls: List<ClassDecl>
    ): List<ClassDecl> {
        val leafNodes = findLeafNodes(allInterfaces, analyzer)
        val implementationClasses = mutableListOf<ClassDecl>()

        leafNodes.forEach { interfaceDecl ->
            try {
                val implClass = createImplementationClass(interfaceDecl, analyzer, allClassDecls)
                implementationClasses.add(implClass)
                Logger.verbose("  ✓ 生成实现类: ${implClass.name}", 4)
            } catch (e: Exception) {
                Logger.warn("  生成实现类失败: ${interfaceDecl.name}Impl, ${e.message}")
            }
        }

        return implementationClasses
    }

    /**
     * Generate implementation classes for all leaf nodes (cached version)
     */
    fun generateImplementationClasses(
        allInterfaces: List<ClassDecl>,
        cache: InheritanceAnalysisCache,
        allClassDecls: List<ClassDecl>
    ): List<ClassDecl> {
        val leafNodes = findLeafNodes(allInterfaces, cache)
        val implementationClasses = mutableListOf<ClassDecl>()

        leafNodes.forEach { interfaceDecl ->
            try {
                val implClass = createImplementationClass(interfaceDecl, cache, allClassDecls)
                implementationClasses.add(implClass)
                Logger.verbose("  ✓ 生成实现类: ${implClass.name}", 4)
            } catch (e: Exception) {
                Logger.warn("  生成实现类失败: ${interfaceDecl.name}Impl, ${e.message}")
            }
        }

        return implementationClasses
    }
}
