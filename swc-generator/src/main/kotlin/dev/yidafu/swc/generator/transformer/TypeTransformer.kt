package dev.yidafu.swc.generator.transformer

import dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.adt.result.*
import dev.yidafu.swc.generator.builder.InheritanceBuilder
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.core.relation.InheritanceGraph
import dev.yidafu.swc.generator.monitor.PerformanceMonitor
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.transformer.processors.*
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.validator.InheritanceValidator

/**
 * 类型转换器
 * * 协调各个处理器，将 TypeScript 类型转换为 Kotlin 类型
 */
class TypeTransformer(
    private val config: SwcGeneratorConfig
) {
    private val classDeclMap = mutableMapOf<String, KotlinDeclaration.ClassDecl>()

    /**
     * 转换类型
     */
    fun transform(parseResult: ParseResult): GeneratorResult<TransformResult> {
        return PerformanceMonitor.measure("type_transformation") {
            try {
                Logger.debug("开始类型转换...")

                // 创建 AST 访问器
                val visitor = PerformanceMonitor.measure("ast_visitor_creation") {
                    TsAstVisitor(parseResult.astJsonString).apply { visit() }
                }

                val interfaces = visitor.getInterfaces()
                val typeAliases = visitor.getTypeAliases()

                Logger.info("找到 ${interfaces.size} 个 interface")
                Logger.info("找到 ${typeAliases.size} 个 type alias")

                // 创建上下文
                val context = TransformContext(classDeclMap)

                // 按顺序处理各种类型
                PerformanceMonitor.measure("process_literal_unions") {
                    processLiteralUnions(typeAliases, visitor, context)
                }
                PerformanceMonitor.measure("process_mixed_unions") {
                    processMixedUnions(typeAliases, visitor, context)
                }
                PerformanceMonitor.measure("process_intersections") {
                    processIntersections(typeAliases, visitor, context)
                }
                PerformanceMonitor.measure("process_ref_unions") {
                    processRefUnions(typeAliases, visitor, context)
                }

                // 构建继承关系
                val inheritanceGraph = PerformanceMonitor.measure("build_inheritance_relationship") {
                    buildInheritanceRelationship(interfaces, visitor)
                }

                // 验证继承关系
                PerformanceMonitor.measure("validate_inheritance") {
                    validateInheritanceGraph(inheritanceGraph)
                }

                // 处理接口
                PerformanceMonitor.measure("process_interfaces") {
                    processInterfaces(interfaces, visitor, context)
                }

                Logger.debug("类型转换完成，生成了 ${classDeclMap.size} 个类")

                // 生成性能报告
                Logger.debug("性能统计:\n${PerformanceMonitor.generateReport()}")

                GeneratorResultFactory.success(
                    TransformResult(
                        classDecls = classDeclMap.values.toList(),
                        classAllPropertiesMap = context.getAllPropertiesMap()
                    )
                )
            } catch (e: Exception) {
                Logger.error("类型转换失败: ${e.message}")
                GeneratorResultFactory.failure(
                    code = ErrorCode.CODE_GENERATION_ERROR,
                    message = "Failed to transform types",
                    cause = e
                )
            }
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
    ): InheritanceGraph {
        Logger.debug("构建类型继承关系...")

        val inheritanceBuilder = InheritanceBuilder(visitor, config)
        val inheritanceGraph = inheritanceBuilder.buildInheritanceGraph(interfaces)

        // 为了向后兼容，同时更新全局的 ExtendRelationship
        inheritanceGraph.getAllTypes().forEach { child ->
            inheritanceGraph.findParentsByChild(child).forEach { parent ->
                ExtendRelationship.addRelation(parent, child)
            }
        }

        Logger.debug("建立了 ${inheritanceGraph.getAllTypes().size} 个类型的继承关系")

        // 打印继承图统计信息
        Logger.debug(inheritanceGraph.generateStats())

        // 在DEBUG模式下打印完整的依赖关系报告
        if (System.getProperty("DEBUG")?.toBoolean() == true) {
            inheritanceGraph.printFullReport()
        }

        return inheritanceGraph
    }

    /**
     * 验证继承关系
     */
    private fun validateInheritanceGraph(inheritanceGraph: InheritanceGraph) {
        Logger.debug("验证继承关系...")

        val validationReport = InheritanceValidator.detectCycles(ExtendRelationship)
        if (validationReport.isNotEmpty()) {
            Logger.warn("检测到循环继承:")
            validationReport.forEach { cycle ->
                Logger.warn("  - ${cycle.joinToString(" -> ")}")
            }
        }

        val maxDepth = inheritanceGraph.getMaxDepth()
        if (maxDepth > 10) {
            Logger.warn("继承深度过深: $maxDepth，可能影响性能")
        }

        Logger.debug("继承关系验证完成")
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
    val classDecls: List<KotlinDeclaration.ClassDecl>,
    val classAllPropertiesMap: Map<String, List<KotlinDeclaration.PropertyDecl>>
)
