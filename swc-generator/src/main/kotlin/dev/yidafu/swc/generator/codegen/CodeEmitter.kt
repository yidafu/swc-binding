package dev.yidafu.swc.generator.codegen

import dev.yidafu.swc.generator.codegen.generator.DslGenerator
import dev.yidafu.swc.generator.codegen.generator.SerializerGenerator
import dev.yidafu.swc.generator.codegen.generator.TypesGenerator
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.result.*
import dev.yidafu.swc.generator.transformer.TransformResult
import dev.yidafu.swc.generator.util.DebugUtils.findDebugTypes
import dev.yidafu.swc.generator.util.Logger
import java.io.File

/**
 * 代码生成器
 *
 * 负责协调生成三种类型的 Kotlin 代码文件：
 * 1. **types.kt** - 包含所有类型定义（接口、类、类型别名等）
 * 2. **serializer.kt** - 包含序列化器定义
 * 3. **DSL 文件** - 包含 DSL 扩展函数和创建函数
 *
 * 支持 dry-run 模式，可以在不实际写入文件的情况下预览生成操作。
 *
 * @param config 生成器配置，包含输出路径和 dry-run 设置
 * @param generatorConfig SWC 生成器配置，包含类型转换规则等
 */
class CodeEmitter(
    private val config: GeneratorConfig,
    private val generatorConfig: SwcGeneratorConfig
) {

    /**
     * 生成所有代码文件
     *
     * 按照以下顺序生成：
     * 1. types.kt - 类型定义文件
     * 2. serializer.kt - 序列化器文件
     * 3. DSL 文件 - DSL 扩展函数文件
     *
     * @param transformResult 转换结果，包含所有需要生成的类声明和类型别名
     * @return 生成结果，成功时返回 [GeneratorResult.success]，失败时返回包含错误信息的 [GeneratorResult.failure]
     */
    fun emit(transformResult: TransformResult): GeneratorResult<Unit> {
        return try {
            Logger.debug("开始生成代码文件...")

            if (!config.dryRun) {
                ensureOutputDirectories()
            }

            // 生成 types.kt
            emitTypes(transformResult)

            // 生成 serializer.kt
            emitSerializer(transformResult)

            // 生成 DSL
            emitDsl(transformResult)

            Logger.success("代码生成完成")
            GeneratorResultFactory.success(Unit)
        } catch (e: Exception) {
            Logger.error("代码生成失败: ${e.message}")
            Logger.error("错误详情: ${e.stackTraceToString()}")
            GeneratorResultFactory.failure(
                code = ErrorCode.CODE_GENERATION_ERROR,
                message = "Failed to emit code: ${e.message}",
                cause = e
            )
        }
    }

    /**
     * 通用的生成函数，消除重复的 dry-run 检查和资源管理代码
     *
     * 此方法统一处理以下逻辑：
     * - dry-run 模式下的日志输出
     * - 实际生成时的资源管理（使用 `use` 确保资源正确关闭）
     * - 错误处理和日志记录
     *
     * @param T 生成器类型，必须是 [AutoCloseable] 的子类
     * @param operationName 操作名称，用于日志输出
     * @param outputPath 输出路径，如果为 null 则跳过生成
     * @param dryRunInfo 在 dry-run 模式下显示的额外信息
     * @param generateAction 创建生成器的函数
     * @param executeAction 执行生成操作的函数
     */
    private fun <T : AutoCloseable> generateWithDryRun(
        operationName: String,
        outputPath: String?,
        dryRunInfo: () -> String? = { null },
        generateAction: (String) -> T,
        executeAction: (T) -> Unit
    ) {
        Logger.debug("生成 $operationName...")

        if (config.dryRun) {
            Logger.info("  [DRY-RUN] 将生成到: $outputPath", 2)
            dryRunInfo()?.let { Logger.info("  [DRY-RUN] $it", 2) }
        } else {
            outputPath?.let { path ->
                generateAction(path).use { generator ->
                    executeAction(generator)
                }
                Logger.success("  ✓ 生成 $operationName")
            }
        }
    }

    /**
     * 生成 types.kt 文件
     *
     * 生成包含所有类型定义的文件，包括：
     * - 接口声明
     * - 类声明（数据类、枚举类、普通类等）
     * - 类型别名
     *
     * @param transformResult 转换结果，包含所有类声明和类型别名
     */
    private fun emitTypes(transformResult: TransformResult) {
        generateWithDryRun<TypesGenerator>(
            operationName = "types.kt",
            outputPath = config.outputTypesPath,
            dryRunInfo = { "类数量: ${transformResult.classDecls.size}" },
            generateAction = { path ->
                // 调试：检查调试类型
                val debugTypes = findDebugTypes(transformResult.classDecls)
                debugTypes.forEach { (typeName, decl) ->
                    Logger.debug("  CodeEmitter 找到 $typeName: modifier=${decl.modifier}", 4)
                }
                TypesGenerator(transformResult.classDecls.toMutableList())
            },
            executeAction = { generator ->
                // 添加类型别名
                transformResult.typeAliases.forEach { typeAlias: KotlinDeclaration.TypeAliasDecl ->
                    generator.addTypeAlias(typeAlias)
                }
                generator.writeToFile(config.outputTypesPath!!)
            }
        )
    }

    /**
     * 生成 serializer.kt 文件
     *
     * 生成包含所有序列化器定义的文件，用于 kotlinx.serialization。
     *
     * @param transformResult 转换结果，包含所有类声明
     */
    private fun emitSerializer(transformResult: TransformResult) {
        generateWithDryRun<SerializerGenerator>(
            operationName = "serializer.kt",
            outputPath = config.outputSerializerPath,
            dryRunInfo = { "类数量: ${transformResult.classDecls.size}" },
            generateAction = { path ->
                SerializerGenerator()
            },
            executeAction = { generator ->
                generator.writeToFile(config.outputSerializerPath!!, transformResult.classDecls)
            }
        )
    }

    /**
     * 生成 DSL 扩展函数文件
     *
     * 为每个接收者类型生成一个 DSL 文件，包含：
     * - 扩展函数（用于构建 AST 节点）
     * - create 函数（用于创建可实例化的节点）
     *
     * @param transformResult 转换结果，包含所有类声明和属性映射
     */
    private fun emitDsl(transformResult: TransformResult) {
        generateWithDryRun<DslGenerator>(
            operationName = "DSL 扩展函数",
            outputPath = config.outputDslDir,
            dryRunInfo = {
                val functionCount = transformResult.classDecls.count { it.modifier in listOf(ClassModifier.Interface, ClassModifier.SealedInterface) }
                "DSL 函数数（估计）: ~$functionCount"
            },
            generateAction = { dir ->
                DslGenerator(transformResult.classDecls, transformResult.classAllPropertiesMap)
            },
            executeAction = { generator ->
                generator.writeToDirectory(config.outputDslDir!!)
            }
        )
    }

    /**
     * 确保所有输出目录存在
     *
     * 为以下路径创建目录：
     * - types.kt 的父目录
     * - serializer.kt 的父目录
     * - DSL 文件的输出目录
     *
     * 如果路径指向文件（以 .kt 结尾），则创建其父目录；
     * 如果路径指向目录，则创建目录本身。
     */
    private fun ensureOutputDirectories() {
        Logger.debug("创建输出目录...")

        config.outputTypesPath?.let { path ->
            val file = File(path)
            // 如果是文件路径（以.kt结尾），创建父目录；否则创建目录本身
            if (path.endsWith(".kt")) {
                file.parentFile?.mkdirs()
            } else {
                file.mkdirs()
            }
        }

        config.outputSerializerPath?.let { File(it).parentFile?.mkdirs() }

        config.outputDslDir?.let { File(it).mkdirs() }
    }
}

/**
 * 生成器配置
 *
 * 包含代码生成器的所有配置选项，包括输出路径和运行模式。
 *
 * @param outputTypesPath types.kt 文件的输出路径，如果为 null 则跳过生成
 * @param outputSerializerPath serializer.kt 文件的输出路径，如果为 null 则跳过生成
 * @param outputDslDir DSL 文件的输出目录，如果为 null 则跳过生成
 * @param dryRun 是否为 dry-run 模式，true 时只输出日志不实际生成文件
 */
data class GeneratorConfig(
    val outputTypesPath: String? = null,
    val outputSerializerPath: String? = null,
    val outputDslDir: String? = null,
    val dryRun: Boolean = false
)
