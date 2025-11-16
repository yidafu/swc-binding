@file:Suppress("ktlint:standard:filename")

package dev.yidafu.swc.sample

import dev.yidafu.swc.*
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*

fun parseSyncBasicUsage() {
    SwcNative().parseSync(
        """
          const foo = "bar";
          console.log(foo);
          """,
        """{"syntax":"ecmascript"}""",
        "temp.js",
    )
}

fun parseFileSyncBasicUsage() {
    SwcNative().parseFileSync(
        "path/to/js/code.js",
        """{"syntax":"ecmascript"}""",
    )
}

fun parseSyncEsSample() {
    SwcNative().parseSync(
        """
        const foo = "bar";
        console.log(foo);
        """.trimIndent(),
        esParseOptions {
            allowReturnOutsideFunction = true
        },
        "temp.js",
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
        },
        "temp.js",
    )
}

fun parseSyncTsSample() {
    SwcNative().parseSync(
        """
        const foo: string = "bar";
        console.log(foo);
        """.trimIndent(),
        tsParseOptions {
            tsx = true
        },
        "temp.js",
    )
}

fun parseFileSyncEsSample() {
    SwcNative().parseFileSync(
        "path/to/js/code.js",
        esParseOptions {
            dynamicImport = true
        },
    )
}

fun createExampleDsl() {
    module {
        body =
            arrayOf<ModuleItem>(
                importDeclaration {
                    specifiers =
                        arrayOf<ImportSpecifier>(
                            importDefaultSpecifier {
                                local =
                                    identifier {
                                        span = emptySpan()
                                        value = "x"
                                    }
                            },
                        )
                    source =
                        stringLiteral {
                            value = "./test.js"
                            raw = "./test.js"
                            span = emptySpan()
                        }
                    typeOnly = false
                    span = emptySpan()
                },
                classDeclaration {
                    identifier = identifier { }
                    span = emptySpan()
                    body =
                        arrayOf<ClassMember>(
                            classProperty {
                                span = emptySpan()
                                typeAnnotation =
                                    tsTypeAnnotation {
                                        span = emptySpan()
                                        typeAnnotation =
                                            tsKeywordType {
                                                span = emptySpan()
                                                kind = TsKeywordTypeKind.STRING
                                            }
                                    }
                            },
                        )
                },
            )
    }
}