package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.AbstractStage
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.parser.ParseResult
import dev.yidafu.swc.generator.parser.TsAstVisitor
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.util.Logger

/**
 * 提取阶段
 * 负责从解析结果中提取 TypeScript 声明
 */
class ExtractorStage(
    private val config: Configuration,
    private val container: DependencyContainer,
    private val visitorFactory: (String) -> TsAstVisitor = ::TsAstVisitor
) : AbstractStage<ParseResult, List<TypeScriptDeclaration>>() {

    override val name: String = "Extractor"

    override fun doExecute(input: ParseResult, context: PipelineContext): GeneratorResult<List<TypeScriptDeclaration>> {
        Logger.debug("开始提取 TypeScript 声明")

        val declarations = mutableListOf<TypeScriptDeclaration>()

        val parseResults = collectParseResults(input)
        Logger.info("  需要处理 ${parseResults.size} 个解析文件")
        parseResults.forEach { parseResult ->
            Logger.info("    • ${parseResult.inputPath}")
            val visitor = visitorFactory(parseResult.astJsonString)
            val extractor = container.createTypeScriptADTExtractor(visitor)
            visitor.visit()

            visitor.getInterfaces().forEach { astNode ->
                val result = extractor.extractInterface(astNode)
                result.onSuccess { declaration ->
                    declarations.add(declaration)
                }
                result.onFailure { error ->
                    Logger.warn("提取接口声明失败 (${parseResult.inputPath}): ${error.message}")
                }
            }

            visitor.getTypeAliases().forEach { astNode ->
                val result = extractor.extractTypeAlias(astNode)
                result.onSuccess { declaration ->
                    declarations.add(declaration)
                }
                result.onFailure { error ->
                    Logger.warn("提取类型别名声明失败 (${parseResult.inputPath}): ${error.message}")
                }
            }
        }

        Logger.success("提取完成: ${declarations.size} 个声明")

        // 将提取结果存储到上下文中
        context.setMetadata("typeScriptDeclarations", declarations)

        return GeneratorResultFactory.success(declarations)
    }

    private fun collectParseResults(root: ParseResult): List<ParseResult> {
        val results = mutableListOf<ParseResult>()
        fun traverse(current: ParseResult) {
            results.add(current)
            current.importedFiles.forEach { traverse(it) }
        }
        traverse(root)
        return results
    }
}
