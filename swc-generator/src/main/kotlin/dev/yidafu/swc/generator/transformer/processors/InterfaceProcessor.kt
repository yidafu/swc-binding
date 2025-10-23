package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.extractor.InterfaceExtractor
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.util.Logger

/**
 * 接口处理器
 */
class InterfaceProcessor(
    private val config: SwcGeneratorConfig,
    private val visitor: dev.yidafu.swc.generator.parser.TsAstVisitor
) : TypeProcessor {

    private val interfaceExtractor = InterfaceExtractor(visitor)

    override fun canProcess(interfaceDecl: AstNode): Boolean {
        return interfaceDecl.type == "TsInterfaceDeclaration"
    }

    override fun processLegacy(interfaceDecl: AstNode, context: TransformContext) {
        val name = interfaceDecl.getNode("id")?.getString("value")
        if (name == null) {
            Logger.warn("跳过空名称的接口")
            return
        }

        Logger.verbose("处理接口: $name")

        val classDecl = createClassDeclFromInterface(interfaceDecl, interfaceExtractor)
        Logger.debug("  属性数: ${classDecl.properties.size}", 4)
        Logger.debug("  父类型: ${classDecl.parents.joinToString(", ")}", 4)

        context.addProperties(name, classDecl.properties)

        // 应用特殊处理
        val modifiedClassDecl = applySpecialModifiers(classDecl, name)
        Logger.logIf(modifiedClassDecl.modifier != ClassModifier.Interface, "  特殊类型: ${modifiedClassDecl.modifier}")

        // 添加类
        if (config.toKotlinClass.contains(name)) {
            context.addClassDecl(modifiedClassDecl.toClass())
            Logger.verbose("  ✓ 生成为 class", 4)
        } else {
            context.addClassDecl(modifiedClassDecl.toInterface())
            Logger.verbose("  ✓ 生成为 interface", 4)
        }

        // 生成实现类
        if (!config.keepInterface.contains(name)) {
            ExtendRelationship.addRelation(name, "${name}Impl")
            context.addClassDecl(modifiedClassDecl.toImplClass())
            Logger.verbose("  ✓ 生成 Impl 类", 4)
        } else {
            Logger.verbose("  跳过 Impl 类生成（在保留列表中）", 4)
        }
    }

    /**
     * 从 interface 创建 ClassDecl
     */
    private fun createClassDeclFromInterface(
        interfaceDecl: AstNode,
        interfaceExtractor: InterfaceExtractor
    ): KotlinDeclaration.ClassDecl {
        val name = interfaceDecl.getNode("id")?.getString("value") ?: ""
        val genericParents = interfaceExtractor.extractGenericExtends(interfaceDecl)
        val kotlinProperties = interfaceExtractor.getInterfaceAllProperties(interfaceDecl)
        val properties = kotlinProperties
        return KotlinDeclaration.ClassDecl(
            name = wrapReservedWord(name),
            modifier = ClassModifier.Interface,
            properties = properties,
            parents = genericParents.map { KotlinType.Simple(it.name) },
            annotations = emptyList(),
            kdoc = interfaceExtractor.extractHeaderComment(interfaceDecl).takeIf { it.isNotEmpty() }
        )
    }

    /**
     * 应用特殊修饰符
     */
    private fun applySpecialModifiers(classDecl: KotlinDeclaration.ClassDecl, name: String): KotlinDeclaration.ClassDecl {
        return when {
            config.sealedInterface.contains(name) -> {
                classDecl.copy(
                    modifier = ClassModifier.SealedInterface,
                    annotations = classDecl.annotations + listOf(
                        KotlinDeclaration.Annotation("SwcDslMarker"),
                        KotlinDeclaration.Annotation("Serializable", listOf(Expression.StringLiteral("NodeSerializer::class")))
                    )
                )
            }
            config.toKotlinClass.contains(name) -> {
                classDecl.copy(
                    modifier = ClassModifier.FinalClass,
                    annotations = classDecl.annotations + listOf(
                        KotlinDeclaration.Annotation("SwcDslMarker"),
                        KotlinDeclaration.Annotation("Serializable"),
                        KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral(name)))
                    )
                )
            }
            else -> classDecl
        }
    }
}
