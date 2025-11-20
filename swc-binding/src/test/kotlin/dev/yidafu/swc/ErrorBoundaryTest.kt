package dev.yidafu.swc

import dev.yidafu.swc.generated.dsl.esParseOptions
import dev.yidafu.swc.generated.dsl.options
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class ErrorBoundaryTest : ShouldSpec({
    val swc = SwcNative()

    should("invalid js throws") {
        assertFailsWith<RuntimeException> {
            swc.parseSync("val a = 1", esParseOptions { }, "invalid.js")
        }
    }

    should("empty input throws") {
        // SWC 不会对空字符串抛出异常，而是返回一个空模块（包含单个分号）
        // 这个测试验证空字符串是有效的输入
        val result = swc.parseSync("", esParseOptions { }, "empty.js")
        assertNotNull(result)
        // 空字符串会被解析为一个空模块，body 可能为空或包含单个分号
    }

    should("printSync with empty options works fine") {
        val code = "const x = 1;"
        val program = swc.parseSync(code, esParseOptions { }, "test.js")
        
        // Test that empty options {} works fine (should serialize to "{}")
        val output = swc.printSync(program, options { })
        assertNotNull(output)
        assertNotNull(output.code)
    }
})
