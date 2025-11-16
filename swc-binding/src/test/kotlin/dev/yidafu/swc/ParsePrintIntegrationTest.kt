package dev.yidafu.swc

import dev.yidafu.swc.generated.Module
import dev.yidafu.swc.generated.dsl.esParseOptions
import dev.yidafu.swc.options
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class ParsePrintIntegrationTest : AnnotationSpec() {
    private val swc = SwcNative()

    @Test
    fun `parse simple js and print roundtrip`() {
        val src = """
            function add(a, b) {
                return a + b
            }
            add(1,2)
        """.trimIndent()
        val mod = swc.parseSync(src, esParseOptions { }, "add.js")
        assertIs<Module>(mod)

        val printed = swc.printSync(
            mod,
            options { }
        )
        assertNotNull(printed.code)
        // 关键语句仍然存在
        assertEquals(true, printed.code.contains("function add"))
        assertEquals(true, printed.code.contains("add(1, 2)"))
    }
}


