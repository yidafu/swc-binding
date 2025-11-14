package dev.yidafu.swc.generator.core

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.result.GeneratorResult

/**
 * 管道模式核心接口
 * * 定义处理管道的通用行为。管道由多个 [Stage] 组成，
 * 每个阶段按顺序执行，前一阶段的输出作为后一阶段的输入。
 * * @param T 管道的输入类型
 * @param R 管道的输出类型
 * * ## 使用示例
 * ```kotlin
 * val pipeline = GeneratorPipeline(config)
 *     .addStage(ParserStage())
 *     .addStage(ConverterStage())
 *     .addStage(CodeGenStage())
 * * val result = pipeline.execute("input.d.ts")
 * ```
 * * @see Stage
 * @see PipelineContext
 */
interface Pipeline<T, R> {
    /**
     * 添加处理阶段
     * * @param stage 要添加的处理阶段
     * @return 返回管道自身，支持链式调用
     */
    fun addStage(stage: Stage<*, *>): Pipeline<T, R>

    /**
     * 执行管道处理
     * * 按顺序执行所有已添加的阶段，如果任何阶段失败，
     * 管道将立即停止并返回失败结果。
     * * @param input 管道的输入数据
     * @return 包含最终结果或错误信息的 [GeneratorResult]
     */
    fun execute(input: T): GeneratorResult<R>
}

/**
 * 处理阶段接口
 * * 表示管道中的单个处理阶段。每个阶段负责特定的转换步骤，
 * 接收类型为 [I] 的输入并产生类型为 [O] 的输出。
 * 阶段按顺序执行，并通过 [PipelineContext] 共享状态。
 * * @param I 输入类型
 * @param O 输出类型
 * * ## 实现指南
 * 继承 [AbstractStage] 而不是直接实现此接口，以获得默认的
 * 前置/后置处理支持。
 * * ## 使用示例
 * ```kotlin
 * class MyStage(config: Configuration) : AbstractStage<Input, Output>() {
 *     override val name = "MyStage"
 * *     override fun doExecute(input: Input, context: PipelineContext): GeneratorResult<Output> {
 *         // 处理逻辑
 *         return GeneratorResultFactory.success(output)
 *     }
 * }
 * ```
 * * @see Pipeline
 * @see PipelineContext
 * @see AbstractStage
 */
interface Stage<I, O> {
    /**
     * 阶段名称，用于日志和错误报告
     */
    val name: String

    /**
     * 执行当前阶段处理
     * * @param input 输入数据
     * @param context 管道上下文，用于访问配置和共享数据
     * @return 包含处理结果或错误的 [GeneratorResult]
     */
    fun execute(input: I, context: PipelineContext): GeneratorResult<O>
}

/**
 * 管道上下文
 * * 在管道各阶段之间传递共享数据和配置。每个阶段可以通过上下文
 * 访问配置信息，并存储/读取中间结果供后续阶段使用。
 * * ## 使用示例
 * ```kotlin
 * // 在某个阶段中存储数据
 * context.setMetadata("parseResult", result)
 * * // 在后续阶段中读取数据
 * val parseResult = context.getMetadata<ParseResult>("parseResult")
 * ```
 * * @property configuration 生成器配置，所有阶段共享
 * @property metadata 元数据存储，用于阶段间数据传递
 * * @see Pipeline
 * @see Stage
 */
data class PipelineContext(
    val configuration: Configuration,
    val metadata: MutableMap<String, Any> = mutableMapOf()
) {
    /**
     * 获取元数据
     * * @param key 元数据键
     * @return 元数据值，如果不存在或类型不匹配返回 null
     */
    inline fun <reified T> getMetadata(key: String): T? {
        val value = metadata[key] ?: return null
        return value as? T
    }

    /**
     * 设置元数据
     * * @param key 元数据键
     * @param value 元数据值
     */
    fun setMetadata(key: String, value: Any) {
        metadata[key] = value
    }
}
