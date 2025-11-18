package dev.yidafu.swc.generated

import dev.yidafu.swc.astJson
import dev.yidafu.swc.span
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertNotNull

class AstNodeTest : AnnotationSpec() {

    @Test
    fun `binary ast tree`() {
        val expr = BinaryExpression().apply {
            operator = BinaryOperator.Addition
            left = NumericLiteral().apply {
                value = 2.0
                raw = "2"
                span = span().apply {
                    start = 1
                    end = 2
                    ctxt = 3
                }
            }

            right = NumericLiteral().apply {
                value = 2.0
                raw = "2"
                span = span().apply {
                    start = 4
                    end = 5
                    ctxt = 6
                }
            }

            span = span().apply {
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