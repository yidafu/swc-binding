package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.jscConfig
import dev.yidafu.swc.generated.dsl.options
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class OptionsUnionAdditionalTest : AnnotationSpec() {
    @Test
    fun `isModule as string branch`() {
        val opt = options {
            isModule = Union.U2<Boolean, String>(b = "unknown")
        }
        assertEquals("unknown", opt.isModule?.b)
    }

    @Test
    fun `configFile as string path`() {
        val opt = options {
            configFile = Union.U2<String, Boolean>(a = "/path/to/.swcrc")
        }
        assertEquals("/path/to/.swcrc", opt.configFile?.a)
    }

    @Test
    fun `sourceMaps as boolean true`() {
        val opt = options {
            sourceMaps = Union.U2<Boolean, String>(a = true)
        }
        assertTrue(opt.sourceMaps?.a == true)
    }

    @Test
    fun `combine multiple union branches`() {
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
}


