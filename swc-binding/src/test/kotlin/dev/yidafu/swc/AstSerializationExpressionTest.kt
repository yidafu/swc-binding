package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
/**
 * Expression serialization tests
 * Split from AstSerializationTest
 */
class AstSerializationExpressionTest : ShouldSpec({

    should("serialize and deserialize BinaryExpression") {
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

    should("serialize and deserialize CallExpression") {
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

    should("serialize and deserialize ArrayExpression") {
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

    should("serialize and deserialize ObjectExpression") {
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

    should("serialize and deserialize TemplateLiteral") {
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

    should("round-trip serialize TemplateLiteral") {
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
})
