package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class OptionsBuilderTest : AnnotationSpec() {

    @Test
    fun `build empty options`() {
        val opt = options { }

        assertNotNull(opt)
    }

    @Test
    fun `build options with cwd`() {
        val opt = options {
            cwd = "/path/to/project"
        }

        assertEquals("/path/to/project", opt.cwd)
    }

    @Test
    fun `build options with swcrc`() {
        val opt = options {
            swcrc = true
        }

        assertEquals(true, opt.swcrc)
    }

    @Test
    fun `build options with filename`() {
        val opt = options {
            filename = "test.js"
            sourceFileName = "test.ts"
        }

        assertEquals("test.js", opt.filename)
        assertEquals("test.ts", opt.sourceFileName)
    }

    @Test
    fun `build options with union isModule`() {
        val opt = options {
            isModule = Union.U2<Boolean, String>(a = true)
        }

        assertTrue(opt.isModule?.a == true)
    }

    @Test
    fun `build options with union configFile false`() {
        val opt = options {
            configFile = Union.U2<String, Boolean>(b = false)
        }

        assertTrue(opt.configFile?.b == false)
    }

    @Test
    fun `build options with jscConfig`() {
        val opt = options {
            jsc = JscConfig().apply { loose = true }
        }

        assertNotNull(opt.jsc)
        assertEquals(true, opt.jsc?.loose)
    }

    @Test
    fun `build options with nested jscConfig and parser`() {
        val opt = options {
            jsc = JscConfig().apply {
                parser = tsParseOptions { tsx = true }
            }
        }

        assertNotNull(opt.jsc)
        assertNotNull(opt.jsc?.parser)
    }

    @Test
    fun `build options with jscConfig target`() {
        val opt = options {
            jsc = JscConfig().apply { target = JscTarget.ES2015 }
        }

        assertEquals(JscTarget.ES2015, opt.jsc?.target)
    }

    @Test
    fun `build options with jscConfig keepClassNames`() {
        val opt = options {
            jsc = JscConfig().apply { keepClassNames = true }
        }

        assertEquals(true, opt.jsc?.keepClassNames)
    }

    @Test
    fun `build options with multiple fields`() {
        val opt = options {
            cwd = "/project"
            swcrc = false
            filename = "app.js"
            jsc = JscConfig().apply {
                loose = true
                target = JscTarget.ES2020
            }
        }

        assertEquals("/project", opt.cwd)
        assertEquals(false, opt.swcrc)
        assertEquals("app.js", opt.filename)
        assertEquals(true, opt.jsc?.loose)
        assertEquals(JscTarget.ES2020, opt.jsc?.target)
    }

    @Test
    fun `serialize empty options`() {
        val opt = options { }
        val json = Json.encodeToString(opt)

        assertNotNull(json)
    }

    @Test
    fun `serialize and deserialize options`() {
        val opt = options {
            cwd = "/test"
            swcrc = true
        }

        val json = Json.encodeToString(opt)
        val decoded = Json.decodeFromString<Options>(json)

        assertEquals("/test", decoded.cwd)
        assertEquals(true, decoded.swcrc)
    }

    @Test
    fun `build options with inlineSourcesContent`() {
        val opt = options {
            inlineSourcesContent = true
        }

        assertEquals(true, opt.inlineSourcesContent)
    }

    @Test
    fun `build options with sourceMaps union`() {
        val opt = options {
            sourceMaps = Union.U2<Boolean, String>(b = "inline")
        }

        assertEquals("inline", opt.sourceMaps?.b)
    }

    @Test
    fun `build options with jscConfig externalHelpers`() {
        val opt = options {
            jsc = JscConfig().apply { externalHelpers = true }
        }

        assertEquals(true, opt.jsc?.externalHelpers)
    }
}
