package dev.yidafu.swc

import dev.yidafu.swc.generated.dsl.esParseOptions
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ErrorBoundaryTest : AnnotationSpec() {
    private val swc = SwcNative()

    @Test
    fun `invalid js throws`() {
        assertFailsWith<RuntimeException> {
            swc.parseSync("val a = 1", esParseOptions { }, "invalid.js")
        }
    }

    @Test
    fun `empty input throws`() {
        assertFailsWith<RuntimeException> {
            swc.parseSync("", esParseOptions { }, "empty.js")
        }
    }
}


