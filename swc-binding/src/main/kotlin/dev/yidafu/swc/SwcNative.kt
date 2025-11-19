package dev.yidafu.swc

import dev.yidafu.swc.generated.* // ktlint-disable no-wildcard-imports
import kotlinx.serialization.encodeToString
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.jvm.JvmOverloads
import kotlin.jvm.Throws

/**
 * Callback interface for async operations.
 *
 * This interface is Java-friendly and can be implemented in Java code
 * using anonymous inner classes or lambda expressions (Java 8+).
 *
 * @example Java usage:
 * ```java
 * SwcCallback callback = new SwcCallback() {
 *     @Override
 *     public void onSuccess(String result) {
 *         System.out.println("Success: " + result);
 *     }
 *
 *     @Override
 *     public void onError(String error) {
 *         System.err.println("Error: " + error);
 *     }
 * };
 * ```
 */
interface SwcCallback {
    /**
     * Called when operation succeeds.
     *
     * @param result JSON string of the result
     */
    fun onSuccess(result: String)

    /**
     * Called when operation fails.
     *
     * @param error Error message describing what went wrong
     */
    fun onError(error: String)
}

/**
 * Main entry point for SWC (Speedy Web Compiler) operations in Kotlin/Java.
 *
 * This class provides both synchronous and asynchronous methods for:
 * - Parsing JavaScript/TypeScript code to AST
 * - Transforming code (transpiling, minifying, etc.)
 * - Printing AST back to code
 * - Minifying code
 *
 * @sample dev.yidafu.swc.sample.parseSyncEsSample
 * @sample dev.yidafu.swc.sample.parseSyncTsSample
 *
 * ## Usage Examples
 *
 * ### Synchronous Parsing
 * ```kotlin
 * val swc = SwcNative()
 * val ast = swc.parseSync(
 *     code = "const x = 42;",
 *     options = esParseOptions { },
 *     filename = "test.js"
 * )
 * ```
 *
 * ### Asynchronous Parsing with Coroutines
 * ```kotlin
 * val swc = SwcNative()
 * val ast = swc.parseAsync(
 *     code = "const x = 42;",
 *     options = esParseOptions { },
 *     filename = "test.js"
 * )
 * ```
 *
 * ### Transforming Code
 * ```kotlin
 * val swc = SwcNative()
 * val output = swc.transformSync(
 *     code = "const arrow = () => 42;",
 *     isModule = false,
 *     options = options {
 *         jsc {
 *             target = JscTarget.ES5
 *         }
 *     }
 * )
 * println(output.code) // Transformed code
 * ```
 */
class SwcNative {
    init {
        try {
            // Try to load the native library from system path
            System.loadLibrary("swc_jni")
        } catch (e: UnsatisfiedLinkError) {
            // If not found, copy from JAR resources to temp directory and load
            val dllPath = DllLoader.copyDll2Temp("swc_jni")
            System.load(dllPath)
        }
    }

    /**
     * external native method. recommend using `parseSync(code: String, options: ParserConfig, filename: String?): Program`
     *
     * [swc#parsesync](https://swc.rs/docs/usage/core#parsesync)
     *
     * you could use this method only when the overhead method was broken
     * @throws [RuntimeException]
     * @sample [dev.yidafu.swc.sample.parseSyncBasicUsage]
     * @throws [SwcAnyException]
     * @param [code] source code
     * @param [options] options json string. see [dev.yidafu.swc.generated.ParserConfig].
     * @param [filename] js file name
     * @return ast tree json string
     */
    @Throws(RuntimeException::class)
    external fun parseSync(
        code: String,
        options: String,
        filename: String?
    ): String

    /**
     * Parse JavaScript/TypeScript code to AST Tree synchronously.
     *
     * [swc#parsesync](https://swc.rs/docs/usage/core#parsesync)
     *
     * This method is Java-friendly and can be called from Java code.
     * For Java usage, you can pass `null` for the filename parameter.
     *
     * @sample dev.yidafu.swc.sample.parseSyncEsSample
     * @sample dev.yidafu.swc.sample.parseSyncTsSample
     * @sample dev.yidafu.swc.sample.parseSyncJsxSample
     * @throws RuntimeException if parsing fails
     * @param code Source code to parse
     * @param options Parser configuration
     * @param filename Source code filename (can be null)
     * @return Parsed AST tree
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * ParserConfig options = // ... create options
     * Program ast = swc.parseSync("const x = 42;", options, null);
     * ```
     */
    @Throws(RuntimeException::class)
    @JvmOverloads
    fun parseSync(
        code: String,
        options: ParserConfig,
        filename: String? = null
    ): Program {
        val optStr = configJson.encodeToString<ParserConfig>(options)
        val output = parseSync(code, optStr, filename)
        println("parseSync ==> $output")
        return parseAstTree(output)
    }

    /**
     * external native method. recommend using  `parseSync(filepath: String, options: ParserConfig): Program`
     *
     * [swc#parseFileSync](https://swc.rs/docs/usage/core#parsefilesync)
     *
     * you could use this method only when the overhead method was broken
     *
     * @throws [RuntimeException]
     * @sample [dev.yidafu.swc.sample.parseFileSyncBasicUsage]
     * @param filepath filepath to source code
     * @param [options] options json string. see [dev.yidafu.swc.generated.ParserConfig].
     * @return ast tree json string
     */
    @Throws(RuntimeException::class)
    external fun parseFileSync(
        filepath: String,
        options: String
    ): String

    /**
     * parse js file to AST Tree
     *
     * [swc#parseFileSync](https://swc.rs/docs/usage/core#parsefilesync)
     *
     * @throws [RuntimeException]
     * @sample dev.yidafu.swc.sample.parseFileSyncEsSample
     * @param [filepath] filepath to source code
     * @param [options] parse options
     * @return ast tree node
     */
    @Throws(RuntimeException::class)
    fun parseFileSync(
        filepath: String,
        options: ParserConfig
    ): Program {
        val optStr = configJson.encodeToString<ParserConfig>(options)
        val output = parseFileSync(filepath, optStr)
        return parseAstTree(output)
    }

//    fun parseFileSync(
//        filepath: String,
//        options: EsParserConfig,
//    ): String {
//        val res = parseFileSync(filepath, configJson.encodeToString<ParserConfig>(options))
//        return res
//    }

    /**
     * External native method for synchronous code transformation.
     * Prefer using the overloaded method with [Options] parameter.
     *
     * @param code Source code to transform
     * @param isModule Whether the code should be treated as a module
     * @param options Transform options as JSON string
     * @return Transform output as JSON string
     * @throws RuntimeException if transformation fails
     */
    @Throws(RuntimeException::class)
    external fun transformSync(
        code: String,
        isModule: Boolean,
        options: String
    ): String

    /**
     * External native method for synchronous file transformation.
     * Prefer using the overloaded method with [Options] parameter.
     *
     * @param filepath Path to source file
     * @param isModule Whether the file should be treated as a module
     * @param options Transform options as JSON string
     * @return Transform output as JSON string
     * @throws RuntimeException if transformation fails
     */
    @Throws(RuntimeException::class)
    external fun transformFileSync(
        filepath: String,
        isModule: Boolean,
        options: String
    ): String

    /**
     * Transform JavaScript/TypeScript code synchronously.
     *
     * This method can transpile code (e.g., ES6+ to ES5), apply transformations,
     * and perform other code modifications based on the provided options.
     *
     * @sample dev.yidafu.swc.sample.transformSyncSample
     *
     * @param code Source code to transform
     * @param isModule Whether the code should be treated as a module (affects parsing)
     * @param options Transform configuration (target, plugins, etc.)
     * @return Transform output containing the transformed code
     * @throws RuntimeException if transformation fails
     *
     * @example
     * ```kotlin
     * val swc = SwcNative()
     * val output = swc.transformSync(
     *     code = "const arrow = () => 42;",
     *     isModule = false,
     *     options = options {
     *         jsc {
     *             target = JscTarget.ES5
     *             parser {
     *                 syntax = Syntax.ECMASCRIPT
     *             }
     *         }
     *     }
     * )
     * println(output.code) // "function arrow() { return 42; }"
     * ```
     */
    @Throws(RuntimeException::class)
    fun transformSync(
        code: String,
        isModule: Boolean,
        options: Options
    ): TransformOutput {
        val optionStr = configJson.encodeToString(options)
        val res = transformSync(code, isModule, optionStr)
        return outputJson.decodeFromString<TransformOutput>(res)
    }

    /**
     * Transform a JavaScript/TypeScript file synchronously.
     *
     * @param filepath Path to source file
     * @param isModule Whether the file should be treated as a module
     * @param options Transform configuration
     * @return Transform output containing the transformed code
     * @throws RuntimeException if transformation fails
     *
     * @example
     * ```kotlin
     * val swc = SwcNative()
     * val output = swc.transformFileSync(
     *     filepath = "src/index.ts",
     *     isModule = true,
     *     options = options {
     *         jsc {
     *             target = JscTarget.ES2020
     *             parser {
     *                 syntax = Syntax.TYPESCRIPT
     *             }
     *         }
     *     }
     * )
     * ```
     */
    @Throws(RuntimeException::class)
    fun transformFileSync(
        filepath: String,
        isModule: Boolean,
        options: Options
    ): TransformOutput {
        val res = transformFileSync(filepath, isModule, configJson.encodeToString(options))
        return outputJson.decodeFromString<TransformOutput>(res)
    }

    /**
     * External native method for synchronous AST printing.
     * Prefer using the overloaded method with [Program] and [Options] parameters.
     *
     * @param program AST program as JSON string
     * @param options Print options as JSON string
     * @return Generated code as JSON string
     * @throws RuntimeException if printing fails
     */
    @Throws(RuntimeException::class)
    external fun printSync(
        program: String,
        options: String
    ): String

    /**
     * Convert AST program to JavaScript/TypeScript code synchronously.
     *
     * This method takes a parsed AST and generates the corresponding source code.
     * Useful for code generation, AST manipulation, and round-trip transformations.
     *
     * @param program Parsed AST program
     * @param options Print configuration (target, minify, source maps, etc.)
     * @return Transform output containing the generated code
     * @throws RuntimeException if printing fails
     *
     * @example
     * ```kotlin
     * val swc = SwcNative()
     * // Parse code to AST
     * val ast = swc.parseSync("const x = 42;", esParseOptions { }, "test.js")
     * // Modify AST if needed...
     * // Print AST back to code
     * val output = swc.printSync(ast, options { })
     * println(output.code) // "const x = 42;"
     * ```
     */
    @Throws(RuntimeException::class)
    fun printSync(
        program: Program,
        options: Options
    ): TransformOutput {
        val pStr = astJson.encodeToString<Program>(program)
        val oStr = configJson.encodeToString(options)
        val res = printSync(pStr, oStr)
        return outputJson.decodeFromString<TransformOutput>(res)
    }

    /**
     * External native method for synchronous code minification.
     * Prefer using the overloaded method with [Program] and [Options] parameters.
     *
     * @param program Code or program as JSON string
     * @param options Minify options as JSON string
     * @return Minified code as JSON string
     * @throws RuntimeException if minification fails
     */
    @Throws(RuntimeException::class)
    external fun minifySync(
        program: String,
        options: String
    ): String

    /**
     * Minify JavaScript/TypeScript code synchronously.
     *
     * This method takes source code as a string and minifies it.
     * Minification reduces code size by removing whitespace, shortening variable names,
     * and applying other optimizations.
     *
     * According to SWC documentation: https://swc.rs/docs/usage/core#minify
     * The first parameter should be source code string, not AST.
     *
     * @param src Source code to minify
     * @param options Optional minify configuration (compress, mangle, etc.)
     * @return Transform output containing the minified code
     * @throws RuntimeException if minification fails
     *
     * @example
     * ```kotlin
     * val swc = SwcNative()
     * val output = swc.minifySync(
     *     src = "function hello() { console.log('Hello World'); }",
     *     options = JsMinifyOptions().apply {
     *         compress = Union.U2<TerserCompressOptions, Boolean>(b = true)
     *         mangle = Union.U2<TerserMangleOptions, Boolean>(b = true)
     *     }
     * )
     * println(output.code) // Minified code
     * ```
     */
    @Throws(RuntimeException::class)
    fun minifySync(
        src: String,
        options: JsMinifyOptions = JsMinifyOptions()
    ): TransformOutput {
        // Minify expects MinifyTarget::Single(String), so wrap in quotes
        val codeStr = configJson.encodeToString(src)
        // Serialize JsMinifyOptions to JSON
        val oStr = configJson.encodeToString(options)
        val res = minifySync(codeStr, oStr)
        return outputJson.decodeFromString<TransformOutput>(res)
    }

    // ==================== Async Methods ====================

    /**
     * Native async parse method (callback-based)
     * * This method returns immediately and executes parsing in a background thread.
     * The callback will be invoked when parsing completes or fails.
     * * @param code Source code to parse
     * @param options Parser options JSON string
     * @param filename Source file name
     * @param callback Callback interface to receive results
     */
    external fun parseAsync(
        code: String,
        options: String,
        filename: String?,
        callback: SwcCallback
    )

    /**
     * Native async parse file method (callback-based)
     * * @param filepath Path to source file
     * @param options Parser options JSON string
     * @param callback Callback interface to receive results
     */
    external fun parseFileAsync(
        filepath: String,
        options: String,
        callback: SwcCallback
    )

    /**
     * Native async transform method (callback-based)
     * * @param code Source code or AST JSON
     * @param isModule Whether to treat as module
     * @param options Transform options JSON string
     * @param callback Callback interface to receive results
     */
    external fun transformAsync(
        code: String,
        isModule: Boolean,
        options: String,
        callback: SwcCallback
    )

    /**
     * Native async transform file method (callback-based)
     * * @param filepath Path to source file
     * @param isModule Whether to treat as module
     * @param options Transform options JSON string
     * @param callback Callback interface to receive results
     */
    external fun transformFileAsync(
        filepath: String,
        isModule: Boolean,
        options: String,
        callback: SwcCallback
    )

    /**
     * Native async print method (callback-based)
     * * @param program AST program JSON string
     * @param options Print options JSON string
     * @param callback Callback interface to receive results
     */
    external fun printAsync(
        program: String,
        options: String,
        callback: SwcCallback
    )

    /**
     * Native async minify method (callback-based)
     * * @param program Code or program JSON string
     * @param options Minify options JSON string
     * @param callback Callback interface to receive results
     */
    external fun minifyAsync(
        program: String,
        options: String,
        callback: SwcCallback
    )

    // ==================== Kotlin-friendly Lambda Callbacks ====================

    /**
     * Kotlin-friendly async parse with lambda callbacks.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * @param code Source code to parse
     * @param options Parser configuration
     * @param filename Source file name (can be null)
     * @param onSuccess Called with parsed AST when successful
     * @param onError Called with error message when failed
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.parseAsync("const x = 42;", options, null,
     *     (Program ast) -> System.out.println("Success"),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     */
    @JvmOverloads
    fun parseAsync(
        code: String,
        options: ParserConfig,
        filename: String? = null,
        onSuccess: (Program) -> Unit,
        onError: (String) -> Unit
    ) {
        val optStr = configJson.encodeToString<ParserConfig>(options)

        parseAsync(
            code,
            optStr,
            filename,
            object : SwcCallback {
                override fun onSuccess(result: String) {
                    try {
                        val ast = parseAstTree(result)
                        onSuccess(ast)
                    } catch (e: Exception) {
                        onError("Failed to parse result: ${e.message}")
                    }
                }

                override fun onError(error: String) {
                    onError(error)
                }
            }
        )
    }

    /**
     * Kotlin-friendly async parse file with lambda callbacks.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * @param filepath Path to source file
     * @param options Parser configuration
     * @param onSuccess Called with parsed AST when successful
     * @param onError Called with error message when failed
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.parseFileAsync("path/to/file.js", options,
     *     (Program ast) -> System.out.println("Success"),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     */
    fun parseFileAsync(
        filepath: String,
        options: ParserConfig,
        onSuccess: (Program) -> Unit,
        onError: (String) -> Unit
    ) {
        val optStr = configJson.encodeToString<ParserConfig>(options)

        parseFileAsync(
            filepath,
            optStr,
            object : SwcCallback {
                override fun onSuccess(result: String) {
                    try {
                        val ast = parseAstTree(result)
                        onSuccess(ast)
                    } catch (e: Exception) {
                        onError("Failed to parse result: ${e.message}")
                    }
                }

                override fun onError(error: String) {
                    onError(error)
                }
            }
        )
    }

    /**
     * Kotlin-friendly async transform with lambda callbacks.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * @param code Source code
     * @param isModule Whether to treat as module
     * @param options Transform configuration
     * @param onSuccess Called with transform output when successful
     * @param onError Called with error message when failed
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.transformAsync("const x = 42;", false, options,
     *     (TransformOutput output) -> System.out.println(output.getCode()),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     */
    fun transformAsync(
        code: String,
        isModule: Boolean,
        options: Options,
        onSuccess: (TransformOutput) -> Unit,
        onError: (String) -> Unit
    ) {
        val optionStr = configJson.encodeToString(options)

        transformAsync(
            code,
            isModule,
            optionStr,
            object : SwcCallback {
                override fun onSuccess(result: String) {
                    try {
                        val output = outputJson.decodeFromString<TransformOutput>(result)
                        onSuccess(output)
                    } catch (e: Exception) {
                        onError("Failed to parse result: ${e.message}")
                    }
                }

                override fun onError(error: String) {
                    onError(error)
                }
            }
        )
    }

    /**
     * Kotlin-friendly async transform file with lambda callbacks.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * @param filepath Path to source file
     * @param isModule Whether to treat as module
     * @param options Transform configuration
     * @param onSuccess Called with transform output when successful
     * @param onError Called with error message when failed
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.transformFileAsync("path/to/file.ts", true, options,
     *     (TransformOutput output) -> System.out.println(output.getCode()),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     */
    fun transformFileAsync(
        filepath: String,
        isModule: Boolean,
        options: Options,
        onSuccess: (TransformOutput) -> Unit,
        onError: (String) -> Unit
    ) {
        val optStr = configJson.encodeToString(options)

        transformFileAsync(
            filepath,
            isModule,
            optStr,
            object : SwcCallback {
                override fun onSuccess(result: String) {
                    try {
                        val output = outputJson.decodeFromString<TransformOutput>(result)
                        onSuccess(output)
                    } catch (e: Exception) {
                        onError("Failed to parse result: ${e.message}")
                    }
                }

                override fun onError(error: String) {
                    onError(error)
                }
            }
        )
    }

    /**
     * Kotlin-friendly async print with lambda callbacks.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * @param program AST program
     * @param options Print options
     * @param onSuccess Called with transform output when successful
     * @param onError Called with error message when failed
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.printAsync(program, options,
     *     (TransformOutput output) -> System.out.println(output.getCode()),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     */
    fun printAsync(
        program: Program,
        options: Options,
        onSuccess: (TransformOutput) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val pStr = astJson.encodeToString<Program>(program)
            val oStr = configJson.encodeToString(options)

            printAsync(
                pStr,
                oStr,
                object : SwcCallback {
                    override fun onSuccess(result: String) {
                        try {
                            val output = outputJson.decodeFromString<TransformOutput>(result)
                            onSuccess(output)
                        } catch (e: Exception) {
                            onError("Failed to parse result: ${e.message}")
                        }
                    }

                    override fun onError(error: String) {
                        onError(error)
                    }
                }
            )
        } catch (e: Exception) {
            onError("Failed to serialize program: ${e.message}")
        }
    }

    /**
     * Kotlin-friendly async minify with lambda callbacks.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * According to SWC documentation: https://swc.rs/docs/usage/core#minify
     * The first parameter should be source code string, not AST.
     *
     * @param src Source code to minify
     * @param options Minify options
     * @param onSuccess Called with transform output when successful
     * @param onError Called with error message when failed
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.minifyAsync("function hello() { console.log('Hello'); }", options,
     *     (TransformOutput output) -> System.out.println(output.getCode()),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     */
    fun minifyAsync(
        src: String,
        options: JsMinifyOptions = JsMinifyOptions(),
        onSuccess: (TransformOutput) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            // Minify expects MinifyTarget::Single(String), so wrap in quotes
            val codeStr = configJson.encodeToString(src)
            // Serialize JsMinifyOptions to JSON
            val oStr = configJson.encodeToString(options)

            minifyAsync(
                codeStr,
                oStr,
                object : SwcCallback {
                    override fun onSuccess(result: String) {
                        try {
                            val output = outputJson.decodeFromString<TransformOutput>(result)
                            onSuccess(output)
                        } catch (e: Exception) {
                            onError("Failed to parse result: ${e.message}")
                        }
                    }

                    override fun onError(error: String) {
                        onError(error)
                    }
                }
            )
        } catch (e: Exception) {
            onError("Failed to serialize code: ${e.message}")
        }
    }

    // ==================== Coroutine-based Suspend Functions ====================

    /**
     * Coroutine-based async parse (suspend function).
     *
     * This is the most Kotlin-idiomatic way to use async parsing.
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [parseAsync] method instead.
     *
     * @throws RuntimeException if parsing fails
     * @param code Source code to parse
     * @param options Parser configuration
     * @param filename Source file name (can be null)
     * @return Parsed AST
     */
    suspend fun parseAsync(
        code: String,
        options: ParserConfig,
        filename: String? = null
    ): Program = suspendCoroutine { continuation ->
        parseAsync(
            code = code,
            options = options,
            filename = filename,
            onSuccess = { continuation.resume(it) },
            onError = { continuation.resumeWithException(RuntimeException(it)) }
        )
    }

    /**
     * Coroutine-based async parse file (suspend function).
     *
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [parseFileAsync] method instead.
     *
     * @throws RuntimeException if parsing fails
     * @param filepath Path to source file
     * @param options Parser configuration
     * @return Parsed AST
     */
    suspend fun parseFileAsync(
        filepath: String,
        options: ParserConfig
    ): Program = suspendCoroutine { continuation ->
        parseFileAsync(
            filepath = filepath,
            options = options,
            onSuccess = { continuation.resume(it) },
            onError = { continuation.resumeWithException(RuntimeException(it)) }
        )
    }

    /**
     * Coroutine-based async transform (suspend function).
     *
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [transformAsync] method instead.
     *
     * @throws RuntimeException if transform fails
     * @param code Source code
     * @param isModule Whether to treat as module
     * @param options Transform options
     * @return Transform output
     */
    suspend fun transformAsync(
        code: String,
        isModule: Boolean,
        options: Options
    ): TransformOutput = suspendCoroutine { continuation ->
        transformAsync(
            code = code,
            isModule = isModule,
            options = options,
            onSuccess = { continuation.resume(it) },
            onError = { continuation.resumeWithException(RuntimeException(it)) }
        )
    }

    /**
     * Coroutine-based async transform file (suspend function).
     *
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [transformFileAsync] method instead.
     *
     * @throws RuntimeException if transform fails
     * @param filepath Path to source file
     * @param isModule Whether to treat as module
     * @param options Transform options
     * @return Transform output
     */
    suspend fun transformFileAsync(
        filepath: String,
        isModule: Boolean,
        options: Options
    ): TransformOutput = suspendCoroutine { continuation ->
        transformFileAsync(
            filepath = filepath,
            isModule = isModule,
            options = options,
            onSuccess = { continuation.resume(it) },
            onError = { continuation.resumeWithException(RuntimeException(it)) }
        )
    }

    /**
     * Coroutine-based async print (suspend function).
     *
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [printAsync] method instead.
     *
     * @throws RuntimeException if print fails
     * @param program AST program
     * @param options Print options
     * @return Transform output
     */
    suspend fun printAsync(
        program: Program,
        options: Options
    ): TransformOutput = suspendCoroutine { continuation ->
        printAsync(
            program = program,
            options = options,
            onSuccess = { continuation.resume(it) },
            onError = { continuation.resumeWithException(RuntimeException(it)) }
        )
    }

    /**
     * Coroutine-based async minify (suspend function).
     *
     * This is the most Kotlin-idiomatic way to use async minify.
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [minifyAsync] method instead.
     *
     * According to SWC documentation: https://swc.rs/docs/usage/core#minify
     * The first parameter should be source code string, not AST.
     *
     * @throws RuntimeException if minify fails
     * @param src Source code to minify
     * @param options Minify options
     * @return Transform output
     */
    suspend fun minifyAsync(
        src: String,
        options: JsMinifyOptions = JsMinifyOptions()
    ): TransformOutput = suspendCoroutine { continuation ->
        minifyAsync(
            src = src,
            options = options,
            onSuccess = { continuation.resume(it) },
            onError = { error ->
                continuation.resumeWithException(RuntimeException(error))
            }
        )
    }
}