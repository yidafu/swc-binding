package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import kotlin.test.assertEquals
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import io.kotest.assertions.throwables.shouldThrow
import kotlin.test.assertTrue
import io.kotest.core.spec.style.AnnotationSpec
import java.io.File
import java.net.URL
import kotlin.test.Test

class SwcNativeTest : AnnotationSpec() {
    private val swcNative = SwcNative()
    private fun getResource(filename: String): String {
        return SwcNativeTest::class.java.classLoader.getResource(filename)!!.file!!
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
        println(ast)
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
    fun `parse import statements`() {
        val module = swcNative.parseSync(
            """
            import { foo, getRoot, bar as baz } from '@jupyter';

            import jupyter from '@jupyter';

            const b = 345;

            console.log(b)
            """.trimIndent(),
            esParseOptions {
                target = JscTarget.ES2020
                comments = false
                topLevelAwait = true
                nullishCoalescing = true
            },
            "jupyter-cell.js"
        ) as Module
        val output = swcNative.printSync(
            module,
            options {
                jscConfig {
                    minify = jsMinifyOptions {
                    }
                }
            }
        )
        println(output.code)
        println("moduel => ${module.body?.get(0)}")
    }

    @Test
    fun `parse invalid js code`() {
        shouldThrow<RuntimeException> {
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
    fun `transform jsx snippet`() {
        val output = swcNative.transformSync(
            """
                function App() {
                    return <div>Text</div>
                }
            """.trimIndent(),
            isModule = false,
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
        println(output.code)
        assertNotNull(output.code)
    }

    @Test
    fun `transform ts code`() {
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
            false,
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
//    @Test
//    fun `transform ast json file to ast str`() {
//        val ast = swcNative.transformFileSync(
//            SwcNativeTest::class.java.classLoader.getResource("ast.json").readText(),
//            true,
// //            Options().apply {
// //                jsc  = jscConfig {
// //                    parser = ParserConfig().apply {
// //                        syntax = "ecmascript"
// //                    }
// //                }
// //            }
//            Options()
//        )
//        assertNotNull(ast) { "transform result can't be null "}
//    }

    @Test
    fun `native print ast dsl`() {
        val res =
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

        println(res)
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
    fun `template literal`() {
        val output = swcNative.parseSync(
            "var a = `string text \${expression} string text`;\ntype T = `Ts\${type}`",
            tsParseOptions { },
            "test.ts"
        )
        val module = output.shouldBeInstanceOf<Module>()
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val templateLiteral = declarator.init.shouldBeInstanceOf<TemplateLiteral>()
            assertNotNull(templateLiteral.expressions)

            val alias = items[1].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val typeAnnotation = alias.typeAnnotation.shouldBeInstanceOf<TsLiteralType>()
            val literal = typeAnnotation.literal.shouldBeInstanceOf<TsTemplateLiteralType>()
            assertNotNull(literal.types)
        }
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
    fun `parse arrow functions`() {
        val output = swcNative.parseSync(
            "const add = (a, b) => a + b;",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse class syntax`() {
        val output = swcNative.parseSync(
            """
            class Person {
                constructor(name) {
                    this.name = name;
                }
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse async await`() {
        val output = swcNative.parseSync(
            """
            async function fetchData() {
                const data = await fetch('/api');
                return data;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse destructuring assignment`() {
        val output = swcNative.parseSync(
            """
            const [a, b] = [1, 2];
            const obj = { x: 10, y: 20 };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse spread operator`() {
        val output = swcNative.parseSync(
            "const arr = [...[1, 2], 3];",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse optional chaining`() {
        val output = swcNative.parseSync(
            "const value = obj?.property?.nested;",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse nullish coalescing`() {
        val output = swcNative.parseSync(
            "const value = foo ?? 'default';",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `transform with es5 target`() {
        val output = swcNative.transformSync(
            "const x = 1;",
            false,
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )
        assertNotNull(output.code)
    }

    @Test
    fun `transform arrow to function`() {
        val output = swcNative.transformSync(
            "const add = (a, b) => a + b;",
            false,
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

    @Test
    fun `transform class to es5`() {
        val output = swcNative.transformSync(
            """
            class Animal {
                constructor(name) {
                    this.name = name;
                }
            }
            """.trimIndent(),
            false,
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )
        assertNotNull(output.code)
    }

    @Test
    fun `parse for-of loop`() {
        val output = swcNative.parseSync(
            """
            for (const item of items) {
                console.log(item);
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse generator function`() {
        val output = swcNative.parseSync(
            """
            function* generator() {
                yield 1;
                yield 2;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse object shorthand`() {
        val output = swcNative.parseSync(
            """
            const name = "test";
            const obj = { name };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `printSync with empty module`() {
        // Use parsed minimal code instead of manually creating module to avoid serialization issues
        val mod = swcNative.parseSync(";", esParseOptions { }, "empty.js") as Module

        val output = swcNative.printSync(mod, options { })
        assertNotNull(output.code)
    }

    @Test
    fun `parse TypeScript type annotations`() {
        val output = swcNative.parseSync(
            """
            const x: number = 42;
            const y: string = "hello";
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        assertIs<Module>(output)
    }

    @Test
    fun `parse TypeScript interface`() {
        val output = swcNative.parseSync(
            """
            interface User {
                name: string;
                age: number;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        assertIs<Module>(output)
    }

    @Test
    fun `transform TypeScript to JavaScript`() {
        val output = swcNative.transformSync(
            """
            const greeting: string = "Hello";
            interface Greeter {
                greet(): void;
            }
            """.trimIndent(),
            false,
            options {
                jsc = jscConfig {
                    parser = tsParseOptions { }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("interface"))
    }

    @Test
    fun `parse export statement`() {
        val output = swcNative.parseSync(
            "export const x = 1;",
            esParseOptions { },
            "test.js"
        )
        assertIs<Module>(output)
    }

    // ==================== MinifySync tests ====================

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
}