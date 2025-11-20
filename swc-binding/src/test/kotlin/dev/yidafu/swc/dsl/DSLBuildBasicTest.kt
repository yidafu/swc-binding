package dev.yidafu.swc.dsl

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.emptySpan
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Basic DSL build tests
 * Split from DSLBuildTest
 */
class DSLBuildBasicTest : ShouldSpec({
    val swcNative = SwcNative()

    should("build Module with DSL") {
        val mod = module {
            span = emptySpan()
            body = arrayOf()
        }

        assertNotNull(mod)
        mod.shouldBeInstanceOf<Module>()
        assertNotNull(mod.span)
        assertNotNull(mod.body)
    }

    should("build Module with body") {
        val mod = module {
            span = emptySpan()
            body = arrayOf() // Empty body for simplicity
        }

        assertNotNull(mod.body)
        assertEquals(0, mod.body!!.size)
    }

    should("compose module with emptySpan") {
        val mod = module {
            span = emptySpan()
            body = arrayOf()
        }

        assertNotNull(mod)
        assertNotNull(mod.span)
        assertEquals(0, mod.span!!.start)
        assertEquals(0, mod.span!!.end)
        assertEquals(0, mod.span!!.ctxt)
    }

    should("build VariableDeclaration with DSL") {
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

        assertNotNull(mod)
        val declaration = mod.body!![0].shouldBeInstanceOf<VariableDeclaration>()
        assertEquals(VariableDeclarationKind.CONST, declaration.kind)
        assertNotNull(declaration.declarations)
        val declarator = declaration.declarations!![0].shouldBeInstanceOf<VariableDeclarator>()
        val id = declarator.id.shouldBeInstanceOf<Identifier>()
        assertEquals("x", id.value)
    }

    should("build FunctionDeclaration with DSL") {
        val mod = module {
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

        assertNotNull(mod)
        val funcDecl = mod.body!![0].shouldBeInstanceOf<FunctionDeclaration>()
        val id = funcDecl.identifier.shouldBeInstanceOf<Identifier>()
        assertEquals("add", id.value)
        assertNotNull(funcDecl.params)
        assertEquals(2, funcDecl.params!!.size)
    }

    should("build ObjectExpression with DSL") {
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
                                value = "obj"
                                optional = false
                            }
                            init = objectExpression {
                                span = emptySpan()
                                properties = arrayOf(
                                    keyValueProperty {
                                        key = identifier {
                                            span = emptySpan()
                                            value = "name"
                                            optional = false
                                        }
                                        value = stringLiteral {
                                            span = emptySpan()
                                            value = "test"
                                            raw = "\"test\""
                                        }
                                    },
                                    keyValueProperty {
                                        key = identifier {
                                            span = emptySpan()
                                            value = "age"
                                            optional = false
                                        }
                                        value = numericLiteral {
                                            span = emptySpan()
                                            value = 30.0
                                            raw = "30"
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
        val declaration = mod.body!![0].shouldBeInstanceOf<VariableDeclaration>()
        val declarator = declaration.declarations!![0].shouldBeInstanceOf<VariableDeclarator>()
        val objExpr = declarator.init.shouldBeInstanceOf<ObjectExpression>()
        assertNotNull(objExpr.properties)
        assertEquals(2, objExpr.properties!!.size)
    }

    should("build ArrayExpression with DSL") {
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
                                value = "arr"
                                optional = false
                            }
                            init = arrayExpression {
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
                                    },
                                    argument {
                                        expression = numericLiteral {
                                            span = emptySpan()
                                            value = 3.0
                                            raw = "3"
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
        val declaration = mod.body!![0].shouldBeInstanceOf<VariableDeclaration>()
        val declarator = declaration.declarations!![0].shouldBeInstanceOf<VariableDeclarator>()
        val arrExpr = declarator.init.shouldBeInstanceOf<ArrayExpression>()
        assertNotNull(arrExpr.elements)
        assertEquals(3, arrExpr.elements!!.size)
    }

    should("build CallExpression with DSL") {
        val mod = module {
            span = emptySpan()
            body = arrayOf(
                expressionStatement {
                    span = emptySpan()
                    expression = callExpression {
                        span = emptySpan()
                        callee = createIdentifier {
                            span = emptySpan()
                            value = "console"
                            optional = false
                        }
                        arguments = arrayOf(
                            argument {
                                expression = memberExpression {
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
                            },
                            argument {
                                expression = stringLiteral {
                                    span = emptySpan()
                                    value = "Hello"
                                    raw = "\"Hello\""
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
    }

    should("build ClassDeclaration with DSL") {
        val mod = module {
            span = emptySpan()
            body = arrayOf(
                classDeclaration {
                    span = emptySpan()
                    identifier = identifier {
                        span = emptySpan()
                        value = "MyClass"
                        optional = false
                    }
                    declare = false
                    decorators = arrayOf()
                    body = arrayOf(
                        classMethod {
                            span = emptySpan()
                            key = identifier {
                                span = emptySpan()
                                value = "method"
                                optional = false
                            }
                            kind = MethodKind.METHOD
                            function = functionExpression {
                                span = emptySpan()
                                params = arrayOf()
                                decorators = arrayOf()
                                body = blockStatement {
                                    span = emptySpan()
                                    stmts = arrayOf()
                                }
                                generator = false
                                async = false
                            }
                            isStatic = false
                            isAbstract = false
                            isOptional = false
                            accessibility = null
                        }
                    )
                    superClass = null
                    isAbstract = false
                    typeParams = null
                    superTypeParams = null
                    implements = arrayOf()
                }
            )
        }

        assertNotNull(mod)
        val classDecl = mod.body!![0].shouldBeInstanceOf<ClassDeclaration>()
        val id = classDecl.identifier.shouldBeInstanceOf<Identifier>()
        assertEquals("MyClass", id.value)
        assertNotNull(classDecl.body)
    }
})
