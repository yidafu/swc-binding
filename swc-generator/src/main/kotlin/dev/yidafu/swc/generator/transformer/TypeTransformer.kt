package dev.yidafu.swc.generator.transformer

import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.model.KotlinClass
import dev.yidafu.swc.generator.core.model.KotlinProperty
import dev.yidafu.swc.generator.adt.result.*
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.extractor.InterfaceExtractor
import dev.yidafu.swc.generator.extractor.TypeAliasExtractor
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.transformer.processors.*
import dev.yidafu.swc.generator.util.Logger

/**
 * 类型转换器
 * 
 * 协调各个处理器，将 TypeScript 类型转换为 Kotlin 类型
 */
class TypeTransformer(
    private val config: SwcGeneratorConfig
) {
    private val kotlinClassMap = mutableMapOf<String, KotlinClass>()
    
    /**
     * 转换类型
     */
    fun transform(parseResult: ParseResult): GeneratorResult<TransformResult> {
        return try {
            Logger.debug("开始类型转换...")
            
            // 创建 AST 访问器
            val visitor = TsAstVisitor(parseResult.astJsonString)
            visitor.visit()
            
            val interfaces = visitor.getInterfaces()
            val typeAliases = visitor.getTypeAliases()
            
            Logger.info("找到 ${interfaces.size} 个 interface")
            Logger.info("找到 ${typeAliases.size} 个 type alias")
            
            // 创建上下文
            val context = TransformContext(kotlinClassMap)
            
            // 按顺序处理各种类型
            processLiteralUnions(typeAliases, visitor, context)
            processMixedUnions(typeAliases, visitor, context)
            processIntersections(typeAliases, visitor, context)
            processRefUnions(typeAliases, visitor, context)
            
            // 构建继承关系
            buildInheritanceRelationship(interfaces, visitor)
            
            // 处理接口
            processInterfaces(interfaces, visitor, context)
            
            Logger.debug("类型转换完成，生成了 ${kotlinClassMap.size} 个类")
            
            GeneratorResultFactory.success(TransformResult(
                kotlinClasses = kotlinClassMap.values.toList(),
                classAllPropertiesMap = context.getAllPropertiesMap()
            ))
            
        } catch (e: Exception) {
            GeneratorResultFactory.failure(
                code = ErrorCode.CODE_GENERATION_ERROR,
                message = "Failed to transform types",
                cause = e
            )
        }
    }
    
    /**
     * 处理字面量联合类型
     */
    private fun processLiteralUnions(
        typeAliases: List<AstNode>,
        visitor: TsAstVisitor,
        context: TransformContext
    ) {
        Logger.debug("处理字面量联合类型...")
        val processor = LiteralUnionProcessor(config, visitor)
        typeAliases.filter { processor.canProcess(it) }
            .forEach { processor.process(it, context) }
    }
    
    /**
     * 处理混合联合类型
     */
    private fun processMixedUnions(
        typeAliases: List<AstNode>,
        visitor: TsAstVisitor,
        context: TransformContext
    ) {
        Logger.debug("处理混合联合类型...")
        val processor = MixedUnionProcessor(config, visitor)
        typeAliases.filter { processor.canProcess(it) }
            .forEach { processor.process(it, context) }
    }
    
    /**
     * 处理交叉类型
     */
    private fun processIntersections(
        typeAliases: List<AstNode>,
        visitor: TsAstVisitor,
        context: TransformContext
    ) {
        Logger.debug("处理交叉类型...")
        val processor = IntersectionTypeProcessor(config, visitor)
        typeAliases.filter { processor.canProcess(it) }
            .forEach { processor.process(it, context) }
    }
    
    /**
     * 处理引用联合类型
     */
    private fun processRefUnions(
        typeAliases: List<AstNode>,
        visitor: TsAstVisitor,
        context: TransformContext
    ) {
        Logger.debug("处理引用联合类型...")
        val processor = RefUnionProcessor(config, visitor)
        typeAliases.filter { processor.canProcess(it) }
            .forEach { processor.process(it, context) }
    }
    
    /**
     * 构建继承关系
     */
    private fun buildInheritanceRelationship(
        interfaces: List<AstNode>,
        visitor: TsAstVisitor
    ) {
        Logger.debug("构建类型继承关系...")
        val interfaceExtractor = InterfaceExtractor(visitor)
        var relationCount = 0
        
        interfaces.forEach { interfaceDecl ->
            val interfaceName = interfaceDecl.getInterfaceName() ?: return@forEach
            
            val parents = interfaceExtractor.extractExtends(interfaceDecl)
            Logger.logIf(parents.isNotEmpty(), "$interfaceName extends ${parents.joinToString(", ")}")
            
            parents.forEach { parent -> 
                ExtendRelationship.addRelation(parent, interfaceName)
                relationCount++
            }
        }
        
        Logger.debug("建立了 $relationCount 个继承关系")
    }
    
    /**
     * 处理接口
     */
    private fun processInterfaces(
        interfaces: List<AstNode>,
        visitor: TsAstVisitor,
        context: TransformContext
    ) {
        Logger.debug("处理接口定义...")
        val processor = InterfaceProcessor(config, visitor)
        interfaces.filter { processor.canProcess(it) }
            .forEach { processor.process(it, context) }
    }
}

/**
 * 转换结果
 */
data class TransformResult(
    val kotlinClasses: List<KotlinClass>,
    val classAllPropertiesMap: Map<String, List<KotlinProperty>>
)
