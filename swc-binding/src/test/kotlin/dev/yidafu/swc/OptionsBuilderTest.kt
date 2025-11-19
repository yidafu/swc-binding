package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.core.spec.style.ShouldSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class OptionsBuilderTest : ShouldSpec({

    should("build empty options") {
        val opt = options { }

        assertNotNull(opt)
    }

    should("build options with cwd") {
        val opt = options {
            cwd = "/path/to/project"
        }

        assertEquals("/path/to/project", opt.cwd)
    }

    should("build options with swcrc") {
        val opt = options {
            swcrc = true
        }

        assertEquals(true, opt.swcrc)
    }

    should("build options with filename") {
        val opt = options {
            filename = "test.js"
            sourceFileName = "test.ts"
        }

        assertEquals("test.js", opt.filename)
        assertEquals("test.ts", opt.sourceFileName)
    }

    should("build options with union isModule") {
        val opt = options {
            isModule = Union.U2<Boolean, String>(a = true)
        }

        assertTrue(opt.isModule?.a == true)
    }

    should("build options with union configFile false") {
        val opt = options {
            configFile = Union.U2<String, Boolean>(b = false)
        }

        assertTrue(opt.configFile?.b == false)
    }

    should("build options with jscConfig") {
        val opt = options {
            jsc = JscConfig().apply { loose = true }
        }

        assertNotNull(opt.jsc)
        assertEquals(true, opt.jsc?.loose)
    }

    should("build options with nested jscConfig and parser") {
        val opt = options {
            jsc = JscConfig().apply {
                parser = tsParseOptions { tsx = true }
            }
        }

        assertNotNull(opt.jsc)
        assertNotNull(opt.jsc?.parser)
    }

    should("build options with jscConfig target") {
        val opt = options {
            jsc = JscConfig().apply { target = JscTarget.ES2015 }
        }

        assertEquals(JscTarget.ES2015, opt.jsc?.target)
    }

    should("build options with jscConfig keepClassNames") {
        val opt = options {
            jsc = JscConfig().apply { keepClassNames = true }
        }

        assertEquals(true, opt.jsc?.keepClassNames)
    }

    should("build options with multiple fields") {
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

    should("serialize empty options") {
        val opt = options { }
        val json = Json.encodeToString(opt)

        assertNotNull(json)
    }

    should("serialize and deserialize options") {
        val opt = options {
            cwd = "/test"
            swcrc = true
        }

        val json = Json.encodeToString(opt)
        val decoded = Json.decodeFromString<Options>(json)

        assertEquals("/test", decoded.cwd)
        assertEquals(true, decoded.swcrc)
    }

    should("build options with inlineSourcesContent") {
        val opt = options {
            inlineSourcesContent = true
        }

        assertEquals(true, opt.inlineSourcesContent)
    }

    should("build options with sourceMaps union") {
        val opt = options {
            sourceMaps = Union.U2<Boolean, String>(b = "inline")
        }

        assertEquals("inline", opt.sourceMaps?.b)
    }

    should("build options with jscConfig externalHelpers") {
        val opt = options {
            jsc = JscConfig().apply { externalHelpers = true }
        }

        assertEquals(true, opt.jsc?.externalHelpers)
    }
})
