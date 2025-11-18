package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Tests for code minification functionality (sync and async)
 */
class SwcNativeMinifyTest : AnnotationSpec() {
    private val swcNative = SwcNative()

    @Test
    fun `minifySync with simple code`() {
        val code = """
            function add(a, b) {
                return a + b;
            }
            const result = add(1, 2);
        """.trimIndent()

        val program = swcNative.parseSync(code, esParseOptions { }, "test.js")
        val result = swcNative.minifySync(program, options { })

        assertNotNull(result)
        assertNotNull(result.code)
        assertTrue(result.code.length <= code.length)
    }

    @Test
    fun `minifySync reduces code size`() {
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

        val program = swcNative.parseSync(verboseCode, esParseOptions { }, "test.js")
        val result = swcNative.minifySync(program, options { })

        assertNotNull(result.code)
        assertTrue(
            result.code.length < verboseCode.length,
            "Minified code should be shorter than original"
        )
    }

    @Test
    fun `minifySync with ES6 features`() {
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

        val program = swcNative.parseSync(es6Code, esParseOptions { }, "test.js")
        val result = swcNative.minifySync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.length < es6Code.length)
    }

    @Test
    fun `minifySync with TypeScript`() {
        val tsCode = """
            function greet(userParam: {name: string}) {
                return "Hello, " + userParam.name + "!";
            }
            
            const myUser = {name: "Alice", age: 30};
            greet(myUser);
        """.trimIndent()

        val program = swcNative.parseSync(tsCode, tsParseOptions { }, "test.ts")
        val result = swcNative.minifySync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.length < tsCode.length)
    }

    @Test
    fun `minifySync with already minified code`() {
        val minifiedCode = "function f(){return 42}"
        val program = swcNative.parseSync(minifiedCode, esParseOptions { }, "test.js")
        val result = swcNative.minifySync(program, options { })

        assertNotNull(result.code)
        // Should still process, even if already minified
        assertTrue(result.code.length <= minifiedCode.length + 10)
    }

    @Test
    fun `minifySync with large code`() {
        val largeCode = buildString {
            repeat(50) { i ->
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

        val program = swcNative.parseSync(largeCode, esParseOptions { }, "large.js")
        val result = swcNative.minifySync(program, options { })

        assertNotNull(result.code)
        assertTrue(
            result.code.length < largeCode.length,
            "Minified large code should be shorter"
        )
    }

    @Test
    fun `minifySync preserves functionality`() {
        val code = """
            function add(a, b) {
                return a + b;
            }
        """.trimIndent()

        val program = swcNative.parseSync(code, esParseOptions { }, "test.js")
        val result = swcNative.minifySync(program, options { })

        assertNotNull(result.code)
        // Minified code should still contain 'function' or equivalent
        assertTrue(result.code.contains("function") || result.code.contains("add"))
    }

    @Test
    fun `minifySync with comments`() {
        val codeWithComments = """
            // This is a comment
            function test() {
                return 42; // Another comment
            }
            /* Multi-line
               comment */
        """.trimIndent()

        val program = swcNative.parseSync(
            codeWithComments,
            esParseOptions {
                comments = true
            },
            "test.js"
        )
        val result = swcNative.minifySync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `minifySync with empty module`() {
        // Use parsed minimal code instead of manually creating module to avoid serialization issues
        val program = swcNative.parseSync(";", esParseOptions { }, "empty.js") as Module

        val result = swcNative.minifySync(program, options { })

        assertNotNull(result)
        assertNotNull(result.code)
    }

    @Test
    fun `minifySync with different minify options`() {
        val code = """
            function calculateSum(firstNumber, secondNumber) {
                const temporaryResult = firstNumber + secondNumber;
                console.log("The sum is: " + temporaryResult);
                return temporaryResult;
            }
        """.trimIndent()

        val program = swcNative.parseSync(code, esParseOptions { }, "test.js")

        val result1 = swcNative.minifySync(
            program,
            options {
                minify = false
            }
        )

        val result2 = swcNative.minifySync(
            program,
            options {
                minify = true
            }
        )

        assertNotNull(result1.code)
        assertNotNull(result2.code)
        assertTrue(result1.code.contains("console") || !result2.code.contains("console"))
    }

    @Test
    fun `minifySync preserves functionality with complex code`() {
        val code = """
            async function fetchData(url) {
                try {
                    const response = await fetch(url);
                    const data = await response.json();
                    return { success: true, data };
                } catch (error) {
                    return { success: false, error: error.message };
                }
            }
            
            class DataProcessor {
                constructor(initialData) {
                    this.data = initialData;
                }
                
                process() {
                    return this.data.map(item => item * 2);
                }
            }
        """.trimIndent()

        val program = swcNative.parseSync(code, esParseOptions { }, "test.js")
        val result = swcNative.minifySync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.length < code.length)
        assertTrue(result.code.contains("function") || result.code.contains("async"))
    }

    // ==================== Async Minify Tests ====================

    @Test
    fun `minifyAsync with lambda callback`() {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        val code = """
            function add(a, b) {
                return a + b;
            }
        """.trimIndent()

        val program = swcNative.parseSync(code, esParseOptions {}, "test.js")

        swcNative.minifyAsync(
            program = program,
            options = options { },
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
        assertTrue(result!!.code.length <= code.length)
    }

    @Test
    fun `minifyAsync with coroutine`() = runBlocking {
        val code = """
            function add(a, b) {
                return a + b;
            }
        """.trimIndent()

        val program = swcNative.parseSync(code, esParseOptions {}, "test.js")
        val result = swcNative.minifyAsync(program, options { })

        assertNotNull(result)
        assertNotNull(result.code)
        assertTrue(result.code.length <= code.length)
    }
}

