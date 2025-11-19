package dev.yidafu.swc.generator.di

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.codegen.CodeEmitter
import dev.yidafu.swc.generator.codegen.GeneratorConfig
import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.converter.TypeScriptToKotlinConverter
import dev.yidafu.swc.generator.converter.declaration.InterfaceConverter
import dev.yidafu.swc.generator.converter.declaration.TypeAliasConverter
import dev.yidafu.swc.generator.converter.type.TypeConverter
import dev.yidafu.swc.generator.extractor.TypeScriptADTExtractor
import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.parser.TsAstVisitor
import dev.yidafu.swc.generator.parser.TypeScriptParser
import dev.yidafu.swc.generator.processor.KotlinADTProcessor
import dev.yidafu.swc.generator.processor.KotlinADTProcessorFactory

/**
 * 依赖注入容器
 * * 管理所有组件的生命周期和依赖关系。使用延迟初始化（lazy）
 * 确保组件仅在需要时才创建，减少启动时间和内存占用。
 * * ## 管理的组件类型
 * * 1. **核心组件**
 *    - SwcNative: SWC 原生绑定
 *    - SwcGeneratorConfig: 生成器配置
 *    - InheritanceAnalyzer: 继承关系分析器
 * * 2. **解析器**
 *    - TypeScriptParser: TypeScript 文件解析器
 * * 3. **转换器**
 *    - TypeConverter: 类型转换器
 *    - InterfaceConverter: 接口声明转换器
 *    - TypeAliasConverter: 类型别名转换器
 *    - TypeScriptToKotlinConverter: 主转换器
 * * 4. **处理器**
 *    - KotlinADTProcessor: Kotlin 代码处理器
 * * 5. **代码生成器**
 *    - CodeEmitter: 代码文件生成器
 * * 6. **工具类**
 *    - PerformanceMonitor: 性能监控器（统一管理性能监控和缓存统计）
 * * ## 生命周期
 * * - 所有组件使用 `lazy` 延迟初始化
 * - 首次访问时创建，之后重用同一实例
 * - 容器本身的生命周期由调用者管理
 * * ## 工厂方法
 * * 某些组件需要运行时参数（如 TypeScriptADTExtractor 需要 visitor），
 * 因此提供工厂方法而不是直接提供实例。
 * * ## 使用示例
 * * ```kotlin
 * val container = DependencyContainer(configuration)
 * * // 获取单例组件
 * val parser = container.typeScriptParser
 * * // 使用工厂方法创建组件
 * val extractor = container.createTypeScriptADTExtractor(visitor)
 * ```
 * * @property configuration 生成器配置
 * * @see Configuration
 * @see TypeScriptParser
 * @see TypeScriptToKotlinConverter
 */
class DependencyContainer(
    private val configuration: Configuration
) {

    // 核心组件
    private val _swcNative by lazy { SwcNative() }
    private val _swcGeneratorConfig by lazy { SwcGeneratorConfig() }
    private val _inheritanceAnalyzer by lazy { InheritanceAnalyzer() }

    // 解析器
    private val _typeScriptParser by lazy { TypeScriptParser(_swcNative, _swcGeneratorConfig) }

    // 转换器
    private val _typeConverter by lazy { TypeConverter(configuration) }

    private val _interfaceConverter by lazy { InterfaceConverter(configuration, _inheritanceAnalyzer) }

    private val _typeAliasConverter by lazy { TypeAliasConverter(configuration) }

    // 重要：不要向主转换器注入一个“空”的 InheritanceAnalyzer，
    // 让其基于当前声明集动态构建分析器，避免将所有接口误判为“叶子”从而生成为 class。
    private val _typeScriptToKotlinConverter by lazy { TypeScriptToKotlinConverter(configuration, null) }

    // 处理器
    private val _kotlinADTProcessor by lazy { KotlinADTProcessorFactory.createCombinedProcessor(_swcGeneratorConfig) }

    // 代码生成器
    private val _codeEmitter by lazy {
        CodeEmitter(
            GeneratorConfig(
                outputTypesPath = configuration.output.outputTypesPath,
                outputSerializerPath = configuration.output.outputSerializerPath,
                outputDslDir = configuration.output.outputDslDir,
                dryRun = configuration.output.dryRun
            ),
            _swcGeneratorConfig
        )
    }

    // 公共访问器
    val swcNative: SwcNative get() = _swcNative
    val swcGeneratorConfig: SwcGeneratorConfig get() = _swcGeneratorConfig
    val inheritanceAnalyzer: InheritanceAnalyzer get() = _inheritanceAnalyzer
    val typeScriptParser: TypeScriptParser get() = _typeScriptParser
    val typeConverter: TypeConverter get() = _typeConverter
    val interfaceConverter: InterfaceConverter get() = _interfaceConverter
    val typeAliasConverter: TypeAliasConverter get() = _typeAliasConverter
    val typeScriptToKotlinConverter: TypeScriptToKotlinConverter get() = _typeScriptToKotlinConverter
    val kotlinADTProcessor: KotlinADTProcessor get() = _kotlinADTProcessor
    val codeEmitter: CodeEmitter get() = _codeEmitter

    /**
     * 创建新的 TypeScriptADTExtractor 实例（因为需要不同的 visitor）
     */
    fun createTypeScriptADTExtractor(visitor: TsAstVisitor): TypeScriptADTExtractor {
        return TypeScriptADTExtractor(visitor)
    }

    /**
     * 创建新的 InheritanceAnalyzer 实例（因为需要不同的声明列表）
     */
    fun createInheritanceAnalyzer(declarations: List<TypeScriptDeclaration>): InheritanceAnalyzer {
        return InheritanceAnalyzer(declarations)
    }
}
