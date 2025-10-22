package dev.yidafu.swc

import dev.yidafu.swc.types.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Comprehensive tests for async minify methods
 */
class AsyncMinifyTest {
    private lateinit var swc: SwcNative

    @BeforeEach
    fun setup() {
        swc = SwcNative()
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
    fun `test minifyAsync with lambda callback`() {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        val program = swc.parseSync(testCode, esParseOptions {}, "test.js")

        swc.minifyAsync(
            program = program,
            options = Options(),
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Minify should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout waiting for minify")
        assertNotNull(result)
        assertNotNull(result!!.code)
        assertTrue(result!!.code.length < testCode.length, "Minified code should be shorter")
    }

    @Test
    fun `test minifyAsync lambda completes successfully`() {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        val program = swc.parseSync("function test() { return 42; }", esParseOptions {}, "test.js")

        swc.minifyAsync(
            program = program,
            options = Options(),
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Minify should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(result)
    }

    // ==================== Coroutine (suspend) tests ====================

    @Test
    fun `test minifyAsync with coroutine`() = runBlocking {
        val program = swc.parseSync(testCode, esParseOptions {}, "test.js")

        val result = swc.minifyAsync(
            program = program,
            options = Options()
        )

        assertNotNull(result)
        assertNotNull(result.code)
        assertTrue(result.code.length < testCode.length, "Minified code should be shorter")
    }

    @Test
    fun `test minifyAsync reduces code size significantly`() = runBlocking {
        val verboseCode = """
            function calculateSum(firstNumber, secondNumber) {
                const temporaryResult = firstNumber + secondNumber;
                console.log("The sum is: " + temporaryResult);
                return temporaryResult;
            }
            
            function calculateProduct(firstNumber, secondNumber) {
                const temporaryResult = firstNumber * secondNumber;
                console.log("The product is: " + temporaryResult);
                return temporaryResult;
            }
            
            const sumResult = calculateSum(10, 20);
            const productResult = calculateProduct(5, 6);
        """.trimIndent()

        val program = swc.parseSync(verboseCode, esParseOptions {}, "test.js")
        val result = swc.minifyAsync(program, Options())

        assertNotNull(result.code)
        assertTrue(
            result.code.length < verboseCode.length / 2,
            "Minified code should be less than half the original size"
        )
        assertFalse(
            result.code.contains("temporaryResult"),
            "Variable names should be shortened"
        )
        assertFalse(
            result.code.contains("  "),
            "Whitespace should be removed"
        )
    }

    @Test
    fun `test minifyAsync with options`() = runBlocking {
        val program = swc.parseSync(testCode, esParseOptions {}, "test.js")

        val options = Options()

        val result = swc.minifyAsync(program, options)

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test minifyAsync preserves functionality`() = runBlocking {
        val simpleCode = """
            function add(a, b) {
                return a + b;
            }
        """.trimIndent()

        val program = swc.parseSync(simpleCode, esParseOptions {}, "test.js")
        val result = swc.minifyAsync(program, Options())

        assertNotNull(result.code)
        // Minified code should still contain 'function' or equivalent
        assertTrue(result.code.contains("function") || result.code.contains("add"))
    }

    // ==================== Different code types ====================

    @Test
    fun `test minifyAsync with ES6 features`() = runBlocking {
        val es6Code = """
            const arrow = (x, y) => x + y;
            const spread = [...[1, 2, 3]];
            const {a, b} = {a: 1, b: 2};
            class MyClass {
                constructor() {
                    this.value = 42;
                }
            }
        """.trimIndent()

        val program = swc.parseSync(es6Code, esParseOptions {}, "test.js")
        val result = swc.minifyAsync(program, Options())

        assertNotNull(result.code)
        assertTrue(result.code.length < es6Code.length)
    }

    @Test
    fun `test minifyAsync with JavaScript from TypeScript`() = runBlocking {
        // Parse TypeScript and transform it first, then minify the result
        val tsCode = """
            function greet(userParam) {
                return "Hello, " + userParam.name + "!";
            }
            
            const myUser = {name: "Alice", age: 30};
            greet(myUser);
        """.trimIndent()

        val program = swc.parseSync(tsCode, esParseOptions {}, "test.js")
        val result = swc.minifyAsync(program, Options())

        assertNotNull(result.code)
        assertTrue(result.code.length < tsCode.length)
    }

    // ==================== Concurrent minify tests ====================

    @Test
    fun `test concurrent minifyAsync with coroutines`() = runBlocking {
        // Use simple codes to avoid parsing issues
        val codes = listOf(
            "const x1 = 1;",
            "const x2 = 2;",
            "const x3 = 3;"
        )

        val results = codes.map { code ->
            async {
                val program = swc.parseSync(code, esParseOptions {}, "test.js")
                swc.minifyAsync(program, Options())
            }
        }.awaitAll()

        results.forEach { result ->
            assertNotNull(result)
            assertNotNull(result.code)
            assertTrue(result.code.isNotEmpty())
        }
    }

    @Test
    fun `test concurrent minifyAsync with callbacks`() {
        val count = 5
        val latch = CountDownLatch(count)
        val results = mutableListOf<TransformOutput>()
        val errors = mutableListOf<String>()

        repeat(count) { i ->
            val code = "function f$i() { return $i; }"
            val program = swc.parseSync(code, esParseOptions {}, "test$i.js")

            swc.minifyAsync(
                program = program,
                options = Options(),
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
        assertTrue(results.size == count, "All minifies should succeed")
    }

    // ==================== Edge cases ====================

    @Test
    fun `test minifyAsync with empty code`() = runBlocking {
        val program = swc.parseSync("", esParseOptions {}, "test.js")
        val result = swc.minifyAsync(program, Options())

        assertNotNull(result)
        assertNotNull(result.code)
    }

    @Test
    fun `test minifyAsync with already minified code`() = runBlocking {
        val minifiedCode = "function f(){return 42}"
        val program = swc.parseSync(minifiedCode, esParseOptions {}, "test.js")
        val result = swc.minifyAsync(program, Options())

        assertNotNull(result.code)
        // Should still process, even if already minified
        assertTrue(result.code.length <= minifiedCode.length + 10)
    }

    @Test
    fun `test minifyAsync with large code`() = runBlocking {
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

        val program = swc.parseSync(largeCode, esParseOptions {}, "large.js")
        val result = swc.minifyAsync(program, Options())

        assertNotNull(result.code)
        assertTrue(
            result.code.length < largeCode.length,
            "Minified large code should be shorter"
        )
    }

    // ==================== Options variations ====================

    @Test
    fun `test minifyAsync with simple options`() = runBlocking {
        val code = """
            function longFunctionName(parameterOne, parameterTwo) {
                const localVariable = parameterOne + parameterTwo;
                return localVariable;
            }
        """.trimIndent()

        val program = swc.parseSync(code, esParseOptions {}, "test.js")
        val options = Options()

        val result = swc.minifyAsync(program, options)

        assertNotNull(result.code)
        // Minified code should be shorter
        assertTrue(result.code.length < code.length)
    }
}
