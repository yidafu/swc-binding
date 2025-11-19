package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import dev.yidafu.swc.util.NodeJsHelper
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.encodeToString

/**
 * E2E tests for transform method - comparing transformed code from Kotlin and @swc/core
 * Currently skipped - uncomment @Ignore annotation when ready to run
 */
@Ignored
class AstJsonTransformE2ETest : ShouldSpec({
    val swcNative = SwcNative()

    // Helper functions
    fun normalizeCode(code: String): String {
        return code
            .replace("\r\n", "\n")
            .replace("\r", "\n")
            .lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .joinToString("\n")
            .trim()
    }

    fun convertToSwcTransformOptions(options: Options, isModule: Boolean = false): String {
        val jsc = options.jsc
        val parser = jsc?.parser

        // Get serialized target name using configJson
        val target = jsc?.target?.let {
            configJson.encodeToString(it).trim('"')
        } ?: "es5"

        // Determine syntax from parser type
        val syntax = when (parser) {
            is EsParserConfig -> "ecmascript"
            is TsParserConfig -> "typescript"
            else -> "ecmascript"
        }

        val tsx = (parser as? TsParserConfig)?.tsx ?: false
        val decorators = when (parser) {
            is EsParserConfig -> parser.decorators ?: false
            is TsParserConfig -> parser.decorators ?: false
            else -> false
        }
        val jsx = (parser as? EsParserConfig)?.jsx ?: false

        val parserOptions = buildString {
            append("""{"syntax":"$syntax","target":"$target","isModule":$isModule""")
            if (tsx) append(""","tsx":true""")
            if (decorators) append(""","decorators":true""")
            if (jsx) append(""","jsx":true""")
            append("}")
        }

        return """{"jsc":{"parser":$parserOptions,"target":"$target"}}"""
    }

    should("transform ES6 arrow function to ES5") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = "const add = (a, b) => a + b;"
        val options = options {
            jsc = JscConfig().apply {
                target = JscTarget.ES5
                parser = esParseOptions { }
            }
        }

        // Get Kotlin transformed code
        val kotlinOutput = swcNative.transformSync(code, false, options)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        // Get @swc/core transformed code
        val swcOptionsJson = convertToSwcTransformOptions(options)
        val swcCode = normalizeCode(NodeJsHelper.transformCode(code, swcOptionsJson))

        kotlinCode shouldBe swcCode
    }

    should("transform TypeScript to JavaScript") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = """
            interface User {
                name: string;
            }
            const user: User = { name: "John" };
        """.trimIndent()
        val options = options {
            jsc = JscConfig().apply {
                target = JscTarget.ES2020
                parser = tsParseOptions { }
            }
        }

        val kotlinOutput = swcNative.transformSync(code, false, options)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        val swcOptionsJson = convertToSwcTransformOptions(options)
        val swcCode = normalizeCode(NodeJsHelper.transformCode(code, swcOptionsJson))

        kotlinCode shouldBe swcCode
    }

    should("transform with different target") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = "const x = async () => await Promise.resolve(42);"
        val options = options {
            jsc = JscConfig().apply {
                target = JscTarget.ES2015
                parser = esParseOptions { }
            }
        }

        val kotlinOutput = swcNative.transformSync(code, false, options)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        val swcOptionsJson = convertToSwcTransformOptions(options)
        val swcCode = normalizeCode(NodeJsHelper.transformCode(code, swcOptionsJson))

        kotlinCode shouldBe swcCode
    }

    should("transform module code") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = "export const x = 42;"
        val options = options {
            jsc = JscConfig().apply {
                target = JscTarget.ES2020
                parser = esParseOptions { }
            }
        }

        val kotlinOutput = swcNative.transformSync(code, true, options)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        val swcOptionsJson = convertToSwcTransformOptions(options, isModule = true)
        val swcCode = normalizeCode(NodeJsHelper.transformCode(code, swcOptionsJson))

        kotlinCode shouldBe swcCode
    }
})
