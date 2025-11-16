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
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.monitor.PerformanceMonitor
import java.io.Closeable
import java.nio.file.Paths
import java.util.LinkedHashMap
import dev.yidafu.swc.generator.util.CacheManager

/**
 * types.kt 生成器（使用 KotlinPoet）
 */
class TypesGenerator(
    private val classDecls: MutableList<KotlinDeclaration.ClassDecl>,
    private val typeAliases: MutableList<KotlinDeclaration.TypeAliasDecl> = mutableListOf(),
    private val writer: GeneratedFileWriter = GeneratedFileWriter(),
    private val interfaceRegistry: InterfaceRegistry = InterfaceRegistry(classDecls),
    private val typeAliasEmitter: TypeAliasEmitter = TypeAliasEmitter(),
    private val interfaceEmitter: InterfaceEmitter = InterfaceEmitter(interfaceRegistry),
    private val concreteClassEmitter: ConcreteClassEmitter = ConcreteClassEmitter(interfaceRegistry),
    private val implementationEmitter: ImplementationEmitter = ImplementationEmitter(interfaceRegistry),
    private val postProcessor: TypesPostProcessor = TypesPostProcessor(),
    private val poetGenerator: PoetGenerator = DefaultPoetGenerator(),
    customStages: List<GenerationStage<TypesGenerationContext>>? = null
) : Closeable {
    private val generationStages: List<GenerationStage<TypesGenerationContext>> =
        customStages ?: listOf(
            TypeAliasStage(typeAliasEmitter),
            InterfaceStage(interfaceEmitter),
            ConcreteClassStage(concreteClassEmitter),
            // 去掉实现类（Impl）生成阶段
            CollectTypesFileStage(postProcessor)
        )
    private val pipeline = GenerationPipeline(generationStages + WriteFilesStage(writer))

    /**
     * 添加 typealias（使用 ADT）
     */
    fun addTypeAlias(typeAlias: KotlinDeclaration.TypeAliasDecl) {
        typeAliases.add(typeAlias)
        Logger.debug("添加 typealias: ${typeAlias.name} = ${typeAlias.type.toTypeString()}", 6)
    }

    /**
     * 添加类声明（可能是枚举类）
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
     */
    fun writeToFile(outputPath: String) {
        PerformanceMonitor.measureTime("生成类型文件") {
            val resolvedOutputPath = Paths.get(outputPath)
            Logger.debug("使用 KotlinPoet 生成拆分后的 types 文件...", 4)

            val dedicatedInterfaces = computeInterfacesWithDedicatedFiles(classDecls)
            val context = TypesGenerationContext(
                input = TypesGenerationInput(
                    classDecls = classDecls,
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
            "kotlin" to "Nothing"
        )
    }

    /**
     * 关闭资源，释放线程池
     */
    override fun close() {
        writer.close()
    }
}
