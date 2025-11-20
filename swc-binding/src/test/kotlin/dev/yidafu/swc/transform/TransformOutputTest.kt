package dev.yidafu.swc.transform

import dev.yidafu.swc.TransformOutput
import io.kotest.core.spec.style.ShouldSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TransformOutputTest : ShouldSpec({

    should("create TransformOutput with code only") {
        val output = TransformOutput(code = "const x = 1;")

        assertEquals("const x = 1;", output.code)
        assertNull(output.msg)
    }

    should("create TransformOutput with code and message") {
        val output = TransformOutput(
            code = "const x = 1;",
            msg = "Successfully transformed"
        )

        assertEquals("const x = 1;", output.code)
        assertEquals("Successfully transformed", output.msg)
    }

    should("serialize TransformOutput") {
        val output = TransformOutput(code = "const x = 1;", msg = "test")
        val json = Json.encodeToString(output)

        assertNotNull(json)
        assertEquals("""{"code":"const x = 1;","msg":"test"}""", json)
    }

    should("deserialize TransformOutput") {
        val json = """{"code":"const x = 1;","msg":"test"}"""
        val output = Json.decodeFromString<TransformOutput>(json)

        assertEquals("const x = 1;", output.code)
        assertEquals("test", output.msg)
    }

    should("deserialize TransformOutput without message") {
        val json = """{"code":"const x = 1;"}"""
        val output = Json.decodeFromString<TransformOutput>(json)

        assertEquals("const x = 1;", output.code)
        assertNull(output.msg)
    }

    should("empty code in TransformOutput") {
        val output = TransformOutput(code = "")

        assertEquals("", output.code)
    }

    should("TransformOutput with multiline code") {
        val code = """
            function test() {
                return 42;
            }
        """.trimIndent()

        val output = TransformOutput(code = code)

        assertEquals(code, output.code)
    }

    should("TransformOutput with special characters") {
        val code = "const str = \"Hello\\nWorld\\t!\";"
        val output = TransformOutput(code = code)

        assertEquals(code, output.code)
    }

    should("TransformOutput serialization round trip") {
        val original = TransformOutput(
            code = "const x = 1; const y = 2;",
            msg = "Transformed successfully"
        )

        val json = Json.encodeToString(original)
        val decoded = Json.decodeFromString<TransformOutput>(json)

        assertEquals(original.code, decoded.code)
        assertEquals(original.msg, decoded.msg)
    }

    should("TransformOutput with unicode characters") {
        val code = "const emoji = '👋 🌍 🎉';"
        val output = TransformOutput(code = code)

        assertEquals(code, output.code)
    }

    should("data class copy functionality") {
        val original = TransformOutput(code = "const x = 1;", msg = "test")
        val copied = original.copy(msg = "modified")

        assertEquals("const x = 1;", copied.code)
        assertEquals("modified", copied.msg)
    }

    should("data class equals and hashCode") {
        val output1 = TransformOutput(code = "const x = 1;", msg = "test")
        val output2 = TransformOutput(code = "const x = 1;", msg = "test")

        assertEquals(output1, output2)
        assertEquals(output1.hashCode(), output2.hashCode())
    }

    should("TransformOutput toString") {
        val output = TransformOutput(code = "const x = 1;", msg = "test")
        val str = output.toString()

        assertNotNull(str)
        assert(str.contains("const x = 1;"))
    }
})
