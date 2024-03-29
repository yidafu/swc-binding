package dev.yidafu.swc.types

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test

class AstNodeTest {
    private val json = Json { encodeDefaults = true }

    @Test
    fun `binary ast tree`() {
        val expr = BinaryExpressionImpl().apply {
            operator = BinaryOperator.UnaryPlus
            left = NumericLiteralImpl().apply {
                value = 2.0
                raw = "2"
                span = Span().apply {
                    start = 1
                    end = 2
                    ctxt = 3
                }
            }

            right = NumericLiteralImpl().apply {
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

        val jsonStr = json.encodeToString(expr)
        println(jsonStr)
        val node = Json.decodeFromString<BinaryExpression>(jsonStr)
//        assertEquals(node, "BinaryExpression")
    }
}