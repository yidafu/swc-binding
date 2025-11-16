package dev.yidafu.swc

import kotlin.test.assertEquals
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test

class PlatformTest : AnnotationSpec() {
    @Test
    fun `current is mac`() {
        assertEquals(DllLoader.Platform.current, DllLoader.Platform.Mac)
        DllLoader.Platform.Mac.isM1()
    }
}