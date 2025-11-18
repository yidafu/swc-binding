package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import java.net.URL
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Basic tests for SwcNative core functionality
 * Tests for parse, transform, and print operations (sync and async)
 */
class SwcNativeBasicTest : AnnotationSpec() {
    private val swcNative = SwcNative()

    private fun getResource(filename: String): String {
        return SwcNativeBasicTest::class.java.classLoader.getResource(filename)!!.file!!
    }

    private fun getResourceContent(url: String): String {
        return URL(url).readText()
    }

    @Test
    fun `parse js code to ast str`() {
        val ast =
            swcNative.parseSync(
                """
                function add(a, b) {
                    return a + b;
                };
                add(1,2);
                """.trimIndent(),
                esParseOptions { },
                "temp.js"
            )
        assertNotNull(ast, "ast string can't be null ")
    }

    @Test
    fun `parse js file to ast str`() {
        val ast =
            swcNative.parseFileSync(
                getResource("test.js"),
                tsParseOptions { }
            )
        assertNotNull(ast, "ast string can't be null ")
    }

    @Test
    fun `transform js code to ast str`() {
        val ast =
            swcNative.transformSync(
                "function add(a, b) {return a + b;}; add(1,2)",
                false,
                options {
                    jsc =
                        jscConfig {
                            parser = tsParseOptions { }
                        }
                }
            )
        assertEquals(
            ast.code.trim(),
            """
            function add(a, b) {
                return a + b;
            }
            add(1, 2);
            """.trimIndent()
        )
    }

    @Test
    fun `transform js file to ast str`() {
        val ast =
            swcNative.transformFileSync(
                getResource("test.js"),
                false,
                options {
                    jsc =
                        jscConfig {
                            parser = esParseOptions { }
                        }
                }
            )
        assertNotNull(ast, "transform result can't be null ")
    }

    @Test
    fun `transform ts file to ast str`() {
        val ast =
            swcNative.transformFileSync(
                getResource("test.ts"),
                false,
                options {
                    jsc =
                        jscConfig {
                            parser = tsParseOptions { }
                        }
                }
            )

        assertEquals(
            ast.code.trim(),
            """
            function add(a, b) {
                return a + b;
            }
            add(1, 2);
            """.trimIndent()
        )
    }

    @Test
    fun `parse invalid js code`() {
        assertFailsWith<RuntimeException> {
            val module = swcNative.parseSync(
                """
                    val a = 234; // kotlin code
                """.trimIndent(),
                esParseOptions { },
                "test.js"
            ) as Module
        }
    }

    @Test
    fun `wrapper printSync method`() {
        val output =
            swcNative.printSync(
                esAddFunction,
                options { }
            )

        assertEquals(
            output.code.trim(),
            """
            function add(a, b) {
                return a + b;
            }
            ;
            add(1, 2);
            """.trimIndent()
        )
    }

    @Test
    fun `printSync with empty module`() {
        // Use parsed minimal code instead of manually creating module to avoid serialization issues
        val mod = swcNative.parseSync(";", esParseOptions { }, "empty.js") as Module

        val output = swcNative.printSync(mod, options { })
        assertNotNull(output.code)
    }

    @Test
    fun `parse react source code`() {
        val output1 = swcNative.parseSync(
            getResourceContent("https://unpkg.com/react@18/umd/react.development.js"),
            esParseOptions { },
            "react.development.js"
        )

        output1.shouldBeInstanceOf<Module>()

        val output2 = swcNative.parseSync(
            getResourceContent("https://unpkg.com/react-dom@18/umd/react-dom.development.js"),
            esParseOptions { },
            "react-dom.development.js"
        )

        output2.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `native print ast dsl`() {
        val res: String =
            swcNative.printSync(
                """
                {
                  "type": "Module",
                  "span": {
                    "start": 104,
                    "end": 171,
                    "ctxt": 0
                  },
                  "body": [{
                    "type": "ImportDeclaration",
                    "span": {
                      "start": 104,
                      "end": 130,
                      "ctxt": 0
                    },
                    "specifiers": [{
                      "type": "ImportDefaultSpecifier",
                      "span": {
                        "start": 111,
                        "end": 112,
                        "ctxt": 0
                      },
                      "local": {
                        "type": "Identifier",
                        "span": {
                          "start": 111,
                          "end": 112,
                          "ctxt": 2
                        },
                        "value": "x",
                        "optional": false
                      }
                    }],
                    "source": {
                      "type": "StringLiteral",
                      "span": {
                        "start": 118,
                        "end": 129,
                        "ctxt": 0
                      },
                      "value": "./test.js",
                      "raw": "'./test.js'"
                    },
                    "typeOnly": false,
                    "with": null
                  }, {
                    "type": "ClassDeclaration",
                    "identifier": {
                      "type": "Identifier",
                      "span": {
                        "start": 137,
                        "end": 140,
                        "ctxt": 2
                      },
                      "value": "Foo",
                      "optional": false
                    },
                    "declare": false,
                    "span": {
                      "start": 131,
                      "end": 144,
                      "ctxt": 0
                    },
                    "decorators": [],
                    "body": [],
                    "superClass": null,
                    "isAbstract": false,
                    "typeParams": null,
                    "superTypeParams": null,
                    "implements": []
                  }, {
                    "type": "ClassDeclaration",
                    "identifier": {
                      "type": "Identifier",
                      "span": {
                        "start": 152,
                        "end": 155,
                        "ctxt": 2
                      },
                      "value": "Bar",
                      "optional": false
                    },
                    "declare": false,
                    "span": {
                      "start": 146,
                      "end": 171,
                      "ctxt": 0
                    },
                    "decorators": [],
                    "body": [],
                    "superClass": {
                      "type": "Identifier",
                      "span": {
                        "start": 164,
                        "end": 167,
                        "ctxt": 2
                      },
                      "value": "Foo",
                      "optional": false
                    },
                    "isAbstract": false,
                    "typeParams": null,
                    "superTypeParams": null,
                    "implements": []
                  }],
                  "interpreter": null
                }
                """.trimIndent(),
                "{}"
            )
        assertNotNull(res)
    }

    @Test
    fun `parse simple js and print roundtrip`() {
        val src = """
            function add(a, b) {
                return a + b
            }
            add(1,2)
        """.trimIndent()
        val mod = swcNative.parseSync(src, esParseOptions { }, "add.js")
        assertIs<Module>(mod)

        val printed = swcNative.printSync(
            mod,
            options { }
        )
        assertNotNull(printed.code)
        // 关键语句仍然存在
        assertEquals(true, printed.code.contains("function add"))
        assertEquals(true, printed.code.contains("add(1, 2)"))
    }

    // ==================== Async Parse Tests ====================

    @Test
    fun `parseAsync with lambda callback`() {
        val latch = CountDownLatch(1)
        var result: Program? = null

        swcNative.parseAsync(
            code = "const x = 42;",
            options = esParseOptions {},
            filename = "test.js",
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Parse should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout waiting for callback")
        assertNotNull(result)
        result.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parseAsync with coroutine`() = runBlocking {
        val result = swcNative.parseAsync(
            code = "const x = 42;",
            options = esParseOptions {},
            filename = "test.js"
        )

        assertNotNull(result)
        result.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parseAsync error handling`() = runBlocking {
        val exception = try {
            swcNative.parseAsync(
                code = "const x = ;", // Invalid syntax
                options = esParseOptions {},
                filename = "test.js"
            )
            null
        } catch (e: RuntimeException) {
            e
        }

        assertNotNull(exception?.message)
    }

    @Test
    fun `parseFileAsync with lambda`() {
        val latch = CountDownLatch(1)
        var result: Program? = null

        swcNative.parseFileAsync(
            filepath = getResource("test.js"),
            options = esParseOptions {},
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Parse file should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(result)
    }

    @Test
    fun `parseFileAsync with coroutine`() = runBlocking {
        val result = swcNative.parseFileAsync(
            filepath = getResource("test.js"),
            options = esParseOptions {}
        )

        assertNotNull(result)
        result.shouldBeInstanceOf<Module>()
    }

    // ==================== Async Print Tests ====================

    @Test
    fun `printAsync with lambda callback`() {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        val program = swcNative.parseSync(
            "const x = 1;",
            esParseOptions { },
            "test.js"
        ) as Module

        swcNative.printAsync(
            program = program,
            options = options { },
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Print should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout waiting for print")
        assertNotNull(result)
        assertNotNull(result!!.code)
        assertTrue(result!!.code.isNotEmpty())
    }

    @Test
    fun `printAsync with coroutine`() = runBlocking {
        val program = swcNative.parseSync(
            "const x = 42;",
            esParseOptions { },
            "test.js"
        ) as Module

        val result = swcNative.printAsync(
            program = program,
            options = options { }
        )

        assertNotNull(result)
        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }
}

