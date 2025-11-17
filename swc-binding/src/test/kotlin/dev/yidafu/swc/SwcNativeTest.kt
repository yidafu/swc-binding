package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import kotlin.test.assertEquals
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
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

    // ==================== Extended JS Syntax Tests ====================

    @Test
    fun `parse object literal with computed properties`() {
        val output = swcNative.parseSync(
            """
            const key = "name";
            const obj = { [key]: "value", [key + "2"]: "value2" };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[1].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val objectExpr = declarator.init.shouldBeInstanceOf<ObjectExpression>()
            assertNotNull(objectExpr.properties)
        }
    }

    @Test
    fun `parse object destructuring with nested patterns`() {
        val output = swcNative.parseSync(
            """
            const { a, b: { c, d } } = { a: 1, b: { c: 2, d: 3 } };
            const { x, ...rest } = { x: 1, y: 2, z: 3 };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse array destructuring with rest element`() {
        val output = swcNative.parseSync(
            """
            const [first, second, ...rest] = [1, 2, 3, 4, 5];
            const [, , third] = [1, 2, 3];
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse tagged template literals`() {
        val output = swcNative.parseSync(
            """
            const result = tag`Hello world, age is 30`;
            const sql = sql`SELECT * FROM users WHERE id = 123`;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val taggedTemplate = declarator.init.shouldBeInstanceOf<TaggedTemplateExpression>()
            assertNotNull(taggedTemplate.tag)
            assertNotNull(taggedTemplate.template)
        }
    }

    @Test
    fun `parse object spread operator`() {
        val output = swcNative.parseSync(
            """
            const obj1 = { a: 1, b: 2 };
            const obj2 = { ...obj1, c: 3, d: 4 };
            const obj3 = { ...obj1, ...obj2 };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse call expression with spread arguments`() {
        val output = swcNative.parseSync(
            """
            const arr = [1, 2, 3];
            Math.max(...arr);
            func(a, ...args, z);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val callExpr = exprStmt.expression.shouldBeInstanceOf<CallExpression>()
            assertNotNull(callExpr.arguments)
        }
    }

    @Test
    fun `parse member expression and optional chaining`() {
        val output = swcNative.parseSync(
            """
            obj.property;
            obj?.optional?.property;
            obj?.["computed"];
            obj?.method?.();
            """.trimIndent(),
            esParseOptions {
                optionalChaining = true
            },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val optChaining = exprStmt.expression.shouldBeInstanceOf<OptionalChainingExpression>()
            assertNotNull(optChaining.base)
        }
    }

    @Test
    fun `parse new expression and constructor`() {
        val output = swcNative.parseSync(
            """
            const date = new Date();
            const obj = new MyClass(arg1, arg2);
            new Array(10);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val newExpr = declarator.init.shouldBeInstanceOf<NewExpression>()
            assertNotNull(newExpr.callee)
        }
    }

    @Test
    fun `parse binary expressions`() {
        val output = swcNative.parseSync(
            """
            const result = a + b * c - d / e;
            const logical = a && b || c;
            const comparison = a === b && c !== d;
            const bitwise = a | b & c ^ d;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val binExpr = declarator.init.shouldBeInstanceOf<BinaryExpression>()
            assertNotNull(binExpr.left)
            assertNotNull(binExpr.right)
        }
    }

    @Test
    fun `parse unary expressions`() {
        val output = swcNative.parseSync(
            """
            const neg = -x;
            const not = !value;
            const bitwiseNot = ~num;
            ++counter;
            --counter;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse conditional expression`() {
        val output = swcNative.parseSync(
            """
            const result = condition ? trueValue : falseValue;
            const nested = a ? b ? c : d : e;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val condExpr = declarator.init.shouldBeInstanceOf<ConditionalExpression>()
            assertNotNull(condExpr.test)
            assertNotNull(condExpr.consequent)
            assertNotNull(condExpr.alternate)
        }
    }

    @Test
    fun `parse switch statement`() {
        val output = swcNative.parseSync(
            """
            switch (value) {
                case 1:
                    break;
                case 2:
                case 3:
                    return;
                default:
                    break;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val switchStmt = items[0].shouldBeInstanceOf<SwitchStatement>()
            assertNotNull(switchStmt.discriminant)
            assertNotNull(switchStmt.cases)
        }
    }

    @Test
    fun `parse try catch finally`() {
        val output = swcNative.parseSync(
            """
            try {
                risky();
            } catch (error) {
                handle(error);
            } finally {
                cleanup();
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val tryStmt = items[0].shouldBeInstanceOf<TryStatement>()
            assertNotNull(tryStmt.block)
            assertNotNull(tryStmt.handler)
            assertNotNull(tryStmt.finalizer)
        }
    }

    @Test
    fun `parse while and do while loops`() {
        val output = swcNative.parseSync(
            """
            while (condition) {
                doSomething();
            }
            
            do {
                something();
            } while (condition);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val whileStmt = items[0].shouldBeInstanceOf<WhileStatement>()
            assertNotNull(whileStmt.test)
            assertNotNull(whileStmt.body)
            
            val doWhileStmt = items[1].shouldBeInstanceOf<DoWhileStatement>()
            assertNotNull(doWhileStmt.test)
            assertNotNull(doWhileStmt.body)
        }
    }

    @Test
    fun `parse for in and for of loops`() {
        val output = swcNative.parseSync(
            """
            for (const key in object) {
                console.log(key);
            }
            
            for (const value of array) {
                console.log(value);
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val forInStmt = items[0].shouldBeInstanceOf<ForInStatement>()
            assertNotNull(forInStmt.left)
            assertNotNull(forInStmt.right)
            
            val forOfStmt = items[1].shouldBeInstanceOf<ForOfStatement>()
            assertNotNull(forOfStmt.left)
            assertNotNull(forOfStmt.right)
        }
    }

    @Test
    fun `parse yield expression in generator`() {
        val output = swcNative.parseSync(
            """
            function* gen() {
                yield 1;
                yield* anotherGen();
                return value;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val yieldExpr = body?.stmts?.get(0)?.shouldBeInstanceOf<ExpressionStatement>()
                ?.expression?.shouldBeInstanceOf<YieldExpression>()
            assertNotNull(yieldExpr)
        }
    }

    @Test
    fun `parse await expression in async function`() {
        val output = swcNative.parseSync(
            """
            async function fetchData() {
                const response = await fetch('/api');
                const data = await response.json();
                return data;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            assertNotNull(funcDecl.async)
        }
    }

    @Test
    fun `parse export all declaration`() {
        val output = swcNative.parseSync(
            """
            export * from './module';
            export * as ns from './module';
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exportAll = items[0].shouldBeInstanceOf<ExportAllDeclaration>()
            assertNotNull(exportAll.source)
        }
    }

    @Test
    fun `parse named export and default export`() {
        val output = swcNative.parseSync(
            """
            export const name = "value";
            export function func() {}
            export default class MyClass {}
            export { a, b as c } from './module';
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse dynamic import`() {
        val output = swcNative.parseSync(
            """
            const module = await import('./module');
            import('./module').then(m => m.default);
            """.trimIndent(),
            esParseOptions {
                topLevelAwait = true
            },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse object method shorthand and getters setters`() {
        val output = swcNative.parseSync(
            """
            const obj = {
                method() { return this; },
                get prop() { return this._prop; },
                set prop(value) { this._prop = value; },
                regular: function() {}
            };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse class with static and private methods`() {
        val output = swcNative.parseSync(
            """
            class MyClass {
                static staticMethod() {}
                #privateMethod() {}
                static #privateStaticMethod() {}
                get prop() { return this._prop; }
                set prop(value) { this._prop = value; }
            }
            """.trimIndent(),
            esParseOptions {
                target = JscTarget.ES2022
            },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse sequence expression`() {
        val output = swcNative.parseSync(
            """
            let a, b, c;
            a = 1, b = 2, c = 3;
            (a++, b++, c++);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val seqExpr = exprStmt.expression.shouldBeInstanceOf<SequenceExpression>()
            assertNotNull(seqExpr.expressions)
        }
    }

    @Test
    fun `parse assignment expressions`() {
        val output = swcNative.parseSync(
            """
            let x = 1;
            x += 2;
            x -= 3;
            x *= 4;
            x /= 5;
            x %= 6;
            x **= 7;
            x |= 8;
            x &= 9;
            x ^= 10;
            x <<= 11;
            x >>= 12;
            x >>>= 13;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val assignExpr = exprStmt.expression.shouldBeInstanceOf<AssignmentExpression>()
            assertNotNull(assignExpr.left)
            assertNotNull(assignExpr.right)
        }
    }

    @Test
    fun `parse update expressions`() {
        val output = swcNative.parseSync(
            """
            let x = 0;
            ++x;
            --x;
            x++;
            x--;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val updateExpr = exprStmt.expression.shouldBeInstanceOf<UpdateExpression>()
            assertNotNull(updateExpr.argument)
        }
    }

    @Test
    fun `parse throw and return statements`() {
        val output = swcNative.parseSync(
            """
            function test() {
                if (error) {
                    throw new Error("message");
                }
                return value;
                return;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val throwStmt = body?.stmts?.get(1)?.shouldBeInstanceOf<ThrowStatement>()
            assertNotNull(throwStmt)
            
            val returnStmt = body?.stmts?.get(2)?.shouldBeInstanceOf<ReturnStatement>()
            assertNotNull(returnStmt)
        }
    }

    @Test
    fun `parse break and continue statements`() {
        val output = swcNative.parseSync(
            """
            while (true) {
                if (condition) break;
                if (other) continue;
                break label;
                continue label;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val whileStmt = items[0].shouldBeInstanceOf<WhileStatement>()
            val body = whileStmt.body.shouldBeInstanceOf<BlockStatement>()
            val breakStmt = body.stmts?.get(0)?.shouldBeInstanceOf<IfStatement>()
                ?.consequent?.shouldBeInstanceOf<BlockStatement>()
                ?.stmts?.get(0)?.shouldBeInstanceOf<BreakStatement>()
            assertNotNull(breakStmt)
        }
    }

    @Test
    fun `parse labeled statement`() {
        val output = swcNative.parseSync(
            """
            outer: for (let i = 0; i < 10; i++) {
                inner: for (let j = 0; j < 10; j++) {
                    if (j === 5) break outer;
                }
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val labeledStmt = items[0].shouldBeInstanceOf<LabeledStatement>()
            assertNotNull(labeledStmt.label)
        }
    }

    // ==================== Symbol Type Tests ====================

    @Test
    fun `parse Symbol type`() {
        val output = swcNative.parseSync(
            """
            const sym = Symbol("description");
            const sym2 = Symbol();
            const key = Symbol("key");
            const obj = { [key]: "value" };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val callExpr = declarator.init.shouldBeInstanceOf<CallExpression>()
            assertNotNull(callExpr.callee)
        }
    }

    // ==================== Proxy and Reflect Tests ====================

    @Test
    fun `parse Proxy usage`() {
        val output = swcNative.parseSync(
            """
            const target = { message: "hello" };
            const handler = {
                get: function(target, prop) {
                    return target[prop];
                }
            };
            const proxy = new Proxy(target, handler);
            const value = proxy.message;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[3].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val newExpr = declarator.init.shouldBeInstanceOf<NewExpression>()
            assertNotNull(newExpr.callee)
        }
    }

    @Test
    fun `parse Reflect API usage`() {
        val output = swcNative.parseSync(
            """
            const obj = { name: "test" };
            const value = Reflect.get(obj, "name");
            Reflect.set(obj, "age", 30);
            const has = Reflect.has(obj, "name");
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val callExpr = exprStmt.expression.shouldBeInstanceOf<CallExpression>()
            assertNotNull(callExpr.callee)
        }
    }

    // ==================== Set and Map Tests ====================

    @Test
    fun `parse Set usage`() {
        val output = swcNative.parseSync(
            """
            const set = new Set([1, 2, 3]);
            set.add(4);
            set.delete(2);
            const has = set.has(1);
            const size = set.size;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val newExpr = declarator.init.shouldBeInstanceOf<NewExpression>()
            assertNotNull(newExpr.callee)
        }
    }

    @Test
    fun `parse Map usage`() {
        val output = swcNative.parseSync(
            """
            const map = new Map();
            map.set("key1", "value1");
            map.set("key2", "value2");
            const value = map.get("key1");
            const has = map.has("key2");
            map.delete("key1");
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val newExpr = declarator.init.shouldBeInstanceOf<NewExpression>()
            assertNotNull(newExpr.callee)
        }
    }

    @Test
    fun `parse WeakSet and WeakMap`() {
        val output = swcNative.parseSync(
            """
            const weakSet = new WeakSet();
            const weakMap = new WeakMap();
            const obj = {};
            weakSet.add(obj);
            weakMap.set(obj, "value");
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    // ==================== Extended Promise and async/await Tests ====================

    @Test
    fun `parse Promise with then and catch`() {
        val output = swcNative.parseSync(
            """
            const promise = new Promise((resolve, reject) => {
                setTimeout(() => resolve("done"), 1000);
            });
            
            promise.then(value => console.log(value));
            promise.catch(error => console.error(error));
            promise.finally(() => console.log("completed"));
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val newExpr = declarator.init.shouldBeInstanceOf<NewExpression>()
            assertNotNull(newExpr.callee)
        }
    }

    @Test
    fun `parse Promise all and Promise race`() {
        val output = swcNative.parseSync(
            """
            const p1 = Promise.resolve(1);
            const p2 = Promise.resolve(2);
            const p3 = Promise.resolve(3);
            
            Promise.all([p1, p2, p3]).then(values => console.log(values));
            Promise.race([p1, p2, p3]).then(value => console.log(value));
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse async function with multiple awaits`() {
        val output = swcNative.parseSync(
            """
            async function fetchUserData(userId) {
                const user = await fetchUser(userId);
                const posts = await fetchPosts(userId);
                const comments = await fetchComments(userId);
                return { user, posts, comments };
            }
            
            async function processData() {
                try {
                    const data = await fetchUserData(123);
                    return data;
                } catch (error) {
                    console.error(error);
                    throw error;
                }
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            assertNotNull(funcDecl.async)
        }
    }

    @Test
    fun `parse top-level await`() {
        val output = swcNative.parseSync(
            """
            const data = await fetch('/api/data');
            const result = await data.json();
            console.log(result);
            """.trimIndent(),
            esParseOptions {
                topLevelAwait = true
            },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val awaitExpr = declarator.init.shouldBeInstanceOf<AwaitExpression>()
            assertNotNull(awaitExpr.argument)
        }
    }

    // ==================== Extended Generator Function Tests ====================

    @Test
    fun `parse generator function with yield*`() {
        val output = swcNative.parseSync(
            """
            function* gen1() {
                yield 1;
                yield 2;
            }
            
            function* gen2() {
                yield* gen1();
                yield 3;
            }
            
            function* infinite() {
                let i = 0;
                while (true) {
                    yield i++;
                }
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[1].shouldBeInstanceOf<FunctionDeclaration>()
            assertNotNull(funcDecl.generator)
        }
    }

    @Test
    fun `parse generator with return statement`() {
        val output = swcNative.parseSync(
            """
            function* gen() {
                yield 1;
                yield 2;
                return "done";
            }
            
            const g = gen();
            const first = g.next();
            const second = g.next();
            const last = g.next();
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    // ==================== Extended Module System Tests ====================

    @Test
    fun `parse default export with declaration`() {
        val output = swcNative.parseSync(
            """
            export default function myFunction() {}
            export default class MyClass {}
            export default const value = 42;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exportDecl = items[0].shouldBeInstanceOf<ExportDefaultDeclaration>()
            assertNotNull(exportDecl.decl)
        }
    }

    @Test
    fun `parse named export with specifiers`() {
        val output = swcNative.parseSync(
            """
            export { foo, bar };
            export { foo as myFoo, bar as myBar };
            export { default } from './module';
            export { foo, default as bar } from './module';
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exportDecl = items[0].shouldBeInstanceOf<ExportNamedDeclaration>()
            assertNotNull(exportDecl.specifiers)
        }
    }

    @Test
    fun `parse import with type modifier`() {
        val output = swcNative.parseSync(
            """
            import type { Type } from './types';
            import { type Type, Value } from './module';
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse import with assert`() {
        val output = swcNative.parseSync(
            """
            import json from './data.json' assert { type: 'json' };
            import('./module.js', { assert: { type: 'module' } });
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    // ==================== Extended Transform Tests ====================

    @Test
    fun `transform with ES2015 target`() {
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
            false,
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

    @Test
    fun `transform with ES2020 target`() {
        val output = swcNative.transformSync(
            """
            const value = foo ?? 'default';
            const result = obj?.property?.nested;
            """.trimIndent(),
            false,
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

    @Test
    fun `transform with ES2022 target`() {
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
            false,
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES2022
                }
            }
        )
        assertNotNull(output.code)
    }

    @Test
    fun `transform arrow function to ES5`() {
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
        assertTrue(!output.code.contains("=>"), "ES5 target should not contain arrow functions")
    }

    @Test
    fun `transform class to ES5`() {
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
            false,
            options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                    target = JscTarget.ES5
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("class"), "ES5 target should not contain class syntax")
    }

    @Test
    fun `transform const and let to var for ES5`() {
        val output = swcNative.transformSync(
            """
            const x = 1;
            let y = 2;
            {
                const z = 3;
                let w = 4;
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
    fun `transform template literals to ES5`() {
        val output = swcNative.transformSync(
            """
            const name = "World";
            const greeting = `Hello, World!`;
            const multiLine = `Line 1
            Line 2`;
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
        assertTrue(!output.code.contains("`"), "ES5 target should not contain template literals")
    }

    @Test
    fun `transform destructuring to ES5`() {
        val output = swcNative.transformSync(
            """
            const [a, b] = [1, 2];
            const { x, y } = { x: 10, y: 20 };
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
    fun `transform spread operator to ES5`() {
        val output = swcNative.transformSync(
            """
            const arr = [...[1, 2], 3];
            const obj = { ...{ a: 1 }, b: 2 };
            func(...args);
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

    // ==================== Extended Minify Tests ====================

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
        
        val result1 = swcNative.minifySync(program, options {
            minify = false
        })
        
        val result2 = swcNative.minifySync(program, options {
            minify = true
        })
        
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
}