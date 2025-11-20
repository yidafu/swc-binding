package dev.yidafu.swc.e2e

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.Union
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
        val tsCode = $$"""
            interface User {
              name: string
              age: number
            }
            const greet = (u: User) => `Hello, ${u.name}!`;
            export const answer: number = 42;
        """.trimIndent()

        // 1) parse TS -> Program
        val output = swc.transformSync(tsCode, options { 
            isModule = Union.U2.ofA(true)
            filename = "test.js"
            jsc = jscConfig { 
                parser = tsParserConfig {  
                    target = JscTarget.ES2020
                }
                transform = transformConfig {  }
            }
        })
        println("transform result:\n--------------\n${output.code}\n--------------")
        assertNotNull(output.code)

        // 2) minify code -> minified code
        // Use JSON format with filename and module option to allow SWC to recognize module code (export/import)
        val minifyOptions = JsMinifyOptions().apply {
            module = Union.U2.ofA(true)
        }
        val minified = swc.minifySync(mapOf("test.mjs" to output.code), minifyOptions)
        assertNotNull(minified.code)
        assertTrue(minified.code.isNotEmpty())

        // 3) parse minified code -> Program
        val parsedMinified = swc.parseSync(minified.code, esParseOptions { }, "pipeline.min.js")
        assertNotNull(parsedMinified)

        // 4) print pretty code from Program
        val pretty = swc.printSync(parsedMinified as Module, options { })
        assertNotNull(pretty.code)
        assertTrue(pretty.code.isNotEmpty())

    }
})
