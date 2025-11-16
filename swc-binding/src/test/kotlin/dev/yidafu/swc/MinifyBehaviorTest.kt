package dev.yidafu.swc

import dev.yidafu.swc.generated.dsl.esParseOptions
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MinifyBehaviorTest : AnnotationSpec() {
    private val swc = SwcNative()

    @Test
    fun `minify reduces size`() {
        val code = """
            function add(a, b) {
                const sum = a + b;
                console.log(sum);
                return sum;
            }
            add(1, 2);
        """.trimIndent()
        val program = swc.parseSync(code, esParseOptions { }, "minify.js")
        val result = swc.minifySync(program, options { })
        assertNotNull(result.code)
        assertTrue(result.code.length < code.length)
    }
}


