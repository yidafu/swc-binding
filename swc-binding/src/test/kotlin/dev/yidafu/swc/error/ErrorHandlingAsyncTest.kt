package dev.yidafu.swc.error

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Async method error handling tests
 * Split from ErrorHandlingTest
 */
class ErrorHandlingAsyncTest : ShouldSpec({
    val swcNative = SwcNative()

    should("transformAsync with invalid syntax triggers error callback") {
        val latch = java.util.concurrent.CountDownLatch(1)
        var errorOccurred = false

        swcNative.transformAsync(
            code = "const x = ;", // Invalid syntax
            isModule = false,
            options = options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                }
            },
            onSuccess = {
                fail("Transform should fail for invalid syntax")
            },
            onError = {
                errorOccurred = true
                latch.countDown()
            }
        )

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS))
        assertTrue(errorOccurred, "Error callback should be triggered")
    }

    should("transformAsync coroutine throws exception on error") {
        kotlinx.coroutines.runBlocking {
            val exception = try {
                swcNative.transformAsync(
                    code = "const x = ;", // Invalid syntax
                    isModule = false,
                    options = options {
                        jsc = jscConfig {
                            parser = esParseOptions { }
                        }
                    }
                )
                null
            } catch (e: RuntimeException) {
                e
            }

            assertNotNull(exception?.message)
        }
    }

    should("printAsync with invalid program triggers error callback") {
        val latch = java.util.concurrent.CountDownLatch(1)
        var errorOccurred = false

        val program = swcNative.parseSync(";", esParseOptions { }, "empty.js") as Module

        swcNative.printAsync(
            program = program,
            options = options { },
            onSuccess = {
                latch.countDown()
            },
            onError = {
                errorOccurred = true
                latch.countDown()
            }
        )

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS))
    }

    should("printAsync coroutine throws exception on error") {
        kotlinx.coroutines.runBlocking {
            val program = swcNative.parseSync(
                "const x = 1;",
                esParseOptions { },
                "test.js"
            ) as Module

            val result = swcNative.printAsync(program, options { })

            assertNotNull(result.code)
        }
    }

    should("minifyAsync with invalid program triggers error callback") {
        val latch = java.util.concurrent.CountDownLatch(1)
        var errorOccurred = false

        swcNative.minifyAsync(
            src = ";",
            options = JsMinifyOptions(),
            onSuccess = {
                latch.countDown()
            },
            onError = {
                errorOccurred = true
                latch.countDown()
            }
        )

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS))
    }

    should("minifyAsync coroutine throws exception on error") {
        kotlinx.coroutines.runBlocking {
            val result = swcNative.minifyAsync("function test() { return 42; }", JsMinifyOptions())

            assertNotNull(result.code)
        }
    }

    should("parseAsync with invalid syntax triggers error callback") {
        val latch = java.util.concurrent.CountDownLatch(1)
        var errorOccurred = false

        swcNative.parseAsync(
            code = "const x = ;", // Invalid syntax
            options = esParseOptions { },
            filename = "test.js",
            onSuccess = {
                fail("Parse should fail for invalid syntax")
            },
            onError = {
                errorOccurred = true
                latch.countDown()
            }
        )

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS))
        assertTrue(errorOccurred, "Error callback should be triggered")
    }

    should("parseAsync coroutine throws exception on error") {
        kotlinx.coroutines.runBlocking {
            val exception = try {
                swcNative.parseAsync(
                    code = "const x = ;", // Invalid syntax
                    options = esParseOptions { },
                    filename = "test.js"
                )
                null
            } catch (e: RuntimeException) {
                e
            }

            assertNotNull(exception?.message)
        }
    }

    should("parseFileAsync with non-existent file triggers error callback") {
        val latch = java.util.concurrent.CountDownLatch(1)
        var errorOccurred = false

        swcNative.parseFileAsync(
            filepath = "/non/existent/path/file.js",
            options = esParseOptions { },
            onSuccess = {
                fail("Parse file should fail for non-existent file")
            },
            onError = {
                errorOccurred = true
                latch.countDown()
            }
        )

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS))
        assertTrue(errorOccurred, "Error callback should be triggered")
    }

    should("parseFileAsync coroutine throws exception on non-existent file") {
        kotlinx.coroutines.runBlocking {
            val exception = try {
                swcNative.parseFileAsync(
                    filepath = "/non/existent/path/file.js",
                    options = esParseOptions { }
                )
                null
            } catch (e: RuntimeException) {
                e
            }

            assertNotNull(exception?.message)
        }
    }

    should("transformFileAsync with non-existent file triggers error callback") {
        val latch = java.util.concurrent.CountDownLatch(1)
        var errorOccurred = false

        swcNative.transformFileAsync(
            filepath = "/non/existent/path/file.js",
            isModule = false,
            options = options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                }
            },
            onSuccess = {
                fail("Transform file should fail for non-existent file")
            },
            onError = {
                errorOccurred = true
                latch.countDown()
            }
        )

        assertTrue(latch.await(10, java.util.concurrent.TimeUnit.SECONDS))
        assertTrue(errorOccurred, "Error callback should be triggered")
    }

    should("transformFileAsync coroutine throws exception on non-existent file") {
        kotlinx.coroutines.runBlocking {
            val exception = try {
                swcNative.transformFileAsync(
                    filepath = "/non/existent/path/file.js",
                    isModule = false,
                    options = options {
                        jsc = jscConfig {
                            parser = esParseOptions { }
                        }
                    }
                )
                null
            } catch (e: RuntimeException) {
                e
            }

            assertNotNull(exception?.message)
        }
    }
})
