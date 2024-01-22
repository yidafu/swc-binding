package dev.yidafu.swc

import dev.yidafu.swc.dsl.* // ktlint-disable no-wildcard-imports
import dev.yidafu.swc.types.*
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.net.URL
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class SwcNativeTest {
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
        assertNotNull(ast) { "ast string can't be null " }
    }

    @Test
    fun `parse js file to ast str`() {
        val ast =
            swcNative.parseFileSync(
                getResource("test.js"),
                tsParseOptions { }
            )
        assertNotNull(ast) { "ast string can't be null " }
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
                            parser = tsParserConfig { }
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
                            parser = esParserConfig { }
                        }
                }
            )
        assertNotNull(ast) { "transform result can't be null " }
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
                            parser = tsParserConfig { }
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
                target = "es2020"
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
        assertThrows<RuntimeException> {
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
                        target = "es2020"
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
                        target = "es2020"
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
        assertIs<Module>(output)
        output.body?.let {
            val d = it[0]
            assertIs<VariableDeclaration>(d)
            val d1 = d.declarations?.get(0)
            assertIs<VariableDeclarator>(d1)
            val d2 = d1.init
            assertIs<TemplateLiteral>(d2)
            assertNotNull(d2.expressions)

            val t = it[1]
            assertIs<TsTypeAliasDeclaration>(t)
            val t1 = t.typeAnnotation
            assertIs<TsLiteralType>(t1)
            val t2 = t1.literal
            assertIs<TsTemplateLiteralType>(t2)
            assertNotNull(t2.types)
        }
    }

    @Test
    fun `parse react source code`() {
        val output1 = swcNative.parseSync(
            getResourceContent("https://unpkg.com/react@18/umd/react.development.js"),
            esParseOptions { },
            "react.development.js"
        )
        
        assertIs<Module>(output1)

        val output2 = swcNative.parseSync(
            getResourceContent("https://unpkg.com/react-dom@18/umd/react-dom.development.js"),
            esParseOptions { },
            "react-dom.development.js"
        )

        assertIs<Module>(output2)
        
    }
}