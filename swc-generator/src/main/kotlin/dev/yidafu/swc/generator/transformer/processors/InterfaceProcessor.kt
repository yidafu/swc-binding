package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.model.KotlinClass
import dev.yidafu.swc.generator.extractor.InterfaceExtractor
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
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
        
        val kotlinClass = createKotlinClassFromInterface(interfaceDecl, interfaceExtractor)
        Logger.debug("  属性数: ${kotlinClass.properties.size}", 4)
        Logger.debug("  父类型: ${kotlinClass.parents.joinToString(", ")}", 4)
        
        context.addProperties(name, kotlinClass.properties)
        
        // 应用特殊处理
        applySpecialModifiers(kotlinClass, name)
        Logger.logIf(kotlinClass.modifier != "interface", "  特殊类型: ${kotlinClass.modifier}")
        
        // 添加类
        if (config.toKotlinClass.contains(name)) {
            context.addKotlinClass(kotlinClass.toClass())
            Logger.verbose("  ✓ 生成为 class", 4)
        } else {
            context.addKotlinClass(kotlinClass.toInterface())
            Logger.verbose("  ✓ 生成为 interface", 4)
        }
        
        // 生成实现类
        if (!config.keepInterface.contains(name)) {
            ExtendRelationship.addRelation(name, "${name}Impl")
            context.addKotlinClass(kotlinClass.toImplClass())
            Logger.verbose("  ✓ 生成 Impl 类", 4)
        } else {
            Logger.verbose("  跳过 Impl 类生成（在保留列表中）", 4)
        }
    }
    
    /**
     * 从 interface 创建 KotlinClass
     */
    private fun createKotlinClassFromInterface(
        interfaceDecl: AstNode,
        interfaceExtractor: InterfaceExtractor
    ): KotlinClass {
        return dev.yidafu.swc.generator.core.model.KotlinClass(
            klassName = interfaceDecl.getNode("id")?.getString("value") ?: "",
            headerComment = interfaceExtractor.extractHeaderComment(interfaceDecl),
            modifier = "interface",
            parents = interfaceExtractor.extractExtends(interfaceDecl).toMutableList(),
            properties = interfaceExtractor.getInterfaceAllProperties(interfaceDecl).toMutableList()
        )
    }
    
    /**
     * 应用特殊修饰符
     */
    private fun applySpecialModifiers(kotlinClass: KotlinClass, name: String) {
        when {
            config.sealedInterface.contains(name) -> {
                kotlinClass.modifier = "sealed interface"
                kotlinClass.annotations.addAll(listOf(
                    "@SwcDslMarker",
                    "@Serializable(NodeSerializer::class)"
                ))
            }
            config.toKotlinClass.contains(name) -> {
                kotlinClass.modifier = "class"
                kotlinClass.annotations.addAll(listOf(
                    "@SwcDslMarker",
                    "@Serializable",
                    "@SerialName(\"$name\")"
                ))
            }
        }
    }
}
