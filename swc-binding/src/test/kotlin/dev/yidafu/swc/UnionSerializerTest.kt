package dev.yidafu.swc

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test

class UnionSerializerTest : AnnotationSpec() {
    private val format = Json
    private val serializerU2 = Union.U2.serializerFor(String.serializer(), Double.serializer())
    private val serializerU3 = Union.U3.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer())
    private val serializerU4 = Union.U4.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer(), Int.serializer())
    private val serializerU5 = Union.U5.serializerFor(String.serializer(), Double.serializer(), Boolean.serializer(), Int.serializer(), Long.serializer())

    @Test
    fun `encode string variant without wrapper`() {
        val union = Union.U2.ofA<String, Double>("hello")
        val json = format.encodeToString(serializerU2, union)
        assertEquals("\"hello\"", json)
    }

    @Test
    fun `encode number variant without wrapper`() {
        val union = Union.U2.ofB<String, Double>(42.0)
        val json = format.encodeToString(serializerU2, union)
        assertEquals("42.0", json)
    }

    @Test
    fun `decode string variant`() {
        val decoded = format.decodeFromString(serializerU2, "\"hello\"")
        assertTrue(decoded.isA())
        assertEquals("hello", decoded.valueOfA())
    }

    @Test
    fun `decode number variant`() {
        val decoded = format.decodeFromString(serializerU2, "42.0")
        assertTrue(decoded.isB())
        assertEquals(42.0, decoded.valueOfB())
    }

    @Test
    fun `U3 encode decode third variant`() {
        val union = Union.U3.ofC<String, Double, Boolean>(true)
        val json = format.encodeToString(serializerU3, union)
        assertEquals("true", json)

        val decoded = format.decodeFromString(serializerU3, "\"word\"")
        assertTrue(decoded.isA())
        assertEquals("word", decoded.valueOfA())
    }

    @Test
    fun `U4 encode decode fourth variant`() {
        val union = Union.U4.ofD<String, Double, Boolean, Int>(7)
        val json = format.encodeToString(serializerU4, union)
        assertEquals("7", json)

        val decoded = format.decodeFromString(serializerU4, "3.14")
        assertTrue(decoded.isB())
        assertEquals(3.14, decoded.valueOfB())
    }

    @Test
    fun `U5 encode decode second variant`() {
        val union = Union.U5.ofB<String, Double, Boolean, Int, Long>(9.9)
        val json = format.encodeToString(serializerU5, union)
        assertEquals("9.9", json)

        val decoded = format.decodeFromString(serializerU5, "123456789")
        assertTrue(decoded.isE())
        assertEquals(123456789L, decoded.valueOfE())
    }
}
