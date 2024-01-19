package dev.yidafu.swc.sample // ktlint-disable filename

import dev.yidafu.swc.*
import dev.yidafu.swc.dsl.*
import dev.yidafu.swc.types.TsKeywordTypeKind

fun parseSyncBasicUsage() {
    SwcNative().parseSync(
        """
          const foo = "bar";
          console.log(foo);
          """,
        """{"syntax":"ecmascript"}""",
        "temp.js"
    )
}

fun parseFileSyncBasicUsage() {
    SwcNative().parseFileSync(
        "path/to/js/code.js",
        """{"syntax":"ecmascript"}"""
    )
}

fun parseSyncEsSample() {
    SwcNative().parseSync(
        """
          const foo = "bar";
          console.log(foo);
        """.trimIndent(),
        esParseOptions {
            target = "es5"
        },
        "temp.js"
    )
}

fun parseSyncJsxSample() {
    SwcNative().parseSync(
        """
          function App() {
            return <div>App</div>
          }
        """.trimIndent(),
        esParseOptions {
            jsx = true
            target = "es5"
        },
        "temp.js"
    )
}

fun parseSyncTsSample() {
    SwcNative().parseSync(
        """
          const foo: string = "bar";
          console.log(foo);
        """.trimIndent(),
        tsParseOptions {
            target = "es5"
        },
        "temp.js"
    )
}

fun parseFileSyncEsSample() {
    SwcNative().parseFileSync(
        "path/to/js/code.js",
        esParseOptions {
            target = "es5"
        }
    )
}

fun createExampleDsl() {
    module {
        body = arrayOf(
            importDeclaration {
                specifiers = arrayOf(
                    importDefaultSpecifier {
                        local = createIdentifier {
                            span = emptySpan()
                            value = "x"
                        }
                    }
                )
                source = stringLiteral {
                    value = "./test.js"
                    raw = "./test.js"
                    span = emptySpan()
                }
                typeOnly = false
                span = emptySpan()
            },

            classDeclaration {
                identifier = createIdentifier { }
                span = emptySpan()
                body = arrayOf(
                    classProperty {
                        span = emptySpan()
                        typeAnnotation = tsTypeAnnotation {
                            span = emptySpan()
                            typeAnnotation = tsKeywordType {
                                span = emptySpan()
                                kind = TsKeywordTypeKind.STRING
                            }
                        }
                    }
                )
            }
        )
    }
}