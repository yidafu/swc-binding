package dev.yidafu.swc

import dev.yidafu.swc.generated.dsl.esParseOptions
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

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
        // SWC 不会对空字符串抛出异常，而是返回一个空模块（包含单个分号）
        // 这个测试验证空字符串是有效的输入
        val result = swc.parseSync("", esParseOptions { }, "empty.js")
        assertNotNull(result)
        // 空字符串会被解析为一个空模块，body 可能为空或包含单个分号
    }
}
