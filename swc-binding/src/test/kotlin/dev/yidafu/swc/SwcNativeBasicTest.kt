package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import java.net.URL
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
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
class SwcNativeBasicTest : ShouldSpec({
    val swcNative = SwcNative()

    fun getResource(filename: String): String {
        return SwcNativeBasicTest::class.java.classLoader.getResource(filename)!!.file!!
    }

    fun getResourceContent(url: String): String {
        return URL(url).readText()
    }

    should("parse js code to ast str") {
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

    should("parse js file to ast str") {
        val ast =
            swcNative.parseFileSync(
                getResource("test.js"),
                tsParseOptions { }
            )
        assertNotNull(ast, "ast string can't be null ")
    }

    should("transform js code to ast str") {
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

    should("transform js file to ast str") {
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

    should("transform ts file to ast str") {
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

    should("parse invalid js code") {
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

    should("parse react source code") {
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

    // ==================== Async Parse Tests ====================

    should("parseAsync with lambda callback") {
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

    should("parseAsync with coroutine") {
        runBlocking {
            val result = swcNative.parseAsync(
                code = "const x = 42;",
                options = esParseOptions {},
                filename = "test.js"
            )

            assertNotNull(result)
            result.shouldBeInstanceOf<Module>()
        }
    }

    should("parseAsync error handling") {
        runBlocking {
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
    }

    should("parseFileAsync with lambda") {
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

    should("parseFileAsync with coroutine") {
        runBlocking {
            val result = swcNative.parseFileAsync(
                filepath = getResource("test.js"),
                options = esParseOptions {}
            )

            assertNotNull(result)
            result.shouldBeInstanceOf<Module>()
        }
    }

})
