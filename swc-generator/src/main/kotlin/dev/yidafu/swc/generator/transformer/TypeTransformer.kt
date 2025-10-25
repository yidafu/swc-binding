package dev.yidafu.swc.generator.transformer

import dev.yidafu.swc.generator.adt.kotlin.ClassModifier
import dev.yidafu.swc.generator.adt.kotlin.Expression
import dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.adt.result.*
import dev.yidafu.swc.generator.adt.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.extractor.TypeScriptADTExtractor
import dev.yidafu.swc.generator.monitor.PerformanceMonitor
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.transformer.processors.TransformContext
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzer
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzerHolder
import dev.yidafu.swc.generator.processor.KotlinADTProcessorFactory
import dev.yidafu.swc.generator.util.Logger

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

                // 新的 TypeScript ADT 提取阶段
                val tsAdtExtractor = TypeScriptADTExtractor(visitor)
                val tsDeclarations = PerformanceMonitor.measure("typescript_adt_extraction") {
                    tsAdtExtractor.extractAllDeclarations()
                }.getOrDefault(emptyList())

                Logger.info("提取到 ${tsDeclarations.size} 个 TypeScript 声明")

                val interfaces = visitor.getInterfaces()
                val typeAliases = visitor.getTypeAliases()

                Logger.info("找到 ${interfaces.size} 个 interface")
                Logger.info("找到 ${typeAliases.size} 个 type alias")

                // 创建上下文
                val context = TransformContext(classDeclMap)

                // 处理 TypeScript ADT 声明
                PerformanceMonitor.measure("process_typescript_declarations") {
                    processTypeScriptDeclarations(tsDeclarations, context)
                }

                // 分析继承关系（基于 ADT 结构）- 必须在 processKotlinADT 之前初始化
                PerformanceMonitor.measure("analyze_inheritance") {
                    analyzeInheritanceRelationships(tsDeclarations)
                }

                // 自动标记 sealed 接口（基于继承关系）
                PerformanceMonitor.measure("mark_sealed_interfaces") {
                    markSealedInterfaces(context)
                }

                // 自定义处理 Kotlin ADT（新增阶段）
                PerformanceMonitor.measure("custom_kotlin_adt_processing") {
                    processKotlinADT(context)
                }

                // 注意：传统的类型处理器已被新的 TypeScript ADT 架构替代
                // 所有类型处理现在通过 processTypeScriptDeclarations 完成
                Logger.debug("跳过传统类型处理器（已由 TypeScript ADT 架构替代）")

                Logger.debug("类型转换完成，生成了 ${classDeclMap.size} 个类")

                // 生成性能报告
                Logger.debug("性能统计:\n${PerformanceMonitor.generateReport()}")

                GeneratorResultFactory.success(
                    TransformResult(
                        classDecls = classDeclMap.values.toList(),
                        classAllPropertiesMap = context.getAllPropertiesMap(),
                        typeAliases = context.getAllTypeAliases()
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
     * 处理 TypeScript ADT 声明
     */
    private fun processTypeScriptDeclarations(
        tsDeclarations: List<TypeScriptDeclaration>,
        context: TransformContext
    ) {
        Logger.debug("处理 TypeScript ADT 声明...")
        
        tsDeclarations.forEach { declaration ->
            when (declaration) {
                is TypeScriptDeclaration.InterfaceDeclaration -> {
                    Logger.debug("  处理接口: ${declaration.name}")
                    processInterfaceDeclaration(declaration, context)
                }
                is TypeScriptDeclaration.TypeAliasDeclaration -> {
                    Logger.debug("  处理类型别名: ${declaration.name}")
                    processTypeAliasDeclaration(declaration, context)
                }
            }
        }
    }

    /**
     * 处理接口声明
     */
    private fun processInterfaceDeclaration(
        tsInterface: TypeScriptDeclaration.InterfaceDeclaration,
        context: TransformContext
    ) {
        Logger.debug("处理接口声明: ${tsInterface.name}", 4)
        
        dev.yidafu.swc.generator.adt.converter.TypeConverter.convertInterfaceDeclaration(tsInterface)
            .onSuccess { classDecl: KotlinDeclaration.ClassDecl ->
                context.addClassDecl(classDecl)
                Logger.debug("  ✓ 转换成功: ${classDecl.name} (${classDecl.modifier})", 6)
            }
            .onFailure { error: GeneratorError ->
                Logger.warn("转换接口声明失败: ${tsInterface.name}, ${error.message}")
            }
    }

    /**
     * 处理类型别名声明
     */
    private fun processTypeAliasDeclaration(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration,
        context: TransformContext
    ) {
        Logger.debug("处理类型别名声明: ${tsTypeAlias.name}", 4)
        
        dev.yidafu.swc.generator.adt.converter.TypeConverter.convertTypeAliasDeclaration(tsTypeAlias)
            .onSuccess { typeAliasDecl: KotlinDeclaration.TypeAliasDecl ->
                context.addTypeAlias(typeAliasDecl)
                Logger.debug("  ✓ 转换成功", 6)
            }
            .onFailure { error: GeneratorError ->
                Logger.warn("转换类型别名声明失败: ${tsTypeAlias.name}, ${error.message}")
            }
    }

    /**
     * 分析继承关系（基于 ADT 结构）
     */
    private fun analyzeInheritanceRelationships(tsDeclarations: List<TypeScriptDeclaration>) {
        Logger.debug("分析继承关系...")
        
        // 初始化全局继承分析器
        InheritanceAnalyzerHolder.initialize(tsDeclarations)
        val analyzer = InheritanceAnalyzerHolder.get()
        
        // 检测循环继承
        val cycles = analyzer.detectCycles()
        if (cycles.isNotEmpty()) {
            Logger.warn("检测到循环继承:")
            cycles.forEach { cycle ->
                Logger.warn("  - ${cycle.joinToString(" -> ")}")
            }
        }
        
        // 检查继承深度
        val maxDepth = analyzer.getMaxDepth()
        if (maxDepth > 10) {
            Logger.warn("继承深度过深: $maxDepth，可能影响性能")
        }
        
        // 生成统计信息
        Logger.debug(analyzer.generateStats())
        
        // 在DEBUG模式下打印详细的继承关系
        if (System.getProperty("DEBUG")?.toBoolean() == true) {
            printDetailedInheritanceReport(analyzer, tsDeclarations)
        }
    }
    
    /**
     * 打印详细的继承关系报告
     */
    private fun printDetailedInheritanceReport(analyzer: InheritanceAnalyzer, tsDeclarations: List<TypeScriptDeclaration>) {
        Logger.debug("详细继承关系报告:")
        Logger.debug("=".repeat(50))
        
        val rootTypes = analyzer.findRootTypes()
        Logger.debug("根类型 (${rootTypes.size} 个):")
        rootTypes.forEach { root ->
            Logger.debug("  - $root")
        }
        
        // 深度分布
        val allTypes = tsDeclarations
            .filterIsInstance<TypeScriptDeclaration.InterfaceDeclaration>()
            .map { it.name }
        
        val depthDistribution = allTypes.groupBy { analyzer.getInheritanceDepth(it) }
        Logger.debug("深度分布:")
        depthDistribution.entries.sortedBy { it.key }.forEach { (depth, types) ->
            Logger.debug("  深度 $depth: ${types.size} 个类型")
            if (types.size <= 10) {
                Logger.debug("    ${types.joinToString(", ")}")
            }
        }
    }







    /**
     * 自定义处理 Kotlin ADT
     * 
     * 在 Kotlin ADT 生成之后、Kotlin 代码生成之前进行自定义处理
     * 用于处理特殊的业务逻辑，保持 Kotlin ADT 的纯净性
     * 同时为接口生成实现类
     */
    private fun processKotlinADT(context: TransformContext) {
        Logger.debug("开始自定义处理 Kotlin ADT...")
        
        // 获取所有 Kotlin 声明
        val allDeclarations = mutableListOf<KotlinDeclaration>()
        allDeclarations.addAll(context.getAllClassDecls())
        allDeclarations.addAll(context.getAllTypeAliases())
        
        Logger.debug("准备处理 ${allDeclarations.size} 个 Kotlin 声明")
        
        // 创建组合处理器：默认处理 + 实现类生成
        val combinedProcessor = KotlinADTProcessorFactory.createCombinedProcessor(config)
        
        val processResult = combinedProcessor.processDeclarations(allDeclarations, config)
        
        processResult.onSuccess { processedDeclarations ->
            Logger.debug("Kotlin ADT 自定义处理完成，处理了 ${processedDeclarations.size} 个声明")
            
            // 更新上下文中的声明
            processedDeclarations.forEach { declaration ->
                when (declaration) {
                    is KotlinDeclaration.ClassDecl -> {
                        context.updateClassDecl(declaration)
                    }
                    is KotlinDeclaration.TypeAliasDecl -> {
                        context.updateTypeAlias(declaration)
                    }
                    else -> {
                        // 其他类型的声明暂不处理
                    }
                }
            }
        }.onFailure { error ->
            Logger.warn("Kotlin ADT 自定义处理失败: ${error.message}")
            // 处理失败时继续使用原始声明
        }
    }

    /**
     * 自动标记 sealed 接口（基于继承关系）
     * 如果一个接口有多个子接口，则将其标记为 sealed
     */
    private fun markSealedInterfaces(context: TransformContext) {
        Logger.debug("自动标记 sealed 接口...")
        
        val analyzer = InheritanceAnalyzerHolder.get()
        val allClassDecls = context.getAllClassDecls()
        
        // 找出所有接口
        val interfaces = allClassDecls.filter { it.modifier == ClassModifier.Interface }
        
        interfaces.forEach { interfaceDecl ->
            val children = analyzer.findAllChildrenByParent(interfaceDecl.name)
            
            // 如果接口有子接口，则标记为 sealed
            if (children.isNotEmpty()) {
                Logger.debug("  标记为 sealed: ${interfaceDecl.name} (有 ${children.size} 个子接口)")
                
                val sealedInterface = interfaceDecl.copy(
                    modifier = ClassModifier.SealedInterface,
                    annotations = interfaceDecl.annotations + listOf(
                        KotlinDeclaration.Annotation("SwcDslMarker")
                    )
                )
                
                context.updateClassDecl(sealedInterface)
            }
        }
        
        Logger.debug("sealed 接口标记完成")
    }
}

/**
 * 转换结果
 */

/**
 * 转换结果
 */
data class TransformResult(
    val classDecls: List<KotlinDeclaration.ClassDecl>,
    val classAllPropertiesMap: Map<String, List<KotlinDeclaration.PropertyDecl>>,
    val typeAliases: List<KotlinDeclaration.TypeAliasDecl> = emptyList()
)
