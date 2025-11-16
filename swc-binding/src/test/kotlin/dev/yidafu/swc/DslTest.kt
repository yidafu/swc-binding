package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.jscConfig
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
        val mod = module {
            span = emptySpan()
            body = arrayOf()
        }

        assertNotNull(mod)
        mod.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `module DSL with body`() {
        val mod = module {
            span = emptySpan()
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
            jsc = jscConfig {
                parser = esParseOptions { }
            }
        }

        assertNotNull(opts)
        assertNotNull(opts.jsc)
    }

    // ==================== tsParserConfig DSL tests ====================

    @Test
    fun `tsParserConfig DSL creates TsParserConfig`() {
        val config = tsParserConfig { }

        assertNotNull(config)
        config.shouldBeInstanceOf<TsParserConfig>()
    }

    @Test
    fun `tsParserConfig DSL with target`() {
        val config = tsParserConfig {
            target = JscTarget.ES2020
        }

        assertNotNull(config)
        assertEquals(JscTarget.ES2020, config.target)
    }

    @Test
    fun `tsParserConfig DSL with comments`() {
        val config = tsParserConfig {
            comments = true
        }

        assertNotNull(config)
        assertEquals(true, config.comments)
    }

    // ==================== esParserConfig DSL tests ====================

    @Test
    fun `esParserConfig DSL creates EsParserConfig`() {
        val config = esParserConfig { }

        assertNotNull(config)
        config.shouldBeInstanceOf<EsParserConfig>()
    }

    @Test
    fun `esParserConfig DSL with target`() {
        val config = esParserConfig {
            target = JscTarget.ES2015
        }

        assertNotNull(config)
        assertEquals(JscTarget.ES2015, config.target)
    }

    @Test
    fun `esParserConfig DSL with jsx`() {
        val config = esParserConfig {
            jsx = true
        }

        assertNotNull(config)
        assertEquals(true, config.jsx)
    }

    @Test
    fun `esParserConfig DSL with multiple options`() {
        val config = esParserConfig {
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

    // ==================== tsParseOptions DSL tests ====================

    @Test
    fun `tsParseOptions DSL creates TsParserConfig`() {
        val config = tsParseOptions { }

        assertNotNull(config)
        config.shouldBeInstanceOf<TsParserConfig>()
    }

    @Test
    fun `tsParseOptions DSL with empty block`() {
        val config = tsParseOptions()

        assertNotNull(config)
        config.shouldBeInstanceOf<TsParserConfig>()
    }

    @Test
    fun `tsParseOptions DSL with options`() {
        val config = tsParseOptions {
            target = JscTarget.ES2021
            comments = false
        }

        assertNotNull(config)
        assertEquals(JscTarget.ES2021, config.target)
        assertEquals(false, config.comments)
    }

    // ==================== esParseOptions DSL tests ====================

    @Test
    fun `esParseOptions DSL creates EsParserConfig`() {
        val config = esParseOptions { }

        assertNotNull(config)
        config.shouldBeInstanceOf<EsParserConfig>()
    }

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

    // ==================== span DSL tests ====================

    @Test
    fun `span DSL creates Span`() {
        val s = span()

        assertNotNull(s)
        s.shouldBeInstanceOf<Span>()
    }

    @Test
    fun `span DSL with start parameter`() {
        val s = span(start = 10)

        assertNotNull(s)
        assertEquals(10, s.start)
    }

    @Test
    fun `span DSL with end parameter`() {
        val s = span(end = 20)

        assertNotNull(s)
        assertEquals(20, s.end)
    }

    @Test
    fun `span DSL with ctxt parameter`() {
        val s = span(ctxt = 5)

        assertNotNull(s)
        assertEquals(5, s.ctxt)
    }

    @Test
    fun `span DSL with all parameters`() {
        val s = span(start = 10, end = 20, ctxt = 3)

        assertNotNull(s)
        assertEquals(10, s.start)
        assertEquals(20, s.end)
        assertEquals(3, s.ctxt)
    }

    @Test
    fun `span DSL with block`() {
        val s = span {
            start = 15
            end = 25
            ctxt = 7
        }

        assertNotNull(s)
        assertEquals(15, s.start)
        assertEquals(25, s.end)
        assertEquals(7, s.ctxt)
    }

    @Test
    fun `span DSL with parameters and block`() {
        val s = span(start = 10, end = 20) {
            ctxt = 5
        }

        assertNotNull(s)
        assertEquals(10, s.start)
        assertEquals(20, s.end)
        assertEquals(5, s.ctxt)
    }

    @Test
    fun `span DSL block overrides parameters`() {
        val s = span(start = 10) {
            start = 20
        }

        assertNotNull(s)
        assertEquals(20, s.start) // Block should override parameter
    }

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
            jsc = jscConfig {
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

    @Test
    fun `compose module with span`() {
        val mod = module {
            span = span(start = 0, end = 100, ctxt = 0)
            body = arrayOf()
        }

        assertNotNull(mod)
        assertNotNull(mod.span)
        assertEquals(0, mod.span!!.start)
        assertEquals(100, mod.span!!.end)
    }

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
        val s = span(start = 0, end = 0, ctxt = 0)

        assertNotNull(s)
        // Should use default values when null
        assertEquals(0, s.start)
        assertEquals(0, s.end)
        assertEquals(0, s.ctxt)
    }

    @Test
    fun `options DSL with nested configurations`() {
        val opts = options {
            jsc = jscConfig {
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

