package dev.yidafu.swc.swcnative.syntax.typescript

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * TypeScript transformation tests
 * Split from SwcNativeTypeScriptTest
 */
class SwcNativeTypeScriptTransformTest : ShouldSpec({
    val swcNative = SwcNative()

    should("transform TypeScript to JavaScript") {
        val output = swcNative.transformSync(
            """
            const greeting: string = "Hello";
            interface Greeter {
                greet(): void;
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = tsParseOptions { }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("interface"))
    }

    should("transform TypeScript interface to JavaScript") {
        val output = swcNative.transformSync(
            """
            interface User {
                name: string;
                age: number;
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = tsParseOptions { }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("interface"))
    }

    should("transform TypeScript enum to JavaScript") {
        val output = swcNative.transformSync(
            """
            enum Color {
                Red,
                Green,
                Blue
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = tsParseOptions { }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("enum"))
    }

    should("transform TypeScript with generic constraints") {
        val output = swcNative.transformSync(
            """
            function identity<T extends string | number>(arg: T): T {
                return arg;
            }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = tsParseOptions { }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("extends"))
    }

    should("transform ts code") {
        swcNative.transformSync(
            """
            const n: number = 123;
            const s: string = "foo";
            interface IUser {
                name: string
                id: number
            }
            const user: IUser = { name: "jupyter", id: 1 };
            
            console.log(n, s, user)
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = tsParseOptions {
                        target = JscTarget.ES2020
                        comments = false
                    }
                }
            }
        )
    }

    should("transform jsx snippet") {
        val output = swcNative.transformSync(
            """
                function App() {
                    return <div>Text</div>
                }
            """.trimIndent(),
            options {
                jsc = jscConfig {
                    parser = esParseOptions {
                        jsx = true
                        target = JscTarget.ES2020
                        comments = false
                        topLevelAwait = true
                        nullishCoalescing = true
                        optionalChaining = true
                    }
                }
            }
        )
        assertNotNull(output.code)
    }
})
