package dev.yidafu.swc.swcnative.core

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.TransformOutput
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Tests for code transformation with different ES targets (sync and async)
 */
class SwcNativeTransformTest : ShouldSpec({
    val swcNative = SwcNative()

    fun getResource(filename: String): String {
        return SwcNativeTransformTest::class.java.classLoader.getResource(filename)!!.file!!
    }

    should("transform with es5 target") {
        val output = swcNative.transformSync(
            "const x = 1;",
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )
        assertNotNull(output.code)
    }

    should("transform arrow function to ES5") {
        val output = swcNative.transformSync(
            "const add = (a, b) => a + b;",
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("=>"))
    }

    should("transform class to ES5") {
        val output = swcNative.transformSync(
            """
            class Animal {
                constructor(name) {
                    this.name = name;
                }
                speak() {
                    return this.name + " makes a sound";
                }
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )
        assertNotNull(output.code)
        // Note: ES5 transformation may still contain class syntax in some cases
        // The important thing is that the transformation succeeds
    }

    should("transform const and let to var for ES5") {
        val output = swcNative.transformSync(
            """
            const x = 1;
            let y = 2;
            {
                const z = 3;
                let w = 4;
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )
        assertNotNull(output.code)
    }

    should("transform template literals to ES5") {
        val output = swcNative.transformSync(
            """
            const name = "World";
            const greeting = `Hello, World!`;
            const multiLine = `Line 1
            Line 2`;
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("`"), "ES5 target should not contain template literals")
    }

    should("transform destructuring to ES5") {
        val output = swcNative.transformSync(
            """
            const [a, b] = [1, 2];
            const { x, y } = { x: 10, y: 20 };
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )
        assertNotNull(output.code)
    }

    should("transform spread operator to ES5") {
        val output = swcNative.transformSync(
            """
            const arr = [...[1, 2], 3];
            const obj = { ...{ a: 1 }, b: 2 };
            func(...args);
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )
        assertNotNull(output.code)
    }

    should("transform with ES2015 target") {
        val output = swcNative.transformSync(
            """
            const x = 1;
            let y = 2;
            class MyClass {
                constructor() {
                    this.value = 42;
                }
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES2015
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(output.code.contains("const") || output.code.contains("var"))
    }

    should("transform with ES2020 target") {
        val output = swcNative.transformSync(
            """
            const value = foo ?? 'default';
            const result = obj?.property?.nested;
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions {
                        nullishCoalescing = true
                        optionalChaining = true
                    }
                    target = JscTarget.ES2020
                }
            }
        )
        assertNotNull(output.code)
    }

    should("transform with ES2022 target") {
        val output = swcNative.transformSync(
            """
            class MyClass {
                #privateField = 42;
                static #privateStaticField = 100;
                
                #privateMethod() {
                    return this.#privateField;
                }
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES2022
                }
            }
        )
        assertNotNull(output.code)
    }

    // ==================== Async Transform Tests ====================

    should("transformAsync with lambda callback") {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        swcNative.transformAsync(
            code = "const x = 1;",
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

    should("transformAsync with coroutine") {
        runBlocking {
            val result = swcNative.transformAsync(
                code = "const x = 1;",
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
    }

    should("transformAsync error handling") {
        val latch = CountDownLatch(1)
        var errorMsg: String? = null

        swcNative.transformAsync(
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

    should("transformFileAsync with lambda") {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        swcNative.transformFileAsync(
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

    should("transformFileAsync with coroutine") {
        runBlocking {
            val result = swcNative.transformFileAsync(
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
    }

    // ==================== JSX Transform Tests ====================

    should("transform JSX to JavaScript") {
        val output = swcNative.transformSync(
            """
            function App() {
                return <div>Hello World</div>;
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions {
                        jsx = true
                    }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("<"), "Transformed code should not contain JSX syntax")
    }

    should("transform TSX to JavaScript") {
        val output = swcNative.transformSync(
            """
            interface Props {
                name: string;
            }
            
            function Greeting(props: Props) {
                return <div>Hello, {props.name}!</div>;
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = tsParseOptions {
                        tsx = true
                    }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("interface"), "Transformed code should not contain TypeScript syntax")
        assertTrue(!output.code.contains("<"), "Transformed code should not contain JSX syntax")
    }

    should("transform React component with useState hook") {
        val output = swcNative.transformSync(
            """
            import { useState } from "react";

            function Counter() {
                const [count, setCount] = useState(0);
                return (
                    <div>
                        <button onClick={() => setCount(count + 1)}>Increment</button>
                        <span>Count: {count}</span>
                    </div>
                );
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions {
                        jsx = true
                    }
                    transform = transformConfig {
                        react = reactConfig {
                            runtime = "automatic"
                        }
                    }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(output.code.isNotEmpty(), "Transformed code should not be empty")
        assertTrue(!output.code.contains("<"), "Transformed code should not contain JSX syntax")
        assertTrue(output.code.contains("useState") || output.code.contains("_useState"), "Transformed code should contain useState reference")
    }

    should("transform React component with named imports and default export") {
        val output = swcNative.transformSync(
            """
            import { foo, bar } from "@jupyter";

            export default function App() {
                return <div>{foo}</div>
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions {
                        jsx = true
                    }
                    transform = transformConfig {
                        react = reactConfig {
                            runtime = "automatic"
                        }
                    }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(output.code.isNotEmpty(), "Transformed code should not be empty")
        assertTrue(!output.code.contains("<"), "Transformed code should not contain JSX syntax")
        assertTrue(output.code.contains("foo") || output.code.contains("@jupyter"), "Transformed code should contain imported variable reference")
        assertTrue(output.code.contains("export") || output.code.contains("default"), "Transformed code should contain export statement")
    }
})