package dev.yidafu.swc.generator.result

/**
 * 统一的结果类型，用于处理成功和失败的情况
 */
sealed class GeneratorResult<out T> {
    data class Success<T>(val value: T) : GeneratorResult<T>()
    data class Failure(val error: GeneratorError) : GeneratorResult<Nothing>()

    /**
     * 映射成功值
     */
    fun <R> map(transform: (T) -> R): GeneratorResult<R> = when (this) {
        is Success -> Success(transform(value))
        is Failure -> this
    }

    /**
     * 扁平映射，用于链式操作
     */
    fun <R> flatMap(transform: (T) -> GeneratorResult<R>): GeneratorResult<R> = when (this) {
        is Success -> transform(value)
        is Failure -> this
    }

    /**
     * 获取成功值或 null
     */
    fun getOrNull(): T? = when (this) {
        is Success -> value
        is Failure -> null
    }

    /**
     * 获取成功值或默认值
     */
    fun getOrDefault(default: @UnsafeVariance T): T = when (this) {
        is Success -> value
        is Failure -> default
    }

    /**
     * 获取成功值或抛出异常
     */
    fun getOrThrow(): T = when (this) {
        is Success -> value
        is Failure -> throw error.toException()
    }

    /**
     * 处理成功情况
     */
    fun onSuccess(action: (T) -> Unit): GeneratorResult<T> {
        if (this is Success) {
            action(value)
        }
        return this
    }

    /**
     * 处理失败情况
     */
    fun onFailure(action: (GeneratorError) -> Unit): GeneratorResult<T> {
        if (this is Failure) {
            action(error)
        }
        return this
    }

    /**
     * 是否为成功
     */
    fun isSuccess(): Boolean = this is Success

    /**
     * 是否为失败
     */
    fun isFailure(): Boolean = this is Failure
}

/**
 * 生成器错误信息
 */
data class GeneratorError(
    val code: ErrorCode,
    val message: String,
    val cause: Throwable? = null,
    val context: Map<String, Any> = emptyMap()
) {
    fun toException(): GeneratorException {
        return GeneratorException(message, cause, code, context)
    }
}


/**
 * 生成器异常
 */
class GeneratorException(
    message: String,
    cause: Throwable? = null,
    val code: ErrorCode,
    val context: Map<String, Any> = emptyMap()
) : Exception(message, cause)

/**
 * 便捷的创建方法
 */
object GeneratorResultFactory {
    fun <T> success(value: T): GeneratorResult<T> = GeneratorResult.Success(value)

    fun <T> failure(code: ErrorCode, message: String, cause: Throwable? = null, context: Map<String, Any> = emptyMap()): GeneratorResult<T> {
        return GeneratorResult.Failure(GeneratorError(code, message, cause, context))
    }

    fun <T> failure(error: GeneratorError): GeneratorResult<T> {
        return GeneratorResult.Failure(error)
    }
}
