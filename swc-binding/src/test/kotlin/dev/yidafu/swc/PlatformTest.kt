package dev.yidafu.swc

import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.assertEquals

class PlatformTest : ShouldSpec({
    should("current is mac") {
        assertEquals(DllLoader.Platform.current, DllLoader.Platform.Mac)
        DllLoader.Platform.Mac.isM1()
    }
})