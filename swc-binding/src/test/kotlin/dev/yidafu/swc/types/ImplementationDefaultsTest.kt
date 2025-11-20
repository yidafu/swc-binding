package dev.yidafu.swc.generated

import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertNull

class ImplementationDefaultsTest : AnnotationSpec() {
    @Test
    fun `binary expression implementation properties default values`() {
        val impl = BinaryExpression()

        // type property has been removed, using @SerialName and @JsonClassDiscriminator instead
        // No longer verify type property default value
        assertNull(impl.operator, "operator should default to null")
        assertNull(impl.left, "left should default to null")
        assertNull(impl.right, "right should default to null")
    }
}
