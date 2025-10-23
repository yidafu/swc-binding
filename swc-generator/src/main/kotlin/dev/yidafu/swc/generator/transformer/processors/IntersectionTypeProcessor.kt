package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.extractor.InterfaceExtractor
import dev.yidafu.swc.generator.extractor.TypeAliasExtractor
import dev.yidafu.swc.generator.extractor.TypeResolver
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.util.Logger

/**
 * 交叉类型处理器
 */
class IntersectionTypeProcessor(
    private val config: SwcGeneratorConfig,
    private val visitor: dev.yidafu.swc.generator.parser.TsAstVisitor
) : TypeProcessor {

    private val typeAliasExtractor = TypeAliasExtractor(visitor)
    private val interfaceExtractor = InterfaceExtractor(visitor)

    override fun canProcess(typeAlias: AstNode): Boolean {
        return typeAliasExtractor.isIntersectionType(typeAlias)
    }

    override fun processLegacy(typeAlias: AstNode, context: TransformContext) {
        val name = typeAliasExtractor.getTypeAliasName(typeAlias)
        if (name.isEmpty()) {
            Logger.warn("跳过空名称的交叉类型")
            return
        }

        Logger.verbose("处理交叉类型: $name")

        val info = typeAliasExtractor.extractIntersectionInfo(typeAlias)
        if (info == null) {
            Logger.warn("  无法提取交叉类型信息: $name")
            return
        }

        val (parentName, typeLiteral) = info
        Logger.debug("  父类型: $parentName", 4)

        // 建立继承关系
        ExtendRelationship.addRelation("Base$name", name)
        ExtendRelationship.addRelation(parentName, name)
        ExtendRelationship.addRelation("Base$name", parentName)
        Logger.verbose("  建立继承关系: Base$name -> $name -> $parentName", 4)

        // 创建 Base interface
        val kotlinProperties = TypeResolver.extractPropertiesFromTypeLiteral(typeLiteral)
        val properties = kotlinProperties
        Logger.debug("  提取 ${properties.size} 个属性", 4)

        val baseInterface = createBaseInterface(name, properties)
        context.addClassDecl(baseInterface)
        context.addProperties("Base$name", properties.map { it.clone() })

        // 创建实现类
        val parentKotlinProps = visitor.findInterface(parentName)?.let {
            interfaceExtractor.getInterfaceOwnProperties(it)
        } ?: emptyList()
        val parentProps = parentKotlinProps
        Logger.debug("  父类属性: ${parentProps.size} 个", 4)

        val implClass = createIntersectionImplClass(name, parentName, parentProps, properties)
        context.addClassDecl(implClass)
        Logger.verbose("  ✓ 创建交叉类型: $name", 4)
    }

    /**
     * 创建 Base interface
     */
    private fun createBaseInterface(name: String, properties: List<KotlinDeclaration.PropertyDecl>): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = wrapReservedWord("Base$name"),
            modifier = ClassModifier.SealedInterface,
            properties = properties.map { it.clone().copy(defaultValue = null) },
            parents = emptyList(),
            annotations = listOf(
                KotlinDeclaration.Annotation("SwcDslMarker"),
                KotlinDeclaration.Annotation("Serializable")
            ),
            kdoc = null
        )
    }

    /**
     * 创建 Intersection 实现类
     */
    private fun createIntersectionImplClass(
        name: String,
        parentName: String,
        parentProps: List<KotlinDeclaration.PropertyDecl>,
        ownProps: List<KotlinDeclaration.PropertyDecl>
    ): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = wrapReservedWord(name),
            modifier = ClassModifier.OpenClass,
            properties = parentProps + ownProps.map { it.clone() },
            parents = listOf(KotlinType.Simple(parentName), KotlinType.Simple("Base$name")),
            annotations = listOf(
                KotlinDeclaration.Annotation("SwcDslMarker"),
                KotlinDeclaration.Annotation("Serializable")
            ),
            kdoc = null
        )
    }
}
