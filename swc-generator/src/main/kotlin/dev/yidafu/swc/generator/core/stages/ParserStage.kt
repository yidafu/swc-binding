package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.AbstractStage
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.parser.ParseResult
import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.util.Logger

/**
 * 解析阶段
 * 负责解析 TypeScript 定义文件
 */
class ParserStage(
    private val config: Configuration,
    private val container: DependencyContainer
) : AbstractStage<String, ParseResult>() {
    
    override val name: String = "Parser"
    
    override fun doExecute(input: String, context: PipelineContext): GeneratorResult<ParseResult> {
        Logger.debug("开始解析 TypeScript 文件: $input")
        
        val parseResult = container.typeScriptParser.parse(input)
        val result = when {
            parseResult.isSuccess() -> {
                val parsed = parseResult.getOrThrow()
                GeneratorResultFactory.success(ParseResult(parsed.astJsonString, parsed.program, parsed.inputPath, parsed.sourceCode))
            }
            else -> GeneratorResultFactory.failure(ErrorCode.PARSE_ERROR, "Failed to parse input")
        }
        
        result.onSuccess { parseResult ->
            Logger.success("解析完成: ${parseResult.inputPath}")
            // 将解析结果存储到上下文中
            context.setMetadata("parseResult", parseResult)
        }
        
        return result
    }
}

