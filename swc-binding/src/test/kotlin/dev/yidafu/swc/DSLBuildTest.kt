package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DSLBuildTest : AnnotationSpec() {
    private val swcNative = SwcNative()

    // ==================== DSL Build Complex AST Nodes Tests ====================

    @Test
    fun `build Module with DSL`() {
        val mod = module {
            span = emptySpan()
            body = arrayOf()
        }

        assertNotNull(mod)
        mod.shouldBeInstanceOf<Module>()
        assertNotNull(mod.span)
        assertNotNull(mod.body)
    }

    @Test
    fun `build VariableDeclaration with DSL`() {
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

    @Test
    fun `build FunctionDeclaration with DSL`() {
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

    @Test
    fun `build ObjectExpression with DSL`() {
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

    @Test
    fun `build ArrayExpression with DSL`() {
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

    @Test
    fun `build CallExpression with DSL`() {
        val mod = module {
            span = emptySpan()
            body = arrayOf(
                expressionStatement {
                    span = emptySpan()
                    expression = callExpression {
                        span = emptySpan()
                        callee = identifier {
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

    @Test
    fun `build ClassDeclaration with DSL`() {
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

    // ==================== DSL Build Round-trip Tests ====================

    @Test
    fun `DSL build and printSync round-trip`() {
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

    @Test
    fun `DSL build parseSync printSync round-trip`() {
        val originalCode = "const x = 42;"
        val parsed = swcNative.parseSync(originalCode, esParseOptions { }, "test.js") as Module

        val output = swcNative.printSync(parsed, options { })
        assertNotNull(output.code)

        val reparsed = swcNative.parseSync(output.code, esParseOptions { }, "test.js") as Module
        assertNotNull(reparsed)
        assertNotNull(reparsed.body)
    }

    @Test
    fun `DSL build Module compare with parseSync result`() {
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

    // ==================== DSL Build AST Node Type Validation Tests ====================

    @Test
    fun `DSL build validates AST node types`() {
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

    @Test
    fun `DSL build validates BinaryExpression types`() {
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

    @Test
    fun `DSL build validates CallExpression types`() {
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

    @Test
    fun `DSL build validates ArrowFunctionExpression types`() {
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

    @Test
    fun `DSL build validates TemplateLiteral types`() {
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

    @Test
    fun `DSL build complex nested structure`() {
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
}
