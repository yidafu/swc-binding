package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.core.spec.style.ShouldSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ParserConfigTest : ShouldSpec({

    should("create EsParserConfig with apply") {
        val config = esParseOptions {
            jsx = true
            target = JscTarget.ES2020
        }

        assertEquals(true, config.jsx)
        assertEquals(JscTarget.ES2020, config.target)
    }

    should("create EsParserConfig with comments") {
        val config = esParseOptions {
            comments = true
        }

        assertEquals(true, config.comments)
    }

    should("create EsParserConfig with decorators") {
        val config = esParseOptions {
            decorators = true
        }

        assertEquals(true, config.decorators)
    }

    should("create EsParserConfig with dynamicImport") {
        val config = esParseOptions {
            dynamicImport = true
        }

        assertEquals(true, config.dynamicImport)
    }

    should("create TsParserConfig with apply") {
        val config = tsParseOptions {
            tsx = true
            target = JscTarget.ES2015
        }

        assertEquals(true, config.tsx)
        assertEquals(JscTarget.ES2015, config.target)
    }

    should("create TsParserConfig with decorators") {
        val config = tsParseOptions {
            decorators = true
        }

        assertEquals(true, config.decorators)
    }

    should("create TsParserConfig with dynamicImport") {
        val config = tsParseOptions {
            dynamicImport = true
        }

        assertEquals(true, config.dynamicImport)
    }

    should("use esParseOptions DSL") {
        val config = esParseOptions {
            jsx = true
        }

        assertNotNull(config)
        assertEquals(true, config.jsx)
    }

    should("use tsParseOptions DSL") {
        val config = tsParseOptions {
            tsx = true
        }

        assertNotNull(config)
        assertEquals(true, config.tsx)
    }

    should("serialize EsParserConfig") {
        val config = esParseOptions {
            jsx = true
            target = JscTarget.ES2020
            comments = false
        }

        val json = Json.encodeToString<EsParserConfig>(config)
        assertNotNull(json)
    }

    should("deserialize EsParserConfig") {
        val json = """{"jsx":true,"target":"es2020"}"""
        val config = Json.decodeFromString<EsParserConfig>(json)

        assertEquals(true, config.jsx)
        assertEquals(JscTarget.ES2020, config.target)
    }

    should("serialize and deserialize TsParserConfig") {
        val config = tsParseOptions {
            tsx = true
            decorators = true
        }

        val json = Json.encodeToString<TsParserConfig>(config)
        val decoded = Json.decodeFromString<TsParserConfig>(json)

        assertEquals(true, decoded.tsx)
        assertEquals(true, decoded.decorators)
    }

    should("EsParserConfig with multiple options") {
        val config = esParseOptions {
            jsx = true
            target = JscTarget.ES2020
            comments = true
            decorators = true
            dynamicImport = true
        }

        assertEquals(true, config.jsx)
        assertEquals(JscTarget.ES2020, config.target)
        assertEquals(true, config.comments)
        assertEquals(true, config.decorators)
        assertEquals(true, config.dynamicImport)
    }

    should("TsParserConfig with multiple options") {
        val config = tsParseOptions {
            tsx = true
            target = JscTarget.ES2015
            decorators = false
            dynamicImport = false
        }

        assertEquals(true, config.tsx)
        assertEquals(JscTarget.ES2015, config.target)
        assertEquals(false, config.decorators)
        assertEquals(false, config.dynamicImport)
    }

    should("create default EsParserConfig") {
        val config = esParseOptions { }

        assertNotNull(config)
    }

    should("create default TsParserConfig") {
        val config = tsParseOptions { }

        assertNotNull(config)
    }
})
