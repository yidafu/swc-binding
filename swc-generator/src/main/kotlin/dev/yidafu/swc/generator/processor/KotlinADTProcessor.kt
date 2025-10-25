package dev.yidafu.swc.generator.processor

import dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.adt.result.GeneratorResult
import dev.yidafu.swc.generator.adt.result.GeneratorResultFactory
import dev.yidafu.swc.generator.config.SwcGeneratorConfig

/**
 * Kotlin ADT 自定义处理器
 * 
 * 在 Kotlin ADT 生成之后、Kotlin 代码生成之前进行自定义处理
 * 用于处理特殊的业务逻辑，保持 Kotlin ADT 的纯净性
 */
interface KotlinADTProcessor {
    
    /**
     * 处理 Kotlin 声明列表
     * 
     * @param declarations Kotlin 声明列表
     * @param config 生成器配置
     * @return 处理后的 Kotlin 声明列表
     */
    fun processDeclarations(
        declarations: List<KotlinDeclaration>,
        config: SwcGeneratorConfig
    ): GeneratorResult<List<KotlinDeclaration>>
    
    /**
     * 处理单个 Kotlin 声明
     * 
     * @param declaration Kotlin 声明
     * @param config 生成器配置
     * @return 处理后的 Kotlin 声明
     */
    fun processDeclaration(
        declaration: KotlinDeclaration,
        config: SwcGeneratorConfig
    ): GeneratorResult<KotlinDeclaration>
}

/**
 * 默认的空实现处理器
 * 
 * 不进行任何处理，直接返回原始声明
 */
class DefaultKotlinADTProcessor : KotlinADTProcessor {
    
    override fun processDeclarations(
        declarations: List<KotlinDeclaration>,
        config: SwcGeneratorConfig
    ): GeneratorResult<List<KotlinDeclaration>> {
        return GeneratorResultFactory.success(declarations)
    }
    
    override fun processDeclaration(
        declaration: KotlinDeclaration,
        config: SwcGeneratorConfig
    ): GeneratorResult<KotlinDeclaration> {
        return GeneratorResultFactory.success(declaration)
    }
}
