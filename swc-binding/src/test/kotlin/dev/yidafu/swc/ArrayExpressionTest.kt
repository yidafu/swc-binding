package dev.yidafu.swc

import dev.yidafu.swc.generated.ArrayExpression
import dev.yidafu.swc.generated.ArrayExpressionImpl
import dev.yidafu.swc.generated.ExprOrSpreadImpl
import dev.yidafu.swc.generated.NumericLiteralImpl
import dev.yidafu.swc.generated.dsl.createArrayExpression
import dev.yidafu.swc.generated.dsl.exprOrSpread
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test

class ArrayExpressionTest : AnnotationSpec() {

    @Test
    fun `ArrayExpression elements allows holes (null entries)`() {
        val arr: ArrayExpression = ArrayExpressionImpl().apply {
            elements = arrayOf(
                null, // hole
                ExprOrSpreadImpl().apply {
                    expression = NumericLiteralImpl().apply {
                        value = 1.0
                        raw = "1"
                    }
                },
                null // hole
            )
        }

        assertNotNull(arr.elements)
        assertEquals(3, arr.elements!!.size)
        assertNull(arr.elements!![0])
        assertNotNull(arr.elements!![1])
        assertNull(arr.elements!![2])
    }

    @Test
    fun `DSL createArrayExpression works with holes`() {
        val arr = createArrayExpression {
            elements = arrayOf(
                null,
                exprOrSpread {
                    expression = NumericLiteralImpl().apply {
                        value = 2.0
                        raw = "2"
                    }
                },
                null
            )
        }

        assertNotNull(arr.elements)
        assertEquals(3, arr.elements!!.size)
        assertNull(arr.elements!![0])
        assertNotNull(arr.elements!![1])
        assertNull(arr.elements!![2])
    }
}


