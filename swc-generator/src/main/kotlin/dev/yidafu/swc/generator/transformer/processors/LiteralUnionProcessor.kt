package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
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
        return typeAliasExtractor.isLiteralUnion(typeAlias) && typeAliasExtractor.isAllLiteralTypeSame(typeAlias)
    }

    override fun processLegacy(typeAlias: AstNode, context: TransformContext) {
        val name = typeAliasExtractor.getTypeAliasName(typeAlias)
        if (name.isEmpty()) {
            Logger.warn("跳过空名称的 type alias")
            return
        }

        Logger.verbose("处理字面量联合: $name")
        val kotlinProperties = typeAliasExtractor.extractLiteralUnionProperties(typeAlias)
        val properties = kotlinProperties
        Logger.debug("  生成 ${properties.size} 个常量属性", 4)

        // 记录类型别名
        kotlinProperties.firstOrNull()?.getTypeString()?.let { firstType ->
            TypeResolver.typeAliasMap[name] = firstType
            Logger.verbose("  类型映射: $name -> $firstType", 4)
        }

        // 创建 object
        val classDecl = KotlinDeclaration.ClassDecl(
            name = wrapReservedWord(name),
            modifier = ClassModifier.Object,
            properties = properties,
            parents = emptyList(),
            annotations = emptyList(),
            kdoc = "/**\n  * ${typeAliasExtractor.getTypeString(typeAlias)}\n  */"
        )

        context.addClassDecl(classDecl)
        Logger.verbose("  ✓ 创建 object: $name", 4)
    }
}
