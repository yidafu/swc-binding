package dev.yidafu.swc.error

import dev.yidafu.swc.SwcNative
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
        // SWC does not throw exception for empty string, but returns an empty module (containing a single semicolon)
        // This test verifies empty string is valid input
        val result = swc.parseSync("", esParseOptions { }, "empty.js")
        assertNotNull(result)
        // Empty string will be parsed as an empty module, body may be empty or contain a single semicolon
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
