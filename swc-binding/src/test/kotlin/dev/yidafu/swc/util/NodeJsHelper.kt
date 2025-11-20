package dev.yidafu.swc.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.net.URLDecoder

/**
 * Helper class for executing Node.js scripts in tests.
 */
object NodeJsHelper {
    private var nodeJsAvailable: Boolean = TODO("initialize me")
    private val scriptPath: String by lazy {
        val resource = NodeJsHelper::class.java.classLoader.getResource("scripts/generate-ast-json.js")
            ?: throw IllegalStateException("Cannot find generate-ast-json.js script")

        // Handle both file:// URLs and regular file paths
        val path = if (resource.protocol == "file") {
            URLDecoder.decode(resource.path, "UTF-8")
        } else {
            resource.file ?: throw IllegalStateException("Cannot get file path from resource: $resource")
        }

        File(path).absolutePath
    }

    /**
     * Check if Node.js is available.
     */
    fun isNodeJsAvailable(): Boolean {
        return try {
            val process = ProcessBuilder("node", "--version").start()
            val exitCode = process.waitFor()
            exitCode == 0
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Execute Node.js script with given mode, code, and options.
     * * @param mode Operation mode: "parse", "transform", or "minify"
     * @param code Source code
     * @param options Options as JSON string
     * @return Output from the script
     * @throws IllegalStateException if Node.js is not available or script execution fails
     */
    fun executeScript(mode: String, code: String, options: String): String {
        if (!isNodeJsAvailable()) {
            throw IllegalStateException("Node.js is not available. Please install Node.js to run e2e tests.")
        }

        try {
            // Escape code as JSON string, options is already a JSON string
            val codeJson = Json.encodeToString(code)
            // Options is already a JSON string, so we just pass it as-is
            val optionsJson = options

            val process = ProcessBuilder(
                "node",
                scriptPath,
                mode,
                codeJson,
                optionsJson
            )
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().readText()
            val errorOutput = process.errorStream.bufferedReader().readText()
            val exitCode = process.waitFor()

            if (exitCode != 0) {
                throw IllegalStateException(
                    "Node.js script failed with exit code $exitCode.\n" +
                        "Error output: $errorOutput\n" +
                        "Output: $output"
                )
            }

            if (errorOutput.isNotEmpty()) {
                // Some warnings might go to stderr, but we'll still return output
                System.err.println("Node.js script warnings: $errorOutput")
            }

            return output.trim()
        } catch (e: Exception) {
            throw IllegalStateException("Failed to execute Node.js script: ${e.message}", e)
        }
    }

    /**
     * Execute parse mode and return AST JSON.
     */
    fun parseCode(code: String, options: String): String {
        return executeScript("parse", code, options)
    }

    /**
     * Execute transform mode and return transformed code.
     */
    fun transformCode(code: String, options: String): String {
        return executeScript("transform", code, options)
    }

    /**
     * Execute minify mode and return minified code.
     */
    fun minifyCode(code: String, options: String): String {
        return executeScript("minify", code, options)
    }

    /**
     * Execute transform-ast mode: transform code then parse the transformed code to get AST JSON.
     * This is used for E2E tests to compare transform results.
     */
    fun transformCodeToAstJson(code: String, options: String): String {
        return executeScript("transform-ast", code, options)
    }

    /**
     * Execute print mode: convert AST JSON to code using @swc/core print method.
     * @param astJson AST JSON string
     * @param options Print options as JSON string
     * @return Generated code string
     */
    fun printCode(astJson: String, options: String): String {
        return executeScript("print", astJson, options)
    }
}
