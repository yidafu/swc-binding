package dev.yidafu.swc.e2e

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.Union
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.io.InputStream

/**
 * E2E tests for transform method - comparing transformed code from Kotlin and @swc/core
 * Uses pre-generated @swc/core transform results for comparison
 * Currently skipped - uncomment @Ignore annotation when ready to run
 */
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

    /**
     * Read pre-generated @swc/core transform results from resources directory
     */
    fun readSwcTransformResult(resourceName: String): String {
        val resourcePath = "transform/$resourceName.js"
        val inputStream: InputStream = AstJsonTransformE2ETest::class.java.classLoader
            .getResourceAsStream(resourcePath)
            ?: throw IllegalStateException("Cannot find resource: $resourcePath")
        
        return inputStream.bufferedReader().use { it.readText() }
    }

    /**
     * Extract key part: get code line containing key function call
     * For async/await transform, extract the line with const x = ...
     */
    fun extractKeyPart(code: String): String {
        return code.lines()
            .find { it.contains("const x =") && it.contains("_async_to_generator") }
            ?: throw IllegalStateException("Key part not found in code: const x = ... _async_to_generator")
    }
    
    /**
     * Normalize key part: remove format differences, unify comparison
     */
    fun normalizeKeyPart(part: String): String {
        return part
            .replace(Regex("\\s+"), " ")  // Unify whitespace characters
            .replace("_async_to_generator._", "_async_to_generator")  // Unify function call style
            .replace("_async_to_generator(", "_async_to_generator(")  // Ensure consistent format
            .trim()
    }

    should("transform ES6 arrow function to ES5") {
        val code = "const add = (a, b) => a + b;"
        val options = options {
            jsc = jscConfig {
                target = JscTarget.ES5
                parser = esParseOptions { }
                externalHelpers = false
            }
        }

        // Get Kotlin transformed code
        val kotlinOutput = swcNative.transformSync(code, options)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        // Get @swc/core transformed code from resources
        val swcCode = normalizeCode(readSwcTransformResult("es6-arrow-to-es5"))

        kotlinCode shouldBe swcCode
    }

    should("transform TypeScript to JavaScript") {
        val code = """
            interface User {
                name: string;
            }
            const user: User = { name: "John" };
        """.trimIndent()
        val options = options {
            jsc = jscConfig {
                target = JscTarget.ES2020
                parser = tsParseOptions { }
                externalHelpers = false
            }
            
        }

        val kotlinOutput = swcNative.transformSync(code, options)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        // Get @swc/core transformed code from resources
        val swcCode = normalizeCode(readSwcTransformResult("typescript-to-javascript"))

        kotlinCode shouldBe swcCode
    }

    should("transform with different target") {
        val code = "const x = async () => await Promise.resolve(42);"
        val options = options {
            jsc = jscConfig {
                target = JscTarget.ES2015
                parser = esParseOptions {
                    target = JscTarget.ES2015
                    isModule = Union.U2.ofA(false)
                }
                externalHelpers = false
            }
        }

        val kotlinOutput = swcNative.transformSync(code, options)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        // Get @swc/core transformed code from resources
        val swcCode = normalizeCode(readSwcTransformResult("async-await-es2015"))

        // Extract and compare key part: const x = ()=>_async_to_generator(...)
        val kotlinKeyPart = normalizeKeyPart(extractKeyPart(kotlinCode))
        val swcKeyPart = normalizeKeyPart(extractKeyPart(swcCode))
        
        kotlinKeyPart shouldBe swcKeyPart
    }

    should("transform module code") {
        val code = "export const x = 42;"
        val options = options {
            jsc = jscConfig {
                target = JscTarget.ES2020
                parser = esParseOptions {
                    isModule = Union.U2.ofA(true)
                }
                externalHelpers = false
            }
        }

        val kotlinOutput = swcNative.transformSync(code, options)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        // Get @swc/core transformed code from resources
        val swcCode = normalizeCode(readSwcTransformResult("module-export-es2020"))

        kotlinCode shouldBe swcCode
    }
})
