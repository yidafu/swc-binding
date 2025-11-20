package dev.yidafu.swc.util

import dev.yidafu.swc.DllLoader
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.assertEquals

class PlatformTest : ShouldSpec({
    should("current is mac") {
        assertEquals(DllLoader.Platform.current, DllLoader.Platform.Mac)
        DllLoader.Platform.Mac.isM1()
    }
})