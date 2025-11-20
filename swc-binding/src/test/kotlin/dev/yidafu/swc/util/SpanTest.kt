package dev.yidafu.swc.util

import dev.yidafu.swc.astJson
import dev.yidafu.swc.emptySpan
import dev.yidafu.swc.span
import dev.yidafu.swc.generated.Span
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

/**
 * Tests for Span class serialization/deserialization behavior, especially @EncodeDefault annotation behavior
 * * Problem analysis:
 * - When Rust serializes, if ctxt field value is 0 (default value), it won't serialize this field by default
 * - When Kotlin deserializes, missing ctxt will be set to default value 0 (coerceInputValues = true)
 * - @EncodeDefault behavior: only serializes when field value equals default value and encodeDefaults = true
 * - Key issue: fields missing during deserialization, even if set to default value, may not be output when serializing again
 */
class SpanTest : ShouldSpec({

    should("Directly created Span object with ctxt default value 0 should include ctxt field when serialized") {
        // Directly create Span object, ctxt uses default value 0
        val span = Span().apply {
            start = 10
            end = 20
            // ctxt uses default value 0
        }

        val json = astJson.encodeToString(span)

        // Verify JSON contains ctxt field (@EncodeDefault should take effect)
        json shouldContain "\"ctxt\""
        json shouldContain "\"ctxt\":0"
        json shouldBe """{"start":10,"end":20,"ctxt":0}"""
    }

    should("Directly created Span object with explicit ctxt = 0 should include ctxt field when serialized") {
        // Explicitly set ctxt = 0
        val span = Span().apply {
            start = 5
            end = 15
            ctxt = 0
        }

        val json = astJson.encodeToString(span)

        // Verify JSON contains ctxt field
        json shouldContain "\"ctxt\""
        json shouldContain "\"ctxt\":0"
        json shouldBe """{"start":5,"end":15,"ctxt":0}"""
    }

    should("Deserializing from JSON containing ctxt field should include ctxt field when serialized again") {
        // Deserialize from JSON containing ctxt field
        val jsonInput = """{"start":10,"end":20,"ctxt":5}"""
        val span = astJson.decodeFromString<Span>(jsonInput)

        // Verify deserialization result
        span.start shouldBe 10
        span.end shouldBe 20
        span.ctxt shouldBe 5

        // Serialize again
        val jsonOutput = astJson.encodeToString(span)

        // Verify JSON contains ctxt field
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldContain "\"ctxt\":5"
        jsonOutput shouldBe """{"start":10,"end":20,"ctxt":5}"""
    }

    should("Deserializing from JSON missing ctxt field (simulating Rust behavior) should include ctxt field when serialized again") {
        // Simulate JSON returned from Rust: missing ctxt field (because value is 0, Rust doesn't serialize by default)
        val jsonInput = """{"start":10,"end":20}"""
        val span = astJson.decodeFromString<Span>(jsonInput)

        // Verify deserialization result: ctxt should be set to default value 0 (coerceInputValues = true)
        span.start shouldBe 10
        span.end shouldBe 20
        span.ctxt shouldBe 0

        // Serialize again - this is the key test point
        val jsonOutput = astJson.encodeToString(span)

        // Verify JSON should contain ctxt field (@EncodeDefault should take effect)
        // Note: This is the key issue - if @EncodeDefault doesn't take effect, ctxt field will be missing here
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldContain "\"ctxt\":0"
        jsonOutput shouldBe """{"start":10,"end":20,"ctxt":0}"""
    }

    should("Deserializing from JSON missing ctxt field, modifying ctxt then serializing should include ctxt field") {
        // Deserialize from JSON missing ctxt field
        val jsonInput = """{"start":10,"end":20}"""
        val span = astJson.decodeFromString<Span>(jsonInput)

        // Modify ctxt value
        span.ctxt = 3

        // Serialize
        val jsonOutput = astJson.encodeToString(span)

        // Verify JSON contains ctxt field
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldContain "\"ctxt\":3"
        jsonOutput shouldBe """{"start":10,"end":20,"ctxt":3}"""
    }

    should("Deserializing from JSON missing ctxt field, explicitly setting ctxt = 0 then serializing should include ctxt field") {
        // Deserialize from JSON missing ctxt field
        val jsonInput = """{"start":10,"end":20}"""
        val span = astJson.decodeFromString<Span>(jsonInput)

        // Explicitly set ctxt = 0
        span.ctxt = 0

        // Serialize
        val jsonOutput = astJson.encodeToString(span)

        // Verify JSON contains ctxt field
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldContain "\"ctxt\":0"
        jsonOutput shouldBe """{"start":10,"end":20,"ctxt":0}"""
    }

    should("Span created with span() DSL should include ctxt field when serialized") {
        val span = span(start = 1, end = 10, ctxt = 0)

        val json = astJson.encodeToString(span)

        json shouldContain "\"ctxt\""
        json shouldContain "\"ctxt\":0"
        json shouldBe """{"start":1,"end":10,"ctxt":0}"""
    }

    should("Span created with emptySpan() should include ctxt field when serialized") {
        val span = emptySpan()

        val json = astJson.encodeToString(span)

        json shouldContain "\"ctxt\""
        json shouldContain "\"ctxt\":0"
        json shouldBe """{"start":0,"end":0,"ctxt":0}"""
    }

    should("Deserializing from JSON missing start and end fields should use default values") {
        val jsonInput = """{"ctxt":2}"""
        val span = astJson.decodeFromString<Span>(jsonInput)

        span.start shouldBe 0
        span.end shouldBe 0
        span.ctxt shouldBe 2

        val jsonOutput = astJson.encodeToString(span)
        jsonOutput shouldContain "\"start\""
        jsonOutput shouldContain "\"end\""
        jsonOutput shouldContain "\"ctxt\""
    }

    should("Deserializing from completely empty JSON object should use all default values") {
        val jsonInput = """{}"""
        val span = astJson.decodeFromString<Span>(jsonInput)

        span.start shouldBe 0
        span.end shouldBe 0
        span.ctxt shouldBe 0

        val jsonOutput = astJson.encodeToString(span)
        // All fields should be serialized (because they all have @EncodeDefault)
        jsonOutput shouldContain "\"start\""
        jsonOutput shouldContain "\"end\""
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldBe """{"start":0,"end":0,"ctxt":0}"""
    }

    should("Multiple serialize/deserialize cycles should keep ctxt field consistent") {
        // First: create and serialize
        val span1 = Span().apply {
            start = 10
            end = 20
            ctxt = 0
        }
        val json1 = astJson.encodeToString(span1)
        json1 shouldContain "\"ctxt\""

        // Second: deserialize and serialize again
        val span2 = astJson.decodeFromString<Span>(json1)
        val json2 = astJson.encodeToString(span2)
        json2 shouldContain "\"ctxt\""

        // Third: deserialize from JSON missing ctxt and serialize
        val jsonWithoutCtxt = """{"start":10,"end":20}"""
        val span3 = astJson.decodeFromString<Span>(jsonWithoutCtxt)
        val json3 = astJson.encodeToString(span3)
        json3 shouldContain "\"ctxt\""

        // Verify all JSON contain ctxt field
        json1 shouldContain "\"ctxt\""
        json2 shouldContain "\"ctxt\""
        json3 shouldContain "\"ctxt\""
    }

    should("Verify @EncodeDefault behavior for start and end fields") {
        // Test if @EncodeDefault for start and end fields also works correctly
        val span = Span().apply {
            start = 0
            end = 0
            ctxt = 0
        }

        val json = astJson.encodeToString(span)

        // All fields should be serialized
        json shouldContain "\"start\""
        json shouldContain "\"end\""
        json shouldContain "\"ctxt\""
        json shouldBe """{"start":0,"end":0,"ctxt":0}"""
    }

    should("Deserializing from JSON missing start field should use default value 0 for start, and include start field when serialized") {
        val jsonInput = """{"end":20,"ctxt":1}"""
        val span = astJson.decodeFromString<Span>(jsonInput)

        span.start shouldBe 0
        span.end shouldBe 20
        span.ctxt shouldBe 1

        val jsonOutput = astJson.encodeToString(span)
        jsonOutput shouldContain "\"start\""
        jsonOutput shouldContain "\"start\":0"
        jsonOutput shouldBe """{"start":0,"end":20,"ctxt":1}"""
    }

    should("Deserializing from JSON missing end field should use default value 0 for end, and include end field when serialized") {
        val jsonInput = """{"start":10,"ctxt":1}"""
        val span = astJson.decodeFromString<Span>(jsonInput)

        span.start shouldBe 10
        span.end shouldBe 0
        span.ctxt shouldBe 1

        val jsonOutput = astJson.encodeToString(span)
        jsonOutput shouldContain "\"end\""
        jsonOutput shouldContain "\"end\":0"
        jsonOutput shouldBe """{"start":10,"end":0,"ctxt":1}"""
    }

    should("Deserializing from JSON missing ctxt field (specific values), should have ctxt=0 after deserialization") {
        // Test specific JSON input: {"start":4258,"end":4270}
        val jsonInput = """{"start":4258,"end":4270}"""
        val span = astJson.decodeFromString<Span>(jsonInput)

        // Verify deserialization result: ctxt should be set to default value 0
        span.start shouldBe 4258
        span.end shouldBe 4270
        span.ctxt shouldBe 0

        // Serialize again, verify ctxt field should be included
        val jsonOutput = astJson.encodeToString(span)
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldContain "\"ctxt\":0"
        jsonOutput shouldBe """{"start":4258,"end":4270,"ctxt":0}"""
    }
})
