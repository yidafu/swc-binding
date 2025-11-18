package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PipelineE2ETest : AnnotationSpec() {
    private val swc = SwcNative()

    @Test
    fun parse_minify_print_pipeline_works() {
        val tsCode = """
            interface User {
              name: string
              age: number
            }
            const greet = (u: User) => `Hello, ${"$"}{u.name}!`
            export const answer: number = 42
        """.trimIndent()

        // 1) parse TS -> Program
        val program = swc.parseSync(tsCode, tsParseOptions { }, "pipeline.ts")
        assertNotNull(program)

        // 2) minify Program -> code
        val minified = swc.minifySync(program, options { })
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
}
