package dev.yidafu.swc.generator.processor

import dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.adt.result.GeneratorResult
import dev.yidafu.swc.generator.adt.result.GeneratorResultFactory
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.util.Logger

/**
 * 组合 Kotlin ADT 处理器
 * 
 * 将多个处理器组合在一起，按顺序执行
 */
class CombinedKotlinADTProcessor(
    private val processors: List<KotlinADTProcessor>
) : KotlinADTProcessor {
    
    constructor(vararg processors: KotlinADTProcessor) : this(processors.toList())
    
    override fun processDeclarations(
        declarations: List<KotlinDeclaration>,
        config: SwcGeneratorConfig
    ): GeneratorResult<List<KotlinDeclaration>> {
        var currentDeclarations = declarations
        
        processors.forEachIndexed { index, processor ->
            Logger.debug("执行处理器 ${index + 1}/${processors.size}: ${processor::class.simpleName}")
            
            val result = processor.processDeclarations(currentDeclarations, config)
            result.onSuccess { processedDeclarations ->
                currentDeclarations = processedDeclarations
                Logger.debug("处理器 ${index + 1} 完成，处理了 ${processedDeclarations.size} 个声明")
            }.onFailure { error ->
                Logger.warn("处理器 ${index + 1} 失败: ${error.message}")
                // 继续使用当前声明，不中断处理链
            }
        }
        
        return GeneratorResultFactory.success(currentDeclarations)
    }
    
    override fun processDeclaration(
        declaration: KotlinDeclaration,
        config: SwcGeneratorConfig
    ): GeneratorResult<KotlinDeclaration> {
        var currentDeclaration = declaration
        
        processors.forEachIndexed { index, processor ->
            val result = processor.processDeclaration(currentDeclaration, config)
            result.onSuccess { processedDeclaration ->
                currentDeclaration = processedDeclaration
            }.onFailure { error ->
                Logger.warn("处理器 ${index + 1} 处理单个声明失败: ${error.message}")
                // 继续使用当前声明，不中断处理链
            }
        }
        
        return GeneratorResultFactory.success(currentDeclaration)
    }
}
