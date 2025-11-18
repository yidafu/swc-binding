package dev.yidafu.swc.generator.codegen

import dev.yidafu.swc.generator.codegen.generator.DslGenerator
import dev.yidafu.swc.generator.codegen.generator.SerializerGenerator
import dev.yidafu.swc.generator.codegen.generator.TypesGenerator
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.result.*
import dev.yidafu.swc.generator.transformer.TransformResult
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
     * 生成 types.kt
     */
    private fun emitTypes(transformResult: TransformResult) {
        Logger.debug("生成 types.kt...")

        if (config.dryRun) {
            Logger.info("  [DRY-RUN] 将生成到: ${config.outputTypesPath}", 2)
            Logger.info("  [DRY-RUN] 类数量: ${transformResult.classDecls.size}", 2)
        } else {
            config.outputTypesPath?.let { path ->
                // 调试：检查 ForOfStatement 和 ComputedPropName
                val forOfStatement = transformResult.classDecls.find { it.name.removeSurrounding("`") == "ForOfStatement" }
                val computedPropName = transformResult.classDecls.find { it.name.removeSurrounding("`") == "ComputedPropName" }
                if (forOfStatement != null) {
                    Logger.debug("  CodeEmitter 找到 ForOfStatement: modifier=${forOfStatement.modifier}", 4)
                } else {
                    Logger.warn("  CodeEmitter 未找到 ForOfStatement，总类数: ${transformResult.classDecls.size}")
                }
                if (computedPropName != null) {
                    Logger.debug("  CodeEmitter 找到 ComputedPropName: modifier=${computedPropName.modifier}", 4)
                } else {
                    Logger.warn("  CodeEmitter 未找到 ComputedPropName，总类数: ${transformResult.classDecls.size}")
                }

                TypesGenerator(transformResult.classDecls.toMutableList()).use { generator ->
                    // 添加类型别名
                    transformResult.typeAliases.forEach { typeAlias: KotlinDeclaration.TypeAliasDecl ->
                        generator.addTypeAlias(typeAlias)
                    }
                    generator.writeToFile(path)
                }
                Logger.success("  ✓ 生成 types.kt")
            }
        }
    }

    /**
     * 生成 serializer.kt
     */
    private fun emitSerializer(transformResult: TransformResult) {
        Logger.debug("生成 serializer.kt...")

        if (config.dryRun) {
            Logger.info("  [DRY-RUN] 将生成到: ${config.outputSerializerPath}", 2)
            Logger.info("  [DRY-RUN] 类数量: ${transformResult.classDecls.size}", 2)
        } else {
            config.outputSerializerPath?.let { path ->
                SerializerGenerator().use { generator ->
                    generator.writeToFile(path, transformResult.classDecls)
                }
                Logger.success("  ✓ 生成 serializer.kt")
            }
        }
    }

    /**
     * 生成 DSL
     */
    private fun emitDsl(transformResult: TransformResult) {
        Logger.debug("生成 DSL 扩展函数...")

        if (config.dryRun) {
            Logger.info("  [DRY-RUN] 将生成到: ${config.outputDslDir}", 2)
            val functionCount = transformResult.classDecls.count { it.modifier in listOf(ClassModifier.Interface, ClassModifier.SealedInterface) }
            Logger.info("  [DRY-RUN] DSL 函数数（估计）: ~$functionCount", 2)
        } else {
            config.outputDslDir?.let { dir ->
                DslGenerator(transformResult.classDecls, transformResult.classAllPropertiesMap).use { generator ->
                    generator.writeToDirectory(dir)
                }
                Logger.success("  ✓ 生成 DSL 文件")
            }
        }
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
