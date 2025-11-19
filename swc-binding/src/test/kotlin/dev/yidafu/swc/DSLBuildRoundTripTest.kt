package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * DSL build round-trip tests
 * Split from DSLBuildTest
 */
class DSLBuildRoundTripTest : ShouldSpec({
    val swcNative = SwcNative()

    should("DSL build and printSync round-trip") {
        val mod = module {
            span = emptySpan()
            body = arrayOf(
                variableDeclaration {
                    span = emptySpan()
                    kind = VariableDeclarationKind.CONST
                    declare = false
                    declarations = arrayOf(
                        variableDeclarator {
                            span = emptySpan()
                            id = identifier {
                                span = emptySpan()
                                value = "x"
                                optional = false
                            }
                            init = numericLiteral {
                                span = emptySpan()
                                value = 42.0
                                raw = "42"
                            }
                        }
                    )
                }
            )
        }

        val output = swcNative.printSync(mod, options { })
        assertNotNull(output.code)
        assertTrue(output.code.isNotEmpty())
    }

    should("DSL build parseSync printSync round-trip") {
        val originalCode = "const x = 42;"
        val parsed = swcNative.parseSync(originalCode, esParseOptions { }, "test.js") as Module

        val output = swcNative.printSync(parsed, options { })
        assertNotNull(output.code)

        val reparsed = swcNative.parseSync(output.code, esParseOptions { }, "test.js") as Module
        assertNotNull(reparsed)
        assertNotNull(reparsed.body)
    }

    should("DSL build Module compare with parseSync result") {
        val code = """
            function add(a, b) {
                return a + b;
            }
        """.trimIndent()

        val parsed = swcNative.parseSync(code, esParseOptions { }, "test.js") as Module

        val dslBuilt = module {
            span = emptySpan()
            body = arrayOf(
                functionDeclaration {
                    span = emptySpan()
                    identifier = identifier {
                        span = emptySpan()
                        value = "add"
                        optional = false
                    }
                    declare = false
                    params = arrayOf(
                        param {
                            span = emptySpan()
                            decorators = arrayOf()
                            pat = identifier {
                                span = emptySpan()
                                value = "a"
                                optional = false
                            }
                        },
                        param {
                            span = emptySpan()
                            decorators = arrayOf()
                            pat = identifier {
                                span = emptySpan()
                                value = "b"
                                optional = false
                            }
                        }
                    )
                    decorators = arrayOf()
                    body = blockStatement {
                        span = emptySpan()
                        stmts = arrayOf(
                            returnStatement {
                                span = emptySpan()
                                argument = binaryExpression {
                                    span = emptySpan()
                                    operator = BinaryOperator.Addition
                                    left = identifier {
                                        span = emptySpan()
                                        value = "a"
                                        optional = false
                                    }
                                    right = identifier {
                                        span = emptySpan()
                                        value = "b"
                                        optional = false
                                    }
                                }
                            }
                        )
                    }
                    generator = false
                    async = false
                }
            )
        }

        val parsedOutput = swcNative.printSync(parsed, options { })
        val dslOutput = swcNative.printSync(dslBuilt, options { })

        assertNotNull(parsedOutput.code)
        assertNotNull(dslOutput.code)
    }
})
