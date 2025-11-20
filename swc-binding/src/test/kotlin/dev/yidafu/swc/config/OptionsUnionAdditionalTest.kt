package dev.yidafu.swc.config

import dev.yidafu.swc.Union
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.jscConfig
import dev.yidafu.swc.generated.dsl.options
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class OptionsUnionAdditionalTest : ShouldSpec({
    should("isModule as string branch") {
        val opt = options {
            isModule = Union.U2<Boolean, String>(b = "unknown")
        }
        assertEquals("unknown", opt.isModule?.b)
    }

    should("configFile as string path") {
        val opt = options {
            configFile = Union.U2<String, Boolean>(a = "/path/to/.swcrc")
        }
        assertEquals("/path/to/.swcrc", opt.configFile?.a)
    }

    should("sourceMaps as boolean true") {
        val opt = options {
            sourceMaps = Union.U2<Boolean, String>(a = true)
        }
        assertTrue(opt.sourceMaps?.a == true)
    }

    should("combine multiple union branches") {
        val opt = options {
            isModule = Union.U2(a = false, b = null)
            configFile = Union.U2(a = null, b = true)
            jsc = jscConfig {
                externalHelpers = true
            }
        }
        assertTrue(opt.isModule?.a == false)
        assertTrue(opt.configFile?.b == true)
        assertNotNull(opt.jsc)
        assertTrue(opt.jsc?.externalHelpers == true)
    }
})
