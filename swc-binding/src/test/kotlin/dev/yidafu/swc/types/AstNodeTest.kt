package dev.yidafu.swc.types

import dev.yidafu.swc.astJson
import dev.yidafu.swc.dsl.createBinaryExpression
import dev.yidafu.swc.dsl.numericLiteral
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertNotNull

class AstNodeTest {

    @Test
    fun `binary ast tree`() {
        val expr = createBinaryExpression {
            operator = BinaryOperator.UnaryPlus
            left = numericLiteral {
                value = 2.0
                raw = "2"
                span = Span().apply {
                    start = 1
                    end = 2
                    ctxt = 3
                }
            }

            right = numericLiteral {
                value = 2.0
                raw = "2"
                span = Span().apply {
                    start = 4
                    end = 5
                    ctxt = 6
                }
            }

            span = Span().apply {
                start = 7
                end = 8
                ctxt = 9
            }
        }

        val jsonStr = astJson.encodeToString(expr)
        assertNotNull(jsonStr)
        val node = astJson.decodeFromString<BinaryExpression>(jsonStr)
        assertNotNull(node)
    }
}