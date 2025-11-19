package dev.yidafu.swc

import kotlinx.serialization.Serializable

/**
 * Output from SWC transform, print, or minify operations.
 *
 * This data class contains the result of code transformations:
 * - The transformed/generated code
 * - Optional message (e.g., source map or warnings)
 *
 * This class is fully Java-compatible. Java code can access properties using
 * standard getter methods: `getCode()` and `getMsg()`.
 *
 * @param code The transformed or generated code
 * @param msg Optional message, typically contains source map data or warnings
 *
 * @example Kotlin usage:
 * ```kotlin
 * val output = swc.transformSync(
 *     code = "const arrow = () => 42;",
 *     isModule = false,
 *     options = options { }
 * )
 * println(output.code) // Transformed code
 * output.msg?.let { println("Message: $it") } // Optional message
 * ```
 *
 * @example Java usage:
 * ```java
 * TransformOutput output = swc.transformSync("const arrow = () => 42;", false, options);
 * System.out.println(output.getCode()); // Transformed code
 * if (output.getMsg() != null) {
 *     System.out.println("Message: " + output.getMsg());
 * }
 * ```
 */
@Serializable
data class TransformOutput(val code: String, val msg: String? = null)