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
        // 只为 interfaceToImplMap 中的接口生成 Impl 类
        val allInterfaces = classDecls.filter { it.modifier == ClassModifier.Interface }
        Logger.debug("  所有接口数量: ${allInterfaces.size}", 4)
        Logger.debug("  interfaceToImplMap 中的键: ${dev.yidafu.swc.generator.config.SerializerConfig.interfaceToImplMap.keys}", 6)

        val interfacesToGenerate = allInterfaces.filter { decl ->
            val cleanName = decl.name.removeSurrounding("`")
            val isInMap = dev.yidafu.swc.generator.config.SerializerConfig.interfaceToImplMap.containsKey(cleanName)
            if (!isInMap && cleanName in listOf("VariableDeclarator", "Param", "BlockStatement", "JSXOpeningElement", "JSXClosingElement")) {
                Logger.warn("  接口 $cleanName 不在 interfaceToImplMap 中，但应该在")
            }
            isInMap
        }
        Logger.debug("  需要生成 Impl 类的接口数量: ${interfacesToGenerate.size}", 4)
        interfacesToGenerate.forEach { interfaceDecl ->
            val cleanName = interfaceDecl.name.removeSurrounding("`")
            Logger.debug("  生成 Impl 类: $cleanName", 4)
            val implClass = createImplementationClass(interfaceDecl, analyzer, classDecls, propertyCache)
            val builder = builderSelector(interfaceDecl)
            if (poet.emitType(builder, implClass, interfaceRegistry.names, declLookup)) {
                Logger.info("  ✓ 实现类: ${implClass.name}", 4)
            } else {
                Logger.warn("  ✗ 实现类生成失败: ${implClass.name}")
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

        // 对于 interfaceToImplMap 中的接口，生成 ${name}Impl 类名
        val implClassName = dev.yidafu.swc.generator.config.SerializerConfig.interfaceToImplMap[interfaceDecl.name.removeSurrounding("`")]
            ?: "${interfaceDecl.name}Impl"

        return interfaceDecl.copy(
            // 生成 Impl 类，继承接口原有父接口
            name = implClassName,
            modifier = ClassModifier.FinalClass,
            parents = listOf(dev.yidafu.swc.generator.model.kotlin.KotlinType.Simple(interfaceDecl.name)),
            properties = processedProperties,
            nestedClasses = emptyList(),
            annotations = TypesImplementationRules.implementationAnnotations(interfaceRule, interfaceDecl)
        )
    }
}
