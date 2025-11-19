package dev.yidafu.swc.generated

import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertNull

class ImplementationDefaultsTest : AnnotationSpec() {
    @Test
    fun `binary expression implementation properties default values`() {
        val impl = BinaryExpression()

        // type 属性已移除，使用 @SerialName 和 @JsonClassDiscriminator 代替
        // 不再验证 type 属性的默认值
        assertNull(impl.operator, "operator should default to null")
        assertNull(impl.left, "left should default to null")
        assertNull(impl.right, "right should default to null")
    }
}
