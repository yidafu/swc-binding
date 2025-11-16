package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.assertIs
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.LazyThreadSafetyMode

/**
 * Comprehensive tests for async parse methods
 */
class AsyncParseTest : AnnotationSpec() {
    private val swc: SwcNative by lazy(LazyThreadSafetyMode.NONE) {
        runCatching { SwcNative() }.getOrElse { throwable ->
            throw RuntimeException("Failed to initialize SwcNative", throwable)
        }
    }

    private fun getResource(filename: String): String {
        return AsyncParseTest::class.java.classLoader.getResource(filename)!!.file!!
    }

    // ==================== Callback-based tests ====================

    @Test
    fun `test parseAsync with native callback - success`() {
        val latch = CountDownLatch(1)
        var result: String? = null
        var errorMsg: String? = null

        swc.parseAsync(
            code = "const x = 42;",
            options = """{"syntax":"ecmascript","target":"es2020"}""",
            filename = "test.js",
            callback = object : SwcCallback {
                override fun onSuccess(r: String) {
                    result = r
                    latch.countDown()
                }

                override fun onError(error: String) {
                    errorMsg = error
                    latch.countDown()
                }
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout waiting for callback")
        assertNotNull(result, "Result should not be null")
        assertTrue(result!!.isNotEmpty(), "Result should not be empty")
        assertTrue(result!!.contains("\"type\""), "Result should contain type field")
    }

    @Test
    fun `test parseAsync with native callback - error`() {
        val latch = CountDownLatch(1)
        var result: String? = null
        var errorMsg: String? = null

        swc.parseAsync(
            code = "const x = ;", // Invalid syntax
            options = """{"syntax":"ecmascript","target":"es2020"}""",
            filename = "test.js",
            callback = object : SwcCallback {
                override fun onSuccess(r: String) {
                    result = r
                    latch.countDown()
                }

                override fun onError(error: String) {
                    errorMsg = error
                    latch.countDown()
                }
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout waiting for callback")
        assertNotNull(errorMsg, "Error message should not be null")
        assertTrue(errorMsg!!.isNotEmpty(), "Error message should not be empty")
    }

    // ==================== Lambda callback tests ====================

    @Test
    fun `test parseAsync with lambda callback`() {
        val latch = CountDownLatch(1)
        var result: Program? = null

        swc.parseAsync(
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
    fun `test parseAsync lambda with TypeScript`() {
        val latch = CountDownLatch(1)
        var result: Program? = null

        swc.parseAsync(
            code = "const x: number = 42;",
            options = tsParseOptions {},
            filename = "test.ts",
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Parse should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(result)
    }

    @Test
    fun `test parseAsync lambda with JSX`() {
        val latch = CountDownLatch(1)
        var result: Program? = null

        swc.parseAsync(
            code = "const element = <div>Hello</div>;",
            options = esParseOptions {
                jsx = true
            },
            filename = "test.jsx",
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Parse should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(result)
    }

    @Test
    fun `test parseAsync lambda error handling`() {
        val latch = CountDownLatch(1)
        var errorMsg: String? = null

        swc.parseAsync(
            code = "const x = ;", // Invalid syntax
            options = esParseOptions {},
            filename = "test.js",
            onSuccess = {
                fail("Parse should fail for invalid syntax")
            },
            onError = {
                errorMsg = it
                latch.countDown()
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(errorMsg)
    }

    // ==================== Coroutine (suspend) tests ====================

    @Test
    fun `test parseAsync with coroutine`() = runBlocking {
        val result = swc.parseAsync(
            code = "const x = 42;",
            options = esParseOptions {},
            filename = "test.js"
        )

        assertNotNull(result)
        result.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `test parseAsync coroutine with TypeScript`() = runBlocking {
        val result = swc.parseAsync(
            code = """
                interface User {
                    name: string;
                    age: number;
                }
                const user: User = { name: "John", age: 30 };
            """.trimIndent(),
            options = tsParseOptions {},
            filename = "test.ts"
        )

        assertNotNull(result)
        result.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `test parseAsync coroutine error handling`() = runBlocking {
        val exception = try {
            swc.parseAsync(
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

    // ==================== File-based async tests ====================

    @Test
    fun `test parseFileAsync with lambda`() {
        val latch = CountDownLatch(1)
        var result: Program? = null

        swc.parseFileAsync(
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
    fun `test parseFileAsync with coroutine`() = runBlocking {
        val result = swc.parseFileAsync(
            filepath = getResource("test.js"),
            options = esParseOptions {}
        )

        assertNotNull(result)
        result.shouldBeInstanceOf<Module>()
    }

    // ==================== Concurrent tests ====================

    @Test
    fun `test concurrent parseAsync with coroutines`() = runBlocking {
        val codes = listOf(
            "const x = 1;",
            "const y = 2;",
            "const z = 3;",
            "function foo() { return 42; }",
            "class MyClass { constructor() {} }"
        )

        val results = codes.map { code ->
            async {
                swc.parseAsync(
                    code = code,
                    options = esParseOptions {},
                    filename = "test.js"
                )
            }
        }.awaitAll()

        results.forEach { result ->
            assertNotNull(result)
            result.shouldBeInstanceOf<Module>()
        }
    }

    @Test
    fun `test concurrent parseAsync with callbacks`() {
        val count = 5
        val latch = CountDownLatch(count)
        val results = mutableListOf<Program>()
        val errors = mutableListOf<String>()

        repeat(count) { i ->
            swc.parseAsync(
                code = "const x$i = $i;",
                options = esParseOptions {},
                filename = "test$i.js",
                onSuccess = {
                    synchronized(results) {
                        results.add(it)
                    }
                    latch.countDown()
                },
                onError = {
                    synchronized(errors) {
                        errors.add(it)
                    }
                    latch.countDown()
                }
            )
        }

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertTrue(errors.isEmpty(), "No errors should occur")
        assertTrue(results.size == count, "All parses should succeed")
    }

    // ==================== Edge cases ====================

    @Test
    fun `test parseAsync with empty code`() = runBlocking {
        val result = swc.parseAsync(
            code = "",
            options = esParseOptions {},
            filename = "test.js"
        )

        assertNotNull(result)
        assertIs<Module>(result)
    }

    @Test
    fun `test parseAsync with large code`() = runBlocking {
        val largeCode = buildString {
            repeat(1000) { i ->
                appendLine("const var$i = $i;")
            }
        }

        val result = swc.parseAsync(
            code = largeCode,
            options = esParseOptions {},
            filename = "large.js"
        )

        assertNotNull(result)
        assertIs<Module>(result)
    }
}
