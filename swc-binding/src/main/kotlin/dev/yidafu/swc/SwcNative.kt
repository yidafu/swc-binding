package dev.yidafu.swc

import dev.yidafu.swc.types.* // ktlint-disable no-wildcard-imports
import kotlinx.serialization.encodeToString
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.jvm.Throws

/**
 * Callback interface for async operations
 */
interface SwcCallback {
    /**
     * Called when operation succeeds
     * @param result JSON string of the result
     */
    fun onSuccess(result: String)

    /**
     * Called when operation fails
     * @param error Error message
     */
    fun onError(error: String)
}

class SwcNative {
    init {
        try {
            // 加载 DLL 文件
            System.loadLibrary("swc_jni")
        } catch (e: UnsatisfiedLinkError) {
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
     * @param [options] options json string. see [dev.yidafu.swc.types.ParserConfig].
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
     * parse js code to AST Tree
     *
     * [swc#parsesync](https://swc.rs/docs/usage/core#parsesync)
     *
     * @sample dev.yidafu.swc.sample.parseSyncEsSample
     * @sample dev.yidafu.swc.sample.parseSyncTsSample
     * @sample dev.yidafu.swc.sample.parseSyncJsxSample
     * @throws [RuntimeException]
     * @param [code] source code
     * @param [options] parse option
     * @param [filename] source code filename
     * @return ast tree
     */
    @Throws(RuntimeException::class)
    fun parseSync(
        code: String,
        options: ParserConfig,
        filename: String?
    ): Program {
        val optStr = configJson.encodeToString<ParserConfig>(options)
        val output = parseSync(code, optStr, filename)
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
     * @param [options] options json string. see [dev.yidafu.swc.types.ParserConfig].
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
     * @throws [RuntimeException]
     *
     */
    @Throws(RuntimeException::class)
    external fun transformSync(
        code: String,
        isModule: Boolean,
        options: String
    ): String

    /**
     * @throws [RuntimeException]
     *
     */
    @Throws(RuntimeException::class)
    external fun transformFileSync(
        filepath: String,
        isModule: Boolean,
        options: String
    ): String

    /**
     * @throws [RuntimeException]
     *
     */
    @Throws(RuntimeException::class)
    fun transformSync(
        code: String,
        isModule: Boolean,
        options: Options
    ): TransformOutput {
        val optionStr = configJson.encodeToString(options)
        val res = transformSync(code, isModule, optionStr)
        return astJson.decodeFromString<TransformOutput>(res)
    }

    /**
     * @throws [RuntimeException]
     *
     */
    @Throws(RuntimeException::class)
    fun transformFileSync(
        filepath: String,
        isModule: Boolean,
        options: Options
    ): TransformOutput {
        val res = transformFileSync(filepath, isModule, configJson.encodeToString(options))
        return astJson.decodeFromString<TransformOutput>(res)
    }

    /**
     * @throws [RuntimeException]
     *
     */
    @Throws(RuntimeException::class)
    external fun printSync(
        program: String,
        options: String
    ): String

    /**
     * @throws [RuntimeException]
     *
     */
    @Throws(RuntimeException::class)
    fun printSync(
        program: Program,
        options: Options
    ): TransformOutput {
        val pStr = astJson.encodeToString(program)
        val oStr = configJson.encodeToString(options)
        val res = printSync(pStr, oStr)
        return astJson.decodeFromString<TransformOutput>(res)
    }

    /**
     * @throws [RuntimeException]
     *
     */
    @Throws(RuntimeException::class)
    external fun minifySync(
        program: String,
        options: String
    ): String

    /**
     * @throws [RuntimeException]
     *
     */
    @Throws(RuntimeException::class)
    fun minifySync(
        program: Program,
        options: Options
    ): TransformOutput {
        // Rust minify expects code string, not AST JSON
        // First convert Program to code using printSync
        val printResult = printSync(program, options)
        val code = printResult.code

        // Minify expects MinifyTarget::Single(String), so wrap in quotes
        val codeStr = configJson.encodeToString(code)
        val oStr = configJson.encodeToString(options)
        val res = minifySync(codeStr, oStr)
        return astJson.decodeFromString<TransformOutput>(res)
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
     * Kotlin-friendly async parse with lambda callbacks
     * * @param code Source code to parse
     * @param options Parser configuration
     * @param filename Source file name
     * @param onSuccess Called with parsed AST when successful
     * @param onError Called with error message when failed
     */
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
     * Kotlin-friendly async parse file with lambda callbacks
     * * @param filepath Path to source file
     * @param options Parser configuration
     * @param onSuccess Called with parsed AST when successful
     * @param onError Called with error message when failed
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
     * Kotlin-friendly async transform with lambda callbacks
     * * @param code Source code
     * @param isModule Whether to treat as module
     * @param options Transform configuration
     * @param onSuccess Called with transform output when successful
     * @param onError Called with error message when failed
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
                        val output = astJson.decodeFromString<TransformOutput>(result)
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
     * Kotlin-friendly async transform file with lambda callbacks
     * * @param filepath Path to source file
     * @param isModule Whether to treat as module
     * @param options Transform configuration
     * @param onSuccess Called with transform output when successful
     * @param onError Called with error message when failed
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
                        val output = astJson.decodeFromString<TransformOutput>(result)
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
     * Kotlin-friendly async print with lambda callbacks
     * * @param program AST program
     * @param options Print options
     * @param onSuccess Called with transform output when successful
     * @param onError Called with error message when failed
     */
    fun printAsync(
        program: Program,
        options: Options,
        onSuccess: (TransformOutput) -> Unit,
        onError: (String) -> Unit
    ) {
        val pStr = astJson.encodeToString(program)
        val oStr = configJson.encodeToString(options)

        printAsync(
            pStr,
            oStr,
            object : SwcCallback {
                override fun onSuccess(result: String) {
                    try {
                        val output = astJson.decodeFromString<TransformOutput>(result)
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
     * Kotlin-friendly async minify with lambda callbacks
     * * @param program AST program
     * @param options Minify options
     * @param onSuccess Called with transform output when successful
     * @param onError Called with error message when failed
     */
    fun minifyAsync(
        program: Program,
        options: Options,
        onSuccess: (TransformOutput) -> Unit,
        onError: (String) -> Unit
    ) {
        // Rust minify expects code string, not AST JSON
        // First convert Program to code using printSync
        val printResult = try {
            printSync(program, options)
        } catch (e: Exception) {
            onError("Failed to convert AST to code: ${e.message}")
            return
        }
        val code = printResult.code

        // Minify expects MinifyTarget::Single(String), so wrap in quotes
        val codeStr = configJson.encodeToString(code)
        val oStr = configJson.encodeToString(options)

        minifyAsync(
            codeStr,
            oStr,
            object : SwcCallback {
                override fun onSuccess(result: String) {
                    try {
                        val output = astJson.decodeFromString<TransformOutput>(result)
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

    // ==================== Coroutine-based Suspend Functions ====================

    /**
     * Coroutine-based async parse (suspend function)
     * * This is the most Kotlin-idiomatic way to use async parsing.
     * * @throws RuntimeException if parsing fails
     * @param code Source code to parse
     * @param options Parser configuration
     * @param filename Source file name
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
     * Coroutine-based async parse file (suspend function)
     * * @throws RuntimeException if parsing fails
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
     * Coroutine-based async transform (suspend function)
     * * @throws RuntimeException if transform fails
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
     * Coroutine-based async transform file (suspend function)
     * * @throws RuntimeException if transform fails
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
     * Coroutine-based async print (suspend function)
     * * @throws RuntimeException if print fails
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
     * Coroutine-based async minify (suspend function)
     * * @throws RuntimeException if minify fails
     * @param program AST program
     * @param options Minify options
     * @return Transform output
     */
    suspend fun minifyAsync(
        program: Program,
        options: Options
    ): TransformOutput = suspendCoroutine { continuation ->
        minifyAsync(
            program = program,
            options = options,
            onSuccess = { continuation.resume(it) },
            onError = { continuation.resumeWithException(RuntimeException(it)) }
        )
    }
}