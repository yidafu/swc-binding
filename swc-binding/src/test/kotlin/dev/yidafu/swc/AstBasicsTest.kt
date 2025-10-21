package dev.yidafu.swc

import dev.yidafu.swc.dsl.*
import dev.yidafu.swc.types.*
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class AstBasicsTest {

    @Test
    fun `create Span with span DSL`() {
        val s = span()

        assertNotNull(s)
        assertIs<Span>(s)
    }

    @Test
    fun `create Span with emptySpan DSL`() {
        val s = emptySpan()

        assertNotNull(s)
        assertEquals(0, s.start)
        assertEquals(0, s.end)
        assertEquals(0, s.ctxt)
    }

    @Test
    fun `create Span with apply`() {
        val s = Span().apply {
            start = 10
            end = 20
            ctxt = 0
        }

        assertEquals(10, s.start)
        assertEquals(20, s.end)
        assertEquals(0, s.ctxt)
    }

    @Test
    fun `serialize Span`() {
        val s = Span().apply {
            start = 5
            end = 15
            ctxt = 2
        }

        val json = astJson.encodeToString(s)
        assertNotNull(json)
    }

    @Test
    fun `deserialize Span`() {
        val json = """{"start":10,"end":20,"ctxt":5}"""
        val s = astJson.decodeFromString<Span>(json)

        assertEquals(10, s.start)
        assertEquals(20, s.end)
        assertEquals(5, s.ctxt)
    }

    @Test
    fun `create IdentifierImpl`() {
        val id = IdentifierImpl().apply {
            value = "testVar"
            optional = false
            span = emptySpan()
        }

        assertEquals("testVar", id.value)
        assertEquals(false, id.optional)
    }

    @Test
    fun `serialize Identifier`() {
        val id = IdentifierImpl().apply {
            value = "myVar"
            optional = false
            span = emptySpan()
        }

        val json = astJson.encodeToString<Identifier>(id)
        assertNotNull(json)
    }

    @Test
    fun `deserialize Identifier`() {
        val json = """{"type":"Identifier","value":"test","optional":false,"span":{"start":0,"end":0,"ctxt":0}}"""
        val id = astJson.decodeFromString<Identifier>(json)

        assertEquals("test", id.value)
        assertEquals(false, id.optional)
    }

    @Test
    fun `create StringLiteralImpl`() {
        val lit = StringLiteralImpl().apply {
            value = "hello"
            raw = "\"hello\""
            span = emptySpan()
        }

        assertEquals("hello", lit.value)
        assertEquals("\"hello\"", lit.raw)
    }

    @Test
    fun `create NumericLiteralImpl`() {
        val lit = NumericLiteralImpl().apply {
            value = 42.0
            raw = "42"
            span = emptySpan()
        }

        assertEquals(42.0, lit.value)
        assertEquals("42", lit.raw)
    }

    @Test
    fun `create BooleanLiteralImpl`() {
        val lit = BooleanLiteralImpl().apply {
            value = true
            span = emptySpan()
        }

        assertEquals(true, lit.value)
    }

    @Test
    fun `create NullLiteralImpl`() {
        val lit = NullLiteralImpl().apply {
            span = emptySpan()
        }

        assertNotNull(lit)
    }

    @Test
    fun `create ModuleImpl with apply`() {
        val mod = ModuleImpl().apply {
            span = emptySpan()
            body = arrayOf()
        }

        assertNotNull(mod)
        assertNotNull(mod.body)
        assertEquals(0, mod.body?.size)
    }

    @Test
    fun `use module DSL`() {
        val mod = module {
            span = emptySpan()
            body = arrayOf()
        }

        assertNotNull(mod)
        assertIs<Module>(mod)
    }

    @Test
    fun `serialize Module`() {
        val mod = ModuleImpl().apply {
            span = emptySpan()
            body = arrayOf()
        }

        val json = astJson.encodeToString<Module>(mod)
        assertNotNull(json)
    }

    @Test
    fun `create VariableDeclaratorImpl`() {
        val decl = VariableDeclaratorImpl().apply {
            span = emptySpan()
            id = IdentifierImpl().apply {
                value = "x"
                span = emptySpan()
            }
            init = NumericLiteralImpl().apply {
                value = 1.0
                raw = "1"
                span = emptySpan()
            }
        }

        assertNotNull(decl)
        assertIs<Identifier>(decl.id)
    }

    @Test
    fun `Span with same values`() {
        val s1 = Span().apply { start = 1; end = 2; ctxt = 0 }
        val s2 = Span().apply { start = 1; end = 2; ctxt = 0 }

        // Span may not implement equals, just verify they can be created
        assertNotNull(s1)
        assertNotNull(s2)
        assertEquals(s1.start, s2.start)
        assertEquals(s1.end, s2.end)
    }
}
