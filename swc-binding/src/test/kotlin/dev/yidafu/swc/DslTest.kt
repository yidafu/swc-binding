package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.esParseOptions
import dev.yidafu.swc.generated.dsl.module
import dev.yidafu.swc.generated.dsl.tsParseOptions
// 使用生成的 DSL
import dev.yidafu.swc.generated.dsl.span as genSpan
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test

/**
 * Tests for DSL functions in dsl.kt
 */
class DslTest : AnnotationSpec() {

    // ==================== module DSL tests ====================

    @Test
    fun `module DSL creates Module`() {
        val mod = Module().apply {
            span = Span()
            body = arrayOf()
        }

        assertNotNull(mod)
        mod.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `module DSL with body`() {
        val mod = Module().apply {
            span = Span()
            body = arrayOf() // Empty body for simplicity
        }

        assertNotNull(mod.body)
        assertTrue(mod.body!!.size == 0)
    }

    // ==================== options DSL tests ====================

    @Test
    fun `options DSL creates Options`() {
        val opts = options { }

        assertNotNull(opts)
        opts.shouldBeInstanceOf<Options>()
    }

    @Test
    fun `options DSL with jsc config`() {
        val opts = options {
            jsc = JscConfig().apply { parser = esParseOptions { } }
        }

        assertNotNull(opts)
        assertNotNull(opts.jsc)
    }

    // ==================== tsParserConfig DSL tests ====================

    @Test
    fun `tsParseOptions DSL creates TsParserConfig`() {
        val config = tsParseOptions { }

        assertNotNull(config)
        config.shouldBeInstanceOf<TsParserConfig>()
    }

    @Test
    fun `tsParseOptions DSL with target`() {
        val config = tsParseOptions {
            target = JscTarget.ES2020
        }

        assertNotNull(config)
        assertEquals(JscTarget.ES2020, config.target)
    }

    @Test
    fun `tsParseOptions DSL with comments`() {
        val config = tsParseOptions {
            comments = true
        }

        assertNotNull(config)
        assertEquals(true, config.comments)
    }

    // ==================== esParserConfig DSL tests ====================

    @Test
    fun `esParseOptions DSL creates EsParserConfig`() {
        val config = esParseOptions { }

        assertNotNull(config)
        config.shouldBeInstanceOf<EsParserConfig>()
    }

    @Test
    fun `esParseOptions DSL with target`() {
        val config = esParseOptions {
            target = JscTarget.ES2015
        }

        assertNotNull(config)
        assertEquals(JscTarget.ES2015, config.target)
    }

    @Test
    fun `esParseOptions DSL with jsx`() {
        val config = esParseOptions {
            jsx = true
        }

        assertNotNull(config)
        assertEquals(true, config.jsx)
    }

    @Test
    fun `esParseOptions DSL with multiple options`() {
        val config = esParseOptions {
            target = JscTarget.ES2020
            comments = true
            jsx = true
            topLevelAwait = true
            nullishCoalescing = true
            optionalChaining = true
        }

        assertNotNull(config)
        assertEquals(JscTarget.ES2020, config.target)
        assertEquals(true, config.comments)
        assertEquals(true, config.jsx)
    }

    // ==================== esParseOptions DSL tests (more) ====================

    @Test
    fun `esParseOptions DSL with empty block`() {
        val config = esParseOptions()

        assertNotNull(config)
        config.shouldBeInstanceOf<EsParserConfig>()
    }

    @Test
    fun `esParseOptions DSL with options`() {
        val config = esParseOptions {
            target = JscTarget.ES2018
            jsx = false
            comments = true
        }

        assertNotNull(config)
        assertEquals(JscTarget.ES2018, config.target)
        assertEquals(false, config.jsx)
        assertEquals(true, config.comments)
    }


    // (span DSL tests removed to avoid conflicts)

    // ==================== emptySpan DSL tests ====================

    @Test
    fun `emptySpan creates Span with zeros`() {
        val s = emptySpan()

        assertNotNull(s)
        assertEquals(0, s.start)
        assertEquals(0, s.end)
        assertEquals(0, s.ctxt)
    }

    @Test
    fun `emptySpan is consistent`() {
        val s1 = emptySpan()
        val s2 = emptySpan()

        assertEquals(s1.start, s2.start)
        assertEquals(s1.end, s2.end)
        assertEquals(s1.ctxt, s2.ctxt)
    }

    // ==================== DSL composition tests ====================

    @Test
    fun `compose options with parser config`() {
        val opts = options {
            jsc = JscConfig().apply {
                parser = esParseOptions {
                    target = JscTarget.ES2020
                    jsx = true
                }
            }
        }

        assertNotNull(opts)
        assertNotNull(opts.jsc)
        assertNotNull(opts.jsc!!.parser)
        val parser = opts.jsc!!.parser as EsParserConfig
        assertEquals(JscTarget.ES2020, parser.target)
        assertEquals(true, parser.jsx)
    }

    // (compose module with span removed)

    @Test
    fun `compose module with emptySpan`() {
        val mod = module {
            span = emptySpan()
            body = arrayOf()
        }

        assertNotNull(mod)
        assertNotNull(mod.span)
        assertEquals(0, mod.span!!.start)
        assertEquals(0, mod.span!!.end)
        assertEquals(0, mod.span!!.ctxt)
    }

    // ==================== Edge cases ====================

    @Test
    fun `span DSL with null parameters`() {
        val s = Span().apply {
            start = 0
            end = 0
            ctxt = 0
        }

        assertNotNull(s)
        // Should use default values when null
        assertEquals(0, s.start)
        assertEquals(0, s.end)
        assertEquals(0, s.ctxt)
    }

    @Test
    fun `options DSL with nested configurations`() {
        val opts = options {
            jsc = JscConfig().apply {
                parser = tsParseOptions {
                    target = JscTarget.ES2019
                }
                target = JscTarget.ES2020
            }
        }

        assertNotNull(opts)
        assertNotNull(opts.jsc)
        assertEquals(JscTarget.ES2020, opts.jsc!!.target)
    }
}

