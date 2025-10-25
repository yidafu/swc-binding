package dev.yidafu.swc.generator.cli

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generator.adt.result.*
import dev.yidafu.swc.generator.codegen.CodeEmitter
import dev.yidafu.swc.generator.codegen.GeneratorConfig
import dev.yidafu.swc.generator.config.GlobalConfig
import dev.yidafu.swc.generator.parser.TypeScriptParser
import dev.yidafu.swc.generator.transformer.TypeTransformer
import dev.yidafu.swc.generator.util.*
import kotlinx.cli.*
import java.nio.file.Paths
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val parser = ArgParser("SwcGen")

    // 定义命令行参数
    val input by parser.option(
        ArgType.String,
        shortName = "i",
        fullName = "input",
        description = "输入的 TypeScript 定义文件路径 (.d.ts)"
    )

    val verbose by parser.option(
        ArgType.Boolean,
        shortName = "v",
        fullName = "verbose",
        description = "启用详细日志输出"
    ).default(false)

    val debug by parser.option(
        ArgType.Boolean,
        fullName = "debug",
        description = "启用调试日志输出"
    ).default(false)

    val configFile by parser.option(
        ArgType.String,
        shortName = "c",
        fullName = "config",
        description = "配置文件路径（默认: swc-generator-config.yaml）"
    )

    // 位置参数（可选）
    val inputFile by parser.argument(
        ArgType.String,
        description = "输入的 TypeScript 定义文件（也可以用 -i 指定）"
    ).optional()

    try {
        parser.parse(args)

        // 设置日志级别
        if (verbose) {
            System.setProperty("VERBOSE", "true")
        }
        if (debug) {
            System.setProperty("DEBUG", "true")
        }
        GlobalConfig.reload(configFile)

        // 确定输入文件（优先级：-i 参数 > 位置参数 > 配置文件默认值）
        val inputPath = input ?: inputFile ?: GlobalConfig.config.paths.defaultInputPath

        // 使用新架构
        Logger.header("SWC Generator Kotlin (TypeScript ADT Architecture)")
        Logger.separator()

        val generator = SwcGenerator()
        val result = generator.run(inputPath ?: "test-simple.d.ts")

        if (result.isSuccess()) {
            Logger.success("代码生成成功！")
        } else {
            result.onFailure { generatorError ->
                val exception = generatorError.toException()
                Logger.error(exception, "代码生成失败")
            }
            exitProcess(1)
        }

        Logger.separator()
        Logger.header("代码生成完成！")
    } catch (e: IllegalArgumentException) {
        Logger.error(e, "参数错误")
        println()
        println("运行 --help 查看使用方法")
        exitProcess(1)
    } catch (e: Exception) {
        Logger.separator()
        Logger.error(e, "代码生成失败")
        val debugEnabled = System.getProperty("DEBUG")?.toBoolean() ?: false
        if (debugEnabled) {
            e.printStackTrace()
        }
        exitProcess(1)
    }
}

/**
 * 新架构的 swc-generator 主类
 */
class SwcGenerator {
    private val swc = SwcNative()

    private val parser = TypeScriptParser(swc, GlobalConfig.config)
    private val transformer = TypeTransformer(GlobalConfig.config)
    private val emitter = CodeEmitter(
        createEmitterConfig(),
        GlobalConfig.config
    )

    /**
     * 创建发射器配置
     */
    private fun createEmitterConfig(): GeneratorConfig {
        val projectRoot = Paths.get("").toAbsolutePath().parent.toString()
        val outputTypesPath = "$projectRoot/swc-binding/src/main/kotlin/dev/yidafu/swc/generated/types.kt"
        val outputSerializerPath = "$projectRoot/swc-binding/src/main/kotlin/dev/yidafu/swc/generated/serializer.kt"
        val outputDslDir = "$projectRoot/swc-binding/src/main/kotlin/dev/yidafu/swc/generated/dsl"

        return GeneratorConfig(
            outputTypesPath = outputTypesPath,
            outputSerializerPath = outputSerializerPath,
            outputDslDir = outputDslDir,
            dryRun = false
        )
    }

    fun run(inputPath: String): GeneratorResult<Unit> {
        Logger.setTotalSteps(6)
        Logger.startTimer("total")

        return parser.parse(inputPath)
            .flatMap { parseResult ->
                Logger.step("解析 TypeScript 文件...")
                Logger.info("✓ 解析完成: ${parseResult.inputPath}")
                GeneratorResultFactory.success(parseResult)
            }
            .flatMap { parseResult ->
                Logger.step("提取 TypeScript ADT...")
                transformer.transform(parseResult)
            }
            .flatMap { transformResult ->
                Logger.step("生成 Kotlin 代码...")
                emitter.emit(transformResult)
            }
            .also {
                val totalTime = Logger.endTimer("total")
                Logger.info("总耗时: ${totalTime}ms")
            }
    }
}
