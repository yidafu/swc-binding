package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
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
 * Tests for generated Union serializers
 * * These tests verify that Union types serialize to bare values (not wrapped objects)
 * and can deserialize from bare values correctly.
 */
class UnionSerializerTest : AnnotationSpec() {
    private val format = Json

    // ==================== U2_Boolean_String__Serializer tests ====================

    @Test
    fun `U2 Boolean String - serialize Boolean valueOfA to bare true`() {
        val union = Union.U2.ofA<Boolean, String>(true)
        val json = format.encodeToString(U2_Boolean_String__Serializer, union)

        assertEquals("true", json)
    }

    @Test
    fun `U2 Boolean String - serialize String valueOfB to bare string`() {
        val union = Union.U2.ofB<Boolean, String>("Str")
        val json = format.encodeToString(U2_Boolean_String__Serializer, union)

        assertEquals("\"Str\"", json)
    }

    @Test
    fun `U2 Boolean String - deserialize true to valueOfA`() {
        val decoded = format.decodeFromString(U2_Boolean_String__Serializer, "true")

        assertTrue(decoded.isA())
        assertFalse(decoded.isB())
        assertEquals(true, decoded.valueOfA())
        assertNull(decoded.valueOfB())
    }

    @Test
    fun `U2 Boolean String - deserialize string to valueOfB`() {
        val decoded = format.decodeFromString(U2_Boolean_String__Serializer, "\"Str\"")

        assertFalse(decoded.isA())
        assertTrue(decoded.isB())
        assertNull(decoded.valueOfA())
        assertEquals("Str", decoded.valueOfB())
    }

    @Test
    fun `U2 Boolean String - deserialize false to valueOfA`() {
        val decoded = format.decodeFromString(U2_Boolean_String__Serializer, "false")

        assertTrue(decoded.isA())
        assertEquals(false, decoded.valueOfA())
    }

    @Test
    fun `U2 Boolean String - deserialize empty string to valueOfB`() {
        val decoded = format.decodeFromString(U2_Boolean_String__Serializer, "\"\"")

        assertTrue(decoded.isB())
        assertEquals("", decoded.valueOfB())
    }

    // ==================== U3_Boolean_MatchPattern_ArrayMatchPattern__Serializer tests ====================

    @Test
    fun `U3 Boolean MatchPattern ArrayMatchPattern - serialize Boolean valueOfA to bare true`() {
        val union = Union.U3.ofA<Boolean, MatchPattern, Array<MatchPattern>>(true)
        val json = format.encodeToString(U3_Boolean_MatchPattern_ArrayMatchPattern__Serializer, union)

        assertEquals("true", json)
    }

    @Test
    fun `U3 Boolean MatchPattern ArrayMatchPattern - deserialize true to valueOfA`() {
        val decoded = format.decodeFromString(U3_Boolean_MatchPattern_ArrayMatchPattern__Serializer, "true")

        assertTrue(decoded.isA())
        assertFalse(decoded.isB())
        assertFalse(decoded.isC())
        assertEquals(true, decoded.valueOfA())
        assertNull(decoded.valueOfB())
        assertNull(decoded.valueOfC())
    }

    @Test
    fun `U3 Boolean MatchPattern ArrayMatchPattern - deserialize false to valueOfA`() {
        val decoded = format.decodeFromString(U3_Boolean_MatchPattern_ArrayMatchPattern__Serializer, "false")

        assertTrue(decoded.isA())
        assertEquals(false, decoded.valueOfA())
    }

    // ==================== U2_String_Boolean__Serializer tests ====================

    @Test
    fun `U2 String Boolean - serialize String valueOfA to bare string`() {
        val union = Union.U2.ofA<String, Boolean>("test")
        val json = format.encodeToString(U2_String_Boolean__Serializer, union)

        assertEquals("\"test\"", json)
    }

    @Test
    fun `U2 String Boolean - serialize Boolean valueOfB to bare true`() {
        val union = Union.U2.ofB<String, Boolean>(true)
        val json = format.encodeToString(U2_String_Boolean__Serializer, union)

        assertEquals("true", json)
    }

    @Test
    fun `U2 String Boolean - deserialize string to valueOfA`() {
        val decoded = format.decodeFromString(U2_String_Boolean__Serializer, "\"test\"")

        assertTrue(decoded.isA())
        assertEquals("test", decoded.valueOfA())
    }

    @Test
    fun `U2 String Boolean - deserialize true to valueOfB`() {
        val decoded = format.decodeFromString(U2_String_Boolean__Serializer, "true")

        assertTrue(decoded.isB())
        assertEquals(true, decoded.valueOfB())
    }

    // ==================== U2_Double_Boolean__Serializer tests ====================

    @Test
    fun `U2 Double Boolean - serialize Double valueOfA to bare number`() {
        val union = Union.U2.ofA<Double, Boolean>(3.14)
        val json = format.encodeToString(U2_Double_Boolean__Serializer, union)

        assertEquals("3.14", json)
    }

    @Test
    fun `U2 Double Boolean - serialize Boolean valueOfB to bare true`() {
        val union = Union.U2.ofB<Double, Boolean>(true)
        val json = format.encodeToString(U2_Double_Boolean__Serializer, union)

        assertEquals("true", json)
    }

    @Test
    fun `U2 Double Boolean - deserialize number to valueOfA`() {
        val decoded = format.decodeFromString(U2_Double_Boolean__Serializer, "3.14")

        assertTrue(decoded.isA())
        assertEquals(3.14, decoded.valueOfA())
    }

    @Test
    fun `U2 Double Boolean - deserialize true to valueOfB`() {
        val decoded = format.decodeFromString(U2_Double_Boolean__Serializer, "true")

        assertTrue(decoded.isB())
        assertEquals(true, decoded.valueOfB())
    }

    // ==================== Edge cases ====================

    @Test
    fun `U2 Boolean String - constructor with both null throws on serialize`() {
        val union = Union.U2<Boolean, String>(a = null, b = null)

        assertFailsWith<SerializationException> {
            format.encodeToString(U2_Boolean_String__Serializer, union)
        }
    }

    @Test
    fun `U2 Boolean String - with multiple non-null values uses first`() {
        val union = Union.U2<Boolean, String>(a = true, b = "second")

        // Should serialize the first non-null value (a)
        val json = format.encodeToString(U2_Boolean_String__Serializer, union)
        assertEquals("true", json)
    }

    @Test
    fun `U3 Boolean MatchPattern ArrayMatchPattern - with multiple non-null values uses first`() {
        val union = Union.U3<Boolean, MatchPattern, Array<MatchPattern>>(a = true, b = MatchPattern(), c = arrayOf())

        // Should serialize the first non-null value (a)
        val json = format.encodeToString(U3_Boolean_MatchPattern_ArrayMatchPattern__Serializer, union)
        assertEquals("true", json)
    }

    // ==================== U3_Boolean_String_JsFormatOptionsComments__Serializer tests ====================

    @Test
    fun `U3 Boolean String JsFormatOptionsComments - serialize Boolean valueOfA to bare true`() {
        val union = Union.U3.ofA<Boolean, String, JsFormatOptions.JsFormatOptionsComments>(true)
        val json = format.encodeToString(U3_Boolean_String_JsFormatOptionsComments__Serializer, union)

        assertEquals("true", json)
    }

    @Test
    fun `U3 Boolean String JsFormatOptionsComments - serialize String valueOfB to bare string`() {
        val union = Union.U3.ofB<Boolean, String, JsFormatOptions.JsFormatOptionsComments>("Str")
        val json = format.encodeToString(U3_Boolean_String_JsFormatOptionsComments__Serializer, union)

        assertEquals("\"Str\"", json)
    }

    @Test
    fun `U3 Boolean String JsFormatOptionsComments - deserialize true to valueOfA`() {
        val decoded = format.decodeFromString(U3_Boolean_String_JsFormatOptionsComments__Serializer, "true")

        assertTrue(decoded.isA())
        assertFalse(decoded.isB())
        assertFalse(decoded.isC())
        assertEquals(true, decoded.valueOfA())
        assertNull(decoded.valueOfB())
        assertNull(decoded.valueOfC())
    }

    @Test
    fun `U3 Boolean String JsFormatOptionsComments - deserialize string to valueOfB`() {
        val decoded = format.decodeFromString(U3_Boolean_String_JsFormatOptionsComments__Serializer, "\"Str\"")

        assertFalse(decoded.isA())
        assertTrue(decoded.isB())
        assertFalse(decoded.isC())
        assertNull(decoded.valueOfA())
        assertEquals("Str", decoded.valueOfB())
        assertNull(decoded.valueOfC())
    }

    @Test
    fun `U3 Boolean String JsFormatOptionsComments - deserialize false to valueOfA`() {
        val decoded = format.decodeFromString(U3_Boolean_String_JsFormatOptionsComments__Serializer, "false")

        assertTrue(decoded.isA())
        assertEquals(false, decoded.valueOfA())
    }

    @Test
    fun `U3 Boolean String JsFormatOptionsComments - deserialize empty string to valueOfB`() {
        val decoded = format.decodeFromString(U3_Boolean_String_JsFormatOptionsComments__Serializer, "\"\"")

        assertTrue(decoded.isB())
        assertEquals("", decoded.valueOfB())
    }

    @Test
    fun `U3 Boolean String JsFormatOptionsComments - with multiple non-null values uses first`() {
        val union = Union.U3<Boolean, String, JsFormatOptions.JsFormatOptionsComments>(a = true, b = "second", c = null)

        // Should serialize the first non-null value (a)
        val json = format.encodeToString(U3_Boolean_String_JsFormatOptionsComments__Serializer, union)
        assertEquals("true", json)
    }

    // ==================== Type compatibility tests ====================

    @Test
    fun `U2 Boolean String - can handle numeric string`() {
        val decoded = format.decodeFromString(U2_Boolean_String__Serializer, "\"123\"")

        assertTrue(decoded.isB())
        assertEquals("123", decoded.valueOfB())
    }

    @Test
    fun `U2 Boolean String - can handle string that looks like number`() {
        val decoded = format.decodeFromString(U2_Boolean_String__Serializer, "\"3.14\"")

        assertTrue(decoded.isB())
        assertEquals("3.14", decoded.valueOfB())
    }

    @Test
    fun `U2 Boolean String - deserialize invalid value throws`() {
        assertFailsWith<SerializationException> {
            format.decodeFromString(U2_Boolean_String__Serializer, "null")
        }
    }

    @Test
    fun `U2 Boolean String - deserialize array throws`() {
        assertFailsWith<SerializationException> {
            format.decodeFromString(U2_Boolean_String__Serializer, "[]")
        }
    }

    // ==================== Generic serializerFor tests ====================

    @Test
    fun `U2 String Double - encode string variant without wrapper using serializerFor`() {
        val serializerU2 = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val union = Union.U2.ofA<String, Double>("hello")
        val json = format.encodeToString(serializerU2, union)
        assertEquals("\"hello\"", json)
    }

    @Test
    fun `U2 String Double - encode number variant without wrapper using serializerFor`() {
        val serializerU2 = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val union = Union.U2.ofB<String, Double>(42.0)
        val json = format.encodeToString(serializerU2, union)
        assertEquals("42.0", json)
    }

    @Test
    fun `U2 String Double - decode string variant using serializerFor`() {
        val serializerU2 = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val decoded = format.decodeFromString(serializerU2, "\"hello\"")
        assertTrue(decoded.isA())
        assertEquals("hello", decoded.valueOfA())
    }

    @Test
    fun `U2 String Double - decode number variant using serializerFor`() {
        val serializerU2 = Union.U2.serializerFor(String.serializer(), Double.serializer())
        val decoded = format.decodeFromString(serializerU2, "42.0")
        assertTrue(decoded.isB())
        assertEquals(42.0, decoded.valueOfB())
    }

    @Test
    fun `U3 String Double Boolean - encode decode third variant using serializerFor`() {
        val serializerU3 = Union.U3.serializerFor(
            String.serializer(),
            Double.serializer(),
            Boolean.serializer()
        )
        val union = Union.U3.ofC<String, Double, Boolean>(true)
        val json = format.encodeToString(serializerU3, union)
        assertEquals("true", json)

        val decoded = format.decodeFromString(serializerU3, "\"word\"")
        assertTrue(decoded.isA())
        assertEquals("word", decoded.valueOfA())
    }
}
