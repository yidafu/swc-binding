package dev.yidafu.swc.generator.codegen.generator.types

import com.squareup.kotlinpoet.FileSpec
import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.codegen.generator.PoetGenerator
import dev.yidafu.swc.generator.config.TypesImplementationRules
import dev.yidafu.swc.generator.extensions.getAllPropertiesForImpl
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.util.ImplementationClassGenerator
import dev.yidafu.swc.generator.util.Logger

/**
 * 负责生成实现类。
 */
class ImplementationEmitter(
    private val interfaceRegistry: InterfaceRegistry
) {

    fun emit(
        builderSelector: (KotlinDeclaration.ClassDecl) -> FileSpec.Builder,
        classDecls: List<KotlinDeclaration.ClassDecl>,
        analyzer: InheritanceAnalyzer,
        propertyCache: MutableMap<String, List<KotlinDeclaration.PropertyDecl>>,
        poet: PoetGenerator,
        declLookup: Map<String, KotlinDeclaration.ClassDecl>
    ) {
        val leafNodes = findLeafNodes(classDecls, analyzer)
        Logger.debug("  叶子节点数量: ${leafNodes.size}", 4)
        leafNodes.forEach { leaf ->
            val implClass = createImplementationClass(leaf, analyzer, classDecls, propertyCache)
            val builder = builderSelector(leaf)
            if (poet.emitType(builder, implClass, interfaceRegistry.names, declLookup)) {
                Logger.verbose("  ✓ 实现类: ${leaf.name}Impl", 6)
            }
        }
    }

    private fun findLeafNodes(
        classDecls: List<KotlinDeclaration.ClassDecl>,
        analyzer: InheritanceAnalyzer
    ): List<KotlinDeclaration.ClassDecl> {
        val allInterfaces = classDecls.filter { it.modifier == ClassModifier.Interface }
        return ImplementationClassGenerator.findLeafNodes(allInterfaces, analyzer)
    }

    internal fun createImplementationClass(
        interfaceDecl: KotlinDeclaration.ClassDecl,
        analyzer: InheritanceAnalyzer,
        classDecls: List<KotlinDeclaration.ClassDecl>,
        propertyCache: MutableMap<String, List<KotlinDeclaration.PropertyDecl>>
    ): KotlinDeclaration.ClassDecl {
        val allProperties = interfaceDecl.getAllPropertiesForImpl(analyzer, classDecls, propertyCache)
        val interfaceRule = TypesImplementationRules.createInterfaceRule(interfaceDecl.name)
        val reorderedProperties = TypesImplementationRules.reorderImplementationProperties(allProperties, interfaceDecl)
        val processedProperties = reorderedProperties.map { prop ->
            TypesImplementationRules.processImplementationProperty(prop, interfaceRule)
        }

        return interfaceDecl.copy(
            // 生成与接口同名的具体类，直接继承接口原有父接口（名实合一）
            name = interfaceDecl.name,
            modifier = ClassModifier.FinalClass,
            parents = interfaceDecl.parents,
            properties = processedProperties,
            nestedClasses = emptyList(),
            annotations = TypesImplementationRules.implementationAnnotations(interfaceRule, interfaceDecl)
        )
    }
}
