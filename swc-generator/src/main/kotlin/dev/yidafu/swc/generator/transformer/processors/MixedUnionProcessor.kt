package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.extractor.TypeAliasExtractor
import dev.yidafu.swc.generator.extractor.TypeResolver
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.adt.kotlin.*

/**
 * 混合联合类型处理器
 */
class MixedUnionProcessor(
    private val config: SwcGeneratorConfig,
    private val visitor: dev.yidafu.swc.generator.parser.TsAstVisitor
) : TypeProcessor {
    
    private val typeAliasExtractor = TypeAliasExtractor(visitor)
    
    override fun canProcess(typeAlias: AstNode): Boolean {
        return (typeAliasExtractor.isLiteralUnion(typeAlias) || typeAliasExtractor.isMixedUnion(typeAlias)) &&
               !typeAliasExtractor.isAllLiteralTypeSame(typeAlias)
    }
    
    override fun process(typeAlias: AstNode, context: TransformContext): KotlinDeclaration? {
        val name = typeAliasExtractor.getTypeAliasName(typeAlias)
        if (name.isEmpty()) {
            Logger.warn("跳过空名称的混合联合类型")
            return null
        }
        
        val typeString = typeAliasExtractor.getTypeString(typeAlias)
        Logger.verbose("处理混合联合: $name")
        Logger.debug("  原始类型: $typeString", 4)
        
        val uniqueTypes = typeString.split(",").map { 
            it.trim().replace(Regex("""Union\.U\d+<|>"""), "")
        }.toSet().toList()
        
        Logger.debug("  唯一类型数: ${uniqueTypes.size}", 4)
        
        // 创建类型别名声明
        val kotlinType = when {
            uniqueTypes.size == 1 -> uniqueTypes[0].parseToKotlinType()
            uniqueTypes.size == 2 -> KotlinTypeFactory.union(
                uniqueTypes[0].parseToKotlinType(),
                uniqueTypes[1].parseToKotlinType()
            )
            uniqueTypes.size in 3..4 -> {
                val unionTypes = uniqueTypes.map { it.parseToKotlinType() }
                KotlinTypeFactory.union(*unionTypes.toTypedArray())
            }
            else -> KotlinTypeFactory.any()
        }
        
        val typeAliasDecl = KotlinDeclaration.TypeAliasDecl(
            name = name,
            type = kotlinType,
            kdoc = "混合联合类型: $typeString"
        )
        
        Logger.verbose("  ✓ 处理混合联合: $name", 4)
        return typeAliasDecl
    }
    
    override fun processLegacy(typeAlias: AstNode, context: TransformContext) {
        // 旧实现，保持向后兼容
        val name = typeAliasExtractor.getTypeAliasName(typeAlias)
        if (name.isEmpty()) {
            Logger.warn("跳过空名称的混合联合类型")
            return
        }
        
        val typeString = typeAliasExtractor.getTypeString(typeAlias)
        Logger.verbose("处理混合联合: $name")
        Logger.debug("  原始类型: $typeString", 4)
        
        val uniqueTypes = typeString.split(",").map { 
            it.trim().replace(Regex("""Union\.U\d+<|>"""), "")
        }.toSet().toList()
        
        Logger.debug("  唯一类型数: ${uniqueTypes.size}", 4)
        
        // 这里不直接生成类，而是记录类型别名信息
        // 实际的类型别名生成在 TypesGenerator 中处理
        Logger.verbose("  ✓ 处理混合联合: $name", 4)
    }
}
