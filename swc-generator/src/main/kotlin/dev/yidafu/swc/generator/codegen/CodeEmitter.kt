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
 * * 负责生成 types.kt、serializer.kt 和 DSL 文件
 */
class CodeEmitter(
    private val config: GeneratorConfig,
    private val generatorConfig: SwcGeneratorConfig
) {

    /**
     * 生成代码文件
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
     * 生成 types.kt
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
     * 生成 serializer.kt
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
     * 生成 DSL
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
     * 确保输出目录存在
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
 */
data class GeneratorConfig(
    val outputTypesPath: String? = null,
    val outputSerializerPath: String? = null,
    val outputDslDir: String? = null,
    val dryRun: Boolean = false
)
