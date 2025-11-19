package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertTrue

/**
 * Syntax error handling tests
 * Split from ErrorHandlingTest
 */
class ErrorHandlingSyntaxTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse invalid JS syntax") {
        assertFailsWith<RuntimeException> {
            swcNative.parseSync(
                "function { // missing name",
                esParseOptions { },
                "test.js"
            )
        }
    }

    should("parse unclosed brace") {
        assertFailsWith<RuntimeException> {
            swcNative.parseSync(
                "function test() { console.log('test');",
                esParseOptions { },
                "test.js"
            )
        }
    }

    should("parse invalid TypeScript syntax") {
        assertFailsWith<RuntimeException> {
            swcNative.parseSync(
                "interface { // missing name",
                tsParseOptions { },
                "test.ts"
            )
        }
    }

    should("parse with unexpected token") {
        assertFailsWith<RuntimeException> {
            swcNative.parseSync(
                "const x = @invalid;",
                esParseOptions { },
                "test.js"
            )
        }
    }

    should("transform with empty string") {
        val output = swcNative.transformSync(
            "",
            false,
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                }
            }
        )
        assertTrue(output.code.isEmpty() || output.code.isBlank())
    }

    should("parse empty string") {
        val result = swcNative.parseSync(
            "",
            esParseOptions { },
            "empty.js"
        )
        assertTrue(result is Module)
    }

    should("parse with invalid ES version") {
        val result = swcNative.parseSync(
            "const x = 1;",
            esParseOptions {
                target = JscTarget.ES3
            },
            "test.js"
        )
        assertTrue(result is Module)
    }

    should("transform with syntax error in code") {
        assertFailsWith<RuntimeException> {
            swcNative.transformSync(
                "const x = ;", // 缺少值
                false,
                options {
                    jsc = jscConfig {
                        parser = esParseOptions { }
                    }
                }
            )
        }
    }

    should("parse JSX without JSX enabled") {
        assertFailsWith<RuntimeException> {
            swcNative.parseSync(
                "<div>Test</div>",
                esParseOptions {
                    jsx = false
                },
                "test.js"
            )
        }
    }

    should("parse TypeScript with ES parser") {
        assertFailsWith<RuntimeException> {
            swcNative.parseSync(
                "interface Test { x: number }",
                esParseOptions { },
                "test.ts"
            )
        }
    }

    should("parse with special characters in string") {
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

    should("parse with emoji in code") {
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

    should("parse with regex literals") {
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

    should("parse with comments") {
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

    should("parse with strict mode") {
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

    should("transform with duplicate variable declarations") {
        val result = swcNative.transformSync(
            """
            var x = 1;
            var x = 2;
            """.trimIndent(),
            false,
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                }
            }
        )
        assertTrue(result.code.isNotEmpty())
    }

    should("parse with very long identifier") {
        val longId = "a".repeat(100)
        val code = "const $longId = 1;"
        val result = swcNative.parseSync(
            code,
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    should("parse with numbers in scientific notation") {
        val result = swcNative.parseSync(
            "const big = 1e10; const small = 1e-5;",
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    should("parse with hex numbers") {
        val result = swcNative.parseSync(
            "const hex = 0xFF; const hex2 = 0x1A3B;",
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    should("parse with octal and binary numbers") {
        val result = swcNative.parseSync(
            "const octal = 0o755; const binary = 0b1010;",
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    should("parse multiple statements") {
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
        val module = assertIs<Module>(result)
        assertTrue((module.body?.size ?: 0) >= 4)
    }

    should("transform preserves code structure") {
        val code = "const x = 1; function test() { return x; }"
        val result = swcNative.transformSync(
            code,
            false,
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                }
            }
        )
        assertTrue(result.code.contains("test"))
    }

    should("parse with whitespace variations") {
        val result = swcNative.parseSync(
            "const   x   =   1   ;",
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }

    should("parse ternary operator") {
        val result = swcNative.parseSync(
            "const result = condition ? 'yes' : 'no';",
            esParseOptions { },
            "test.js"
        )
        assertTrue(result is Module)
    }
})
