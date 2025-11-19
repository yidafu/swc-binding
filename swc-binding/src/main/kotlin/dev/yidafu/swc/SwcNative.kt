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
     * Native async parse method (callback-based).
     *
     * This is a low-level native method that accepts JSON strings. Prefer using
     * the higher-level [parseAsync] method with [ParserConfig] parameter instead.
     *
     * This method returns immediately and executes parsing in a background thread.
     * The callback will be invoked when parsing completes or fails.
     *
     * **Note**: This method is primarily for internal use. Use the Kotlin-friendly
     * overloads with [ParserConfig] or suspend functions for better type safety.
     *
     * @param code Source code to parse
     * @param options Parser options as JSON string (see [ParserConfig])
     * @param filename Source file name (can be null)
     * @param callback Callback interface to receive results
     * @see parseAsync for Kotlin-friendly version with [ParserConfig]
     * @see parseAsync for coroutine-based suspend function version
     */
    external fun parseAsync(
        code: String,
        options: String,
        filename: String?,
        callback: SwcCallback
    )

    /**
     * Native async parse file method (callback-based).
     *
     * This is a low-level native method that accepts JSON strings. Prefer using
     * the higher-level [parseFileAsync] method with [ParserConfig] parameter instead.
     *
     * This method returns immediately and executes file parsing in a background thread.
     * The callback will be invoked when parsing completes or fails.
     *
     * **Note**: This method is primarily for internal use. Use the Kotlin-friendly
     * overloads with [ParserConfig] or suspend functions for better type safety.
     *
     * @param filepath Path to source file
     * @param options Parser options as JSON string (see [ParserConfig])
     * @param callback Callback interface to receive results
     * @see parseFileAsync for Kotlin-friendly version with [ParserConfig]
     * @see parseFileAsync for coroutine-based suspend function version
     */
    external fun parseFileAsync(
        filepath: String,
        options: String,
        callback: SwcCallback
    )

    /**
     * Native async transform method (callback-based).
     *
     * This is a low-level native method that accepts JSON strings. Prefer using
     * the higher-level [transformAsync] method with [Options] parameter instead.
     *
     * This method returns immediately and executes transformation in a background thread.
     * The callback will be invoked when transformation completes or fails.
     *
     * **Note**: This method is primarily for internal use. Use the Kotlin-friendly
     * overloads with [Options] or suspend functions for better type safety.
     *
     * @param code Source code to transform (as string, not AST JSON)
     * @param isModule Whether the code should be treated as a module
     * @param options Transform options as JSON string (see [Options])
     * @param callback Callback interface to receive results
     * @see transformAsync for Kotlin-friendly version with [Options]
     * @see transformAsync for coroutine-based suspend function version
     */
    external fun transformAsync(
        code: String,
        isModule: Boolean,
        options: String,
        callback: SwcCallback
    )

    /**
     * Native async transform file method (callback-based).
     *
     * This is a low-level native method that accepts JSON strings. Prefer using
     * the higher-level [transformFileAsync] method with [Options] parameter instead.
     *
     * This method returns immediately and executes file transformation in a background thread.
     * The callback will be invoked when transformation completes or fails.
     *
     * **Note**: This method is primarily for internal use. Use the Kotlin-friendly
     * overloads with [Options] or suspend functions for better type safety.
     *
     * @param filepath Path to source file
     * @param isModule Whether the file should be treated as a module
     * @param options Transform options as JSON string (see [Options])
     * @param callback Callback interface to receive results
     * @see transformFileAsync for Kotlin-friendly version with [Options]
     * @see transformFileAsync for coroutine-based suspend function version
     */
    external fun transformFileAsync(
        filepath: String,
        isModule: Boolean,
        options: String,
        callback: SwcCallback
    )

    /**
     * Native async print method (callback-based).
     *
     * This is a low-level native method that accepts JSON strings. Prefer using
     * the higher-level [printAsync] method with [Program] and [Options] parameters instead.
     *
     * This method returns immediately and executes AST printing in a background thread.
     * The callback will be invoked when printing completes or fails.
     *
     * **Note**: This method is primarily for internal use. Use the Kotlin-friendly
     * overloads with [Program] and [Options] or suspend functions for better type safety.
     *
     * @param program AST program as JSON string
     * @param options Print options as JSON string (see [Options])
     * @param callback Callback interface to receive results
     * @see printAsync for Kotlin-friendly version with [Program] and [Options]
     * @see printAsync for coroutine-based suspend function version
     */
    external fun printAsync(
        program: String,
        options: String,
        callback: SwcCallback
    )

    /**
     * Native async minify method (callback-based).
     *
     * This is a low-level native method that accepts JSON strings. Prefer using
     * the higher-level [minifyAsync] method with [JsMinifyOptions] parameter instead.
     *
     * This method returns immediately and executes minification in a background thread.
     * The callback will be invoked when minification completes or fails.
     *
     * According to SWC documentation: https://swc.rs/docs/usage/core#minify
     * The first parameter should be source code string (wrapped as JSON string), not AST.
     *
     * **Note**: This method is primarily for internal use. Use the Kotlin-friendly
     * overloads with [JsMinifyOptions] or suspend functions for better type safety.
     *
     * @param program Source code as JSON string (wrapped string, not AST)
     * @param options Minify options as JSON string (see [JsMinifyOptions])
     * @param callback Callback interface to receive results
     * @see minifyAsync for Kotlin-friendly version with [JsMinifyOptions]
     * @see minifyAsync for coroutine-based suspend function version
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
     * This method executes parsing asynchronously in a background thread and invokes
     * the provided callbacks when the operation completes or fails. The calling thread
     * is not blocked.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * **For Kotlin code, prefer using the suspend function [parseAsync] instead**,
     * which provides better integration with coroutines and structured concurrency.
     *
     * @param code Source code to parse
     * @param options Parser configuration (use [esParseOptions] or [tsParseOptions] DSL)
     * @param filename Source file name (can be null, used for error reporting)
     * @param onSuccess Lambda called with parsed [Program] AST when parsing succeeds
     * @param onError Lambda called with error message string when parsing fails
     *
     * @example Kotlin usage:
     * ```kotlin
     * val swc = SwcNative()
     * swc.parseAsync(
     *     code = "const x = 42;",
     *     options = esParseOptions { },
     *     filename = "test.js",
     *     onSuccess = { ast -> println("Parsed: ${ast.type}") },
     *     onError = { error -> println("Error: $error") }
     * )
     * ```
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.parseAsync("const x = 42;", options, null,
     *     (Program ast) -> System.out.println("Success"),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     *
     * @see parseAsync for coroutine-based suspend function version (recommended for Kotlin)
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
     * This method reads a file from disk and parses it asynchronously in a background thread.
     * The callbacks are invoked when the operation completes or fails. The calling thread
     * is not blocked.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * **For Kotlin code, prefer using the suspend function [parseFileAsync] instead**,
     * which provides better integration with coroutines and structured concurrency.
     *
     * @param filepath Absolute or relative path to the source file
     * @param options Parser configuration (use [esParseOptions] or [tsParseOptions] DSL)
     * @param onSuccess Lambda called with parsed [Program] AST when parsing succeeds
     * @param onError Lambda called with error message string when parsing fails
     *
     * @example Kotlin usage:
     * ```kotlin
     * val swc = SwcNative()
     * swc.parseFileAsync(
     *     filepath = "src/index.ts",
     *     options = tsParseOptions { tsx = true },
     *     onSuccess = { ast -> println("Parsed: ${ast.type}") },
     *     onError = { error -> println("Error: $error") }
     * )
     * ```
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.parseFileAsync("path/to/file.js", options,
     *     (Program ast) -> System.out.println("Success"),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     *
     * @see parseFileAsync for coroutine-based suspend function version (recommended for Kotlin)
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
     * This method executes code transformation asynchronously in a background thread and invokes
     * the provided callbacks when the operation completes or fails. The calling thread
     * is not blocked.
     *
     * Transformation can include transpiling (e.g., ES6+ to ES5), applying plugins,
     * minification, and other code modifications based on the provided options.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * **For Kotlin code, prefer using the suspend function [transformAsync] instead**,
     * which provides better integration with coroutines and structured concurrency.
     *
     * @param code Source code to transform
     * @param isModule Whether the code should be treated as a module (affects parsing behavior)
     * @param options Transform configuration (use [options] DSL to create)
     * @param onSuccess Lambda called with [TransformOutput] containing transformed code when successful
     * @param onError Lambda called with error message string when transformation fails
     *
     * @example Kotlin usage:
     * ```kotlin
     * val swc = SwcNative()
     * swc.transformAsync(
     *     code = "const arrow = () => 42;",
     *     isModule = false,
     *     options = options {
     *         jsc {
     *             target = JscTarget.ES5
     *         }
     *     },
     *     onSuccess = { output -> println(output.code) },
     *     onError = { error -> println("Error: $error") }
     * )
     * ```
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.transformAsync("const x = 42;", false, options,
     *     (TransformOutput output) -> System.out.println(output.getCode()),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     *
     * @see transformAsync for coroutine-based suspend function version (recommended for Kotlin)
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
     * This method reads a file from disk and transforms it asynchronously in a background thread.
     * The callbacks are invoked when the operation completes or fails. The calling thread
     * is not blocked.
     *
     * Transformation can include transpiling (e.g., TypeScript to JavaScript), applying plugins,
     * minification, and other code modifications based on the provided options.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * **For Kotlin code, prefer using the suspend function [transformFileAsync] instead**,
     * which provides better integration with coroutines and structured concurrency.
     *
     * @param filepath Absolute or relative path to the source file
     * @param isModule Whether the file should be treated as a module (affects parsing behavior)
     * @param options Transform configuration (use [options] DSL to create)
     * @param onSuccess Lambda called with [TransformOutput] containing transformed code when successful
     * @param onError Lambda called with error message string when transformation fails
     *
     * @example Kotlin usage:
     * ```kotlin
     * val swc = SwcNative()
     * swc.transformFileAsync(
     *     filepath = "src/index.ts",
     *     isModule = true,
     *     options = options {
     *         jsc {
     *             target = JscTarget.ES2020
     *             parser {
     *                 syntax = Syntax.TYPESCRIPT
     *             }
     *         }
     *     },
     *     onSuccess = { output -> println(output.code) },
     *     onError = { error -> println("Error: $error") }
     * )
     * ```
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.transformFileAsync("path/to/file.ts", true, options,
     *     (TransformOutput output) -> System.out.println(output.getCode()),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     *
     * @see transformFileAsync for coroutine-based suspend function version (recommended for Kotlin)
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
     * This method converts an AST program to source code asynchronously in a background thread.
     * The callbacks are invoked when the operation completes or fails. The calling thread
     * is not blocked.
     *
     * This is useful for code generation, AST manipulation, and round-trip transformations
     * where you parse code, modify the AST, and then print it back to code.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * **For Kotlin code, prefer using the suspend function [printAsync] instead**,
     * which provides better integration with coroutines and structured concurrency.
     *
     * @param program Parsed AST [Program] to convert to source code
     * @param options Print configuration (use [options] DSL to create)
     * @param onSuccess Lambda called with [TransformOutput] containing generated code when successful
     * @param onError Lambda called with error message string when printing fails
     *
     * @example Kotlin usage:
     * ```kotlin
     * val swc = SwcNative()
     * val ast = swc.parseSync("const x = 42;", esParseOptions { }, "test.js")
     * // Modify AST if needed...
     * swc.printAsync(
     *     program = ast,
     *     options = options { },
     *     onSuccess = { output -> println(output.code) },
     *     onError = { error -> println("Error: $error") }
     * )
     * ```
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.printAsync(program, options,
     *     (TransformOutput output) -> System.out.println(output.getCode()),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     *
     * @see printAsync for coroutine-based suspend function version (recommended for Kotlin)
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
     * This method minifies JavaScript/TypeScript code asynchronously in a background thread.
     * The callbacks are invoked when the operation completes or fails. The calling thread
     * is not blocked.
     *
     * Minification reduces code size by removing whitespace, shortening variable names,
     * and applying other optimizations while preserving functionality.
     *
     * According to SWC documentation: https://swc.rs/docs/usage/core#minify
     * The first parameter should be source code string, not AST.
     *
     * This method is also Java-friendly. Java code can use anonymous inner classes
     * or lambda expressions (Java 8+) for the callbacks.
     *
     * **For Kotlin code, prefer using the suspend function [minifyAsync] instead**,
     * which provides better integration with coroutines and structured concurrency.
     *
     * @param src Source code to minify (as string, not AST)
     * @param options Minify configuration (compress, mangle, etc.)
     * @param onSuccess Lambda called with [TransformOutput] containing minified code when successful
     * @param onError Lambda called with error message string when minification fails
     *
     * @example Kotlin usage:
     * ```kotlin
     * val swc = SwcNative()
     * swc.minifyAsync(
     *     src = "function hello() { console.log('Hello World'); }",
     *     options = JsMinifyOptions(),
     *     onSuccess = { output -> println(output.code) },
     *     onError = { error -> println("Error: $error") }
     * )
     * ```
     *
     * @example Java usage:
     * ```java
     * SwcNative swc = new SwcNative();
     * swc.minifyAsync("function hello() { console.log('Hello'); }", options,
     *     (TransformOutput output) -> System.out.println(output.getCode()),
     *     (String error) -> System.err.println("Error: " + error)
     * );
     * ```
     *
     * @see minifyAsync for coroutine-based suspend function version (recommended for Kotlin)
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
     * This is the most Kotlin-idiomatic way to use async parsing. It integrates seamlessly
     * with Kotlin coroutines and structured concurrency, allowing you to use `await` syntax
     * and cancellation support.
     *
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [parseAsync] method instead.
     *
     * @param code Source code to parse
     * @param options Parser configuration (use [esParseOptions] or [tsParseOptions] DSL)
     * @param filename Source file name (can be null, used for error reporting)
     * @return Parsed [Program] AST
     * @throws RuntimeException if parsing fails
     *
     * @example
     * ```kotlin
     * import kotlinx.coroutines.*
     *
     * suspend fun parseCode() {
     *     val swc = SwcNative()
     *     val ast = swc.parseAsync(
     *         code = "const x = 42;",
     *         options = esParseOptions { },
     *         filename = "test.js"
     *     )
     *     println("Parsed: ${ast.type}")
     * }
     *
     * // Usage
     * runBlocking {
     *     parseCode()
     * }
     * ```
     *
     * @see parseAsync for callback-based version (for Java compatibility)
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
     * This method reads a file from disk and parses it asynchronously using coroutines.
     * It integrates seamlessly with Kotlin coroutines and structured concurrency.
     *
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [parseFileAsync] method instead.
     *
     * @param filepath Absolute or relative path to the source file
     * @param options Parser configuration (use [esParseOptions] or [tsParseOptions] DSL)
     * @return Parsed [Program] AST
     * @throws RuntimeException if parsing fails or file cannot be read
     *
     * @example
     * ```kotlin
     * import kotlinx.coroutines.*
     *
     * suspend fun parseFile() {
     *     val swc = SwcNative()
     *     val ast = swc.parseFileAsync(
     *         filepath = "src/index.ts",
     *         options = tsParseOptions { tsx = true }
     *     )
     *     println("Parsed: ${ast.type}")
     * }
     *
     * // Usage
     * runBlocking {
     *     parseFile()
     * }
     * ```
     *
     * @see parseFileAsync for callback-based version (for Java compatibility)
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
     * This method transforms code asynchronously using coroutines. Transformation can include
     * transpiling (e.g., ES6+ to ES5), applying plugins, minification, and other code modifications.
     * It integrates seamlessly with Kotlin coroutines and structured concurrency.
     *
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [transformAsync] method instead.
     *
     * @param code Source code to transform
     * @param isModule Whether the code should be treated as a module (affects parsing behavior)
     * @param options Transform configuration (use [options] DSL to create)
     * @return [TransformOutput] containing the transformed code
     * @throws RuntimeException if transformation fails
     *
     * @example
     * ```kotlin
     * import kotlinx.coroutines.*
     *
     * suspend fun transformCode() {
     *     val swc = SwcNative()
     *     val output = swc.transformAsync(
     *         code = "const arrow = () => 42;",
     *         isModule = false,
     *         options = options {
     *             jsc {
     *                 target = JscTarget.ES5
     *             }
     *         }
     *     )
     *     println(output.code) // Transformed code
     * }
     *
     * // Usage
     * runBlocking {
     *     transformCode()
     * }
     * ```
     *
     * @see transformAsync for callback-based version (for Java compatibility)
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
     * This method reads a file from disk and transforms it asynchronously using coroutines.
     * Transformation can include transpiling (e.g., TypeScript to JavaScript), applying plugins,
     * minification, and other code modifications. It integrates seamlessly with Kotlin coroutines
     * and structured concurrency.
     *
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [transformFileAsync] method instead.
     *
     * @param filepath Absolute or relative path to the source file
     * @param isModule Whether the file should be treated as a module (affects parsing behavior)
     * @param options Transform configuration (use [options] DSL to create)
     * @return [TransformOutput] containing the transformed code
     * @throws RuntimeException if transformation fails or file cannot be read
     *
     * @example
     * ```kotlin
     * import kotlinx.coroutines.*
     *
     * suspend fun transformFile() {
     *     val swc = SwcNative()
     *     val output = swc.transformFileAsync(
     *         filepath = "src/index.ts",
     *         isModule = true,
     *         options = options {
     *             jsc {
     *                 target = JscTarget.ES2020
     *                 parser {
     *                     syntax = Syntax.TYPESCRIPT
     *                 }
     *             }
     *         }
     *     )
     *     println(output.code) // Transformed code
     * }
     *
     * // Usage
     * runBlocking {
     *     transformFile()
     * }
     * ```
     *
     * @see transformFileAsync for callback-based version (for Java compatibility)
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
     * This method converts an AST program to source code asynchronously using coroutines.
     * It integrates seamlessly with Kotlin coroutines and structured concurrency.
     *
     * This is useful for code generation, AST manipulation, and round-trip transformations
     * where you parse code, modify the AST, and then print it back to code.
     *
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [printAsync] method instead.
     *
     * @param program Parsed AST [Program] to convert to source code
     * @param options Print configuration (use [options] DSL to create)
     * @return [TransformOutput] containing the generated code
     * @throws RuntimeException if printing fails
     *
     * @example
     * ```kotlin
     * import kotlinx.coroutines.*
     *
     * suspend fun printAst() {
     *     val swc = SwcNative()
     *     val ast = swc.parseSync("const x = 42;", esParseOptions { }, "test.js")
     *     // Modify AST if needed...
     *     val output = swc.printAsync(ast, options { })
     *     println(output.code) // Generated code
     * }
     *
     * // Usage
     * runBlocking {
     *     printAst()
     * }
     * ```
     *
     * @see printAsync for callback-based version (for Java compatibility)
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
     * This is the most Kotlin-idiomatic way to use async minification. It integrates seamlessly
     * with Kotlin coroutines and structured concurrency, allowing you to use `await` syntax
     * and cancellation support.
     *
     * Minification reduces code size by removing whitespace, shortening variable names,
     * and applying other optimizations while preserving functionality.
     *
     * According to SWC documentation: https://swc.rs/docs/usage/core#minify
     * The first parameter should be source code string, not AST.
     *
     * **Note**: This method cannot be called directly from Java.
     * Java code should use the callback-based [minifyAsync] method instead.
     *
     * @param src Source code to minify (as string, not AST)
     * @param options Minify configuration (compress, mangle, etc.)
     * @return [TransformOutput] containing the minified code
     * @throws RuntimeException if minification fails
     *
     * @example
     * ```kotlin
     * import kotlinx.coroutines.*
     *
     * suspend fun minifyCode() {
     *     val swc = SwcNative()
     *     val output = swc.minifyAsync(
     *         src = "function hello() { console.log('Hello World'); }",
     *         options = JsMinifyOptions()
     *     )
     *     println(output.code) // Minified code
     * }
     *
     * // Usage
     * runBlocking {
     *     minifyCode()
     * }
     * ```
     *
     * @see minifyAsync for callback-based version (for Java compatibility)
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