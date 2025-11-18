package dev.yidafu.swc

import dev.yidafu.swc.generated.Argument
import dev.yidafu.swc.generated.ArrayExpression
import dev.yidafu.swc.generated.NumericLiteral
import dev.yidafu.swc.generated.dsl.argument
import dev.yidafu.swc.generated.dsl.createArrayExpression
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ArrayExpressionTest : AnnotationSpec() {

    @Test
    fun `ArrayExpression elements allows holes (null entries)`() {
        val arr: ArrayExpression = ArrayExpression().apply {
            elements = arrayOf(
                null, // hole
                Argument().apply {
                    expression = NumericLiteral().apply {
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
                argument {
                    expression = NumericLiteral().apply {
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
