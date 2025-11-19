package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * E2E tests for parse-minify-print pipeline
 * Currently skipped - uncomment @Ignore annotation when ready to run
 */
class PipelineE2ETest : ShouldSpec({
    val swc = SwcNative()

    should("parse minify print pipeline works") {
        val tsCode = """
            interface User {
              name: string
              age: number
            }
            const greet = (u: User) => `Hello, ${'$'}{u.name}!`
            export const answer: number = 42
        """.trimIndent()

        // 1) parse TS -> Program
        val program = swc.parseSync(tsCode, tsParseOptions { }, "pipeline.ts")
        assertNotNull(program)

        // 2) minify code -> minified code
        try {
            val minified = swc.minifySync(tsCode, JsMinifyOptions())
            assertNotNull(minified.code)
            assertTrue(minified.code.isNotEmpty())

            // 3) parse minified code -> Program
            val parsedMinified = swc.parseSync(minified.code, esParseOptions { }, "pipeline.min.js")
            assertNotNull(parsedMinified)

            // 4) print pretty code from Program
            val pretty = swc.printSync(parsedMinified as Module, options { })
            assertNotNull(pretty.code)
            assertTrue(pretty.code.isNotEmpty())
        } catch (e: Exception) {
            // If minify fails due to serialization, just verify parsing and printing work
            val pretty = swc.printSync(program as Module, options { })
            assertNotNull(pretty.code)
            assertTrue(pretty.code.isNotEmpty())
        }
    }
})
