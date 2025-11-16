package dev.yidafu.swc

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test

/**
 * Comprehensive tests for Union types (U2, U3, U4, U5)
 * This supplements UnionSerializerTest.kt with additional edge cases and error handling
 */
class UnionTest : AnnotationSpec() {
    private val format = Json
    private val serializerU2 = Union.U2.serializerFor(String.serializer(), Double.serializer())
    private val serializerU3 = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
    private val serializerU4 = Union.U4.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer(), Int.serializer())
    private val serializerU5 = Union.U5.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer(), Int.serializer(), Long.serializer())

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

        assertFailsWith<SerializationException> {
            format.encodeToString(serializerU2, union)
        }
    }

    @Test
    fun `U2 serialize with A value`() {
        val union = Union.U2.ofA<String, Double>("hello")
        val json = format.encodeToString(serializerU2, union)

        assertEquals("\"hello\"", json)
    }

    @Test
    fun `U2 serialize with B value`() {
        val union = Union.U2.ofB<String, Double>(3.14)
        val json = format.encodeToString(serializerU2, union)

        assertEquals("3.14", json)
    }

    @Test
    fun `U2 deserialize string value`() {
        val decoded = format.decodeFromString(serializerU2, "\"world\"")

        assertTrue(decoded.isA())
        assertEquals("world", decoded.valueOfA())
    }

    @Test
    fun `U2 deserialize number value`() {
        val decoded = format.decodeFromString(serializerU2, "99.9")

        assertTrue(decoded.isB())
        assertEquals(99.9, decoded.valueOfB())
    }

    @Test
    fun `U2 deserialize invalid value throws`() {
        assertFailsWith<SerializationException> {
            format.decodeFromString(serializerU2, "null")
        }
    }

    // ==================== U3 tests ====================

    @Test
    fun `U3 ofA creates union with A value`() {
        val union = Union.U3.ofA<String, Double, Boolean>("test")

        assertTrue(union.isA())
        assertFalse(union.isB())
        assertFalse(union.isC())
        assertEquals("test", union.valueOfA())
    }

    @Test
    fun `U3 ofB creates union with B value`() {
        val union = Union.U3.ofB<String, Double, Boolean>(42.0)

        assertFalse(union.isA())
        assertTrue(union.isB())
        assertFalse(union.isC())
        assertEquals(42.0, union.valueOfB())
    }

    @Test
    fun `U3 ofC creates union with C value`() {
        val union = Union.U3.ofC<String, Double, Boolean>(true)

        assertFalse(union.isA())
        assertFalse(union.isB())
        assertTrue(union.isC())
        assertEquals(true, union.valueOfC())
    }

    @Test
    fun `U3 constructor with all null throws on serialize`() {
        val union = Union.U3<String, Double, Boolean>(a = null, b = null, c = null)

        assertFailsWith<SerializationException> {
            format.encodeToString(serializerU3, union)
        }
    }

    @Test
    fun `U3 serialize with A value`() {
        val union = Union.U3.ofA<String, Double, Boolean>("test")
        val json = format.encodeToString(serializerU3, union)

        assertEquals("\"test\"", json)
    }

    @Test
    fun `U3 serialize with B value`() {
        val union = Union.U3.ofB<String, Double, Boolean>(2.5)
        val json = format.encodeToString(serializerU3, union)

        assertEquals("2.5", json)
    }

    @Test
    fun `U3 serialize with C value`() {
        val union = Union.U3.ofC<String, Double, Boolean>(false)
        val json = format.encodeToString(serializerU3, union)

        assertEquals("false", json)
    }

    @Test
    fun `U3 deserialize boolean value`() {
        val decoded = format.decodeFromString(serializerU3, "true")

        assertTrue(decoded.isC())
        assertEquals(true, decoded.valueOfC())
    }

    @Test
    fun `U3 deserialize invalid value throws`() {
        assertFailsWith<SerializationException> {
            format.decodeFromString(serializerU3, "[]")
        }
    }

    // ==================== U4 tests ====================

    @Test
    fun `U4 ofA creates union with A value`() {
        val union = Union.U4.ofA<String, Double, Boolean, Int>("test")

        assertTrue(union.isA())
        assertFalse(union.isB())
        assertFalse(union.isC())
        assertFalse(union.isD())
        assertEquals("test", union.valueOfA())
    }

    @Test
    fun `U4 ofD creates union with D value`() {
        val union = Union.U4.ofD<String, Double, Boolean, Int>(42)

        assertFalse(union.isA())
        assertFalse(union.isB())
        assertFalse(union.isC())
        assertTrue(union.isD())
        assertEquals(42, union.valueOfD())
    }

    @Test
    fun `U4 constructor with all null throws on serialize`() {
        val union = Union.U4<String, Double, Boolean, Int>(a = null, b = null, c = null, d = null)

        assertFailsWith<SerializationException> {
            format.encodeToString(serializerU4, union)
        }
    }

    @Test
    fun `U4 serialize with D value`() {
        val union = Union.U4.ofD<String, Double, Boolean, Int>(100)
        val json = format.encodeToString(serializerU4, union)

        assertEquals("100", json)
    }

    @Test
    fun `U4 deserialize int value`() {
        val decoded = format.decodeFromString(serializerU4, "200")

        assertTrue(decoded.isD())
        assertEquals(200, decoded.valueOfD())
    }

    // ==================== U5 tests ====================

    @Test
    fun `U5 ofA creates union with A value`() {
        val union = Union.U5.ofA<String, Double, Boolean, Int, Long>("test")

        assertTrue(union.isA())
        assertFalse(union.isB())
        assertFalse(union.isC())
        assertFalse(union.isD())
        assertFalse(union.isE())
        assertEquals("test", union.valueOfA())
    }

    @Test
    fun `U5 ofE creates union with E value`() {
        val union = Union.U5.ofE<String, Double, Boolean, Int, Long>(123456789L)

        assertFalse(union.isA())
        assertFalse(union.isB())
        assertFalse(union.isC())
        assertFalse(union.isD())
        assertTrue(union.isE())
        assertEquals(123456789L, union.valueOfE())
    }

    @Test
    fun `U5 constructor with all null throws on serialize`() {
        val union = Union.U5<String, Double, Boolean, Int, Long>(a = null, b = null, c = null, d = null, e = null)

        assertFailsWith<SerializationException> {
            format.encodeToString(serializerU5, union)
        }
    }

    @Test
    fun `U5 serialize with E value`() {
        val union = Union.U5.ofE<String, Double, Boolean, Int, Long>(999999999L)
        val json = format.encodeToString(serializerU5, union)

        assertEquals("999999999", json)
    }

    @Test
    fun `U5 deserialize long value`() {
        val decoded = format.decodeFromString(serializerU5, "987654321")

        assertTrue(decoded.isE())
        assertEquals(987654321L, decoded.valueOfE())
    }

    // ==================== Edge cases ====================

    @Test
    fun `U2 with multiple non-null values uses first`() {
        val union = Union.U2<String, Double>(a = "first", b = 42.0)

        // Should serialize the first non-null value (a)
        val json = format.encodeToString(serializerU2, union)
        assertEquals("\"first\"", json)
    }

    @Test
    fun `U3 with multiple non-null values uses first`() {
        val union = Union.U3<String, Double, Boolean>(a = "first", b = 42.0, c = true)

        // Should serialize the first non-null value (a)
        val json = format.encodeToString(serializerU3, union)
        assertEquals("\"first\"", json)
    }

    @Test
    fun `U4 with multiple non-null values uses first`() {
        val union = Union.U4<String, Double, Boolean, Int>(a = "first", b = 42.0, c = true, d = 100)

        // Should serialize the first non-null value (a)
        val json = format.encodeToString(serializerU4, union)
        assertEquals("\"first\"", json)
    }

    @Test
    fun `U5 with multiple non-null values uses first`() {
        val union = Union.U5<String, Double, Boolean, Int, Long>(a = "first", b = 42.0, c = true, d = 100, e = 999L)

        // Should serialize the first non-null value (a)
        val json = format.encodeToString(serializerU5, union)
        assertEquals("\"first\"", json)
    }

    @Test
    fun `U2 deserialize empty string`() {
        val decoded = format.decodeFromString(serializerU2, "\"\"")

        assertTrue(decoded.isA())
        assertEquals("", decoded.valueOfA())
    }

    @Test
    fun `U2 deserialize zero`() {
        val decoded = format.decodeFromString(serializerU2, "0")

        assertTrue(decoded.isB())
        assertEquals(0.0, decoded.valueOfB())
    }

    @Test
    fun `U3 deserialize false boolean`() {
        val decoded = format.decodeFromString(serializerU3, "false")

        assertTrue(decoded.isC())
        assertEquals(false, decoded.valueOfC())
    }

    @Test
    fun `U4 deserialize zero int`() {
        val decoded = format.decodeFromString(serializerU4, "0")

        // Could be B (Double) or D (Int), but should succeed
        assertNotNull(decoded)
    }

    @Test
    fun `U5 deserialize zero long`() {
        val decoded = format.decodeFromString(serializerU5, "0")

        // Could be B, D, or E, but should succeed
        assertNotNull(decoded)
    }

    // ==================== Type compatibility tests ====================

    @Test
    fun `U2 can handle numeric string`() {
        val decoded = format.decodeFromString(serializerU2, "\"123\"")

        assertTrue(decoded.isA())
        assertEquals("123", decoded.valueOfA())
    }

    @Test
    fun `U2 can handle string that looks like number`() {
        val decoded = format.decodeFromString(serializerU2, "\"3.14\"")

        assertTrue(decoded.isA())
        assertEquals("3.14", decoded.valueOfA())
    }

    @Test
    fun `U3 boolean true deserializes correctly`() {
        val decoded = format.decodeFromString(serializerU3, "true")

        assertTrue(decoded.isC())
        assertEquals(true, decoded.valueOfC())
    }

    @Test
    fun `U3 boolean false deserializes correctly`() {
        val decoded = format.decodeFromString(serializerU3, "false")

        assertTrue(decoded.isC())
        assertEquals(false, decoded.valueOfC())
    }
}

