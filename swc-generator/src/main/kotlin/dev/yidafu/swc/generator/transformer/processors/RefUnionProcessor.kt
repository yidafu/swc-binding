package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.model.KotlinClass
import dev.yidafu.swc.generator.extractor.TypeAliasExtractor
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.util.Logger

/**
 * 引用联合类型处理器
 */
class RefUnionProcessor(
    private val config: SwcGeneratorConfig,
    private val visitor: dev.yidafu.swc.generator.parser.TsAstVisitor
) : TypeProcessor {
    
    private val typeAliasExtractor = TypeAliasExtractor(visitor)
    
    override fun canProcess(typeAlias: AstNode): Boolean {
        return typeAliasExtractor.isRefUnion(typeAlias)
    }
    
    override fun processLegacy(typeAlias: AstNode, context: TransformContext) {
        val name = typeAliasExtractor.getTypeAliasName(typeAlias)
        if (name.isEmpty()) {
            Logger.warn("跳过空名称的引用联合类型")
            return
        }
        
        Logger.verbose("处理引用联合: $name")
        
        // 创建 sealed interface
        val kotlinClass = dev.yidafu.swc.generator.core.model.KotlinClass(
            klassName = name,
            annotations = mutableListOf("@SwcDslMarker", "@Serializable"),
            modifier = "sealed interface",
            properties = mutableListOf()
        )
        
        context.addKotlinClass(kotlinClass)
        
        // 建立继承关系
        val childTypes = typeAliasExtractor.getUnionTypeNames(typeAlias)
        Logger.debug("  子类型数: ${childTypes.size}", 4)
        Logger.verbose("  子类型: ${childTypes.joinToString(", ")}", 4)
        
        childTypes.forEach { child -> 
            ExtendRelationship.addRelation(name, child)
            Logger.logIf(childTypes.size < 5, "    添加关系: $name -> $child")
        }
        
        Logger.verbose("  ✓ 创建 sealed interface: $name", 4)
    }
}
