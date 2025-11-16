package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test

class ParserConfigTest : AnnotationSpec() {

    @Test
    fun `create EsParserConfig with apply`() {
        val config = esParseOptions {
            jsx = true
            target = JscTarget.ES2020
        }

        assertEquals(true, config.jsx)
        assertEquals(JscTarget.ES2020, config.target)
    }

    @Test
    fun `create EsParserConfig with comments`() {
        val config = esParseOptions {
            comments = true
        }

        assertEquals(true, config.comments)
    }

    @Test
    fun `create EsParserConfig with decorators`() {
        val config = esParseOptions {
            decorators = true
        }

        assertEquals(true, config.decorators)
    }

    @Test
    fun `create EsParserConfig with dynamicImport`() {
        val config = esParseOptions {
            dynamicImport = true
        }

        assertEquals(true, config.dynamicImport)
    }

    @Test
    fun `create TsParserConfig with apply`() {
        val config = tsParseOptions {
            tsx = true
            target = JscTarget.ES2015
        }

        assertEquals(true, config.tsx)
        assertEquals(JscTarget.ES2015, config.target)
    }

    @Test
    fun `create TsParserConfig with decorators`() {
        val config = tsParseOptions {
            decorators = true
        }

        assertEquals(true, config.decorators)
    }

    @Test
    fun `create TsParserConfig with dynamicImport`() {
        val config = tsParseOptions {
            dynamicImport = true
        }

        assertEquals(true, config.dynamicImport)
    }

    @Test
    fun `use esParseOptions DSL`() {
        val config = esParseOptions {
            jsx = true
        }

        assertNotNull(config)
        assertEquals(true, config.jsx)
    }

    @Test
    fun `use tsParseOptions DSL`() {
        val config = tsParseOptions {
            tsx = true
        }

        assertNotNull(config)
        assertEquals(true, config.tsx)
    }

    @Test
    fun `serialize EsParserConfig`() {
        val config = esParseOptions {
            jsx = true
            target = JscTarget.ES2020
            comments = false
        }

        val json = Json.encodeToString<EsParserConfig>(config)
        assertNotNull(json)
    }

    @Test
    fun `deserialize EsParserConfig`() {
        val json = """{"jsx":true,"target":"es2020"}"""
        val config = Json.decodeFromString<EsParserConfig>(json)

        assertEquals(true, config.jsx)
        assertEquals(JscTarget.ES2020, config.target)
    }

    @Test
    fun `serialize and deserialize TsParserConfig`() {
        val config = tsParseOptions {
            tsx = true
            decorators = true
        }

        val json = Json.encodeToString<TsParserConfig>(config)
        val decoded = Json.decodeFromString<TsParserConfig>(json)

        assertEquals(true, decoded.tsx)
        assertEquals(true, decoded.decorators)
    }

    @Test
    fun `EsParserConfig with multiple options`() {
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

    @Test
    fun `TsParserConfig with multiple options`() {
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

    @Test
    fun `create default EsParserConfig`() {
        val config = esParseOptions { }

        assertNotNull(config)
    }

    @Test
    fun `create default TsParserConfig`() {
        val config = tsParseOptions { }

        assertNotNull(config)
    }
}
