package dev.yidafu.swc.generator.parser

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generator.adt.result.ErrorCode
import dev.yidafu.swc.generator.adt.result.GeneratorResult
import dev.yidafu.swc.generator.adt.result.GeneratorResultFactory
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.tsParseOptions
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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

    /**
     * 解析 TypeScript 文件
     */
    fun parse(inputPath: String): GeneratorResult<ParseResult> {
        return try {
            val file = File(inputPath)

            if (!file.exists()) {
                return GeneratorResultFactory.failure(
                    code = ErrorCode.FILE_IO_ERROR,
                    message = "File not found: $inputPath"
                )
            }

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

            GeneratorResultFactory.success(
                ParseResult(
                    astJsonString = jsonString,
                    program = program,
                    inputPath = inputPath,
                    sourceCode = sourceCode
                )
            )
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
    val sourceCode: String
)
