package dev.yidafu.swc.dsl

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.emptySpan
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * DSL build validation tests
 * Split from DSLBuildTest
 */
class DSLBuildValidationTest : ShouldSpec({
    val swcNative = SwcNative()

    should("DSL build validates AST node types") {
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
                                value = "result"
                                optional = false
                            }
                            init = conditionalExpression {
                                span = emptySpan()
                                test = identifier {
                                    span = emptySpan()
                                    value = "condition"
                                    optional = false
                                }
                                consequent = stringLiteral {
                                    span = emptySpan()
                                    value = "true"
                                    raw = "\"true\""
                                }
                                alternate = stringLiteral {
                                    span = emptySpan()
                                    value = "false"
                                    raw = "\"false\""
                                }
                            }
                        }
                    )
                }
            )
        }

        assertNotNull(mod)
        val declaration = mod.body!![0].shouldBeInstanceOf<VariableDeclaration>()
        val declarator = declaration.declarations!![0].shouldBeInstanceOf<VariableDeclarator>()
        val condExpr = declarator.init.shouldBeInstanceOf<ConditionalExpression>()
        assertNotNull(condExpr.test)
        assertNotNull(condExpr.consequent)
        assertNotNull(condExpr.alternate)
    }

    should("DSL build validates BinaryExpression types") {
        val mod = module {
            span = emptySpan()
            body = arrayOf(
                expressionStatement {
                    span = emptySpan()
                    expression = binaryExpression {
                        span = emptySpan()
                        operator = BinaryOperator.Addition
                        left = numericLiteral {
                            span = emptySpan()
                            value = 10.0
                            raw = "10"
                        }
                        right = numericLiteral {
                            span = emptySpan()
                            value = 20.0
                            raw = "20"
                        }
                    }
                }
            )
        }

        assertNotNull(mod)
        val exprStmt = mod.body!![0].shouldBeInstanceOf<ExpressionStatement>()
        val binExpr = exprStmt.expression.shouldBeInstanceOf<BinaryExpression>()
        assertEquals(BinaryOperator.Addition, binExpr.operator)
        assertNotNull(binExpr.left)
        assertNotNull(binExpr.right)
    }

    should("DSL build validates CallExpression types") {
        val mod = module {
            span = emptySpan()
            body = arrayOf(
                expressionStatement {
                    span = emptySpan()
                    expression = callExpression {
                        span = emptySpan()
                        callee = memberExpression {
                            span = emptySpan()
                            `object` = identifier {
                                span = emptySpan()
                                value = "console"
                                optional = false
                            }
                            `property` = identifier {
                                span = emptySpan()
                                value = "log"
                                optional = false
                            }
                        }
                        arguments = arrayOf(
                            argument {
                                expression = stringLiteral {
                                    span = emptySpan()
                                    value = "test"
                                    raw = "\"test\""
                                }
                            }
                        )
                    }
                }
            )
        }

        assertNotNull(mod)
        val exprStmt = mod.body!![0].shouldBeInstanceOf<ExpressionStatement>()
        val callExpr = exprStmt.expression.shouldBeInstanceOf<CallExpression>()
        assertNotNull(callExpr.callee)
        assertNotNull(callExpr.arguments)
        assertEquals(1, callExpr.arguments!!.size)
    }

    should("DSL build validates ArrowFunctionExpression types") {
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
                                value = "add"
                                optional = false
                            }
                            init = arrowFunctionExpression {
                                span = emptySpan()
                                params = arrayOf(
                                    identifier {
                                        span = emptySpan()
                                        value = "a"
                                        optional = false
                                    } as Pattern,
                                    identifier {
                                        span = emptySpan()
                                        value = "b"
                                        optional = false
                                    } as Pattern
                                )
                                body = binaryExpression {
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
                                generator = false
                                async = false
                            }
                        }
                    )
                }
            )
        }

        assertNotNull(mod)
        val declaration = mod.body!![0].shouldBeInstanceOf<VariableDeclaration>()
        val declarator = declaration.declarations!![0].shouldBeInstanceOf<VariableDeclarator>()
        val arrowFunc = declarator.init.shouldBeInstanceOf<ArrowFunctionExpression>()
        assertNotNull(arrowFunc.params)
        assertEquals(2, arrowFunc.params!!.size)
        assertNotNull(arrowFunc.body)
    }

    should("DSL build validates TemplateLiteral types") {
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
                                value = "message"
                                optional = false
                            }
                            init = templateLiteral {
                                span = emptySpan()
                                expressions = arrayOf(
                                    identifier {
                                        span = emptySpan()
                                        value = "name"
                                        optional = false
                                    }
                                )
                                quasis = arrayOf(
                                    templateElement {
                                        span = emptySpan()
                                        raw = "Hello, "
                                        cooked = "Hello, "
                                        tail = false
                                    },
                                    templateElement {
                                        span = emptySpan()
                                        raw = "!"
                                        cooked = "!"
                                        tail = true
                                    }
                                )
                            }
                        }
                    )
                }
            )
        }

        assertNotNull(mod)
        val declaration = mod.body!![0].shouldBeInstanceOf<VariableDeclaration>()
        val declarator = declaration.declarations!![0].shouldBeInstanceOf<VariableDeclarator>()
        val templateLit = declarator.init.shouldBeInstanceOf<TemplateLiteral>()
        assertNotNull(templateLit.expressions)
        assertNotNull(templateLit.quasis)
        assertEquals(1, templateLit.expressions!!.size)
        assertEquals(2, templateLit.quasis!!.size)
    }

    should("DSL build complex nested structure") {
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
                                value = "result"
                                optional = false
                            }
                            init = callExpression {
                                span = emptySpan()
                                callee = memberExpression {
                                    span = emptySpan()
                                    `object` = arrayExpression {
                                        span = emptySpan()
                                        elements = arrayOf(
                                            argument {
                                                expression = numericLiteral {
                                                    span = emptySpan()
                                                    value = 1.0
                                                    raw = "1"
                                                }
                                            },
                                            argument {
                                                expression = numericLiteral {
                                                    span = emptySpan()
                                                    value = 2.0
                                                    raw = "2"
                                                }
                                            }
                                        )
                                    }
                                    `property` = identifier {
                                        span = emptySpan()
                                        value = "map"
                                        optional = false
                                    }
                                }
                                arguments = arrayOf(
                                    argument {
                                        expression = arrowFunctionExpression {
                                            span = emptySpan()
                                            params = arrayOf(
                                                identifier {
                                                    span = emptySpan()
                                                    value = "x"
                                                    optional = false
                                                } as Pattern
                                            )
                                            body = binaryExpression {
                                                span = emptySpan()
                                                operator = BinaryOperator.Multiplication
                                                left = identifier {
                                                    span = emptySpan()
                                                    value = "x"
                                                    optional = false
                                                }
                                                right = numericLiteral {
                                                    span = emptySpan()
                                                    value = 2.0
                                                    raw = "2"
                                                }
                                            }
                                            generator = false
                                            async = false
                                        }
                                    }
                                )
                            }
                        }
                    )
                }
            )
        }

        assertNotNull(mod)
        val output = swcNative.printSync(mod, options { })
        assertNotNull(output.code)
        assertTrue(output.code.isNotEmpty())
    }
})
