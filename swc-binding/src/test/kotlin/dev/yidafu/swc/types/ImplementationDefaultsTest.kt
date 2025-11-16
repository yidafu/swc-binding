package dev.yidafu.swc.generated

import kotlin.test.assertEquals
import kotlin.test.assertNull
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test

class ImplementationDefaultsTest : AnnotationSpec() {
    @Test
    fun `binary expression implementation properties default values`() {
        val impl = BinaryExpression()

        assertEquals("BinaryExpression", impl.type, "type should default to interface name")
        assertNull(impl.operator, "operator should default to null")
        assertNull(impl.left, "left should default to null")
        assertNull(impl.right, "right should default to null")
    }
}
