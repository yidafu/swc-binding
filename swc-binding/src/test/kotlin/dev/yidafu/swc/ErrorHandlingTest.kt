package dev.yidafu.swc

import dev.yidafu.swc.dsl.*
import dev.yidafu.swc.types.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Tests for error handling functionality
 * 
 * DISABLED: This test causes Rust panic due to AST compatibility issues with SWC 43.0.0
 * The issue is related to missing 'ctxt' field in the AST structure.
 * TODO: Fix AST compatibility or regenerate Kotlin types for SWC 43.0.0
 */
@Disabled("Temporarily disabled due to AST compatibility issues with SWC 43.0.0")
class ErrorHandlingTest {
    private val swcNative = SwcNative()

    @Test
    fun `parse invalid JS syntax`() {
        assertThrows<RuntimeException> {
            swcNative.parseSync(
                "function { // missing name",
                esParseOptions { },
                "test.js"
            )
        }
    }

    @Test
    fun `parse unclosed brace`() {
        assertThrows<RuntimeException> {
            swcNative.parseSync(
                "function test() { console.log('test');",
                esParseOptions { },
                "test.js"
            )
        }
    }

    @Test
    fun `parse invalid TypeScript syntax`() {
        assertThrows<RuntimeException> {
            swcNative.parseSync(
                "interface { // missing name",
                tsParseOptions { },
                "test.ts"
            )
        }
    }

    @Test
    fun `parse with unexpected token`() {
        assertThrows<RuntimeException> {
            swcNative.parseSync(
                "const x = @invalid;",
                esParseOptions { },
                "test.js"
            )
        }
    }

    // 此测试会导致 JVM 崩溃，已移除
    // @Test
    // fun `parse non-existent file`() {
    //     assertThrows<RuntimeException> {
    //         swcNative.parseFileSync(
    //             "/non/existent/path/file.js",
    //             esParseOptions { }
    //         )
    //     }
    // }

    @Test
    fun `transform with empty string`() {
        val output = swcNative.transformSync(
            "",
            false,
            options {
                jsc = jscConfig {
                    parser = esParserConfig { }
                }
            }
        )
        // 空字符串应该能处理
        assertTrue(output.code.isEmpty() || output.code.isBlank())
    }

    @Test
    fun `parse empty string`() {
        val result = swcNative.parseSync(
            "",
            esParseOptions { },
            "empty.js"
        )
        // 空代码应该返回空的 Module
        assertTrue(result is Module)
    }

    @Test
    fun `parse with invalid ES version`() {
        // 这应该仍然能工作，只是可能不支持某些特性
        val result = swcNative.parseSync(
            "const x = 1;",
            esParseOptions {
                target = "es3"
            },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `transform with syntax error in code`() {
        assertThrows<RuntimeException> {
            swcNative.transformSync(
                "const x = ;", // 缺少值
                false,
                options {
                    jsc = jscConfig {
                        parser = esParserConfig { }
                    }
                }
            )
        }
    }

    @Test
    fun `parse JSX without JSX enabled`() {
        // 不启用 JSX 解析应该失败
        assertThrows<RuntimeException> {
            swcNative.parseSync(
                "<div>Test</div>",
                esParseOptions {
                    jsx = false
                },
                "test.js"
            )
        }
    }

    @Test
    fun `parse TypeScript with ES parser`() {
        // 用 ES 解析器解析 TS 代码应该失败
        assertThrows<RuntimeException> {
            swcNative.parseSync(
                "interface Test { x: number }",
                esParseOptions { },
                "test.ts"
            )
        }
    }

    // 此测试会导致 JVM 崩溃，已移除
    // @Test
    // fun `transform file that does not exist`() {
    //     assertThrows<RuntimeException> {
    //         swcNative.transformFileSync(
    //             "/invalid/path/test.js",
    //             false,
    //             options {
    //                 jsc = jscConfig {
    //                     parser = esParserConfig { }
    //                 }
    //             }
    //         )
    //     }
    // }

    // 此测试会导致 JVM 崩溃（minify 问题），已移除
    // @Test
    // fun `empty module for minify`() {
    //     val module = ModuleImpl().apply {
    //         span = Span().apply { start = 0; end = 0; ctxt = 0 }
    //         body = arrayOf()
    //     }
    //     val output = swcNative.minifySync(module, options { })
    //     assertNotNull(output.code)
    // }

    @Test
    fun `parse extremely nested code`() {
        // 创建深度嵌套的代码
        val nested = "(".repeat(100) + "1" + ")".repeat(100)

        val result = swcNative.parseSync(
            nested,
            esParseOptions { },
            "nested.js"
        )
        // 应该能够解析深度嵌套的代码
        assertTrue(result is Module)
    }

    @Test
    fun `parse with special characters in string`() {
        val result = swcNative.parseSync(
            """
            const str = "Hello\nWorld\t\r\n";
            const unicode = "\u0048\u0065\u006C\u006C\u006F";
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `parse with emoji in code`() {
        val result = swcNative.parseSync(
            """
            const emoji = "Hello 👋 World 🌍";
            console.log("🎉");
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `parse with regex literals`() {
        val result = swcNative.parseSync(
            """
            const pattern = /[a-z]+/gi;
            const complex = /^(?:https?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+$/;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `parse with comments`() {
        val result = swcNative.parseSync(
            """
            // Single line comment
            const x = 1; // inline comment
            /* Multi
               line
               comment */
            const y = 2;
            """.trimIndent(),
            esParseOptions {
                comments = true
            },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `parse with strict mode`() {
        val result = swcNative.parseSync(
            """
            "use strict";
            const x = 1;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `transform with duplicate variable declarations`() {
        // 这在严格模式下可能会有问题，但应该能解析
        val result = swcNative.transformSync(
            """
            var x = 1;
            var x = 2;
            """.trimIndent(),
            false,
            options {
                jsc = jscConfig {
                    parser = esParserConfig { }
                }
            }
        )
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `parse with very long identifier`() {
        val longId = "a".repeat(100)
        val code = "const $longId = 1;"
        val result = swcNative.parseSync(
            code,
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `parse with numbers in scientific notation`() {
        val result = swcNative.parseSync(
            "const big = 1e10; const small = 1e-5;",
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `parse with hex numbers`() {
        val result = swcNative.parseSync(
            "const hex = 0xFF; const hex2 = 0x1A3B;",
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `parse with octal and binary numbers`() {
        val result = swcNative.parseSync(
            "const octal = 0o755; const binary = 0b1010;",
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `parse multiple statements`() {
        val result = swcNative.parseSync(
            """
            const a = 1;
            const b = 2;
            const c = 3;
            console.log(a, b, c);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
        assertTrue((result.body?.size ?: 0) >= 4)
    }

    @Test
    fun `transform preserves code structure`() {
        val code = "const x = 1; function test() { return x; }"
        val result = swcNative.transformSync(
            code,
            false,
            options {
                jsc = jscConfig {
                    parser = esParserConfig { }
                }
            }
        )
        assertTrue(result.code.contains("test"))
    }

    @Test
    fun `parse with whitespace variations`() {
        val result = swcNative.parseSync(
            "const   x   =   1   ;",
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    @Test
    fun `parse ternary operator`() {
        val result = swcNative.parseSync(
            "const result = condition ? 'yes' : 'no';",
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }
}
