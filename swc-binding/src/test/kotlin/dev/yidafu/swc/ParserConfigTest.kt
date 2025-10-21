package dev.yidafu.swc

import dev.yidafu.swc.dsl.*
import dev.yidafu.swc.types.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ParserConfigTest {

    @Test
    fun `create EsParserConfig with apply`() {
        val config = EsParserConfig().apply {
            jsx = true
            target = "es2020"
        }

        assertEquals(true, config.jsx)
        assertEquals("es2020", config.target)
    }

    @Test
    fun `create EsParserConfig with comments`() {
        val config = EsParserConfig().apply {
            comments = true
        }

        assertEquals(true, config.comments)
    }

    @Test
    fun `create EsParserConfig with decorators`() {
        val config = EsParserConfig().apply {
            decorators = true
        }

        assertEquals(true, config.decorators)
    }

    @Test
    fun `create EsParserConfig with dynamicImport`() {
        val config = EsParserConfig().apply {
            dynamicImport = true
        }

        assertEquals(true, config.dynamicImport)
    }

    @Test
    fun `create TsParserConfig with apply`() {
        val config = TsParserConfig().apply {
            tsx = true
            target = "es2015"
        }

        assertEquals(true, config.tsx)
        assertEquals("es2015", config.target)
    }

    @Test
    fun `create TsParserConfig with decorators`() {
        val config = TsParserConfig().apply {
            decorators = true
        }

        assertEquals(true, config.decorators)
    }

    @Test
    fun `create TsParserConfig with dynamicImport`() {
        val config = TsParserConfig().apply {
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
        val config = EsParserConfig().apply {
            jsx = true
            target = "es2020"
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
        assertEquals("es2020", config.target)
    }

    @Test
    fun `serialize and deserialize TsParserConfig`() {
        val config = TsParserConfig().apply {
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
        val config = EsParserConfig().apply {
            jsx = true
            target = "es2020"
            comments = true
            decorators = true
            dynamicImport = true
        }

        assertEquals(true, config.jsx)
        assertEquals("es2020", config.target)
        assertEquals(true, config.comments)
        assertEquals(true, config.decorators)
        assertEquals(true, config.dynamicImport)
    }

    @Test
    fun `TsParserConfig with multiple options`() {
        val config = TsParserConfig().apply {
            tsx = true
            target = "es2015"
            decorators = false
            dynamicImport = false
        }

        assertEquals(true, config.tsx)
        assertEquals("es2015", config.target)
        assertEquals(false, config.decorators)
        assertEquals(false, config.dynamicImport)
    }

    @Test
    fun `create default EsParserConfig`() {
        val config = EsParserConfig()

        assertNotNull(config)
    }

    @Test
    fun `create default TsParserConfig`() {
        val config = TsParserConfig()

        assertNotNull(config)
    }
}
