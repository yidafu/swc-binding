package dev.yidafu.swc

/**
 * Exception thrown when SWC operations fail.
 *
 * This exception wraps error messages from the native SWC library
 * and provides them as a standard Kotlin exception.
 *
 * @param msg Error message describing what went wrong
 *
 * @example
 * ```kotlin
 * try {
 *     val ast = swc.parseSync(code, options, filename)
 * } catch (e: SwcException) {
 *     println("SWC error: ${e.message}")
 * }
 * ```
 */
class SwcException(msg: String) : RuntimeException(msg)