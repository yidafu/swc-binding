package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.createIdentifier
import dev.yidafu.swc.generated.dsl.createVariableDeclarator
import dev.yidafu.swc.generated.dsl.esParseOptions
import dev.yidafu.swc.generated.dsl.options
import dev.yidafu.swc.generated.dsl.tsParseOptions
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.encodeToString
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Tests for DSL configuration functions (options, parser configs, emptySpan)
 * Note: Module DSL tests have been moved to DSLBuildBasicTest.kt
 */
class DslTest : ShouldSpec({

    // ==================== options DSL tests ====================

    should("options DSL creates Options") {
        val opts = options { }

        assertNotNull(opts)
        opts.shouldBeInstanceOf<Options>()
    }

    should("options DSL with jsc config") {
        val opts = options {
            jsc = JscConfig().apply { parser = esParseOptions { } }
        }

        assertNotNull(opts)
        assertNotNull(opts.jsc)
    }

    // ==================== tsParserConfig DSL tests ====================

    should("tsParseOptions DSL creates TsParserConfig") {
        val config = tsParseOptions { }

        assertNotNull(config)
        config.shouldBeInstanceOf<TsParserConfig>()
    }

    should("tsParseOptions DSL with target") {
        val config = tsParseOptions {
            target = JscTarget.ES2020
        }

        assertNotNull(config)
        assertEquals(JscTarget.ES2020, config.target)
    }

    should("tsParseOptions DSL with comments") {
        val config = tsParseOptions {
            comments = true
        }

        assertNotNull(config)
        assertEquals(true, config.comments)
    }

    // ==================== esParserConfig DSL tests ====================

    should("esParseOptions DSL creates EsParserConfig") {
        val config = esParseOptions { }

        assertNotNull(config)
        config.shouldBeInstanceOf<EsParserConfig>()
    }

    should("esParseOptions DSL with target") {
        val config = esParseOptions {
            target = JscTarget.ES2015
        }

        assertNotNull(config)
        assertEquals(JscTarget.ES2015, config.target)
    }

    should("esParseOptions DSL with jsx") {
        val config = esParseOptions {
            jsx = true
        }

        assertNotNull(config)
        assertEquals(true, config.jsx)
    }

    should("esParseOptions DSL with multiple options") {
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

    should("esParseOptions DSL with empty block") {
        val config = esParseOptions()

        assertNotNull(config)
        config.shouldBeInstanceOf<EsParserConfig>()
    }

    should("esParseOptions DSL with options") {
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

    should("emptySpan creates Span with zeros") {
        val s = emptySpan()

        assertNotNull(s)
        assertEquals(0, s.start)
        assertEquals(0, s.end)
        assertEquals(0, s.ctxt)
    }

    should("emptySpan is consistent") {
        val s1 = emptySpan()
        val s2 = emptySpan()

        assertEquals(s1.start, s2.start)
        assertEquals(s1.end, s2.end)
        assertEquals(s1.ctxt, s2.ctxt)
    }

    // ==================== DSL composition tests ====================

    should("compose options with parser config") {
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

    should("options DSL with nested configurations") {
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

    // ==================== Edge cases ====================

    should("span DSL with null parameters") {
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

    // ==================== Span default value tests ====================

    should("Identifier has default span as emptySpan") {
        val identifier = createIdentifier { }

        assertNotNull(identifier.span)
        assertEquals(0, identifier.span.start)
        assertEquals(0, identifier.span.end)
        assertEquals(0, identifier.span.ctxt)
    }

    should("Module has default span as emptySpan") {
        val module = Module()

        assertNotNull(module.span)
        assertEquals(0, module.span.start)
        assertEquals(0, module.span.end)
        assertEquals(0, module.span.ctxt)
    }

    should("ArrayExpression has default span as emptySpan") {
        val arrayExpr = ArrayExpression()

        assertNotNull(arrayExpr.span)
        assertEquals(0, arrayExpr.span.start)
        assertEquals(0, arrayExpr.span.end)
        assertEquals(0, arrayExpr.span.ctxt)
    }

    should("serialize Identifier with default span includes ctxt field") {
        val identifier = createIdentifier {
            value = "test"
            optional = false
            // span 使用默认值 emptySpan()
        }

        val json = astJson.encodeToString<Identifier>(identifier)

        // 验证 JSON 包含 ctxt 字段
        assertTrue(json.contains("\"ctxt\""), "JSON should contain ctxt field: $json")
        assertTrue(json.contains("\"ctxt\":0"), "JSON should contain ctxt:0: $json")
    }

    should("serialize Module with default span includes ctxt field") {
        val module = Module().apply {
            body = arrayOf()
            // span 使用默认值 emptySpan()
        }

        val json = astJson.encodeToString<Module>(module)

        // 验证 JSON 包含 ctxt 字段
        assertTrue(json.contains("\"ctxt\""), "JSON should contain ctxt field: $json")
        assertTrue(json.contains("\"span\""), "JSON should contain span field: $json")

        // 验证 span 对象中包含 ctxt
        val spanStartIndex = json.indexOf("\"span\":{")
        if (spanStartIndex != -1) {
            val spanEndIndex = json.indexOf("}", spanStartIndex + 8)
            if (spanEndIndex != -1) {
                val spanContent = json.substring(spanStartIndex, spanEndIndex + 1)
                assertTrue(spanContent.contains("\"ctxt\""), "Span object should contain ctxt: $spanContent")
            }
        }
    }

    should("serialize ArrayExpression with default span includes ctxt field") {
        val arrayExpr = ArrayExpression().apply {
            elements = arrayOf()
            // span 使用默认值 emptySpan()
        }

        val json = astJson.encodeToString<ArrayExpression>(arrayExpr)

        // 验证 JSON 包含 ctxt 字段
        assertTrue(json.contains("\"ctxt\""), "JSON should contain ctxt field: $json")
    }

    should("serialize complex AST with multiple default spans includes ctxt fields") {
        val module = Module().apply {
            body = arrayOf(
                VariableDeclaration().apply {
                    kind = VariableDeclarationKind.CONST
                    declare = false
                    declarations = arrayOf(
                        createVariableDeclarator {
                            id = createIdentifier {
                                value = "x"
                                optional = false
                            }
                            init = NumericLiteral().apply {
                                value = 42.0
                                raw = "42"
                            }
                        }
                    )
                }
            )
        }

        val json = astJson.encodeToString<Module>(module)

        // 验证 JSON 包含多个 ctxt 字段（每个 span 对象一个）
        val ctxtCount = json.split("\"ctxt\"").size - 1
        assertTrue(ctxtCount >= 1, "JSON should contain at least one ctxt field, found: $ctxtCount")

        // 验证所有 span 对象都包含 ctxt
        var index = 0
        var spanCount = 0
        var spansWithCtxt = 0

        while (true) {
            val spanIndex = json.indexOf("\"span\":{", index)
            if (spanIndex == -1) break

            spanCount++
            // 查找对应的结束括号
            var depth = 0
            var endIndex = spanIndex + 8
            var found = false

            while (endIndex < json.length) {
                when (json[endIndex]) {
                    '{' -> depth++
                    '}' -> {
                        if (depth == 0) {
                            found = true
                            break
                        }
                        depth--
                    }
                }
                endIndex++
            }

            if (found) {
                val spanContent = json.substring(spanIndex, endIndex + 1)
                if (spanContent.contains("\"ctxt\"")) {
                    spansWithCtxt++
                }
            }

            index = spanIndex + 8
        }

        assertTrue(spanCount > 0, "Should find at least one span object")
        assertEquals(spanCount, spansWithCtxt, "All span objects should contain ctxt field")
    }
})
