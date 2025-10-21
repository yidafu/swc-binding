package dev.yidafu.swc.booleanable

import dev.yidafu.swc.types.MatchPattern
import dev.yidafu.swc.types.TerserCompressOptions
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Serializable
data class StringOrBoolean(
    @Serializable(BooleanableStringSerializer::class)
    val value: BooleanableString? = null
)

@Serializable
data class BooleanableProps(
    @Serializable(BooleanableStringSerializer::class)
    val str: BooleanableString? = null,

    @Serializable(BooleanableFloatSerializer::class)
    val f: BooleanableFloat? = null,

    @Serializable(BooleanableArrayStringSerializer::class)
    val arr: BooleanableArrayString? = null,

    @Serializable(BooleanableArrayMatchPatternSerializer::class)
    val matches: BooleanableArrayMatchPattern? = null,

    @Serializable(BooleanableTerserCompressOptionsSerializer::class)
    val terser: BooleanableTerserCompressOptions? = null
)
val format = Json

class BooleanableTest {
    @Test
    fun `encode value which may be string or boolean`() {
        assertEquals(
            format.encodeToString(StringOrBoolean(Booleanable.ofValue("str"))),
            "{\"value\":\"str\"}"
        )

        assertEquals(
            format.encodeToString(StringOrBoolean(Booleanable.ofTrue())),
            "{\"value\":true}"
        )

        assertEquals(
            format.encodeToString(StringOrBoolean(Booleanable.ofFalse())),
            "{\"value\":false}"
        )
    }

    @Test
    fun `Booleanable is null`() {
        val v = StringOrBoolean()
        assertEquals(format.encodeToString(v), "{}")
    }

    @Test
    fun `decode value which may be string or boolean`() {
        val b1: StringOrBoolean = format.decodeFromString("""{"value":true}""")
        assertTrue(b1.value!!.isTrue())
        val b2: StringOrBoolean = format.decodeFromString("""{"value":false}""")
        assertTrue(b2.value!!.isFalse())

        val b3: StringOrBoolean = format.decodeFromString("""{"value":"str"}""")

        assertEquals(
            b3.value?.value,
            "str"
        )
    }

    @Test
    fun `encode BooleanableProps to String`() {
        val props = BooleanableProps(
            Booleanable.ofValue("string"),
            Booleanable.ofValue(1.23f),
            Booleanable.ofValue(arrayOf("foo", "bar")),
            Booleanable.ofValue(arrayOf(MatchPattern())),
            Booleanable.ofValue(
                TerserCompressOptions().apply {
                    arguments = true
                    booleans = true
                }
            )
        )
        val output = format.encodeToString(props)
        assertEquals(
            output,
            """{"str":"string","f":1.23,"arr":["foo","bar"],"matches":[{}],"terser":{"arguments":true,"booleans":true}}"""
        )
    }

    @Test
    fun `decode booleanable value`() {
        val input = """{"str":"string","f":1.23,"arr":["foo","bar"]}"""
        val arr: BooleanableProps = format.decodeFromString(input)
        assertEquals(arr.str?.value, "string")
        assertEquals(arr.f?.value, 1.23f)
        assertEquals(arr.arr?.value?.get(0), "foo")

        val output = format.encodeToString(arr)
        assertEquals(input, output)
    }

    @Test
    fun `Booleanable ofValue creates correct instance`() {
        val boolStr = Booleanable.ofValue("test")
        assertEquals("test", boolStr.value)
        assertTrue(!boolStr.isTrue())
        assertTrue(!boolStr.isFalse())
    }

    @Test
    fun `Booleanable ofTrue creates correct instance`() {
        val boolTrue = Booleanable.ofTrue<String>()
        assertEquals(null, boolTrue.value)
        assertTrue(boolTrue.isTrue())
        assertTrue(!boolTrue.isFalse())
    }

    @Test
    fun `Booleanable ofFalse creates correct instance`() {
        val boolFalse = Booleanable.ofFalse<String>()
        assertEquals(null, boolFalse.value)
        assertTrue(!boolFalse.isTrue())
        assertTrue(boolFalse.isFalse())
    }

    @Test
    fun `Booleanable onValue callback`() {
        var called = false
        Booleanable.ofValue("test").onValue {
            called = true
            assertEquals("test", it)
        }
        assertTrue(called)
    }

    @Test
    fun `Booleanable onBool callback`() {
        var called = false
        Booleanable.ofTrue<String>().onBool {
            called = true
            assertEquals(true, it)
        }
        assertTrue(called)
    }

    @Test
    fun `Booleanable onValue not called for boolean`() {
        var called = false
        Booleanable.ofTrue<String>().onValue {
            called = true
        }
        assertTrue(!called)
    }

    @Test
    fun `Booleanable onBool not called for value`() {
        var called = false
        Booleanable.ofValue("test").onBool {
            called = true
        }
        assertTrue(!called)
    }

    @Test
    fun `BooleanableInt with value`() {
        val boolInt = Booleanable.ofValue(42)
        assertEquals(42, boolInt.value)
    }

    @Test
    fun `BooleanableFloat with value`() {
        val boolFloat = Booleanable.ofValue(3.14f)
        assertEquals(3.14f, boolFloat.value)
    }

    @Test
    fun `BooleanableArrayString with value`() {
        val arr = Booleanable.ofValue(arrayOf("a", "b", "c"))
        assertEquals(3, arr.value?.size)
        assertEquals("a", arr.value?.get(0))
    }

    @Test
    fun `encode BooleanableInt`() {
        val boolInt = Booleanable.ofValue(100)
        val json = format.encodeToString(boolInt)
        assertEquals("100", json)
    }

    @Test
    fun `encode BooleanableFloat`() {
        val boolFloat = Booleanable.ofValue(2.5f)
        val json = format.encodeToString(boolFloat)
        assertEquals("2.5", json)
    }
}