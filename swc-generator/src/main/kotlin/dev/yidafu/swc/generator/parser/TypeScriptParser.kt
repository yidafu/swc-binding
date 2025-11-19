package dev.yidafu.swc.generator.parser

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.tsParseOptions
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonArray
import java.io.File
import kotlin.io.path.Path

/**
 * TypeScript 解析器
 * * 负责解析 TypeScript 定义文件为 AST
 */
class TypeScriptParser(
    private val swc: SwcNative,
    private val config: SwcGeneratorConfig
) {
    private val importResolver = ImportResolver()

    /**
     * 解析 TypeScript 文件（支持导入解析）
     */
    fun parse(inputPath: String): GeneratorResult<ParseResult> {
        return parseWithImports(inputPath, mutableSetOf())
    }

    /**
     * 递归解析 TypeScript 文件及其导入
     */
    private fun parseWithImports(inputPath: String, visitedFiles: MutableSet<String>): GeneratorResult<ParseResult> {
        return try {
            // 检查是否已访问过此文件
            if (visitedFiles.contains(inputPath)) {
                Logger.debug("跳过已访问的文件: $inputPath")
                return GeneratorResultFactory.failure(
                    code = ErrorCode.CIRCULAR_IMPORT_ERROR,
                    message = "Circular import detected: $inputPath"
                )
            }

            val file = File(inputPath)
            if (!file.exists()) {
                return GeneratorResultFactory.failure(
                    code = ErrorCode.FILE_IO_ERROR,
                    message = "File not found: $inputPath"
                )
            }

            visitedFiles.add(inputPath)
            val sourceCode = file.readText()
            Logger.debug("文件大小: ${sourceCode.length} 字符")

            // 检测是否是 @swc/types
            val isSwcTypes = inputPath.contains("@swc/types") || inputPath.contains("index.d.ts")
            if (isSwcTypes) {
                Logger.warn("检测到尝试解析 @swc/types 或大型类型文件")
                Logger.warn("这可能因循环依赖导致失败（详见 ARCHITECTURE.md）")
            }

            val optionsJson = buildParserOptions()
            Logger.debug("Parser options: $optionsJson")
            Logger.info("开始解析 TypeScript 文件: ${Path(inputPath).toFile().absolutePath}")
            val jsonString = swc.parseSync(sourceCode, optionsJson, inputPath)
            Logger.debug("解析成功，JSON 长度: ${jsonString.length}")

            val program = AstNode.fromJson(jsonString)
            Logger.debug("Program 类型: ${program.type}")

            // 创建 AST 访问器来提取导入
            val visitor = TsAstVisitor(jsonString)
            visitor.visit()
            val imports = visitor.getImports()

            Logger.info("发现 ${imports.size} 个导入声明")

            // 解析导入的文件
            val importedResults = mutableListOf<ParseResult>()
            if (imports.isNotEmpty()) {
                val importPaths = importResolver.resolveImports(imports, inputPath)
                Logger.info("解析 ${importPaths.size} 个导入文件")

                for (importPath in importPaths) {
                    val importResult = parseWithImports(importPath, visitedFiles)
                    if (importResult.isSuccess()) {
                        importResult.onSuccess { result ->
                            importedResults.add(result)
                        }
                    } else {
                        Logger.warn("导入文件解析失败: $importPath")
                        // 继续处理其他导入，不因单个导入失败而停止
                    }
                }
            }

            // 合并所有解析结果
            val mergedResult = mergeParseResults(
                ParseResult(
                    astJsonString = jsonString,
                    program = program,
                    inputPath = inputPath,
                    sourceCode = sourceCode
                ),
                importedResults
            )

            Logger.debug("合并后的程序包含 ${mergedResult.program.getNodes("body").size} 个声明")
            Logger.debug("导入的文件数量: ${importedResults.size}")

            GeneratorResultFactory.success(mergedResult)
        } catch (e: kotlinx.serialization.SerializationException) {
            Logger.error("序列化错误: ${e.message}")
            handleSerializationError(e, inputPath)
        } catch (e: Exception) {
            Logger.error("解析失败: ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.PARSE_ERROR,
                message = "Failed to parse TypeScript file: $inputPath",
                cause = e,
                context = mapOf("inputPath" to inputPath)
            )
        }
    }

    /**
     * 构建解析器选项
     */
    private fun buildParserOptions(): String {
        return Json.encodeToString(
            tsParseOptions {
                tsx = false
                decorators = true
                comments = false
                script = true
            }
        ).replace(
            "}",
            ",\"syntax\":\"typescript\"}"
        )
    }

    /**
     * 合并多个解析结果
     */
    private fun mergeParseResults(mainResult: ParseResult, importedResults: List<ParseResult>): ParseResult {
        if (importedResults.isEmpty()) {
            return mainResult
        }

        // 合并所有 AST JSON 字符串
        val allAstJsonStrings = listOf(mainResult.astJsonString) + importedResults.map { it.astJsonString }

        // 合并所有源代码
        val allSourceCodes = listOf(mainResult.sourceCode) + importedResults.map { it.sourceCode }

        // 合并所有文件路径
        val allInputPaths = listOf(mainResult.inputPath) + importedResults.map { it.inputPath }

        // 创建合并的 AST 程序
        val mergedProgram = createMergedProgram(mainResult.program, importedResults.map { it.program })

        return ParseResult(
            astJsonString = mainResult.astJsonString, // 使用主文件的 AST JSON
            program = mergedProgram,
            inputPath = mainResult.inputPath,
            sourceCode = mainResult.sourceCode,
            importedFiles = importedResults
        )
    }

    /**
     * 创建合并的 AST 程序
     */
    private fun createMergedProgram(mainProgram: AstNode, importedPrograms: List<AstNode>): AstNode {
        // 合并所有程序的主体
        val allBodies = mutableListOf<AstNode>()

        // 添加主程序的主体
        val mainBody = mainProgram.getNodes("body")
        allBodies.addAll(mainBody)

        // 添加导入程序的主体
        for (importedProgram in importedPrograms) {
            val importedBody = importedProgram.getNodes("body")
            allBodies.addAll(importedBody)
        }

        // 创建新的合并程序
        val mergedData = mainProgram.toJsonObject().toMutableMap()
        val bodyArray = buildJsonArray {
            allBodies.forEach { body ->
                add(body.toJsonObject())
            }
        }
        mergedData["body"] = bodyArray

        return AstNode(
            type = mainProgram.type,
            data = JsonObject(mergedData)
        )
    }

    /**
     * 处理序列化错误
     */
    private fun handleSerializationError(e: Exception, inputPath: String): GeneratorResult<ParseResult> {
        val isSwcTypes = inputPath.contains("@swc/types") || inputPath.contains("index.d.ts")

        val message = if (isSwcTypes) {
            """
            序列化错误 - 这是循环依赖问题
            
            原因：
              swc-generator-kt 依赖 swc-binding 来解析 TypeScript
              但 swc-binding 的类型定义本身需要从 @swc/types 生成
              这形成了循环依赖
            
            解决方案：
              1. 使用 TypeScript 版本的 swc-generator 生成 @swc/types
                cd ../swc-generator && npm run dev
            
              2. 或者使用 swc-generator-kt 处理您自己的类型文件
                ./gradlew :swc-generator-kt:run --args="-i your-types.d.ts"
            """.trimIndent()
        } else {
            """
            序列化错误
            
            可能原因：
              1. TypeScript 文件使用了 swc-binding 不支持的类型
              2. @swc/types 版本与 swc-binding 不兼容
            
            建议：
              1. 简化您的 TypeScript 类型定义
              2. 使用测试文件验证：
                ./gradlew :swc-generator-kt:run --args="-i test-simple.d.ts"
            """.trimIndent()
        }

        return GeneratorResultFactory.failure(
            code = ErrorCode.SERIALIZATION_ERROR,
            message = message,
            cause = e,
            context = mapOf("inputPath" to inputPath, "isSwcTypes" to isSwcTypes)
        )
    }
}

/**
 * 解析结果
 */
data class ParseResult(
    val astJsonString: String,
    val program: AstNode,
    val inputPath: String,
    val sourceCode: String,
    val importedFiles: List<ParseResult> = emptyList()
)
