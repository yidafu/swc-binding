package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import dev.yidafu.swc.util.JsonComparator
import dev.yidafu.swc.util.NodeJsHelper
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.encodeToString

/**
 * E2E tests for parse method - comparing AST JSON from Kotlin and @swc/core
 * Currently skipped - uncomment @Ignore annotation when ready to run
 */
@Ignored
class AstJsonParseE2ETest : ShouldSpec({
    val swcNative = SwcNative()

    /**
     * Convert Kotlin ParserConfig to @swc/core parse options format
     */
    fun convertToSwcParseOptions(options: ParserConfig): String {
        // Helper to get serialized target name
        fun getTargetName(target: JscTarget?): String {
            return target?.let {
                // Use serialization to get the correct serialized name
                // JscTarget uses @SerialName, so we serialize it to get the correct value
                configJson.encodeToString(it)
                    .trim('"') // Remove quotes from JSON string
            } ?: "es5"
        }

        return when (options) {
            is EsParserConfig -> {
                val syntax = "ecmascript"
                val target = getTargetName(options.target)
                // isModule defaults to true for parse options
                val isModule = true
                val comments = options.comments ?: false
                val script = options.script ?: false

                """{"syntax":"$syntax","target":"$target","isModule":$isModule,"comments":$comments,"script":$script}"""
            }
            is TsParserConfig -> {
                val syntax = "typescript"
                val target = getTargetName(options.target)
                // isModule defaults to true for parse options
                val isModule = true
                val comments = options.comments ?: false
                val script = options.script ?: false
                val tsx = options.tsx ?: false
                val decorators = options.decorators ?: false

                """{"syntax":"$syntax","target":"$target","isModule":$isModule,"comments":$comments,"script":$script,"tsx":$tsx,"decorators":$decorators}"""
            }
            else -> {
                """{"syntax":"ecmascript","target":"es5","isModule":true,"comments":false,"script":false}"""
            }
        }
    }

    should("parse simple JavaScript variable declaration") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = "const x = 42;"
        val options = esParseOptions { }

        // Get Kotlin AST JSON
        val kotlinAst = swcNative.parseSync(code, options, "test.js")
        val kotlinJson = astJson.encodeToString<Program>(kotlinAst)

        // Get @swc/core AST JSON
        val swcOptionsJson = convertToSwcParseOptions(options)
        val swcJson = NodeJsHelper.parseCode(code, swcOptionsJson)

        // Compare JSON (ignoring property order)
        val areEqual = JsonComparator.compare(kotlinJson, swcJson)
        if (!areEqual) {
            val differences = JsonComparator.getDifferences(kotlinJson, swcJson)
            println("Differences found:")
            if (differences.isEmpty()) {
                println("  (No differences reported, but comparison returned false)")
                println("Kotlin JSON length: ${kotlinJson.length}")
                println("SWC JSON length: ${swcJson.length}")
            } else {
                differences.forEach { println("  - $it") }
            }
        }
        areEqual shouldBe true
    }

    should("parse JavaScript function") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = """
            function add(a, b) {
                return a + b;
            }
        """.trimIndent()
        val options = esParseOptions { }

        val kotlinAst = swcNative.parseSync(code, options, "test.js")
        val kotlinJson = astJson.encodeToString<Program>(kotlinAst)

        val swcOptionsJson = convertToSwcParseOptions(options)
        val swcJson = NodeJsHelper.parseCode(code, swcOptionsJson)

        val areEqual = JsonComparator.compare(kotlinJson, swcJson)
        if (!areEqual) {
            val differences = JsonComparator.getDifferences(kotlinJson, swcJson)
            println("Differences found:")
            differences.forEach { println("  - $it") }
        }
        areEqual shouldBe true
    }

    should("parse TypeScript interface") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = """
            interface User {
                name: string;
                age: number;
            }
        """.trimIndent()
        val options = tsParseOptions { }

        val kotlinAst = swcNative.parseSync(code, options, "test.ts")
        val kotlinJson = astJson.encodeToString<Program>(kotlinAst)

        val swcOptionsJson = convertToSwcParseOptions(options)
        val swcJson = NodeJsHelper.parseCode(code, swcOptionsJson)

        val areEqual = JsonComparator.compare(kotlinJson, swcJson)
        if (!areEqual) {
            val differences = JsonComparator.getDifferences(kotlinJson, swcJson)
            println("Differences found:")
            differences.forEach { println("  - $it") }
        }
        areEqual shouldBe true
    }

    should("parse TypeScript class") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = """
            class Person {
                name: string;
                constructor(name: string) {
                    this.name = name;
                }
            }
        """.trimIndent()
        val options = tsParseOptions { }

        val kotlinAst = swcNative.parseSync(code, options, "test.ts")
        val kotlinJson = astJson.encodeToString<Program>(kotlinAst)

        val swcOptionsJson = convertToSwcParseOptions(options)
        val swcJson = NodeJsHelper.parseCode(code, swcOptionsJson)

        val areEqual = JsonComparator.compare(kotlinJson, swcJson)
        if (!areEqual) {
            val differences = JsonComparator.getDifferences(kotlinJson, swcJson)
            println("Differences found:")
            differences.forEach { println("  - $it") }
        }
        areEqual shouldBe true
    }

    should("parse JavaScript with comments") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = """
            // This is a comment
            const x = 42; // inline comment
        """.trimIndent()
        val options = esParseOptions {
            comments = true
        }

        val kotlinAst = swcNative.parseSync(code, options, "test.js")
        val kotlinJson = astJson.encodeToString<Program>(kotlinAst)

        val swcOptionsJson = convertToSwcParseOptions(options)
        val swcJson = NodeJsHelper.parseCode(code, swcOptionsJson)

        val areEqual = JsonComparator.compare(kotlinJson, swcJson)
        if (!areEqual) {
            val differences = JsonComparator.getDifferences(kotlinJson, swcJson)
            println("Differences found:")
            differences.forEach { println("  - $it") }
        }
        areEqual shouldBe true
    }

    should("parse empty module") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = ""
        val options = esParseOptions { }

        val kotlinAst = swcNative.parseSync(code, options, "test.js")
        val kotlinJson = astJson.encodeToString<Program>(kotlinAst)

        val swcOptionsJson = convertToSwcParseOptions(options)
        val swcJson = NodeJsHelper.parseCode(code, swcOptionsJson)

        val areEqual = JsonComparator.compare(kotlinJson, swcJson)
        if (!areEqual) {
            val differences = JsonComparator.getDifferences(kotlinJson, swcJson)
            println("Differences found:")
            differences.forEach { println("  - $it") }
        }
        areEqual shouldBe true
    }
})
