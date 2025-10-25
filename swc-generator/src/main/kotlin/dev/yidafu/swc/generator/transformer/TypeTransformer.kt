package dev.yidafu.swc.generator.transformer

import dev.yidafu.swc.generator.adt.kotlin.ClassModifier
import dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.adt.result.*
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzer
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzerHolder
import dev.yidafu.swc.generator.adt.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.extractor.TypeScriptADTExtractor
import dev.yidafu.swc.generator.monitor.PerformanceMonitor
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.processor.KotlinADTProcessorFactory
import dev.yidafu.swc.generator.transformer.processors.TransformContext
import dev.yidafu.swc.generator.util.Logger
import kotlinx.serialization.json.Json

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

                // 创建 AST 访问器 - 使用合并后的 AST
                val visitor = PerformanceMonitor.measure("ast_visitor_creation") {
                    // 将合并后的程序转换回 JSON 字符串
                    val mergedJsonString = parseResult.program.toJsonObject().toString()
                    TsAstVisitor(mergedJsonString).apply { visit() }
                }

                // 调试：检查合并后的 AST 是否包含 Assumptions
                val allBodies = parseResult.program.getNodes("body")
                Logger.debug("合并后的 AST 包含 ${allBodies.size} 个声明")
                val assumptionsNodes = allBodies.filter { 
                    it.type == "TsInterfaceDeclaration" && 
                    it.getNode("id")?.getString("value")?.contains("Assumptions") == true 
                }
                Logger.debug("找到 ${assumptionsNodes.size} 个 Assumptions 接口节点")

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

                // 分析继承关系（基于 ADT 结构）- 必须在 processTypeScriptDeclarations 之前初始化
                PerformanceMonitor.measure("analyze_inheritance") {
                    analyzeInheritanceRelationships(tsDeclarations)
                }

                // 处理 TypeScript ADT 声明
                PerformanceMonitor.measure("process_typescript_declarations") {
                    processTypeScriptDeclarations(tsDeclarations, context)
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

        val inheritanceAnalyzer = if (InheritanceAnalyzerHolder.isInitialized()) {
            InheritanceAnalyzerHolder.get()
        } else {
            null
        }

        val typeConverter = dev.yidafu.swc.generator.adt.converter.TypeConverter(config)
        typeConverter.convertInterfaceDeclaration(tsInterface, inheritanceAnalyzer)
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
            .onSuccess { declaration: KotlinDeclaration ->
                when (declaration) {
                    is KotlinDeclaration.TypeAliasDecl -> {
                        context.addTypeAlias(declaration)
                        Logger.debug("  ✓ 转换成功: typealias", 6)
                    }
                    is KotlinDeclaration.ClassDecl -> {
                        context.addClassDecl(declaration)
                        if (declaration.modifier == ClassModifier.SealedInterface) {
                            // 处理密封接口的继承关系
                            handleSealedInterfaceInheritance(tsTypeAlias, declaration, context)
                        } else {
                            Logger.debug("  ✓ 转换成功: enum class", 6)
                        }
                    }
                    else -> {
                        Logger.warn("未知的声明类型: ${declaration::class.simpleName}")
                    }
                }
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
     * * 在 Kotlin ADT 生成之后、Kotlin 代码生成之前进行自定义处理
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

    /**
     * 处理密封接口的继承关系
     */
    private fun handleSealedInterfaceInheritance(
        tsTypeAlias: TypeScriptDeclaration.TypeAliasDeclaration,
        sealedInterface: KotlinDeclaration.ClassDecl,
        context: TransformContext
    ) {
        Logger.debug("处理密封接口继承关系: ${sealedInterface.name}", 6)

        // 获取联合类型中的接口名称
        val unionType = tsTypeAlias.type as dev.yidafu.swc.generator.adt.typescript.TypeScriptType.Union
        val interfaceNames = unionType.types.mapNotNull { type ->
            if (type is dev.yidafu.swc.generator.adt.typescript.TypeScriptType.Reference) type.name else null
        }

        // 更新继承关系
        if (InheritanceAnalyzerHolder.isInitialized()) {
            val analyzer = InheritanceAnalyzerHolder.get()
            interfaceNames.forEach { interfaceName ->
                analyzer.addInheritance(sealedInterface.name, interfaceName)
                Logger.debug("  添加继承关系: $interfaceName -> ${sealedInterface.name}", 8)
            }
        } else {
            Logger.debug("  InheritanceAnalyzer 未初始化，跳过继承关系更新", 8)
        }

        // 更新成员接口的父接口
        interfaceNames.forEach { interfaceName ->
            updateInterfaceParent(interfaceName, sealedInterface.name, context)
        }

        Logger.debug("  ✓ 密封接口继承关系处理完成: ${sealedInterface.name}", 6)
    }

    /**
     * 更新接口的父接口
     */
    private fun updateInterfaceParent(
        interfaceName: String,
        parentInterfaceName: String,
        context: TransformContext
    ) {
        Logger.debug("  更新接口父接口: $interfaceName -> $parentInterfaceName", 8)

        // 查找接口声明
        val allClassDecls = context.getAllClassDecls()
        val interfaceDecl = allClassDecls.find { it.name == interfaceName && it.modifier == ClassModifier.Interface }

        if (interfaceDecl != null) {
            // 检查是否已经继承了该父接口
            val alreadyInherits = interfaceDecl.parents.any { parent ->
                when (parent) {
                    is dev.yidafu.swc.generator.adt.kotlin.KotlinType.Simple -> parent.name == parentInterfaceName
                    else -> false
                }
            }

            if (!alreadyInherits) {
                // 添加父接口
                val newParent = dev.yidafu.swc.generator.adt.kotlin.KotlinType.Simple(parentInterfaceName)
                val updatedInterface = interfaceDecl.copy(
                    parents = interfaceDecl.parents + newParent
                )

                // 更新接口声明
                context.updateClassDecl(updatedInterface)
                Logger.debug("    ✓ 接口 $interfaceName 现在继承 $parentInterfaceName", 10)
            } else {
                Logger.debug("    - 接口 $interfaceName 已经继承 $parentInterfaceName", 10)
            }
        } else {
            Logger.warn("  未找到接口声明: $interfaceName")
        }
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
