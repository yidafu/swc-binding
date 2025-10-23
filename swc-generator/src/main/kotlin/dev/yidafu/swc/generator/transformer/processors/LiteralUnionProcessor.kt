package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.model.KotlinClass
import dev.yidafu.swc.generator.core.model.KotlinProperty
import dev.yidafu.swc.generator.extractor.TypeAliasExtractor
import dev.yidafu.swc.generator.extractor.TypeResolver
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.util.Logger

/**
 * 字面量联合类型处理器
 */
class LiteralUnionProcessor(
    private val config: SwcGeneratorConfig,
    private val visitor: dev.yidafu.swc.generator.parser.TsAstVisitor
) : TypeProcessor {
    
    private val typeAliasExtractor = TypeAliasExtractor(visitor)
    
    override fun canProcess(typeAlias: AstNode): Boolean {
        return typeAliasExtractor.isLiteralUnion(typeAlias) && 
               typeAliasExtractor.isAllLiteralTypeSame(typeAlias)
    }
    
    override fun processLegacy(typeAlias: AstNode, context: TransformContext) {
        val name = typeAliasExtractor.getTypeAliasName(typeAlias)
        if (name.isEmpty()) {
            Logger.warn("跳过空名称的 type alias")
            return
        }
        
        Logger.verbose("处理字面量联合: $name")
        val properties = typeAliasExtractor.extractLiteralUnionProperties(typeAlias)
        Logger.debug("  生成 ${properties.size} 个常量属性", 4)
        
        // 记录类型别名
        properties.firstOrNull()?.getTypeString()?.let { firstType ->
            TypeResolver.typeAliasMap[name] = firstType
            Logger.verbose("  类型映射: $name -> $firstType", 4)
        }
        
        // 创建 object
        val kotlinClass = dev.yidafu.swc.generator.core.model.KotlinClass(
            klassName = name,
            headerComment = "/**\n  * ${typeAliasExtractor.getTypeString(typeAlias)}\n  */",
            modifier = "object",
            properties = properties.toMutableList()
        )
        
        context.addKotlinClass(kotlinClass)
        Logger.verbose("  ✓ 创建 object: $name", 4)
    }
}
