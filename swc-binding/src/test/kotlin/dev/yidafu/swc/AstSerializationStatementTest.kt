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
 * Statement serialization tests
 * Split from AstSerializationTest
 */
class AstSerializationStatementTest : ShouldSpec({

    should("serialize and deserialize IfStatement") {
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

    should("serialize and deserialize ForStatement") {
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
})
