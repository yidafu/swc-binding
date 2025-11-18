package dev.yidafu.swc

import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Tests for Union types (U2, U3)
 * * These tests verify the basic functionality of Union types including:
 * - Factory methods (ofA, ofB, ofC)
 * - Type checking methods (isA, isB, isC)
 * - Value access methods (valueOfA, valueOfB, valueOfC)
 * - Serialization/deserialization using serializerFor
 */
class UnionTest : AnnotationSpec() {
    private val format = Json

    // ==================== U2 tests ====================

    @Test
    fun `U2 ofA creates union with A value`() {
        val union = Union.U2.ofA<String, Double>("test")

        assertTrue(union.isA())
        assertFalse(union.isB())
        assertEquals("test", union.valueOfA())
        assertNull(union.valueOfB())
    }

    @Test
    fun `U2 ofB creates union with B value`() {
        val union = Union.U2.ofB<String, Double>(42.0)

        assertFalse(union.isA())
        assertTrue(union.isB())
        assertNull(union.valueOfA())
        assertEquals(42.0, union.valueOfB())
    }

    @Test
    fun `U2 constructor with both null throws on serialize`() {
        val union = Union.U2<String, Double>(a = null, b = null)
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())

        assertFailsWith<SerializationException> {
            format.encodeToString(serializer, union)
        }
    }

    @Test
    fun `U2 serialize with A value`() {
        val union = Union.U2.ofA<String, Double>("hello")
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val json = format.encodeToString(serializer, union)

        assertEquals("\"hello\"", json)
    }

    @Test
    fun `U2 serialize with B value`() {
        val union = Union.U2.ofB<String, Double>(3.14)
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val json = format.encodeToString(serializer, union)

        assertEquals("3.14", json)
    }

    @Test
    fun `U2 deserialize string value`() {
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val decoded = format.decodeFromString(serializer, "\"world\"")

        assertTrue(decoded.isA())
        assertEquals("world", decoded.valueOfA())
    }

    @Test
    fun `U2 deserialize number value`() {
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val decoded = format.decodeFromString(serializer, "99.9")

        assertTrue(decoded.isB())
        assertEquals(99.9, decoded.valueOfB())
    }

    @Test
    fun `U2 deserialize invalid value throws`() {
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())

        assertFailsWith<SerializationException> {
            format.decodeFromString(serializer, "null")
        }
    }

    @Test
    fun `U2 with multiple non-null values uses first`() {
        val union = Union.U2<String, Double>(a = "first", b = 42.0)
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())

        // Should serialize the first non-null value (a)
        val json = format.encodeToString(serializer, union)
        assertEquals("\"first\"", json)
    }

    @Test
    fun `U2 deserialize empty string`() {
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val decoded = format.decodeFromString(serializer, "\"\"")

        assertTrue(decoded.isA())
        assertEquals("", decoded.valueOfA())
    }

    @Test
    fun `U2 deserialize zero`() {
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val decoded = format.decodeFromString(serializer, "0")

        assertTrue(decoded.isB())
        assertEquals(0.0, decoded.valueOfB())
    }

    // ==================== U3 tests ====================

    @Test
    fun `U3 ofA creates union with A value`() {
        val union = Union.U3.ofA<String, Double, Boolean>("test")

        assertTrue(union.isA())
        assertFalse(union.isB())
        assertFalse(union.isC())
        assertEquals("test", union.valueOfA())
        assertNull(union.valueOfB())
        assertNull(union.valueOfC())
    }

    @Test
    fun `U3 ofB creates union with B value`() {
        val union = Union.U3.ofB<String, Double, Boolean>(42.0)

        assertFalse(union.isA())
        assertTrue(union.isB())
        assertFalse(union.isC())
        assertNull(union.valueOfA())
        assertEquals(42.0, union.valueOfB())
        assertNull(union.valueOfC())
    }

    @Test
    fun `U3 ofC creates union with C value`() {
        val union = Union.U3.ofC<String, Double, Boolean>(true)

        assertFalse(union.isA())
        assertFalse(union.isB())
        assertTrue(union.isC())
        assertNull(union.valueOfA())
        assertNull(union.valueOfB())
        assertEquals(true, union.valueOfC())
    }

    @Test
    fun `U3 constructor with all null throws on serialize`() {
        val union = Union.U3<String, Double, Boolean>(a = null, b = null, c = null)
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())

        assertFailsWith<SerializationException> {
            format.encodeToString(serializer, union)
        }
    }

    @Test
    fun `U3 serialize with A value`() {
        val union = Union.U3.ofA<String, Double, Boolean>("test")
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
        val json = format.encodeToString(serializer, union)

        assertEquals("\"test\"", json)
    }

    @Test
    fun `U3 serialize with B value`() {
        val union = Union.U3.ofB<String, Double, Boolean>(2.5)
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
        val json = format.encodeToString(serializer, union)

        assertEquals("2.5", json)
    }

    @Test
    fun `U3 serialize with C value`() {
        val union = Union.U3.ofC<String, Double, Boolean>(false)
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
        val json = format.encodeToString(serializer, union)

        assertEquals("false", json)
    }

    @Test
    fun `U3 deserialize string value`() {
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
        val decoded = format.decodeFromString(serializer, "\"word\"")

        assertTrue(decoded.isA())
        assertEquals("word", decoded.valueOfA())
    }

    @Test
    fun `U3 deserialize number value`() {
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
        val decoded = format.decodeFromString(serializer, "3.14")

        assertTrue(decoded.isB())
        assertEquals(3.14, decoded.valueOfB())
    }

    @Test
    fun `U3 deserialize boolean value`() {
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
        val decoded = format.decodeFromString(serializer, "true")

        assertTrue(decoded.isC())
        assertEquals(true, decoded.valueOfC())
    }

    @Test
    fun `U3 deserialize invalid value throws`() {
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())

        assertFailsWith<SerializationException> {
            format.decodeFromString(serializer, "[]")
        }
    }

    @Test
    fun `U3 with multiple non-null values uses first`() {
        val union = Union.U3<String, Double, Boolean>(a = "first", b = 42.0, c = true)
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())

        // Should serialize the first non-null value (a)
        val json = format.encodeToString(serializer, union)
        assertEquals("\"first\"", json)
    }

    @Test
    fun `U3 deserialize false boolean`() {
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
        val decoded = format.decodeFromString(serializer, "false")

        assertTrue(decoded.isC())
        assertEquals(false, decoded.valueOfC())
    }

    // ==================== Type compatibility tests ====================

    @Test
    fun `U2 can handle numeric string`() {
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val decoded = format.decodeFromString(serializer, "\"123\"")

        assertTrue(decoded.isA())
        assertEquals("123", decoded.valueOfA())
    }

    @Test
    fun `U2 can handle string that looks like number`() {
        val serializer = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val decoded = format.decodeFromString(serializer, "\"3.14\"")

        assertTrue(decoded.isA())
        assertEquals("3.14", decoded.valueOfA())
    }

    @Test
    fun `U3 boolean true deserializes correctly`() {
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
        val decoded = format.decodeFromString(serializer, "true")

        assertTrue(decoded.isC())
        assertEquals(true, decoded.valueOfC())
    }

    @Test
    fun `U3 boolean false deserializes correctly`() {
        val serializer = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
        val decoded = format.decodeFromString(serializer, "false")

        assertTrue(decoded.isC())
        assertEquals(false, decoded.valueOfC())
    }
}
