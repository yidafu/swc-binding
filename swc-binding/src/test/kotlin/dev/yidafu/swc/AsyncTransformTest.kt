package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.LazyThreadSafetyMode
import kotlin.test.Test

/**
 * Comprehensive tests for async transform methods
 */
class AsyncTransformTest : AnnotationSpec() {
    private val swc: SwcNative by lazy(LazyThreadSafetyMode.NONE) {
        runCatching { SwcNative() }.getOrElse { throwable ->
            throw RuntimeException("Failed to initialize SwcNative", throwable)
        }
    }

    private fun getResource(filename: String): String {
        return AsyncTransformTest::class.java.classLoader.getResource(filename)!!.file!!
    }

    private val testCode = """
        function hello() {
            const message = "Hello World";
            console.log(message);
            return message;
        }
        
        function add(a, b) {
            return a + b;
        }
        
        const result = add(1, 2);
        hello();
    """.trimIndent()

    // ==================== Lambda callback tests ====================

    @Test
    fun `test transformAsync with lambda callback`() {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        swc.transformAsync(
            code = testCode,
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions { }
                }
            },
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Transform should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout waiting for transform")
        assertNotNull(result)
        assertNotNull(result!!.code)
        assertTrue(result!!.code.isNotEmpty())
    }

    @Test
    fun `test transformAsync lambda with TypeScript`() {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        val tsCode = """
            const greeting: string = "Hello";
            interface Greeter {
                greet(): void;
            }
        """.trimIndent()

        swc.transformAsync(
            code = tsCode,
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = tsParseOptions { }
                }
            },
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Transform should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(result)
        assertNotNull(result!!.code)
    }

    @Test
    fun `test transformAsync lambda error handling`() {
        val latch = CountDownLatch(1)
        var errorMsg: String? = null

        swc.transformAsync(
            code = "const x = ;", // Invalid syntax
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions { }
                }
            },
            onSuccess = {
                fail("Transform should fail for invalid syntax")
            },
            onError = {
                errorMsg = it
                latch.countDown()
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(errorMsg)
    }

    @Test
    fun `test transformFileAsync with lambda callback`() {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        swc.transformFileAsync(
            filepath = getResource("test.js"),
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions { }
                }
            },
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Transform file should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(result)
        assertNotNull(result!!.code)
    }

    @Test
    fun `test transformFileAsync lambda with TypeScript`() {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        swc.transformFileAsync(
            filepath = getResource("test.ts"),
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = tsParseOptions { }
                }
            },
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Transform file should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(result)
        assertNotNull(result!!.code)
    }

    // ==================== Coroutine (suspend) tests ====================

    @Test
    fun `test transformAsync with coroutine`() = runBlocking {
        val result = swc.transformAsync(
            code = testCode,
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions { }
                }
            }
        )

        assertNotNull(result)
        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test transformAsync coroutine with ES5 target`() = runBlocking {
        val result = swc.transformAsync(
            code = "const x = 1;",
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test transformAsync coroutine with TypeScript`() = runBlocking {
        val tsCode = """
            interface User {
                name: string;
                age: number;
            }
            const user: User = { name: "John", age: 30 };
        """.trimIndent()

        val result = swc.transformAsync(
            code = tsCode,
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = tsParseOptions { }
                }
            }
        )

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test transformAsync coroutine error handling`() = runBlocking {
        val exception = try {
            swc.transformAsync(
                code = "const x = ;", // Invalid syntax
                isModule = false,
                options = options {
                    jsc = JscConfig().apply {
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

    @Test
    fun `test transformFileAsync with coroutine`() = runBlocking {
        val result = swc.transformFileAsync(
            filepath = getResource("test.js"),
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions { }
                }
            }
        )

        assertNotNull(result)
        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test transformFileAsync coroutine with TypeScript`() = runBlocking {
        val result = swc.transformFileAsync(
            filepath = getResource("test.ts"),
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = tsParseOptions { }
                }
            }
        )

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    // ==================== Concurrent tests ====================

    @Test
    fun `test concurrent transformAsync with coroutines`() = runBlocking {
        val codes = listOf(
            "const x = 1;",
            "const y = 2;",
            "const z = 3;",
            "function foo() { return 42; }",
            "class MyClass { constructor() {} }"
        )

        val results = codes.map { code ->
            async {
                swc.transformAsync(
                    code = code,
                    isModule = false,
                    options = options {
                        jsc = JscConfig().apply {
                            parser = esParseOptions { }
                        }
                    }
                )
            }
        }.awaitAll()

        results.forEach { result ->
            assertNotNull(result)
            assertNotNull(result.code)
            assertTrue(result.code.isNotEmpty())
        }
    }

    @Test
    fun `test concurrent transformAsync with callbacks`() {
        val count = 5
        val latch = CountDownLatch(count)
        val results = mutableListOf<TransformOutput>()
        val errors = mutableListOf<String>()

        repeat(count) { i ->
            val code = "const x$i = $i;"
            swc.transformAsync(
                code = code,
                isModule = false,
                options = options {
                    jsc = JscConfig().apply {
                        parser = esParseOptions { }
                    }
                },
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
        assertTrue(results.size == count, "All transforms should succeed")
    }

    // ==================== Edge cases ====================

    @Test
    fun `test transformAsync with empty code`() = runBlocking {
        val result = swc.transformAsync(
            code = "",
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions { }
                }
            }
        )

        assertNotNull(result)
        assertNotNull(result.code)
    }

    @Test
    fun `test transformAsync with module mode`() = runBlocking {
        val result = swc.transformAsync(
            code = "export const x = 1;",
            isModule = true,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions { }
                }
            }
        )

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test transformAsync with large code`() = runBlocking {
        val largeCode = buildString {
            repeat(100) { i ->
                appendLine(
                    """
                    function func$i(param1, param2) {
                        const result = param1 + param2;
                        console.log("Function $i result:", result);
                        return result;
                    }
                    """.trimIndent()
                )
            }
        }

        val result = swc.transformAsync(
            code = largeCode,
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions { }
                }
            }
        )

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test transformAsync with JSX`() = runBlocking {
        val jsxCode = """
            function App() {
                return <div>Hello World</div>;
            }
        """.trimIndent()

        val result = swc.transformAsync(
            code = jsxCode,
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions {
                        jsx = true
                    }
                }
            }
        )

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test transformAsync preserves functionality`() = runBlocking {
        val code = "function add(a, b) { return a + b; }"
        val result = swc.transformAsync(
            code = code,
            isModule = false,
            options = options {
                jsc = JscConfig().apply {
                    parser = esParseOptions { }
                }
            }
        )

        assertNotNull(result.code)
        assertTrue(result.code.contains("add") || result.code.contains("function"))
    }
}

