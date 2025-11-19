package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.codegen.generator.types.ConcreteClassEmitter
import dev.yidafu.swc.generator.codegen.generator.types.ImplementationEmitter
import dev.yidafu.swc.generator.codegen.generator.types.InterfaceEmitter
import dev.yidafu.swc.generator.codegen.generator.types.InterfaceRegistry
import dev.yidafu.swc.generator.codegen.generator.types.TypeAliasEmitter
import dev.yidafu.swc.generator.codegen.generator.types.TypesFileLayout
import dev.yidafu.swc.generator.codegen.generator.types.TypesGenerationContext
import dev.yidafu.swc.generator.codegen.generator.types.TypesGenerationInput
import dev.yidafu.swc.generator.codegen.generator.types.TypesPostProcessor
import dev.yidafu.swc.generator.codegen.generator.types.stages.CollectTypesFileStage
import dev.yidafu.swc.generator.codegen.generator.types.stages.ConcreteClassStage
import dev.yidafu.swc.generator.codegen.generator.types.stages.ImplementationStage
import dev.yidafu.swc.generator.codegen.generator.types.stages.InterfaceStage
import dev.yidafu.swc.generator.codegen.generator.types.stages.TypeAliasStage
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFileWriter
import dev.yidafu.swc.generator.codegen.pipeline.GenerationPipeline
import dev.yidafu.swc.generator.codegen.pipeline.GenerationStage
import dev.yidafu.swc.generator.codegen.pipeline.WriteFilesStage
import dev.yidafu.swc.generator.codegen.poet.PoetConstants
import dev.yidafu.swc.generator.config.TypesImplementationRules
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.monitor.PerformanceMonitor
import dev.yidafu.swc.generator.util.CacheManager
import dev.yidafu.swc.generator.util.DebugUtils.findDebugTypes
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.util.NameUtils.clean
import java.nio.file.Paths
import java.util.LinkedHashMap

/**
 * types.kt 生成器
 *
 * 使用 KotlinPoet 生成包含所有类型定义的 Kotlin 文件。
 *
 * 生成的内容包括：
 * - 类型别名（typealias）
 * - 接口声明（interface）
 * - 实现类（implementation classes）
 * - 具体类（concrete classes）
 *
 * 生成过程通过管道（pipeline）执行，包含以下阶段：
 * 1. [TypeAliasStage] - 生成类型别名
 * 2. [InterfaceStage] - 生成接口
 * 3. [ImplementationStage] - 生成实现类
 * 4. [ConcreteClassStage] - 生成具体类
 * 5. [CollectTypesFileStage] - 收集并后处理文件
 * 6. [WriteFilesStage] - 写入文件
 *
 * @param classDecls 类声明列表
 * @param typeAliases 类型别名列表，默认为空
 * @param writer 文件写入器，默认为 [GeneratedFileWriter]
 * @param interfaceRegistry 接口注册表，用于管理接口关系
 * @param typeAliasEmitter 类型别名生成器
 * @param interfaceEmitter 接口生成器
 * @param concreteClassEmitter 具体类生成器
 * @param implementationEmitter 实现类生成器
 * @param postProcessor 后处理器，用于处理生成的文件
 * @param poetGenerator KotlinPoet 生成器
 * @param customStages 自定义阶段列表，如果为 null 则使用默认阶段
 */
class TypesGenerator(
    private val classDecls: MutableList<KotlinDeclaration.ClassDecl>,
    private val typeAliases: MutableList<KotlinDeclaration.TypeAliasDecl> = mutableListOf(),
    writer: GeneratedFileWriter = GeneratedFileWriter(),
    private val interfaceRegistry: InterfaceRegistry = InterfaceRegistry(classDecls),
    private val typeAliasEmitter: TypeAliasEmitter = TypeAliasEmitter(),
    private val interfaceEmitter: InterfaceEmitter = InterfaceEmitter(interfaceRegistry),
    private val concreteClassEmitter: ConcreteClassEmitter = ConcreteClassEmitter(interfaceRegistry),
    private val implementationEmitter: ImplementationEmitter = ImplementationEmitter(interfaceRegistry),
    private val postProcessor: TypesPostProcessor = TypesPostProcessor(),
    private val poetGenerator: PoetGenerator = DefaultPoetGenerator(),
    customStages: List<GenerationStage<TypesGenerationContext>>? = null
) : BaseGenerator(writer) {
    private val generationStages: List<GenerationStage<TypesGenerationContext>> =
        customStages ?: listOf(
            TypeAliasStage(typeAliasEmitter),
            InterfaceStage(interfaceEmitter),
            ImplementationStage(implementationEmitter),
            ConcreteClassStage(concreteClassEmitter),
            CollectTypesFileStage(postProcessor)
        )
    private val pipeline = GenerationPipeline(generationStages + WriteFilesStage(writer))

    /**
     * 添加类型别名
     *
     * 将类型别名添加到生成列表中，将在生成 types.kt 时包含。
     *
     * @param typeAlias 要添加的类型别名声明
     */
    fun addTypeAlias(typeAlias: KotlinDeclaration.TypeAliasDecl) {
        typeAliases.add(typeAlias)
        Logger.debug("添加 typealias: ${typeAlias.name} = ${typeAlias.type.toTypeString()}", 6)
    }

    /**
     * 添加类声明
     *
     * 将类声明添加到生成列表中，并注册到接口注册表。
     * 支持的类类型包括：接口、密封接口、数据类、枚举类、普通类等。
     *
     * @param classDecl 要添加的类声明
     */
    fun addClassDecl(classDecl: KotlinDeclaration.ClassDecl) {
        classDecls.add(classDecl)
        interfaceRegistry.register(classDecl)
        Logger.debug("添加类声明: ${classDecl.name} (${classDecl.modifier.toModifierString()})", 6)
    }

    // 注意：类型转换现在完全基于 ADT
    // 使用 KotlinPoetConverter.convertType(kotlinType) 进行转换
    // 不再使用基于字符串解析的方式

    /**
     * 生成代码并写入文件
     *
     * 执行完整的生成流程：
     * 1. 过滤需要跳过的类型（如 Identifier、BindingIdentifier 等）
     * 2. 计算需要独立文件的接口
     * 3. 创建生成上下文
     * 4. 执行生成管道
     * 5. 打印缓存统计信息
     *
     * @param outputPath 输出文件路径
     */
    fun writeToFile(outputPath: String) {
        PerformanceMonitor.measureTime("生成类型文件") {
            val resolvedOutputPath = Paths.get(outputPath)
            Logger.debug("使用 KotlinPoet 生成拆分后的 types 文件...", 4)

            // 过滤掉需要手动定义的类型（在 customType.kt 中定义）
            val skippedTypes = setOf("Identifier", "BindingIdentifier", "TemplateLiteral", "TsTemplateLiteralType")
            val filteredClassDecls = classDecls.filter {
                val cleanName = clean(it.name)
                !skippedTypes.contains(cleanName)
            }.toMutableList()
            Logger.debug("跳过类型生成: $skippedTypes (共 ${classDecls.size - filteredClassDecls.size} 个)", 4)

            val dedicatedInterfaces = computeInterfacesWithDedicatedFiles(filteredClassDecls)

            // 调试：检查调试类型
            val debugTypes = findDebugTypes(filteredClassDecls)
            debugTypes.forEach { (typeName, decl) ->
                Logger.debug("  TypesGenerator 找到 $typeName: modifier=${decl.modifier}, name=${decl.name}", 4)
            }
            if (debugTypes.isEmpty() && filteredClassDecls.isNotEmpty()) {
                Logger.debug("  TypesGenerator 未找到调试类型，总类数: ${filteredClassDecls.size}", 4)
            }

            val context = TypesGenerationContext(
                input = TypesGenerationInput(
                    classDecls = filteredClassDecls,
                    typeAliases = typeAliases,
                    outputPath = resolvedOutputPath
                ),
                fileLayout = TypesFileLayout(
                    packageName = PoetConstants.PKG_TYPES,
                    defaultImports = DEFAULT_IMPORTS,
                    originalOutputPath = resolvedOutputPath,
                    interfacesWithDedicatedFiles = dedicatedInterfaces
                ),
                interfaceRegistry = interfaceRegistry,
                poet = poetGenerator
            )
            pipeline.execute(context)
        }

        // 打印缓存统计
        CacheManager.printStats()
    }

    private fun computeInterfacesWithDedicatedFiles(
        declarations: List<KotlinDeclaration.ClassDecl>
    ): Set<String> {
        return declarations
            .filter { it.modifier == ClassModifier.Interface }
            .map { it.name }
            .toSet()
    }

    @Suppress("unused")
    fun createImplementationClass(
        interfaceDecl: KotlinDeclaration.ClassDecl,
        analyzer: InheritanceAnalyzer
    ): KotlinDeclaration.ClassDecl {
        val propertyCache = LinkedHashMap<String, List<KotlinDeclaration.PropertyDecl>>()
        return implementationEmitter.createImplementationClass(interfaceDecl, analyzer, classDecls, propertyCache)
    }

    @Suppress("unused")
    fun processImplementationProperty(
        prop: KotlinDeclaration.PropertyDecl,
        interfaceName: String
    ): KotlinDeclaration.PropertyDecl {
        val rule = TypesImplementationRules.createInterfaceRule(interfaceName)
        return TypesImplementationRules.processImplementationProperty(prop, rule)
    }

    companion object {
        private val DEFAULT_IMPORTS = arrayOf(
            "kotlinx.serialization" to "SerialName",
            "kotlinx.serialization" to "Serializable",
            "kotlinx.serialization.json" to "JsonClassDiscriminator",
            "kotlin" to "Int",
            "kotlin" to "Nothing",
            // 添加 emptySpan 导入，确保所有使用 span 属性的类都能正确导入
            "dev.yidafu.swc" to "emptySpan"
        )
    }
}
