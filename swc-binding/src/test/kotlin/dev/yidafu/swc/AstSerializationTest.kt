package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import dev.yidafu.swc.generated.dsl.createBindingIdentifier
import dev.yidafu.swc.generated.dsl.createIdentifier
import dev.yidafu.swc.generated.dsl.createTemplateLiteral
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test

/**
 * AST 节点序列化和反序列化测试
 * 
 * 测试各种 AST 节点类型的序列化和反序列化功能，确保：
 * 1. 序列化后的 JSON 格式正确
 * 2. 反序列化能够正确恢复对象
 * 3. 往返序列化（序列化 -> 反序列化 -> 序列化）结果一致
 * 4. 多态序列化正常工作
 */
class AstSerializationTest : AnnotationSpec() {

    @Test
    fun `serialize and deserialize Identifier`() {
        val identifier = createIdentifier {
            value = "test"
            optional = false
            span = span {
                start = 0
                end = 4
            }
        }

        val json = astJson.encodeToString(identifier)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"Identifier\""
        json shouldContain "\"value\":\"test\""

        val deserialized = astJson.decodeFromString<Identifier>(json)
        deserialized.value shouldBe "test"
        deserialized.optional shouldBe false
        deserialized.span.start shouldBe 0
        deserialized.span.end shouldBe 4
    }

    @Test
    fun `round-trip serialize Identifier`() {
        val original = createIdentifier {
            value = "x"
            optional = true
            span = span {
                start = 10
                end = 11
                ctxt = 2
            }
        }

        val json1 = astJson.encodeToString(original)
        val deserialized = astJson.decodeFromString<Identifier>(json1)
        val json2 = astJson.encodeToString(deserialized)

        json1 shouldBe json2
    }

    @Test
    fun `serialize and deserialize StringLiteral`() {
        val literal: Literal = createStringLiteral {
            value = "hello"
            raw = "\"hello\""
            span = span {
                start = 0
                end = 7
            }
        }

        val json = astJson.encodeToString(literal)
        json.shouldNotBeNull()
        // 通过 sealed interface Literal 序列化时，会包含 type 字段
        json shouldContain "\"type\":\"StringLiteral\""
        json shouldContain "\"value\":\"hello\""
        json shouldContain "\"raw\":\"\\\"hello\\\"\""

        val deserialized = astJson.decodeFromString<Literal>(json)
        deserialized.shouldBeInstanceOf<StringLiteral>()
        (deserialized as StringLiteral).value shouldBe "hello"
        (deserialized as StringLiteral).raw shouldBe "\"hello\""
    }

    @Test
    fun `serialize and deserialize NumericLiteral`() {
        val literal: Literal = createNumericLiteral {
            value = 42.0
            raw = "42"
            span = span {
                start = 0
                end = 2
            }
        }

        val json = astJson.encodeToString(literal)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"NumericLiteral\""
        
        val deserialized = astJson.decodeFromString<Literal>(json)
        deserialized.shouldBeInstanceOf<NumericLiteral>()
        (deserialized as NumericLiteral).value shouldBe 42.0
        (deserialized as NumericLiteral).raw shouldBe "42"
    }

    @Test
    fun `serialize and deserialize BooleanLiteral`() {
        val literal: Literal = createBooleanLiteral {
            value = true
            span = emptySpan()
        }

        val json = astJson.encodeToString(literal)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"BooleanLiteral\""
        
        val deserialized = astJson.decodeFromString<Literal>(json)
        deserialized.shouldBeInstanceOf<BooleanLiteral>()
        (deserialized as BooleanLiteral).value shouldBe true
    }

    @Test
    fun `serialize and deserialize Module`() {
        val module = createModule {
            body = arrayOf(
                createVariableDeclaration {
                    kind = VariableDeclarationKind.CONST
                    declarations = arrayOf(
                        createVariableDeclarator {
                            id = createIdentifier {
                                value = "x"
                                span = emptySpan()
                            }
                            init = createNumericLiteral {
                                value = 1.0
                                raw = "1"
                                span = emptySpan()
                            }
                        }
                    )
                    span = emptySpan()
                }
            )
            span = emptySpan()
        }

        val json = astJson.encodeToString(module as Program)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"Module\""

        val deserialized = astJson.decodeFromString<Program>(json) as Module
        deserialized.body.shouldNotBeNull()
        deserialized.body!!.size shouldBe 1
        deserialized.body!![0].shouldBeInstanceOf<VariableDeclaration>()
    }

    @Test
    fun `round-trip serialize Module`() {
        val original = createModule {
            body = arrayOf(
                createVariableDeclaration {
                    kind = VariableDeclarationKind.LET
                    declarations = arrayOf(
                        createVariableDeclarator {
                            id = createIdentifier {
                                value = "y"
                                span = emptySpan()
                            }
                            init = createStringLiteral {
                                value = "test"
                                raw = "\"test\""
                                span = emptySpan()
                            }
                        }
                    )
                    span = emptySpan()
                }
            )
            span = emptySpan()
        }

        val json1 = astJson.encodeToString(original)
        val deserialized = astJson.decodeFromString<Module>(json1)
        val json2 = astJson.encodeToString(deserialized)

        json1 shouldBe json2
    }

    @Test
    fun `serialize and deserialize VariableDeclaration`() {
        val decl: Declaration = createVariableDeclaration {
            kind = VariableDeclarationKind.VAR
                    declarations = arrayOf(
                        createVariableDeclarator {
                            id = createIdentifier {
                                value = "counter"
                                span = emptySpan()
                            }
                    init = createNumericLiteral {
                        value = 0.0
                        raw = "0"
                        span = emptySpan()
                    }
                }
            )
            span = emptySpan()
        }

        val json = astJson.encodeToString(decl)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"VariableDeclaration\""
        
        val deserialized = astJson.decodeFromString<Declaration>(json)
        deserialized.shouldBeInstanceOf<VariableDeclaration>()
        (deserialized as VariableDeclaration).kind shouldBe VariableDeclarationKind.VAR
        (deserialized as VariableDeclaration).declarations.shouldNotBeNull()
        (deserialized as VariableDeclaration).declarations!!.size shouldBe 1
    }

    @Test
    fun `serialize and deserialize ClassDeclaration`() {
        val classDecl = createClassDeclaration {
            identifier = createIdentifier {
                value = "MyClass"
                span = emptySpan()
            }
            declare = false
            body = arrayOf()
            superClass = null
            isAbstract = false
            span = emptySpan()
        }

        val json = astJson.encodeToString(classDecl as Declaration)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"ClassDeclaration\""
        json shouldContain "\"identifier\""

        val deserialized = astJson.decodeFromString<Declaration>(json)
        deserialized.shouldBeInstanceOf<ClassDeclaration>()
        (deserialized as ClassDeclaration).identifier.shouldNotBeNull()
        (deserialized as ClassDeclaration).identifier!!.value shouldBe "MyClass"
    }

    @Test
    fun `serialize and deserialize FunctionDeclaration`() {
        val funcDecl: Declaration = FunctionDeclaration().apply {
            identifier = createIdentifier {
                value = "myFunction"
                span = emptySpan()
            }
            declare = false
            params = arrayOf()
            body = null
            span = emptySpan()
        }

        val json = astJson.encodeToString(funcDecl)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"FunctionDeclaration\""
        
        val deserialized = astJson.decodeFromString<Declaration>(json)
        deserialized.shouldBeInstanceOf<FunctionDeclaration>()
        (deserialized as FunctionDeclaration).identifier.shouldNotBeNull()
        (deserialized as FunctionDeclaration).identifier!!.value shouldBe "myFunction"
    }

    @Test
    fun `serialize and deserialize BinaryExpression`() {
        val expr = createBinaryExpression {
            operator = BinaryOperator.Addition
            left = createNumericLiteral {
                value = 1.0
                raw = "1"
                span = emptySpan()
            }
            right = createNumericLiteral {
                value = 2.0
                raw = "2"
                span = emptySpan()
            }
            span = emptySpan()
        }

        val json = astJson.encodeToString(expr as Expression)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"BinaryExpression\""
        json shouldContain "\"operator\":\"+\""

        val deserialized = astJson.decodeFromString<Expression>(json)
        deserialized.shouldBeInstanceOf<BinaryExpression>()
        (deserialized as BinaryExpression).operator shouldBe BinaryOperator.Addition
        (deserialized as BinaryExpression).left.shouldBeInstanceOf<NumericLiteral>()
        (deserialized as BinaryExpression).right.shouldBeInstanceOf<NumericLiteral>()
    }

    @Test
    fun `serialize and deserialize CallExpression`() {
        val callExpr: Expression = createCallExpression {
            callee = createIdentifier {
                value = "console"
                span = emptySpan()
            }
            arguments = arrayOf(
                Argument().apply {
                    expression = createStringLiteral {
                        value = "Hello"
                        raw = "\"Hello\""
                        span = emptySpan()
                    }
                }
            )
            span = emptySpan()
        }

        val json = astJson.encodeToString(callExpr)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"CallExpression\""
        
        val deserialized = astJson.decodeFromString<Expression>(json)
        deserialized.shouldBeInstanceOf<CallExpression>()
        (deserialized as CallExpression).callee.shouldBeInstanceOf<Identifier>()
        ((deserialized as CallExpression).callee as Identifier).value shouldBe "console"
        (deserialized as CallExpression).arguments.shouldNotBeNull()
        (deserialized as CallExpression).arguments!!.size shouldBe 1
    }

    @Test
    fun `serialize and deserialize ArrayExpression`() {
        val arrayExpr: Expression = createArrayExpression {
            elements = arrayOf(
                Argument().apply {
                    expression = createNumericLiteral {
                        value = 1.0
                        raw = "1"
                        span = emptySpan()
                    }
                },
                Argument().apply {
                    expression = createNumericLiteral {
                        value = 2.0
                        raw = "2"
                        span = emptySpan()
                    }
                },
                Argument().apply {
                    expression = createNumericLiteral {
                        value = 3.0
                        raw = "3"
                        span = emptySpan()
                    }
                }
            )
            span = emptySpan()
        }

        val json = astJson.encodeToString(arrayExpr)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"ArrayExpression\""
        
        val deserialized = astJson.decodeFromString<Expression>(json)
        deserialized.shouldBeInstanceOf<ArrayExpression>()
        (deserialized as ArrayExpression).elements.shouldNotBeNull()
        (deserialized as ArrayExpression).elements!!.size shouldBe 3
    }

    @Test
    fun `serialize and deserialize ObjectExpression`() {
        val objExpr: Expression = ObjectExpression().apply {
            properties = arrayOf(
                KeyValueProperty().apply {
                    key = createIdentifier {
                        value = "name"
                        span = emptySpan()
                    }
                    value = createStringLiteral {
                        value = "John"
                        raw = "\"John\""
                        span = emptySpan()
                    }
                }
            )
            span = emptySpan()
        }

        val json = astJson.encodeToString(objExpr)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"ObjectExpression\""
        
        val deserialized = astJson.decodeFromString<Expression>(json)
        deserialized.shouldBeInstanceOf<ObjectExpression>()
        (deserialized as ObjectExpression).properties.shouldNotBeNull()
        (deserialized as ObjectExpression).properties!!.size shouldBe 1
    }

    @Test
    fun `serialize and deserialize IfStatement`() {
        val ifStmt: Statement = IfStatement().apply {
            test = createBooleanLiteral {
                value = true
                span = emptySpan()
            }
            consequent = createExpressionStatement {
                expression = createStringLiteral {
                    value = "then"
                    raw = "\"then\""
                    span = emptySpan()
                }
                span = emptySpan()
            }
            alternate = null
            span = emptySpan()
        }

        val json = astJson.encodeToString(ifStmt)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"IfStatement\""
        
        val deserialized = astJson.decodeFromString<Statement>(json)
        deserialized.shouldBeInstanceOf<IfStatement>()
        (deserialized as IfStatement).test.shouldBeInstanceOf<BooleanLiteral>()
        (deserialized as IfStatement).consequent.shouldBeInstanceOf<ExpressionStatement>()
        (deserialized as IfStatement).alternate shouldBe null
    }

    @Test
    fun `serialize and deserialize ForStatement`() {
        val forStmt = ForStatement().apply {
            init = createVariableDeclaration {
                kind = VariableDeclarationKind.LET
                declarations = arrayOf(
                    createVariableDeclarator {
                        id = createIdentifier {
                            value = "i"
                            span = emptySpan()
                        }
                        init = createNumericLiteral {
                            value = 0.0
                            raw = "0"
                            span = emptySpan()
                        }
                    }
                )
                span = emptySpan()
            }
            test = createBinaryExpression {
                operator = BinaryOperator.LessThan
                left = createIdentifier {
                    value = "i"
                    span = emptySpan()
                }
                right = createNumericLiteral {
                    value = 10.0
                    raw = "10"
                    span = emptySpan()
                }
                span = emptySpan()
            }
            update = createUpdateExpression {
                operator = UpdateOperator.Increment
                prefix = false
                argument = createIdentifier {
                    value = "i"
                    span = emptySpan()
                }
                span = emptySpan()
            }
            body = createBlockStatement {
                stmts = arrayOf()
                span = emptySpan()
            }
            span = emptySpan()
        }

        val json = astJson.encodeToString(forStmt as Statement)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"ForStatement\""
        
        val deserialized = astJson.decodeFromString<Statement>(json)
        deserialized.shouldBeInstanceOf<ForStatement>()
        (deserialized as ForStatement).init.shouldBeInstanceOf<VariableDeclaration>()
        (deserialized as ForStatement).test.shouldBeInstanceOf<BinaryExpression>()
        (deserialized as ForStatement).update.shouldBeInstanceOf<UpdateExpression>()
        (deserialized as ForStatement).body.shouldBeInstanceOf<BlockStatement>()
    }

    @Test
    fun `serialize and deserialize ExportDeclaration`() {
        val exportDecl = ExportDeclaration().apply {
            declaration = createVariableDeclaration {
                kind = VariableDeclarationKind.CONST
                declarations = arrayOf(
                    createVariableDeclarator {
                        id = createIdentifier {
                            value = "exported"
                            span = emptySpan()
                        }
                        init = null
                    }
                )
                span = emptySpan()
            }
            span = emptySpan()
        }

        val json = astJson.encodeToString(exportDecl as ModuleDeclaration)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"ExportDeclaration\""

        val deserialized = astJson.decodeFromString<ModuleDeclaration>(json)
        deserialized.shouldBeInstanceOf<ExportDeclaration>()
        deserialized.declaration.shouldNotBeNull()
        deserialized.declaration!!.shouldBeInstanceOf<VariableDeclaration>()
    }

    @Test
    fun `serialize and deserialize ImportDeclaration`() {
        val importDecl: ModuleDeclaration = createImportDeclaration {
            specifiers = arrayOf(
                createImportDefaultSpecifier {
                    local = createIdentifier {
                        value = "React"
                        span = emptySpan()
                    }
                    span = emptySpan()
                }
            )
            source = createStringLiteral {
                value = "react"
                raw = "\"react\""
                span = emptySpan()
            }
            typeOnly = false
            span = emptySpan()
        }

        val json = astJson.encodeToString(importDecl)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"ImportDeclaration\""
        
        val deserialized = astJson.decodeFromString<ModuleDeclaration>(json)
        deserialized.shouldBeInstanceOf<ImportDeclaration>()
        (deserialized as ImportDeclaration).specifiers.shouldNotBeNull()
        (deserialized as ImportDeclaration).specifiers!!.size shouldBe 1
        (deserialized as ImportDeclaration).source.shouldNotBeNull()
        ((deserialized as ImportDeclaration).source as StringLiteral).value shouldBe "react"
    }

    @Test
    fun `polymorphic serialization - Expression union`() {
        // 测试多态序列化：Expression 可以是多种类型
        val expressions: Array<Expression> = arrayOf(
            createIdentifier {
                value = "x"
                span = emptySpan()
            },
            createNumericLiteral {
                value = 42.0
                raw = "42"
                span = emptySpan()
            },
            createStringLiteral {
                value = "hello"
                raw = "\"hello\""
                span = emptySpan()
            }
        )

        val json = astJson.encodeToString(expressions)
        val deserialized = astJson.decodeFromString<Array<Expression>>(json)

        deserialized.size shouldBe 3
        deserialized[0].shouldBeInstanceOf<Identifier>()
        deserialized[1].shouldBeInstanceOf<NumericLiteral>()
        deserialized[2].shouldBeInstanceOf<StringLiteral>()
    }

    @Test
    fun `polymorphic serialization - Statement union`() {
        // 测试多态序列化：Statement 可以是多种类型
        val statements: Array<Statement> = arrayOf(
            createExpressionStatement {
                expression = createIdentifier {
                    value = "x"
                    span = emptySpan()
                }
                span = emptySpan()
            },
            createReturnStatement {
                argument = createNumericLiteral {
                    value = 1.0
                    raw = "1"
                    span = emptySpan()
                }
                span = emptySpan()
            }
        )

        val json = astJson.encodeToString(statements)
        val deserialized = astJson.decodeFromString<Array<Statement>>(json)

        deserialized.size shouldBe 2
        deserialized[0].shouldBeInstanceOf<ExpressionStatement>()
        deserialized[1].shouldBeInstanceOf<ReturnStatement>()
    }

    @Test
    fun `polymorphic serialization - Declaration union`() {
        // 测试多态序列化：Declaration 可以是多种类型
        val declarations: Array<Declaration> = arrayOf(
            createVariableDeclaration {
                kind = VariableDeclarationKind.CONST
                declarations = arrayOf()
                span = emptySpan()
            },
            FunctionDeclaration().apply {
                identifier = createIdentifier {
                    value = "fn"
                    span = emptySpan()
                }
                params = arrayOf()
                body = null
                span = emptySpan()
            },
            createClassDeclaration {
                identifier = createIdentifier {
                    value = "Cls"
                    span = emptySpan()
                }
                body = arrayOf()
                span = emptySpan()
            }
        )

        val json = astJson.encodeToString(declarations)
        val deserialized = astJson.decodeFromString<Array<Declaration>>(json)

        deserialized.size shouldBe 3
        deserialized[0].shouldBeInstanceOf<VariableDeclaration>()
        deserialized[1].shouldBeInstanceOf<FunctionDeclaration>()
        deserialized[2].shouldBeInstanceOf<ClassDeclaration>()
    }

    @Test
    fun `complex nested AST structure`() {
        // 测试复杂的嵌套 AST 结构
        val module = createModule {
            body = arrayOf(
                FunctionDeclaration().apply {
                    identifier = createIdentifier {
                        value = "calculate"
                        span = emptySpan()
                    }
                    params = arrayOf(
                        Param().apply {
                            pat = createIdentifier {
                                value = "a"
                                span = emptySpan()
                            }
                            span = emptySpan()
                        },
                        Param().apply {
                            pat = createIdentifier {
                                value = "b"
                                span = emptySpan()
                            }
                            span = emptySpan()
                        }
                    )
                    body = createBlockStatement {
                        stmts = arrayOf(
                            createReturnStatement {
                                argument = createBinaryExpression {
                                    operator = BinaryOperator.Addition
                                    left = createIdentifier {
                                        value = "a"
                                        span = emptySpan()
                                    }
                                    right = createIdentifier {
                                        value = "b"
                                        span = emptySpan()
                                    }
                                    span = emptySpan()
                                }
                                span = emptySpan()
                            }
                        )
                        span = emptySpan()
                    }
                    span = emptySpan()
                }
            )
            span = emptySpan()
        }

        val json = astJson.encodeToString(module)
        val deserialized = astJson.decodeFromString<Module>(json)

        deserialized.body.shouldNotBeNull()
        deserialized.body!!.size shouldBe 1
        val funcDecl = deserialized.body!![0] as FunctionDeclaration
        funcDecl.identifier!!.value shouldBe "calculate"
        funcDecl.params!!.size shouldBe 2
        funcDecl.body.shouldBeInstanceOf<BlockStatement>()
    }

    @Test
    fun `deserialize from JSON string`() {
        // 测试从 JSON 字符串反序列化
        val json = """
            {
                "type": "Identifier",
                "value": "testId",
                "optional": false,
                "span": {
                    "start": 0,
                    "end": 6,
                    "ctxt": 0
                }
            }
        """.trimIndent()

        val identifier = astJson.decodeFromString<Identifier>(json)

        identifier.value shouldBe "testId"
        identifier.optional shouldBe false
        identifier.span.start shouldBe 0
        identifier.span.end shouldBe 6
    }

    @Test
    fun `serialize to JSON string`() {
        // 测试序列化为 JSON 字符串
        val identifier = createIdentifier {
            value = "myVar"
            optional = true
            span = span {
                start = 10
                end = 15
                ctxt = 2
            }
        }

        val json = astJson.encodeToString(identifier)

        json.shouldNotBeNull()
        json shouldContain "\"type\":\"Identifier\""
        json shouldContain "\"value\":\"myVar\""
        json shouldContain "\"optional\":true"
        json shouldContain "\"span\""
    }

    @Test
    fun `serialize and deserialize BindingIdentifier`() {
        val bindingIdentifier = createBindingIdentifier {
            value = "param"
            optional = false
            span = span {
                start = 0
                end = 5
            }
        }

        val json = astJson.encodeToString(bindingIdentifier)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"Identifier\""
        json shouldContain "\"value\":\"param\""

        val deserialized = astJson.decodeFromString<BindingIdentifier>(json)
        deserialized.value shouldBe "param"
        deserialized.optional shouldBe false
        deserialized.span.start shouldBe 0
        deserialized.span.end shouldBe 5
    }

    @Test
    fun `round-trip serialize BindingIdentifier`() {
        val original = createBindingIdentifier {
            value = "binding"
            optional = true
            span = span {
                start = 10
                end = 17
            }
        }

        val json1 = astJson.encodeToString(original)
        val deserialized = astJson.decodeFromString<BindingIdentifier>(json1)
        val json2 = astJson.encodeToString(deserialized)

        json1 shouldBe json2
        deserialized.value shouldBe "binding"
        deserialized.optional shouldBe true
    }

    @Test
    fun `serialize and deserialize TemplateLiteral`() {
        val templateLiteral: Expression = createTemplateLiteral {
            quasis = arrayOf(
                createTemplateElement {
                    raw = "Hello "
                    cooked = "Hello "
                    tail = false
                },
                createTemplateElement {
                    raw = "!"
                    cooked = "!"
                    tail = true
                }
            )
            expressions = arrayOf(
                createIdentifier {
                    value = "name"
                    span = emptySpan()
                }
            )
            span = emptySpan()
        }

        val json = astJson.encodeToString(templateLiteral)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"TemplateLiteral\""
        json shouldContain "\"quasis\""
        json shouldContain "\"expressions\""

        val deserialized = astJson.decodeFromString<Expression>(json)
        deserialized.shouldBeInstanceOf<TemplateLiteral>()
        (deserialized as TemplateLiteral).quasis.shouldNotBeNull()
        (deserialized as TemplateLiteral).quasis!!.size shouldBe 2
        (deserialized as TemplateLiteral).expressions.shouldNotBeNull()
        (deserialized as TemplateLiteral).expressions!!.size shouldBe 1
    }

    @Test
    fun `round-trip serialize TemplateLiteral`() {
        val original = createTemplateLiteral {
            quasis = arrayOf(
                createTemplateElement {
                    raw = "Value: "
                    cooked = "Value: "
                    tail = false
                },
                createTemplateElement {
                    raw = ""
                    cooked = ""
                    tail = true
                }
            )
            expressions = arrayOf(
                createNumericLiteral {
                    value = 42.0
                    raw = "42"
                    span = emptySpan()
                }
            )
            span = emptySpan()
        }

        val json1 = astJson.encodeToString(original)
        val deserialized = astJson.decodeFromString<TemplateLiteral>(json1)
        val json2 = astJson.encodeToString(deserialized)

        json1 shouldBe json2
        deserialized.quasis!!.size shouldBe 2
        deserialized.expressions!!.size shouldBe 1
    }

    @Test
    fun `serialize and deserialize TsTemplateLiteralType`() {
        val tsTemplateLiteralType = createTsTemplateLiteralType {
            quasis = arrayOf(
                createTemplateElement {
                    raw = "Type: "
                    cooked = "Type: "
                    tail = false
                },
                createTemplateElement {
                    raw = ""
                    cooked = ""
                    tail = true
                }
            )
            span = emptySpan()
            types = arrayOf(
                createTsKeywordType {
                    kind = TsKeywordTypeKind.STRING
                    span = emptySpan()
                }
            )
        }

        val json = astJson.encodeToString(tsTemplateLiteralType as TsLiteral)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"TemplateLiteral\""
        json shouldContain "\"quasis\""
        json shouldContain "\"types\""

        val deserialized = astJson.decodeFromString<TsLiteral>(json)
        deserialized.shouldBeInstanceOf<TsTemplateLiteralType>()
        (deserialized as TsTemplateLiteralType).quasis.shouldNotBeNull()
        (deserialized as TsTemplateLiteralType).quasis!!.size shouldBe 2
        (deserialized as TsTemplateLiteralType).types.shouldNotBeNull()
        (deserialized as TsTemplateLiteralType).types!!.size shouldBe 1
    }

    @Test
    fun `round-trip serialize TsTemplateLiteralType`() {
        val original = createTemplateLiteral {
            quasis = arrayOf(
                createTemplateElement {
                    raw = "T"
                    cooked = "T"
                    tail = false
                },
                createTemplateElement {
                    raw = "Value"
                    cooked = "Value"
                    tail = true
                }
            )
            span = emptySpan()
        } as TsTemplateLiteralType
        original.types = arrayOf(
            createTsKeywordType {
                kind = TsKeywordTypeKind.STRING
                span = emptySpan()
            }
        )

        val json1 = astJson.encodeToString(original)
        val deserialized = astJson.decodeFromString<TsTemplateLiteralType>(json1)
        val json2 = astJson.encodeToString(deserialized)

        json1 shouldBe json2
        deserialized.quasis!!.size shouldBe 2
        deserialized.types!!.size shouldBe 1
    }

    @Test
    fun `compare Identifier and BindingIdentifier serialization`() {
        // 测试 Identifier 和 BindingIdentifier 的序列化差异
        val identifier = createIdentifier {
            value = "test"
            optional = false
            span = emptySpan()
        }

        val bindingIdentifier = createBindingIdentifier {
            value = "test"
            optional = false
            span = emptySpan()
        }

        val identifierJson = astJson.encodeToString(identifier)
        val bindingIdentifierJson = astJson.encodeToString(bindingIdentifier)

        // 两者都应该包含相同的 type 字段值（都是 "Identifier"）
        identifierJson shouldContain "\"type\":\"Identifier\""
        bindingIdentifierJson shouldContain "\"type\":\"Identifier\""
        identifierJson shouldContain "\"value\":\"test\""
        bindingIdentifierJson shouldContain "\"value\":\"test\""

        // 反序列化应该能正确区分
        val deserializedId = astJson.decodeFromString<Identifier>(identifierJson)
        val deserializedBinding = astJson.decodeFromString<BindingIdentifier>(bindingIdentifierJson)

        deserializedId.value shouldBe "test"
        deserializedBinding.value shouldBe "test"
    }

    @Test
    fun `compare TemplateLiteral and TsTemplateLiteralType serialization`() {
        // 测试 TemplateLiteral 和 TsTemplateLiteralType 的序列化差异
        val templateLiteral = createTemplateLiteral {
            quasis = arrayOf(
                createTemplateElement {
                    raw = "test"
                    cooked = "test"
                    tail = true
                }
            )
            expressions = arrayOf()
            span = emptySpan()
        }

        val tsTemplateLiteralType = createTsTemplateLiteralType {
            quasis = arrayOf(
                createTemplateElement {
                    raw = "test"
                    cooked = "test"
                    tail = true
                }
            )
            types = arrayOf()
            span = emptySpan()
        }

        val templateJson = astJson.encodeToString(templateLiteral)
        val tsTemplateJson = astJson.encodeToString(tsTemplateLiteralType)

        // 两者都使用 "TemplateLiteral" 作为 type
        templateJson shouldContain "\"type\":\"TemplateLiteral\""
        tsTemplateJson shouldContain "\"type\":\"TemplateLiteral\""

        // TemplateLiteral 有 expressions，TsTemplateLiteralType 有 types
        templateJson shouldContain "\"expressions\""
        tsTemplateJson shouldContain "\"types\""

        // 反序列化应该能正确区分
        val deserializedTemplate = astJson.decodeFromString<TemplateLiteral>(templateJson)
        val deserializedTsTemplate = astJson.decodeFromString<TsTemplateLiteralType>(tsTemplateJson)

        deserializedTemplate.expressions.shouldNotBeNull()
        deserializedTsTemplate.types.shouldNotBeNull()
    }
}

