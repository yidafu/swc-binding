package dev.yidafu.swc.ast.serialization

import dev.yidafu.swc.astJson
import dev.yidafu.swc.emptySpan
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import dev.yidafu.swc.generated.dsl.createTemplateLiteral
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
/**
 * Polymorphic serialization and complex structure tests
 * Split from AstSerializationTest
 */
class AstSerializationPolymorphicTest : ShouldSpec({

    should("polymorphic serialization - Expression union") {
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

    should("polymorphic serialization - Statement union") {
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

    should("polymorphic serialization - Declaration union") {
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

    should("complex nested AST structure") {
        val module = createModule {
            body = arrayOf(
                FunctionDeclaration().apply {
                    identifier = createIdentifier {
                        value = "calculate"
                        span = emptySpan()
                    }
                    params = arrayOf(
                        createParam {
                            pat = createIdentifier {
                                value = "a"
                                span = emptySpan()
                            }
                            span = emptySpan()
                        },
                        createParam {
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

    should("serialize and deserialize TsTemplateLiteralType") {
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

    should("round-trip serialize TsTemplateLiteralType") {
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

    should("compare TemplateLiteral and TsTemplateLiteralType serialization") {
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

        templateJson shouldContain "\"type\":\"TemplateLiteral\""
        tsTemplateJson shouldContain "\"type\":\"TemplateLiteral\""
        templateJson shouldContain "\"expressions\""
        tsTemplateJson shouldContain "\"types\""

        val deserializedTemplate = astJson.decodeFromString<TemplateLiteral>(templateJson)
        val deserializedTsTemplate = astJson.decodeFromString<TsTemplateLiteralType>(tsTemplateJson)

        deserializedTemplate.expressions.shouldNotBeNull()
        deserializedTsTemplate.types.shouldNotBeNull()
    }
})
