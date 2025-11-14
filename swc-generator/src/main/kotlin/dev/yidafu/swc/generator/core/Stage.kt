package dev.yidafu.swc.generator.core

import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory

/**
 * 处理阶段抽象基类
 * 提供通用的阶段实现模板
 */
abstract class AbstractStage<I, O> : Stage<I, O> {
    
    /**
     * 执行前的准备工作
     * 子类可以重写此方法进行初始化
     */
    protected open fun beforeExecute(input: I, context: PipelineContext) {
        // 默认空实现
    }
    
    /**
     * 执行后的清理工作
     * 子类可以重写此方法进行清理
     */
    protected open fun afterExecute(input: I, output: O, context: PipelineContext) {
        // 默认空实现
    }
    
    /**
     * 核心处理逻辑
     * 子类必须实现此方法
     */
    protected abstract fun doExecute(input: I, context: PipelineContext): GeneratorResult<O>
    
    override fun execute(input: I, context: PipelineContext): GeneratorResult<O> {
        beforeExecute(input, context)
        
        val result = doExecute(input, context)
        
        result.onSuccess { output ->
            afterExecute(input, output, context)
        }
        
        return result
    }
}

/**
 * 简单的转换阶段
 * 用于简单的数据转换场景
 */
abstract class TransformStage<I, O> : AbstractStage<I, O>() {
    
    /**
     * 转换逻辑
     */
    protected abstract fun transform(input: I, context: PipelineContext): O
    
    override fun doExecute(input: I, context: PipelineContext): GeneratorResult<O> {
        return try {
            val output = transform(input, context)
            GeneratorResultFactory.success(output)
        } catch (e: Exception) {
            GeneratorResultFactory.failure(
                code = ErrorCode.UNKNOWN,
                message = "Transform failed in stage ${name}: ${e.message}",
                cause = e
            )
        }
    }
}

/**
 * 验证阶段
 * 用于数据验证
 */
abstract class ValidationStage<T> : AbstractStage<T, T>() {
    
    /**
     * 验证逻辑
     */
    protected abstract fun validate(input: T, context: PipelineContext): GeneratorResult<T>
    
    override fun doExecute(input: T, context: PipelineContext): GeneratorResult<T> {
        return validate(input, context)
    }
}
